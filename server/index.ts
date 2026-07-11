import path from "node:path";
import express from "express";
import cookieParser from "cookie-parser";
import cors from "cors";
import type { SqlValue } from "sql.js";
import { initDb, run, get, all, createCode, addLog, saveAttachment, listAttachments, listLogs, maskPhone, findComplaintByCode, findComplaintById } from "./db.js";
import { login, logout, requireAuth } from "./auth.js";
import { upload } from "./uploads.js";
import type { AuthRequest, ComplaintRow } from "./types.js";

const app = express();
const port = Number(process.env.PORT || 3000);

app.use(cors({ origin: true, credentials: true }));
app.use(cookieParser());
app.use(express.json());
app.use("/uploads", express.static(path.join(process.cwd(), "uploads")));

function now() {
  return new Date().toISOString();
}

function publicComplaint(row: ComplaintRow) {
  return {
    code: row.code,
    county: row.county,
    category: row.category,
    title: row.title,
    content: row.content,
    status: row.status,
    assignee: row.assignee,
    createdAt: row.created_at,
    updatedAt: row.updated_at,
    supplementRequested: Boolean(row.supplement_requested),
    evaluationScore: row.evaluation_score,
    attachments: listAttachments(row.id).map((item) => ({
      id: item.id,
      kind: item.kind,
      name: item.original_name,
      path: item.path,
      size: item.size
    })),
    logs: listLogs(row.id).map((item) => ({
      action: item.action,
      actor: item.actor,
      note: item.note,
      createdAt: item.created_at
    }))
  };
}

function adminComplaint(row: ComplaintRow, req: AuthRequest) {
  const isCounty = req.user?.role === "county";
  return {
    ...publicComplaint(row),
    id: row.id,
    mode: row.mode,
    name: row.mode === "realname" ? row.name : "",
    phone: row.mode === "realname" ? (isCounty ? row.phone : maskPhone(row.phone)) : "",
    rawPhoneVisible: isCounty,
    evaluationComment: row.evaluation_comment
  };
}

function countyWhere(req: AuthRequest) {
  if (req.user?.role === "county") return { sql: " AND county = ?", params: [req.user.county] };
  return { sql: "", params: [] as SqlValue[] };
}

app.get("/api/health", (_req, res) => {
  res.json({ ok: true, name: "complaint-feedback-platform" });
});

app.post("/api/auth/login", (req, res) => {
  const username = String(req.body.username || "");
  const password = String(req.body.password || "");
  const session = login(username, password);
  if (!session) {
    res.status(401).json({ message: "账号或密码错误" });
    return;
  }
  res.cookie("session", session.token, {
    httpOnly: true,
    sameSite: "lax",
    maxAge: 1000 * 60 * 60 * 8
  });
  res.json({ user: session });
});

app.post("/api/auth/logout", (req, res) => {
  logout(req.cookies?.session);
  res.clearCookie("session");
  res.json({ ok: true });
});

app.get("/api/auth/me", requireAuth, (req: AuthRequest, res) => {
  res.json({ user: req.user });
});

app.post("/api/complaints", upload.array("attachments", 5), (req, res) => {
  const files = (req.files || []) as Express.Multer.File[];
  const mode = req.body.mode === "realname" ? "realname" : "anonymous";
  const county = String(req.body.county || "").trim();
  const category = String(req.body.category || "").trim();
  const title = String(req.body.title || "").trim();
  const content = String(req.body.content || "").trim();
  const name = mode === "realname" ? String(req.body.name || "").trim() : "";
  const phone = mode === "realname" ? String(req.body.phone || "").trim() : "";

  if (!county || !category || !title || !content) {
    res.status(400).json({ message: "请完整填写县区、类型、标题和内容" });
    return;
  }
  if (mode === "realname" && (!name || !phone)) {
    res.status(400).json({ message: "实名反馈请填写姓名和联系电话" });
    return;
  }

  const code = createCode();
  const timestamp = now();
  run(
    `INSERT INTO complaints
      (code, mode, county, category, title, content, name, phone, status, assignee, created_at, updated_at, supplement_requested)
     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
    [code, mode, county, category, title, content, name || null, phone || null, "待受理", null, timestamp, timestamp, 0]
  );
  const complaint = findComplaintByCode(code);
  if (!complaint) {
    res.status(500).json({ message: "提交失败" });
    return;
  }
  for (const file of files) saveAttachment(complaint.id, "initial", file);
  addLog(complaint.id, "提交", mode === "realname" ? name : "匿名群众", "公众提交投诉反馈");
  res.status(201).json({ code, complaint: publicComplaint(complaint) });
});

app.get("/api/complaints/:code", (req, res) => {
  const complaint = findComplaintByCode(String(req.params.code || "").toUpperCase());
  if (!complaint) {
    res.status(404).json({ message: "未查询到对应事项" });
    return;
  }
  res.json({ complaint: publicComplaint(complaint) });
});

app.post("/api/complaints/:code/supplement", upload.array("attachments", 5), (req, res) => {
  const complaint = findComplaintByCode(String(req.params.code || "").toUpperCase());
  if (!complaint) {
    res.status(404).json({ message: "未查询到对应事项" });
    return;
  }
  if (!complaint.supplement_requested) {
    res.status(400).json({ message: "当前事项不需要补充材料" });
    return;
  }
  const note = String(req.body.note || "").trim();
  const files = (req.files || []) as Express.Multer.File[];
  if (!note && files.length === 0) {
    res.status(400).json({ message: "请填写补充说明或上传附件" });
    return;
  }
  for (const file of files) saveAttachment(complaint.id, "supplement", file);
  run("UPDATE complaints SET status = ?, supplement_requested = 0, updated_at = ? WHERE id = ?", ["待受理", now(), complaint.id]);
  addLog(complaint.id, "补充材料", "公众", note || "已补充附件材料");
  res.json({ complaint: publicComplaint(findComplaintById(complaint.id)!) });
});

app.post("/api/complaints/:code/evaluation", (req, res) => {
  const complaint = findComplaintByCode(String(req.params.code || "").toUpperCase());
  if (!complaint) {
    res.status(404).json({ message: "未查询到对应事项" });
    return;
  }
  if (complaint.status !== "已办结") {
    res.status(400).json({ message: "事项办结后才能评价" });
    return;
  }
  const score = Number(req.body.score);
  const comment = String(req.body.comment || "").trim();
  if (!Number.isInteger(score) || score < 1 || score > 5) {
    res.status(400).json({ message: "满意度评分需为 1 到 5 分" });
    return;
  }
  run("UPDATE complaints SET evaluation_score = ?, evaluation_comment = ?, updated_at = ? WHERE id = ?", [score, comment, now(), complaint.id]);
  addLog(complaint.id, "评价", "公众", `${score} 分${comment ? `：${comment}` : ""}`);
  res.json({ complaint: publicComplaint(findComplaintById(complaint.id)!) });
});

app.get("/api/admin/stats", requireAuth, (req: AuthRequest, res) => {
  const where = countyWhere(req);
  const rows = all<{ status: string; total: number }>(`SELECT status, COUNT(*) as total FROM complaints WHERE 1=1${where.sql} GROUP BY status`, where.params);
  const countyRows = all<{ county: string; total: number }>(
    `SELECT county, COUNT(*) as total FROM complaints WHERE 1=1${where.sql} GROUP BY county ORDER BY total DESC`,
    where.params
  );
  const total = get<{ total: number }>(`SELECT COUNT(*) as total FROM complaints WHERE 1=1${where.sql}`, where.params)?.total ?? 0;
  res.json({ total, byStatus: rows, byCounty: countyRows });
});

app.get("/api/admin/complaints", requireAuth, (req: AuthRequest, res) => {
  const params: SqlValue[] = [];
  let sql = "SELECT * FROM complaints WHERE 1=1";
  if (req.user?.role === "county") {
    sql += " AND county = ?";
    params.push(req.user.county);
  } else if (req.query.county) {
    sql += " AND county = ?";
    params.push(String(req.query.county));
  }
  if (req.query.status && req.query.status !== "all") {
    sql += " AND status = ?";
    params.push(String(req.query.status));
  }
  if (req.query.keyword) {
    sql += " AND (code LIKE ? OR title LIKE ? OR content LIKE ? OR category LIKE ?)";
    const keyword = `%${String(req.query.keyword)}%`;
    params.push(keyword, keyword, keyword, keyword);
  }
  if (req.query.from) {
    sql += " AND created_at >= ?";
    params.push(String(req.query.from));
  }
  if (req.query.to) {
    sql += " AND created_at <= ?";
    params.push(String(req.query.to));
  }
  sql += " ORDER BY id DESC";
  const rows = all<ComplaintRow>(sql, params);
  res.json({ complaints: rows.map((row) => adminComplaint(row, req)) });
});

app.get("/api/admin/complaints/:id", requireAuth, (req: AuthRequest, res) => {
  const complaint = findComplaintById(Number(req.params.id));
  if (!complaint || (req.user?.role === "county" && complaint.county !== req.user.county)) {
    res.status(404).json({ message: "未查询到对应事项" });
    return;
  }
  res.json({ complaint: adminComplaint(complaint, req) });
});

app.post("/api/admin/complaints/:id/actions", requireAuth, (req: AuthRequest, res) => {
  const complaint = findComplaintById(Number(req.params.id));
  if (!complaint || (req.user?.role === "county" && complaint.county !== req.user.county)) {
    res.status(404).json({ message: "未查询到对应事项" });
    return;
  }

  const action = String(req.body.action || "");
  const note = String(req.body.note || "").trim();
  const assignee = String(req.body.assignee || "").trim();
  let status = complaint.status;
  let supplementRequested = complaint.supplement_requested;
  let nextAssignee = complaint.assignee;

  if (action === "accept") status = "办理中";
  else if (action === "requestSupplement") {
    status = "待补充";
    supplementRequested = 1;
  } else if (action === "assign") {
    status = "办理中";
    nextAssignee = assignee || "综合承办单位";
  } else if (action === "finish") {
    status = "已办结";
  } else if (action !== "supervise") {
    res.status(400).json({ message: "不支持的办理动作" });
    return;
  }

  run("UPDATE complaints SET status = ?, assignee = ?, supplement_requested = ?, updated_at = ? WHERE id = ?", [
    status,
    nextAssignee,
    supplementRequested,
    now(),
    complaint.id
  ]);
  const actionLabels: Record<string, string> = {
    accept: "受理",
    requestSupplement: "退回补充",
    assign: "转派",
    finish: "办结",
    supervise: "督办"
  };
  addLog(complaint.id, actionLabels[action], req.user?.label || "管理员", note || nextAssignee || "后台操作");
  res.json({ complaint: adminComplaint(findComplaintById(complaint.id)!, req) });
});

app.get("/api/admin/export.csv", requireAuth, (req: AuthRequest, res) => {
  const where = countyWhere(req);
  const rows = all<ComplaintRow>(`SELECT * FROM complaints WHERE 1=1${where.sql} ORDER BY id DESC`, where.params);
  const header = ["编号", "县区", "类型", "标题", "提交方式", "状态", "承办单位", "创建时间", "更新时间"];
  const body = rows.map((row) =>
    [row.code, row.county, row.category, row.title, row.mode === "realname" ? "实名" : "匿名", row.status, row.assignee || "", row.created_at, row.updated_at]
      .map((value) => `"${String(value).replaceAll('"', '""')}"`)
      .join(",")
  );
  const csv = `\uFEFF${header.join(",")}\n${body.join("\n")}`;
  res.setHeader("Content-Type", "text/csv; charset=utf-8");
  res.setHeader("Content-Disposition", "attachment; filename=complaints.csv");
  res.send(csv);
});

const clientDir = path.join(process.cwd(), "dist", "client");
app.use(express.static(clientDir));
app.get(/.*/, (_req, res) => {
  res.sendFile(path.join(clientDir, "index.html"));
});

await initDb();

if (process.env.NODE_ENV !== "test") {
  app.listen(port, "0.0.0.0", () => {
    console.log(`Complaint platform listening on http://127.0.0.1:${port}`);
  });
}

export { app };

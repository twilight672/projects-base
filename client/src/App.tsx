import { useEffect, useMemo, useState } from "react";
import QRCode from "qrcode";
import {
  BarChart3,
  Building2,
  CheckCircle2,
  ClipboardList,
  Download,
  FileText,
  LogOut,
  MessageSquareText,
  Paperclip,
  Search,
  ShieldCheck,
  Smartphone,
  UploadCloud
} from "lucide-react";

type View = "submit" | "query" | "admin";
type Mode = "anonymous" | "realname";
type Role = "city" | "county";
type Status = "待受理" | "办理中" | "待补充" | "已办结";

type User = {
  id: number;
  username: string;
  role: Role;
  county: string | null;
  label: string;
};

type Attachment = {
  id: number;
  kind: "initial" | "supplement";
  name: string;
  path: string;
  size: number;
};

type LogItem = {
  action: string;
  actor: string;
  note: string;
  createdAt: string;
};

type Complaint = {
  id?: number;
  code: string;
  mode?: Mode;
  county: string;
  category: string;
  title: string;
  content: string;
  status: Status;
  assignee: string | null;
  createdAt: string;
  updatedAt: string;
  name?: string;
  phone?: string;
  rawPhoneVisible?: boolean;
  supplementRequested: boolean;
  evaluationScore: number | null;
  evaluationComment?: string | null;
  attachments: Attachment[];
  logs: LogItem[];
};

type Stats = {
  total: number;
  byStatus: Array<{ status: Status; total: number }>;
  byCounty: Array<{ county: string; total: number }>;
};

const counties = ["东城区", "西城区", "南城区", "北城区"];
const categories = ["政务服务", "城市管理", "市场监管", "民生保障", "其他问题"];
const departments = ["综合承办单位", "政务服务中心", "城市管理局", "市场监管所", "住建维护队", "民生保障窗口"];
const statuses: Array<Status | "all"> = ["all", "待受理", "办理中", "待补充", "已办结"];

function statusClass(status: Status) {
  if (status === "办理中") return "processing";
  if (status === "待补充") return "supplement";
  if (status === "已办结") return "done";
  return "pending";
}

function formatDate(value: string) {
  return new Date(value).toLocaleString("zh-CN", { hour12: false });
}

async function api<T>(url: string, options: RequestInit = {}): Promise<T> {
  const response = await fetch(url, {
    credentials: "include",
    ...options,
    headers: options.body instanceof FormData ? options.headers : { "Content-Type": "application/json", ...options.headers }
  });
  const text = await response.text();
  const data = text ? JSON.parse(text) : {};
  if (!response.ok) throw new Error(data.message || "请求失败");
  return data as T;
}

export function App() {
  const [view, setView] = useState<View>("submit");
  const [mode, setMode] = useState<Mode>("anonymous");
  const [toast, setToast] = useState("");
  const [qr, setQr] = useState("");
  const [createdCode, setCreatedCode] = useState("");
  const [queryCode, setQueryCode] = useState("");
  const [queryResult, setQueryResult] = useState<Complaint | null>(null);
  const [user, setUser] = useState<User | null>(null);
  const [stats, setStats] = useState<Stats>({ total: 0, byStatus: [], byCounty: [] });
  const [complaints, setComplaints] = useState<Complaint[]>([]);
  const [selected, setSelected] = useState<Complaint | null>(null);
  const [filters, setFilters] = useState({ status: "all", county: "", keyword: "" });

  const visibleStatus = useMemo(
    () => Object.fromEntries(stats.byStatus.map((item) => [item.status, item.total])),
    [stats]
  );

  useEffect(() => {
    QRCode.toDataURL(window.location.href.split("#")[0], { width: 220, margin: 1 }).then(setQr);
    api<{ user: User }>("/api/auth/me").then((data) => setUser(data.user)).catch(() => undefined);
  }, []);

  useEffect(() => {
    if (!toast) return;
    const timer = window.setTimeout(() => setToast(""), 2800);
    return () => window.clearTimeout(timer);
  }, [toast]);

  useEffect(() => {
    if (view === "admin" && user) void refreshAdmin();
  }, [view, user, filters.status, filters.county]);

  function notice(message: string) {
    setToast(message);
  }

  async function refreshAdmin() {
    const query = new URLSearchParams();
    if (filters.status !== "all") query.set("status", filters.status);
    if (filters.county) query.set("county", filters.county);
    if (filters.keyword) query.set("keyword", filters.keyword);
    const [statsData, listData] = await Promise.all([
      api<{ total: number; byStatus: Stats["byStatus"]; byCounty: Stats["byCounty"] }>("/api/admin/stats"),
      api<{ complaints: Complaint[] }>(`/api/admin/complaints?${query.toString()}`)
    ]);
    setStats(statsData);
    setComplaints(listData.complaints);
    if (listData.complaints[0]) setSelected(listData.complaints[0]);
  }

  async function submitComplaint(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const form = event.currentTarget;
    const formData = new FormData(form);
    formData.set("mode", mode);
    const data = await api<{ code: string }>("/api/complaints", { method: "POST", body: formData });
    form.reset();
    setMode("anonymous");
    setCreatedCode(data.code);
    setQueryCode(data.code);
    notice(`提交成功，查询编号：${data.code}`);
  }

  async function queryComplaint(event?: React.FormEvent) {
    event?.preventDefault();
    if (!queryCode.trim()) return;
    const data = await api<{ complaint: Complaint }>(`/api/complaints/${queryCode.trim().toUpperCase()}`);
    setQueryResult(data.complaint);
  }

  async function submitSupplement(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!queryResult) return;
    const formData = new FormData(event.currentTarget);
    const data = await api<{ complaint: Complaint }>(`/api/complaints/${queryResult.code}/supplement`, {
      method: "POST",
      body: formData
    });
    event.currentTarget.reset();
    setQueryResult(data.complaint);
    notice("补充材料已提交");
  }

  async function submitEvaluation(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!queryResult) return;
    const formData = new FormData(event.currentTarget);
    const data = await api<{ complaint: Complaint }>(`/api/complaints/${queryResult.code}/evaluation`, {
      method: "POST",
      body: JSON.stringify({
        score: Number(formData.get("score")),
        comment: formData.get("comment")
      })
    });
    setQueryResult(data.complaint);
    notice("评价已提交");
  }

  async function loginAdmin(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    const data = await api<{ user: User }>("/api/auth/login", {
      method: "POST",
      body: JSON.stringify({
        username: formData.get("username"),
        password: formData.get("password")
      })
    });
    setUser(data.user);
    event.currentTarget.reset();
    notice("登录成功");
  }

  async function logoutAdmin() {
    await api("/api/auth/logout", { method: "POST", body: JSON.stringify({}) });
    setUser(null);
    setComplaints([]);
    setSelected(null);
  }

  async function adminAction(action: string, note = "", assignee = "") {
    if (!selected?.id) return;
    const data = await api<{ complaint: Complaint }>(`/api/admin/complaints/${selected.id}/actions`, {
      method: "POST",
      body: JSON.stringify({ action, note, assignee })
    });
    setSelected(data.complaint);
    await refreshAdmin();
    notice("办理状态已更新");
  }

  async function openDetail(item: Complaint) {
    if (!item.id) return;
    const data = await api<{ complaint: Complaint }>(`/api/admin/complaints/${item.id}`);
    setSelected(data.complaint);
  }

  return (
    <div className="app-shell">
      <header className="topbar">
        <button className="brand" onClick={() => setView("submit")} type="button">
          <span className="brand-mark">民</span>
          <span>
            <strong>民生诉求反馈平台</strong>
            <small>统一提交 · 分级办理 · 闭环跟踪</small>
          </span>
        </button>
        <nav className="nav-tabs" aria-label="主导航">
          <button className={view === "submit" ? "active" : ""} onClick={() => setView("submit")} type="button">
            <MessageSquareText size={17} /> 我要反馈
          </button>
          <button className={view === "query" ? "active" : ""} onClick={() => setView("query")} type="button">
            <Search size={17} /> 进度查询
          </button>
          <button className={view === "admin" ? "active" : ""} onClick={() => setView("admin")} type="button">
            <ShieldCheck size={17} /> 管理后台
          </button>
        </nav>
      </header>

      {view === "submit" && (
        <main className="public-grid">
          <section className="public-intro">
            <p className="eyebrow">跨端反馈入口</p>
            <h1>群众投诉与诉求，统一提交、分级流转、限时办理。</h1>
            <p>支持电脑端和手机端访问，可匿名或实名提交，附件材料随诉求一并流转。</p>
            <div className="qr-panel">
              {qr && <img src={qr} alt="扫码打开反馈页面" />}
              <div>
                <strong>扫码打开</strong>
                <span>二维码自动指向当前访问地址，公网部署后可直接扫码访问。</span>
              </div>
            </div>
            <div className="feature-line">
              <span><Smartphone size={16} /> 手机适配</span>
              <span><Paperclip size={16} /> 附件上传</span>
              <span><ClipboardList size={16} /> 编号查询</span>
            </div>
          </section>

          <section className="work-panel">
            <div className="panel-head">
              <div>
                <p className="eyebrow">投诉反馈</p>
                <h2>填写反馈信息</h2>
              </div>
              <div className="segmented">
                <button className={mode === "anonymous" ? "active" : ""} onClick={() => setMode("anonymous")} type="button">匿名</button>
                <button className={mode === "realname" ? "active" : ""} onClick={() => setMode("realname")} type="button">实名</button>
              </div>
            </div>
            <form className="form-grid" onSubmit={submitComplaint}>
              <div className="cols-2">
                <FieldSelect label="所属县区" name="county" options={counties} />
                <FieldSelect label="诉求类型" name="category" options={categories} />
              </div>
              <label>标题<input name="title" maxLength={60} placeholder="请简要概括投诉或诉求" required /></label>
              <label>详细内容<textarea name="content" rows={6} maxLength={1000} placeholder="请描述发生时间、地点、问题经过和希望处理结果" required /></label>
              {mode === "realname" && (
                <div className="cols-2">
                  <label>姓名<input name="name" maxLength={20} placeholder="请输入姓名" required /></label>
                  <label>联系电话<input name="phone" inputMode="tel" maxLength={20} placeholder="便于工作人员联系核实" required /></label>
                </div>
              )}
              <label>附件材料<input name="attachments" type="file" multiple accept="image/*,.pdf,.doc,.docx,.xls,.xlsx" /></label>
              <div className="form-actions">
                <span>单个附件不超过 10MB，最多 5 个。</span>
                <button className="primary-btn" type="submit"><UploadCloud size={18} /> 提交反馈</button>
              </div>
            </form>
            {createdCode && <div className="success-box">提交成功，查询编号：<strong>{createdCode}</strong></div>}
          </section>
        </main>
      )}

      {view === "query" && (
        <main className="query-layout">
          <section className="work-panel">
            <div className="panel-head">
              <div>
                <p className="eyebrow">进度查询</p>
                <h2>输入查询编号</h2>
              </div>
            </div>
            <form className="query-row" onSubmit={queryComplaint}>
              <input value={queryCode} onChange={(event) => setQueryCode(event.target.value)} placeholder="例如：MS202604250001" />
              <button className="primary-btn" type="submit"><Search size={17} /> 查询</button>
            </form>
            {queryResult ? (
              <ComplaintTimeline complaint={queryResult} onSupplement={submitSupplement} onEvaluation={submitEvaluation} />
            ) : (
              <div className="empty-state">提交成功后，可使用系统生成的编号查询办理状态。</div>
            )}
          </section>
        </main>
      )}

      {view === "admin" && (
        <main className="admin-layout">
          {!user ? (
            <section className="login-panel">
              <div>
                <p className="eyebrow">管理后台</p>
                <h2>分级账号登录</h2>
                <p>市级查看全市统计和督办，县级只办理本县区事项。</p>
              </div>
              <form className="form-grid" onSubmit={loginAdmin}>
                <label>账号<input name="username" autoComplete="username" required placeholder="city / county-east / county-west" /></label>
                <label>密码<input name="password" type="password" autoComplete="current-password" required placeholder="123456" /></label>
                <button className="primary-btn" type="submit"><ShieldCheck size={18} /> 登录后台</button>
              </form>
            </section>
          ) : (
            <>
              <section className="admin-head">
                <div>
                  <p className="eyebrow">{user.role === "city" ? "市级总览" : `${user.county} 办理工作台`}</p>
                  <h2>投诉办理工作台</h2>
                </div>
                <div className="admin-actions">
                  <a className="ghost-btn" href="/api/admin/export.csv"><Download size={16} /> 导出 CSV</a>
                  <button className="ghost-btn" onClick={logoutAdmin} type="button"><LogOut size={16} /> 退出</button>
                </div>
              </section>

              <section className="metrics-grid">
                <Metric icon={<BarChart3 />} label="全部事项" value={stats.total} />
                <Metric label="待受理" value={visibleStatus["待受理"] || 0} />
                <Metric label="办理中" value={visibleStatus["办理中"] || 0} />
                <Metric label="已办结" value={visibleStatus["已办结"] || 0} />
              </section>

              <section className="admin-main">
                <div className="list-pane">
                  <div className="toolbar">
                    <select value={filters.status} onChange={(event) => setFilters({ ...filters, status: event.target.value })}>
                      {statuses.map((status) => <option key={status} value={status}>{status === "all" ? "全部状态" : status}</option>)}
                    </select>
                    {user.role === "city" && (
                      <select value={filters.county} onChange={(event) => setFilters({ ...filters, county: event.target.value })}>
                        <option value="">全部县区</option>
                        {counties.map((county) => <option key={county}>{county}</option>)}
                      </select>
                    )}
                    <input value={filters.keyword} onChange={(event) => setFilters({ ...filters, keyword: event.target.value })} onBlur={refreshAdmin} placeholder="搜索编号、标题、内容" />
                    <button className="ghost-btn" onClick={refreshAdmin} type="button"><Search size={16} /> 搜索</button>
                  </div>
                  <div className="complaint-list">
                    {complaints.map((item) => (
                      <button key={item.code} className={selected?.code === item.code ? "complaint-row active" : "complaint-row"} onClick={() => openDetail(item)} type="button">
                        <span className={`status ${statusClass(item.status)}`}>{item.status}</span>
                        <strong>{item.title}</strong>
                        <small>{item.code} · {item.county} · {formatDate(item.createdAt)}</small>
                      </button>
                    ))}
                    {complaints.length === 0 && <div className="empty-state">暂无符合条件的事项。</div>}
                  </div>
                </div>
                <DetailPane complaint={selected} user={user} onAction={adminAction} countyRank={stats.byCounty} />
              </section>
            </>
          )}
        </main>
      )}

      <div className={toast ? "toast visible" : "toast"}>{toast}</div>
    </div>
  );
}

function FieldSelect({ label, name, options }: { label: string; name: string; options: string[] }) {
  return (
    <label>{label}
      <select name={name} required>
        <option value="">请选择</option>
        {options.map((item) => <option key={item}>{item}</option>)}
      </select>
    </label>
  );
}

function Metric({ icon, label, value }: { icon?: React.ReactNode; label: string; value: number }) {
  return (
    <div className="metric">
      <span>{icon || <Building2 />}</span>
      <strong>{value}</strong>
      <small>{label}</small>
    </div>
  );
}

function ComplaintTimeline({
  complaint,
  onSupplement,
  onEvaluation
}: {
  complaint: Complaint;
  onSupplement: (event: React.FormEvent<HTMLFormElement>) => void;
  onEvaluation: (event: React.FormEvent<HTMLFormElement>) => void;
}) {
  return (
    <div className="query-result">
      <div className="result-summary">
        <span className={`status ${statusClass(complaint.status)}`}>{complaint.status}</span>
        <h3>{complaint.title}</h3>
        <p>{complaint.code} · {complaint.county} · {complaint.category}</p>
      </div>
      <div className="timeline">
        {complaint.logs.map((log, index) => (
          <div className="timeline-item" key={`${log.createdAt}-${index}`}>
            <strong>{log.action}</strong>
            <span>{log.actor} · {formatDate(log.createdAt)}</span>
            <p>{log.note}</p>
          </div>
        ))}
      </div>
      {complaint.attachments.length > 0 && <AttachmentList attachments={complaint.attachments} />}
      {complaint.supplementRequested && (
        <form className="inline-form" onSubmit={onSupplement}>
          <h3>补充材料</h3>
          <textarea name="note" rows={3} placeholder="请填写补充说明" />
          <input name="attachments" type="file" multiple accept="image/*,.pdf,.doc,.docx,.xls,.xlsx" />
          <button className="primary-btn" type="submit">提交补充</button>
        </form>
      )}
      {complaint.status === "已办结" && !complaint.evaluationScore && (
        <form className="inline-form" onSubmit={onEvaluation}>
          <h3>满意度评价</h3>
          <select name="score" required>
            <option value="">请选择评分</option>
            {[5, 4, 3, 2, 1].map((score) => <option key={score} value={score}>{score} 分</option>)}
          </select>
          <textarea name="comment" rows={3} placeholder="可填写评价意见" />
          <button className="primary-btn" type="submit"><CheckCircle2 size={16} /> 提交评价</button>
        </form>
      )}
    </div>
  );
}

function DetailPane({
  complaint,
  user,
  onAction,
  countyRank
}: {
  complaint: Complaint | null;
  user: User;
  onAction: (action: string, note?: string, assignee?: string) => void;
  countyRank: Array<{ county: string; total: number }>;
}) {
  const [note, setNote] = useState("");
  const [assignee, setAssignee] = useState(departments[0]);

  useEffect(() => {
    setNote("");
    setAssignee(departments[0]);
  }, [complaint?.code]);

  if (!complaint) {
    return <aside className="detail-pane empty-state">选择左侧事项查看详情。</aside>;
  }

  return (
    <aside className="detail-pane">
      <div className="detail-title">
        <span className={`status ${statusClass(complaint.status)}`}>{complaint.status}</span>
        <h3>{complaint.title}</h3>
        <p>{complaint.code} · {complaint.county} · {formatDate(complaint.createdAt)}</p>
      </div>
      <dl className="detail-grid">
        <div><dt>类型</dt><dd>{complaint.category}</dd></div>
        <div><dt>提交方式</dt><dd>{complaint.mode === "realname" ? "实名" : "匿名"}</dd></div>
        <div><dt>姓名</dt><dd>{complaint.name || "-"}</dd></div>
        <div><dt>电话</dt><dd>{complaint.phone || "-"}</dd></div>
        <div><dt>承办单位</dt><dd>{complaint.assignee || "未转派"}</dd></div>
        <div><dt>评价</dt><dd>{complaint.evaluationScore ? `${complaint.evaluationScore} 分` : "未评价"}</dd></div>
      </dl>
      {user.role === "city" && (
        <div className="rank-box">
          <h4>县区事项排行</h4>
          {countyRank.map((item) => <span key={item.county}>{item.county}<strong>{item.total}</strong></span>)}
        </div>
      )}
      <section className="content-box">
        <h4>诉求内容</h4>
        <p>{complaint.content}</p>
      </section>
      <AttachmentList attachments={complaint.attachments} />
      <section className="action-box">
        <h4>办理操作</h4>
        <textarea value={note} onChange={(event) => setNote(event.target.value)} rows={3} placeholder="填写办理意见或督办说明" />
        {user.role === "county" && (
          <select value={assignee} onChange={(event) => setAssignee(event.target.value)}>
            {departments.map((item) => <option key={item}>{item}</option>)}
          </select>
        )}
        <div className="action-buttons">
          {user.role === "county" && (
            <>
              <button className="ghost-btn" onClick={() => onAction("accept", note)} type="button">受理</button>
              <button className="ghost-btn" onClick={() => onAction("assign", note, assignee)} type="button">转派</button>
              <button className="ghost-btn" onClick={() => onAction("requestSupplement", note)} type="button">退回补充</button>
              <button className="primary-btn" onClick={() => onAction("finish", note)} type="button">办结</button>
            </>
          )}
          {user.role === "city" && <button className="primary-btn" onClick={() => onAction("supervise", note)} type="button">督办</button>}
        </div>
      </section>
      <section className="timeline compact">
        {complaint.logs.map((log, index) => (
          <div className="timeline-item" key={`${log.createdAt}-${index}`}>
            <strong>{log.action}</strong>
            <span>{log.actor} · {formatDate(log.createdAt)}</span>
            <p>{log.note}</p>
          </div>
        ))}
      </section>
    </aside>
  );
}

function AttachmentList({ attachments }: { attachments: Attachment[] }) {
  if (attachments.length === 0) return null;
  return (
    <div className="attachment-list">
      <h4>附件材料</h4>
      {attachments.map((file) => (
        <a href={file.path} target="_blank" rel="noreferrer" key={file.id}>
          <FileText size={16} />
          <span>{file.name}</span>
          <small>{Math.ceil(file.size / 1024)} KB</small>
        </a>
      ))}
    </div>
  );
}

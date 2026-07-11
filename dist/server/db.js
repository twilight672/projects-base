import fs from "node:fs";
import path from "node:path";
import crypto from "node:crypto";
import initSqlJs from "sql.js";
const rootDir = process.cwd();
const dataDir = path.join(rootDir, "data");
const dbPath = path.join(dataDir, "complaints.sqlite");
let SQL = null;
let database = null;
export function hashPassword(password) {
    return crypto.createHash("sha256").update(password).digest("hex");
}
function now() {
    return new Date().toISOString();
}
function persist() {
    if (!database)
        return;
    fs.mkdirSync(dataDir, { recursive: true });
    fs.writeFileSync(dbPath, Buffer.from(database.export()));
}
export async function initDb() {
    fs.mkdirSync(dataDir, { recursive: true });
    SQL ??= await initSqlJs();
    database = fs.existsSync(dbPath) ? new SQL.Database(fs.readFileSync(dbPath)) : new SQL.Database();
    run(`
    CREATE TABLE IF NOT EXISTS users (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      username TEXT NOT NULL UNIQUE,
      password_hash TEXT NOT NULL,
      role TEXT NOT NULL,
      county TEXT,
      label TEXT NOT NULL
    )
  `);
    run(`
    CREATE TABLE IF NOT EXISTS complaints (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      code TEXT NOT NULL UNIQUE,
      mode TEXT NOT NULL,
      county TEXT NOT NULL,
      category TEXT NOT NULL,
      title TEXT NOT NULL,
      content TEXT NOT NULL,
      name TEXT,
      phone TEXT,
      status TEXT NOT NULL,
      assignee TEXT,
      created_at TEXT NOT NULL,
      updated_at TEXT NOT NULL,
      supplement_requested INTEGER NOT NULL DEFAULT 0,
      evaluation_score INTEGER,
      evaluation_comment TEXT
    )
  `);
    run(`
    CREATE TABLE IF NOT EXISTS attachments (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      complaint_id INTEGER NOT NULL,
      kind TEXT NOT NULL,
      original_name TEXT NOT NULL,
      stored_name TEXT NOT NULL,
      mime_type TEXT NOT NULL,
      size INTEGER NOT NULL,
      path TEXT NOT NULL,
      created_at TEXT NOT NULL,
      FOREIGN KEY (complaint_id) REFERENCES complaints(id)
    )
  `);
    run(`
    CREATE TABLE IF NOT EXISTS logs (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      complaint_id INTEGER NOT NULL,
      action TEXT NOT NULL,
      actor TEXT NOT NULL,
      note TEXT NOT NULL,
      created_at TEXT NOT NULL,
      FOREIGN KEY (complaint_id) REFERENCES complaints(id)
    )
  `);
    seed();
    persist();
}
export function run(sql, params = []) {
    if (!database)
        throw new Error("Database is not initialized");
    const stmt = database.prepare(sql);
    try {
        stmt.bind(params);
        while (stmt.step()) {
            // consume
        }
    }
    finally {
        stmt.free();
    }
    persist();
}
export function all(sql, params = []) {
    if (!database)
        throw new Error("Database is not initialized");
    const stmt = database.prepare(sql);
    const rows = [];
    try {
        stmt.bind(params);
        while (stmt.step())
            rows.push(stmt.getAsObject());
    }
    finally {
        stmt.free();
    }
    return rows;
}
export function get(sql, params = []) {
    return all(sql, params)[0] ?? null;
}
export function createCode() {
    const date = new Date().toISOString().slice(0, 10).replaceAll("-", "");
    const count = get("SELECT COUNT(*) as total FROM complaints")?.total ?? 0;
    return `MS${date}${String(count + 1).padStart(4, "0")}`;
}
export function addLog(complaintId, action, actor, note) {
    run("INSERT INTO logs (complaint_id, action, actor, note, created_at) VALUES (?, ?, ?, ?, ?)", [
        complaintId,
        action,
        actor,
        note,
        now()
    ]);
}
export function saveAttachment(complaintId, kind, file) {
    const publicPath = `/uploads/${file.filename}`;
    run("INSERT INTO attachments (complaint_id, kind, original_name, stored_name, mime_type, size, path, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", [complaintId, kind, file.originalname, file.filename, file.mimetype, file.size, publicPath, now()]);
}
export function listAttachments(complaintId) {
    return all("SELECT * FROM attachments WHERE complaint_id = ? ORDER BY id DESC", [complaintId]);
}
export function listLogs(complaintId) {
    return all("SELECT * FROM logs WHERE complaint_id = ? ORDER BY id DESC", [complaintId]);
}
export function maskPhone(phone) {
    if (!phone)
        return "";
    if (phone.length < 7)
        return "***";
    return `${phone.slice(0, 3)}****${phone.slice(-4)}`;
}
function seed() {
    const userCount = get("SELECT COUNT(*) as total FROM users")?.total ?? 0;
    if (userCount === 0) {
        const password = hashPassword("123456");
        run("INSERT INTO users (username, password_hash, role, county, label) VALUES (?, ?, ?, ?, ?)", [
            "city",
            password,
            "city",
            null,
            "市级管理员"
        ]);
        run("INSERT INTO users (username, password_hash, role, county, label) VALUES (?, ?, ?, ?, ?)", [
            "county-east",
            password,
            "county",
            "东城区",
            "东城区办理员"
        ]);
        run("INSERT INTO users (username, password_hash, role, county, label) VALUES (?, ?, ?, ?, ?)", [
            "county-west",
            password,
            "county",
            "西城区",
            "西城区办理员"
        ]);
    }
    const complaintCount = get("SELECT COUNT(*) as total FROM complaints")?.total ?? 0;
    if (complaintCount > 0)
        return;
    const rows = [
        {
            code: "MS202604250001",
            mode: "anonymous",
            county: "东城区",
            category: "城市管理",
            title: "小区门口夜间施工噪音扰民",
            content: "连续三晚夜间施工，希望协调规范施工时间。",
            name: null,
            phone: null,
            status: "待受理",
            assignee: null,
            created_at: now(),
            updated_at: now(),
            supplement_requested: 0,
            evaluation_score: null,
            evaluation_comment: null
        },
        {
            code: "MS202604250002",
            mode: "realname",
            county: "西城区",
            category: "政务服务",
            title: "窗口排队时间较长",
            content: "上午办理业务等待超过两小时，希望增加引导和分流。",
            name: "张先生",
            phone: "13800000000",
            status: "办理中",
            assignee: "政务服务中心",
            created_at: now(),
            updated_at: now(),
            supplement_requested: 0,
            evaluation_score: null,
            evaluation_comment: null
        },
        {
            code: "MS202604250003",
            mode: "anonymous",
            county: "东城区",
            category: "民生保障",
            title: "路灯损坏影响通行",
            content: "主路人行道有三盏路灯不亮，夜间通行不便。",
            name: null,
            phone: null,
            status: "已办结",
            assignee: "住建维护队",
            created_at: now(),
            updated_at: now(),
            supplement_requested: 0,
            evaluation_score: 5,
            evaluation_comment: "处理及时。",
        }
    ];
    for (const item of rows) {
        run(`INSERT INTO complaints
        (code, mode, county, category, title, content, name, phone, status, assignee, created_at, updated_at, supplement_requested, evaluation_score, evaluation_comment)
       VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`, [
            item.code,
            item.mode,
            item.county,
            item.category,
            item.title,
            item.content,
            item.name,
            item.phone,
            item.status,
            item.assignee,
            item.created_at,
            item.updated_at,
            item.supplement_requested,
            item.evaluation_score,
            item.evaluation_comment
        ]);
        const complaint = get("SELECT * FROM complaints WHERE code = ?", [item.code]);
        if (complaint)
            addLog(complaint.id, "系统导入", "system", "初始化演示数据");
    }
}
export function findUser(username) {
    return get("SELECT * FROM users WHERE username = ?", [username]);
}
export function findComplaintByCode(code) {
    return get("SELECT * FROM complaints WHERE code = ?", [code]);
}
export function findComplaintById(id) {
    return get("SELECT * FROM complaints WHERE id = ?", [id]);
}

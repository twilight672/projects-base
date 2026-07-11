import crypto from "node:crypto";
import { findUser, hashPassword } from "./db.js";
const sessions = new Map();
export function login(username, password) {
    const user = findUser(username);
    if (!user || user.password_hash !== hashPassword(password))
        return null;
    const token = crypto.randomBytes(24).toString("hex");
    const session = {
        id: user.id,
        username: user.username,
        role: user.role,
        county: user.county,
        label: user.label,
        token
    };
    sessions.set(token, session);
    return session;
}
export function logout(token) {
    if (token)
        sessions.delete(token);
}
export function currentUser(token) {
    if (!token)
        return null;
    return sessions.get(token) ?? null;
}
export function requireAuth(req, res, next) {
    const token = req.cookies?.session;
    const user = currentUser(token);
    if (!user) {
        res.status(401).json({ message: "请先登录" });
        return;
    }
    req.user = user;
    next();
}

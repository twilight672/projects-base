import crypto from "node:crypto";
import type { NextFunction, Response } from "express";
import { findUser, hashPassword } from "./db.js";
import type { AuthRequest, SessionUser } from "./types.js";

const sessions = new Map<string, SessionUser>();

export function login(username: string, password: string) {
  const user = findUser(username);
  if (!user || user.password_hash !== hashPassword(password)) return null;
  const token = crypto.randomBytes(24).toString("hex");
  const session: SessionUser = {
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

export function logout(token?: string) {
  if (token) sessions.delete(token);
}

export function currentUser(token?: string) {
  if (!token) return null;
  return sessions.get(token) ?? null;
}

export function requireAuth(req: AuthRequest, res: Response, next: NextFunction) {
  const token = req.cookies?.session;
  const user = currentUser(token);
  if (!user) {
    res.status(401).json({ message: "请先登录" });
    return;
  }
  req.user = user;
  next();
}

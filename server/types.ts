import type { Request } from "express";

export type Role = "city" | "county";

export type User = {
  id: number;
  username: string;
  role: Role;
  county: string | null;
  label: string;
};

export type SessionUser = User & {
  token: string;
};

export type ComplaintStatus = "待受理" | "办理中" | "待补充" | "已办结";

export type ComplaintRow = {
  id: number;
  code: string;
  mode: "anonymous" | "realname";
  county: string;
  category: string;
  title: string;
  content: string;
  name: string | null;
  phone: string | null;
  status: ComplaintStatus;
  assignee: string | null;
  created_at: string;
  updated_at: string;
  supplement_requested: number;
  evaluation_score: number | null;
  evaluation_comment: string | null;
};

export type AttachmentRow = {
  id: number;
  complaint_id: number;
  kind: "initial" | "supplement";
  original_name: string;
  stored_name: string;
  mime_type: string;
  size: number;
  path: string;
  created_at: string;
};

export type LogRow = {
  id: number;
  complaint_id: number;
  action: string;
  actor: string;
  note: string;
  created_at: string;
};

export type AuthRequest = Request & {
  user?: SessionUser;
};

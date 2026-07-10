package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.util.Pager;
import com.util.TableDataInfo;

import net.sf.json.JSONObject;

public class BaseServlet extends HttpServlet {
	SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");// 日期格式化
	
	public void out(JSONObject object, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(object);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void returnjson(HttpServletResponse resp) {
		JSONObject object = new JSONObject();
		object.put("code", "0");
		object.put("msg", "操作成功");
		out(object,resp);
	}
	protected TableDataInfo getDataTable(Pager page) {
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(0);
		rspData.setRows(page.getList());
		rspData.setTotal(page.getTotalCount());
		return rspData;
	}
}

package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.io.PrintWriter;
import com.entity.Shangpindalei;
import com.service.shangpindalei.ShangpindaleiService;
import com.service.shangpindalei.impl.ShangpindaleiServiceImpl;
import com.util.Pager;

import net.sf.json.JSONObject;

@WebServlet("/shangpindaleiservlet")
public class ShangpindaleiServlet extends BaseServlet {

	ShangpindaleiService shangpindaleiService=new ShangpindaleiServiceImpl(new Shangpindalei());
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("get"+method);
		if(method!=null){
			if(method.equals("insert")){
				req.getRequestDispatcher("pages/shangpindalei/shangpindaleiinsertUI.jsp").forward(req, resp);
				return;
			}else if(method.equals("update")){
				String id=req.getParameter("id");
				Shangpindalei shangpindalei = shangpindaleiService.queryByKey("id", id).get(0);
				req.setAttribute("shangpindalei", shangpindalei);
				req.getRequestDispatcher("pages/shangpindalei/shangpindaleiupdateUI.jsp").forward(req, resp);
				return;
			}
			
		}
		req.getRequestDispatcher("pages/shangpindalei/shangpindaleilist.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("post"+method);
		if(method.equals("data")){
			Pager page=new Pager();
			Shangpindalei shangpindalei=new Shangpindalei();
			shangpindalei.setLeibieming(req.getParameter("leibieming"));
			shangpindalei.setZhanshitupian(req.getParameter("zhanshitupian"));
			page.setList(shangpindaleiService.query(shangpindalei));
			out(JSONObject.fromObject(getDataTable(page)),resp);
			return;
		}else if(method.equals("insert")){
			Shangpindalei shangpindalei=new Shangpindalei();
			shangpindalei.setLeibieming(req.getParameter("leibieming"));
			shangpindalei.setZhanshitupian(req.getParameter("zhanshitupian"));
			shangpindaleiService.insert(shangpindalei);
			super.returnjson(resp);
			return;
		}else if(method.equals("update")){
			String id=req.getParameter("id");
			Shangpindalei shangpindalei = shangpindaleiService.queryByKey("id", id).get(0);
			shangpindalei.setLeibieming(req.getParameter("leibieming"));
			shangpindalei.setZhanshitupian(req.getParameter("zhanshitupian"));
			shangpindaleiService.update(shangpindalei);
			super.returnjson(resp);
			return;
		}else if(method.equals("delete")){
			String id=req.getParameter("id");
			shangpindaleiService.delBykey("id", id);
			super.returnjson(resp);
			return;
		}
		
	}
	
	public void golist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<Shangpindalei> shangpindaleilist = shangpindaleiService.getAll();
		req.setAttribute("shangpindaleilist", shangpindaleilist);
		req.getRequestDispatcher("shangpindalei/shangpindaleilist.jsp").forward(req, resp);
	}
}

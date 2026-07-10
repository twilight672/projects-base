package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.io.PrintWriter;
import com.entity.Kucun;
import com.service.kucun.KucunService;
import com.service.kucun.impl.KucunServiceImpl;
import com.util.Pager;

import net.sf.json.JSONObject;

@WebServlet("/kucunservlet")
public class KucunServlet extends BaseServlet {

	KucunService kucunService=new KucunServiceImpl(new Kucun());
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("get"+method);
		if(method!=null){
			if(method.equals("insert")){
				req.getRequestDispatcher("pages/kucun/kucuninsertUI.jsp").forward(req, resp);
				return;
			}else if(method.equals("update")){
				String id=req.getParameter("id");
				Kucun kucun = kucunService.queryByKey("id", id).get(0);
				req.setAttribute("kucun", kucun);
				req.getRequestDispatcher("pages/kucun/kucunupdateUI.jsp").forward(req, resp);
				return;
			}
			
		}
		req.getRequestDispatcher("pages/kucun/kucunlist.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("post"+method);
		if(method.equals("data")){
			Pager page=new Pager();
			Kucun kucun=new Kucun();
			kucun.setShangpinming(req.getParameter("shangpinming"));
			kucun.setKucunliang(req.getParameter("kucunliang"));
			page.setList(kucunService.query(kucun));
			out(JSONObject.fromObject(getDataTable(page)),resp);
			return;
		}else if(method.equals("insert")){
			Kucun kucun=new Kucun();
			kucun.setShangpinming(req.getParameter("shangpinming"));
			kucun.setKucunliang(req.getParameter("kucunliang"));
			kucunService.insert(kucun);
			super.returnjson(resp);
			return;
		}else if(method.equals("update")){
			String id=req.getParameter("id");
			Kucun kucun = kucunService.queryByKey("id", id).get(0);
			kucun.setShangpinming(req.getParameter("shangpinming"));
			kucun.setKucunliang(req.getParameter("kucunliang"));
			kucunService.update(kucun);
			super.returnjson(resp);
			return;
		}else if(method.equals("delete")){
			String id=req.getParameter("id");
			kucunService.delBykey("id", id);
			super.returnjson(resp);
			return;
		}
		
	}
	
	public void golist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<Kucun> kucunlist = kucunService.getAll();
		req.setAttribute("kucunlist", kucunlist);
		req.getRequestDispatcher("kucun/kucunlist.jsp").forward(req, resp);
	}
}

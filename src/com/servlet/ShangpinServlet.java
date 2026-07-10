package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.io.PrintWriter;
import com.entity.Shangpin;
import com.entity.Shangpindalei;
import com.service.shangpin.ShangpinService;
import com.service.shangpin.impl.ShangpinServiceImpl;
import com.service.shangpindalei.ShangpindaleiService;
import com.service.shangpindalei.impl.ShangpindaleiServiceImpl;
import com.util.Pager;

import net.sf.json.JSONObject;

@WebServlet("/shangpinservlet")
public class ShangpinServlet extends BaseServlet {

	ShangpinService shangpinService=new ShangpinServiceImpl(new Shangpin());
	ShangpindaleiService shangpindaleiService=new ShangpindaleiServiceImpl(new Shangpindalei());
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("get"+method);
		if(method!=null){
			if(method.equals("insert")){
				req.setAttribute("shangpindaleilist", shangpindaleiService.getAll());
				req.getRequestDispatcher("pages/shangpin/shangpininsertUI.jsp").forward(req, resp);
				return;
			}else if(method.equals("update")){
				String id=req.getParameter("id");
				Shangpin shangpin = shangpinService.queryByKey("id", id).get(0);
				req.setAttribute("shangpin", shangpin);
				req.getRequestDispatcher("pages/shangpin/shangpinupdateUI.jsp").forward(req, resp);
				return;
			}
			
		}
		req.getRequestDispatcher("pages/shangpin/shangpinlist.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("post"+method);
		if(method.equals("data")){
			Pager page=new Pager();
			Shangpin shangpin=new Shangpin();
			shangpin.setShangpinming(req.getParameter("shangpinming"));
			shangpin.setJiage(req.getParameter("jiage"));
			shangpin.setShangpinmiaoshu(req.getParameter("shangpinmiaoshu"));
			shangpin.setShangpindalei(req.getParameter("shangpindalei"));
			shangpin.setTupian(req.getParameter("tupian"));
			page.setList(shangpinService.query(shangpin));
			out(JSONObject.fromObject(getDataTable(page)),resp);
			return;
		}else if(method.equals("insert")){
			Shangpin shangpin=new Shangpin();
			shangpin.setShangpinming(req.getParameter("shangpinming"));
			shangpin.setJiage(req.getParameter("jiage"));
			shangpin.setShangpinmiaoshu(req.getParameter("shangpinmiaoshu"));
			shangpin.setShangpindalei(req.getParameter("shangpindalei"));
			shangpin.setTupian(req.getParameter("tupian"));
			shangpinService.insert(shangpin);
			super.returnjson(resp);
			return;
		}else if(method.equals("update")){
			String id=req.getParameter("id");
			Shangpin shangpin = shangpinService.queryByKey("id", id).get(0);
			shangpin.setShangpinming(req.getParameter("shangpinming"));
			shangpin.setJiage(req.getParameter("jiage"));
			shangpin.setShangpinmiaoshu(req.getParameter("shangpinmiaoshu"));
			shangpin.setShangpindalei(req.getParameter("shangpindalei"));
			shangpin.setTupian(req.getParameter("tupian"));
			shangpinService.update(shangpin);
			super.returnjson(resp);
			return;
		}else if(method.equals("delete")){
			String id=req.getParameter("id");
			shangpinService.delBykey("id", id);
			super.returnjson(resp);
			return;
		}
		
	}
	
	public void golist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<Shangpin> shangpinlist = shangpinService.getAll();
		req.setAttribute("shangpinlist", shangpinlist);
		req.getRequestDispatcher("shangpin/shangpinlist.jsp").forward(req, resp);
	}
}

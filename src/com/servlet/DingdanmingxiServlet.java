package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.io.PrintWriter;
import com.entity.Dingdanmingxi;
import com.service.dingdanmingxi.DingdanmingxiService;
import com.service.dingdanmingxi.impl.DingdanmingxiServiceImpl;
import com.util.Pager;

import net.sf.json.JSONObject;

@WebServlet("/dingdanmingxiservlet")
public class DingdanmingxiServlet extends BaseServlet {

	DingdanmingxiService dingdanmingxiService=new DingdanmingxiServiceImpl(new Dingdanmingxi());
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("get"+method);
		if(method!=null){
			if(method.equals("insert")){
				req.getRequestDispatcher("pages/dingdanmingxi/dingdanmingxiinsertUI.jsp").forward(req, resp);
				return;
			}else if(method.equals("update")){
				String id=req.getParameter("id");
				Dingdanmingxi dingdanmingxi = dingdanmingxiService.queryByKey("id", id).get(0);
				req.setAttribute("dingdanmingxi", dingdanmingxi);
				req.getRequestDispatcher("pages/dingdanmingxi/dingdanmingxiupdateUI.jsp").forward(req, resp);
				return;
			}
			
		}
		req.getRequestDispatcher("pages/dingdanmingxi/dingdanmingxilist.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("post"+method);
		if(method.equals("data")){
			Pager page=new Pager();
			Dingdanmingxi dingdanmingxi=new Dingdanmingxi();
			dingdanmingxi.setDingdanbianhao(req.getParameter("dingdanbianhao"));
			dingdanmingxi.setJiage(req.getParameter("jiage"));
			dingdanmingxi.setShangpin(req.getParameter("shangpin"));
			dingdanmingxi.setShuliang(req.getParameter("shuliang"));
			dingdanmingxi.setZongjia(req.getParameter("zongjia"));
			dingdanmingxi.setShangpinid(req.getParameter("shangpinid"));
			page.setList(dingdanmingxiService.query(dingdanmingxi));
			out(JSONObject.fromObject(getDataTable(page)),resp);
			return;
		}else if(method.equals("insert")){
			Dingdanmingxi dingdanmingxi=new Dingdanmingxi();
			dingdanmingxi.setDingdanbianhao(req.getParameter("dingdanbianhao"));
			dingdanmingxi.setJiage(req.getParameter("jiage"));
			dingdanmingxi.setShangpin(req.getParameter("shangpin"));
			dingdanmingxi.setShuliang(req.getParameter("shuliang"));
			dingdanmingxi.setZongjia(req.getParameter("zongjia"));
			dingdanmingxi.setShangpinid(req.getParameter("shangpinid"));
			dingdanmingxiService.insert(dingdanmingxi);
			super.returnjson(resp);
			return;
		}else if(method.equals("update")){
			String id=req.getParameter("id");
			Dingdanmingxi dingdanmingxi = dingdanmingxiService.queryByKey("id", id).get(0);
			dingdanmingxi.setDingdanbianhao(req.getParameter("dingdanbianhao"));
			dingdanmingxi.setJiage(req.getParameter("jiage"));
			dingdanmingxi.setShangpin(req.getParameter("shangpin"));
			dingdanmingxi.setShuliang(req.getParameter("shuliang"));
			dingdanmingxi.setZongjia(req.getParameter("zongjia"));
			dingdanmingxi.setShangpinid(req.getParameter("shangpinid"));
			dingdanmingxiService.update(dingdanmingxi);
			super.returnjson(resp);
			return;
		}else if(method.equals("delete")){
			String id=req.getParameter("id");
			dingdanmingxiService.delBykey("id", id);
			super.returnjson(resp);
			return;
		}
		
	}
	
	public void golist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<Dingdanmingxi> dingdanmingxilist = dingdanmingxiService.getAll();
		req.setAttribute("dingdanmingxilist", dingdanmingxilist);
		req.getRequestDispatcher("dingdanmingxi/dingdanmingxilist.jsp").forward(req, resp);
	}
}

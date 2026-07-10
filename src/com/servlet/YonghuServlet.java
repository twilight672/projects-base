package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.io.PrintWriter;
import com.entity.Yonghu;
import com.service.yonghu.YonghuService;
import com.service.yonghu.impl.YonghuServiceImpl;
import com.util.Pager;

import net.sf.json.JSONObject;

@WebServlet("/yonghuservlet")
public class YonghuServlet extends BaseServlet {

	YonghuService yonghuService=new YonghuServiceImpl(new Yonghu());
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("get"+method);
		if(method!=null){
			if(method.equals("insert")){
				req.getRequestDispatcher("pages/yonghu/yonghuinsertUI.jsp").forward(req, resp);
				return;
			}else if(method.equals("update")){
				String id=req.getParameter("id");
				Yonghu yonghu = yonghuService.queryByKey("id", id).get(0);
				req.setAttribute("yonghu", yonghu);
				req.getRequestDispatcher("pages/yonghu/yonghuupdateUI.jsp").forward(req, resp);
				return;
			}
			if (method.equals("resetpwd")) {
				req.getRequestDispatcher("pages/resetPwd.jsp").forward(req, resp);
				return;
			}else if (method.equals("logout")) {
				Enumeration em = req.getSession().getAttributeNames();
				while (em.hasMoreElements()) {
					req.getSession().removeAttribute(em.nextElement().toString());
				}
				req.getRequestDispatcher("index.jsp").forward(req, resp);
				return;
			}
			
		}
		req.getRequestDispatcher("pages/yonghu/yonghulist.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("post"+method);
		if(method.equals("data")){
			Pager page=new Pager();
			Yonghu yonghu=new Yonghu();
			yonghu.setYonghuming(req.getParameter("yonghuming"));
			yonghu.setMima(req.getParameter("mima"));
			yonghu.setJiaose(req.getParameter("jiaose"));
			yonghu.setDianhua(req.getParameter("dianhua"));
			yonghu.setDizhi(req.getParameter("dizhi"));
			yonghu.setXingming(req.getParameter("xingming"));
			yonghu.setYoubian(req.getParameter("youbian"));
			yonghu.setYoujian(req.getParameter("youjian"));
			yonghu.setTouxiang(req.getParameter("touxiang"));
			page.setList(yonghuService.query(yonghu));
			out(JSONObject.fromObject(getDataTable(page)),resp);
			return;
		}else if(method.equals("insert")){
			Yonghu yonghu=new Yonghu();
			yonghu.setYonghuming(req.getParameter("yonghuming"));
			yonghu.setMima(req.getParameter("mima"));
			yonghu.setJiaose(req.getParameter("jiaose"));
			yonghu.setDianhua(req.getParameter("dianhua"));
			yonghu.setDizhi(req.getParameter("dizhi"));
			yonghu.setXingming(req.getParameter("xingming"));
			yonghu.setYoubian(req.getParameter("youbian"));
			yonghu.setYoujian(req.getParameter("youjian"));
			yonghu.setTouxiang(req.getParameter("touxiang"));
			yonghuService.insert(yonghu);
			super.returnjson(resp);
			return;
		}else if(method.equals("update")){
			String id=req.getParameter("id");
			Yonghu yonghu = yonghuService.queryByKey("id", id).get(0);
			yonghu.setYonghuming(req.getParameter("yonghuming"));
			yonghu.setMima(req.getParameter("mima"));
			yonghu.setJiaose(req.getParameter("jiaose"));
			yonghu.setDianhua(req.getParameter("dianhua"));
			yonghu.setDizhi(req.getParameter("dizhi"));
			yonghu.setXingming(req.getParameter("xingming"));
			yonghu.setYoubian(req.getParameter("youbian"));
			yonghu.setYoujian(req.getParameter("youjian"));
			yonghu.setTouxiang(req.getParameter("touxiang"));
			yonghuService.update(yonghu);
			super.returnjson(resp);
			return;
		}else if(method.equals("delete")){
			String id=req.getParameter("id");
			yonghuService.delBykey("id", id);
			super.returnjson(resp);
			return;
		}
		if (method.equals("login")) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			List<Yonghu> yonghulist = yonghuService.queryByKey("yonghuming", username);


			if (yonghulist.size() > 0) {
				if (yonghulist.get(0).getMima().equals(password)) {
					req.getSession().setAttribute("userid", yonghulist.get(0).getId());
					req.getSession().setAttribute("name", yonghulist.get(0).getYonghuming());
					req.getSession().setAttribute("role", yonghulist.get(0).getJiaose());
					req.getRequestDispatcher("shopservlet?method=index").forward(req, resp);
					return;
				}
			}
















					req.getRequestDispatcher("index.jsp").forward(req, resp);
		} else if (method.equals("checkPassword")) {
			String password = req.getParameter("password");
			String id = req.getSession().getAttribute("userid").toString();
			List<Yonghu> yonghulist = yonghuService.queryByKey("id", id);
			if (yonghulist.size() > 0) {
				if (yonghulist.get(0).getMima().equals(password)) {
					PrintWriter out = resp.getWriter();
					out.print(true);
					out.close();
					return;
				}
			}
			PrintWriter out = resp.getWriter();
			out.print(false);
			out.close();
			return;
		} else if (method.equals("resetpwd")) {
			String newPassword = req.getParameter("newPassword");
			String id = req.getSession().getAttribute("userid").toString();
			Yonghu yonghu = yonghuService.queryByKey("id", id).get(0);
			yonghu.setMima(newPassword);
			yonghuService.update(yonghu);
			super.returnjson(resp);
			return;
		}
		
	}
	
	public void golist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<Yonghu> yonghulist = yonghuService.getAll();
		req.setAttribute("yonghulist", yonghulist);
		req.getRequestDispatcher("yonghu/yonghulist.jsp").forward(req, resp);
	}
}

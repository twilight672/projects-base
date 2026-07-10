package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.io.PrintWriter;
import com.entity.Dingdan;
import com.service.dingdan.DingdanService;
import com.service.dingdan.impl.DingdanServiceImpl;
import com.util.Pager;

import net.sf.json.JSONObject;

@WebServlet("/dingdanservlet")
public class DingdanServlet extends BaseServlet {

	DingdanService dingdanService=new DingdanServiceImpl(new Dingdan());
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("get"+method);
		if(method!=null){
			if(method.equals("insert")){
				req.getRequestDispatcher("pages/dingdan/dingdaninsertUI.jsp").forward(req, resp);
				return;
			}else if(method.equals("update")){
				String id=req.getParameter("id");
				Dingdan dingdan = dingdanService.queryByKey("id", id).get(0);
				req.setAttribute("dingdan", dingdan);
				req.getRequestDispatcher("pages/dingdan/dingdanupdateUI.jsp").forward(req, resp);
				return;
			}
			
		}
		req.getRequestDispatcher("pages/dingdan/dingdanlist.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("post"+method);
		if(method.equals("data")){
			Pager page=new Pager();
			Dingdan dingdan=new Dingdan();
			dingdan.setDingdanriqi(req.getParameter("dingdanriqi"));
			dingdan.setDingdanzhuangtai(req.getParameter("dingdanzhuangtai"));
			dingdan.setHeji(req.getParameter("heji"));
			dingdan.setZhifuzhuangtai(req.getParameter("zhifuzhuangtai"));
			dingdan.setDingdanbianhao(req.getParameter("dingdanbianhao"));
			dingdan.setMaijia(req.getParameter("maijia"));
			page.setList(dingdanService.query(dingdan));
			out(JSONObject.fromObject(getDataTable(page)),resp);
			return;
		}else if(method.equals("insert")){
			Dingdan dingdan=new Dingdan();
			dingdan.setDingdanriqi(req.getParameter("dingdanriqi"));
			dingdan.setDingdanzhuangtai(req.getParameter("dingdanzhuangtai"));
			dingdan.setHeji(req.getParameter("heji"));
			dingdan.setZhifuzhuangtai(req.getParameter("zhifuzhuangtai"));
			dingdan.setDingdanbianhao(req.getParameter("dingdanbianhao"));
			dingdan.setMaijia(req.getParameter("maijia"));
			dingdanService.insert(dingdan);
			super.returnjson(resp);
			return;
		}else if(method.equals("update")){
			String id=req.getParameter("id");
			Dingdan dingdan = dingdanService.queryByKey("id", id).get(0);
			dingdan.setDingdanriqi(req.getParameter("dingdanriqi"));
			dingdan.setDingdanzhuangtai(req.getParameter("dingdanzhuangtai"));
			dingdan.setHeji(req.getParameter("heji"));
			dingdan.setZhifuzhuangtai(req.getParameter("zhifuzhuangtai"));
			dingdan.setDingdanbianhao(req.getParameter("dingdanbianhao"));
			dingdan.setMaijia(req.getParameter("maijia"));
			dingdanService.update(dingdan);
			super.returnjson(resp);
			return;
		}else if(method.equals("delete")){
			String id=req.getParameter("id");
			dingdanService.delBykey("id", id);
			super.returnjson(resp);
			return;
		}
		
	}
	
	public void golist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<Dingdan> dingdanlist = dingdanService.getAll();
		req.setAttribute("dingdanlist", dingdanlist);
		req.getRequestDispatcher("dingdan/dingdanlist.jsp").forward(req, resp);
	}
}

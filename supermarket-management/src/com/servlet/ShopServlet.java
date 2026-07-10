package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.Cart;
import com.entity.Dingdan;
import com.entity.Dingdanmingxi;
import com.entity.Shangpin;
import com.entity.Shangpindalei;
import com.entity.Yonghu;
import com.service.dingdan.DingdanService;
import com.service.dingdan.impl.DingdanServiceImpl;
import com.service.dingdanmingxi.DingdanmingxiService;
import com.service.dingdanmingxi.impl.DingdanmingxiServiceImpl;
import com.service.shangpin.ShangpinService;
import com.service.shangpin.impl.ShangpinServiceImpl;
import com.service.shangpindalei.ShangpindaleiService;
import com.service.shangpindalei.impl.ShangpindaleiServiceImpl;
import com.service.yonghu.YonghuService;
import com.service.yonghu.impl.YonghuServiceImpl;

@WebServlet("/shopservlet")
public class ShopServlet extends BaseServlet{
	ShangpinService shangpinService=new ShangpinServiceImpl(new Shangpin());
	ShangpindaleiService shangpindaleiService=new ShangpindaleiServiceImpl(new Shangpindalei());

	DingdanService dingdanService=new DingdanServiceImpl(new Dingdan());
	YonghuService yonghuService=new YonghuServiceImpl(new Yonghu());
	DingdanmingxiService dingdanmingxiService=new DingdanmingxiServiceImpl(new Dingdanmingxi());
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("get"+method);
		if(method!=null){
			if(method.equals("index")){
				initindex(req);
				req.getRequestDispatcher("shopindex.jsp").forward(req, resp);
				return;
			}else if(method.equals("shangpinlist")){
				String dalei = req.getParameter("shangpindalei");
				Shangpin sp=new Shangpin();
				sp.setShangpindalei(dalei);
				req.setAttribute("shangpinlist", shangpinService.query(sp));
				req.setAttribute("dalei", dalei);
				initindex(req);
				req.getRequestDispatcher("shangpinlist.jsp").forward(req, resp);
				return;
			}else if(method.equals("shangpin")){
				String shangpinid=req.getParameter("shangpinid");
				req.setAttribute("shangpin", shangpinService.queryByKey("id", shangpinid).get(0));
				initindex(req);
				req.getRequestDispatcher("shangpin.jsp").forward(req, resp);
				return;
			}else if(method.equals("shoppingcart")){
				Map<String,Cart> cartmap = new HashMap<>();
				if(req.getSession().getAttribute("mycart")!=null){
					cartmap = (HashMap<String,Cart>) req.getSession().getAttribute("mycart");
				}
				double total=0.0;
				for(String cid:cartmap.keySet()){
					total+=Double.valueOf(cartmap.get(cid).getSp().getJiage())*cartmap.get(cid).getSum();
				}
				req.getSession().setAttribute("total", total);
				initindex(req);
				req.getRequestDispatcher("shoppingcart.jsp").forward(req, resp);
				return;
			}else if(method.equals("clearcart")){
				req.getSession().removeAttribute("mycart");
				req.getSession().setAttribute("total", 0);
				initindex(req);
				req.getRequestDispatcher("shoppingcart.jsp").forward(req, resp);
				return;
			}else if(method.equals("removecart")){
				String shangpinid=req.getParameter("shangpinid");
				Map<String,Cart> cartmap =  new HashMap<>();
				if(req.getSession().getAttribute("mycart")!=null){
					cartmap = (HashMap<String,Cart>) req.getSession().getAttribute("mycart");
					if(cartmap.get(shangpinid)!=null){
						cartmap.remove(shangpinid);
					}
				}
				double total=0.0;
				for(String cid:cartmap.keySet()){
					total+=Double.valueOf(cartmap.get(cid).getSp().getJiage())*cartmap.get(cid).getSum();
				}
				req.getSession().setAttribute("total", total);
				initindex(req);
				req.getRequestDispatcher("shoppingcart.jsp").forward(req, resp);
				return;
			}else if(method.equals("checkout")){
				initindex(req);
				
				if(req.getSession().getAttribute("name")==null){
					req.setAttribute("msg", "请先登录");
					req.getRequestDispatcher("login.jsp").forward(req, resp);
					return;
				}
				
				Map<String,Cart> cartmap=new HashMap<>();
				if(req.getSession().getAttribute("mycart")!=null){
					cartmap = (HashMap<String,Cart>) req.getSession().getAttribute("mycart");
					double total=0.0;
					for(String cid:cartmap.keySet()){
						total+=Double.valueOf(cartmap.get(cid).getSp().getJiage())*cartmap.get(cid).getSum();
					}
					String uuid=UUID.randomUUID().toString().replace("-", "");
					Dingdan dingdan=new Dingdan();
					dingdan.setDingdanbianhao(uuid);
					dingdan.setMaijia((String) req.getSession().getAttribute("name"));
					dingdan.setDingdanriqi(ss.format(new Date()));
					dingdan.setDingdanzhuangtai("已提交");
					dingdan.setZhifuzhuangtai("已支付");
					dingdan.setHeji("总金额:"+total+" 共"+cartmap.keySet().size()+"件商品");
					dingdanService.insert(dingdan);
					
					dingdan=dingdanService.query(dingdan).get(0);
					for(String cid:cartmap.keySet()){
						Dingdanmingxi ddmx=new Dingdanmingxi();
						ddmx.setDingdanbianhao(uuid);
						ddmx.setShangpinid(cid);
						ddmx.setJiage(cartmap.get(cid).getSp().getJiage());
						ddmx.setShangpin(cartmap.get(cid).getSp().getShangpinming());
						ddmx.setShuliang(cartmap.get(cid).getSum().toString());
						ddmx.setZongjia(String.valueOf(Double.valueOf(ddmx.getJiage())*Integer.valueOf(ddmx.getShuliang())));
						dingdanmingxiService.insert(ddmx);
					}
					req.getSession().removeAttribute("mycart");
					req.getSession().setAttribute("total", 0);
				}
				
				Yonghu yh=new Yonghu();
				yh.setYonghuming((String) req.getSession().getAttribute("name"));
				Dingdan dingdan=new Dingdan();
				dingdan.setMaijia(yh.getYonghuming());
				req.setAttribute("orderlist", dingdanService.query(dingdan));
				req.setAttribute("account", yonghuService.query(yh).get(0));
				req.getRequestDispatcher("account.jsp").forward(req, resp);
				return;
			}else if(method.equals("orderdetail")){
				initindex(req);
				String dingdanbianhao=req.getParameter("dingdanbianhao");
				Dingdan order=new Dingdan();
				order.setDingdanbianhao(dingdanbianhao);
				
				Dingdanmingxi dd=new Dingdanmingxi();
				dd.setDingdanbianhao(dingdanbianhao);
				req.setAttribute("orderlist", dingdanmingxiService.query(dd));
				req.setAttribute("order", dingdanService.query(order).get(0));
				req.getRequestDispatcher("orderdetail.jsp").forward(req, resp);
				return;
			}else if(method.equals("frontcreate")){
				initindex(req);
				req.getRequestDispatcher("createaccount.jsp").forward(req, resp);
				return;
			}else if(method.equals("frontlogin")){
				initindex(req);
				req.getRequestDispatcher("login.jsp").forward(req, resp);
				return;
			}else if(method.equals("account")){
				initindex(req);
				Yonghu yh=new Yonghu();
				yh.setYonghuming((String) req.getSession().getAttribute("name"));
				Dingdan dingdan=new Dingdan();
				dingdan.setMaijia(yh.getYonghuming());
				req.setAttribute("orderlist", dingdanService.query(dingdan));
				req.setAttribute("account", yonghuService.query(yh).get(0));
				req.getRequestDispatcher("account.jsp").forward(req, resp);
				return;
			}else if(method.equals("logout")){
				initindex(req);
				Enumeration em = req.getSession().getAttributeNames();
				  while(em.hasMoreElements()){
				    req.getSession().removeAttribute(em.nextElement().toString());
				  }
				  req.getSession().setAttribute("shopname", "小型购物超市");
				req.getRequestDispatcher("shopindex.jsp").forward(req, resp);
				return;
			}
			
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		req.setCharacterEncoding("utf-8");
		String method=req.getParameter("method");
		System.out.println("post"+method);
		if(method!=null){
			 if(method.equals("addcart")){
				String shangpinid=req.getParameter("shangpinid");
				Shangpin sp=shangpinService.queryByKey("id", shangpinid).get(0);
				String sum=req.getParameter("sum");
				if(sum==null||sum.equals("")){
					sum="1";
				}
				Map<String,Cart> cartmap;
				if(req.getSession().getAttribute("mycart")!=null){
					cartmap = (HashMap<String,Cart>) req.getSession().getAttribute("mycart");
					if(cartmap.get(shangpinid)!=null){
						cartmap.get(sp.getId().toString()).setSum(cartmap.get(sp.getId().toString()).getSum()+1);
					}else{
						Cart cart=new Cart();
						cart.setSp(sp);
						cart.setSpid(sp.getId());
						cart.setSum(1);
						cartmap.put(sp.getId().toString(), cart);
					}
				}else{
					cartmap=new HashMap<>();
					Cart cart=new Cart();
					cart.setSp(sp);
					cart.setSpid(sp.getId());
					cart.setSum(1);
					cartmap.put(sp.getId().toString(), cart);
				}
				double total=0.0;
				for(String cid:cartmap.keySet()){
					total+=Double.valueOf(cartmap.get(cid).getSp().getJiage())*cartmap.get(cid).getSum();
				}
				req.getSession().setAttribute("mycart", cartmap);
				
				
				resp.setCharacterEncoding("utf-8");
				
				StringBuffer content = new StringBuffer();

				append(content, "<div class=\"tt-modal-addtocart desctope\">",1);
				append(content, "					<div class=\"row\">",1);
				append(content, "						<div class=\"col-12 col-lg-6\">",1);
				append(content, "							<div class=\"tt-modal-messages\">",1);
				append(content, "								<i class=\"icon-f-68\"></i> 加入购物车成功!",1);
				append(content, "							</div>",1);
				append(content, "							<div class=\"tt-modal-product\">",1);
				append(content, "								<div class=\"tt-img\">",1);
				append(content, "									<img src=\"upload/"+sp.getTupian()+"\" data-src=\"upload/"+sp.getTupian()+"\" alt=\"\">",1);
				append(content, "								</div>",1);
				append(content, "								<h2 class=\"tt-title\"><a href=\"shopservlet?method=shangpin&shangpinid="+sp.getId()+"\">"+sp.getShangpinming()+"</a></h2>",1);
				append(content, "								<div class=\"tt-qty\">",1);
				append(content, "									数量: <span>"+sum+"</span>",1);
				append(content, "								</div>",1);
				append(content, "							</div>",1);
				append(content, "							<div class=\"tt-product-total\">",1);
				append(content, "								<div class=\"tt-total\">",1);
				append(content, "									总价: <span class=\"tt-price\">￥"+sp.getJiage()+"</span>",1);
				append(content, "								</div>",1);
				append(content, "							</div>",1);
				append(content, "						</div>",1);
				append(content, "						<div class=\"col-12 col-lg-6\">",1);
				append(content, "							<a href=\"#\" class=\"tt-cart-total\">",1);
				append(content, "								有 "+cartmap.keySet().size()+" 件商品在购物车里",1);
				append(content, "								<div class=\"tt-total\">",1);
				append(content, "									总价: <span class=\"tt-price\">￥"+total+"</span>",1);
				append(content, "								</div>",1);
				append(content, "							</a>",1);
				append(content, "							<a href=\"shopservlet?method=index\" class=\"btn btn-border btn-close-popup\">继续购物</a>",1);
				append(content, "							<a href=\"shopservlet?method=shoppingcart\" class=\"btn btn-border\">浏览购物车</a>",1);
				append(content, "						</div>",1);
				append(content, "					</div>",1);
				append(content, "				</div>",1);

				try {
					PrintWriter out = resp.getWriter();
					out.print(content.toString());
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(method.equals("create")){
				initindex(req);
				String username=req.getParameter("username");
				String password=req.getParameter("password");
				String email=req.getParameter("email");
				Yonghu yh=new Yonghu();
				yh.setYonghuming(username);
				yh.setMima(password);
				yh.setYoujian(email);
				yh.setJiaose("普通用户");
				yonghuService.insert(yh);
				req.setAttribute("msg", "账号创建成功请登录");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
				return;
			}else if(method.equals("login")){
				initindex(req);
				String username=req.getParameter("username");
				String password=req.getParameter("password");
				try {
					boolean result=false;
					List<Yonghu> yonghulist = yonghuService.queryByKey("yonghuming", username);

					if (yonghulist.size() > 0) {
						if (yonghulist.get(0).getMima().equals(password)) {
							req.getSession().setAttribute("userid", yonghulist.get(0).getId());
							req.getSession().setAttribute("name", yonghulist.get(0).getYonghuming());
							req.getSession().setAttribute("role", yonghulist.get(0).getJiaose());
							result=true;
						}
					}
					if(result){
						req.getRequestDispatcher("shopindex.jsp").forward(req, resp);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				req.setAttribute("msg", "用户名或密码错误");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
				return;
			}
		}
	}






//	
//	public void yonghudata() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		Yonghu yh=new Yonghu();
//		yh.setYonghuming((String) request.getSession().getAttribute("name"));
//		Pager page = new Pager();
//		List<Yonghu> ylist=yonghuService.findByObjectEq(yh);
//		page.setList(ylist);
//		out(JSONObject.fromObject(getDataTable(page)));
//	}
//	
//	
//	public void dingdandata() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		Dingdan dingdan=new Dingdan();
//		dingdan.setMaijia((String) request.getSession().getAttribute("name"));
//		Pager page = dingdanService.loadPage(dingdan, startPager());
//		System.out.println(JSONObject.fromObject(getDataTable(page)));
//		out(JSONObject.fromObject(getDataTable(page)));
//	}


//	public String updatecart() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String shangpinid=request.getParameter("shangpinid");
//		Shangpin sp=shangpinService.find(Integer.valueOf(shangpinid));
//		String sum=request.getParameter("sum");
//		Map<String,Cart> cartmap =  new HashMap<>();
//		if(request.getSession().getAttribute("mycart")!=null){
//			cartmap = (HashMap<String,Cart>) request.getSession().getAttribute("mycart");
//			if(cartmap.get(shangpinid)!=null){
//				cartmap.get(shangpinid).setSum(Integer.valueOf(sum));
//			}else{
//				Cart cart=new Cart();
//				cart.setSp(sp);
//				cart.setSpid(sp.getId());
//				cart.setSum(Integer.valueOf(sum));
//				cartmap.put(shangpinid, cart);
//			}
//		}else{
//			cartmap=new HashMap<>();
//			Cart cart=new Cart();
//			cart.setSp(sp);
//			cart.setSpid(sp.getId());
//			cart.setSum(Integer.valueOf(sum));
//			cartmap.put(shangpinid, cart);
//		}
//		request.getSession().setAttribute("mycart", cartmap);
//		double total=0.0;
//		for(String cid:cartmap.keySet()){
//			total+=Double.valueOf(cartmap.get(cid).getSp().getJiage())*cartmap.get(cid).getSum();
//		}
//		request.getSession().setAttribute("total", total);
//		initindex(request);
//		return "shoppingcart";
//	}



	public void append(StringBuffer content,String str,int tr){
		content.append(str);
	}


	//基础数据
	public void initindex(HttpServletRequest request){
		request.setAttribute("shangpindalei", shangpindaleiService.getAll());
		request.getSession().setAttribute("shopname", "小型超市");
	}
	
}

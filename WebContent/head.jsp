<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<link rel="stylesheet" href="css/fonts.css">
<link rel="stylesheet" href="css/theme.css">

<head>
	<meta charset="utf-8">
	<title>${shopname}</title>
</head>
<body>
<header>
	<div class="tt-desktop-header">
		<div class="container">
			<div class="tt-header-holder">
				<div class="tt-obj-logo">
					<!-- logo -->
					<a class="tt-logo tt-logo-alignment" href="shopservlet?method=index"><img src="upload/logo.png" alt=""></a>
					<!-- /logo -->
				</div>
				<div class="tt-obj-options obj-move-right tt-position-absolute">
					<!-- tt-cart -->
					<div class="tt-desctop-parent-cart tt-parent-box">
						<div class="tt-cart tt-dropdown-obj" data-tooltip="Cart" data-tposition="bottom">
							<button class="tt-dropdown-toggle">
								<i class="icon-f-39"></i>
								<span class="tt-badge-cart"><c:out value="${fn:length(mycart)}"></c:out></span>
							</button>
							<div class="tt-dropdown-menu">
								<div class="tt-mobile-add">
									<h6 class="tt-title">SHOPPING CART</h6>
									<button class="tt-close">Close</button>
								</div>
								<div class="tt-dropdown-inner">
									<div class="tt-cart-layout">
										<!-- layout emty cart -->
										<!-- <a href="empty-cart.html" class="tt-cart-empty">
											<i class="icon-f-39"></i>
											<p>No Products in the Cart</p>
										</a> -->
										<div class="tt-cart-content">
											<div class="tt-cart-list">
												<c:forEach items="${mycart }" var="cart">
												<div class="tt-item">
													<a href="shopservlet?method=shangpin&shangpinid=${cart.value.sp.id }">
														<div class="tt-item-img">
															<img src="upload/${cart.value.sp.tupian }" data-src="upload/${cart.value.sp.tupian }" alt="">
														</div>
														<div class="tt-item-descriptions">
															<h2 class="tt-title">${cart.value.sp.shangpinming }</h2>
															<div class="tt-quantity">${cart.value.sum }</div> <div class="tt-price">￥${cart.value.sp.jiage }</div>
														</div>
													</a>
													<div class="tt-item-close">
														<a href="shopservlet?method=removecart&shangpinid=${cart.value.sp.id }" class="tt-btn-close"></a>
													</div>
												</div>
												</c:forEach>
												
											</div>
											<div class="tt-cart-total-row">
												<div class="tt-cart-total-title">合计:</div>
												<div class="tt-cart-total-price">￥${total }</div>
											</div>
											<div class="tt-cart-btn">
												<div class="tt-item">
													<a href="shopservlet?method=checkout" class="btn">结算提交订单</a>
												</div>
												<div class="tt-item">
													<a href="shopservlet?method=shoppingcart" class="btn-link-02 tt-hidden-mobile">浏览购物车</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- /tt-cart -->
					<!-- tt-account -->
					<div class="tt-desctop-parent-account tt-parent-box">
						<div class="tt-account tt-dropdown-obj">
							<button class="tt-dropdown-toggle"  data-tooltip="My Account" data-tposition="bottom"><i class="icon-f-94"></i></button>
							<div class="tt-dropdown-menu">
								<div class="tt-mobile-add">
									<button class="tt-close">Close</button>
								</div>
								<div class="tt-dropdown-inner">
									<ul>
									    <c:if test="${name==null }">
									    <li><a href="shopservlet?method=frontlogin"><i class="icon-f-76"></i>登录</a></li>
									    </c:if>
									    <c:if test="${name!=null }">
									     <li><a href="shopservlet?method=account"><i class="icon-f-94"></i>我的账户</a></li>
									   
									    <li><a href="shopservlet?method=logout"><i class="icon-f-77"></i>退出</a></li>
									    </c:if>
									    
									    <li><a href="shopservlet?method=frontcreate"><i class="icon-f-94"></i>注册</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<!-- /tt-account -->
				</div>
			</div>
		</div>
		<div class="tt-color-scheme-01">
			<div class="container">
				<div class="tt-header-holder">
					<div class="tt-obj-menu">
						<!-- tt-menu -->
						<div class="tt-desctop-parent-menu tt-parent-box">
							<div class="tt-desctop-menu">
							<nav>
								<ul>
									<li class="dropdown tt-megamenu-col-02 selected">
										<a href="shopservlet?method=index">首页</a>
									</li>
									<c:forEach items="${shangpindalei }" var="dalei">
									<li class="dropdown megamenu">
										<a href="shopservlet?method=shangpinlist&shangpindalei=${dalei.leibieming}">${dalei.leibieming }</a>
									</li>	
									</c:forEach>
									
								</ul>
							</nav>
							</div>
						</div>
						<!-- /tt-menu -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /tt-desktop-header -->
	<!-- stuck nav -->
	<div class="tt-stuck-nav">
		<div class="container">
			<div class="tt-header-row ">
				<div class="tt-stuck-parent-menu"></div>
				<div class="tt-stuck-parent-search tt-parent-box"></div>
				<div class="tt-stuck-parent-cart tt-parent-box"></div>
				<div class="tt-stuck-parent-account tt-parent-box"></div>
				<div class="tt-stuck-parent-multi tt-parent-box"></div>
			</div>
		</div>
	</div>
</header>




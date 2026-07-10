<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="head.jsp"%>


<div id="tt-pageContent">
	<div class="container-indent">
		<div class="container container-fluid-custom-mobile-padding">
			<h1 class="tt-title-subpages noborder">订单详情</h1>
			<div class="tt-shopping-layout">
				<h2 class="tt-title">订单 ${order.dingdanbianhao }</h2>
				<a href="shopservlet?method=account" class="tt-link-back">
					<i class="icon-e-19"></i> 返回订单列表
				</a>
				<div class="tt-data">${order.dingdanriqi }</div>
				<div class="tt-wrapper">
					<div class="tt-table-responsive">
						<table class="tt-table-shop-03">
							<thead>
								<tr>
									<th>商品</th>
									<th>价格</th>
									<th>数量</th>
									<th>总价</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${orderlist }" var="dingdan">
								<tr>
									<td>${dingdan.shangpin } </td>
									<td>${dingdan.jiage }</td>
									<td>${dingdan.shuliang }</td>
									<td>${dingdan.zongjia }</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<%@include file="footer.jsp"%>
</body>
</html>
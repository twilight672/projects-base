<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="head.jsp"%>


<div id="tt-pageContent">
	<div class="container-indent">
		<div class="container container-fluid-custom-mobile-padding">
			<h1 class="tt-title-subpages noborder">账户</h1>
			<div class="tt-shopping-layout">
				<h2 class="tt-title-border">我的账户</h2>
				<div class="tt-wrapper">
					<h3 class="tt-title">历史订单</h3>
					<div class="tt-table-responsive">
						<table class="tt-table-shop-01">
							<thead>
								<tr>
									<th>订单编号</th>
									<th>日期</th>
									<th>支付状态</th>
									<th>订单状态</th>
									<th>合计</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${orderlist }" var="order">
								<tr>
									<td><a href="shopservlet?method=orderdetail&dingdanbianhao=${order.dingdanbianhao }">${order.dingdanbianhao }</a></td>
									<td>${order.dingdanriqi }</td>
									<td>${order.zhifuzhuangtai }</td>
									<td>${order.dingdanzhuangtai }</td>
									<td>${order.heji }</td>
								</tr>
							</c:forEach>

							</tbody>
						</table>
					</div>
				</div>
				<div class="tt-wrapper">
					<h3 class="tt-title">账户信息</h3>
					<div class="tt-table-responsive">
						<table class="tt-table-shop-02">
							<tbody>
								<tr>
									<td>姓名:</td>
									<td>${account.xingming } </td>
								</tr>
								<tr>
									<td>E-MAIL:</td>
									<td>${account.youjian }</td>
								</tr>
								<tr>
									<td>地址:</td>
									<td>${account.dizhi }</td>
								</tr>
								<tr>
									<td>邮编:</td>
									<td>${account.youbian }</td>
								</tr>
								<tr>
									<td>电话:</td>
									<td>${account.dianhua }</td>
								</tr>
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
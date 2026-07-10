<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="head.jsp"%>


<div id="tt-pageContent">
	<div class="container-indent">
		<div class="container">
			<h1 class="tt-title-subpages noborder">购物车</h1>
			<div class="tt-shopcart-table-02">
				<table>
					<tbody>
						<c:forEach items="${sessionScope.mycart}" var="cart">
						<tr>
							<td>
								<div class="tt-product-img">
									<img src="upload/${cart.value.sp.tupian }" data-src="upload/${cart.value.sp.tupian }" alt="">
								</div>
							</td>
							<td>
								<h2 class="tt-title">
									<a href="shopservlet?method=shangpin&shangpinid=${cart.value.sp.id }">${cart.value.sp.shangpinming }</a>
								</h2>
							</td>
							<td>
								<div class="tt-price">
									￥${cart.value.sp.jiage}
								</div>
							</td>
							<td>
								<div class="detach-quantity-desctope">
									<div class="tt-input-counter style-01">
										<span class="minus-btn"></span>
										<span class="shangpinid" style="display: none;">${cart.value.sp.id}</span>
										<input type="text" value="${cart.value.sum }" size="100">
										<span class="plus-btn"></span>
									</div>
								</div>
							</td>
							<td>
								<div class="tt-price subtotal">
									￥${cart.value.sp.jiage*cart.value.sum}
								</div>
							</td>
							<td>
								<a href="shopservlet?method=removecart&shangpinid=${cart.value.sp.id }" class="tt-btn-close"></a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="tt-shopcart-btn">
					<div class="col-left">
						<a class="btn-link" href="shopservlet?method=index"><i class="icon-e-19"></i>继续购物</a>
					</div>
					<div class="col-right">
						<a class="btn-link" href="shopservlet?method=clearcart"><i class="icon-h-02"></i>清空购物车</a>
					</div>
				</div>
			</div>
			<div class="tt-shopcart-col">
				<div class="row">
					<div class="col-md-6 col-lg-4">
						
					</div>
					<div class="col-md-6 col-lg-4">
						
					</div>
					<div class="col-md-6 col-lg-4">
						<div class="tt-shopcart-box tt-boredr-large">
							<table class="tt-shopcart-table01">
								<tfoot>
									<tr>
										<th>总计</th>
										<td>￥${total }</td>
									</tr>
								</tfoot>
							</table>
							<a href="shopservlet?method=checkout" class="btn btn-lg"><span class="icon icon-check_circle"></span>结算提交订单</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<%@include file="footer.jsp"%>
</body>
</html>
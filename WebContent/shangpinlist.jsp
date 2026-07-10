<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="head.jsp"%>


<div id="tt-pageContent">
	<div class="container-indent">
		<div class="container">
			<div class="row">
				<div class="col-md-12 col-lg-9 col-xl-9">
					<div class="content-indent container-fluid-custom-mobile-padding-02">
						<div class="tt-filters-options">
							<h1 class="tt-title">
								${dalei } <span class="tt-title-total">(${shangpinlist.size() })</span>
							</h1>
							<div class="tt-btn-toggle">
								<a href="#">FILTER</a>
							</div>
							<div class="tt-quantity">
								<a href="#" class="tt-col-one" data-value="tt-col-one"></a>
								<a href="#" class="tt-col-two" data-value="tt-col-two"></a>
								<a href="#" class="tt-col-three" data-value="tt-col-three"></a>
								<a href="#" class="tt-col-four" data-value="tt-col-four"></a>
								<a href="#" class="tt-col-six" data-value="tt-col-six"></a>
							</div>
						</div>
						<div class="tt-product-listing row">
							<c:forEach items="${shangpinlist }" var="shangpin">
							<div class="col-6 col-md-4 tt-col-item">
								<div class="tt-product thumbprod-center">
									<div class="tt-image-box">
										<a href="shopservlet?method=shangpin&shangpinid=${shangpin.id }">
											<span class="tt-img"><img src="upload/${shangpin.tupian }" data-src="upload/${shangpin.tupian }" alt=""></span>
											<span class="tt-img-roll-over"><img src="upload/${shangpin.tupian}" data-src="upload/${shangpin.tupian }" alt=""></span>
										</a>
									</div>
									<div class="tt-description">
										<h2 class="tt-title"><a href="shopservlet?method=shangpin&shangpinid=${shangpin.id }">${shangpin.shangpinming }</a></h2>
										<div class="tt-price">
											￥${shangpin.jiage }
										</div>
										<div class="tt-product-inside-hover">
								<div class="tt-row-btn">
									<a href="#" class="tt-btn-addtocart thumbprod-button-bg" onclick="addcart(${shangpin.id });"  data-toggle="modal">加入购物车</a>
								</div>
							</div>
									</div>
								</div>
							</div>
							</c:forEach>
							
							
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
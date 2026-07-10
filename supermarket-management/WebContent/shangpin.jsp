<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="head.jsp"%>


<div id="tt-pageContent">
	<div class="container-indent">
		
		<div class="container container-fluid-mobile">
			<div class="row">
				<div class="col-6 hidden-xs">
					<div class="tt-product-vertical-layout">
						<div class="tt-product-single-img">
							<div>
								<button class="tt-btn-zomm tt-top-right"><i class="icon-f-86"></i></button>
								<img class="zoom-product" src='upload/${shangpin.tupian }' data-zoom-image="upload/${shangpin.tupian }" alt="">
							</div>
						</div>
						<div class="tt-product-single-carousel-vertical">
							<ul id="smallGallery" class="tt-slick-button-vertical  slick-animated-show-js">
								<li><a class="zoomGalleryActive" href="#" data-image="upload/${shangpin.tupian }" data-zoom-image="upload/${shangpin.tupian }"><img src="upload/${shangpin.tupian }" alt=""></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-6">
					<div class="tt-product-single-info">
						<h1 class="tt-title">${shangpin.shangpinming }</h1>
						<div class="tt-price">
							<span class="new-price">￥${shangpin.jiage }</span>
						</div>
						<div class="tt-wrapper">
							${shangpin.shangpinmiaoshu }
						</div>
						<div class="tt-wrapper">
							<div class="tt-row-custom-01">
								<div class="col-item">
									<div class="tt-input-counter style-01">
										<span class="minus-btn"></span>
										<span class="shangpinid" style="display: none;">${shangpin.id}</span>
										<input type="text" value="1" size="100">
										<span class="plus-btn"></span>
									</div>
								</div>
								<div class="col-item">
									<a href="#"  onclick="addcart(${shangpin.id });" class="btn btn-lg"><i class="icon-f-39"></i>加入购物车</a>
								</div>
							</div>
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
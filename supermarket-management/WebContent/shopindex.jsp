<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="head.jsp"%>

<div id="tt-pageContent">
	<div class="container-indent">
		<div class="container container-fluid-custom-mobile-padding">
			<div class="tt-categories-listing">
				<div class="row">
					<c:forEach items="${shangpindalei}" var="dalei">
					<div class="col-6 col-md-4">
						<div class="tt-items-categories">
							<a class="tt-title-block" href="shopservlet?method=shangpinlist&shangpindalei=${dalei.leibieming}">
								<h2 class="tt-title">${dalei.leibieming }</h2>
								<img src="upload/${dalei.zhanshitupian }" data-src="upload/${dalei.zhanshitupian }" alt="">
							</a>
							<a class="btn-link-02" href="shopservlet?method=shangpinlist&shangpindalei=${dalei.leibieming}">${dalei.leibieming }浏览</a>
						</div>
					</div>
					</c:forEach>
					
					
					
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="footer.jsp"%>
</body>
</html>
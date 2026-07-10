<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="head.jsp"%>


<div id="tt-pageContent">
	<div class="container-indent">
		<div class="container">
			<h1 class="tt-title-subpages noborder">创建一个账号</h1>
			<div class="tt-login-form">
				<div class="row justify-content-center">
					<div class="col-md-8 col-lg-6">
						<div class="tt-item">
							<h2 class="tt-title">个人信息</h2>
							<div class="form-default">
							
								<form id="contactform" method="post" action="shopservlet?method=create" novalidate="novalidate">
									<div class="form-group">
										<label for="loginInputName">用户名 *</label>
										<div class="tt-required">* 必填</div>
										<input type="text" name="username" class="form-control" id="loginInputName" placeholder="用户名">
									</div>
									<div class="form-group">
										<label for="loginInputEmail">E-MAIL *</label>
										<input type="text" name="email" class="form-control" id="loginInputEmail" placeholder="邮件">
									</div>
									<div class="form-group">
										<label for="loginInputPassword">密码 *</label>
										<input type="text" name="password" class="form-control" id="loginInputPassword" placeholder="密码">
									</div>
									<div class="row">
										<div class="col-auto">
											<div class="form-group">
												<button class="btn btn-border" type="submit">创建</button>
											</div>
										</div>
										<div class="col-auto align-self-center">
											<div class="form-group">
												<ul class="additional-links">
													<li>or <a href="shopservlet?method=index">返回购物</a></li>
												</ul>
											</div>
										</div>
									</div>

								</form>
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
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="head.jsp"%>


<div id="tt-pageContent">
	<div class="container-indent">
		<div class="container">
			<h1 class="tt-title-subpages noborder">已经注册了吗?</h1>
			<div class="tt-login-form">
				<div class="row">
					<div class="col-xs-12 col-md-6">
						<div class="tt-item">
							<h2 class="tt-title">新账号</h2>
							<p>
							</p>
							<div class="form-group">
								<a href="shopservlet?method=frontcreate" class="btn btn-top btn-border">创建一个账号</a>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-md-6">
						<div class="tt-item">
							<h2 class="tt-title">登录</h2>
							<h4 style="color: red;">${msg }</h4>
							<div class="form-default form-top">
								<form id="customer_login" method="post" action="shopservlet?method=login" novalidate="novalidate">
									<div class="form-group">
										<label for="loginInputName">用户名 *</label>
										<div class="tt-required">* 必填</div>
										<input type="text" name="username" class="form-control" id="loginInputName" placeholder="用户名">
									</div>
									<div class="form-group">
										<label for="loginInputEmail">密码 *</label>
										<input type="text" name="password" class="form-control" id="loginInputEmail" placeholder="请输入密码">
									</div>
									<div class="row">
										<div class="col-auto mr-auto">
											<div class="form-group">
												<button class="btn btn-border" type="submit">登录</button>
											</div>
										</div>
										<div class="col-auto align-self-end">
											<div class="form-group">
												<ul class="additional-links">
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
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-user-resetPwd">
			<div class="form-group">
				<label class="col-sm-3 control-label">旧密码：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="oldPassword" id="oldPassword">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">新密码：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="newPassword" id="newPassword">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">再次确认：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="confirm" id="confirm">
					<span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 请再次输入您的密码</span>
				</div>
			</div>
		</form>
	</div>

	<script>
		$("#form-user-resetPwd").validate({
			rules:{
				oldPassword:{
					required:true,
					remote: {
	                    url: "yonghuservlet?method=checkPassword",
	                    type: "post",
	                    dataType: "json",
	                    data: {
	                        password: function() {
	                            return $("input[name='oldPassword']").val();
	                        }
	                    }
	                }
				},
				newPassword: {
	                required: true,
	            },
	            confirm: {
	                required: true,
	                equalTo: "#newPassword"
	            }
			},
			messages: {
	            oldPassword: {
	                required: "请输入原密码",
				    remote: "原密码错误"
	            },
	            newPassword: {
	                required: "请输入新密码",
	            },
	            confirm: {
	                required: "请再次输入新密码",
	                equalTo: "两次密码输入不一致"
	            }

	        },
	        focusCleanup: true
		});
		
		function submitHandler() {
	        if ($.validate.form()) {
	        	$.operate.save("yonghuservlet?method=resetpwd", $('#form-user-resetPwd').serialize());
	        }
	    }
	</script>
</body>
</html>

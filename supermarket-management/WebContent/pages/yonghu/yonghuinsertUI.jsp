<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-yonghu-add">
        	<div class="form-group">	
				<label class="col-sm-3 control-label">用户名：</label>
				<div class="col-sm-8">
					<input name="yonghuming" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">密码：</label>
				<div class="col-sm-8">
					<input name="mima" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">角色：</label>
				<div class="col-sm-8">
					<input name="jiaose" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">电话：</label>
				<div class="col-sm-8">
					<input name="dianhua" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">地址：</label>
				<div class="col-sm-8">
					<input name="dizhi" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">姓名：</label>
				<div class="col-sm-8">
					<input name="xingming" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">邮编：</label>
				<div class="col-sm-8">
					<input name="youbian" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">邮件：</label>
				<div class="col-sm-8">
					<input name="youjian" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">头像：</label>
				<div class="col-sm-8">
					<input name="touxiang" class="form-control" type="text">

				</div>
			</div>
		</form>
	</div>
    <script type="text/javascript">
		$("#form-yonghu-add").validate({
			rules:{
				xxxx:{
					required:true,
				},
			}
		});
		function submitHandler() {
	        if ($.validate.form()) {
	            $.operate.save("yonghuservlet?method=insert", $('#form-yonghu-add').serialize());
	        }
	    }
	     $('.summernote').summernote({
		    	placeholder: '请输入',
				height : 192,
				lang : 'zh-CN',
				followingToolbar: false,
				callbacks: {
	                onImageUpload: function (files) {
	                   // sendFile(files[0], this);
	                }
	            }
			});
	</script>
</body>
</html>

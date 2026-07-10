<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<meta charset="utf-8">
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-yonghu-edit">
            <input id="id" name="id" value="${yonghu.id}"  type="hidden">
            <div class="form-group">	
				<label class="col-sm-3 control-label">用户名：</label>
				<div class="col-sm-8">
					<input id="yonghuming" name="yonghuming" value="${ yonghu.yonghuming }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">密码：</label>
				<div class="col-sm-8">
					<input id="mima" name="mima" value="${ yonghu.mima }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">角色：</label>
				<div class="col-sm-8">
					<input id="jiaose" name="jiaose" value="${ yonghu.jiaose }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">电话：</label>
				<div class="col-sm-8">
					<input id="dianhua" name="dianhua" value="${ yonghu.dianhua }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">地址：</label>
				<div class="col-sm-8">
					<input id="dizhi" name="dizhi" value="${ yonghu.dizhi }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">姓名：</label>
				<div class="col-sm-8">
					<input id="xingming" name="xingming" value="${ yonghu.xingming }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">邮编：</label>
				<div class="col-sm-8">
					<input id="youbian" name="youbian" value="${ yonghu.youbian }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">邮件：</label>
				<div class="col-sm-8">
					<input id="youjian" name="youjian" value="${ yonghu.youjian }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">头像：</label>
				<div class="col-sm-8">
					<input id="touxiang" name="touxiang" value="${ yonghu.touxiang }"  class="form-control" type="text">
				</div>
			</div>
		</form>
    </div>
    <script type="text/javascript">
		
		$("#form-yonghu-edit").validate({
			rules:{
				xxxx:{
					required:true,
				},
			}
		});
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
		function submitHandler() {
	        if ($.validate.form()) {
	            $.operate.save("yonghuservlet?method=update", $('#form-yonghu-edit').serialize());
	        }
	    }
	</script>
</body>
</html>

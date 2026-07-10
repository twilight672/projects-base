<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-dingdanmingxi-add">
        	<div class="form-group">	
				<label class="col-sm-3 control-label">订单编号：</label>
				<div class="col-sm-8">
					<input name="dingdanbianhao" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">价格：</label>
				<div class="col-sm-8">
					<input name="jiage" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">商品：</label>
				<div class="col-sm-8">
					<input name="shangpin" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">数量：</label>
				<div class="col-sm-8">
					<input name="shuliang" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">总价：</label>
				<div class="col-sm-8">
					<input name="zongjia" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">商品id：</label>
				<div class="col-sm-8">
					<input name="shangpinid" class="form-control" type="text">

				</div>
			</div>
		</form>
	</div>
    <script type="text/javascript">
		$("#form-dingdanmingxi-add").validate({
			rules:{
				xxxx:{
					required:true,
				},
			}
		});
		function submitHandler() {
	        if ($.validate.form()) {
	            $.operate.save("dingdanmingxiservlet?method=insert", $('#form-dingdanmingxi-add').serialize());
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

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-shangpin-add">
        	<div class="form-group">	
				<label class="col-sm-3 control-label">商品名：</label>
				<div class="col-sm-8">
					<input name="shangpinming" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">价格：</label>
				<div class="col-sm-8">
					<input name="jiage" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">商品描述：</label>
				<div class="col-sm-8">
					<input name="shangpinmiaoshu" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">商品大类：</label>
				<div class="col-sm-8">
				<select class="form-control" name="shangpindalei"> 
					<option value="">请选择</option>
					<c:forEach items="${shangpindaleilist }" var="temp">
					<option value="${temp.leibieming}">${temp.leibieming}</option>
					</c:forEach>
					
				</select>

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">图片：</label>
				<div class="col-sm-8">
					<input name="tupian" class="form-control" type="text">

				</div>
			</div>
		</form>
	</div>
    <script type="text/javascript">
		$("#form-shangpin-add").validate({
			rules:{
				xxxx:{
					required:true,
				},
			}
		});
		function submitHandler() {
	        if ($.validate.form()) {
	            $.operate.save("shangpinservlet?method=insert", $('#form-shangpin-add').serialize());
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

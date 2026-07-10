<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-kucun-add">
        	<div class="form-group">	
				<label class="col-sm-3 control-label">商品名：</label>
				<div class="col-sm-8">
					<input name="shangpinming" class="form-control" type="text">

				</div>
			</div>
        	<div class="form-group">	
				<label class="col-sm-3 control-label">库存量：</label>
				<div class="col-sm-8">
					<input name="kucunliang" class="form-control" type="text">

				</div>
			</div>
		</form>
	</div>
    <script type="text/javascript">
		$("#form-kucun-add").validate({
			rules:{
				xxxx:{
					required:true,
				},
			}
		});
		function submitHandler() {
	        if ($.validate.form()) {
	            $.operate.save("kucunservlet?method=insert", $('#form-kucun-add').serialize());
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

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<meta charset="utf-8">
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-kucun-edit">
            <input id="id" name="id" value="${kucun.id}"  type="hidden">
            <div class="form-group">	
				<label class="col-sm-3 control-label">商品名：</label>
				<div class="col-sm-8">
					<input id="shangpinming" name="shangpinming" value="${ kucun.shangpinming }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">库存量：</label>
				<div class="col-sm-8">
					<input id="kucunliang" name="kucunliang" value="${ kucun.kucunliang }"  class="form-control" type="text">
				</div>
			</div>
		</form>
    </div>
    <script type="text/javascript">
		
		$("#form-kucun-edit").validate({
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
	            $.operate.save("kucunservlet?method=update", $('#form-kucun-edit').serialize());
	        }
	    }
	</script>
</body>
</html>

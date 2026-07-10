<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<meta charset="utf-8">
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-shangpindalei-edit">
            <input id="id" name="id" value="${shangpindalei.id}"  type="hidden">
            <div class="form-group">	
				<label class="col-sm-3 control-label">类别名：</label>
				<div class="col-sm-8">
					<input id="leibieming" name="leibieming" value="${ shangpindalei.leibieming }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">展示图片：</label>
				<div class="col-sm-8">
					<input id="zhanshitupian" name="zhanshitupian" value="${ shangpindalei.zhanshitupian }"  class="form-control" type="text">
				</div>
			</div>
		</form>
    </div>
    <script type="text/javascript">
		
		$("#form-shangpindalei-edit").validate({
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
	            $.operate.save("shangpindaleiservlet?method=update", $('#form-shangpindalei-edit').serialize());
	        }
	    }
	</script>
</body>
</html>

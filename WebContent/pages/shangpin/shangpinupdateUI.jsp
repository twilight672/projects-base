<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<meta charset="utf-8">
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-shangpin-edit">
            <input id="id" name="id" value="${shangpin.id}"  type="hidden">
            <div class="form-group">	
				<label class="col-sm-3 control-label">商品名：</label>
				<div class="col-sm-8">
					<input id="shangpinming" name="shangpinming" value="${ shangpin.shangpinming }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">价格：</label>
				<div class="col-sm-8">
					<input id="jiage" name="jiage" value="${ shangpin.jiage }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">商品描述：</label>
				<div class="col-sm-8">
					<input id="shangpinmiaoshu" name="shangpinmiaoshu" value="${ shangpin.shangpinmiaoshu }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">商品大类：</label>
				<div class="col-sm-8">
				<select class="form-control" name="shangpindalei"> 
					<option value="">请选择</option>
					<option <c:if test="${ shangpin.shangpindalei == 'shangpindalei.leibieming'}">selected</c:if> value="shangpindalei.leibieming">shangpindalei.leibieming</option>
				</select>
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">图片：</label>
				<div class="col-sm-8">
					<input id="tupian" name="tupian" value="${ shangpin.tupian }"  class="form-control" type="text">
				</div>
			</div>
		</form>
    </div>
    <script type="text/javascript">
		
		$("#form-shangpin-edit").validate({
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
	            $.operate.save("shangpinservlet?method=update", $('#form-shangpin-edit').serialize());
	        }
	    }
	</script>
</body>
</html>

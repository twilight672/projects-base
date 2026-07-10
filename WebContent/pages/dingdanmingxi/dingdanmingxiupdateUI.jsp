<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<meta charset="utf-8">
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-dingdanmingxi-edit">
            <input id="id" name="id" value="${dingdanmingxi.id}"  type="hidden">
            <div class="form-group">	
				<label class="col-sm-3 control-label">订单编号：</label>
				<div class="col-sm-8">
					<input id="dingdanbianhao" name="dingdanbianhao" value="${ dingdanmingxi.dingdanbianhao }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">价格：</label>
				<div class="col-sm-8">
					<input id="jiage" name="jiage" value="${ dingdanmingxi.jiage }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">商品：</label>
				<div class="col-sm-8">
					<input id="shangpin" name="shangpin" value="${ dingdanmingxi.shangpin }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">数量：</label>
				<div class="col-sm-8">
					<input id="shuliang" name="shuliang" value="${ dingdanmingxi.shuliang }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">总价：</label>
				<div class="col-sm-8">
					<input id="zongjia" name="zongjia" value="${ dingdanmingxi.zongjia }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">商品id：</label>
				<div class="col-sm-8">
					<input id="shangpinid" name="shangpinid" value="${ dingdanmingxi.shangpinid }"  class="form-control" type="text">
				</div>
			</div>
		</form>
    </div>
    <script type="text/javascript">
		
		$("#form-dingdanmingxi-edit").validate({
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
	            $.operate.save("dingdanmingxiservlet?method=update", $('#form-dingdanmingxi-edit').serialize());
	        }
	    }
	</script>
</body>
</html>

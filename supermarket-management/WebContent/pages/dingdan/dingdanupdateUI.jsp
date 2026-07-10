<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<base href="<%=basePath%>">
<html>
<meta charset="utf-8">
<body class="white-bg">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-dingdan-edit">
            <input id="id" name="id" value="${dingdan.id}"  type="hidden">
            <div class="form-group">	
				<label class="col-sm-3 control-label">订单日期：</label>
				<div class="col-sm-8">
					<input id="dingdanriqi" name="dingdanriqi" value="${ dingdan.dingdanriqi }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">订单状态：</label>
				<div class="col-sm-8">
					<input id="dingdanzhuangtai" name="dingdanzhuangtai" value="${ dingdan.dingdanzhuangtai }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">合计：</label>
				<div class="col-sm-8">
					<input id="heji" name="heji" value="${ dingdan.heji }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">支付状态：</label>
				<div class="col-sm-8">
					<input id="zhifuzhuangtai" name="zhifuzhuangtai" value="${ dingdan.zhifuzhuangtai }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">订单编号：</label>
				<div class="col-sm-8">
					<input id="dingdanbianhao" name="dingdanbianhao" value="${ dingdan.dingdanbianhao }"  class="form-control" type="text">
				</div>
			</div>
            <div class="form-group">	
				<label class="col-sm-3 control-label">买家：</label>
				<div class="col-sm-8">
					<input id="maijia" name="maijia" value="${ dingdan.maijia }"  class="form-control" type="text">
				</div>
			</div>
		</form>
    </div>
    <script type="text/javascript">
		
		$("#form-dingdan-edit").validate({
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
	            $.operate.save("dingdanservlet?method=update", $('#form-dingdan-edit').serialize());
	        }
	    }
	</script>
</body>
</html>

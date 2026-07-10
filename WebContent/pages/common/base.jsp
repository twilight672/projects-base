<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<link href="pages/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="summernote/summernote.css" rel="stylesheet"/>
	<link href="summernote/summernote-bs3.css" rel="stylesheet"/>
	<link href="pages/css/font-awesome.min.css" rel="stylesheet"/>
	<!-- bootstrap-table Ã¨Â¡Â¨Ã¦Â Â¼Ã¦ÂÂÃ¤Â»Â¶Ã¦Â Â·Ã¥Â¼Â -->
	<link href="pages/ajax/libs/bootstrap-table/bootstrap-table.min.css" rel="stylesheet"/>
	<link href="pages/ajax/libs/bootstrap-treetable/bootstrap-treetable.css" rel="stylesheet"/>
	<link href="pages/css/animate.css" rel="stylesheet"/>
	<link href="pages/css/style.css" rel="stylesheet"/>
	<link href="pages/ajax/libs/select/select2.css" rel="stylesheet"/>
	<link href="pages/css/layui.css" rel="stylesheet"/>
	<link href="pages/css/myui.css" rel="stylesheet"/>
	<!--calendar-->
	<link href="pages/ajax/libs/calendar/css/default.css" rel="stylesheet"/>
	<link href="pages/ajax/libs/calendar/css/bootstrap-year-calendar.min.css" rel="stylesheet"/>
	<link href="pages/ajax/libs/calendar/bootstrap-3.3.5/css/bootstrap.min.css" rel="stylesheet"/>
<script src="pages/js/jquery.min.js"></script>
	<script src="pages/js/bootstrap.min.js"></script>
	
	<!-- bootstrap-table Ã¨Â¡Â¨Ã¦Â Â¼Ã¦ÂÂÃ¤Â»Â¶ -->
	<script src="pages/ajax/libs/bootstrap-table/bootstrap-table.min.js"></script>
	<script src="pages/ajax/libs/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	<script src="pages/ajax/libs/bootstrap-table/extensions/mobile/bootstrap-table-mobile.min.js"></script>
	<script src="pages/ajax/libs/bootstrap-table/extensions/toolbar/bootstrap-table-toolbar.min.js"></script>
	<!--calendar-->
	<script src="pages/ajax/libs/calendar/js/bootstrap-datepicker.min.js"></script>
	<script src="pages/ajax/libs/calendar/js/bootstrap-year-calendar.min.js"></script>
	<script src="pages/ajax/libs/calendar/js/bootstrap-popover.js"></script>
	
	<script src="summernote/summernote.min.js"></script>
	<script src="summernote/summernote-zh-CN.js"></script>

	<!-- jquery-validate Ã¨Â¡Â¨Ã¥ÂÂÃ©ÂªÂÃ¨Â¯ÂÃ¦ÂÂÃ¤Â»Â¶ -->
	<script src="pages/ajax/libs/validate/jquery.validate.min.js"></script>
	<script src="pages/ajax/libs/validate/messages_zh.min.js"></script>
	<script src="pages/ajax/libs/validate/jquery.validate.extend.js"></script>
	<!-- jquery-validate Ã¨Â¡Â¨Ã¥ÂÂÃ¦Â ÂÃ¦ÂÂÃ¤Â»Â¶ -->
	<script src="pages/ajax/libs/bootstrap-treetable/bootstrap-treetable.js"></script>
	<!-- jquery-export Ã¨Â¡Â¨Ã¦Â Â¼Ã¥Â¯Â¼Ã¥ÂÂºÃ¦ÂÂÃ¤Â»Â¶ -->
	<script src="pages/ajax/libs/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>
	<script src="pages/ajax/libs/bootstrap-table/extensions/export/tableExport.js"></script>
	<!-- Ã©ÂÂ®Ã§Â½Â©Ã¥Â±Â -->
	<script src="pages/ajax/libs/blockUI/jquery.blockUI.js"></script>
	<script src="pages/ajax/libs/iCheck/icheck.min.js"></script>
	<script src="pages/ajax/libs/layer/layer.min.js"></script>
	<script src="pages/ajax/libs/layui/layui.js"></script>
	<script src="pages/js/common.js?v=3.0.0"></script>
	<script src="pages/js/myui.js?v=3.0.0"></script>
	<script type="text/javascript">
	function upload(clazzname,proname){
		var formData = new FormData();
		formData.append("file1",$("#"+proname+"upload")[0].files[0]);
		$.ajax({
			type : "post",
			url : clazzname+"_uploadFile.do",
			data : formData,
			processData : false,
			contentType : false,
			success : function(data){
				$("input[name='"+clazzname+"."+proname+"']").val(data);
				alert("上传成功");
				}
		});
	}
	var fillForm = function ($form, json,clazzname) {
	    var jsonObj = json;
	    if (typeof json === 'string') {
	        jsonObj = $.parseJSON(json);
	    }
	    for (var key in jsonObj) {  //遍历json字符串
	        $(" input[ name='"+clazzname+"."+key+"' ] ").val(jsonObj[key]);
	    }
	}
	</script>
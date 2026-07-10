<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@include file="/pages/common/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>小型超市管理系统</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/util.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
    <div class="limiter">
        <div class="container-login100" style="background-image: url('images/img-01.jpg');">
            <div class="wrap-login100 p-t-190 p-b-30">
                <form action="yonghuservlet?method=login" method="post" class="login100-form validate-form">
                    <span class="login100-form-title p-t-20 p-b-45">小型超市管理系统</span>
                    <div class="wrap-input100 validate-input m-b-10" data-validate="请输入用户名">
                        <input class="input100" type="text" name="username" placeholder="用户名" autocomplete="off">
                        <span class="focus-input100"></span>
                        <span class="symbol-input100">
                            <i class="fa fa-user"></i>
                        </span>
                    </div>
                    <div class="wrap-input100 validate-input m-b-10" data-validate="请输入密码">
                        <input class="input100" type="password" name="password" placeholder="密码">
                        <span class="focus-input100"></span>
                        <span class="symbol-input100">
                            <i class="fa fa-lock"></i>
                        </span>
                    </div>
                    <div class="container-login100-form-btn p-t-10">
                        <button class="login100-form-btn">登 录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    function zhuce() {
        $.modal.open("注册", "yonghuservlet?method=insert","600","800");
	}
    </script>
    <script src="external/lazyLoad/lazyload.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>奶茶管理系统 - 登录</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 350px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #666;
        }
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn-login {
            width: 100%;
            padding: 10px;
            background-color: #ff6b6b;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .btn-login:hover {
            background-color: #ee5a52;
        }
        .error-message {
            color: red;
            text-align: center;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>奶茶管理系统</h2>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            <div class="form-group">
                <label for="username">用户名:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">密码:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="btn-login">登录</button>
        </form>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>奶茶管理系统</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        header {
            background-color: #ff6b6b;
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        nav {
            background-color: #333;
            overflow: hidden;
        }
        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
        nav li {
            float: left;
        }
        nav li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }
        nav li a:hover {
            background-color: #111;
        }
        .container {
            padding: 20px;
            max-width: 1200px;
            margin: 0 auto;
        }
        .dashboard {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-top: 20px;
        }
        .card {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            flex: 1;
            min-width: 250px;
            text-align: center;
        }
        .card h3 {
            color: #333;
            margin-bottom: 10px;
        }
        .card .value {
            font-size: 36px;
            color: #ff6b6b;
            font-weight: bold;
        }
        .logout-btn {
            background-color: #333;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .logout-btn:hover {
            background-color: #111;
        }
    </style>
</head>
<body>
    <header>
        <h1>奶茶管理系统</h1>
        <div>
            <span>欢迎, ${employee.name}</span>
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">登出</a>
        </div>
    </header>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/index.jsp">首页</a></li>
            <li><a href="${pageContext.request.contextPath}/product?action=list">产品管理</a></li>
            <li><a href="${pageContext.request.contextPath}/order?action=list">订单管理</a></li>
            <li><a href="${pageContext.request.contextPath}/customer?action=list">客户管理</a></li>
            <li><a href="${pageContext.request.contextPath}/employee?action=list">员工管理</a></li>
            <li><a href="${pageContext.request.contextPath}/category?action=list">分类管理</a></li>
        </ul>
    </nav>
    <div class="container">
        <h2>系统概览</h2>
        <div class="dashboard">
            <div class="card">
                <h3>产品总数</h3>
                <div class="value">100</div>
            </div>
            <div class="card">
                <h3>今日订单</h3>
                <div class="value">25</div>
            </div>
            <div class="card">
                <h3>客户总数</h3>
                <div class="value">500</div>
            </div>
            <div class="card">
                <h3>今日销售额</h3>
                <div class="value">¥12,500</div>
            </div>
        </div>
    </div>
</body>
</html>
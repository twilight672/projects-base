<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>客户列表</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>客户列表</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="add.jsp">添加客户</a>
            </div>
        </div>
        
        <div class="content">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>客户ID</th>
                        <th>客户名称</th>
                        <th>性别</th>
                        <th>手机号码</th>
                        <th>地址</th>
                        <th>会员等级</th>
                        <th>积分</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${customers}" var="customer">
                        <tr>
                            <td>${customer.id}</td>
                            <td>${customer.name}</td>
                            <td>${customer.gender == 1 ? '男' : '女'}</td>
                            <td>${customer.phone}</td>
                            <td>${customer.address}</td>
                            <td>${customer.memberLevel}</td>
                            <td>${customer.points}</td>
                            <td>
                                <a href="customer?action=edit&id=${customer.id}">编辑</a>
                                <a href="customer?action=delete&id=${customer.id}" onclick="return confirm('确定要删除这个客户吗？');">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单列表</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>订单列表</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="add.jsp">添加订单</a>
            </div>
        </div>
        
        <div class="content">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>订单ID</th>
                        <th>订单编号</th>
                        <th>客户</th>
                        <th>员工</th>
                        <th>总金额</th>
                        <th>支付方式</th>
                        <th>订单状态</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orders}" var="order">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.orderNumber}</td>
                            <td>${order.customerName}</td>
                            <td>${order.employeeName}</td>
                            <td>${order.totalAmount}</td>
                            <td>${order.paymentMethod == 1 ? '现金' : '微信'}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.orderStatus == 1}">待处理</c:when>
                                    <c:when test="${order.orderStatus == 2}">已完成</c:when>
                                    <c:when test="${order.orderStatus == 3}">已取消</c:when>
                                    <c:otherwise>未知</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${order.createTime}</td>
                            <td>
                                <a href="detail?id=${order.id}">详情</a>
                                <a href="edit.jsp?id=${order.id}">编辑</a>
                                <a href="delete?id=${order.id}" onclick="return confirm('确定要删除这个订单吗？');">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
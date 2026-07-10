<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>订单详情</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="list">订单列表</a>
            </div>
        </div>
        
        <div class="content">
            <div class="order-info">
                <h2>订单信息</h2>
                <table class="info-table">
                    <tr>
                        <td>订单ID:</td>
                        <td>${order.id}</td>
                        <td>订单编号:</td>
                        <td>${order.orderNumber}</td>
                    </tr>
                    <tr>
                        <td>客户:</td>
                        <td>${order.customerName} (${order.customerPhone})</td>
                        <td>员工:</td>
                        <td>${order.employeeName}</td>
                    </tr>
                    <tr>
                        <td>总金额:</td>
                        <td>${order.totalAmount}</td>
                        <td>支付方式:</td>
                        <td>${order.paymentMethod == 1 ? '现金' : '微信'}</td>
                    </tr>
                    <tr>
                        <td>订单状态:</td>
                        <td>
                            <c:choose>
                                <c:when test="${order.orderStatus == 1}">待处理</c:when>
                                <c:when test="${order.orderStatus == 2}">已完成</c:when>
                                <c:when test="${order.orderStatus == 3}">已取消</c:when>
                                <c:otherwise>未知</c:otherwise>
                            </c:choose>
                        </td>
                        <td>创建时间:</td>
                        <td>${order.createTime}</td>
                    </tr>
                </table>
            </div>
            
            <div class="order-items">
                <h2>订单商品</h2>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>产品名称</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>小计</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${order.orderItems}" var="item">
                            <tr>
                                <td>${item.productName}</td>
                                <td>${item.unitPrice}</td>
                                <td>${item.quantity}</td>
                                <td>${item.subtotal}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3" style="text-align: right;">总计:</td>
                            <td>${order.totalAmount}</td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
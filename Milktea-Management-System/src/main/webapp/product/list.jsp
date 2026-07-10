<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>产品列表</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>产品列表</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="add.jsp">添加产品</a>
            </div>
        </div>
        
        <div class="content">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>产品ID</th>
                        <th>产品名称</th>
                        <th>类别</th>
                        <th>价格</th>
                        <th>库存</th>
                        <th>描述</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${products}" var="product">
                        <tr>
                            <td>${product.id}</td>
                            <td>${product.name}</td>
                            <td>${product.categoryName}</td>
                            <td>${product.price}</td>
                            <td>${product.stock}</td>
                            <td>${product.description}</td>
                            <td>${product.status == 1 ? '上架' : '下架'}</td>
                            <td>
                                <a href="edit.jsp?id=${product.id}">编辑</a>
                                <a href="delete?id=${product.id}" onclick="return confirm('确定要删除这个产品吗？');">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
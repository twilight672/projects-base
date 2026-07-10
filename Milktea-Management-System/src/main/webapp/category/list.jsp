<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>类别列表</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>类别列表</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="add.jsp">添加类别</a>
            </div>
        </div>
        
        <div class="content">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>类别ID</th>
                        <th>类别名称</th>
                        <th>描述</th>
                        <th>排序</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td>${category.id}</td>
                            <td>${category.name}</td>
                            <td>${category.description}</td>
                            <td>${category.sortOrder}</td>
                            <td>${category.status == 1 ? '启用' : '禁用'}</td>
                            <td>
                                <a href="edit.jsp?id=${category.id}">编辑</a>
                                <a href="delete?id=${category.id}" onclick="return confirm('确定要删除这个类别吗？');">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
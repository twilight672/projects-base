<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>员工列表</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>员工列表</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="add.jsp">添加员工</a>
            </div>
        </div>
        
        <div class="content">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>员工ID</th>
                        <th>员工名称</th>
                        <th>性别</th>
                        <th>手机号码</th>
                        <th>职位</th>
                        <th>入职时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${employees}" var="employee">
                        <tr>
                            <td>${employee.id}</td>
                            <td>${employee.name}</td>
                            <td>${employee.gender == 1 ? '男' : '女'}</td>
                            <td>${employee.phone}</td>
                            <td>${employee.position}</td>
                            <td>${employee.joinDate}</td>
                            <td>${employee.status == 1 ? '在职' : '离职'}</td>
                            <td>
                                <a href="employee?action=edit&id=${employee.id}">编辑</a>
                                <a href="employee?action=delete&id=${employee.id}" onclick="return confirm('确定要删除这个员工吗？');">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
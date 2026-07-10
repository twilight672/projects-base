<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加产品</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>添加产品</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="list">产品列表</a>
            </div>
        </div>
        
        <div class="content">
            <form action="add" method="post" class="form">
                <div class="form-group">
                    <label for="name">产品名称:</label>
                    <input type="text" id="name" name="name" required>
                </div>
                
                <div class="form-group">
                    <label for="categoryId">产品类别:</label>
                    <select id="categoryId" name="categoryId" required>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="price">价格:</label>
                    <input type="number" id="price" name="price" step="0.01" min="0" required>
                </div>
                
                <div class="form-group">
                    <label for="stock">库存:</label>
                    <input type="number" id="stock" name="stock" min="0" required>
                </div>
                
                <div class="form-group">
                    <label for="description">描述:</label>
                    <textarea id="description" name="description" rows="4"></textarea>
                </div>
                
                <div class="form-group">
                    <label for="status">状态:</label>
                    <select id="status" name="status" required>
                        <option value="1">上架</option>
                        <option value="0">下架</option>
                    </select>
                </div>
                
                <div class="form-actions">
                    <input type="submit" value="添加">
                    <input type="reset" value="重置">
                </div>
            </form>
        </div>
    </div>
</body>
</html>
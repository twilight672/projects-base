<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加类别</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>添加类别</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="list">类别列表</a>
            </div>
        </div>
        
        <div class="content">
            <form action="add" method="post" class="form">
                <div class="form-group">
                    <label for="name">类别名称:</label>
                    <input type="text" id="name" name="name" required>
                </div>
                
                <div class="form-group">
                    <label for="description">描述:</label>
                    <textarea id="description" name="description" rows="3"></textarea>
                </div>
                
                <div class="form-group">
                    <label for="sortOrder">排序:</label>
                    <input type="number" id="sortOrder" name="sortOrder" min="0" value="0">
                </div>
                
                <div class="form-group">
                    <label for="status">状态:</label>
                    <select id="status" name="status" required>
                        <option value="1">启用</option>
                        <option value="0">禁用</option>
                    </select>
                </div>
                
                <div class="form-actions">
                    <input type="submit" value="添加">
                    <a href="list" class="btn-cancel">取消</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑员工</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>编辑员工</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="list">员工列表</a>
            </div>
        </div>
        
        <div class="content">
            <form action="employee?action=edit" method="post" class="form">
                <input type="hidden" name="id" value="${employee.id}">
                
                <div class="form-group">
                    <label for="name">员工名称:</label>
                    <input type="text" id="name" name="name" value="${employee.name}" required>
                </div>
                
                <div class="form-group">
                    <label for="gender">性别:</label>
                    <select id="gender" name="gender" required>
                        <option value="1" ${employee.gender == 1 ? 'selected' : ''}>男</option>
                        <option value="0" ${employee.gender == 0 ? 'selected' : ''}>女</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="phone">手机号码:</label>
                    <input type="text" id="phone" name="phone" value="${employee.phone}" required>
                </div>
                
                <div class="form-group">
                    <label for="position">职位:</label>
                    <select id="position" name="position" required>
                        <option value="管理员" ${employee.position == '管理员' ? 'selected' : ''}>管理员</option>
                        <option value="收银员" ${employee.position == '收银员' ? 'selected' : ''}>收银员</option>
                        <option value="服务员" ${employee.position == '服务员' ? 'selected' : ''}>服务员</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="username">用户名:</label>
                    <input type="text" id="username" name="username" value="${employee.username}" required>
                </div>
                
                <div class="form-group">
                    <label for="password">密码:</label>
                    <input type="password" id="password" name="password" placeholder="不修改密码请留空">
                </div>
                
                <div class="form-group">
                    <label for="status">状态:</label>
                    <select id="status" name="status" required>
                        <option value="1" ${employee.status == 1 ? 'selected' : ''}>在职</option>
                        <option value="0" ${employee.status == 0 ? 'selected' : ''}>离职</option>
                    </select>
                </div>
                
                <div class="form-actions">
                    <input type="submit" value="更新">
                    <a href="list" class="btn-cancel">取消</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
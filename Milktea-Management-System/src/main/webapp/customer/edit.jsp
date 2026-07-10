<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑客户</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>编辑客户</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="list">客户列表</a>
            </div>
        </div>
        
        <div class="content">
            <form action="customer?action=edit" method="post" class="form">
                <input type="hidden" name="id" value="${customer.id}">
                
                <div class="form-group">
                    <label for="name">客户名称:</label>
                    <input type="text" id="name" name="name" value="${customer.name}" required>
                </div>
                
                <div class="form-group">
                    <label for="gender">性别:</label>
                    <select id="gender" name="gender" required>
                        <option value="1" ${customer.gender == 1 ? 'selected' : ''}>男</option>
                        <option value="0" ${customer.gender == 0 ? 'selected' : ''}>女</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="phone">手机号码:</label>
                    <input type="text" id="phone" name="phone" value="${customer.phone}" required>
                </div>
                
                <div class="form-group">
                    <label for="address">地址:</label>
                    <textarea id="address" name="address" rows="3">${customer.address}</textarea>
                </div>
                
                <div class="form-group">
                    <label for="memberLevel">会员等级:</label>
                    <select id="memberLevel" name="memberLevel">
                        <option value="普通会员" ${customer.memberLevel == '普通会员' ? 'selected' : ''}>普通会员</option>
                        <option value="银卡会员" ${customer.memberLevel == '银卡会员' ? 'selected' : ''}>银卡会员</option>
                        <option value="金卡会员" ${customer.memberLevel == '金卡会员' ? 'selected' : ''}>金卡会员</option>
                        <option value="钻石会员" ${customer.memberLevel == '钻石会员' ? 'selected' : ''}>钻石会员</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="points">积分:</label>
                    <input type="number" id="points" name="points" min="0" value="${customer.points}">
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
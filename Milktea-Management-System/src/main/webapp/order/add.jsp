<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加订单</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>添加订单</h1>
            <div class="header-right">
                <a href="../index.jsp">首页</a>
                <a href="list">订单列表</a>
            </div>
        </div>
        
        <div class="content">
            <form action="add" method="post" class="form">
                <div class="form-group">
                    <label for="customerId">客户:</label>
                    <select id="customerId" name="customerId" required>
                        <c:forEach items="${customers}" var="customer">
                            <option value="${customer.id}">${customer.name} (${customer.phone})</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="employeeId">员工:</label>
                    <select id="employeeId" name="employeeId" required>
                        <c:forEach items="${employees}" var="employee">
                            <option value="${employee.id}">${employee.name}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="paymentMethod">支付方式:</label>
                    <select id="paymentMethod" name="paymentMethod" required>
                        <option value="1">现金</option>
                        <option value="2">微信</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="orderStatus">订单状态:</label>
                    <select id="orderStatus" name="orderStatus" required>
                        <option value="1">待处理</option>
                        <option value="2">已完成</option>
                        <option value="3">已取消</option>
                    </select>
                </div>
                
                <h3>订单商品</h3>
                <div id="orderItems">
                    <div class="order-item">
                        <div class="form-group">
                            <label for="productId">产品:</label>
                            <select name="productId[]" class="product-select" required>
                                <c:forEach items="${products}" var="product">
                                    <option value="${product.id}" data-price="${product.price}">${product.name} - ¥${product.price}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="quantity">数量:</label>
                            <input type="number" name="quantity[]" min="1" value="1" class="quantity-input" required>
                        </div>
                        <div class="form-group">
                            <label for="subtotal">小计:</label>
                            <input type="number" name="subtotal[]" step="0.01" readonly class="subtotal-input">
                        </div>
                        <button type="button" class="remove-item">删除</button>
                    </div>
                </div>
                
                <button type="button" id="add-item">添加商品</button>
                
                <div class="form-group">
                    <label for="totalAmount">总金额:</label>
                    <input type="number" id="totalAmount" name="totalAmount" step="0.01" readonly required>
                </div>
                
                <div class="form-actions">
                    <input type="submit" value="创建订单">
                    <a href="list" class="btn-cancel">取消</a>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        // 计算小计和总计
        function calculateTotal() {
            let total = 0;
            document.querySelectorAll('.order-item').forEach(item => {
                const productSelect = item.querySelector('.product-select');
                const quantityInput = item.querySelector('.quantity-input');
                const subtotalInput = item.querySelector('.subtotal-input');
                
                const price = parseFloat(productSelect.options[productSelect.selectedIndex].dataset.price);
                const quantity = parseInt(quantityInput.value);
                const subtotal = price * quantity;
                
                subtotalInput.value = subtotal.toFixed(2);
                total += subtotal;
            });
            
            document.getElementById('totalAmount').value = total.toFixed(2);
        }
        
        // 添加商品
        document.getElementById('add-item').addEventListener('click', () => {
            const orderItems = document.getElementById('orderItems');
            const firstItem = orderItems.querySelector('.order-item');
            const newItem = firstItem.cloneNode(true);
            
            // 重置输入值
            newItem.querySelector('.quantity-input').value = 1;
            newItem.querySelector('.subtotal-input').value = '';
            
            orderItems.appendChild(newItem);
            
            // 重新绑定事件
            bindEvents();
            calculateTotal();
        });
        
        // 删除商品
        function bindEvents() {
            document.querySelectorAll('.remove-item').forEach(btn => {
                btn.addEventListener('click', (e) => {
                    if (document.querySelectorAll('.order-item').length > 1) {
                        e.target.closest('.order-item').remove();
                        calculateTotal();
                    }
                });
            });
            
            document.querySelectorAll('.product-select, .quantity-input').forEach(input => {
                input.addEventListener('change', calculateTotal);
            });
        }
        
        // 初始化
        bindEvents();
        calculateTotal();
    </script>
</body>
</html>
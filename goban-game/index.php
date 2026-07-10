<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>五子棋游戏 - 登录</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <div class="login-box">
            <h1>五子棋游戏</h1>
            
            <!-- 登录表单 -->
            <div id="login-form" class="form-container">
                <h2>用户登录</h2>
                <input type="text" id="username" placeholder="请输入用户名" required>
                <input type="password" id="password" placeholder="请输入密码" required>
                <button id="login-btn">登录</button>
                <p>还没有账号？<span id="show-register">立即注册</span></p>
            </div>
            
            <!-- 注册表单容器(默认隐藏) -->
            <div id="register-form" class="form-container" style="display: none;">
                <h2>用户注册</h2>
                <input type="text" id="reg-username" placeholder="请输入用户名" required>
                <input type="password" id="reg-password" placeholder="请输入密码" required>
                <button id="register-btn">注册</button>
                <p>已有账号？<span id="show-login">返回登录</span></p>
            </div>
            
            <!-- 游客入口 -->
            <div class="guest-entry">
                <p>或</p>
                <button id="guest-btn">游客登录</button>
            </div>
        </div>
    </div>
    
    <script>
       // 显示注册表单，隐藏登录表单
        document.getElementById('show-register').addEventListener('click', function() {
            document.getElementById('login-form').style.display = 'none';
            document.getElementById('register-form').style.display = 'block';
        });
        // 显示登录表单，隐藏注册表单
        document.getElementById('show-login').addEventListener('click', function() {
            document.getElementById('register-form').style.display = 'none';
            document.getElementById('login-form').style.display = 'block';
        });
        
        // 登录按钮点击事件处理
        document.getElementById('login-btn').addEventListener('click', function() {
            // 获取用户名和密码输入值
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
             // 验证输入不为空
            if (!username || !password) {
                alert('请输入用户名和密码');
                return;
            }
            
            // 发送登录请求到服务器
            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'functions.php', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                 // 当请求完成且响应成功时
                if (xhr.readyState === 4 && xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    if (response.success) {
                        // 登录成功，保存用户信息并跳转到排行榜页面
                        localStorage.setItem('currentUser', JSON.stringify(response.user));
                        window.location.href = 'rank.php';
                    } else {
                         // 登录失败，显示错误信息
                        alert(response.message);
                    }
                }
            };
             // 发送包含登录信息的请求
            xhr.send(`action=login&username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`);
        });
        
        // 注册按钮点击事件处理
        document.getElementById('register-btn').addEventListener('click', function() {
            // 获取注册的用户名和密码
            const username = document.getElementById('reg-username').value;
            const password = document.getElementById('reg-password').value;
            // 验证输入不为空
            if (!username || !password) {
                alert('请输入用户名和密码');
                return;
            }
            
            // 发送注册请求到服务器
            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'functions.php', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    if (response.success) {
                         // 注册成功，提示并切换到登录表单
                        alert('注册成功，请登录');
                        document.getElementById('register-form').style.display = 'none';
                        document.getElementById('login-form').style.display = 'block';
                    } else {
                         // 注册失败，显示错误信息
                        alert(response.message);
                    }
                }
            };
             // 发送包含注册信息的请求
            xhr.send(`action=register&username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`);
        });
        
       // 游客登录按钮点击事件
        document.getElementById('guest-btn').addEventListener('click', function() {
            // 设置游客标识并跳转到排行榜页面
            localStorage.setItem('isGuest', 'true');
            localStorage.removeItem('currentUser');
            window.location.href = 'rank.php';
        });
    </script>
</body>
</html>
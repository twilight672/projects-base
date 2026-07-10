<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>五子棋游戏 - 排行榜</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <!-- 页面头部 - 用户信息和登出按钮 -->
        <div class="rank-header">
            <div class="user-info">
                <h2>欢迎，<span id="current-username">游客</span></h2>
                <span id="current-score"></span>
            </div>
            <button id="logout-btn" class="logout-btn">退出登录</button>
        </div>
          <!-- 排行榜容器 -->
        <div class="rank-container">
            <h2>排行榜</h2>
            <table class="rank-table">
                <thead>
                    <tr>
                        <th>排名</th>
                        <th>用户名</th>
                        <th>积分</th>
                        <th>已玩局数</th>
                        <th>胜率</th>
                    </tr>
                </thead>
                <tbody id="rank-list">
                    <!-- 排行榜数据将通过JavaScript动态加载 -->
                    <tr><td colspan="5" style="text-align: center; padding: 20px;">加载中...</td></tr>
                </tbody>
            </table>
        </div>
        <!-- 房间操作按钮 -->
        <div class="room-buttons">
            <button id="create-room-btn" class="create-room-btn">创建房间</button>
            <button id="join-room-btn" class="join-room-btn">加入房间</button>
        </div>
    </div>
    
    <script>
        // 检查用户登录状态
        function checkUserStatus() {
            // 从本地存储获取用户信息和游客标识
            const userData = localStorage.getItem('currentUser');
            const isGuest = localStorage.getItem('isGuest');
            
            if (userData) {
                // 已登录用户 - 显示用户名和积分
                const user = JSON.parse(userData);
                document.getElementById('current-username').textContent = user.username;
                document.getElementById('current-score').textContent = `积分: ${user.score}`;
                
                // 获取最新的用户数据
                const xhr = new XMLHttpRequest();
                xhr.open('POST', 'functions.php', true);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        const response = JSON.parse(xhr.responseText);
                        if (response.success && response.user) {
                             // 更新显示和本地存储的积分
                            document.getElementById('current-score').textContent = `积分: ${response.user.score}`;
                            // 更新本地存储的用户数据
                            localStorage.setItem('currentUser', JSON.stringify(response.user));
                        }
                    }
                };
                xhr.send('action=getCurrentUser');
            } else if (isGuest) {
                // 游客登录 - 显示游客标识
                document.getElementById('current-username').textContent = '游客';
                document.getElementById('current-score').textContent = '';
            } else {
                // 未登录，重定向到登录页面
                window.location.href = 'index.php';
            }
        }
        
        // 加载并显示排行榜数据
        function loadLeaderboard() {
            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'functions.php', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    const leaderboard = JSON.parse(xhr.responseText);
                    const rankList = document.getElementById('rank-list');
                    rankList.innerHTML = '';
                    // 获取当前登录用户名（用于高亮显示）
                    const userData = localStorage.getItem('currentUser');
                    const currentUsername = userData ? JSON.parse(userData).username : null;
                    
                    if (leaderboard.length === 0) {
                        // 无数据时显示提示
                        rankList.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 20px;">暂无排行数据</td></tr>';
                    } else {
                        // 遍历排行榜数据生成表格行
                        leaderboard.forEach(user => {
                            const row = document.createElement('tr');
                            // 如果是当前用户，添加高亮样式
                            if (user.username === currentUsername) {
                                row.classList.add('current-user');
                            }
                            // 计算胜率
                            const winRate = user.games_played > 0 ? Math.round((user.games_won / user.games_played) * 100) : 0;
                            
                            row.innerHTML = `
                                <td>${user.rank}</td>
                                <td>${user.username}</td>
                                <td>${user.score}</td>
                                <td>${user.games_played}</td>
                                <td>${winRate}%</td>
                            `;
                            rankList.appendChild(row);
                        });
                    }
                }
            };
            // 发送获取排行榜请求
            xhr.send('action=getLeaderboard');
        }
        
        // 创建房间
        document.getElementById('create-room-btn').addEventListener('click', function() {
            // 生成房间ID并保存到会话存储
            const roomId = 'room_' + Date.now();
            sessionStorage.setItem('roomId', roomId);
            sessionStorage.setItem('isHost', 'true');
            
            // 跳转到游戏页面
            window.location.href = 'game.php';
        });
        
        // 加入房间（随机匹配）
        document.getElementById('join-room-btn').addEventListener('click', function() {
            // 模拟检查是否有在线玩家
            // 在实际应用中，这里应该检查服务器上是否有等待的房间
            const hasWaitingRooms = false; // 暂时设置为false，实际应该从服务器获取
            
            if (hasWaitingRooms) {
                // 随机选择一个房间加入
                // 这里简化处理，实际应该从服务器获取可用房间列表
                const randomRoomId = 'room_' + Math.floor(Math.random() * 1000);
                sessionStorage.setItem('roomId', randomRoomId);
                sessionStorage.setItem('isHost', 'false');
                window.location.href = 'game.php';
            } else {
                alert('当前没有等待中的房间，请创建房间或稍后再试');
            }
        });
        
        // 退出登录
        document.getElementById('logout-btn').addEventListener('click', function() {
            // 清除本地存储的用户信息
            localStorage.removeItem('currentUser');
            localStorage.removeItem('isGuest');
            sessionStorage.clear();
            
            // 发送登出请求到服务器
            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'functions.php', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    window.location.href = 'index.php';
                }
            };
            xhr.send('action=logout');
        });
        
        // 页面加载时执行
        window.addEventListener('load', function() {
            checkUserStatus();
            loadLeaderboard();
            
            // 定时刷新排行榜
            setInterval(loadLeaderboard, 5000);
        });
    </script>
</body>
</html>
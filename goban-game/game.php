<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>五子棋游戏</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <div class="game-header">
            <h1>五子棋游戏</h1>
            <button id="exit-btn" class="logout-btn">退出房间</button>
        </div>
        
        <!-- 等待房间界面 -->
        <div id="waiting-room" class="waiting-room">
            <h2>等待其他玩家加入...</h2>
            <p>房间ID: <span id="room-id-display"></span></p>
            <p>等待时间: <span id="waiting-timer">8</span> 秒</p>
            <div class="piece-selection">
                <div class="piece-option black-option" data-piece="black"></div>
                <div class="piece-option white-option" data-piece="white"></div>
            </div>
            <p>选择您的棋子颜色（默认为黑色）</p>
        </div>
        
        <!-- 游戏界面（初始隐藏） -->
        <div id="game-interface" style="display: none;">
            <div class="game-container">
                <div class="board-container">
                    <div id="chessboard"></div>
                </div>
                
                <div class="game-info">
                    <div class="player-info">
                        <div class="player" id="player1">
                            <span>玩家1</span>
                            <div class="stone black-stone"></div>
                        </div>
                        <div class="player" id="player2">
                            <span>玩家2</span>
                            <div class="stone white-stone"></div>
                        </div>
                    </div>
                    
                    <div class="game-controls">
                        <button id="undo-btn">悔棋</button>
                        <button id="draw-btn">求和</button>
                        <button id="surrender-btn" class="surrender-btn">认输</button>
                    </div>
                    
                    <div id="game-status">
                        <p id="status-text">游戏进行中</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 确认对话框 -->
    <div id="confirm-modal" class="modal">
        <div class="modal-content">
            <p id="modal-message"></p>
            <div class="modal-buttons">
                <button id="modal-accept">接受</button>
                <button id="modal-reject">拒绝</button>
            </div>
        </div>
    </div>
    
    <!-- 胜利提示 -->
    <div id="victory-modal" class="victory-modal">
        <div class="victory-text" id="victory-text"></div>
        <div class="victory-stats" id="victory-stats"></div>
        <div class="modal-buttons">
            <button id="play-again-btn">再来一局</button>
            <button id="back-to-rank-btn">返回排行榜</button>
        </div>
    </div>
    
    <script>
        // 游戏状态变量
        const gameState = {
            board: Array(15).fill().map(() => Array(15).fill(null)), // 15x15棋盘
            currentPlayer: 'black', // 黑棋先行
            player1: { type: 'human', piece: 'black', name: '您' },
            player2: { type: 'ai', piece: 'white', name: 'AI' },
            currentTurn: 'player1',
            gameOver: false,
            moves: [], // 记录所有落子，用于悔棋
            aiUndoUsed: false, // AI对战时是否已经使用过悔棋
            roomId: sessionStorage.getItem('roomId') || 'room_' + Date.now(),
            isHost: sessionStorage.getItem('isHost') === 'true',
            hasOpponent: false,
            selectedPiece: 'black' // 主机选择的棋子颜色
        };
        
        // 初始化棋盘
        function initChessboard() {
            const board = document.getElementById('chessboard');
            board.innerHTML = '';
            
            // 创建棋盘网格线（已通过CSS实现）
            
            // 创建星位点
            const starPoints = [
                { x: 3, y: 3 }, { x: 3, y: 11 },
                { x: 7, y: 7 },
                { x: 11, y: 3 }, { x: 11, y: 11 }
            ];
            
            starPoints.forEach(point => {
                const star = document.createElement('div');
                star.classList.add('star-point');
                star.style.left = `${point.x * 40}px`;
                star.style.top = `${point.y * 40}px`;
                board.appendChild(star);
            });
            
            // 添加鼠标移动事件
            board.addEventListener('mousemove', handleMouseMove);
            // 添加点击事件
            board.addEventListener('click', handleBoardClick);
        }
        
        // 处理鼠标移动，显示虚影
        function handleMouseMove(e) {
            if (gameState.gameOver) return;
            
            // 检查是否轮到当前玩家
            const isMyTurn = (gameState.currentTurn === 'player1' && gameState.player1.type === 'human') ||
                            (gameState.currentTurn === 'player2' && gameState.player2.type === 'human');
            
            if (!isMyTurn) return;
            
            const board = document.getElementById('chessboard');
            const rect = board.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;
            
            // 计算最近的交叉点，确保边缘位置能被正确识别
            const gridX = Math.min(14, Math.max(0, Math.round((x - 20) / 40)));
            const gridY = Math.min(14, Math.max(0, Math.round((y - 20) / 40)));
            
            // 检查该位置是否已有棋子
            if (gameState.board[gridY][gridX] === null) {
                    // 显示虚影
                    let ghost = document.querySelector('.ghost-stone');
                    if (!ghost) {
                        ghost = document.createElement('div');
                        ghost.classList.add('stone', 'ghost-stone');
                        board.appendChild(ghost);
                    }
                    
                    const currentPiece = gameState.currentPlayer;
                    ghost.className = `stone ghost-stone ${currentPiece}-stone`;
                    ghost.style.left = `${gridX * 40}px`;
                    ghost.style.top = `${gridY * 40}px`;
                    ghost.style.display = 'block';
                    
                    // 存储当前悬停位置
                    ghost.dataset.x = gridX;
                    ghost.dataset.y = gridY;
                    return;
            }
            
            // 隐藏虚影
            const ghost = document.querySelector('.ghost-stone');
            if (ghost) {
                ghost.style.display = 'none';
            }
        }
        
        // 处理棋盘点击
        function handleBoardClick(e) {
            if (gameState.gameOver) return;
            
            // 检查是否轮到当前玩家
            const isMyTurn = (gameState.currentTurn === 'player1' && gameState.player1.type === 'human') ||
                            (gameState.currentTurn === 'player2' && gameState.player2.type === 'human');
            
            if (!isMyTurn) return;
            
            const board = document.getElementById('chessboard');
            const rect = board.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;
            
            // 直接计算点击位置对应的交叉点，并限制在有效范围内 (0到N-1，这里N=15)
            const gridX = Math.min(14, Math.max(0, Math.round((x - 20) / 40)));
            const gridY = Math.min(14, Math.max(0, Math.round((y - 20) / 40)));
            
            // 检查该位置是否已有棋子
            if (gameState.board[gridY][gridX] === null) {
                placeStone(gridX, gridY);
            }
        }
        
        // 放置棋子
        function placeStone(x, y) {
            // 检查位置是否有效
            if (gameState.board[y][x] !== null || gameState.gameOver) return;
            
            const currentPiece = gameState.currentPlayer;
            
            // 更新棋盘数据
            gameState.board[y][x] = currentPiece;
            gameState.moves.push({ x, y, piece: currentPiece });
            
            // 在界面上绘制棋子
            const board = document.getElementById('chessboard');
            const stone = document.createElement('div');
            stone.classList.add('stone', `${currentPiece}-stone`);
            stone.style.left = `${x * 40}px`;
            stone.style.top = `${y * 40}px`;
            board.appendChild(stone);
            
            // 隐藏虚影
            const ghost = document.querySelector('.ghost-stone');
            if (ghost) {
                ghost.style.display = 'none';
            }
            
            // 检查是否获胜
            if (checkWin(x, y, currentPiece)) {
                gameOver(`${currentPiece === 'black' ? gameState.player1.name : gameState.player2.name} 获胜！`);
                // 更新积分：基础分2分 + 获胜额外5分
                updateScore(2 + 5, 'win');
                return;
            }
            
            // 切换玩家
            gameState.currentPlayer = currentPiece === 'black' ? 'white' : 'black';
            gameState.currentTurn = gameState.currentTurn === 'player1' ? 'player2' : 'player1';
            updatePlayerIndicator();
            
            // 如果是AI回合，让AI下棋
            if ((gameState.currentTurn === 'player2' && gameState.player2.type === 'ai') && !gameState.gameOver) {
                setTimeout(makeAIMove, 500);
            }
        }
        
        // 检查是否获胜
        function checkWin(x, y, piece) {
            const directions = [
                [1, 0],  // 水平
                [0, 1],  // 垂直
                [1, 1],  // 对角线
                [1, -1]  // 反对角线
            ];
            
            for (const [dx, dy] of directions) {
                let count = 1; // 当前位置已经有一个棋子
                
                // 正方向检查
                for (let i = 1; i <= 4; i++) {
                    const nx = x + dx * i;
                    const ny = y + dy * i;
                    if (nx < 0 || nx >= 15 || ny < 0 || ny >= 15 || gameState.board[ny][nx] !== piece) {
                        break;
                    }
                    count++;
                }
                
                // 反方向检查
                for (let i = 1; i <= 4; i++) {
                    const nx = x - dx * i;
                    const ny = y - dy * i;
                    if (nx < 0 || nx >= 15 || ny < 0 || ny >= 15 || gameState.board[ny][nx] !== piece) {
                        break;
                    }
                    count++;
                }
                
                if (count >= 5) {
                    return true;
                }
            }
            
            return false;
        }
        
        // AI落子逻辑（简单实现）
        function makeAIMove() {
            // 简单的AI逻辑：优先防守，其次进攻
            let bestMove = null;
            let bestScore = -Infinity;
            
            // 遍历所有空位
            for (let y = 0; y < 15; y++) {
                for (let x = 0; x < 15; x++) {
                    if (gameState.board[y][x] === null) {
                        // 模拟AI落子（白色）
                        gameState.board[y][x] = 'white';
                        const aiScore = evaluatePosition(x, y, 'white');
                        gameState.board[y][x] = null;
                        
                        // 模拟玩家落子（黑色）
                        gameState.board[y][x] = 'black';
                        const humanScore = evaluatePosition(x, y, 'black');
                        gameState.board[y][x] = null;
                        
                        // 综合评分：防守比进攻更重要
                        const score = humanScore * 1.2 + aiScore;
                        
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = { x, y };
                        }
                    }
                }
            }
            
            // 如果没有找到最佳位置，随机选择一个
            if (!bestMove) {
                let emptyPositions = [];
                for (let y = 0; y < 15; y++) {
                    for (let x = 0; x < 15; x++) {
                        if (gameState.board[y][x] === null) {
                            emptyPositions.push({ x, y });
                        }
                    }
                }
                if (emptyPositions.length > 0) {
                    bestMove = emptyPositions[Math.floor(Math.random() * emptyPositions.length)];
                }
            }
            
            // 执行AI落子
            if (bestMove) {
                placeStone(bestMove.x, bestMove.y);
            }
        }
        
        // 评估位置的价值
        function evaluatePosition(x, y, piece) {
            const directions = [
                [1, 0],  // 水平
                [0, 1],  // 垂直
                [1, 1],  // 对角线
                [1, -1]  // 反对角线
            ];
            
            let maxScore = 0;
            
            for (const [dx, dy] of directions) {
                let count = 1;
                let blocked = 0;
                
                // 正方向
                for (let i = 1; i <= 4; i++) {
                    const nx = x + dx * i;
                    const ny = y + dy * i;
                    if (nx < 0 || nx >= 15 || ny < 0 || ny >= 15) {
                        blocked++;
                        break;
                    }
                    if (gameState.board[ny][nx] === piece) {
                        count++;
                    } else if (gameState.board[ny][nx] === null) {
                        break;
                    } else {
                        blocked++;
                        break;
                    }
                }
                
                // 反方向
                for (let i = 1; i <= 4; i++) {
                    const nx = x - dx * i;
                    const ny = y - dy * i;
                    if (nx < 0 || nx >= 15 || ny < 0 || ny >= 15) {
                        blocked++;
                        break;
                    }
                    if (gameState.board[ny][nx] === piece) {
                        count++;
                    } else if (gameState.board[ny][nx] === null) {
                        break;
                    } else {
                        blocked++;
                        break;
                    }
                }
                
                // 根据连续棋子数和阻塞情况评分
                let score = 0;
                if (count >= 5) {
                    score = 100000; // 五连子，必胜
                } else if (count === 4 && blocked === 0) {
                    score = 10000;  // 活四
                } else if (count === 4 && blocked === 1) {
                    score = 1000;   // 冲四
                } else if (count === 3 && blocked === 0) {
                    score = 1000;   // 活三
                } else if (count === 3 && blocked === 1) {
                    score = 100;    // 冲三
                } else if (count === 2 && blocked === 0) {
                    score = 100;    // 活二
                } else if (count === 2 && blocked === 1) {
                    score = 10;     // 冲二
                }
                
                maxScore = Math.max(maxScore, score);
            }
            
            return maxScore;
        }
        
        // 更新玩家指示器
        function updatePlayerIndicator() {
            const player1 = document.getElementById('player1');
            const player2 = document.getElementById('player2');
            
            if (gameState.currentTurn === 'player1') {
                player1.classList.add('current-player');
                player2.classList.remove('current-player');
            } else {
                player2.classList.add('current-player');
                player1.classList.remove('current-player');
            }
        }
        
        // 悔棋功能
        function undoMove() {
            // 如果是AI对战且已经使用过悔棋，则不允许再次悔棋
            if (gameState.player2.type === 'ai' && gameState.aiUndoUsed) {
                alert('AI对战时只能悔棋一次');
                return;
            }
            
            // 如果没有可悔的步数
            if (gameState.moves.length === 0) {
                alert('没有可悔的步数');
                return;
            }
            
            // 在实际对战中，这里应该向对手发送悔棋请求
            // 在AI对战中，直接执行悔棋
            if (gameState.player2.type === 'ai') {
                performUndo();
                gameState.aiUndoUsed = true;
                // 扣1分
                updateScore(-1, 'undo');
            } else {
                // 向对手发送悔棋请求
                showConfirmModal('对方请求悔棋，是否同意？', function() {
                    performUndo();
                    // 扣1分基础分
                    updateScore(-1, 'undo');
                });
            }
        }
        
        // 执行悔棋
        function performUndo() {
            // 撤销两步（自己和对手各一步）
            for (let i = 0; i < 2 && gameState.moves.length > 0; i++) {
                const lastMove = gameState.moves.pop();
                gameState.board[lastMove.y][lastMove.x] = null;
                
                // 更新界面
                const stones = document.querySelectorAll('.stone:not(.ghost-stone)');
                if (stones.length > 0) {
                    stones[stones.length - 1].remove();
                }
            }
            
            // 重置当前玩家
            gameState.currentPlayer = 'black';
            gameState.currentTurn = 'player1';
            gameState.gameOver = false;
            updatePlayerIndicator();
            document.getElementById('status-text').textContent = '游戏进行中';
        }
        
        // 求和功能
        function requestDraw() {
            if (gameState.player2.type === 'ai') {
                // AI对战时，总是同意求和
                gameOver('游戏结束，双方和棋');
                // 和棋：基础分2分 - 求和扣2分 = 0分
                updateScore(2 - 2, 'draw');
            } else {
                showConfirmModal('对方请求求和，是否同意？', function() {
                    gameOver('游戏结束，双方和棋');
                    // 和棋：基础分2分 - 求和扣2分 = 0分
                    updateScore(2 - 2, 'draw');
                });
            }
        }
        
        // 认输功能
        function surrender() {
            if (confirm('确定要认输吗？')) {
                const winner = gameState.currentTurn === 'player1' ? gameState.player2.name : gameState.player1.name;
                gameOver(`${winner} 获胜！`);
                // 获胜者获得：基础分2分 + 获胜额外5分
                const xhr = new XMLHttpRequest();
                xhr.open('POST', 'functions.php', true);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.send('action=updateScore&username=' + encodeURIComponent(winner) + '&scoreChange=7&gameResult=win');
                // 认输方只获得基础分2分
                updateScore(2, 'lose');
            }
        }
        
        // 游戏结束
        function gameOver(message) {
            gameState.gameOver = true;
            document.getElementById('status-text').textContent = message;
            
            // 显示胜利弹窗
            document.getElementById('victory-text').textContent = message;
            
            // 隐藏等待界面，显示游戏界面
            document.getElementById('waiting-room').style.display = 'none';
            document.getElementById('game-interface').style.display = 'block';
            
            // 显示胜利弹窗
            document.getElementById('victory-modal').style.display = 'flex';
        }
        
        // 显示确认对话框
        function showConfirmModal(message, onAccept) {
            const modal = document.getElementById('confirm-modal');
            document.getElementById('modal-message').textContent = message;
            modal.style.display = 'flex';
            
            document.getElementById('modal-accept').onclick = function() {
                modal.style.display = 'none';
                if (onAccept) onAccept();
            };
            
            document.getElementById('modal-reject').onclick = function() {
                modal.style.display = 'none';
            };
        }
        
        // 更新积分
        function updateScore(scoreChange, gameResult = null) {
            const userData = localStorage.getItem('currentUser');
            if (userData) {
                const user = JSON.parse(userData);
                
                const xhr = new XMLHttpRequest();
                xhr.open('POST', 'functions.php', true);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        // 更新本地存储的用户数据
                        user.score += scoreChange;
                        localStorage.setItem('currentUser', JSON.stringify(user));
                    }
                };
                xhr.send(`action=updateScore&username=${encodeURIComponent(user.username)}&scoreChange=${scoreChange}&gameResult=${gameResult || ''}`);
            }
        }
        
        // 初始化等待房间
        function initWaitingRoom() {
            document.getElementById('room-id-display').textContent = gameState.roomId;
            
            // 棋子选择
            const pieceOptions = document.querySelectorAll('.piece-option');
            pieceOptions.forEach(option => {
                option.addEventListener('click', function() {
                    pieceOptions.forEach(opt => opt.classList.remove('piece-selected'));
                    this.classList.add('piece-selected');
                    gameState.selectedPiece = this.dataset.piece;
                    gameState.player1.piece = this.dataset.piece;
                    gameState.player2.piece = this.dataset.piece === 'black' ? 'white' : 'black';
                });
            });
            
            // 默认选择黑色
            document.querySelector('.black-option').classList.add('piece-selected');
            
            // 倒计时
            let timer = 8;
            const timerElement = document.getElementById('waiting-timer');
            const countdown = setInterval(function() {
                timer--;
                timerElement.textContent = timer;
                
                if (timer <= 0) {
                    clearInterval(countdown);
                    // 8秒后开始AI对战
                    startGameWithAI();
                }
            }, 1000);
        }
        
        // 开始AI对战
        function startGameWithAI() {
            // 设置游戏状态
            gameState.hasOpponent = true;
            gameState.player2.name = 'AI';
            
            // 更新玩家信息显示
            document.getElementById('player1').querySelector('span').textContent = gameState.player1.name;
            document.getElementById('player2').querySelector('span').textContent = gameState.player2.name;
            
            // 初始化棋盘
            initChessboard();
            updatePlayerIndicator();
            
            // 隐藏等待界面，显示游戏界面
            document.getElementById('waiting-room').style.display = 'none';
            document.getElementById('game-interface').style.display = 'block';
        }
        
        // 事件监听
        document.getElementById('undo-btn').addEventListener('click', undoMove);
        document.getElementById('draw-btn').addEventListener('click', requestDraw);
        document.getElementById('surrender-btn').addEventListener('click', surrender);
        
        // 再来一局
        document.getElementById('play-again-btn').addEventListener('click', function() {
            document.getElementById('victory-modal').style.display = 'none';
            // 重置游戏状态
            gameState.board = Array(15).fill().map(() => Array(15).fill(null));
            gameState.currentPlayer = 'black';
            gameState.currentTurn = 'player1';
            gameState.gameOver = false;
            gameState.moves = [];
            gameState.aiUndoUsed = false;
            
            // 重新初始化棋盘
            initChessboard();
            updatePlayerIndicator();
            document.getElementById('status-text').textContent = '游戏进行中';
        });
        
        // 返回排行榜
        document.getElementById('back-to-rank-btn').addEventListener('click', function() {
            window.location.href = 'rank.php';
        });
        
        // 退出房间
        document.getElementById('exit-btn').addEventListener('click', function() {
            if (confirm('确定要退出房间吗？')) {
                window.location.href = 'rank.php';
            }
        });
        
        // 页面加载时初始化
        window.addEventListener('load', function() {
            // 检查是否有房间ID
            if (!gameState.roomId) {
                window.location.href = 'rank.php';
                return;
            }
            
            // 初始化等待房间
            initWaitingRoom();
            
            // 如果不是主机，尝试加入房间
            if (!gameState.isHost) {
                // 实际应用中，这里应该检查房间是否存在并加入
                // 这里简化处理，直接开始游戏
                setTimeout(startGameWithAI, 1000);
            }
        });
    </script>
</body>
</html>
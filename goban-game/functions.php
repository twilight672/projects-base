<?php
// 启用会话管理
session_start();

// 设置响应头为JSON格式
header('Content-Type: application/json; charset=UTF-8');

// 数据库文件路径
$db_file = 'users.db';

// 初始化数据库（如果不存在则创建）
function initDatabase() {
    global $db_file;
    
    if (!file_exists($db_file)) {
        // 初始数据库结构包含用户表和游戏表
        $data = [
            'users' => [],// 用户列表
            'games' => []// 游戏记录列表
        ];
        // 将初始数据写入数据库文件
        file_put_contents($db_file, json_encode($data));
    }
}

// 读取数据库
function readDatabase() {
    global $db_file;
    return json_decode(file_get_contents($db_file), true);
}

// 写入数据到数据库
function writeDatabase($data) {
    global $db_file;
    file_put_contents($db_file, json_encode($data));
}

// 用户注册功能
function registerUser($username, $password) {
    initDatabase();// 确保数据库已初始化
    $data = readDatabase();
    
    // 检查用户名是否已存在
    foreach ($data['users'] as $user) {
        if ($user['username'] === $username) {
            return ['success' => false, 'message' => '用户名已存在'];
        }
    }
    
    // 创建新用户
    $newUser = [
        'id' => uniqid(),// 生成唯一ID
        'username' => $username,
        'password' => password_hash($password, PASSWORD_DEFAULT),// 密码加密存储
        'score' => 0,// 初始积分
        'games_played' => 0,// 已玩局数
        'games_won' => 0,// 获胜局数
        'last_game' => null// 最后游戏时间
    ];
    // 添加新用户到数据库并保存
    $data['users'][] = $newUser;
    writeDatabase($data);
    
    return ['success' => true, 'message' => '注册成功'];
}

// 用户登录功能
function loginUser($username, $password) {
    initDatabase();
    $data = readDatabase();
     // 遍历用户列表验证登录信息
    foreach ($data['users'] as $user) {
        if ($user['username'] === $username && password_verify($password, $user['password'])) {
             // 登录成功，移除密码字段后返回用户信息
            unset($user['password']);
            $_SESSION['user'] = $user;// 保存用户信息到会话
            return ['success' => true, 'user' => $user];
        }
    }
    
    return ['success' => false, 'message' => '用户名或密码错误'];
}

// 获取排行榜
function getLeaderboard() {
    initDatabase();
    $data = readDatabase();
    
    // 按积分降序排序用户
    usort($data['users'], function($a, $b) {
        return $b['score'] - $a['score'];
    });
    
    // 提取前10名用户的排行榜信息
    $topUsers = [];
    foreach ($data['users'] as $index => $user) {
        $topUsers[] = [
            'rank' => $index + 1, // 排名
            'username' => $user['username'],// 用户名
            'score' => $user['score'],// 积分
            'games_played' => $user['games_played'],// 已玩局数
            'games_won' => $user['games_won']// 获胜局数
        ];
        ];
        if (count($topUsers) >= 10) break;// 只取前10名
    }
    
    return $topUsers;
}


// 更新用户积分
function updateUserScore($username, $scoreChange, $gameResult = null) {
    initDatabase();
    $data = readDatabase();
     // 查找并更新指定用户的信息
    foreach ($data['users'] as &$user) {// 使用引用修改原数组
        if ($user['username'] === $username) {
            $user['score'] += $scoreChange;// 更新积分
            $user['games_played']++;// 增加已玩局数
            if ($gameResult === 'win') {
                $user['games_won']++;// 如果获胜，增加获胜局数
            }
            $user['last_game'] = time();// 更新最后游戏时间
            break;
        }
    }
    
    writeDatabase($data);// 保存更新后的数据
}

// 获取当前用户信息
function getCurrentUser() {
    if (isset($_SESSION['user'])) {
        return $_SESSION['user'];
    }
    return null;
}

// 主处理逻辑 - 根据POST参数处理不同操作
if (isset($_POST['action'])) {
    $action = $_POST['action'];
    
    switch ($action) {
        case 'login':
         // 处理登录请求
            $username = $_POST['username'] ?? '';
            $password = $_POST['password'] ?? '';
            echo json_encode(loginUser($username, $password));
            break;
            
        case 'register':
        // 处理注册请求
            $username = $_POST['username'] ?? '';
            $password = $_POST['password'] ?? '';
            echo json_encode(registerUser($username, $password));
            break;
            
        case 'getLeaderboard':
        // 处理获取排行榜请求
            echo json_encode(getLeaderboard());
            break;
            
        case 'getCurrentUser':
        // 处理获取当前用户信息请求
            $user = getCurrentUser();
            echo json_encode(['success' => !!$user, 'user' => $user]);
            break;
            
        case 'logout':
        // 处理登出请求
            session_destroy();
            echo json_encode(['success' => true]);
            break;
            // 处理更新积分请求 
        case 'updateScore':
        // 获取请求参数（用户名、积分变化、游戏结果）
            $username = $_POST['username'] ?? '';
            $scoreChange = (int)($_POST['scoreChange'] ?? 0);// 转换为整数
            $gameResult = $_POST['gameResult'] ?? null;// 可选：'win'表示获胜
             // 调用更新积分函数
            updateUserScore($username, $scoreChange, $gameResult);
            // 返回成功响应
            echo json_encode(['success' => true]);
            break;
            
        default:
        // 未知操作
            echo json_encode(['success' => false, 'message' => '未知操作']);
    }
} else {
    // 未指定操作
    echo json_encode(['success' => false, 'message' => '无操作指定']);
}
?>
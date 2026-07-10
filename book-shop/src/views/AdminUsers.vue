<template>
  <!-- 用户账号管理页面：管理所有用户和管理员，支持搜索、筛选和权限设置 -->
  <div class="admin-page">
    <div class="admin-container">
      <!-- 左侧固定导航栏 -->
      <aside class="sidebar">
        <!-- Logo区域 -->
        <div class="sidebar-header">
          <div class="logo">
            <span class="logo-icon">⚙️</span>
            <span class="logo-text">管理后台</span>
          </div>
        </div>
        
        <!-- 导航菜单 -->
        <nav class="sidebar-nav">
          <!-- 仪表盘入口 -->
          <router-link to="/admin/dashboard" class="nav-item" :class="{ active: $route.path === '/admin/dashboard' }">
            <span class="nav-icon">📊</span>
            <span class="nav-text">仪表盘</span>
          </router-link>
          <!-- 书籍管理入口 -->
          <router-link to="/admin/books" class="nav-item" :class="{ active: $route.path === '/admin/books' }">
            <span class="nav-icon">📚</span>
            <span class="nav-text">书籍管理</span>
          </router-link>
          <!-- 账号管理入口（当前页面） -->
          <router-link to="/admin/users" class="nav-item" :class="{ active: $route.path === '/admin/users' }">
            <span class="nav-icon">👥</span>
            <span class="nav-text">账号管理</span>
          </router-link>
        </nav>
        
        <!-- 底部退出按钮 -->
        <div class="sidebar-footer">
          <button class="logout-btn" @click="handleLogout">
            <span class="logout-icon">🚪</span>
            <span>退出登录</span>
          </button>
        </div>
      </aside>
      
      <!-- 右侧主内容区域 -->
      <main class="main-content">
        <!-- 顶部标题栏：包含页面标题、搜索框、添加按钮和管理员信息 -->
        <header class="main-header">
          <div class="header-left">
            <h1>{{ pageTitle }}</h1>
            <p class="header-subtitle">共 {{ users.length }} 个用户，{{ admins.length }} 个管理员</p>
          </div>
          <div class="header-right">
            <!-- 搜索框：支持按用户名搜索 -->
            <div class="search-bar">
              <input type="text" v-model="searchKeyword" placeholder="搜索用户名..." class="search-input" />
              <span class="search-icon">🔍</span>
            </div>
            <!-- 添加管理员按钮 -->
            <button class="add-btn" @click="showAddAdminModal = true">
              <span class="add-icon">➕</span>
              <span>添加管理员</span>
            </button>
            <!-- 当前登录管理员信息 -->
            <div class="admin-info">
              <span class="admin-icon">👑</span>
              <span class="admin-name">{{ currentAdmin.userName }}</span>
            </div>
          </div>
        </header>
        
        <!-- 内容区域 -->
        <div class="content-wrapper">
          <!-- 用户类型筛选按钮组：全部/管理员/普通用户 -->
          <div class="filter-bar">
            <button class="filter-btn" :class="{ active: filterType === 'all' }" @click="filterType = 'all'">
              全部用户 ({{ users.length }})
            </button>
            <button class="filter-btn" :class="{ active: filterType === 'admin' }" @click="filterType = 'admin'">
              管理员 ({{ admins.length }})
            </button>
            <button class="filter-btn" :class="{ active: filterType === 'normal' }" @click="filterType = 'normal'">
              普通用户 ({{ normalUsersCount }})
            </button>
          </div>
          
          <!-- 用户列表表格 -->
          <div class="users-table">
            <table>
              <!-- 表头 -->
              <thead>
                <tr>
                  <th>用户名</th>
                  <th>邮箱</th>
                  <th>注册时间</th>
                  <th>角色</th>
                  <th>操作</th>
                </tr>
              </thead>
              <!-- 表体：遍历显示所有符合条件的用户 -->
              <tbody>
                <tr v-for="user in filteredUsers" :key="user.id">
                  <!-- 用户名（带图标） -->
                  <td>
                    <div class="user-info">
                      <span class="user-icon">👤</span>
                      <span class="user-name">{{ user.userName }}</span>
                    </div>
                  </td>
                  <!-- 邮箱 -->
                  <td>{{ user.email }}</td>
                  <!-- 注册时间（格式化显示） -->
                  <td>{{ formatTime(user.createdAt) }}</td>
                  <!-- 角色：管理员或普通用户，不同颜色标签 -->
                  <td>
                    <span class="role-badge" :class="isAdmin(user.userName) ? 'admin' : 'normal'">
                      {{ isAdmin(user.userName) ? '管理员' : '普通用户' }}
                    </span>
                  </td>
                  <!-- 操作按钮：设为管理员/移除管理员 -->
                  <td>
                    <div class="action-buttons">
                      <!-- 普通用户可以升级为管理员 -->
                      <button v-if="!isAdmin(user.userName)" class="action-btn promote-btn" @click="openAddAdminModal(user)">
                        设为管理员
                      </button>
                      <!-- 管理员可以移除权限（但不能移除超级管理员admin） -->
                      <button v-if="isAdmin(user.userName) && user.userName !== 'admin'" class="action-btn demote-btn" @click="handleRemoveAdmin(user.userName)">
                        移除管理员
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          
          <!-- 空状态提示：当没有符合条件的用户时显示 -->
          <div v-if="filteredUsers.length === 0" class="empty-state">
            <span class="empty-icon">👥</span>
            <p>暂无用户</p>
          </div>
        </div>
      </main>
    </div>
    
    <!-- 添加管理员弹窗 -->
    <div v-if="showAddAdminModal" class="modal-overlay" @click.self="closeAddAdminModal">
      <div class="modal-content">
        <div class="modal-header">
          <h2>添加管理员</h2>
          <button class="close-btn" @click="closeAddAdminModal">✕</button>
        </div>
        <!-- 添加管理员表单 -->
        <form @submit.prevent="handleAddAdmin" class="modal-form">
          <!-- 选择用户下拉框 -->
          <div class="form-group">
            <label>选择用户 *</label>
            <select v-model="selectedUser" class="form-select" required>
              <option value="" disabled>请选择要升级为管理员的用户</option>
              <!-- 只显示普通用户，不显示已有的管理员 -->
              <option v-for="user in normalUsers" :key="user.id" :value="user.userName">
                {{ user.userName }} ({{ user.email }})
              </option>
            </select>
          </div>
          <!-- 确认密码输入框：需要验证用户密码才能升级 -->
          <div class="form-group">
            <label>确认密码 *</label>
            <input type="password" v-model="confirmPassword" class="form-input" placeholder="请输入该用户的密码以确认" required />
          </div>
          <!-- 错误提示信息 -->
          <div v-if="addAdminError" class="error-message">{{ addAdminError }}</div>
          <button type="submit" class="submit-btn">确认添加</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
/**
 * 用户账号管理页面组件
 * 功能：管理所有用户和管理员，支持搜索、筛选、设置管理员权限
 */
import { getCurrentAdmin, adminLogout, getUsers, getAdmins, addAdmin, removeAdmin } from '@/utils/storage'

export default {
  name: 'AdminUsers',
  
  // 数据定义
  data() {
    return {
      currentAdmin: {},           // 当前登录管理员信息
      users: [],                  // 所有用户列表
      admins: [],                 // 管理员列表
      searchKeyword: '',          // 搜索关键词
      filterType: 'all',          // 筛选类型：all全部 / admin管理员 / normal普通用户
      showAddAdminModal: false,   // 是否显示添加管理员弹窗
      selectedUser: '',           // 当前选中的用户名（用于添加管理员）
      confirmPassword: '',        // 确认密码（添加管理员时需要验证）
      addAdminError: ''           // 添加管理员时的错误信息
    }
  },
  
  // 计算属性：根据现有数据派生出的值
  computed: {
    // 页面标题
    pageTitle() { 
      return '账号管理' 
    },
    
    // 获取普通用户列表：排除掉已有的管理员
    normalUsers() {
      const adminNames = this.admins.map(a => a.userName)
      return this.users.filter(u => !adminNames.includes(u.userName))
    },
    
    // 普通用户数量
    normalUsersCount() { 
      return this.normalUsers.length 
    },
    
    // 根据搜索关键词和筛选类型过滤用户列表
    filteredUsers() {
      let result = this.users
      
      // 按用户名搜索（不区分大小写）
      if (this.searchKeyword) {
        const keyword = this.searchKeyword.toLowerCase()
        result = result.filter(u => u.userName.toLowerCase().includes(keyword))
      }
      
      // 按角色筛选
      if (this.filterType === 'admin') {
        const adminNames = this.admins.map(a => a.userName)
        result = result.filter(u => adminNames.includes(u.userName))
      } else if (this.filterType === 'normal') {
        const adminNames = this.admins.map(a => a.userName)
        result = result.filter(u => !adminNames.includes(u.userName))
      }
      
      return result
    }
  },
  
  // 组件挂载后初始化数据
  mounted() {
    this.currentAdmin = getCurrentAdmin()
    this.loadData()
  },
  
  methods: {
    // 加载用户和管理员数据
    loadData() {
      this.users = getUsers()
      this.admins = getAdmins()
    },
    
    // 判断用户是否为管理员
    isAdmin(userName) { 
      return this.admins.some(a => a.userName === userName) 
    },
    
    // 格式化时间戳为本地日期字符串
    formatTime(timestamp) {
      if (!timestamp) return '-'
      const date = new Date(timestamp)
      return date.toLocaleString('zh-CN')
    },
    
    // 打开添加管理员弹窗：可以传入预设的用户
    openAddAdminModal(user) {
      if (user) this.selectedUser = user.userName
      this.showAddAdminModal = true
      this.addAdminError = ''
    },
    
    // 关闭添加管理员弹窗并重置表单
    closeAddAdminModal() {
      this.showAddAdminModal = false
      this.selectedUser = ''
      this.confirmPassword = ''
      this.addAdminError = ''
    },
    
    // 处理添加管理员：验证密码后添加
    handleAddAdmin() {
      const result = addAdmin(this.selectedUser, this.confirmPassword)
      if (result.success) {
        this.loadData()
        this.closeAddAdminModal()
      } else {
        this.addAdminError = result.message
      }
    },
    
    // 处理移除管理员权限
    handleRemoveAdmin(userName) {
      const result = removeAdmin(userName)
      if (result.success) {
        this.loadData()
      }
    },
    
    // 处理退出登录
    handleLogout() {
      adminLogout()
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
/* ===== 页面整体布局 ===== */
/* 页面容器：全屏高度，灰色背景 */
.admin-page {
  min-height: 100vh;
  background: var(--bg-page);
}
/* 管理后台容器：左右分栏布局 */
.admin-container {
  display: flex;
  height: 100vh;
}

/* ===== 左侧侧边栏 ===== */
/* 固定定位，白色背景，带阴影 */
.sidebar {
  width: 240px;
  background: white;
  border-right: 1px solid var(--border-color);
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 0;
  top: 0;
  bottom: 0;
  overflow-y: auto;
  z-index: 100;
  box-shadow: var(--shadow-xs);
}
/* Logo区域：顶部内边距，底部分隔线 */
.sidebar-header {
  padding: 28px 20px 24px;
  border-bottom: 1px solid var(--border-color);
}
.sidebar-header .logo {
  display: flex;
  align-items: center;
  gap: 12px;
}
.sidebar-header .logo-icon {
  font-size: 28px;
}
.sidebar-header .logo-text {
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
}
.sidebar-header .logo-text span {
  color: var(--primary-color);
}

/* 导航菜单区域 */
.sidebar-nav {
  flex: 1;
  padding: 16px 12px;
}
/* 单个导航项：带图标和文字，hover时变色 */
.nav-item {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 12px 18px;
  color: var(--text-muted);
  text-decoration: none;
  border-radius: var(--radius-sm);
  transition: all var(--transition-normal);
  margin-bottom: 2px;
  font-weight: 500;
}
.nav-item:hover {
  background: var(--bg-card-hover);
  color: var(--text-primary);
}
/* 当前激活的导航项：蓝色背景高亮 */
.nav-item.active {
  background: var(--info-bg);
  color: var(--primary-color);
  font-weight: 600;
}
.nav-icon { font-size: 18px; width: 28px; text-align: center; }
.nav-text { font-size: 14px; }

/* 侧边栏底部：退出按钮区域 */
.sidebar-footer {
  padding: 16px 12px 20px;
  border-top: 1px solid var(--border-color);
}
/* 退出按钮：红色背景，居中显示 */
.logout-btn {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 12px;
  background: var(--danger-bg);
  border: 1px solid transparent;
  border-radius: var(--radius-sm);
  color: var(--danger-color);
  cursor: pointer;
  transition: all var(--transition-normal);
  font-size: 14px;
  font-weight: 500;
}
.logout-btn:hover {
  background: #fcd5d5;
  border-color: var(--danger-color);
}

/* ===== 右侧主内容区域 ===== */
/* 占据剩余宽度，左侧留出侧边栏空间 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  margin-left: 240px;
  min-height: 100vh;
  background: var(--bg-page);
}

/* ===== 顶部标题栏 ===== */
/* 固定在顶部，白色背景，包含标题、搜索、按钮 */
.main-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 32px;
  background: white;
  border-bottom: 1px solid var(--border-color);
  flex-wrap: wrap;
  gap: 16px;
  position: sticky;
  top: 0;
  z-index: 50;
}
.header-left h1 {
  font-size: 22px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 2px;
}
.header-subtitle {
  font-size: 14px;
  color: var(--text-muted);
}

/* 右侧工具栏：搜索框、添加按钮、管理员信息 */
.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-wrap: wrap;
}
/* 搜索框容器 */
.search-bar {
  position: relative;
}
/* 搜索输入框：圆角，聚焦时边框变色并放大 */
.search-input {
  padding: 10px 44px 10px 18px;
  background: var(--bg-input);
  border: 1.5px solid transparent;
  border-radius: var(--radius-full);
  color: var(--text-primary);
  font-size: 14px;
  width: 240px;
  transition: all var(--transition-normal);
  outline: none;
}
.search-input:focus {
  border-color: var(--primary-color);
  background: white;
  box-shadow: 0 0 0 4px rgba(79, 140, 247, 0.08);
  width: 280px;
}
.search-input::placeholder { color: var(--text-muted); }
/* 搜索图标：定位在输入框右侧 */
.search-icon {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: var(--text-muted);
  font-size: 16px;
}

/* 添加管理员按钮：蓝色渐变背景，带阴影 */
.add-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 24px;
  background: var(--primary-gradient);
  border: none;
  border-radius: var(--radius-full);
  color: white;
  font-weight: 600;
  font-size: 14px;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
}
.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}

/* 当前管理员信息显示 */
.admin-info {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 6px 16px 6px 12px;
  background: var(--info-bg);
  border-radius: var(--radius-full);
}
.admin-icon { font-size: 16px; }
.admin-name {
  color: var(--primary-color);
  font-weight: 600;
  font-size: 14px;
}

/* ===== 内容区域 ===== */
.content-wrapper {
  padding: 28px 32px;
  flex: 1;
}

/* ===== 用户类型筛选栏 ===== */
/* 横向排列的筛选按钮组 */
.filter-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 28px;
  flex-wrap: wrap;
}
/* 单个筛选按钮：圆角边框样式 */
.filter-btn {
  padding: 9px 22px;
  background: white;
  border: 1.5px solid var(--border-color);
  border-radius: var(--radius-full);
  color: var(--text-secondary);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all var(--transition-normal);
}
.filter-btn:hover {
  border-color: var(--primary-color);
  color: var(--primary-color);
}
/* 当前激活的筛选按钮：蓝色渐变背景 */
.filter-btn.active {
  background: var(--primary-gradient);
  color: white;
  border-color: transparent;
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
}

/* ===== 用户列表表格 ===== */
/* 表格容器：带边框和阴影 */
.users-table {
  overflow-x: auto;
  border-radius: var(--radius-md);
  border: 1px solid var(--border-color);
  background: white;
  box-shadow: var(--shadow-xs);
}
.users-table table {
  width: 100%;
  border-collapse: collapse;
}
/* 表格单元格样式 */
.users-table th,
.users-table td {
  padding: 14px 20px;
  text-align: left;
  border-bottom: 1px solid var(--border-color);
  color: var(--text-primary);
}
/* 表头：浅蓝色背景，加粗字体 */
.users-table th {
  background: #f8faff;
  color: var(--text-secondary);
  font-weight: 600;
  font-size: 13px;
  letter-spacing: 0.3px;
}
/* 最后一行无底边框 */
.users-table tr:last-child td {
  border-bottom: none;
}
/* 行hover效果：浅灰色背景 */
.users-table tr:hover td {
  background: var(--bg-card-hover);
}

/* 用户名显示：带头像图标 */
.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}
/* 用户图标：圆形背景 */
.user-icon {
  font-size: 20px;
  width: 34px;
  height: 34px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--info-bg);
  border-radius: 50%;
}
.user-name {
  color: var(--text-primary);
  font-weight: 500;
}

/* 角色标签：圆角胶囊样式 */
.role-badge {
  padding: 4px 16px;
  border-radius: var(--radius-full);
  font-size: 12px;
  font-weight: 600;
}
/* 管理员标签：绿色背景 */
.role-badge.admin {
  background: var(--success-bg);
  color: var(--success-color);
}
/* 普通用户标签：灰色背景 */
.role-badge.normal {
  background: var(--bg-input);
  color: var(--text-muted);
}

/* 操作按钮区域 */
.action-buttons {
  display: flex;
  gap: 8px;
}
/* 操作按钮通用样式 */
.action-btn {
  padding: 6px 16px;
  border: none;
  border-radius: var(--radius-sm);
  font-size: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
}
/* 设为管理员按钮：绿色背景 */
.promote-btn {
  background: var(--success-bg);
  color: var(--success-color);
}
.promote-btn:hover {
  background: #c8f0df;
}
/* 移除管理员按钮：红色背景 */
.demote-btn {
  background: var(--danger-bg);
  color: var(--danger-color);
}
.demote-btn:hover {
  background: #fcd5d5;
}

/* ===== 空状态提示 ===== */
.empty-state {
  text-align: center;
  padding: 80px 20px;
}
.empty-icon {
  font-size: 64px;
  display: block;
  margin-bottom: 16px;
  opacity: 0.5;
}
.empty-state p {
  color: var(--text-muted);
  font-size: 16px;
}

/* ===== 弹窗样式 ===== */
/* 弹窗遮罩层：半透明背景，居中显示内容 */
.modal-overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0, 0, 0, 0.35);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeInUp 0.3s ease;
}
/* 弹窗内容区：白色背景，圆角，带动画 */
.modal-content {
  background: white;
  border-radius: var(--radius-lg);
  padding: 32px 36px;
  width: 90%;
  max-width: 450px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: var(--shadow-xl);
  animation: scaleIn 0.3s ease;
}

/* 弹窗头部：标题和关闭按钮 */
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}
.modal-header h2 {
  font-size: 22px;
  font-weight: 700;
  color: var(--text-primary);
}
.close-btn {
  background: none;
  border: none;
  color: var(--text-muted);
  font-size: 24px;
  cursor: pointer;
  transition: var(--transition-normal);
  padding: 4px 8px;
  border-radius: var(--radius-sm);
}
.close-btn:hover {
  background: var(--bg-input);
  color: var(--text-primary);
}

/* 弹窗表单 */
.modal-form {
  display: flex;
  flex-direction: column;
  gap: 18px;
}
/* 表单项组 */
.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}
.form-group label {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary);
}
/* 输入框、下拉框通用样式 */
.form-input,
.form-select {
  padding: 12px 16px;
  background: var(--bg-input);
  border: 1.5px solid transparent;
  border-radius: var(--radius-sm);
  color: var(--text-primary);
  font-size: 14px;
  transition: all var(--transition-normal);
  outline: none;
  width: 100%;
}
/* 聚焦状态：边框变蓝，背景变白 */
.form-input:focus,
.form-select:focus {
  border-color: var(--primary-color);
  background: white;
  box-shadow: 0 0 0 4px rgba(79, 140, 247, 0.08);
}
/* 下拉框：自定义下拉箭头 */
.form-select {
  cursor: pointer;
  appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='8' viewBox='0 0 12 8'%3E%3Cpath d='M1 1l5 5 5-5' stroke='%238a94a6' stroke-width='1.5' fill='none' stroke-linecap='round'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 14px center;
}
.form-select option {
  background: white;
  color: var(--text-primary);
}

/* 错误提示信息：红色背景 */
.error-message {
  padding: 12px 16px;
  background: var(--danger-bg);
  border-radius: var(--radius-sm);
  color: var(--danger-color);
  font-size: 14px;
  border: 1px solid #fcd5d5;
}

/* 提交按钮：蓝色渐变，带阴影和hover上浮效果 */
.submit-btn {
  padding: 14px;
  background: var(--primary-gradient);
  border: none;
  border-radius: var(--radius-sm);
  color: white;
  font-weight: 600;
  font-size: 16px;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
  margin-top: 6px;
}
.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}
</style>
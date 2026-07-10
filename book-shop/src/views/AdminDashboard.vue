<template>
  <!-- 管理后台仪表盘主组件 -->
  <div class="admin-page">
    <div class="admin-container">
      <!-- 侧边栏导航 -->
      <aside class="sidebar">
        <!-- 侧边栏头部（Logo区域） -->
        <div class="sidebar-header">
          <div class="logo">
            <span class="logo-icon">⚙️</span>
            <span class="logo-text">管理后台</span>
          </div>
        </div>
        
        <!-- 侧边栏导航菜单 -->
        <nav class="sidebar-nav">
          <!-- 仪表盘导航链接 -->
          <router-link 
            to="/admin/dashboard" 
            class="nav-item" 
            :class="{ active: $route.path === '/admin/dashboard' }"
          >
            <span class="nav-icon">📊</span>
            <span class="nav-text">仪表盘</span>
          </router-link>
          <!-- 书籍管理导航链接 -->
          <router-link 
            to="/admin/books" 
            class="nav-item" 
            :class="{ active: $route.path === '/admin/books' }"
          >
            <span class="nav-icon">📚</span>
            <span class="nav-text">书籍管理</span>
          </router-link>
          <!-- 账号管理导航链接 -->
          <router-link 
            to="/admin/users" 
            class="nav-item" 
            :class="{ active: $route.path === '/admin/users' }"
          >
            <span class="nav-icon">👥</span>
            <span class="nav-text">账号管理</span>
          </router-link>
        </nav>
        
        <!-- 侧边栏底部（退出按钮） -->
        <div class="sidebar-footer">
          <button class="logout-btn" @click="handleLogout">
            <span class="logout-icon">🚪</span>
            <span>退出登录</span>
          </button>
        </div>
      </aside>
      
      <!-- 主内容区域 -->
      <main class="main-content">
        <!-- 主头部（页面标题和管理员信息） -->
        <header class="main-header">
          <div class="header-left">
            <h1>{{ pageTitle }}</h1>
          </div>
          <div class="header-right">
            <div class="admin-info">
              <span class="admin-icon">👑</span>
              <span class="admin-name">{{ currentAdmin.userName }}</span>
            </div>
          </div>
        </header>
        
        <!-- 内容包裹层 -->
        <div class="content-wrapper">
          <!-- 统计卡片网格 -->
          <div class="stats-grid">
            <!-- 书籍总量统计卡片 -->
            <div class="stat-card">
              <div class="stat-icon books-icon">📚</div>
              <div class="stat-info">
                <p class="stat-value">{{ stats.totalBooks }}</p>
                <p class="stat-label">书籍总量</p>
              </div>
            </div>
            <!-- 总浏览量统计卡片 -->
            <div class="stat-card">
              <div class="stat-icon views-icon">📊</div>
              <div class="stat-info">
                <p class="stat-value">{{ stats.totalViews }}</p>
                <p class="stat-label">总浏览量</p>
              </div>
            </div>
            <!-- 总点击量统计卡片 -->
            <div class="stat-card">
              <div class="stat-icon views-icon">👆</div>
              <div class="stat-info">
                <p class="stat-value">{{ stats.totalClicks }}</p>
                <p class="stat-label">总点击量</p>
              </div>
            </div>
            <!-- 总收藏量统计卡片 -->
            <div class="stat-card">
              <div class="stat-icon favorites-icon">❤️</div>
              <div class="stat-info">
                <p class="stat-value">{{ stats.totalFavorites }}</p>
                <p class="stat-label">总收藏量</p>
              </div>
            </div>
            <!-- 总购买量统计卡片 -->
            <div class="stat-card">
              <div class="stat-icon orders-icon">🛒</div>
              <div class="stat-info">
                <p class="stat-value">{{ stats.totalOrders }}</p>
                <p class="stat-label">总购买量</p>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
// 管理后台仪表盘页面组件
import { getCurrentAdmin, adminLogout } from '@/utils/storage'
import { allBooks, getTotalViews, getTotalClicks } from '@/mock/bookData'

export default {
  name: 'AdminDashboard',
  
  data() {
    return {
      // 当前登录管理员信息
      currentAdmin: {},
      // 统计数据对象
      stats: {
        totalBooks: 0,      // 书籍总量
        totalViews: 0,      // 总浏览量
        totalClicks: 0,     // 总点击量
        totalFavorites: 0,  // 总收藏量
        totalOrders: 0      // 总购买量
      }
    }
  },
  
  computed: {
    // 页面标题
    pageTitle() {
      return '仪表盘'
    }
  },
  
  // 组件挂载后加载数据
  mounted() {
    this.currentAdmin = getCurrentAdmin()
    this.loadStats()
  },
  
  methods: {
    // 加载统计数据
    loadStats() {
      // 统计上架中的书籍数量
      const books = allBooks.filter(b => b.status !== 'offline')
      this.stats.totalBooks = books.length
      
      // 获取浏览量和点击量
      this.stats.totalViews = getTotalViews()
      this.stats.totalClicks = getTotalClicks()
      
      // 统计所有用户的收藏总量
      const allFavorites = []
      const users = JSON.parse(localStorage.getItem('users') || '[]')
      users.forEach(user => {
        const favKey = `favorites_${user.userName}`
        const favs = JSON.parse(localStorage.getItem(favKey) || '[]')
        allFavorites.push(...favs)
      })
      this.stats.totalFavorites = allFavorites.length
      
      // 统计所有用户的订单总量
      let totalOrders = 0
      users.forEach(user => {
        const orderKey = `orders_${user.userName}`
        const orders = JSON.parse(localStorage.getItem(orderKey) || '[]')
        totalOrders += orders.length
      })
      this.stats.totalOrders = totalOrders
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
.admin-page {
  min-height: 100vh;
  background: var(--bg-page);
}
.admin-container {
  display: flex;
  min-height: 100vh;
}

/* ===== 侧边栏 (同 AdminBooks) ===== */
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
.sidebar-nav {
  flex: 1;
  padding: 16px 12px;
}
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
.nav-item.active {
  background: var(--info-bg);
  color: var(--primary-color);
  font-weight: 600;
}
.nav-icon { font-size: 18px; width: 28px; text-align: center; }
.nav-text { font-size: 14px; }
.sidebar-footer {
  padding: 16px 12px 20px;
  border-top: 1px solid var(--border-color);
}
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

/* ===== 主内容 ===== */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  margin-left: 240px;
  min-height: 100vh;
  background: var(--bg-page);
}
.main-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 32px;
  background: white;
  border-bottom: 1px solid var(--border-color);
  position: sticky;
  top: 0;
  z-index: 50;
}
.header-left h1 {
  font-size: 22px;
  font-weight: 700;
  color: var(--text-primary);
}
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

.content-wrapper {
  padding: 32px;
  flex: 1;
}

/* ===== 统计卡片 ===== */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 18px;
  padding: 24px 26px;
  background: white;
  border-radius: var(--radius-md);
  border: 1px solid var(--border-color);
  transition: all var(--transition-normal);
  box-shadow: var(--shadow-xs);
}
.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-lg);
  border-color: var(--primary-light);
}

.stat-icon {
  width: 52px;
  height: 52px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: var(--radius-md);
  font-size: 24px;
  flex-shrink: 0;
}
.books-icon {
  background: var(--info-bg);
}
.views-icon {
  background: #f0e8fd;
}
.favorites-icon {
  background: var(--danger-bg);
}
.orders-icon {
  background: var(--success-bg);
}

.stat-info {
  flex: 1;
}
.stat-value {
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 2px;
  letter-spacing: -0.5px;
}
.stat-value.books-value { color: var(--primary-color); }
.stat-value.views-value { color: #7c6df0; }
.stat-value.favorites-value { color: var(--danger-color); }
.stat-value.orders-value { color: var(--success-color); }

.stat-label {
  font-size: 14px;
  color: var(--text-muted);
  font-weight: 500;
}

/* ===== 快捷操作 ===== */
.action-buttons {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
  margin-top: 10px;
}
.action-btn {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 14px 28px;
  background: white;
  border: 1px solid var(--border-color);
  border-radius: var(--radius-sm);
  color: var(--text-primary);
  text-decoration: none;
  font-weight: 500;
  transition: all var(--transition-normal);
  box-shadow: var(--shadow-xs);
}
.action-btn:hover {
  transform: translateY(-3px);
  border-color: var(--primary-color);
  box-shadow: var(--shadow-md);
  background: var(--bg-card-hover);
}
.btn-icon {
  font-size: 20px;
}
</style>
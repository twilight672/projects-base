<template>
  <!--
    Profile.vue - 个人中心页组件
    功能：
      ① 展示用户信息：头像、用户名、ID、加入时间
      ② 统计数据展示：收藏数、购物车数量、评论数
      ③ 快速导航菜单：订单、收藏、评论等
      ④ 子模块切换：订单、收藏、评论组件动态显示
      ⑤ 设置入口：账号设置、消息通知等
      ⑥ 退出登录功能
      ⑦ 未登录提示：引导用户登录/注册
    布局：
      - 顶部用户卡片（渐变背景）
      - 统计卡片行（可点击跳转）
      - 快速导航网格
      - 子内容区（动态组件）
      - 设置列表
      - 退出按钮
  -->
  <div class="profile-page">
    <!-- 背景网格装饰 -->
    <div class="bg-grid"></div>

    <div class="profile-container">
      <!-- 用户信息头部区域 -->
      <div class="profile-header">
        <div class="header-bg"></div>
        <!-- 用户卡片：头像 + 基本信息 -->
        <div class="user-card">
          <div class="avatar-wrapper">
            <div class="avatar">
              <span class="avatar-icon">👤</span>
            </div>
            <!-- 在线状态徽章 -->
            <div class="avatar-badge"></div>
          </div>
          <div class="user-info">
            <h2 class="username">{{ user && user.userName ? user.userName : '用户' }}</h2>
            <p class="user-id">ID: {{ user && user.id ? user.id : '000000' }}</p>
            <p class="join-date">加入时间: {{ joinDate }}</p>
          </div>
        </div>
      </div>

      <!-- 统计卡片：收藏、购物车、评论数量，点击可跳转 -->
      <div class="stats-row">
        <!-- 收藏统计卡片 -->
        <div class="stat-card" @click="switchTab('favorites')">
          <div class="stat-icon">📚</div>
          <div class="stat-info">
            <span class="stat-value">{{ stats.favorites }}</span>
            <span class="stat-label">我的收藏</span>
          </div>
        </div>
        <!-- 购物车统计卡片 -->
        <div class="stat-card" @click="navigateTo('/cart')" style="cursor: pointer;">
          <div class="stat-icon">🛒</div>
          <div class="stat-info">
            <span class="stat-value">{{ stats.cartCount }}</span>
            <span class="stat-label">我的购物车</span>
          </div>
        </div>
        <!-- 评论统计卡片 -->
        <div class="stat-card" @click="switchTab('comments')">
          <div class="stat-icon">💬</div>
          <div class="stat-info">
            <span class="stat-value">{{ stats.comments }}</span>
            <span class="stat-label">我的评论</span>
          </div>
        </div>
      </div>

      <!-- 快速导航菜单：6个功能入口 -->
      <div class="menu-section">
        <div class="section-title">
          <span class="title-icon">🎯</span>
          <span>快速导航</span>
        </div>
        <!-- 菜单网格：3列布局 -->
        <div class="menu-grid">
          <!-- 我的订单入口 -->
          <div
            class="menu-item"
            :class="{ active: activeTab === 'orders' }"
            @click="switchTab('orders')"
          >
            <div class="menu-icon-wrapper">
              <span class="menu-icon">📋</span>
            </div>
            <span class="menu-text">我的订单</span>
          </div>
          <!-- 我的收藏入口 -->
          <div
            class="menu-item"
            :class="{ active: activeTab === 'favorites' }"
            @click="switchTab('favorites')"
          >
            <div class="menu-icon-wrapper">
              <span class="menu-icon">❤️</span>
            </div>
            <span class="menu-text">我的收藏</span>
          </div>
          <!-- 我的评论入口 -->
          <div
            class="menu-item"
            :class="{ active: activeTab === 'comments' }"
            @click="switchTab('comments')"
          >
            <div class="menu-icon-wrapper">
              <span class="menu-icon">💬</span>
            </div>
            <span class="menu-text">我的评论</span>
          </div>
          <!-- 优惠券入口（待开发） -->
          <div class="menu-item">
            <div class="menu-icon-wrapper">
              <span class="menu-icon">🎫</span>
            </div>
            <span class="menu-text">优惠券</span>
          </div>
          <!-- 浏览记录入口（待开发） -->
          <div class="menu-item">
            <div class="menu-icon-wrapper">
              <span class="menu-icon">👀</span>
            </div>
            <span class="menu-text">浏览记录</span>
          </div>
          <!-- 积分商城入口（待开发） -->
          <div class="menu-item">
            <div class="menu-icon-wrapper">
              <span class="menu-icon">🎁</span>
            </div>
            <span class="menu-text">积分商城</span>
          </div>
        </div>
      </div>

      <!-- 子模块内容区：根据 activeTab 动态切换显示 -->
      <div class="sub-content">
        <!-- 我的订单组件 -->
        <MiniOrders v-if="activeTab === 'orders'" @update="loadStats" />

        <!-- 我的收藏组件 -->
        <Favorites v-if="activeTab === 'favorites'" @update="loadStats" />

        <!-- 我的评论组件 -->
        <MyComments v-if="activeTab === 'comments'" @update="loadStats" />
      </div>

      <!-- 设置列表 -->
      <div class="settings-section">
        <div class="section-title">
          <span class="title-icon">⚙️</span>
          <span>设置</span>
        </div>
        <div class="settings-list">
          <!-- 账号设置 -->
          <div class="settings-item">
            <span class="settings-icon">👤</span>
            <span class="settings-text">账号设置</span>
            <span class="settings-arrow">→</span>
          </div>
          <!-- 消息通知 -->
          <div class="settings-item">
            <span class="settings-icon">🔔</span>
            <span class="settings-text">消息通知</span>
            <span class="settings-arrow">→</span>
          </div>
          <!-- 隐私设置 -->
          <div class="settings-item">
            <span class="settings-icon">🔒</span>
            <span class="settings-text">隐私设置</span>
            <span class="settings-arrow">→</span>
          </div>
          <!-- 绑定手机 -->
          <div class="settings-item">
            <span class="settings-icon">📱</span>
            <span class="settings-text">绑定手机</span>
            <span class="settings-arrow">→</span>
          </div>
          <!-- 关于我们 -->
          <div class="settings-item">
            <span class="settings-icon">ℹ️</span>
            <span class="settings-text">关于我们</span>
            <span class="settings-arrow">→</span>
          </div>
        </div>
      </div>

      <!-- 退出登录按钮 -->
      <div class="logout-section">
        <button class="btn btn-outline logout-btn" @click="handleLogout">
          <span class="logout-icon">🚪</span>
          退出登录
        </button>
      </div>

      <!-- 未登录提示：引导用户登录或注册 -->
      <div class="login-prompt" v-if="!isLoggedIn">
        <div class="prompt-card">
          <div class="prompt-icon">🔐</div>
          <h3>请登录</h3>
          <p>登录后可以体验更多功能</p>
          <div class="prompt-actions">
            <router-link to="/login" class="btn btn-primary">登录</router-link>
            <router-link to="/register" class="btn btn-outline">注册</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/**
 * Profile.vue - 个人中心页面
 * 
 * 主要功能：
 * 1. 展示用户信息（头像、用户名、ID、加入时间）
 * 2. 统计数据展示（收藏、购物车、评论数量）
 * 3. 快速导航菜单（订单、收藏、评论等）
 * 4. 子模块动态切换（订单、收藏、评论组件）
 * 5. 设置入口列表
 * 6. 退出登录
 */
import { getCurrentUser, isLoggedIn, logout, getFavorites, getOrders, getMyComments, getCart } from '@/utils/storage'
import Favorites from '@/components/Favorites.vue'
import MyComments from '@/components/MyComments.vue'
import MiniOrders from '@/components/MiniOrders.vue'

export default {
  name: 'Profile',
  
  // 注册子组件
  components: {
    Favorites,     // 收藏列表组件
    MyComments,     // 我的评论组件
    MiniOrders      // 订单列表组件
  },
  
  data() {
    return {
      // 当前登录用户信息
      user: null,
      // 登录状态标识
      isLoggedIn: false,
      // 统计数据
      stats: {
        favorites: 0,    // 收藏数量
        orders: 0,       // 订单数量
        cartCount: 0,   // 购物车数量
        comments: 0      // 评论数量
      },
      joinDate: '--',    // 用户加入时间
      activeTab: 'favorites'  // 当前激活的标签页（orders/favorites/comments）
    }
  },
  
  // keep-alive 组件激活时重新加载数据
  activated() {
    this.loadUserData()
  },
  
  // 组件挂载时初始化数据
  mounted() {
    this.loadUserData()
  },
  
  methods: {
    /**
     * 加载用户数据
     * 获取当前用户信息、登录状态、购物车数量
     */
    loadUserData() {
      this.user = getCurrentUser()
      this.isLoggedIn = isLoggedIn()
      this.stats.cartCount = getCart().length
      
      // 如果已登录，加载统计数据
      if (this.isLoggedIn) {
        this.loadStats()
        this.joinDate = new Date().toLocaleDateString()
      }
    },
    
    /**
     * 加载统计数据
     * 获取收藏数、订单数、评论数
     */
    loadStats() {
      if (!this.isLoggedIn) return
      this.stats.favorites = getFavorites().length
      this.stats.orders = getOrders().length
      // 从 localStorage 获取当前用户的评论数
      const allComments = JSON.parse(localStorage.getItem('comments') || '[]')
      this.stats.comments = allComments.filter(c => c.userName === this.user.userName).length
    },
    
    /**
     * 切换标签页
     * @param {string} tab - 标签名称（orders/favorites/comments）
     */
    switchTab(tab) {
      // 未登录时跳转到登录页
      if (!this.isLoggedIn) {
        this.$router.push('/login')
        return
      }
      this.activeTab = tab
    },
    
    /**
     * 路由导航
     * @param {string} path - 目标路径
     */
    navigateTo(path) {
      // 未登录时跳转到登录页
      if (!this.isLoggedIn) {
        this.$router.push('/login')
        return
      }
      this.$router.push(path)
    },
    
    /**
     * 退出登录
     * 确认后清除登录状态，跳转到登录页
     */
    handleLogout() {
      if (confirm('确定要退出登录吗？')) {
        logout()
        this.isLoggedIn = false
        this.user = null
        this.stats = { favorites: 0, orders: 0, comments: 0 }
        this.$router.push('/login')
      }
    }
  }
}
</script>

<style scoped>
/* ===== 页面整体布局 ===== */
.profile-page {
  min-height: 100vh;
  padding: 20px;
  padding-bottom: 80px;  /* 底部留白，避免被底部导航遮挡 */
  position: relative;
  background: var(--bg-page);
}

.profile-container {
  max-width: 600px;
  margin: 0 auto;
  position: relative;
  z-index: 1;
}

/* ===== 用户卡片头部 ===== */
.profile-header {
  position: relative;
  margin-bottom: 24px;
}
/* 头部渐变背景装饰 */
.header-bg {
  position: absolute;
  top: 0;
  left: -20px;
  right: -20px;
  height: 180px;
  background: var(--primary-gradient);
  border-radius: var(--radius-lg);
  opacity: 0.08;
}
/* 用户信息卡片 */
.user-card {
  position: relative;
  background: white;
  border-radius: var(--radius-md);
  padding: 36px 24px 24px;
  border: 1px solid var(--border-color);
  display: flex;
  align-items: flex-start;
  gap: 20px;
  box-shadow: var(--shadow-sm);
}
/* 头像容器 */
.avatar-wrapper {
  position: relative;
  margin-top: -20px;  /* 头像上移，形成视觉层次 */
}
/* 头像样式 */
.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background: var(--primary-gradient);
  display: flex;
  align-items: center;
  justify-content: center;
  border: 3px solid white;
  box-shadow: 0 4px 16px rgba(79, 140, 247, 0.2);
}
.avatar-icon { font-size: 36px; color: white; }
/* 在线状态徽章 */
.avatar-badge {
  position: absolute;
  bottom: 4px;
  right: 4px;
  width: 16px;
  height: 16px;
  background: var(--success-color);
  border-radius: 50%;
  border: 3px solid white;
}

/* 用户信息区域 */
.user-info {
  flex: 1;
  padding-top: 4px;
}
.username {
  font-size: 22px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 4px;
}
.user-id,
.join-date {
  font-size: 13px;
  color: var(--text-muted);
  margin-bottom: 2px;
}

/* ===== 统计卡片行 ===== */
.stats-row {
  display: flex;
  gap: 14px;
  margin-bottom: 24px;
}
/* 单个统计卡片 */
.stat-card {
  flex: 1;
  background: white;
  border-radius: var(--radius-md);
  padding: 18px 16px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid var(--border-color);
  transition: all var(--transition-normal);
  cursor: pointer;
  box-shadow: var(--shadow-xs);
}
/* 统计卡片悬停效果 */
.stat-card:hover {
  transform: translateY(-3px);
  box-shadow: var(--shadow-md);
  border-color: var(--primary-light);
}
.stat-icon {
  font-size: 26px;
  margin-bottom: 8px;
}
.stat-info {
  text-align: center;
}
.stat-value {
  display: block;
  font-size: 22px;
  font-weight: 700;
  color: var(--primary-color);
  margin-bottom: 2px;
}
.stat-label {
  font-size: 12px;
  color: var(--text-muted);
}

/* ===== 快速导航菜单 ===== */
.menu-section,
.settings-section {
  margin-bottom: 24px;
}
/* 区域标题 */
.section-title {
  display: flex;
  align-items: center;
  margin-bottom: 14px;
  padding-left: 4px;
}
.title-icon {
  font-size: 18px;
  margin-right: 10px;
}
.section-title span:last-child {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}

/* 菜单网格：3列布局 */
.menu-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
}
/* 单个菜单项 */
.menu-item {
  background: white;
  border-radius: var(--radius-md);
  padding: 18px 12px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid var(--border-color);
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: var(--shadow-xs);
}
/* 菜单项悬停效果 */
.menu-item:hover {
  transform: translateY(-3px);
  box-shadow: var(--shadow-md);
  border-color: var(--primary-light);
}
/* 菜单项激活状态 */
.menu-item.active {
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(79, 140, 247, 0.10);
}
/* 菜单图标容器 */
.menu-icon-wrapper {
  width: 44px;
  height: 44px;
  border-radius: var(--radius-sm);
  background: var(--info-bg);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 8px;
}
.menu-icon {
  font-size: 20px;
}
.menu-text {
  font-size: 12px;
  font-weight: 500;
  color: var(--text-secondary);
}

/* ===== 子模块内容区 ===== */
.sub-content {
  margin-top: 16px;
  margin-bottom: 24px;
  background: white;
  border-radius: var(--radius-md);
  padding: 16px;
  border: 1px solid var(--border-color);
  min-height: 160px;
  box-shadow: var(--shadow-xs);
}

/* ===== 设置列表 ===== */
.settings-list {
  background: white;
  border-radius: var(--radius-md);
  overflow: hidden;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}
/* 单个设置项 */
.settings-item {
  display: flex;
  align-items: center;
  padding: 16px 20px;
  border-bottom: 1px solid var(--border-color);
  cursor: pointer;
  transition: var(--transition-normal);
}
.settings-item:last-child { border-bottom: none; }
/* 设置项悬停效果 */
.settings-item:hover {
  background: var(--bg-card-hover);
}
.settings-icon {
  font-size: 18px;
  margin-right: 16px;
}
.settings-text {
  flex: 1;
  font-size: 14px;
  color: var(--text-secondary);
}
.settings-arrow {
  color: var(--text-muted);
  font-size: 18px;
}

/* ===== 退出登录按钮 ===== */
.logout-section {
  margin-top: 16px;
}
.logout-btn {
  width: 100%;
  padding: 16px;
  font-size: 15px;
  font-weight: 600;
  background: var(--danger-bg);
  border: 1px solid transparent;
  border-radius: var(--radius-md);
  color: var(--danger-color);
  cursor: pointer;
  transition: all var(--transition-normal);
}
/* 退出按钮悬停效果 */
.logout-btn:hover {
  background: #fcd5d5;
  border-color: var(--danger-color);
}

/* ===== 未登录提示卡片 ===== */
.login-prompt {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}
.prompt-card {
  background: white;
  border-radius: var(--radius-md);
  padding: 40px 36px;
  text-align: center;
  border: 1px solid var(--border-color);
  width: 100%;
  box-shadow: var(--shadow-sm);
}
.prompt-icon { font-size: 48px; margin-bottom: 16px; }
.prompt-card h3 {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 6px;
}
.prompt-card p {
  font-size: 14px;
  color: var(--text-muted);
  margin-bottom: 24px;
}
/* 登录/注册按钮组 */
.prompt-actions {
  display: flex;
  gap: 14px;
  justify-content: center;
}
.prompt-actions .btn {
  padding: 12px 32px;
  border-radius: var(--radius-full);
  font-weight: 600;
  text-decoration: none;
  transition: all var(--transition-normal);
  font-size: 14px;
}
</style>
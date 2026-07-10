// TabBar.vue - 底部导航栏组件
// 包含5个导航项：首页、书籍、购物车、订单、个人中心

<template>
  <nav class="tab-bar">
    <router-link 
      v-for="item in tabs" 
      :key="item.path" 
      :to="item.path"
      :class="['tab-item', { active: $route.path === item.path }]"
    >
      <span class="icon">{{ item.icon }}</span>
      <span class="text">{{ item.text }}</span>
      <!-- 购物车角标 -->
      <span v-if="item.path === '/cart' && cartCount > 0" class="badge">{{ cartCount > 99 ? '99+' : cartCount }}</span>
    </router-link>
  </nav>
</template>

<script>
// 底部导航栏组件
import { getCart } from '@/utils/storage'

export default {
  name: 'TabBar',
  
  data() {
    return {
      // 底部导航栏配置
      tabs: [
        { path: '/', icon: '🏠', text: '首页' },
        { path: '/category', icon: '📚', text: '书籍' },
        { path: '/cart', icon: '🛒', text: '购物车' },
        { path: '/orders', icon: '📋', text: '订单' },
        { path: '/profile', icon: '👤', text: '我的' }
      ],
      cartCount: 0
    }
  },
  
  // 组件挂载后更新购物车数量
  mounted() {
    this.updateCartCount()
    
    // 监听路由变化，更新购物车数量
    this.$router.afterEach(() => {
      this.updateCartCount()
    })
  },
  
  methods: {
    // 更新购物车数量
    updateCartCount() {
      this.cartCount = getCart().length
    }
  }
}
</script>

<style scoped>
/* 底部导航栏容器：固定在页面底部 */
.tab-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 60px;
  display: flex;
  justify-content: space-around;  /* 导航项均匀分布 */
  align-items: center;
  background: rgba(44, 44, 52, 0.95); /* 机甲灰背景 */
  border-top: 1px solid rgba(0, 212, 255, 0.15); /* 顶部边框线 */
  z-index: 999;  /* 确保在其他内容之上 */
  backdrop-filter: blur(10px);  /* 毛玻璃效果 */
}

/* 单个导航项 */
.tab-item {
  position: relative;
  display: flex;
  flex-direction: column;  /* 图标和文字垂直排列 */
  align-items: center;
  text-decoration: none;
  color: var(--text-muted);  /* 默认灰色 */
  transition: all 0.3s;
  padding: 8px 15px;
}

/* 当前激活的导航项 */
.tab-item.active {
  color: var(--primary-color);  /* 高亮为主题色 */
}

/* 激活状态下的图标放大效果 */
.tab-item.active .icon {
  transform: scale(1.1);
}

/* 导航图标 */
.icon {
  font-size: 24px;
  transition: transform 0.3s;
}

/* 导航文字 */
.text {
  font-size: 11px;
  margin-top: 2px;
}

/* 购物车角标：显示商品数量 */
.badge {
  position: absolute;
  top: 2px;
  right: -4px;
  min-width: 18px;
  height: 18px;
  padding: 0 5px;
  background: linear-gradient(135deg, var(--danger-color) 0%, #d32f2f 100%);  /* 红色渐变 */
  color: #fff;
  font-size: 10px;
  font-weight: 600;
  border-radius: 9px;  /* 圆角胶囊形状 */
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 0 10px rgba(244, 67, 54, 0.4);  /* 发光效果 */
}
</style>

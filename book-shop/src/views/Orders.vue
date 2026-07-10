<template>
  <!-- 订单列表页面容器 -->
  <div class="order-page">
    <!-- 背景装饰效果：粒子动画和网格 -->
    <div class="bg-particles"></div>
    <div class="bg-grid"></div>

    <!-- 页面标题区域 -->
    <div class="page-header">
      <h1 class="page-title glow-text">我的订单</h1>
      <p class="page-subtitle">管理您的所有订单</p>
    </div>

    <!-- Tab切换栏：按订单状态筛选 -->
    <div class="tabs-wrapper">
      <div class="tabs">
        <!-- 遍历生成各个Tab标签 -->
        <span
          v-for="tab in tabs"
          :key="tab"
          :class="{ active: tab === currentTab }"
          @click="currentTab = tab"
        >
          {{ tab }}
          <!-- 显示该状态下的订单数量 -->
          <span class="tab-count" v-if="getTabCount(tab) > 0">{{ getTabCount(tab) }}</span>
        </span>
      </div>
    </div>

    <!-- 空状态：没有订单时显示 -->
    <div v-if="filteredOrders.length === 0" class="empty-state">
      <div class="empty-icon">📦</div>
      <p class="empty-text">暂无订单</p>
      <p class="empty-hint">快去选购心仪的书籍吧</p>
      <router-link to="/" class="btn btn-primary btn-sm">去逛逛</router-link>
    </div>

    <!-- 订单列表：有订单时显示 -->
    <div class="order-list" v-else>
      <!-- 订单卡片：点击跳转到订单详情 -->
      <div
        class="order-card"
        v-for="order in filteredOrders"
        :key="order.id"
        @click="goDetail(order)"
      >
        <!-- 订单头部：订单号和状态 -->
        <div class="order-header">
          <div class="order-info">
            <span class="order-icon">📋</span>
            <span class="order-no">{{ order.id }}</span>
          </div>
          <!-- 订单状态标签，根据状态显示不同颜色 -->
          <span class="status" :class="getStatusClass(order.status)">
            {{ order.status }}
          </span>
        </div>

        <!-- 商品信息区域：展示商品封面和名称 -->
        <div class="goods-container">
          <div class="goods">
            <!-- 商品封面图列表 -->
            <img
              v-for="(g, i) in order.goods"
              :key="i"
              :src="g.img || getPlaceholderCover(g.name)"
              alt=""
              class="goods-img"
              @error="e => e.target.src = getPlaceholderCover(g.name)"
            />
            <!-- 商品名称列表 -->
            <div class="goods-titles">
              <span v-for="(g, i) in order.goods" :key="i" class="goods-title">
                {{ g.name }}
              </span>
            </div>
          </div>
          <!-- 右箭头提示可点击 -->
          <div class="goods-arrow">›</div>
        </div>

        <!-- 订单底部：下单时间和总金额 -->
        <div class="order-footer">
          <div class="footer-left">
            <span class="order-time">{{ formatTime(order.createTime) }}</span>
            <span class="goods-count">共 {{ order.goods.length }} 件商品</span>
          </div>
          <div class="footer-right">
            <span class="total-label">合计</span>
            <span class="total-price">￥{{ order.total }}</span>
          </div>
        </div>

        <!-- 操作按钮区域：根据订单状态显示不同按钮 -->
        <div class="actions">
          <!-- 待付款状态：显示付款和取消按钮 -->
          <template v-if="order.status === '待付款'">
            <button class="btn btn-primary btn-sm" @click.stop="pay(order)">立即付款</button>
            <button class="btn btn-outline btn-sm" @click.stop="cancel(order)">取消订单</button>
          </template>

          <!-- 已完成/已取消状态：显示删除按钮 -->
          <template v-if="order.status === '已完成' || order.status === '已取消'">
            <button class="btn btn-outline btn-sm" @click.stop="deleteOrder(order)">删除订单</button>
          </template>
        </div>
      </div>
    </div>

    <!-- 底部留白，避免被底部导航遮挡 -->
    <div style="height: 70px;"></div>
  </div>
</template>

<script>
/**
 * 订单列表页面
 * 功能：展示用户所有订单，支持按状态筛选，可进行付款、取消、删除操作
 */
import { getOrders, updateOrderStatus, getOrdersKey } from '@/utils/storage'
import { getPlaceholderCover } from '@/utils/placeholder'

export default {
  data () {
    return {
      // Tab标签列表：全部、待付款、已完成、已取消
      tabs: ['全部', '待付款', '已完成', '已取消'],
      // 当前选中的Tab
      currentTab: '全部',
      // 订单列表数据
      orders: []
    }
  },
  
  computed: {
    /**
     * 根据当前Tab筛选订单
     * 全部：返回所有订单
     * 其他：返回对应状态的订单
     */
    filteredOrders () {
      if (this.currentTab === '全部') {
        return this.orders
      }
      return this.orders.filter(o => o.status === this.currentTab)
    }
  },
  
  created () {
    // 页面创建时加载订单数据
    this.loadOrders()
  },
  
  methods: {
    /**
     * 从localStorage加载订单数据
     * 对数据进行字段映射和排序处理
     */
    loadOrders () {
      const rawOrders = getOrders()
  
      // 安全检查：确保数据是数组
      if (!Array.isArray(rawOrders)) {
        this.orders = []
        return
      }
  
      // 数据映射：兼容不同的字段命名，按时间倒序排列
      this.orders = rawOrders
        .map(order => ({
          id: order.id || order.orderNo,
          status: this.mapStatus(order.status),
          total: order.total || order.totalAmount,
          createTime: order.createTime,
          goods: (order.goods || order.items || []).map(item => ({
            img: item.img || item.cover,
            price: item.price,
            name: item.name || item.title
          }))
        }))
        .sort((a, b) => b.createTime - a.createTime)
    },

    /**
     * 将英文状态码映射为中文显示
     * @param {string} status - 英文状态码
     * @returns {string} 中文状态
     */
    mapStatus (status) {
      const map = {
        pendingPayment: '待付款',
        pendingShip: '已完成',
        shipped: '已完成',
        completed: '已完成',
        cancelled: '已取消'
      }
      return map[status] || status
    },

    /**
     * 获取状态对应的CSS类名
     * @param {string} status - 订单状态
     * @returns {string} CSS类名
     */
    getStatusClass (status) {
      const classMap = {
        '待付款': 'status-pending',
        '已完成': 'status-completed',
        '已取消': 'status-cancelled'
      }
      return classMap[status] || ''
    },

    /**
     * 获取指定Tab下的订单数量
     * @param {string} tab - Tab名称
     * @returns {number} 订单数量
     */
    getTabCount (tab) {
      if (tab === '全部') {
        return this.orders.length
      }
      return this.orders.filter(o => o.status === tab).length
    },

    /**
     * 格式化时间戳为可读格式
     * @param {number} timestamp - 时间戳
     * @returns {string} 格式化后的时间 (MM-DD HH:mm)
     */
    formatTime (timestamp) {
      if (!timestamp) return ''
      const date = new Date(timestamp)
      const month = (date.getMonth() + 1).toString().padStart(2, '0')
      const day = date.getDate().toString().padStart(2, '0')
      const hours = date.getHours().toString().padStart(2, '0')
      const minutes = date.getMinutes().toString().padStart(2, '0')
      return `${month}-${day} ${hours}:${minutes}`
    },

    /**
     * 支付订单
     * 更新订单状态为已完成
     * @param {Object} order - 订单对象
     */
    pay (order) {
      if (confirm('确认支付该订单？')) {
        // 网络书籍：付款即完成
        updateOrderStatus(order.id, 'completed')
        this.loadOrders()
      }
    },

    /**
     * 取消订单
     * 更新订单状态为已取消
     * @param {Object} order - 订单对象
     */
    cancel (order) {
      if (confirm('确认取消订单？')) {
        updateOrderStatus(order.id, 'cancelled')
        this.loadOrders()
      }
    },

    /**
     * 删除订单
     * 从localStorage中移除订单数据
     * @param {Object} order - 订单对象
     */
    deleteOrder (order) {
      if (confirm('删除该订单？')) {
        const orders = getOrders().filter(o => o.orderNo !== order.id)
        localStorage.setItem(getOrdersKey(), JSON.stringify(orders))
        this.loadOrders()
      }
    },

    /**
     * 跳转到订单详情页
     * 通过路由参数传递订单数据
     * @param {Object} order - 订单对象
     */
    goDetail (order) {
      this.$router.push({
        name: 'OrderDetail',
        params: { orderId: order.id },
        query: { data: JSON.stringify(order) }
      })
    }
  }
}
</script>

<style scoped>
/* ===== 页面容器 ===== */
.order-page {
  padding: 0;
  min-height: 100vh;
  position: relative;
  background: var(--bg-page);
}

/* ===== 页面标题区域 ===== */
.page-header {
  position: relative;
  z-index: 1;
  text-align: center;
  padding: 28px 16px 18px;
  background: white;
  border-bottom: 1px solid var(--border-color);
}
.page-title {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 4px;
}
.page-subtitle {
  font-size: 13px;
  color: var(--text-muted);
  margin: 0;
}

/* ===== Tab切换栏 ===== */
.tabs-wrapper {
  position: sticky;  /* 吸顶效果 */
  top: 0;
  z-index: 10;
  background: white;
  border-bottom: 1px solid var(--border-color);
}
.tabs {
  display: flex;
  gap: 4px;
  padding: 12px 16px;
  overflow-x: auto;  /* 横向滚动 */
  -webkit-overflow-scrolling: touch;
}
.tabs::-webkit-scrollbar { display: none; }  /* 隐藏滚动条 */
.tabs span {
  flex-shrink: 0;  /* 不压缩 */
  padding: 8px 18px;
  font-size: 14px;
  font-weight: 500;
  color: var(--text-muted);
  cursor: pointer;
  border-radius: var(--radius-full);
  transition: all var(--transition-normal);
  display: flex;
  align-items: center;
  gap: 8px;
}
.tabs span:hover {
  color: var(--text-secondary);
  background: var(--bg-input);
}
.tabs span.active {
  color: var(--primary-color);
  background: var(--info-bg);
}
/* Tab上的数量角标 */
.tab-count {
  font-size: 11px;
  background: var(--bg-input);
  padding: 2px 8px;
  border-radius: var(--radius-full);
  font-weight: 600;
}
.tabs span.active .tab-count {
  background: var(--primary-color);
  color: white;
}

/* ===== 空状态 ===== */
.empty-state {
  position: relative;
  z-index: 1;
  text-align: center;
  padding: 80px 20px;
}
.empty-icon {
  font-size: 64px;
  margin-bottom: 16px;
  opacity: 0.4;
}
.empty-text {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-secondary);
  margin: 0 0 6px;
}
.empty-hint {
  font-size: 14px;
  color: var(--text-muted);
  margin: 0 0 24px;
}
.btn-sm {
  padding: 10px 28px;
  border-radius: var(--radius-full);
  font-weight: 600;
  font-size: 14px;
  display: inline-block;
  text-decoration: none;
}

/* ===== 订单列表容器 ===== */
.order-list {
  position: relative;
  z-index: 1;
  padding: 16px;
}

/* ===== 订单卡片 ===== */
.order-card {
  background: white;
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  padding: 18px 20px;
  margin-bottom: 16px;
  transition: all var(--transition-normal);
  box-shadow: var(--shadow-xs);
}
/* 悬浮效果：上移并加深阴影 */
.order-card:hover {
  transform: translateY(-3px);
  box-shadow: var(--shadow-md);
  border-color: var(--primary-light);
}

/* ===== 订单头部：订单号和状态 ===== */
.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 14px;
  padding-bottom: 14px;
  border-bottom: 1px solid var(--border-color);
}
.order-info {
  display: flex;
  align-items: center;
  gap: 10px;
}
.order-icon { font-size: 18px; }
.order-no {
  font-size: 13px;
  color: var(--text-muted);
  font-family: monospace;
  font-weight: 500;
}
/* 订单状态标签 */
.status {
  font-size: 12px;
  padding: 4px 16px;
  border-radius: var(--radius-full);
  font-weight: 600;
}
.status-pending {
  background: var(--warning-bg);
  color: var(--warning-color);
}
.status-completed {
  background: var(--success-bg);
  color: var(--success-color);
}
.status-cancelled {
  background: var(--danger-bg);
  color: var(--danger-color);
}

/* ===== 商品信息区域 ===== */
.goods-container {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 8px 0;
}
.goods {
  display: flex;
  gap: 10px;
  flex: 1;
  overflow-x: auto;  /* 横向滚动展示多个商品 */
}
.goods::-webkit-scrollbar { display: none; }
/* 商品封面图 */
.goods-img {
  width: 52px;
  height: 68px;
  border-radius: var(--radius-sm);
  object-fit: cover;
  flex-shrink: 0;
  border: 1px solid var(--border-color);
}
/* 右箭头提示 */
.goods-arrow {
  font-size: 22px;
  color: var(--text-muted);
  margin-left: 10px;
  flex-shrink: 0;
}
/* 商品名称列表 */
.goods-titles {
  display: flex;
  flex-direction: column;
  gap: 4px;
  margin-left: 12px;
  flex: 1;
}
.goods-title {
  font-size: 13px;
  color: var(--text-secondary);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* ===== 订单底部：时间和金额 ===== */
.order-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0 8px;
  border-top: 1px solid var(--border-color);
  margin-top: 8px;
}
.footer-left {
  display: flex;
  flex-direction: column;
  gap: 2px;
}
.order-time {
  font-size: 12px;
  color: var(--text-muted);
}
.goods-count {
  font-size: 12px;
  color: var(--text-secondary);
}
.footer-right {
  text-align: right;
}
.total-label {
  font-size: 13px;
  color: var(--text-muted);
  margin-right: 4px;
}
.total-price {
  font-size: 20px;
  font-weight: 700;
  color: var(--primary-color);
}

/* ===== 操作按钮区域 ===== */
.actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  margin-top: 14px;
  padding-top: 14px;
  border-top: 1px solid var(--border-color);
}
.btn-sm {
  padding: 8px 20px;
  border-radius: var(--radius-full);
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
  border: none;
}
/* 主要按钮样式 */
.btn-primary-sm {
  background: var(--primary-gradient);
  color: white;
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.2);
}
.btn-primary-sm:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.25);
}
/* 次要按钮样式 */
.btn-outline-sm {
  background: transparent;
  color: var(--text-secondary);
  border: 1.5px solid var(--border-color);
}
.btn-outline-sm:hover {
  border-color: var(--primary-color);
  color: var(--primary-color);
}
</style>
<template>
  <!-- 订单详情页面容器 -->
  <div class="order-detail-page">
    <!-- 背景装饰效果：粒子动画和网格 -->
    <div class="bg-particles"></div>
    <div class="bg-grid"></div>

    <!-- 页面头部：返回按钮和标题 -->
    <div class="page-header">
      <!-- 返回按钮 -->
      <button class="back-btn" @click="goBack">←</button>
      <h1 class="page-title glow-text">订单详情</h1>
      <!-- 占位元素，保持标题居中 -->
      <span class="header-placeholder"></span>
    </div>

    <!-- 订单内容区域 -->
    <div class="detail-content" v-if="order">
      <!-- 订单状态卡片：显示当前订单状态 -->
      <div class="status-card">
        <span class="status-icon">{{ getStatusIcon(order.status) }}</span>
        <span class="status-text">{{ order.status }}</span>
      </div>

      <!-- 订单基本信息：订单号和下单时间 -->
      <div class="info-card">
        <div class="info-row">
          <span class="info-label">订单编号</span>
          <span class="info-value">{{ order.id }}</span>
        </div>
        <div class="info-row">
          <span class="info-label">下单时间</span>
          <span class="info-value">{{ formatTime(order.createTime) }}</span>
        </div>
      </div>

      <!-- 商品列表卡片 -->
      <div class="goods-card">
        <div class="card-title">商品清单</div>
        <div class="goods-list">
          <!-- 遍历显示每个商品，点击可跳转到书籍详情 -->
          <div class="goods-item" v-for="(item, index) in order.goods" :key="index" @click="goBookDetail(item)">
            <!-- 商品封面图 -->
            <img :src="item.img || getPlaceholderCover(item.name)" alt="" class="goods-img" @error="e => e.target.src = getPlaceholderCover(item.name)" />
            <div class="goods-info">
              <div class="goods-name">{{ item.name }}</div>
              <!-- 商品单价 -->
              <div class="goods-price">¥{{ item.price }}</div>
            </div>
            <!-- 商品数量 -->
            <div class="goods-quantity">×{{ item.quantity || 1 }}</div>
            <!-- 右箭头提示可点击 -->
            <div class="goods-arrow">›</div>
          </div>
        </div>
      </div>

      <!-- 金额明细卡片 -->
      <div class="amount-card">
        <div class="amount-row">
          <span class="amount-label">商品总价</span>
          <span class="amount-value">¥{{ order.total }}</span>
        </div>
        <div class="amount-row">
          <span class="amount-label">运费</span>
          <span class="amount-value" style="color: #10B981;">免运费</span>
        </div>
        <!-- 实付金额（突出显示） -->
        <div class="amount-row total-row">
          <span class="amount-label">实付金额</span>
          <span class="amount-value total-price">¥{{ order.total }}</span>
        </div>
      </div>

      <!-- 操作按钮区域：根据订单状态显示不同按钮 -->
      <div class="action-bar">
        <!-- 待付款状态：显示取消和付款按钮 -->
        <template v-if="order.status === '待付款'">
          <button class="btn btn-outline" @click="cancelOrder">取消订单</button>
          <button class="btn btn-primary" @click="payOrder">立即付款</button>
        </template>
        <!-- 已完成/已取消状态：显示删除按钮 -->
        <template v-if="order.status === '已完成' || order.status === '已取消'">
          <button class="btn btn-danger" @click="deleteOrder">删除订单</button>
        </template>
      </div>
    </div>

    <!-- 底部留白，避免内容被遮挡 -->
    <div style="height: 70px;"></div>
  </div>
</template>

<script>
/**
 * 订单详情页面
 * 功能：展示单个订单的完整信息，包括状态、商品列表、金额明细
 * 支持付款、取消、删除操作
 */
import { getOrders, updateOrderStatus, getOrdersKey } from '@/utils/storage'
import { getPlaceholderCover } from '@/utils/placeholder'
import { bookList } from '@/mock/bookData'

export default {
  name: 'OrderDetail',
  
  data() {
    return {
      // 当前订单数据
      order: null
    }
  },
  
  created() {
    // 页面创建时加载订单数据
    this.loadOrder()
  },
  
  methods: {
    /**
     * 加载订单数据
     * 从路由参数中获取订单数据并解析
     */
    loadOrder() {
      const orderData = this.$route.query.data
      if (orderData) {
        const rawOrder = JSON.parse(orderData)
        // 映射字段，兼容不同的数据结构
        this.order = {
          id: rawOrder.id || rawOrder.orderNo,
          status: this.mapStatus(rawOrder.status),
          total: rawOrder.total || rawOrder.totalAmount,
          createTime: rawOrder.createTime,
          // 映射商品列表字段，兼容不同的数据结构
          goods: (rawOrder.goods || rawOrder.items || []).map(item => ({
            img: item.img || item.cover,
            price: item.price,
            name: item.name || item.title,
            // 数量字段：优先使用quantity，否则默认为1
            quantity: item.quantity || 1
          }))
        }
      }
    },

    /**
     * 将英文状态码映射为中文显示
     * @param {string} status - 英文状态码
     * @returns {string} 中文状态
     */
    mapStatus(status) {
      const map = {
        pendingPayment: '待付款',
        pendingShip: '已完成', // 兼容旧数据：待发货 -> 已完成
        shipped: '已完成', // 兼容旧数据：待收货 -> 已完成
        completed: '已完成',
        cancelled: '已取消'
      }
      return map[status] || status
    },

    /**
     * 根据订单状态返回对应的图标
     * @param {string} status - 订单状态
     * @returns {string} 状态图标
     */
    getStatusIcon(status) {
      const icons = {
        '待付款': '⏳',
        '已完成': '✅',
        '已取消': '❌'
      }
      return icons[status] || '✅'
    },

    /**
     * 格式化时间戳为可读格式
     * @param {number} timestamp - 时间戳
     * @returns {string} 格式化后的时间 (YYYY-MM-DD HH:mm)
     */
    formatTime(timestamp) {
      if (!timestamp) return ''
      const date = new Date(timestamp)
      const year = date.getFullYear()
      const month = (date.getMonth() + 1).toString().padStart(2, '0')
      const day = date.getDate().toString().padStart(2, '0')
      const hours = date.getHours().toString().padStart(2, '0')
      const minutes = date.getMinutes().toString().padStart(2, '0')
      return `${year}-${month}-${day} ${hours}:${minutes}`
    },

    /**
     * 返回上一页
     */
    goBack() {
      this.$router.back()
    },

    /**
     * 跳转到书籍详情页
     * 根据商品名称在书籍列表中查找对应ID
     * @param {Object} item - 商品对象
     */
    goBookDetail(item) {
      // 根据书籍名称查找书籍ID并跳转
      const book = bookList.find(b => b.title === item.name)
      if (book) {
        this.$router.push({ name: 'BookDetail', params: { id: book.id } })
      } else {
        this.$router.push('/')
      }
    },

    /**
     * 支付订单
     * 更新订单状态为已完成
     */
    payOrder() {
      if (confirm('确认支付该订单？')) {
        // 使用英文状态更新存储
        updateOrderStatus(this.order.id, 'completed')
        // 显示中文状态
        this.order.status = '已完成'
        alert('支付成功！')
      }
    },

    /**
     * 取消订单
     * 更新订单状态为已取消
     */
    cancelOrder() {
      if (confirm('确认取消订单？')) {
        // 使用英文状态更新存储
        updateOrderStatus(this.order.id, 'cancelled')
        // 显示中文状态
        this.order.status = '已取消'
      }
    },

    /**
     * 删除订单
     * 从localStorage中移除订单数据，并返回订单列表页
     */
    deleteOrder() {
      if (confirm('删除该订单？')) {
        const orders = getOrders().filter(o => o.orderNo !== this.order.id)
        localStorage.setItem(getOrdersKey(), JSON.stringify(orders))
        this.$router.push('/orders')
      }
    }
  }
}
</script>

<style scoped>
/* ===== 页面容器 ===== */
.order-detail-page {
  min-height: 100vh;
  position: relative;
  padding-bottom: 20px;
  background: var(--bg-page);
}

/* ===== 页面头部 ===== */
.page-header {
  position: relative;
  z-index: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;  /* 两端对齐，使标题居中 */
  padding: 16px 20px;
  background: white;
  border-bottom: 1px solid var(--border-color);
}
/* 返回按钮 */
.back-btn {
  background: none;
  border: none;
  font-size: 24px;
  color: var(--text-primary);
  cursor: pointer;
  padding: 4px 10px;
  border-radius: var(--radius-sm);
  transition: var(--transition-normal);
}
.back-btn:hover {
  background: var(--bg-input);
}
.page-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}
/* 占位元素，保持标题居中 */
.header-placeholder { width: 40px; }

/* ===== 内容区域 ===== */
.detail-content {
  position: relative;
  z-index: 1;
  padding: 16px;
}

/* ===== 状态卡片：显示订单当前状态 ===== */
.status-card {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 20px 24px;
  background: white;
  border-radius: var(--radius-md);
  border: 1px solid var(--border-color);
  margin-bottom: 16px;
  box-shadow: var(--shadow-xs);
}
.status-icon { font-size: 30px; }
.status-text {
  font-size: 18px;
  font-weight: 600;
  color: var(--primary-color);
}

/* ===== 信息卡片：订单号和下单时间 ===== */
.info-card {
  background: white;
  border-radius: var(--radius-md);
  padding: 18px 20px;
  margin-bottom: 16px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}
.info-row {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid var(--border-color);
}
.info-row:last-child { border-bottom: none; }
.info-label {
  font-size: 14px;
  color: var(--text-muted);
}
.info-value {
  font-size: 14px;
  color: var(--text-secondary);
  font-family: monospace;  /* 等宽字体，便于阅读订单号 */
}

/* ===== 商品卡片：商品列表 ===== */
.goods-card {
  background: white;
  border-radius: var(--radius-md);
  padding: 18px 20px;
  margin-bottom: 16px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}
.card-title {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 14px;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--border-color);
}
.goods-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
/* 商品项：可点击跳转到书籍详情 */
.goods-item {
  display: flex;
  align-items: center;
  padding: 10px;
  background: var(--bg-card-hover);
  border-radius: var(--radius-sm);
  cursor: pointer;
  transition: var(--transition-normal);
}
.goods-item:hover {
  background: var(--info-bg);
}
/* 商品封面图 */
.goods-img {
  width: 48px;
  height: 64px;
  border-radius: var(--radius-sm);
  object-fit: cover;
  flex-shrink: 0;
}
/* 商品信息：名称和价格 */
.goods-info {
  flex: 1;
  margin-left: 14px;
}
.goods-name {
  font-size: 14px;
  color: var(--text-primary);
  margin-bottom: 4px;
}
.goods-price {
  font-size: 14px;
  color: var(--primary-color);
  font-weight: 600;
}
/* 商品数量 */
.goods-quantity {
  font-size: 14px;
  color: var(--text-muted);
  margin-right: 8px;
}
/* 右箭头提示可点击 */
.goods-arrow {
  font-size: 20px;
  color: var(--text-muted);
}

/* ===== 金额卡片：价格明细 ===== */
.amount-card {
  background: white;
  border-radius: var(--radius-md);
  padding: 18px 20px;
  margin-bottom: 16px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}
.amount-row {
  display: flex;
  justify-content: space-between;
  padding: 6px 0;
}
.amount-label {
  font-size: 14px;
  color: var(--text-muted);
}
.amount-value {
  font-size: 14px;
  color: var(--text-secondary);
}
/* 实付金额行：突出显示 */
.total-row {
  padding-top: 12px;
  margin-top: 8px;
  border-top: 1px solid var(--border-color);
}
.total-row .amount-label {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}
.total-price {
  font-size: 22px;
  font-weight: 700;
  color: var(--danger-color);  /* 红色突出显示金额 */
}

/* ===== 操作按钮区域 ===== */
.action-bar {
  display: flex;
  gap: 12px;
  justify-content: flex-end;  /* 按钮右对齐 */
  padding: 8px 0;
}
/* 按钮基础样式 */
.btn {
  padding: 12px 28px;
  border-radius: var(--radius-full);
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
  border: none;
}
/* 主要按钮：付款按钮 */
.btn-primary {
  background: var(--primary-gradient);
  color: white;
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.2);
}
.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.25);
}
/* 次要按钮：取消按钮 */
.btn-outline {
  background: transparent;
  color: var(--text-secondary);
  border: 1.5px solid var(--border-color);
}
.btn-outline:hover {
  border-color: var(--primary-color);
  color: var(--primary-color);
}
/* 危险按钮：删除按钮 */
.btn-danger {
  background: var(--danger-bg);
  color: var(--danger-color);
  border: 1px solid transparent;
}
.btn-danger:hover {
  background: #fcd5d5;
  border-color: var(--danger-color);
}
</style>
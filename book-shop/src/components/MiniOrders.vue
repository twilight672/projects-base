<template>
  <!-- 迷你订单组件容器 -->
  <div class="mini-orders">
    
    <!-- 标题栏：显示"最近订单"标题和"查看全部"链接 -->
    <div class="mini-header">
      <span class="mini-title">📋 最近订单</span>
      <!-- 跳转到完整订单列表页面 -->
      <router-link to="/orders" class="view-all">查看全部 ›</router-link>
    </div>

    <!-- 订单列表：有订单时显示，最多显示3条 -->
    <div class="mini-list" v-if="orders.length > 0">
      <!-- 遍历订单列表，slice(0,3)限制最多显示3条 -->
      <div
        class="mini-order-item"
        v-for="order in orders.slice(0, 3)"
        :key="order.id"
        @click="goDetail(order)"
      >
        <!-- 订单基本信息行：订单ID + 状态标签 -->
        <div class="mini-order-info">
          <span class="mini-order-id">{{ order.id }}</span>
          <!-- 根据状态显示不同颜色的标签 -->
          <span class="mini-order-status" :class="getStatusClass(order.status)">
            {{ order.status }}
          </span>
        </div>
        
        <!-- 订单商品信息行：商品数量 + 订单总金额 -->
        <div class="mini-order-goods">
          <span class="mini-goods-count">{{ (order.goods && order.goods.length) || 0 }} 件商品</span>
          <span class="mini-order-total">¥{{ order.total }}</span>
        </div>
      </div>
    </div>

    <!-- 空状态：无订单时显示提示和跳转按钮 -->
    <div class="mini-empty" v-else>
      <!-- 文件袋图标表示空订单 -->
      <span class="mini-empty-icon">📭</span>
      <p>暂无订单</p>
      <!-- 引导用户去首页逛逛 -->
      <router-link to="/" class="mini-btn">去逛逛</router-link>
    </div>
    
  </div>
</template>

<script>
// 迷你订单组件：展示用户最近订单的精简列表，用于个人中心等场景
import { getOrders } from '@/utils/storage'

export default {
  name: 'MiniOrders',
  
  data() {
    return {
      // 订单列表数组
      orders: []
    }
  },
  
  // 组件挂载后加载订单数据
  mounted() {
    this.loadOrders()
  },
  
  // keep-alive 缓存激活时刷新订单数据
  activated() {
    this.loadOrders()
  },
  
  methods: {
    // 加载用户订单列表
    loadOrders() {
      const rawOrders = getOrders()
      
      // 确保数据是数组格式
      if (!Array.isArray(rawOrders)) {
        this.orders = []
        return
      }
      
      // 映射字段，兼容不同数据结构
      this.orders = rawOrders.map(order => ({
        // 订单ID：优先使用 id，否则使用 orderNo
        id: order.id || order.orderNo,
        // 状态：转换为中文显示
        status: this.mapStatus(order.status),
        // 总金额：优先使用 total，否则使用 totalAmount
        total: order.total || order.totalAmount,
        // 创建时间：用于排序
        createTime: order.createTime,
        // 商品列表：统一字段格式
        goods: (order.goods || order.items || []).map(item => ({
          img: item.img || item.cover,
          price: item.price,
          name: item.name || item.title
        }))
        // 按创建时间倒序排序
      })).sort((a, b) => b.createTime - a.createTime)
    },
    
    // 映射订单状态为中文显示
    mapStatus(status) {
      const map = {
        pendingPayment: '待付款',
        pendingShip: '已完成',
        shipped: '已完成',
        completed: '已完成',
        cancelled: '已取消'
      }
      return map[status] || status
    },
    
    // 根据状态获取对应的CSS类名
    getStatusClass(status) {
      const map = {
        '待付款': 'status-pending',
        '已完成': 'status-completed',
        '已取消': 'status-cancelled'
      }
      return map[status] || ''
    },
    
    // 跳转到订单详情页
    goDetail(order) {
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
/* 迷你订单容器 */
.mini-orders {
  background: transparent;
}

/* 标题栏：左侧标题 + 右侧查看全部链接 */
.mini-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(0, 212, 255, 0.1);
  margin-bottom: 12px;
}

/* 标题文字 */
.mini-title {
  font-size: 15px;
  font-weight: 600;
  color: #e5e7eb;
}

/* "查看全部"链接 */
.view-all {
  font-size: 13px;
  color: #00d4ff;
  text-decoration: none;
  transition: color 0.3s;
}

.view-all:hover {
  color: #00a8cc;
}

/* 订单列表容器 */
.mini-list {
  display: flex;
  flex-direction: column;
  gap: 10px;  /* 订单项之间的间距 */
}

/* 单个订单卡片 */
.mini-order-item {
  background: rgba(20, 20, 40, 0.8);
  border-radius: 10px;
  padding: 12px 15px;
  cursor: pointer;
  transition: all 0.3s;
  border: 1px solid rgba(0, 212, 255, 0.08);
}

/* 订单卡片悬停效果：背景变亮 + 边框高亮 + 轻微右移 */
.mini-order-item:hover {
  background: rgba(30, 30, 50, 0.9);
  border-color: rgba(0, 212, 255, 0.2);
  transform: translateX(3px);
}

/* 订单信息行：订单ID + 状态标签 */
.mini-order-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

/* 订单ID：使用等宽字体 */
.mini-order-id {
  font-size: 13px;
  color: #9ca3af;
  font-family: monospace;
}

/* 订单状态标签 */
.mini-order-status {
  font-size: 12px;
  padding: 3px 8px;
  border-radius: 4px;
  font-weight: 500;
}

/* 待付款状态：橙色 */
.status-pending {
  background: rgba(255, 152, 0, 0.2);
  color: #ff9800;
}

/* 已完成状态：绿色 */
.status-completed {
  background: rgba(16, 185, 129, 0.2);
  color: #10b981;
}

/* 已取消状态：红色 */
.status-cancelled {
  background: rgba(244, 67, 54, 0.2);
  color: #f44336;
}

/* 订单商品信息行：商品数量 + 总金额 */
.mini-order-goods {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 商品数量文字 */
.mini-goods-count {
  font-size: 12px;
  color: #6b7280;
}

/* 订单总金额：主题色高亮 */
.mini-order-total {
  font-size: 14px;
  font-weight: 600;
  color: #00d4ff;
}

/* 空状态容器 */
.mini-empty {
  text-align: center;
  padding: 30px 0;
}

/* 空状态图标 */
.mini-empty-icon {
  font-size: 36px;
  display: block;
  margin-bottom: 10px;
}

.mini-empty p {
  font-size: 14px;
  color: #9ca3af;
  margin-bottom: 15px;
}

/* "去逛逛"按钮 */
.mini-btn {
  display: inline-block;
  padding: 8px 20px;
  background: linear-gradient(135deg, #00d4ff, #0088cc);  /* 渐变背景 */
  color: #fff;
  border-radius: 6px;
  font-size: 13px;
  text-decoration: none;
  transition: all 0.3s;
}

/* 按钮悬停效果：上浮 + 发光 */
.mini-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 0 15px rgba(0, 212, 255, 0.3);
}
</style>
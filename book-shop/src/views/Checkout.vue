<!--
  Checkout.vue - 结算页（确认下单）
  功能：
    ① 接收购物车已选商品或立即购买的单品，展示订单商品列表
    ② 计算订单总金额
    ③ 点击"提交订单" → 生成订单对象（状态=pendingPayment）
    ④ 弹出确认支付弹窗
    ⑤ 确认支付 → 更新订单状态为 pendingShip，跳转到"我的订单"
    ⑥ 取消支付 → 订单状态保持 pendingPayment
  布局：
    - 顶部标题栏 + 返回按钮
    - 商品列表（含小计）
    - 订单金额汇总
    - 底部"提交订单"按钮
-->
<template>
  <div class="checkout">
    <!-- 标题栏 -->
    <div class="checkout-header">
      <button class="back-btn" @click="goBack">←</button>
      <span class="header-title">确认订单</span>
      <span class="header-placeholder"></span>
    </div>

    <!-- 滚动内容区 -->
    <div class="scroll-area">
      <!-- 商品列表 -->
      <div class="product-section">
        <div class="section-title">
          <span class="title-icon">📦</span>
          <span>商品清单</span>
          <span class="product-count">共 {{ orderItems.length }} 件商品</span>
        </div>

        <div
          v-for="item in orderItems"
          :key="item.id"
          class="product-item"
        >
          <img
            :src="item.cover || getPlaceholderCover(item.title)"
            alt="商品封面"
            class="product-cover"
            @error="e => e.target.src = getPlaceholderCover(item.title)"
          />
          <div class="product-info">
            <div class="product-title">{{ item.title }}</div>
            <div class="product-author">{{ item.author || '未知作者' }}</div>
            <div class="product-meta">
              <span class="product-price">¥{{ Number(item.price).toFixed(2) }}</span>
              <span class="product-qty">× {{ item.quantity }}</span>
            </div>
          </div>
          <div class="product-subtotal">
            ¥{{ (Number(item.price) * item.quantity).toFixed(2) }}
          </div>
        </div>
      </div>

      <!-- 金额汇总 -->
      <div class="summary-section">
        <div class="summary-row">
          <span class="summary-label">商品总价</span>
          <span class="summary-value">¥{{ totalPrice.toFixed(2) }}</span>
        </div>
        <div class="summary-row">
          <span class="summary-label">运费</span>
          <span class="summary-value" style="color: #10b981;">免运费</span>
        </div>
        <div class="summary-row" v-if="discount > 0">
          <span class="summary-label">优惠</span>
          <span class="summary-value" style="color: #ef4444;">-¥{{ discount.toFixed(2) }}</span>
        </div>
        <div class="summary-row total-row">
          <span class="summary-label">实付金额</span>
          <span class="summary-value total-price">¥{{ finalPrice.toFixed(2) }}</span>
        </div>
      </div>

      <!-- 底部占位 -->
      <div style="height: 80px;"></div>
    </div>

    <!-- 底部固定按钮 -->
    <div class="bottom-bar">
      <div class="total-display">
        <span class="total-label">实付</span>
        <span class="total-amount">¥{{ finalPrice.toFixed(2) }}</span>
      </div>
      <button class="submit-btn" @click="handleSubmitOrder">
        提交订单
      </button>
    </div>

    <!-- 支付弹窗 -->
    <div v-if="showPayModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <div class="modal-header">
          <span class="modal-icon">💳</span>
          <h3>确认支付</h3>
        </div>
        <div class="modal-body">
          <p class="pay-amount">请支付 <span class="highlight">¥{{ finalPrice.toFixed(2) }}</span></p>
          <p class="pay-order">订单号：{{ currentOrderNo }}</p>
          <div class="pay-methods">
            <div class="pay-method active">
              <span class="method-icon">💳</span>
              <span>模拟支付</span>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button class="modal-btn cancel-btn" @click="closeModal">取消</button>
          <button class="modal-btn confirm-btn" @click="confirmPay">确认支付</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// 结算页面组件
import { createOrder, updateOrderStatus, getCart, isLoggedIn } from '@/utils/storage'
import { getBookById } from '@/mock/bookData'
import { getPlaceholderCover } from '@/utils/placeholder'

export default {
  name: 'Checkout',
  
  data() {
    return {
      // 订单商品列表
      orderItems: [],
      // 优惠金额
      discount: 0,
      // 支付弹窗
      showPayModal: false,
      currentOrderNo: '',
      // 当前订单数据
      currentOrderData: null
    }
  },
  
  computed: {
    // 商品总价
    totalPrice() {
      return this.orderItems.reduce((sum, item) => {
        return sum + Number(item.price) * (item.quantity || 1)
      }, 0)
    },
    
    // 最终支付金额（总价 - 优惠）
    finalPrice() {
      return Math.max(0, this.totalPrice - this.discount)
    }
  },
  mounted() {
    // 检查登录状态
    if (!isLoggedIn()) {
      alert('请先登录再下单')
      this.$router.push('/login')
      return
    }
    this.loadOrderData()
  },
  methods: {
    // 加载订单数据
    loadOrderData() {
      const query = this.$route.query

      // 1. 处理"立即购买"场景
      if (query.buyNow === '1' && query.bookId) {
        const book = getBookById(Number(query.bookId))
        if (book) {
          this.orderItems = [{
            id: book.id,
            title: book.title,
            author: book.author,
            price: book.price,
            cover: book.cover,
            quantity: 1
          }]
          return
        }
      }

      // 2. 处理"购物车"场景
      if (query.fromCart === '1' && query.data) {
        try {
          const items = JSON.parse(decodeURIComponent(query.data))
          if (Array.isArray(items) && items.length > 0) {
            this.orderItems = items
            return
          }
        } catch (e) {
          console.warn('解析购物车数据失败:', e)
        }
      }

      // 3. 兜底：如果都没有数据，从购物车获取所有已选商品
      const cart = getCart()
      const selectedItems = cart.filter(item => item.selected !== false)
      if (selectedItems.length > 0) {
        this.orderItems = selectedItems
      } else {
        // 如果购物车也没有商品，返回上一页
        alert('没有可结算的商品')
        this.$router.back()
      }
    },

    // 提交订单
    handleSubmitOrder() {
      if (this.orderItems.length === 0) {
        alert('订单中没有商品')
        return
      }

      // 生成订单数据
      const orderData = {
        items: this.orderItems.map(item => ({
          id: item.id,
          title: item.title,
          author: item.author || '未知作者',
          price: Number(item.price),
          quantity: item.quantity || 1,
          cover: item.cover || ''
        })),
        totalAmount: this.finalPrice,
        status: 'pendingPayment', // 待付款
        createTime: new Date().getTime()
      }

      // 创建订单（存入 localStorage）
      const order = createOrder(orderData)
      this.currentOrderNo = order.orderNo
      this.currentOrderData = order

      // 显示支付弹窗
      this.showPayModal = true
    },

    // 确认支付
    confirmPay() {
      if (!this.currentOrderNo) return

      // 更新订单状态为"已完成"（网络书籍付款即完成，无需发货）
      const updated = updateOrderStatus(this.currentOrderNo, 'completed')

      if (updated) {
        this.showPayModal = false
        alert('🎉 支付成功！')
        // 跳转到我的订单页
        this.$router.push('/orders')
      } else {
        alert('支付失败，请重试')
      }
    },

    // 关闭支付弹窗（取消支付）
    closeModal() {
      this.showPayModal = false
      // 注意：订单状态保持 pendingPayment，不删除
      // 用户可以在"我的订单"中继续支付
    },

    // 返回上一页
    goBack() {
      if (window.history.length > 1) {
        this.$router.back()
      } else {
        this.$router.push('/')
      }
    }
  }
}
</script>

<style scoped>
.checkout {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: var(--bg-page);
  display: flex;
  flex-direction: column;
  z-index: 1001;
}

/* ===== 标题栏 ===== */
.checkout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  background: white;
  border-bottom: 1px solid var(--border-color);
  flex-shrink: 0;
}
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
.header-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
}
.header-placeholder { width: 40px; }

/* ===== 滚动区 ===== */
.scroll-area {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  -webkit-overflow-scrolling: touch;
}
.scroll-area::-webkit-scrollbar { width: 4px; }
.scroll-area::-webkit-scrollbar-thumb {
  background: #d0d7e6;
  border-radius: 4px;
}

/* ===== 商品区域 ===== */
.product-section {
  background: white;
  border-radius: var(--radius-md);
  padding: 20px;
  margin-bottom: 14px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}
.section-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  padding-bottom: 14px;
  border-bottom: 1px solid var(--border-color);
  margin-bottom: 14px;
}
.title-icon { font-size: 20px; }
.product-count {
  margin-left: auto;
  font-size: 13px;
  color: var(--text-muted);
  font-weight: 400;
}

/* ===== 单个商品 ===== */
.product-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid var(--border-color);
}
.product-item:last-child {
  border-bottom: none;
}
.product-cover {
  width: 56px;
  height: 74px;
  object-fit: cover;
  border-radius: var(--radius-sm);
  flex-shrink: 0;
  background: var(--bg-input);
}
.product-info {
  flex: 1;
  padding: 0 14px;
  min-width: 0;
}
.product-title {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.product-author {
  font-size: 12px;
  color: var(--text-muted);
  margin: 4px 0;
}
.product-meta {
  display: flex;
  align-items: center;
  gap: 12px;
}
.product-price {
  font-size: 14px;
  color: var(--danger-color);
  font-weight: 600;
}
.product-qty {
  font-size: 13px;
  color: var(--text-muted);
}
.product-subtotal {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  flex-shrink: 0;
  min-width: 60px;
  text-align: right;
}

/* ===== 金额汇总 ===== */
.summary-section {
  background: white;
  border-radius: var(--radius-md);
  padding: 20px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}
.summary-row {
  display: flex;
  justify-content: space-between;
  padding: 6px 0;
  font-size: 14px;
  color: var(--text-secondary);
}
.summary-row.total-row {
  padding-top: 14px;
  margin-top: 10px;
  border-top: 1px solid var(--border-color);
}
.summary-label { color: var(--text-muted); }
.summary-value { color: var(--text-secondary); font-weight: 500; }
.total-row .summary-label {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}
.total-price {
  font-size: 24px;
  font-weight: 700;
  color: var(--danger-color);
}

/* ===== 底部固定栏 ===== */
.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 20px;
  background: white;
  border-top: 1px solid var(--border-color);
  flex-shrink: 0;
  z-index: 10;
}
.total-display {
  display: flex;
  align-items: baseline;
  gap: 6px;
}
.total-label {
  font-size: 14px;
  color: var(--text-muted);
}
.total-amount {
  font-size: 26px;
  font-weight: 700;
  color: var(--danger-color);
}
.submit-btn {
  padding: 14px 44px;
  background: var(--primary-gradient);
  color: white;
  border: none;
  border-radius: var(--radius-full);
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
}
.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}

/* ===== 支付弹窗 ===== */
.modal-overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0, 0, 0, 0.35);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
  animation: fadeInUp 0.3s ease;
}
.modal-content {
  background: white;
  border-radius: var(--radius-lg);
  width: 90%;
  max-width: 400px;
  overflow: hidden;
  box-shadow: var(--shadow-xl);
  animation: scaleIn 0.3s ease;
}
.modal-header {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 20px 24px 16px;
  border-bottom: 1px solid var(--border-color);
}
.modal-icon { font-size: 28px; }
.modal-header h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
}
.modal-body {
  padding: 24px;
}
.pay-amount {
  font-size: 16px;
  color: var(--text-secondary);
  margin: 0 0 8px;
  text-align: center;
}
.pay-amount .highlight {
  font-size: 30px;
  font-weight: 700;
  color: var(--danger-color);
}
.pay-order {
  font-size: 13px;
  color: var(--text-muted);
  text-align: center;
  margin: 0 0 20px;
}
.pay-methods {
  display: flex;
  justify-content: center;
}
.pay-method {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 14px 28px;
  border: 2px solid var(--border-color);
  border-radius: var(--radius-sm);
  cursor: pointer;
  transition: all var(--transition-normal);
  color: var(--text-secondary);
}
.pay-method.active {
  border-color: var(--primary-color);
  background: var(--info-bg);
  color: var(--text-primary);
}
.method-icon { font-size: 22px; }

.modal-footer {
  display: flex;
  gap: 12px;
  padding: 16px 24px 24px;
  border-top: 1px solid var(--border-color);
}
.modal-btn {
  flex: 1;
  padding: 12px;
  border-radius: var(--radius-sm);
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
  border: none;
}
.cancel-btn {
  background: var(--bg-input);
  color: var(--text-secondary);
}
.cancel-btn:hover {
  background: #e4e8f0;
}
.confirm-btn {
  background: var(--primary-gradient);
  color: white;
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
}
.confirm-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}
</style>
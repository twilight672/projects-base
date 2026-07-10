<template>
  <div class="cart">
    <!-- 页面标题 -->
    <h1 class="page-title">购物车</h1>

    <!-- 空状态 -->
    <div v-if="cartItems.length === 0" class="empty-cart">
      <div class="empty-icon">🛒</div>
      <p class="empty-text">购物车空空如也</p>
      <p class="empty-hint">快去逛逛，把喜欢的书带回家吧～</p>
    </div>

    <!-- 商品列表 -->
    <div v-else class="cart-list">
      <!-- 全选操作栏 -->
      <div class="select-all-bar">
        <label class="checkbox-label">
          <input type="checkbox" v-model="allSelected" />
          <span>全选</span>
        </label>
        <span class="total-count">共 {{ cartItems.length }} 件商品</span>
      </div>

      <!-- 每个商品条目 -->
      <div
        v-for="item in cartItems"
        :key="item.bookId || item.id"
        class="cart-item"
      >
        <div class="item-checkbox">
          <input type="checkbox" v-model="item.selected" @change="onItemSelectChange" />
        </div>
        <img
          :src="item.cover || getPlaceholderCover(item.title)"
          alt="书籍封面"
          class="item-cover"
          @error="e => e.target.src = getPlaceholderCover(item.title)"
        />
        <div class="item-info">
          <div class="item-title">{{ item.title }}</div>
          <div class="item-author">{{ item.author || '未知作者' }}</div>
          <div class="item-price">¥{{ Number(item.price).toFixed(2) }}</div>
        </div>
        <div class="item-quantity">
          <button class="qty-btn" @click="changeQuantity(item, -1)">−</button>
          <span class="qty-num">{{ item.quantity }}</span>
          <button class="qty-btn" @click="changeQuantity(item, 1)">＋</button>
        </div>
        <button class="item-delete" @click="removeItem(item)">✕</button>
      </div>
    </div>

    <!-- 底部结算栏 -->
    <div class="bottom-bar" v-if="cartItems.length > 0">
      <label class="checkbox-label select-all-bottom">
        <input type="checkbox" v-model="allSelected" />
        <span>全选</span>
      </label>
      <div class="total-info">
        <span class="total-label">合计：</span>
        <span class="total-price">¥{{ totalPrice.toFixed(2) }}</span>
        <span class="total-detail">（已选 {{ selectedCount }} 件）</span>
      </div>
      <button class="checkout-btn" :disabled="selectedCount === 0" @click="handleCheckout">
        去结算
      </button>
    </div>
  </div>
</template>

<script>
// 购物车页面组件
import {
  getCart,
  removeFromCart,
  isLoggedIn
} from '@/utils/storage'
import { getPlaceholderCover } from '@/utils/placeholder'

export default {
  name: 'Cart',
  
  data() {
    return {
      // 购物车商品列表
      cartItems: []
    }
  },
  
  computed: {
    // 全选状态（双向绑定）
    allSelected: {
      get() {
        if (this.cartItems.length === 0) return false
        return this.cartItems.every(item => item.selected)
      },
      set(val) {
        this.cartItems.forEach(item => item.selected = val)
        this.persistCart()
      }
    },
    
    // 已选中的商品列表
    selectedItems() {
      return this.cartItems.filter(item => item.selected)
    },
    
    // 已选商品数量
    selectedCount() {
      return this.selectedItems.reduce((sum, item) => sum + (item.quantity || 1), 0)
    },
    
    // 已选商品总价
    totalPrice() {
      return this.selectedItems.reduce((sum, item) => {
        const price = Number(item.price) || 0
        const qty = Number(item.quantity) || 1
        return sum + price * qty
      }, 0)
    }
  },
  
  mounted() {
    this.loadCart()
  },
  
  methods: {
    // 加载购物车数据
    loadCart() {
      this.cartItems = getCart() || []
      this.cartItems = this.cartItems.map(item => ({
        ...item,
        selected: item.selected !== undefined ? item.selected : true,
        id: item.id
      }))
      this.$nextTick(() => {
        this.$forceUpdate()
      })
    },

    // 持久化购物车数据到localStorage
    persistCart() {
      localStorage.setItem('cart', JSON.stringify(this.cartItems))
    },

    // 单个商品选中状态变化时保存
    onItemSelectChange() {
      this.persistCart()
    },

    // 修改商品数量
    changeQuantity(item, delta) {
      const newQty = (item.quantity || 1) + delta
      if (newQty < 1) return
      item.quantity = newQty
      this.persistCart()
    },

    // 移除商品
    removeItem(item) {
      if (confirm(`确定要移除《${item.title}》吗？`)) {
        removeFromCart(item.id)
        this.loadCart()
      }
    },

    // 去结算
    handleCheckout() {
      if (this.selectedCount === 0) {
        alert('请至少选择一件商品')
        return
      }
      if (!isLoggedIn()) {
        alert('请先登录再结算')
        this.$router.push('/login')
        return
      }
      const selectedBooks = this.selectedItems.map(item => ({
        id: item.id,
        title: item.title,
        price: item.price,
        quantity: item.quantity,
        cover: item.cover,
        author: item.author
      }))
      this.$router.push({
        path: '/checkout',
        query: {
          fromCart: '1',
          data: JSON.stringify(selectedBooks)
        }
      })
    }
  }
}
</script>

<style scoped>
.cart {
  padding: 20px 16px 180px;
  min-height: 100vh;
  background: var(--bg-page);
}

.page-title {
  color: var(--text-primary);
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 24px;
}

/* ===== 空状态 ===== */
.empty-cart {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 80px;
  color: var(--text-muted);
}
.empty-icon {
  font-size: 72px;
  margin-bottom: 16px;
  opacity: 0.4;
}
.empty-text {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-secondary);
  margin-bottom: 8px;
}
.empty-hint {
  font-size: 14px;
  color: var(--text-muted);
}

/* ===== 全选操作栏 ===== */
.select-all-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 4px 14px;
  color: var(--text-secondary);
  font-size: 14px;
  border-bottom: 1px solid var(--border-color);
  margin-bottom: 16px;
}
.select-all-bar .total-count {
  font-size: 13px;
  color: var(--text-muted);
}

/* ===== 商品列表 ===== */
.cart-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

/* ===== 单个商品 ===== */
.cart-item {
  display: flex;
  align-items: center;
  background: white;
  border-radius: var(--radius-md);
  padding: 14px 16px;
  border: 1px solid var(--border-color);
  transition: all var(--transition-normal);
  box-shadow: var(--shadow-xs);
}
.cart-item:hover {
  transform: translateY(-3px);
  box-shadow: var(--shadow-md);
  border-color: var(--primary-light);
}

.item-checkbox {
  margin-right: 14px;
}
.item-checkbox input[type="checkbox"] {
  width: 20px;
  height: 20px;
  accent-color: var(--primary-color);
  cursor: pointer;
}

.item-cover {
  width: 60px;
  height: 80px;
  object-fit: cover;
  border-radius: var(--radius-sm);
  flex-shrink: 0;
  margin-right: 14px;
  background: var(--bg-input);
}

.item-info {
  flex: 1;
  min-width: 0;
}
.item-title {
  font-size: 15px;
  font-weight: 500;
  color: var(--text-primary);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 4px;
}
.item-author {
  font-size: 13px;
  color: var(--text-muted);
  margin-bottom: 4px;
}
.item-price {
  font-size: 16px;
  font-weight: 600;
  color: var(--danger-color);
}

/* ===== 数量控制器 ===== */
.item-quantity {
  display: flex;
  align-items: center;
  gap: 6px;
  margin: 0 12px;
}
.qty-btn {
  width: 32px;
  height: 32px;
  border: 1.5px solid var(--border-color);
  border-radius: var(--radius-sm);
  background: transparent;
  color: var(--text-secondary);
  font-size: 18px;
  font-weight: 300;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all var(--transition-normal);
}
.qty-btn:hover {
  border-color: var(--primary-color);
  color: var(--primary-color);
  background: var(--info-bg);
}
.qty-btn:active {
  transform: scale(0.92);
}
.qty-num {
  min-width: 28px;
  text-align: center;
  font-size: 16px;
  font-weight: 500;
  color: var(--text-primary);
}

/* ===== 删除按钮 ===== */
.item-delete {
  background: none;
  border: none;
  color: var(--text-muted);
  font-size: 18px;
  cursor: pointer;
  padding: 6px 10px;
  border-radius: var(--radius-sm);
  transition: all var(--transition-normal);
}
.item-delete:hover {
  color: var(--danger-color);
  background: var(--danger-bg);
}

/* ===== 底部结算栏 ===== */
.bottom-bar {
  position: fixed;
  bottom: 80px;
  left: 16px;
  right: 16px;
  height: 72px;
  background: white;
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  padding: 0 20px;
  gap: 12px;
  box-sizing: border-box;
  z-index: 100;
  box-shadow: var(--shadow-lg);
}

.select-all-bottom {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--text-secondary);
  font-size: 14px;
  cursor: pointer;
  flex-shrink: 0;
}
.select-all-bottom input[type="checkbox"] {
  width: 20px;
  height: 20px;
  accent-color: var(--primary-color);
  cursor: pointer;
}

.total-info {
  flex: 1;
  display: flex;
  align-items: baseline;
  gap: 6px;
  min-width: 0;
}
.total-label {
  font-size: 14px;
  color: var(--text-muted);
}
.total-price {
  font-size: 26px;
  font-weight: 700;
  color: var(--danger-color);
}
.total-detail {
  font-size: 13px;
  color: var(--text-muted);
}

.checkout-btn {
  padding: 12px 32px;
  background: var(--primary-gradient);
  color: white;
  border: none;
  border-radius: var(--radius-full);
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
  flex-shrink: 0;
}
.checkout-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}
.checkout-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
  transform: none;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  user-select: none;
}
.checkbox-label input[type="checkbox"] {
  width: 20px;
  height: 20px;
  accent-color: var(--primary-color);
  cursor: pointer;
}
</style>
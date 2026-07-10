<!--
  BookDetail.vue - 商品详情页
  功能：
    ① 从路由id获取书籍详情，展示大图、书名、作者、价格、简介
    ② "加入购物车"按钮：存储到localStorage购物车数组
    ③ "立即购买"按钮：跳过购物车，直接将该商品携带到结算页面
    ④ 收藏/取消收藏功能
    ⑤ 页面内返回按钮
  布局：
    - 外层固定高度的容器
    - 滚动区展示商品大图、标题/价格、作者/简介、书籍详情描述、评论
    - 底部固定栏放置"加入购物车"和"立即购买"按钮
    - 滚动区内容要避开底部按钮高度（padding-bottom）
-->
<template>
  <div class="book-detail">
    <!-- 页面内返回按钮（固定在左上角） -->
    <button class="back-btn" @click="goBack">← 返回</button>

    <!-- 滚动区 -->
    <div v-if="book" class="scroll-area">
      <!-- 1. 商品轮播图 -->
      <div class="carousel-container">
        <div class="carousel-wrapper">
          <div class="carousel-item" v-for="(img, index) in carouselImages" :key="index">
            <img 
              :src="img" 
              alt="书籍图片" 
              @error="handleImageError($event, index)"
            />
          </div>
        </div>
        <div class="carousel-indicators" v-if="carouselImages.length > 1">
          <span 
            v-for="(_, index) in carouselImages" 
            :key="index" 
            :class="['indicator', { active: currentCarouselIndex === index }]"
            @click="currentCarouselIndex = index"
          ></span>
        </div>
      </div>

      <!-- 2. 价格区域 -->
      <div class="price-section">
        <div class="price-row">
          <span class="current-price">¥{{ book.price.toFixed(2) }}</span>
          <span v-if="book.originalPrice" class="original-price">¥{{ book.originalPrice.toFixed(2) }}</span>
        </div>
        <div class="tags-row">
          <span class="tag tag-hot">大促直降</span>
          <span class="tag tag-sales">已售{{ book.sales }}件</span>
          <span v-if="book.discount" class="tag tag-discount">{{ book.discount }}</span>
        </div>
      </div>

      <!-- 3. 商品标题和描述 -->
      <div class="info-section">
        <h1 class="book-title">{{ book.title }}</h1>
        <p class="book-author">作者：{{ book.author || '未知作者' }}</p>
        <p class="book-brief">{{ book.description || '暂无描述' }}</p>
        <div class="book-meta">
          <span>分类：{{ book.category || getCategoryName(book.categoryId) || '其他' }}</span>
        </div>
      </div>

      <!-- 4. 评价摘要 -->
      <div class="review-summary">
        <div class="review-header">
          <h3>用户评价</h3>
          <span class="review-count">共{{ reviewCount }}条</span>
        </div>
        <div v-if="previewReviews.length > 0" class="review-list">
          <div v-for="review in previewReviews" :key="review.id" class="review-item">
            <div class="review-header-item">
              <span class="reviewer-name">{{ review.userName }}</span>
              <span class="review-time">{{ formatTime(review.createdAt) }}</span>
            </div>
            <p class="review-content">{{ review.content }}</p>
          </div>
        </div>
        <div v-else class="review-empty">
          <p>暂无评价，快来发表第一条评价吧</p>
        </div>
      </div>

      <!-- 完整评论区 -->
      <div class="comment-section">
        <CommentSection :bookId="book.id" />
      </div>
    </div>

    <!-- 加载中/未找到书籍 -->
    <div v-else class="loading">书籍不存在或正在加载...</div>

    <!-- 6. 底部固定栏 -->
    <div class="bottom-bar">
      <button class="fav-btn" @click="handleToggleFav">
        <span class="icon">{{ isFav ? '★' : '☆' }}</span>
        <span class="text">{{ isFav ? '已收藏' : '收藏' }}</span>
      </button>
      <button class="cart-btn" @click="handleAddCart">加入购物车</button>
      <button class="buy-btn" @click="handleBuyNow">立即购买</button>
    </div>
  </div>
</template>

<script>
// 书籍详情页面组件
import { getBookById, getCategoryName, getCommentsByBookId, incrementBookViews, incrementBookClicks } from '@/mock/bookData'
import { isLoggedIn, addToCart, addFavorite, removeFavorite, isFavorite } from '@/utils/storage'
import CommentSection from '@/components/CommentSection.vue'

export default {
  name: 'BookDetail',
  components: {
    CommentSection
  },
  data() {
    return {
      book: null,
      isFav: false,
      currentCarouselIndex: 0,
      reviews: []
    }
  },
  computed: {
    carouselImages() {
      if (!this.book) return []
      const cover = this.book.cover || 'https://picsum.photos/300/400?random=1'
      return [cover]
    },
    reviewCount() {
      return this.reviews.length
    },
    previewReviews() {
      return this.reviews.slice(0, 3)
    }
  },
  mounted() {
    this.initDetail()
  },
  methods: {
    // 获取分类名称
    getCategoryName(categoryId) {
      return getCategoryName(categoryId)
    },
    // 页面内返回按钮
    goBack() {
      // 优先使用浏览器历史返回
      if (window.history.length > 1) {
        this.$router.back()
      } else {
        this.$router.push('/')
      }
    },
    // 图片加载失败处理
    handleImageError(event, index) {
      const fallbackImage = 'data:image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg" width="300" height="400" viewBox="0 0 300 400"%3E%3Crect fill="%23f5f5f5" width="300" height="400"/%3E%3Crect fill="%23e0e0e0" x="30" y="40" width="240" height="320" rx="4"/%3E%3Ctext fill="%23999" font-family="Arial" font-size="16" x="150" y="200" text-anchor="middle"%3E书籍封面%3C/text%3E%3Ctext fill="%23bbb" font-family="Arial" font-size="12" x="150" y="225" text-anchor="middle"%3EBook Cover%3C/text%3E%3C/svg%3E'
      event.target.src = fallbackImage
    },
    // 初始化详情数据
    initDetail() {
      const bookId = Number(this.$route.params.id)
      console.log('BookDetail - initDetail:', {
        routeParams: this.$route.params,
        bookId,
        bookIdType: typeof bookId
      })
      this.book = getBookById(bookId)
      console.log('BookDetail - book found:', this.book)

      if (!this.book) return

      // 增加浏览量
      incrementBookViews(bookId)
      // 增加点击量
      incrementBookClicks(bookId)

      this.isFav = isFavorite(this.book.id)
      this.loadReviews()
    },
    loadReviews() {
      if (!this.book) return
      this.reviews = getCommentsByBookId(this.book.id)
    },

    // ④ 收藏/取消收藏
    handleToggleFav() {
      // 判断是否登录
      if (!isLoggedIn()) {
        alert('请先登录再收藏！')
        this.$router.push('/login')
        return
      }
      if (this.isFav) {
        removeFavorite(this.book.id)
      } else {
        addFavorite(this.book.id)
      }
      this.isFav = !this.isFav
    },

    // ② "加入购物车"：存储到localStorage购物车数组（数量默认为1，已存在则增加数量）
    handleAddCart() {
      if (!this.book) return
      addToCart(this.book)
      alert('成功加入购物车！')
    },

    // ③ "立即购买"：跳过购物车，直接将该商品携带到结算页面（相当于只购买这一件）
    handleBuyNow() {
      if (!this.book) return
      if (!isLoggedIn()) {
        alert('请先登录再下单！')
        this.$router.push('/login')
        return
      }
      // 携带单商品跳转到结算页（路由参数传递）
      this.$router.push({
        path: '/checkout',
        query: {
          buyNow: 1,
          bookId: this.book.id
        }
      })
    },
    formatTime(dateStr) {
      const date = new Date(dateStr)
      return date.toLocaleDateString()
    }
  },
  watch: {
    '$route.params.id'() {
      this.initDetail()
    }
  }
}
</script>

<style scoped>
.book-detail {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: var(--bg-page);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  z-index: 1001;
}

/* ===== 返回按钮 ===== */
.back-btn {
  position: absolute;
  top: 14px;
  left: 14px;
  z-index: 20;
  border: none;
  background: white;
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
  cursor: pointer;
  padding: 10px 18px;
  border-radius: var(--radius-full);
  box-shadow: var(--shadow-sm);
  transition: all var(--transition-normal);
  display: flex;
  align-items: center;
  gap: 6px;
}
.back-btn:hover {
  box-shadow: var(--shadow-md);
  transform: translateX(-2px);
}

/* ===== 滚动区 ===== */
.scroll-area {
  flex: 1;
  overflow-y: auto;
  padding: 64px 16px 80px;
  -webkit-overflow-scrolling: touch;
}
.scroll-area::-webkit-scrollbar { width: 4px; }
.scroll-area::-webkit-scrollbar-thumb {
  background: #d0d7e6;
  border-radius: 4px;
}

/* ===== 轮播图 ===== */
.carousel-container {
  position: relative;
  margin-bottom: 16px;
  border-radius: var(--radius-md);
  overflow: hidden;
  background: white;
  border: 1px solid var(--border-color);
}
.carousel-wrapper {
  display: flex;
  overflow: hidden;
}
.carousel-item {
  flex: 0 0 100%;
  width: 100%;
}
.carousel-item img {
  width: 100%;
  height: 320px;
  object-fit: contain;
  background: #f8faff;
}
.carousel-indicators {
  position: absolute;
  bottom: 16px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 8px;
}
.indicator {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: rgba(0, 0, 0, 0.15);
  cursor: pointer;
  transition: all var(--transition-normal);
}
.indicator.active {
  width: 24px;
  border-radius: 4px;
  background: var(--primary-color);
}

/* ===== 价格区域 ===== */
.price-section {
  background: white;
  border-radius: var(--radius-md);
  padding: 20px;
  margin-bottom: 12px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}
.price-row {
  display: flex;
  align-items: baseline;
  gap: 14px;
  margin-bottom: 12px;
}
.current-price {
  font-size: 30px;
  font-weight: 700;
  color: var(--danger-color);
}
.original-price {
  font-size: 16px;
  color: var(--text-muted);
  text-decoration: line-through;
}
.tags-row {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}
.tag {
  padding: 4px 14px;
  border-radius: var(--radius-full);
  font-size: 12px;
  font-weight: 600;
}
.tag-hot {
  background: var(--danger-bg);
  color: var(--danger-color);
}
.tag-sales {
  background: var(--warning-bg);
  color: var(--warning-color);
}
.tag-discount {
  background: var(--success-bg);
  color: var(--success-color);
}

/* ===== 商品信息 ===== */
.info-section {
  background: white;
  border-radius: var(--radius-md);
  padding: 20px;
  margin-bottom: 12px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}
.book-title {
  margin: 0 0 10px;
  font-size: 22px;
  font-weight: 700;
  color: var(--text-primary);
  line-height: 1.4;
}
.book-author {
  font-size: 15px;
  color: var(--text-secondary);
  margin-bottom: 10px;
}
.book-brief {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.8;
  margin-bottom: 14px;
}
.book-meta {
  font-size: 13px;
  color: var(--text-muted);
  padding-top: 14px;
  border-top: 1px solid var(--border-color);
}

/* ===== 评价摘要 ===== */
.review-summary {
  background: white;
  border-radius: var(--radius-md);
  padding: 20px;
  margin-bottom: 12px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}
.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}
.review-header h3 {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}
.review-count {
  font-size: 13px;
  color: var(--text-muted);
}
.review-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}
.review-item {
  padding-bottom: 14px;
  border-bottom: 1px solid var(--border-color);
}
.review-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
}
.review-header-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 6px;
}
.reviewer-name {
  font-size: 13px;
  font-weight: 600;
  color: var(--text-secondary);
}
.review-time {
  font-size: 12px;
  color: var(--text-muted);
}
.review-content {
  margin: 0;
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
}
.review-empty {
  text-align: center;
  padding: 24px 0;
  color: var(--text-muted);
  font-size: 14px;
}

/* ===== 评论区 ===== */
.comment-section {
  background: white;
  border-radius: var(--radius-md);
  padding: 20px;
  margin-bottom: 12px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
}

/* ===== 加载状态 ===== */
.loading {
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  text-align: center;
  color: var(--text-muted);
  font-size: 16px;
  transform: translateY(-50%);
}

/* ===== 底部固定栏 ===== */
.bottom-bar {
  flex-shrink: 0;
  width: 100%;
  height: 72px;
  display: flex;
  align-items: center;
  background: white;
  border-top: 1px solid var(--border-color);
  padding: 8px 16px;
  box-sizing: border-box;
  z-index: 10;
  gap: 12px;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.04);
}

.fav-btn {
  width: 60px;
  height: 56px;
  border: none;
  background: var(--bg-input);
  border-radius: var(--radius-sm);
  color: var(--text-muted);
  font-size: 12px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 2px;
  transition: all var(--transition-normal);
}
.fav-btn:hover {
  background: var(--warning-bg);
}
.fav-btn .icon {
  font-size: 22px;
  transition: var(--transition-normal);
}
.fav-btn .icon.fav-active {
  color: #f9a825;
}

.cart-btn,
.buy-btn {
  flex: 1;
  height: 56px;
  border: none;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  border-radius: var(--radius-sm);
  transition: all var(--transition-normal);
}
.cart-btn {
  background: var(--bg-input);
  color: var(--text-primary);
}
.cart-btn:hover {
  background: #e4e8f0;
}
.buy-btn {
  background: var(--primary-gradient);
  color: white;
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
}
.buy-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}
</style>

<template>
  <!-- 收藏组件容器 -->
  <div class="favorites-inner">
    
    <!-- 未登录状态：提示用户登录 -->
    <div v-if="!isLoggedIn" class="login-tip">
      <p>登录后可查看收藏的书籍</p>
      <!-- 跳转到登录页面 -->
      <router-link to="/login" class="btn-link">去登录</router-link>
    </div>

    <!-- 已登录但无收藏：显示空状态提示 -->
    <div v-else-if="favoriteBooks.length === 0" class="empty-state">
      <!-- 爱心图标表示空收藏 -->
      <span class="empty-icon">❤️</span>
      <p>还没有收藏的书籍</p>
    </div>

    <!-- 有收藏内容：展示收藏列表 -->
    <div v-else class="favorite-list">
      <!-- 遍历所有收藏的书籍 -->
      <div
        v-for="book in favoriteBooks"
        :key="book.id"
        class="favorite-card"
        @click="goDetail(book.id)"
      >
        <!-- 书籍封面图片，支持加载失败时使用默认图片 -->
        <img :src="book.cover" :alt="book.title" class="book-cover" @error="handleImageError" />
        
        <!-- 书籍信息区域：标题、作者、价格 -->
        <div class="book-info">
          <h4 class="book-title">{{ book.title }}</h4>
          <p class="book-author">{{ book.author }}</p>
          <p class="book-price">¥{{ book.price.toFixed(2) }}</p>
        </div>
        
        <!-- 取消收藏按钮：使用 .stop 阻止事件冒泡，避免触发卡片点击事件 -->
        <button class="unfav-btn" @click.stop="handleRemoveFavorite(book.id)">取消收藏</button>
      </div>
    </div>
    
  </div>
</template>

<script>
// 收藏组件模块：展示用户收藏的书籍列表，支持取消收藏功能
import { isLoggedIn, getFavorites, removeFavorite } from '@/utils/storage'
import { getBookById } from '@/mock/bookData'

export default {
  name: 'Favorites',
  
  data() {
    return {
      // 收藏的书籍列表数组
      favoriteBooks: []
    }
  },
  
  computed: {
    // 判断用户是否已登录
    isLoggedIn() {
      return isLoggedIn()
    }
  },
  
  // 组件挂载后加载收藏数据
  mounted() {
    this.loadFavorites()
  },
  
  methods: {
    // 加载用户收藏的书籍列表
    loadFavorites() {
      if (!this.isLoggedIn) {
        this.favoriteBooks = []
        return
      }
      
      // 获取收藏的书籍ID数组
      const favIds = getFavorites()
      
      // 将ID数组转换为书籍对象数组，过滤无效ID
      this.favoriteBooks = favIds.map(id => getBookById(id)).filter(Boolean)
    },
    
    // 处理取消收藏操作
    handleRemoveFavorite(bookId) {
      if (confirm('确定取消收藏该书籍吗？')) {
        removeFavorite(bookId)
        this.loadFavorites()
        this.$emit('update')
      }
    },
    
    // 跳转到书籍详情页
    goDetail(bookId) {
      this.$router.push({ name: 'BookDetail', params: { id: bookId } })
    },
    
    // 处理图片加载失败事件
    handleImageError(e) {
      e.target.src = 'https://picsum.photos/300/400?random=1'
    }
  }
}
</script>

<style scoped>
/* 收藏组件容器 */
.favorites-inner {
  padding: 4px 0;
}

/* 未登录提示卡片 */
.login-tip {
  text-align: center;
  padding: 40px 20px;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(0, 212, 255, 0.1);
  border-radius: 12px;
}

.login-tip p {
  color: #9ca3af;
  margin-bottom: 12px;
}

/* 登录链接 */
.btn-link {
  color: #00d4ff;
  text-decoration: none;
  font-size: 14px;
}

.btn-link:hover {
  text-decoration: underline;
}

/* 空状态提示 */
.empty-state {
  text-align: center;
  padding: 40px 20px;
}

/* 空状态图标 */
.empty-icon {
  font-size: 48px;
  display: block;
  margin-bottom: 12px;
}

.empty-state p {
  color: #9ca3af;
  font-size: 14px;
}

/* 收藏列表容器 */
.favorite-list {
  display: flex;
  flex-direction: column;
  gap: 10px;  /* 卡片之间的间距 */
}

/* 单个收藏书籍卡片 */
.favorite-card {
  display: flex;
  align-items: center;
  gap: 14px;
  background: rgba(40, 40, 70, 0.6);
  border: 1px solid rgba(0, 212, 255, 0.1);
  border-radius: 10px;
  padding: 10px 14px;
  cursor: pointer;
  transition: all 0.3s;
}

/* 卡片悬停效果：边框高亮 + 轻微右移 */
.favorite-card:hover {
  border-color: rgba(0, 212, 255, 0.3);
  transform: translateX(4px);
}

/* 书籍封面图片 */
.book-cover {
  width: 56px;
  height: 76px;
  object-fit: cover;  /* 保持比例裁剪 */
  border-radius: 6px;
  flex-shrink: 0;  /* 不压缩 */
  background: rgba(0, 212, 255, 0.05);
}

/* 书籍信息区域 */
.book-info {
  flex: 1;
  min-width: 0;  /* 允许文字截断 */
}

/* 书籍标题：单行显示，超出省略 */
.book-title {
  font-size: 14px;
  font-weight: 500;
  color: #e5e7eb;
  margin: 0 0 4px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 书籍作者 */
.book-author {
  font-size: 12px;
  color: #9ca3af;
  margin: 0 0 4px;
}

/* 书籍价格：主题色高亮 */
.book-price {
  font-size: 15px;
  font-weight: 600;
  color: #00d4ff;
  margin: 0;
}

/* 取消收藏按钮 */
.unfav-btn {
  padding: 4px 12px;
  background: transparent;
  border: 1px solid rgba(244, 67, 54, 0.4);  /* 红色边框 */
  color: #ef4444;
  border-radius: 4px;
  font-size: 11px;
  cursor: pointer;
  transition: all 0.3s;
  flex-shrink: 0;  /* 不压缩 */
}

/* 按钮悬停效果：红色背景 */
.unfav-btn:hover {
  background: rgba(244, 67, 54, 0.1);
}
</style>
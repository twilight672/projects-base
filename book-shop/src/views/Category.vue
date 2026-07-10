<template>
  <!-- 
    Category.vue - 分类页组件
    功能：
      ① 左侧分类栏：展示12个书籍分类，点击切换
      ② 右侧书籍列表：按分类筛选展示书籍
      ③ 书籍卡片：显示封面、书名、作者、价格
      ④ 点击书籍跳转到详情页
      ⑤ 支持加载更多和空状态提示
    布局：
      - 左侧固定宽度分类栏
      - 右侧响应式书籍网格列表
  -->
  <div class="category-page">
    <!-- 左侧分类栏 -->
    <div class="category-sidebar">
      <div 
        class="category-item"
        v-for="category in categoryList"
        :key="category.id"
        :class="{ active: currentCategoryId === category.id }"
        @click="switchCategory(category.id)"
      >
        {{ category.name }}
      </div>
    </div>

    <!-- 右侧书籍列表 -->
    <div class="category-content">
      <div class="book-list">
        <div 
          class="book-card"
          v-for="book in currentBookList"
          :key="book.id"
          @click="goToDetail(book.id)"
        >
          <img :src="book.cover" alt="book cover" class="book-cover" />
          <div class="book-info">
            <h3 class="book-title">{{ book.title }}</h3>
            <p class="book-author">作者：{{ book.author }}</p>
            <p class="book-price">¥{{ book.price.toFixed(2) }}</p>
          </div>
        </div>
      </div>

      <!-- 加载更多按钮 -->
      <div class="load-more" v-if="hasMore">
        <button @click="loadMoreBooks">加载更多</button>
      </div>

      <!-- 空状态 -->
      <div class="empty-tip" v-if="currentBookList.length === 0">
        该分类下暂无书籍
      </div>
    </div>
  </div>
</template>

<script>
// 分类页面组件
import { CATEGORY_LIST } from '@/constants/category';
import { getBooksByCategory } from '@/mock/bookData';

export default {
  name: 'Category',
  
  data() {
    return {
      categoryList: CATEGORY_LIST, // 左侧分类列表
      currentCategoryId: 1, // 默认选中第一个分类
      currentBookList: [], // 当前分类下的书籍列表
      pageSize: 6, // 每页6条
      currentPage: 1, // 当前页码
      hasMore: true // 是否有更多数据
    };
  },
  
  created() {
    this.loadBooksByCategory(this.currentCategoryId);
  },
  
  methods: {
    // 切换分类
    switchCategory(categoryId) {
      this.currentCategoryId = categoryId;
      this.currentPage = 1; // 重置页码
      this.currentBookList = []; // 清空列表
      this.loadBooksByCategory(categoryId);
    },

    // 根据分类加载书籍（分页）
    loadBooksByCategory(categoryId) {
      const allBooks = getBooksByCategory(categoryId);
      const start = (this.currentPage - 1) * this.pageSize;
      const end = this.currentPage * this.pageSize;
      const newBooks = allBooks.slice(start, end);
      
      this.currentBookList = [...this.currentBookList, ...newBooks];
      
      // 判断是否还有更多数据
      this.hasMore = end < allBooks.length;
    },

    // 加载更多
    loadMoreBooks() {
      this.currentPage += 1;
      this.loadBooksByCategory(this.currentCategoryId);
    },

    // 跳转到书籍详情页（路由路径与router配置一致为 /book/:id）
    goToDetail(bookId) {
      this.$router.push({ path: `/book/${bookId}` });
    }
  }
};
</script>

<style scoped>
.category-page {
  display: flex;
  height: calc(100vh - 60px);
  background: var(--bg-page);
}

/* ===== 左侧分类栏 ===== */
.category-sidebar {
  width: 110px;
  background: white;
  border-right: 1px solid var(--border-color);
  padding: 16px 0;
  overflow-y: auto;
  flex-shrink: 0;
}
.category-sidebar::-webkit-scrollbar { width: 3px; }
.category-sidebar::-webkit-scrollbar-thumb {
  background: #d0d7e6;
  border-radius: 4px;
}

.category-item {
  height: 48px;
  line-height: 48px;
  text-align: center;
  cursor: pointer;
  color: var(--text-muted);
  font-size: 13px;
  font-weight: 500;
  transition: all var(--transition-normal);
  position: relative;
  padding: 0 8px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.category-item:hover:not(.active) {
  color: var(--text-secondary);
  background: var(--bg-card-hover);
}
.category-item.active {
  background: var(--info-bg);
  color: var(--primary-color);
  font-weight: 600;
}
.category-item.active::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 24px;
  background: var(--primary-color);
  border-radius: 0 4px 4px 0;
}

/* ===== 右侧内容 ===== */
.category-content {
  flex: 1;
  padding: 20px 24px;
  overflow-y: auto;
  background: var(--bg-page);
}
.category-content::-webkit-scrollbar { width: 4px; }
.category-content::-webkit-scrollbar-thumb {
  background: #d0d7e6;
  border-radius: 4px;
}

/* ===== 书籍网格 ===== */
.book-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(170px, 1fr));
  gap: 20px;
}

/* ===== 书籍卡片 ===== */
.book-card {
  background: white;
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  padding: 14px;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: var(--shadow-xs);
}
.book-card:hover {
  transform: translateY(-6px);
  box-shadow: var(--shadow-lg);
  border-color: var(--primary-light);
}

.book-cover {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: var(--radius-sm);
  margin-bottom: 12px;
  background: var(--bg-input);
}

.book-info {
  padding: 2px 0;
}
.book-title {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0 0 4px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.book-author {
  font-size: 12px;
  color: var(--text-muted);
  margin: 0 0 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.book-price {
  font-size: 17px;
  font-weight: 700;
  color: var(--primary-color);
  margin: 0;
}

/* ===== 加载更多 ===== */
.load-more {
  text-align: center;
  margin-top: 28px;
}
.load-more button {
  padding: 12px 40px;
  background: var(--primary-gradient);
  color: white;
  border: none;
  border-radius: var(--radius-full);
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
}
.load-more button:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}

/* ===== 空状态 ===== */
.empty-tip {
  text-align: center;
  padding: 80px 0;
  color: var(--text-muted);
  font-size: 16px;
}
</style>
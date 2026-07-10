<template>
  <!-- 
    Home.vue - 首页组件
    功能：
      ① 顶部搜索框：支持按书名/作者搜索书籍
      ② 轮播图：展示热销书籍，自动轮播
      ③ 书籍列表：按销量排序，分页加载
      ④ 下拉刷新：移动端支持下拉刷新列表
      ⑤ 上拉加载：滚动到底部自动加载下一页
    布局：
      - 搜索框固定在顶部
      - 轮播图位于搜索框下方
      - 书籍列表以卡片形式展示
      - 底部有加载提示和留白避开TabBar
  -->
  <div class="home-page" @touchstart="touchStart" @touchend="touchEnd">
    <!-- 下拉刷新提示 -->
    <div class="pull-refresh" v-if="pullStatus !== 'normal'">
      {{ pullStatus === 'pulling' ? '下拉松开刷新' : '正在刷新...' }}
    </div>

    <!-- 顶部搜索框 -->
    <div class="search-box">
      <input
        v-model="searchKey"
        type="text"
        placeholder="输入书名/作者搜索"
        @keyup.enter="goSearch"
      />
      <button @click="goSearch">搜索</button>
      <!-- 电脑端测试刷新按钮，手机不用管 -->
      <button class="refresh-btn" @click="manualRefresh">刷新</button>
    </div>

    <!-- 顶部轮播 -->
    <div class="swiper-box">
      <div 
        class="swiper-item" 
        v-for="(item, idx) in swiperBooks" 
        :key="idx" 
        v-show="currentSwiper === idx"
        @click="goDetail(item.id)"
      >
        <img 
          :src="item.cover" 
          :alt="item.title"
          @error="$event.target.src='https://picsum.photos/id/24/800/400'"
        />
        <div class="swiper-title">{{ item.title }}</div>
      </div>
      <div class="dots">
        <span 
          v-for="(item, idx) in swiperBooks" 
          :key="idx" 
          :class="['dot', currentSwiper === idx ? 'active' : '']"
        ></span>
      </div>
    </div>

    <div class="list-title">热销好书（每页6本）</div>

    <!-- 书籍列表 立体书本样式 -->
    <div class="book-list">
      <div 
        class="book-card" 
        v-for="book in showBookList" 
        :key="book.id" 
        @click="goDetail(book.id)"
      >
        <!-- 书本立体外壳 -->
        <div class="book-wrap">
          <div class="book-left-side"></div>
          <div class="book-main">
            <img 
              :src="book.cover" 
              class="book-img"
              @error="$event.target.src='https://picsum.photos/id/24/300/400'"
            />
          </div>
        </div>
        <div class="book-desc">
          <h4 class="book-name">{{ book.title }}</h4>
          <p class="book-author">{{ book.author }}</p>
          <p class="book-price">¥{{ book.price.toFixed(2) }}</p>
        </div>
      </div>
    </div>

    <!-- 底部加载提示 -->
    <div class="load-tips" v-if="loadText">
      {{ loadText }}
    </div>

    <!-- 底部留白避开Tab栏 -->
    <div style="height:70px;"></div>
  </div>
</template>

<script>
import { bookList, getTopSellingBooks, searchBooks } from '@/mock/bookData'
export default {
  name: 'Home',
  data() {
    return {
      searchKey: '',
      // 轮播
      swiperBooks: [],
      currentSwiper: 0,
      swiperTimer: null,
      // 分页 每页6本
      page: 1,
      pageSize: 6,
      showBookList: [],
      allBookCache: [],
      loadText: '',
      // 下拉刷新
      pullStatus: 'normal',
      startY: 0,
      pullDistance: 0
    }
  },
  mounted() {
    this.initPage()
    window.addEventListener('scroll', this.handleScroll)
  },
  beforeDestroy() {
    clearInterval(this.swiperTimer)
    window.removeEventListener('scroll', this.handleScroll)
  },
  methods: {
    // 页面初始化
    initPage() {
      this.swiperBooks = getTopSellingBooks(6)
      this.startSwiper()
      // 按销量降序
      this.allBookCache = [...bookList].sort((a, b) => b.sales - a.sales)
      this.page = 1
      this.loadBookList()
    },
    // 自动轮播
    startSwiper() {
      this.swiperTimer = setInterval(() => {
        this.currentSwiper = (this.currentSwiper + 1) % this.swiperBooks.length
      }, 3000)
    },
    // 分页加载核心：每页固定6条
    loadBookList() {
      this.loadText = '加载中...'
      const start = (this.page - 1) * this.pageSize
      const end = this.page * this.pageSize
      const pageData = this.allBookCache.slice(start, end)

      if (this.page === 1) {
        this.showBookList = pageData
      } else {
        this.showBookList = [...this.showBookList, ...pageData]
      }

      // 判断是否全部加载完成
      if (end >= this.allBookCache.length) {
        this.loadText = '没有更多书籍了'
      } else {
        this.loadText = ''
      }
      this.pullStatus = 'normal'
    },
    // 滚动到底部上拉加载下一页
    handleScroll() {
      const scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      const scrollHeight = document.documentElement.scrollHeight
      const clientHeight = document.documentElement.clientHeight
      if (
        scrollTop + clientHeight >= scrollHeight - 80
        && this.loadText !== '加载中...'
        && this.loadText !== '没有更多书籍了'
      ) {
        this.page++
        this.loadBookList()
      }
    },
    // 触摸下拉刷新
    touchStart(e) {
      if (document.documentElement.scrollTop === 0) {
        this.startY = e.touches[0].clientY
      }
    },
    touchEnd(e) {
      if (!this.startY) return
      const endY = e.changedTouches[0].clientY
      this.pullDistance = endY - this.startY
      if (this.pullDistance > 80 && this.pullStatus === 'pulling') {
        this.pullStatus = 'loading'
        this.page = 1
        setTimeout(() => this.loadBookList(), 1000)
      }
      this.startY = 0
    },
    // 电脑端手动刷新按钮
    manualRefresh() {
      this.pullStatus = 'loading'
      this.page = 1
      setTimeout(() => this.loadBookList(), 800)
    },
    // 搜索
    goSearch() {
      const keyword = this.searchKey.trim()
      if (!keyword) return
      this.allBookCache = searchBooks(keyword).sort((a, b) => b.sales - a.sales)
      this.page = 1
      this.loadBookList()
      this.searchKey = ''
    },
    // 跳转详情动态路由
    goDetail(bookId) {
      this.$router.push({
        name: 'BookDetail',
        params: { id: bookId }
      })
    }
  }
}
</script>

<style scoped>
.home-page {
  padding-bottom: 60px;
  background: var(--bg-page);
  min-height: 100vh;
  position: relative;
}

/* ===== 下拉刷新提示 ===== */
.pull-refresh {
  text-align: center;
  padding: 14px;
  color: var(--text-muted);
  font-size: 14px;
  font-weight: 500;
}

/* ===== 搜索栏 ===== */
.search-box {
  display: flex;
  padding: 14px 16px;
  background: white;
  border-bottom: 1px solid var(--border-color);
  gap: 10px;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 10;
  box-shadow: var(--shadow-xs);
}
.search-box input {
  flex: 1;
  height: 42px;
  padding: 0 18px;
  background: var(--bg-input);
  border: 1.5px solid transparent;
  border-radius: var(--radius-full);
  outline: none;
  color: var(--text-primary);
  font-size: 14px;
  transition: all var(--transition-normal);
}
.search-box input:focus {
  border-color: var(--primary-color);
  background: white;
  box-shadow: 0 0 0 4px rgba(79, 140, 247, 0.08);
}
.search-box input::placeholder {
  color: var(--text-muted);
}
.search-box button {
  height: 42px;
  padding: 0 22px;
  background: var(--primary-gradient);
  color: white;
  border: none;
  border-radius: var(--radius-full);
  font-weight: 600;
  font-size: 14px;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.20);
  flex-shrink: 0;
}
.search-box button:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.25);
}
.refresh-btn {
  background: var(--bg-input) !important;
  box-shadow: none !important;
  color: var(--text-secondary) !important;
}
.refresh-btn:hover {
  background: #e4e8f0 !important;
  transform: none !important;
}

/* ===== 轮播图 ===== */
.swiper-box {
  position: relative;
  height: 190px;
  overflow: hidden;
  margin: 14px 16px;
  border-radius: var(--radius-md);
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xs);
  background: white;
}
.swiper-item {
  width: 100%;
  height: 100%;
  cursor: pointer;
}
.swiper-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.swiper-title {
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  padding: 12px 18px;
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.6));
  color: white;
  font-size: 15px;
  font-weight: 600;
}
.dots {
  position: absolute;
  bottom: 14px;
  right: 16px;
  display: flex;
  gap: 8px;
}
.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.4);
  transition: all var(--transition-normal);
  cursor: pointer;
}
.dot.active {
  background: white;
  width: 24px;
  border-radius: 4px;
}

/* ===== 标题 ===== */
.list-title {
  padding: 8px 16px 16px;
  font-size: 18px;
  font-weight: 700;
  color: var(--text-primary);
}

/* ===== 书籍列表 ===== */
.book-list {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px 12px;
  padding: 0 16px;
}

/* ===== 书籍卡片 ===== */
.book-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  transition: all var(--transition-normal);
}
.book-card:hover .book-wrap {
  transform: rotateY(-8deg) scale(1.04);
}
.book-card:hover .book-desc .book-name {
  color: var(--primary-color);
}

.book-wrap {
  position: relative;
  width: 100%;
  max-width: 150px;
  aspect-ratio: 3/4;
  transform: rotateY(-20deg);
  transform-style: preserve-3d;
  transition: all var(--transition-normal);
}
.book-left-side {
  position: absolute;
  left: 0;
  top: 0;
  width: 10%;
  height: 100%;
  background: linear-gradient(90deg, #e8ecf3, transparent);
  border-radius: 4px 0 0 4px;
}
.book-main {
  position: absolute;
  left: 10%;
  top: 0;
  width: 90%;
  height: 100%;
  background: white;
  border-radius: 0 var(--radius-sm) var(--radius-sm) 0;
  overflow: hidden;
  box-shadow: 4px 4px 16px rgba(0, 0, 0, 0.10);
  border: 1px solid var(--border-color);
}
.book-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.book-desc {
  width: 100%;
  max-width: 150px;
  margin-top: 12px;
  text-align: center;
}
.book-name {
  margin: 0;
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  transition: var(--transition-normal);
}
.book-author {
  font-size: 12px;
  color: var(--text-muted);
  margin: 4px 0;
}
.book-price {
  font-size: 16px;
  font-weight: 700;
  color: var(--primary-color);
  margin: 0;
}

/* ===== 加载提示 ===== */
.load-tips {
  text-align: center;
  padding: 16px;
  color: var(--text-muted);
  font-size: 14px;
}
</style>
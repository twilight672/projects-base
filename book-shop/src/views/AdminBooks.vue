<template>
  <!-- 书籍管理页面：用于管理所有书籍的上架、下架、价格修改等操作 -->
  <div class="admin-page">
    <div class="admin-container">
      <!-- 左侧固定导航栏 -->
      <aside class="sidebar">
        <!-- Logo区域 -->
        <div class="sidebar-header">
          <div class="logo">
            <span class="logo-icon">⚙️</span>
            <span class="logo-text">管理后台</span>
          </div>
        </div>
        
        <!-- 导航菜单 -->
        <nav class="sidebar-nav">
          <!-- 仪表盘入口 -->
          <router-link 
            to="/admin/dashboard" 
            class="nav-item" 
            :class="{ active: $route.path === '/admin/dashboard' }"
          >
            <span class="nav-icon">📊</span>
            <span class="nav-text">仪表盘</span>
          </router-link>
          <!-- 书籍管理入口（当前页面） -->
          <router-link 
            to="/admin/books" 
            class="nav-item" 
            :class="{ active: $route.path === '/admin/books' }"
          >
            <span class="nav-icon">📚</span>
            <span class="nav-text">书籍管理</span>
          </router-link>
          <!-- 账号管理入口 -->
          <router-link 
            to="/admin/users" 
            class="nav-item" 
            :class="{ active: $route.path === '/admin/users' }"
          >
            <span class="nav-icon">👥</span>
            <span class="nav-text">账号管理</span>
          </router-link>
        </nav>
        
        <!-- 底部退出按钮 -->
        <div class="sidebar-footer">
          <button class="logout-btn" @click="handleLogout">
            <span class="logout-icon">🚪</span>
            <span>退出登录</span>
          </button>
        </div>
      </aside>
      
      <!-- 右侧主内容区域 -->
      <main class="main-content">
        <!-- 顶部标题栏：包含页面标题、搜索框、上架按钮和管理员信息 -->
        <header class="main-header">
          <div class="header-left">
            <h1>{{ pageTitle }}</h1>
            <p class="header-subtitle">共 {{ books.length }} 本图书</p>
          </div>
          <div class="header-right">
            <!-- 搜索框：支持按书籍名称搜索 -->
            <div class="search-bar">
              <input 
                type="text" 
                v-model="searchKeyword" 
                placeholder="搜索书籍名称..."
                class="search-input"
              />
              <span class="search-icon">🔍</span>
            </div>
            <!-- 上架新书按钮 -->
            <button class="add-btn" @click="showAddModal = true">
              <span class="add-icon">➕</span>
              <span>上架书籍</span>
            </button>
            <!-- 当前登录管理员信息 -->
            <div class="admin-info">
              <span class="admin-icon">👑</span>
              <span class="admin-name">{{ currentAdmin.userName }}</span>
            </div>
          </div>
        </header>
        
        <!-- 内容区域 -->
        <div class="content-wrapper">
          <!-- 状态筛选按钮组：全部/上架中/已下架 -->
          <div class="filter-bar">
            <button 
              class="filter-btn" 
              :class="{ active: filterStatus === 'all' }"
              @click="filterStatus = 'all'"
            >
              全部 ({{ books.length }})
            </button>
            <button 
              class="filter-btn" 
              :class="{ active: filterStatus === 'online' }"
              @click="filterStatus = 'online'"
            >
              上架中 ({{ onlineCount }})
            </button>
            <button 
              class="filter-btn" 
              :class="{ active: filterStatus === 'offline' }"
              @click="filterStatus = 'offline'"
            >
              已下架 ({{ offlineCount }})
            </button>
          </div>
          
          <!-- 书籍卡片网格：展示所有符合条件的书籍 -->
          <div class="books-grid">
            <!-- 单个书籍卡片 -->
            <div 
              v-for="book in filteredBooks" 
              :key="book.id" 
              class="book-card"
              :class="{ offline: book.status === 'offline' }"
            >
              <!-- 书籍封面区域 -->
              <div class="book-cover">
                <img 
                  :src="getBookCover(book.name)" 
                  :alt="book.name"
                  class="book-cover-img"
                />
                <!-- 下架状态标签 -->
                <div v-if="book.status === 'offline'" class="offline-badge">已下架</div>
              </div>
              <!-- 书籍基本信息 -->
              <div class="book-info">
                <h3 class="book-name">{{ book.name }}</h3>
                <p class="book-author">作者: {{ book.author }}</p>
                <p class="book-category">分类: {{ book.category }}</p>
                <p class="book-price">¥{{ book.price.toFixed(2) }}</p>
                <p class="book-views">📊 {{ getBookViews(book.id) }} 次浏览</p>
              </div>
              <!-- 操作按钮：修改价格、上架/下架 -->
              <div class="book-actions">
                <button 
                  class="action-btn edit-btn" 
                  @click="openEditModal(book)"
                >
                  ✏️ 修改价格
                </button>
                <button 
                  class="action-btn"
                  :class="book.status === 'online' ? 'offline-btn' : 'online-btn'"
                  @click="toggleBookStatus(book)"
                >
                  {{ book.status === 'online' ? '下架' : '上架' }}
                </button>
              </div>
            </div>
          </div>
          
          <!-- 空状态提示：当没有符合条件的书籍时显示 -->
          <div v-if="filteredBooks.length === 0" class="empty-state">
            <span class="empty-icon">📚</span>
            <p>暂无书籍</p>
          </div>
        </div>
      </main>
    </div>
    
    <!-- 上架新书弹窗 -->
    <div v-if="showAddModal" class="modal-overlay" @click.self="closeAddModal">
      <div class="modal-content">
        <div class="modal-header">
          <h2>上架新书</h2>
          <button class="close-btn" @click="closeAddModal">✕</button>
        </div>
        <!-- 新书表单 -->
        <form @submit.prevent="handleAddBook" class="modal-form">
          <!-- 书籍名称 -->
          <div class="form-group">
            <label>书籍名称 *</label>
            <input 
              type="text" 
              v-model="newBook.name" 
              class="form-input"
              placeholder="请输入书籍名称"
              required
            />
          </div>
          <!-- 作者 -->
          <div class="form-group">
            <label>作者</label>
            <input 
              type="text" 
              v-model="newBook.author" 
              class="form-input"
              placeholder="请输入作者名称"
            />
          </div>
          <!-- 书籍简介 -->
          <div class="form-group">
            <label>书籍简介 *</label>
            <textarea 
              v-model="newBook.description" 
              class="form-textarea"
              placeholder="请输入书籍简介"
              rows="3"
              required
            ></textarea>
          </div>
          <!-- 分类选择 -->
          <div class="form-group">
            <label>书籍分类 *</label>
            <!-- 注意：此处不使用HTML required属性，因为需要支持自定义分类流程 -->
            <!-- 当用户选择"自定义分类"时，会清空newBook.category并显示自定义输入框 -->
            <!-- 表单验证通过JavaScript的handleAddBook方法进行 -->
            <select v-model="newBook.category" class="form-select" @change="onCategoryChange">
              <option value="" disabled>请选择分类</option>
              <option v-for="cat in allCategories" :key="cat" :value="cat">{{ cat }}</option>
              <option value="__custom__">+ 自定义分类</option>
            </select>
          </div>
          <!-- 自定义分类输入框：当选择"自定义分类"时显示 -->
          <div class="form-group" v-if="showCustomCategoryInput">
            <label>自定义分类名称 *</label>
            <input 
              type="text" 
              v-model="customCategoryName" 
              class="form-input"
              placeholder="请输入自定义分类名称"
              required
            />
          </div>
          <!-- 价格 -->
          <div class="form-group">
            <label>价格 *</label>
            <input 
              type="number" 
              v-model.number="newBook.price" 
              class="form-input"
              placeholder="请输入价格"
              min="0"
              step="0.01"
              required
            />
          </div>
          <button type="submit" class="submit-btn">确认上架</button>
        </form>
      </div>
    </div>
    
    <!-- 修改价格弹窗 -->
    <div v-if="showEditModal" class="modal-overlay" @click.self="closeEditModal">
      <div class="modal-content small">
        <div class="modal-header">
          <h2>修改价格</h2>
          <button class="close-btn" @click="closeEditModal">✕</button>
        </div>
        <!-- 修改价格表单 -->
        <form @submit.prevent="handleEditPrice" class="modal-form">
          <div class="form-group">
            <label>书籍名称</label>
            <p class="book-label">{{ editingBook.name }}</p>
          </div>
          <div class="form-group">
            <label>当前价格</label>
            <p class="book-label">¥{{ editingBook.price.toFixed(2) }}</p>
          </div>
          <div class="form-group">
            <label>新价格 *</label>
            <input 
              type="number" 
              v-model.number="newPrice" 
              class="form-input"
              placeholder="请输入新价格"
              min="0"
              step="0.01"
              required
            />
          </div>
          <button type="submit" class="submit-btn">确认修改</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
/**
 * 书籍管理页面组件
 * 功能：管理书籍的上架、下架、价格修改，支持搜索和筛选
 */
import { getCurrentAdmin, adminLogout } from '@/utils/storage'
import { allBooks, saveBook, getCategoryName, getCategoryIdByName, getAllCategories, addCustomCategory, getBookViews } from '@/mock/bookData'
import { getPlaceholderCover } from '@/utils/placeholder'

export default {
  name: 'AdminBooks',
  
  // 数据定义
  data() {
    return {
      currentAdmin: {},           // 当前登录管理员信息
      books: [],                  // 书籍列表数据
      searchKeyword: '',          // 搜索关键词
      filterStatus: 'all',        // 筛选状态：all全部 / online上架中 / offline已下架
      showAddModal: false,        // 是否显示添加书籍弹窗
      showEditModal: false,       // 是否显示修改价格弹窗
      editingBook: {},            // 当前正在编辑的书籍对象
      newPrice: 0,                // 修改价格时的新价格
      showCustomCategoryInput: false, // 是否显示自定义分类输入框
      customCategoryName: '',     // 自定义分类名称
      newBook: {                  // 新书籍表单数据
        name: '',                 // 书籍名称
        author: '',               // 作者
        description: '',          // 书籍简介
        category: '',             // 分类
        price: 0                  // 价格
      }
    }
  },
  
  // 计算属性：根据现有数据派生出的值
  computed: {
    // 页面标题
    pageTitle() {
      return '书籍管理'
    },
    
    // 获取所有分类列表
    allCategories() {
      return getAllCategories()
    },
    
    // 统计上架中的书籍数量
    onlineCount() {
      return this.books.filter(b => b.status !== 'offline').length
    },
    
    // 统计已下架的书籍数量
    offlineCount() {
      return this.books.filter(b => b.status === 'offline').length
    },
    
    // 根据搜索关键词和筛选状态过滤书籍列表
    filteredBooks() {
      let result = this.books
      
      // 按名称搜索（不区分大小写）
      if (this.searchKeyword) {
        const keyword = this.searchKeyword.toLowerCase()
        result = result.filter(b => b.name.toLowerCase().includes(keyword))
      }
      
      // 按状态筛选
      if (this.filterStatus === 'online') {
        result = result.filter(b => b.status !== 'offline')
      } else if (this.filterStatus === 'offline') {
        result = result.filter(b => b.status === 'offline')
      }
      
      return result
    }
  },
  
  // 组件挂载后初始化数据
  mounted() {
    this.currentAdmin = getCurrentAdmin()
    this.loadBooks()
  },
  
  methods: {
    // 加载书籍数据：从数据源获取并格式化
    loadBooks() {
      this.books = allBooks.map(b => ({
        ...b,
        status: b.status || 'online',           // 默认为上架状态
        name: b.title || b.name,               // 兼容两种字段名
        category: getCategoryName(b.categoryId) // 将分类ID转为名称
      }))
    },
    
    // 获取书籍封面图片（使用占位图服务）
    getBookCover(name) {
      return getPlaceholderCover(name, 120, 160)
    },
    
    // 分类选择变化时的处理：检测是否选择了自定义分类
    onCategoryChange() {
      if (this.newBook.category === '__custom__') {
        // 选择自定义分类时，显示输入框并清空当前分类值
        this.showCustomCategoryInput = true
        this.newBook.category = ''
      } else {
        // 选择已有分类时，隐藏自定义输入框
        this.showCustomCategoryInput = false
        this.customCategoryName = ''
      }
    },
    
    // 打开修改价格弹窗
    openEditModal(book) {
      this.editingBook = { ...book }
      this.newPrice = book.price
      this.showEditModal = true
    },
    
    // 关闭修改价格弹窗并重置状态
    closeEditModal() {
      this.showEditModal = false
      this.editingBook = {}
      this.newPrice = 0
    },
    
    // 关闭添加书籍弹窗并重置表单
    closeAddModal() {
      this.showAddModal = false
      this.showCustomCategoryInput = false
      this.customCategoryName = ''
      this.newBook = {
        name: '',
        author: '',
        description: '',
        category: '',
        price: 0
      }
    },
    
    // 切换书籍上下架状态
    toggleBookStatus(book) {
      const index = allBooks.findIndex(b => b.id === book.id)
      if (index !== -1) {
        // 切换状态：上架变下架，下架变上架
        allBooks[index].status = allBooks[index].status === 'online' ? 'offline' : 'online'
        saveBook(allBooks[index])
        this.loadBooks()
      }
    },
    
    // 处理修改价格提交
    handleEditPrice() {
      const index = allBooks.findIndex(b => b.id === this.editingBook.id)
      if (index !== -1) {
        allBooks[index].price = this.newPrice
        saveBook(allBooks[index])
        this.loadBooks()
        this.closeEditModal()
      }
    },
    
    // 处理添加新书籍提交
    handleAddBook() {
      let categoryName = this.newBook.category
      
      // 处理自定义分类
      if (this.showCustomCategoryInput && this.customCategoryName) {
        categoryName = this.customCategoryName
        addCustomCategory(categoryName)
      }
      
      // 验证分类是否已选择
      if (!categoryName) {
        alert('请选择或输入分类')
        return
      }
      
      // 生成新书籍ID
      const maxId = Math.max(...allBooks.map(b => b.id), 0)
      const categoryId = getCategoryIdByName(categoryName)
      
      // 构建新书籍数据对象
      const newBookData = {
        id: maxId + 1,
        title: this.newBook.name,
        name: this.newBook.name,
        author: this.newBook.author || '未知作者',
        price: this.newBook.price,
        categoryId: categoryId,
        category: categoryName,
        description: this.newBook.description,
        status: 'online',
        views: 0,
        sales: 0,
        comments: []
      }
      
      // 添加到数据源并保存
      allBooks.push(newBookData)
      saveBook(newBookData)
      this.loadBooks()
      this.closeAddModal()
    },
    
    // 处理退出登录
    handleLogout() {
      adminLogout()
      this.$router.push('/login')
    },
    
    // 获取指定书籍的浏览量
    getBookViews(bookId) {
      return getBookViews(bookId)
    }
  }
}
</script>

<style scoped>
/* ===== 页面整体布局 ===== */
/* 页面容器：全屏高度，灰色背景 */
.admin-page {
  min-height: 100vh;
  background: var(--bg-page);
}
/* 管理后台容器：左右分栏布局 */
.admin-container {
  display: flex;
  height: 100vh;
}

/* ===== 左侧侧边栏 ===== */
/* 固定定位，白色背景，带阴影 */
.sidebar {
  width: 240px;
  background: white;
  border-right: 1px solid var(--border-color);
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 0;
  top: 0;
  bottom: 0;
  overflow-y: auto;
  z-index: 100;
  box-shadow: var(--shadow-xs);
}
/* Logo区域：顶部内边距，底部分隔线 */
.sidebar-header {
  padding: 28px 20px 24px;
  border-bottom: 1px solid var(--border-color);
}
.sidebar-header .logo {
  display: flex;
  align-items: center;
  gap: 12px;
}
.sidebar-header .logo-icon {
  font-size: 28px;
}
.sidebar-header .logo-text {
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
}
.sidebar-header .logo-text span {
  color: var(--primary-color);
}

/* 导航菜单区域 */
.sidebar-nav {
  flex: 1;
  padding: 16px 12px;
}
/* 单个导航项：带图标和文字，hover时变色 */
.nav-item {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 12px 18px;
  color: var(--text-muted);
  text-decoration: none;
  border-radius: var(--radius-sm);
  transition: all var(--transition-normal);
  margin-bottom: 2px;
  font-weight: 500;
}
.nav-item:hover {
  background: var(--bg-card-hover);
  color: var(--text-primary);
}
/* 当前激活的导航项：蓝色背景高亮 */
.nav-item.active {
  background: var(--info-bg);
  color: var(--primary-color);
  font-weight: 600;
}
.nav-icon { font-size: 18px; width: 28px; text-align: center; }
.nav-text { font-size: 14px; }

/* 侧边栏底部：退出按钮区域 */
.sidebar-footer {
  padding: 16px 12px 20px;
  border-top: 1px solid var(--border-color);
}
/* 退出按钮：红色背景，居中显示 */
.logout-btn {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 12px;
  background: var(--danger-bg);
  border: 1px solid transparent;
  border-radius: var(--radius-sm);
  color: var(--danger-color);
  cursor: pointer;
  transition: all var(--transition-normal);
  font-size: 14px;
  font-weight: 500;
}
.logout-btn:hover {
  background: #fcd5d5;
  border-color: var(--danger-color);
}

/* ===== 右侧主内容区域 ===== */
/* 占据剩余宽度，左侧留出侧边栏空间 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  margin-left: 240px;
  min-height: 100vh;
  background: var(--bg-page);
}

/* ===== 顶部标题栏 ===== */
/* 固定在顶部，白色背景，包含标题、搜索、按钮 */
.main-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 32px;
  background: white;
  border-bottom: 1px solid var(--border-color);
  flex-wrap: wrap;
  gap: 16px;
  position: sticky;
  top: 0;
  z-index: 50;
}
.header-left h1 {
  font-size: 22px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 2px;
}
.header-subtitle {
  font-size: 14px;
  color: var(--text-muted);
}

/* 右侧工具栏：搜索框、上架按钮、管理员信息 */
.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-wrap: wrap;
}
/* 搜索框容器 */
.search-bar {
  position: relative;
}
/* 搜索输入框：圆角，聚焦时边框变色并放大 */
.search-input {
  padding: 10px 44px 10px 18px;
  background: var(--bg-input);
  border: 1.5px solid transparent;
  border-radius: var(--radius-full);
  color: var(--text-primary);
  font-size: 14px;
  width: 240px;
  transition: all var(--transition-normal);
  outline: none;
}
.search-input:focus {
  border-color: var(--primary-color);
  background: white;
  box-shadow: 0 0 0 4px rgba(79, 140, 247, 0.08);
  width: 280px;
}
.search-input::placeholder { color: var(--text-muted); }
/* 搜索图标：定位在输入框右侧 */
.search-icon {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: var(--text-muted);
  font-size: 16px;
}

/* 上架书籍按钮：蓝色渐变背景，带阴影 */
.add-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 24px;
  background: var(--primary-gradient);
  border: none;
  border-radius: var(--radius-full);
  color: white;
  font-weight: 600;
  font-size: 14px;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
}
.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}

/* 当前管理员信息显示 */
.admin-info {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 6px 16px 6px 12px;
  background: var(--info-bg);
  border-radius: var(--radius-full);
}
.admin-icon { font-size: 16px; }
.admin-name {
  color: var(--primary-color);
  font-weight: 600;
  font-size: 14px;
}

/* ===== 内容区域 ===== */
.content-wrapper {
  padding: 28px 32px;
  flex: 1;
}

/* ===== 状态筛选栏 ===== */
/* 横向排列的筛选按钮组 */
.filter-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 28px;
  flex-wrap: wrap;
}
/* 单个筛选按钮：圆角边框样式 */
.filter-btn {
  padding: 9px 22px;
  background: white;
  border: 1.5px solid var(--border-color);
  border-radius: var(--radius-full);
  color: var(--text-secondary);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all var(--transition-normal);
}
.filter-btn:hover {
  border-color: var(--primary-color);
  color: var(--primary-color);
}
/* 当前激活的筛选按钮：蓝色渐变背景 */
.filter-btn.active {
  background: var(--primary-gradient);
  color: white;
  border-color: transparent;
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
}

/* ===== 书籍卡片网格 ===== */
/* 自适应网格布局，每行最少280px宽度 */
.books-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

/* 单个书籍卡片：白色背景，hover时上浮 */
.book-card {
  background: white;
  border-radius: var(--radius-md);
  border: 1px solid var(--border-color);
  overflow: hidden;
  transition: all var(--transition-normal);
  box-shadow: var(--shadow-xs);
}
.book-card:hover {
  transform: translateY(-6px);
  box-shadow: var(--shadow-lg);
  border-color: var(--primary-light);
}
/* 下架状态的卡片：降低透明度，封面灰度处理 */
.book-card.offline {
  opacity: 0.6;
}
.book-card.offline .book-cover {
  filter: grayscale(0.4);
}

/* 书籍封面区域：居中显示封面图 */
.book-cover {
  position: relative;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8faff;
  padding: 16px;
}
.book-cover-img {
  width: 120px;
  height: 160px;
  border-radius: var(--radius-sm);
  object-fit: cover;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: var(--transition-normal);
}
.book-card:hover .book-cover-img {
  transform: scale(1.03);
}
/* 下架标签：红色背景，定位在封面右上角 */
.offline-badge {
  position: absolute;
  top: 14px;
  right: 14px;
  padding: 4px 14px;
  background: var(--danger-color);
  color: white;
  font-size: 12px;
  font-weight: 600;
  border-radius: var(--radius-full);
}

/* 书籍信息区域 */
.book-info {
  padding: 16px 20px 12px;
}
.book-name {
  font-size: 17px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 4px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.book-author,
.book-category {
  font-size: 13px;
  color: var(--text-muted);
  margin-bottom: 2px;
}
/* 价格：蓝色大字体突出显示 */
.book-price {
  font-size: 20px;
  font-weight: 700;
  color: var(--primary-color);
  margin: 8px 0 4px;
}
.book-views {
  font-size: 12px;
  color: var(--text-muted);
}

/* 书籍操作按钮区域 */
.book-actions {
  padding: 0 20px 20px;
  display: flex;
  gap: 12px;
}
/* 操作按钮通用样式 */
.action-btn {
  flex: 1;
  padding: 9px;
  border: none;
  border-radius: var(--radius-sm);
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
}
/* 修改价格按钮：浅蓝色背景 */
.edit-btn {
  background: var(--info-bg);
  color: var(--primary-color);
}
.edit-btn:hover {
  background: #d6e4fd;
}
/* 下架按钮：红色背景 */
.offline-btn {
  background: var(--danger-bg);
  color: var(--danger-color);
}
.offline-btn:hover {
  background: #fcd5d5;
}
/* 上架按钮：绿色背景 */
.online-btn {
  background: var(--success-bg);
  color: var(--success-color);
}
.online-btn:hover {
  background: #c8f0df;
}

/* ===== 空状态提示 ===== */
.empty-state {
  text-align: center;
  padding: 80px 20px;
}
.empty-icon {
  font-size: 64px;
  display: block;
  margin-bottom: 16px;
  opacity: 0.5;
}
.empty-state p {
  color: var(--text-muted);
  font-size: 16px;
}

/* ===== 弹窗样式 ===== */
/* 弹窗遮罩层：半透明背景，居中显示内容 */
.modal-overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0, 0, 0, 0.35);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeInUp 0.3s ease;
}
/* 弹窗内容区：白色背景，圆角，带动画 */
.modal-content {
  background: white;
  border-radius: var(--radius-lg);
  padding: 32px 36px;
  width: 90%;
  max-width: 520px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: var(--shadow-xl);
  animation: scaleIn 0.3s ease;
}
/* 小尺寸弹窗：用于修改价格 */
.modal-content.small {
  max-width: 420px;
}

/* 弹窗头部：标题和关闭按钮 */
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}
.modal-header h2 {
  font-size: 22px;
  font-weight: 700;
  color: var(--text-primary);
}
.close-btn {
  background: none;
  border: none;
  color: var(--text-muted);
  font-size: 24px;
  cursor: pointer;
  transition: var(--transition-normal);
  padding: 4px 8px;
  border-radius: var(--radius-sm);
}
.close-btn:hover {
  background: var(--bg-input);
  color: var(--text-primary);
}

/* 弹窗表单 */
.modal-form {
  display: flex;
  flex-direction: column;
  gap: 18px;
}
/* 表单项组 */
.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}
.form-group label {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary);
}
/* 输入框、文本域、下拉框通用样式 */
.form-input,
.form-textarea,
.form-select {
  padding: 12px 16px;
  background: var(--bg-input);
  border: 1.5px solid transparent;
  border-radius: var(--radius-sm);
  color: var(--text-primary);
  font-size: 14px;
  transition: all var(--transition-normal);
  outline: none;
  font-family: inherit;
  width: 100%;
}
/* 聚焦状态：边框变蓝，背景变白 */
.form-input:focus,
.form-textarea:focus,
.form-select:focus {
  border-color: var(--primary-color);
  background: white;
  box-shadow: 0 0 0 4px rgba(79, 140, 247, 0.08);
}
.form-textarea {
  resize: vertical;
  min-height: 80px;
}
/* 下拉框：自定义下拉箭头 */
.form-select {
  cursor: pointer;
  appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='8' viewBox='0 0 12 8'%3E%3Cpath d='M1 1l5 5 5-5' stroke='%238a94a6' stroke-width='1.5' fill='none' stroke-linecap='round'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 14px center;
}
.form-select option {
  background: white;
  color: var(--text-primary);
}

/* 表单中的静态标签（如书籍名称显示） */
.book-label {
  padding: 12px 16px;
  background: var(--bg-input);
  border-radius: var(--radius-sm);
  color: var(--text-secondary);
  font-size: 14px;
}

/* 提交按钮：蓝色渐变，带阴影和hover上浮效果 */
.submit-btn {
  padding: 14px;
  background: var(--primary-gradient);
  border: none;
  border-radius: var(--radius-sm);
  color: white;
  font-weight: 600;
  font-size: 16px;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
  margin-top: 6px;
}
.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}
</style>
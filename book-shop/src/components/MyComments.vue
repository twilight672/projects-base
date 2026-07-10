<template>
  <!-- 我的评论组件容器 -->
  <div class="comments-inner">
    
    <!-- 未登录状态：提示用户登录 -->
    <div v-if="!isLoggedIn" class="login-tip">
      <p>登录后可查看我的评论</p>
      <!-- 跳转到登录页面 -->
      <router-link to="/login" class="btn-link">去登录</router-link>
    </div>

    <!-- 已登录但无评论：显示空状态提示 -->
    <div v-else-if="commentList.length === 0" class="empty-state">
      <!-- 对话气泡图标表示空评论 -->
      <span class="empty-icon">💬</span>
      <p>还没有发表过评论</p>
    </div>

    <!-- 有评论内容：展示评论列表 -->
    <div v-else class="comment-list">
      <!-- 遍历当前用户的所有评论 -->
      <div
        v-for="comment in commentList"
        :key="comment.id"
        class="comment-card"
      >
        <!-- 评论头部：书籍名称（可点击跳转）+ 评论时间 -->
        <div class="comment-header">
          <!-- 点击书籍名称跳转到书籍详情页 -->
          <span class="comment-book" @click="goDetail(comment.bookId)">
            📖 {{ getBookTitle(comment.bookId) }}
          </span>
          <!-- 显示相对时间（如"5分钟前"） -->
          <span class="comment-time">{{ formatTime(comment.createdAt) }}</span>
        </div>
        
        <!-- 评论正文内容 -->
        <p class="comment-content">{{ comment.content }}</p>
        
        <!-- 评论操作栏：删除按钮 -->
        <div class="comment-actions">
          <button class="delete-btn" @click="handleDeleteComment(comment.id)">删除</button>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
// 我的评论组件：展示当前用户发表的所有评论，支持查看书籍详情和删除评论功能
import { isLoggedIn, getCurrentUser, deleteComment } from '@/utils/storage'
import { getBookById } from '@/mock/bookData'

export default {
  name: 'MyComments',
  
  data() {
    return {
      // 当前用户的评论列表数组
      commentList: []
    }
  },
  
  computed: {
    // 判断用户是否已登录
    isLoggedIn() {
      return isLoggedIn()
    },
    
    // 获取当前登录用户信息
    currentUser() {
      return getCurrentUser()
    }
  },
  
  // 组件挂载后加载评论数据
  mounted() {
    this.loadComments()
  },
  
  methods: {
    // 加载当前用户的评论列表
    loadComments() {
      if (!this.isLoggedIn) {
        this.commentList = []
        return
      }
      
      const user = this.currentUser
      const allComments = JSON.parse(localStorage.getItem('comments') || '[]')
      
      // 过滤当前用户的评论，按时间倒序排列
      this.commentList = allComments
        .filter(c => c.userName === user.userName)
        .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
    },
    
    // 获取书籍标题
    getBookTitle(bookId) {
      const book = getBookById(Number(bookId))
      return book ? book.title : '已下架书籍'
    },
    
    // 处理删除评论
    handleDeleteComment(commentId) {
      if (confirm('确定删除这条评论吗？')) {
        deleteComment(commentId)
        this.loadComments()
        this.$emit('update')
      }
    },
    
    // 跳转到书籍详情页
    goDetail(bookId) {
      this.$router.push({ name: 'BookDetail', params: { id: bookId } })
    },
    
    // 格式化时间戳为相对时间字符串
    formatTime(dateStr) {
      if (!dateStr) return '未知时间'
      
      let date
      if (typeof dateStr === 'number') {
        date = new Date(dateStr)
      } else {
        date = new Date(dateStr)
      }
      
      if (isNaN(date.getTime())) {
        return '未知时间'
      }
      
      const now = new Date()
      const diff = now - date
      const minutes = Math.floor(diff / 60000)
      const hours = Math.floor(diff / 3600000)
      const days = Math.floor(diff / 86400000)
    
      if (minutes < 1) return '刚刚'
      if (minutes < 60) return `${minutes}分钟前`
      if (hours < 24) return `${hours}小时前`
      if (days < 30) return `${days}天前`
      return date.toLocaleDateString()
    }
  }
}
</script>

<style scoped>
/* 我的评论组件容器 */
.comments-inner {
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

/* 评论列表容器 */
.comment-list {
  display: flex;
  flex-direction: column;
  gap: 10px;  /* 卡片之间的间距 */
}

/* 单条评论卡片 */
.comment-card {
  background: rgba(40, 40, 70, 0.6);
  border: 1px solid rgba(0, 212, 255, 0.1);
  border-radius: 10px;
  padding: 14px;
  transition: all 0.3s;
}

/* 卡片悬停效果：边框高亮 */
.comment-card:hover {
  border-color: rgba(0, 212, 255, 0.2);
}

/* 评论头部：书籍名称 + 评论时间 */
.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

/* 书籍名称链接：可点击跳转到书籍详情 */
.comment-book {
  font-size: 13px;
  font-weight: 500;
  color: #00d4ff;
  cursor: pointer;
}

.comment-book:hover {
  text-decoration: underline;
}

/* 评论时间 */
.comment-time {
  font-size: 11px;
  color: #9ca3af;
}

/* 评论正文内容 */
.comment-content {
  font-size: 14px;
  color: #e5e7eb;
  line-height: 1.6;
  margin: 0 0 10px;
}

/* 评论操作栏 */
.comment-actions {
  display: flex;
  justify-content: flex-end;  /* 按钮靠右对齐 */
}

/* 删除按钮 */
.delete-btn {
  padding: 3px 12px;
  background: transparent;
  border: 1px solid rgba(244, 67, 54, 0.4);  /* 红色边框 */
  color: #ef4444;
  border-radius: 4px;
  font-size: 11px;
  cursor: pointer;
  transition: all 0.3s;
}

/* 按钮悬停效果：红色背景 */
.delete-btn:hover {
  background: rgba(244, 67, 54, 0.1);
}
</style>
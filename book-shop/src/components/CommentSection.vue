// CommentSection.vue - 评论组件
// 功能：展示评论列表、发表评论、删除评论

<template>
  <div class="comment-section">
    <h3 class="section-title">读者评论</h3>

    <!-- 未登录提示 -->
    <div v-if="!isLoggedIn" class="login-tip">
      <p>登录后可发表评论和查看更多书评</p>
      <router-link to="/login" class="btn-link">去登录</router-link>
    </div>

    <!-- 评论输入框（已登录） -->
    <div v-else class="comment-input">
      <textarea
        v-model="newComment"
        placeholder="写下你的阅读感受..."
        rows="3"
      ></textarea>
      <button class="btn-submit" @click="submitComment" :disabled="!newComment.trim()">
        发布评论
      </button>
    </div>

    <!-- 评论列表 -->
    <div class="comment-list">
      <div v-if="commentList.length === 0" class="empty">
        <p>暂无评论，快来发表第一条吧</p>
      </div>
      <div v-for="comment in commentList" :key="comment.id" class="comment-item">
        <div class="comment-header">
          <span class="username">{{ comment.userName }}</span>
          <span class="time">{{ formatTime(comment.createdAt) }}</span>
        </div>
        <p class="comment-content">{{ comment.content }}</p>
        <!-- 删除按钮（仅显示自己的评论） -->
        <div v-if="comment.userId === currentUserId" class="comment-actions">
          <button class="btn-delete" @click="deleteComment(comment.id)">删除</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// 书籍详情页评论组件：支持发表评论、删除评论、查看评论列表
import { isLoggedIn, getCurrentUser, addComment, deleteComment } from '@/utils/storage'
import { getCommentsByBookId as getMockComments } from '@/mock/bookData'

export default {
  name: 'CommentSection',
  
  // 组件属性
  props: {
    // 书籍ID（必填）
    bookId: {
      type: Number,
      required: true
    }
  },
  
  data() {
    return {
      newComment: '',   // 新评论内容
      commentList: []   // 评论列表数据
    }
  },
  
  computed: {
    // 判断用户是否已登录
    isLoggedIn() {
      if (typeof window.checkLogin === 'function') {
        return window.checkLogin()
      }
      return isLoggedIn()
    },
    
    // 获取当前用户ID（用户名）
    currentUserId() {
      const user = getCurrentUser()
      return user ? user.userName : null
    },
    
    // 确保 bookId 为数字类型
    safeBookId() {
      return Number(this.bookId)
    }
  },
  
  // 组件挂载后加载评论列表
  mounted() {
    this.loadComments()
  },
  
  methods: {
    // 加载评论列表
    loadComments() {
      const bookId = this.safeBookId
      this.commentList = getMockComments(bookId)
    },

    // 提交新评论
    async submitComment() {
      if (!this.newComment.trim()) return

      if (typeof window.checkLogin === 'function') {
        if (!(await window.checkLogin())) {
          this.$router.push('/login')
          return
        }
      } else if (!isLoggedIn()) {
        this.$router.push('/login')
        return
      }

      const user = getCurrentUser()
      addComment({ 
        bookId: this.safeBookId,
        content: this.newComment.trim(),
        userName: user.userName
      })
      
      this.newComment = ''
      this.loadComments()
    },

    // 删除评论
    deleteComment(commentId) {
      if (confirm('确定删除这条评论吗？')) {
        deleteComment(commentId)
        this.loadComments()
      }
    },

    // 格式化时间戳为相对时间字符串
    formatTime(dateStr) {
      const date = new Date(dateStr)
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
/* 评论区域容器 */
.comment-section {
  padding: 0;
}

/* 标题 */
.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #e5e7eb;
  margin: 0 0 12px;
  padding-bottom: 8px;
  border-bottom: 1px solid rgba(0, 212, 255, 0.15);
}

/* 未登录提示 */
.login-tip {
  text-align: center;
  padding: 16px;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(0, 212, 255, 0.1);
  border-radius: 8px;
  margin-bottom: 16px;
}

.login-tip p {
  margin: 0 0 8px;
  color: #9ca3af;
  font-size: 13px;
}

.btn-link {
  color: var(--primary-color);
  text-decoration: none;
  font-size: 13px;
  transition: color 0.3s;
}

.btn-link:hover {
  color: var(--primary-light);
  text-decoration: underline;
}

/* 评论输入框 */
.comment-input {
  margin-bottom: 16px;
}

.comment-input textarea {
  width: 100%;
  padding: 12px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(0, 212, 255, 0.15);
  border-radius: 8px;
  font-size: 14px;
  color: #e5e7eb;
  resize: none;
  margin-bottom: 10px;
  transition: all 0.3s;
  box-sizing: border-box;
}

.comment-input textarea:focus {
  outline: none;
  border-color: var(--primary-color);
  background: rgba(255, 255, 255, 0.08);
  box-shadow: 0 0 10px rgba(0, 212, 255, 0.15);
}

.comment-input textarea::placeholder {
  color: #6b7280;
}

/* 发布按钮 */
.btn-submit {
  padding: 10px 20px;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
  color: #fff;
  border: none;
  border-radius: 6px;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
}

.btn-submit:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 0 20px rgba(0, 212, 255, 0.3);
}

.btn-submit:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* 评论列表 */
.comment-list {
  border-top: 1px solid rgba(0, 212, 255, 0.1);
  padding-top: 12px;
}

/* 单条评论 */
.comment-item {
  padding: 14px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.comment-item:last-child {
  border-bottom: none;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.username {
  font-weight: 600;
  color: var(--primary-color);
  font-size: 13px;
}

.time {
  font-size: 11px;
  color: #6b7280;
}

.comment-content {
  color: #d1d5db;
  line-height: 1.6;
  font-size: 14px;
  margin: 0;
}

/* 操作按钮 */
.comment-actions {
  margin-top: 8px;
}

.btn-delete {
  padding: 4px 10px;
  background: transparent;
  border: 1px solid rgba(244, 67, 54, 0.4);
  color: var(--danger-color);
  border-radius: 4px;
  font-size: 11px;
  cursor: pointer;
  transition: all 0.3s;
}

.btn-delete:hover {
  background: rgba(244, 67, 54, 0.1);
}

/* 空状态 */
.empty {
  text-align: center;
  padding: 24px 0;
  color: #6b7280;
  font-size: 13px;
}

.empty p {
  margin: 0;
}
</style>

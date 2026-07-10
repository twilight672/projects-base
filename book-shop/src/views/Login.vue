<template>
  <!-- 
    Login.vue - 登录页组件
    功能：
      ① 用户登录：用户名和密码验证
      ② 表单校验：必填项检查
      ③ 密码可见性切换
      ④ 登录成功后跳转首页
      ⑤ 注册链接跳转
      ⑥ 管理员登录入口
    布局：
      - 深色科技感背景（粒子效果+网格线）
      - 居中卡片式登录表单
      - 电光蓝主题配色
  -->
  <div class="login-page">
    <!-- 背景粒子效果 -->
    <div class="bg-particles"></div>
    <!-- 背景网格线 -->
    <div class="bg-grid"></div>
    
    <div class="login-container">
      <div class="login-card">
        <div class="login-header">
          <div class="logo">
            <span class="logo-icon">📚</span>
            <span class="logo-text">BookShop</span>
          </div>
          
          <!-- 登录类型切换 -->
          <div class="login-tabs">
            <button 
              class="tab-btn" 
              :class="{ active: !isAdminMode }"
              @click="isAdminMode = false"
            >
              用户登录
            </button>
            <button 
              class="tab-btn" 
              :class="{ active: isAdminMode }"
              @click="isAdminMode = true"
            >
              管理员登录
            </button>
          </div>
          
          <h1 class="title glow-text">{{ isAdminMode ? '管理员登录' : '欢迎回来' }}</h1>
          <p class="subtitle">{{ isAdminMode ? '管理后台入口' : '探索知识的无限可能' }}</p>
        </div>
        
        <form @submit.prevent="handleLogin" class="login-form">
          <div class="form-group">
            <label>{{ isAdminMode ? '管理员账号' : '用户名' }}</label>
            <div class="input-wrapper">
              <span class="input-icon">{{ isAdminMode ? '👑' : '👤' }}</span>
              <input
                v-model="form.username"
                type="text"
                class="form-control"
                :class="{ error: errors.username }"
                :placeholder="isAdminMode ? '请输入管理员账号' : '请输入用户名'"
                @blur="validateUsername"
              />
            </div>
            <span v-if="errors.username" class="form-error">{{ errors.username }}</span>
          </div>
          
          <div class="form-group">
            <label>密码</label>
            <div class="input-wrapper">
              <span class="input-icon">🔐</span>
              <input
                v-model="form.password"
                :type="showPassword ? 'text' : 'password'"
                class="form-control"
                :class="{ error: errors.password }"
                placeholder="请输入密码"
                @blur="validatePassword"
              />
              <span class="toggle-password" @click="showPassword = !showPassword">
                {{ showPassword ? '🙈' : '👁️' }}
              </span>
            </div>
            <span v-if="errors.password" class="form-error">{{ errors.password }}</span>
          </div>
          
          <div class="form-group remember-me" v-if="!isAdminMode">
            <label class="checkbox-label">
              <input type="checkbox" v-model="form.rememberMe" />
              <span class="checkmark"></span>
              记住我
            </label>
          </div>
          
          <button type="submit" class="btn btn-primary login-btn pulse-glow" :disabled="loading">
            <span v-if="loading" class="loading-icon">⏳</span>
            {{ loading ? '登录中...' : (isAdminMode ? '管理员登录' : '登 录') }}
          </button>
          
          <div v-if="loginError" class="error-message">
            {{ loginError }}
          </div>
        </form>
        
        <div class="login-footer" v-if="!isAdminMode">
          <p>还没有账号？</p>
          <router-link to="/register" class="link register-link">立即注册</router-link>
        </div>
        
        <div class="admin-hint" v-if="isAdminMode">
          <p>默认管理员账号: <span class="hint-value">admin</span></p>
          <p>默认密码: <span class="hint-value">admin123</span></p>
        </div>
      </div>
      
      <div class="login-decoration">
        <div class="decoration-circle c1"></div>
        <div class="decoration-circle c2"></div>
        <div class="decoration-circle c3"></div>
      </div>
    </div>
  </div>
</template>

<script>
// 登录页面组件
import { login, adminLogin } from '@/utils/storage'

export default {
  name: 'Login',
  
  data() {
    return {
      // 登录表单数据
      form: {
        username: '',
        password: '',
        rememberMe: false
      },
      showPassword: false,  // 密码显示开关
      errors: {},           // 表单错误信息
      loginError: '',       // 登录错误提示
      loading: false,       // 登录加载状态
      isAdminMode: false    // 是否管理员登录模式
    }
  },
  
  mounted() {
    if (!this.isAdminMode) {
      this.loadRememberedUser()
    }
  },
  
  methods: {
    // 验证用户名
    validateUsername() {
      if (!this.form.username) {
        this.errors.username = '请输入账号'
      } else if (this.form.username.length < 3) {
        this.errors.username = '账号至少3个字符'
      } else {
        delete this.errors.username
      }
    },
    
    // 验证密码
    validatePassword() {
      if (!this.form.password) {
        this.errors.password = '请输入密码'
      } else if (this.form.password.length < 6) {
        this.errors.password = '密码至少6个字符'
      } else {
        delete this.errors.password
      }
    },
    
    // 验证整个表单
    validateForm() {
      this.validateUsername()
      this.validatePassword()
      return Object.keys(this.errors).length === 0
    },
    
    // 处理登录提交
    async handleLogin() {
      if (!this.validateForm()) return
      
      this.loading = true
      this.loginError = ''
      
      try {
        await new Promise(resolve => setTimeout(resolve, 500))
        
        let result
        if (this.isAdminMode) {
          result = adminLogin(this.form.username, this.form.password)
        } else {
          result = login(this.form.username, this.form.password)
        }
        
        if (result.success) {
          if (!this.isAdminMode && this.form.rememberMe) {
            localStorage.setItem('bookshop_remembered_user', this.form.username)
          } else {
            localStorage.removeItem('bookshop_remembered_user')
          }
          
          if (this.isAdminMode) {
            this.$router.push('/admin/dashboard')
          } else {
            this.$router.push('/')
          }
        } else {
          this.loginError = result.message
        }
      } catch (error) {
        this.loginError = '登录失败，请稍后重试'
      } finally {
        this.loading = false
      }
    },
    
    // 加载记住的用户名
    loadRememberedUser() {
      const rememberedUser = localStorage.getItem('bookshop_remembered_user')
      if (rememberedUser) {
        this.form.username = rememberedUser
        this.form.rememberMe = true
      }
    }
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  position: relative;
  background: var(--bg-page);
}

.login-container {
  position: relative;
  width: 100%;
  max-width: 420px;
  z-index: 1;
}

/* ===== 登录卡片 ===== */
.login-card {
  background: white;
  border-radius: var(--radius-xl);
  padding: 44px 38px 36px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xl);
}

/* ===== 头部 ===== */
.login-header {
  text-align: center;
  margin-bottom: 36px;
}
.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}
.logo-icon {
  font-size: 40px;
  margin-right: 14px;
}
.logo-text {
  font-size: 30px;
  font-weight: 700;
  color: var(--text-primary);
}
.logo-text span {
  color: var(--primary-color);
}

.login-tabs {
  display: flex;
  justify-content: center;
  gap: 6px;
  margin-bottom: 18px;
  background: var(--bg-input);
  padding: 4px;
  border-radius: var(--radius-full);
  width: fit-content;
  margin-left: auto;
  margin-right: auto;
}
.tab-btn {
  padding: 8px 24px;
  border: none;
  background: transparent;
  color: var(--text-muted);
  border-radius: var(--radius-full);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all var(--transition-normal);
}
.tab-btn:hover {
  color: var(--text-secondary);
}
.tab-btn.active {
  background: white;
  color: var(--primary-color);
  box-shadow: var(--shadow-sm);
}

.title {
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 6px;
}
.subtitle {
  color: var(--text-muted);
  font-size: 14px;
}

/* ===== 表单 ===== */
.login-form {
  margin-bottom: 24px;
}
.form-group {
  margin-bottom: 18px;
}
.form-group label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary);
  margin-bottom: 6px;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}
.input-icon {
  position: absolute;
  left: 16px;
  font-size: 18px;
  color: var(--text-muted);
  z-index: 1;
}
.toggle-password {
  position: absolute;
  right: 16px;
  font-size: 18px;
  cursor: pointer;
  color: var(--text-muted);
  transition: var(--transition-normal);
  z-index: 1;
}
.toggle-password:hover {
  color: var(--text-primary);
}

.form-control {
  width: 100%;
  padding: 14px 44px;
  font-size: 15px;
  background: var(--bg-input);
  border: 1.5px solid transparent;
  border-radius: var(--radius-sm);
  color: var(--text-primary);
  transition: all var(--transition-normal);
  outline: none;
  box-sizing: border-box;
}
.form-control::placeholder {
  color: var(--text-muted);
}
.form-control:focus {
  border-color: var(--primary-color);
  background: white;
  box-shadow: 0 0 0 4px rgba(79, 140, 247, 0.08);
}
.form-control.error {
  border-color: var(--danger-color);
  background: var(--danger-bg);
}
.form-control.error:focus {
  box-shadow: 0 0 0 4px rgba(255, 107, 107, 0.10);
}

.form-error {
  color: var(--danger-color);
  font-size: 13px;
  margin-top: 4px;
  display: block;
}

/* ===== 记住我 — 修复复选框 ===== */
.remember-me {
  display: flex;
  align-items: center;
  margin: 4px 0 8px;
  padding: 0;
}
.remember-me .checkbox-label {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  font-size: 14px;
  color: var(--text-secondary);
  user-select: none;
}
.remember-me .checkbox-label input[type="checkbox"] {
  /* 保留原生复选框，确保可点击 */
  width: 18px;
  height: 18px;
  margin: 0;
  accent-color: var(--primary-color);
  cursor: pointer;
  flex-shrink: 0;
  /* 移除可能的覆盖样式 */
  appearance: auto;
  -webkit-appearance: checkbox;
  position: static;
  opacity: 1;
  pointer-events: auto;
}

/* 如果使用自定义复选框，保留此样式 */
.remember-me .checkbox-label .custom-checkmark {
  display: none;
}

.login-btn {
  width: 100%;
  padding: 16px;
  font-size: 16px;
  font-weight: 600;
  background: var(--primary-gradient);
  border: none;
  border-radius: var(--radius-sm);
  color: white;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 14px rgba(79, 140, 247, 0.25);
  margin-top: 6px;
}
.login-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}
.login-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.error-message {
  color: var(--danger-color);
  font-size: 14px;
  text-align: center;
  margin-top: 16px;
  padding: 12px;
  background: var(--danger-bg);
  border-radius: var(--radius-sm);
  border: 1px solid #fcd5d5;
}

/* ===== 底部 ===== */
.login-footer {
  text-align: center;
  padding-top: 20px;
  border-top: 1px solid var(--border-color);
}
.login-footer p {
  color: var(--text-muted);
  font-size: 14px;
  margin-bottom: 8px;
}
.register-link {
  font-size: 15px;
  font-weight: 600;
  color: var(--primary-color);
  text-decoration: none;
}
.register-link:hover {
  text-decoration: underline;
}

.admin-hint {
  margin-top: 16px;
  padding: 12px 16px;
  background: var(--info-bg);
  border-radius: var(--radius-sm);
  border: 1px solid var(--border-color);
  text-align: center;
}
.admin-hint p {
  margin: 4px 0;
  font-size: 13px;
  color: var(--text-muted);
}
.hint-value {
  color: var(--primary-color);
  font-weight: 600;
  font-family: monospace;
}
</style>
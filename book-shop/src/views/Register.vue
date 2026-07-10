<template>
  <!-- 
    Register.vue - 注册页组件
    功能：
      ① 用户注册：用户名、邮箱、密码输入
      ② 表单校验：必填项、密码确认、邮箱格式
      ③ 密码可见性切换
      ④ 注册成功后自动登录并跳转首页
      ⑤ 登录链接跳转
      ⑥ 用户协议勾选
    布局：
      - 深色科技感背景（粒子效果+网格线）
      - 居中卡片式注册表单
      - 电光蓝主题配色
  -->
  <div class="register-page">
    <!-- 背景粒子效果 -->
    <div class="bg-particles"></div>
    <!-- 背景网格线 -->
    <div class="bg-grid"></div>
    
    <div class="register-container">
      <div class="register-card">
        <div class="register-header">
          <div class="logo">
            <span class="logo-icon">📚</span>
            <span class="logo-text">BookShop</span>
          </div>
          <h1 class="title glow-text">创建账号</h1>
          <p class="subtitle">开启你的阅读之旅</p>
        </div>
        
        <form @submit.prevent="handleRegister" class="register-form">
          <div class="form-group">
            <label>用户名</label>
            <div class="input-wrapper">
              <span class="input-icon">👤</span>
              <input
                v-model="form.username"
                type="text"
                class="form-control"
                :class="{ error: errors.username }"
                placeholder="请输入用户名"
                @blur="validateUsername"
              />
            </div>
            <span v-if="errors.username" class="form-error">{{ errors.username }}</span>
          </div>
          
          <div class="form-group">
            <label>邮箱</label>
            <div class="input-wrapper">
              <span class="input-icon">📧</span>
              <input
                v-model="form.email"
                type="email"
                class="form-control"
                :class="{ error: errors.email }"
                placeholder="请输入邮箱"
                @blur="validateEmail"
              />
            </div>
            <span v-if="errors.email" class="form-error">{{ errors.email }}</span>
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
          
          <div class="form-group">
            <label>确认密码</label>
            <div class="input-wrapper">
              <span class="input-icon">🔑</span>
              <input
                v-model="form.confirmPassword"
                :type="showPassword ? 'text' : 'password'"
                class="form-control"
                :class="{ error: errors.confirmPassword }"
                placeholder="请再次输入密码"
                @blur="validateConfirmPassword"
              />
            </div>
            <span v-if="errors.confirmPassword" class="form-error">{{ errors.confirmPassword }}</span>
          </div>
          
          <div class="form-group agree-terms">
            <label class="checkbox-label">
              <input type="checkbox" v-model="form.agreeTerms" />
              <span class="checkmark"></span>
              <span>我已阅读并同意</span>
              <a href="#" class="link terms-link">《用户协议》</a>
              <span>和</span>
              <a href="#" class="link terms-link">《隐私政策》</a>
            </label>
          </div>
          
          <button type="submit" class="btn btn-primary register-btn pulse-glow" :disabled="loading">
            <span v-if="loading" class="loading-icon">⏳</span>
            {{ loading ? '注册中...' : '注 册' }}
          </button>
          
          <div v-if="registerError" class="error-message">
            {{ registerError }}
          </div>
          
          <div v-if="registerSuccess" class="success-message">
            ✅ 注册成功！正在跳转...
          </div>
        </form>
        
        <div class="register-footer">
          <p>已有账号？</p>
          <router-link to="/login" class="link login-link">立即登录</router-link>
        </div>
      </div>
      
      <div class="register-decoration">
        <div class="decoration-circle c1"></div>
        <div class="decoration-circle c2"></div>
        <div class="decoration-circle c3"></div>
      </div>
    </div>
  </div>
</template>

<script>
// 注册页面组件
import { register } from '@/utils/storage'

export default {
  name: 'Register',
  
  data() {
    return {
      // 注册表单数据
      form: {
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        agreeTerms: false
      },
      showPassword: false,      // 密码显示开关
      errors: {},               // 表单错误信息
      registerError: '',        // 注册错误提示
      registerSuccess: false,   // 注册成功状态
      loading: false            // 加载状态
    }
  },
  
  methods: {
    // 验证用户名
    validateUsername() {
      if (!this.form.username) {
        this.errors.username = '请输入用户名'
      } else if (this.form.username.length < 3) {
        this.errors.username = '用户名至少3个字符'
      } else if (this.form.username.length > 20) {
        this.errors.username = '用户名最多20个字符'
      } else {
        delete this.errors.username
      }
    },
    
    // 验证邮箱
    validateEmail() {
      if (!this.form.email) {
        this.errors.email = '请输入邮箱'
      } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.form.email)) {
        this.errors.email = '请输入有效的邮箱地址'
      } else {
        delete this.errors.email
      }
    },
    
    // 验证密码
    validatePassword() {
      if (!this.form.password) {
        this.errors.password = '请输入密码'
      } else if (this.form.password.length < 6) {
        this.errors.password = '密码至少6个字符'
      } else if (this.form.password.length > 30) {
        this.errors.password = '密码最多30个字符'
      } else {
        delete this.errors.password
      }
    },
    
    // 验证确认密码
    validateConfirmPassword() {
      if (!this.form.confirmPassword) {
        this.errors.confirmPassword = '请确认密码'
      } else if (this.form.confirmPassword !== this.form.password) {
        this.errors.confirmPassword = '两次输入的密码不一致'
      } else {
        delete this.errors.confirmPassword
      }
    },
    
    // 验证整个表单
    validateForm() {
      this.validateUsername()
      this.validateEmail()
      this.validatePassword()
      this.validateConfirmPassword()
      
      if (!this.form.agreeTerms) {
        this.errors.agreeTerms = '请同意用户协议和隐私政策'
      } else {
        delete this.errors.agreeTerms
      }
      
      return Object.keys(this.errors).length === 0
    },
    
    // 处理注册提交
    async handleRegister() {
      if (!this.validateForm()) return
      
      this.loading = true
      this.registerError = ''
      this.registerSuccess = false
      
      try {
        await new Promise(resolve => setTimeout(resolve, 800))
        
        const result = register(this.form.username, this.form.password, this.form.email)
        
        if (result.success) {
          this.registerSuccess = true
          
          setTimeout(() => {
            this.$router.push('/login')
          }, 1500)
        } else {
          this.registerError = result.message
        }
      } catch (error) {
        this.registerError = '注册失败，请稍后重试'
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.register-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  position: relative;
  background: var(--bg-page);
}

.register-container {
  position: relative;
  width: 100%;
  max-width: 450px;
  z-index: 1;
}

/* ===== 注册卡片 ===== */
.register-card {
  background: white;
  border-radius: var(--radius-xl);
  padding: 40px 36px 32px;
  border: 1px solid var(--border-color);
  box-shadow: var(--shadow-xl);
}

/* ===== 头部 ===== */
.register-header {
  text-align: center;
  margin-bottom: 32px;
}
.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 18px;
}
.logo-icon {
  font-size: 38px;
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

.title {
  font-size: 26px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 4px;
}
.subtitle {
  color: var(--text-muted);
  font-size: 14px;
}

/* ===== 表单 ===== */
.register-form {
  margin-bottom: 24px;
}
.form-group {
  margin-bottom: 16px;
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

/* ===== 同意条款 — 修复复选框 ===== */
.agree-terms {
  margin: 6px 0 10px;
  padding: 0;
}
.agree-terms .checkbox-label {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 4px 6px;
  cursor: pointer;
  font-size: 13px;
  color: var(--text-secondary);
  user-select: none;
}
.agree-terms .checkbox-label input[type="checkbox"] {
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

/* 如果使用自定义复选框，隐藏 */
.agree-terms .checkbox-label .custom-checkmark {
  display: none;
}

.terms-link {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 500;
}
.terms-link:hover {
  text-decoration: underline;
}

.register-btn {
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
.register-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(79, 140, 247, 0.30);
}
.register-btn:disabled {
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
.success-message {
  color: var(--success-color);
  font-size: 14px;
  text-align: center;
  margin-top: 16px;
  padding: 12px;
  background: var(--success-bg);
  border-radius: var(--radius-sm);
  border: 1px solid #c8f0df;
  animation: fadeInUp 0.3s ease;
}

/* ===== 底部 ===== */
.register-footer {
  text-align: center;
  padding-top: 20px;
  border-top: 1px solid var(--border-color);
}
.register-footer p {
  color: var(--text-muted);
  font-size: 14px;
  margin-bottom: 8px;
}
.login-link {
  font-size: 15px;
  font-weight: 600;
  color: var(--primary-color);
  text-decoration: none;
}
.login-link:hover {
  text-decoration: underline;
}
</style>
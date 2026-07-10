/**
 * 路由配置文件
 * 
 * 这里定义了应用中所有的页面路径和对应的组件
 * 使用懒加载（lazy loading）方式引入组件，可以提升首屏加载速度
 * 
 * 页面路由说明：
 * - /                 首页，展示书籍列表
 * - /category         分类页，按分类浏览书籍
 * - /book/:id         书籍详情页，:id 是动态参数
 * - /cart             购物车页
 * - /checkout         结算页
 * - /orders           我的订单列表
 * - /order/:orderId   订单详情页
 * - /profile          个人中心
 * - /login            登录页
 * - /register         注册页
 * - /admin/*          管理员后台相关页面
 */

import Vue from 'vue'
import Router from 'vue-router'
// 引入登录状态检查方法
import { isLoggedIn, isAdminLoggedIn } from '@/utils/storage'

// 在Vue中安装路由插件
Vue.use(Router)

// 创建路由实例
const router = new Router({
  // 使用history模式，URL会更干净，没有 # 号
  // 比如：/cart 而不是 /#/cart
  mode: 'history',
  // 应用的基础路径，从环境变量读取
  base: process.env.BASE_URL,
  
  /**
   * 页面切换时的滚动行为
   * 每次进入新页面，都滚动到顶部
   * 这样用户不会在新页面看到上一个页面的滚动位置
   */
  scrollBehavior(to, from, savedPosition) {
    return { x: 0, y: 0 }
  },
  
  // 路由配置列表
  routes: [
    // 首页
    {
      path: '/',
      name: 'Home',
      // 懒加载：只有访问这个页面时才加载对应的组件代码
      component: () => import('@/views/Home.vue')
    },
    
    // 分类页
    {
      path: '/category',
      name: 'Category',
      component: () => import('@/views/Category.vue')
    },
    
    // 书籍详情页
    // :id 是动态参数，比如 /book/123 会显示id为123的书籍
    {
      path: '/book/:id',
      name: 'BookDetail',
      component: () => import('@/views/BookDetail.vue')
    },
    
    // 购物车页
    {
      path: '/cart',
      name: 'Cart',
      component: () => import('@/views/Cart.vue')
    },
    
    // 结算页
    {
      path: '/checkout',
      name: 'Checkout',
      component: () => import('@/views/Checkout.vue')
    },
    
    // 我的订单列表
    {
      path: '/orders',
      name: 'Orders',
      component: () => import('@/views/Orders.vue')
    },
    
    // 订单详情页
    // :orderId 是动态参数，显示具体订单的详细信息
    {
      path: '/order/:orderId',
      name: 'OrderDetail',
      component: () => import('@/views/OrderDetail.vue')
    },
    
    // 个人中心页
    {
      path: '/profile',
      name: 'Profile',
      component: () => import('@/views/Profile.vue')
    },
    
    // 登录页
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue')
    },
    
    // 注册页
    {
      path: '/register',
      name: 'Register',
      component: () => import('@/views/Register.vue')
    },
    
    // ========== 管理员后台路由 ==========
    // 这些页面需要管理员权限才能访问
    
    // 管理员首页/数据看板
    {
      path: '/admin/dashboard',
      name: 'AdminDashboard',
      component: () => import('@/views/AdminDashboard.vue'),
      // meta字段用于存储路由的额外信息
      // requireAdmin: true 表示需要管理员权限
      meta: { requireAdmin: true }
    },
    
    // 书籍管理页
    {
      path: '/admin/books',
      name: 'AdminBooks',
      component: () => import('@/views/AdminBooks.vue'),
      meta: { requireAdmin: true }
    },
    
    // 用户管理页
    {
      path: '/admin/users',
      name: 'AdminUsers',
      component: () => import('@/views/AdminUsers.vue'),
      meta: { requireAdmin: true }
    }
  ]
})

/**
 * 全局路由守卫
 * 
 * 在每次路由跳转前都会执行这个函数
 * 主要用于权限控制：检查用户是否有权限访问目标页面
 * 
 * @param {Object} to - 即将进入的目标路由对象
 * @param {Object} from - 当前要离开的路由对象
 * @param {Function} next - 放行函数，调用后才会继续跳转
 */
router.beforeEach((to, from, next) => {
  // 检查目标路由是否需要登录权限
  if (to.meta.requireLogin) {
    // 需要登录权限，检查用户是否已登录
    if (isLoggedIn()) {
      // 已登录，放行
      next()
    } else {
      // 未登录，跳转到登录页
      next('/login')
    }
  } 
  // 检查目标路由是否需要管理员权限
  else if (to.meta.requireAdmin) {
    // 需要管理员权限，检查是否是管理员登录
    if (isAdminLoggedIn()) {
      // 是管理员，放行
      next()
    } else {
      // 不是管理员，跳转到登录页
      next('/login')
    }
  } 
  // 不需要特殊权限的页面，直接放行
  else {
    next()
  }
})

// 导出路由实例，供main.js使用
export default router
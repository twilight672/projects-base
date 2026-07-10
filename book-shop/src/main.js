/**
 * 应用入口文件
 * 这是整个项目的启动点，负责初始化Vue实例
 */

// 引入Vue框架
import Vue from 'vue'
// 引入根组件
import App from './App.vue'
// 引入路由配置
import router from './router'

// 引入全局样式文件，这些样式会在所有组件中生效
import './assets/global.css'      // 全局通用样式
import './assets/css/reset.css'   // 重置浏览器默认样式
import './styles/theme.css'       // 主题样式

// 引入订单数据初始化方法
import initOrders from '@/utils/initOrders'

// 关闭Vue的生产环境提示
// 开发时Vue会在控制台输出一些提示信息，生产环境不需要
Vue.config.productionTip = false

// 初始化订单数据
// 应用启动时先准备好订单数据，避免首次访问订单页面时数据为空
initOrders()

// 引入本地存储工具类，并挂载到Vue原型上
// 这样所有组件都可以通过 this.$storage 访问存储方法
import * as storage from './utils/storage'
Vue.prototype.$storage = storage

// 创建Vue根实例并挂载到页面
// router: 注入路由配置，让整个应用支持页面跳转
// render: 渲染函数，将App组件渲染为真实的DOM
// $mount('#app'): 将Vue实例挂载到index.html中id为app的DOM元素上
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
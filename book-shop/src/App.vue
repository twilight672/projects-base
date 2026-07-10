<!--
  App.vue - 根组件
  这是整个应用的容器，所有页面都在这里渲染
  主要包含两部分：路由视图（页面内容）和底部导航栏
-->

<template>
  <div id="app">
    <!-- 
      路由视图容器
      根据当前访问的路径，自动渲染对应的页面组件
      比如：访问 /cart 就显示购物车页面，访问 /profile 就显示个人中心页面
      
      transition: 页面切换时的过渡动画
      name="fade": 使用淡入淡出效果
      mode="out-in": 先让旧页面淡出，再让新页面淡入，避免两个页面同时显示
    -->
    <transition name="fade" mode="out-in">
      <router-view />
    </transition>
    
    <!-- 
      底部导航栏
      只在需要显示的页面才渲染，比如登录页和注册页就不显示
      v-if="showTabBar": 根据计算属性决定是否显示
    -->
    <TabBar v-if="showTabBar" />
  </div>
</template>

<script>
// 引入底部导航栏组件
import TabBar from './components/TabBar.vue'

export default {
  name: 'App',
  
  // 注册子组件，注册后才能在模板中使用
  components: {
    TabBar
  },
  
  computed: {
    /**
     * 判断当前页面是否需要显示底部导航栏
     * 
     * 有些页面不应该显示导航栏：
     * - 登录页、注册页：用户还没登录，不应该看到导航
     * - 管理后台：管理员界面有自己的导航，不需要这个
     * 
     * @returns {boolean} true表示显示导航栏，false表示隐藏
     */
    showTabBar() {
      // 定义需要隐藏导航栏的路径列表
      const hidePaths = ['/login', '/register', '/admin/dashboard', '/admin/books', '/admin/users']
      // 检查当前路径是否以这些路径开头
      // 比如当前是 /admin/books/edit，也会被匹配到，从而隐藏导航栏
      return !hidePaths.some(path => this.$route.path.startsWith(path))
    }
  }
}
</script>

<style>
/* 全局样式已在 src/assets/global.css 中定义，这里保持简洁 */
</style>
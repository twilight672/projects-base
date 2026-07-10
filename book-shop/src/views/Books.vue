<template>
  <!-- 
    Books.vue - 书籍列表页组件
    功能：展示所有书籍列表（备用页面）
    布局：标题栏 + 书籍列表
    说明：当前未启用，实际使用 Category.vue 作为书籍分类浏览入口
  -->
  <div class="books">
    <h1 class="page-title">书籍列表</h1>
  </div>
</template>

<script>
export default {
  name: 'Books'
}
</script>

<style scoped>
.books {
  padding: 20px;
  min-height: calc(100vh - 120px);
  background: linear-gradient(180deg, rgba(22, 22, 30, 0.95) 0%, rgba(44, 44, 52, 0.9) 100%);
}

.page-title {
  color: var(--text-primary);
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 20px;
}
</style>
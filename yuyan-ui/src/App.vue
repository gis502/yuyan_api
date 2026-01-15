<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <h1>雨燕应急-重大灾害无人机应急合作机制管理平台</h1>
      </el-header>
      <el-main>
        <el-menu
          :default-active="$route.path"
          mode="horizontal"
          router
          class="mb-4"
        >
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/teams">团队管理</el-menu-item>
        </el-menu>
        <RouterView />
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
.common-layout {
  min-height: 100vh;
}

.el-header {
  background-color: #409eff;
  color: white;
  display: flex;
  align-items: center;
}

.el-main {
  padding-top: 20px;
  padding-left: 20px;
  padding-right: 20px;
}

.mb-4 {
  margin-bottom: 20px;
}
</style>
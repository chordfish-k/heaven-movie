<script setup>
import { RouterView } from 'vue-router'
import MovieHeader from './components/MovieHeader.vue';


const onHandleSearch = (kw) => {
  window.open(`/list?keyword=${kw}`)
}
</script>

<template>
  <el-container>
    <el-header>
      <MovieHeader @handle-search="onHandleSearch" />
    </el-header>
    <el-container class="main">
      <!-- <RouterView :key="$route.fullPath" /> -->
      <RouterView />
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.content {
  width: 100%;
  // height: 100vh;
  // margin: auto;
}

.el-container,
.el-header {
  padding: 0;
  margin: 0;
}

.main {
  // margin: auto;
  // height: 100%;
  overflow: hidden;
  padding: 0;
}
</style>

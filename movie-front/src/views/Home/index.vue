<script setup>
import { computed, defineProps, onMounted, ref, toRefs } from 'vue';
import { getMovieQueryAPI } from '@/apis/movie'

const title = ref("正在热映")
const hotData = ref({})
const queryDTO = ref({
    showType: 1,
    page: 1,
    pageSize: 8
})

// 根据条件查询电影
const getMovieQueryHotData = async () => {
    const res = await getMovieQueryAPI(queryDTO.value)
    hotData.value = res.data
    // console.log("hot", hotData.value)
}

onMounted(() => {
    getMovieQueryHotData()
})


</script>

<template>
    <div class="main">
        <el-main>
            <MovieModule class="module-hot"
                         :title="title"
                         :data="hotData"
                         :all-btn-link="{ path: '/list', query: { showType: 1 } }"
                         :max-count="8" />
        </el-main>
        <el-aside>
            <MovieTopListGroup />
        </el-aside>
    </div>
</template>

<style lang="scss" scoped>
.main {
    margin: auto;
    display: flex;
}

.module-hot {
    max-width: calc(4*190px);
}

.el-main {
    width: calc(210px * 4);
    overflow: hidden;

}

.el-aside {
    width: 30vw;
    padding: 20px;
    overflow: hidden;
}
</style>
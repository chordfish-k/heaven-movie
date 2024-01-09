<script setup>
import TagsPanel from '@/views/List/components/TagsPanel.vue'
import { getMovieQueryAPI } from '@/apis/movie';
import router from '@/router';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute()

const queryDTO = ref({
    showType: 0,
    sortId: 0,
    page: 1,
    pageSize: 30
})

const sortId = ref(0)

const showTypeList = ref([
    "全部电影",
    "正在热映"
])


const data = ref({})

// 根据条件查询电影
const getMovieQueryData = async () => {
    let query = queryDTO.value
    if (route.query.keyword) {
        query.keyword = route.query.keyword
    }
    const res = await getMovieQueryAPI(query)
    data.value = res.data
    console.log(data.value)
}

// 页码改变
const onCurrentPageChange = (page) => {
    queryDTO.value.page = page
    router.replace({ path: "/list", query: queryDTO.value })
    getMovieQueryData()
}

onMounted(() => {
    queryDTO.value = {
        ...queryDTO.value,
        ...route.query
    }
    getMovieQueryData()
})

// 处理已选标签
const onTabChange = (paramName, value, index) => {
    queryDTO.value[paramName] = index
    router.replace({ path: "/list", query: queryDTO.value })
    getMovieQueryData()
}

// 显示类型切换
const onShowTypeChange = (index) => {
    console.log(index)
    queryDTO.value.showType = index
    router.replace({ path: "/list", query: queryDTO.value })
    getMovieQueryData()
}

// 排序类型切换
const onSortIdChange = () => {
    queryDTO.value.sortId = sortId.value
    router.replace({ path: "/list", query: queryDTO.value })
    getMovieQueryData()
}


</script>

<template>
    <el-main class="list-main">
        <MovieNavBar class="navbar"
                     :list="showTypeList"
                     :index="$route.query.showType ? $route.query.showType : 0"
                     @nav-change="onShowTypeChange" />
        <!-- 标签选择器 -->
        <TagsPanel @tab-change="onTabChange" />
        <div class="sort-radio">
            <el-radio-group v-model="sortId"
                            @change="onSortIdChange">
                <el-radio :label="0"
                          size="large">按评分排序</el-radio>
                <el-radio :label="1"
                          size="large">按时间排序</el-radio>

            </el-radio-group>
        </div>

        <MovieModule :max-count="-1"
                     :data="data" />

        <!-- 页码 -->
        <div class="pagination-block">
            <el-pagination layout="prev, pager, next"
                           :total="parseInt(data.total)"
                           :page-size="parseInt(queryDTO.pageSize)"
                           @current-change="onCurrentPageChange" />
        </div>
    </el-main>
</template>

<style lang="scss" scoped>
.pagination-block {
    margin-bottom: auto 0;
    margin: 1rem 0;
    display: flex;
    justify-content: center;
}

.list-main {
    margin: auto;
    padding: 0;
    overflow: hidden;
}

.navbar {
    margin-bottom: 50px;
}

.sort-radio {
    margin: 0 auto;
    margin-top: 50px;
    width: fit-content;
    flex-wrap: nowrap;
}
</style>
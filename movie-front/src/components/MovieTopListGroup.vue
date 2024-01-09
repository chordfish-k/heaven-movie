<script setup>
import { onMounted, ref } from 'vue';
import MovieTopList from './MovieTopList.vue';
import { getRankAPI } from '@/apis/rank'
import router from '@/router';

const listInfo = ref({
    comp: {
        title: "综合排行",
        detailedLink: "/rank/3",
        list: []
    },
    hot: {
        title: "热度排行",
        detailedLink: "/rank/0",
        pattern: "{}播放",
        list: []
    }
})

const getRankData = async (key, rankId, count) => {
    const res = await getRankAPI(rankId, count)
    let pattern = listInfo.value[key].pattern
    if (pattern) {
        res.data.forEach(e => {
            e.rankValue = pattern.replace("{}", e.rankValue)
        });
    }
    listInfo.value[key].list = res.data
    listInfo.value[key].key = key
}

onMounted(async () => {
    await Promise.all([getRankData("hot", 1, 5), getRankData("comp", 3, 5)])
    listInfo.value["comp"].list = listInfo.value["comp"].list.map(e => {
        e.rankValue = e.rankValue.split(",")[0]
        return e;
    })
})

const handleItemClicked = (key, index) => {
    router.push(`/movie/${listInfo.value[key].list[index].movieId}`)
}
</script>

<template>
    <div class="toplist-group">
        <MovieTopList :data="listInfo.hot"
                      @item-clicked="handleItemClicked" />
        <!-- <MovieTopList :data="listInfo.comp"
                      @item-clicked="handleItemClicked" /> -->
    </div>
</template>

<style lang="scss" scoped></style>
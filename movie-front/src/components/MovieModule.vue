<script setup>
import { onMounted, ref, toRefs, watch } from 'vue';
import MoviePosterItem from './MoviePosterItem.vue'

const props = defineProps({
    title: String,
    maxCount: Number,
    allBtnLink: Object,
    data: Object
})


const { title, maxCount, allBtnLink, data } = toRefs(props)


const movieItems = ref([])


onMounted(() => {
    watch(data, (v) => {
        movieItems.value = v.records
        movieItems.value.forEach((d) => {
            d.posterSrc = "//localhost:8080/img/" + d.id + ".jpg"
        })
    })
})


</script>

<template>
    <div class="movie-module">
        <div v-if="title"
             class="module-header">
            <div class="module-title">
                <span class="module-title-text">
                    {{ title }}
                    （{{ data.total }}）
                </span>
                <RouterLink v-if="maxCount >= 0 && allBtnLink != {}"
                            class="module-all-btn"
                            :to="allBtnLink">
                    <span class="module-all-btn-text">全部</span>
                    <el-icon>
                        <ArrowRightBold />
                    </el-icon>
                </RouterLink>
            </div>
        </div>
        <dl class="module-list">
            <dd
                v-for="info in (maxCount == -1 || movieItems && movieItems.length <= maxCount ? movieItems : movieItems.slice(0, maxCount))">
                <MoviePosterItem :id="info.id"
                                 :title="info.name"
                                 :score="info.score"
                                 :type="info.type"
                                 :staff="info.staff"
                                 :release-time="info.releaseTime"
                                 :poster-src="info.posterSrc"
                                 :vip="info.vip" />
            </dd>
        </dl>
    </div>
</template>



<style lang="scss" scoped>
.movie-module {
    padding-top: 20px;
    padding-bottom: 20px;
    margin: auto;
    max-width: calc(6*190px);
}

.module-header {
    width: 100%;
    margin: auto;
    display: flex;
    height: 50px;
}

.module-title {
    color: orange;
    font-size: 26px;
    margin-right: 15px;
    letter-spacing: 3px;
    width: 100%;
    line-height: 40px;
}

.module-all-btn {
    float: right;
    text-decoration: none;
    font-size: 14px;
    color: orange;
    margin-top: 5px;
    letter-spacing: 0;

    .el-icon {
        vertical-align: middle;
        position: relative;
        top: -1px;
    }
}

.module-all-btn-text {
    padding-bottom: 5px;
    position: relative;
    text-align: center;
    line-height: 35px;
}


.module-all-btn:hover {
    cursor: pointer;
}

.module-list dd {
    margin: 15px;
    display: inline-block;
    vertical-align: top;
    position: relative;
}
</style>
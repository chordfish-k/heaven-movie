<script setup>
import router from '@/router';
import { ref, toRefs, watchEffect } from 'vue';
const props = defineProps({
    id: Number,
    title: String,
    score: String,
    releaseTime: String,
    type: String,
    staff: String,
    posterSrc: String,
    vip: Number
})

const { id, title, score, posterSrc, releaseTime, type, staff, vip } = toRefs(props)

const isVisible = ref(false)

const toMovie = () => {
    // console.log("to:", id.value)
    window.open("/movie/" + id.value)
}

</script>

<template>
    <div class="movie-poster"
         @mouseover="isVisible = true"
         @mouseout="isVisible = false"
         @click="toMovie">
        <img class="movie-img"
             :src="posterSrc"
             :alt="title + '封面'">
        <div v-if="vip"
             class="movie-vip">
            VIP
        </div>
        <div class="movie-overlay">
            <div class="movie-info">
                <div v-show="score != '暂无评分'"
                     class="movie-score">
                    {{ score }}
                </div>
                <div class="movie-title">
                    {{ title }}
                </div>
            </div>
        </div>
        <div class="movie-hover"
             v-show="isVisible">
            <img class="movie-img"
                 :src="posterSrc"
                 :alt="title + '封面'">
            <div class="movie-hover-info">
                <div class="movie-hover-title">
                    <span class="name">{{ title }}</span>
                    <span v-show="score != '暂无评分'"
                          class="score">{{ score }}</span>
                </div>
                <div class="movie-hover-title">
                    <span class="key">类型：</span>
                    <span class="value">{{ type.split(",").join("/") }}</span>
                </div>
                <div class="movie-hover-title">
                    <span class="key">主演：</span>
                    <span class="value">{{ staff.split(",").join("/") }}</span>
                </div>
                <div class="movie-hover-title">
                    <span class="key">上映时间：</span>
                    <span class="value">{{ releaseTime }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.movie-poster {
    background-color: #fcfcfc;
    width: 160px;
    height: 220px;
    // overflow: hidden;
    position: relative;
    margin: -1px;
    cursor: pointer;
}

.movie-img {
    width: 100%;
    height: 100%;
}

.movie-vip {
    background-color: $mainColor;
    color: red;
    width: fit-content;
    font-size: 14px;
    line-height: 15px;
    font-weight: bold;
    padding: 2px 8px;
    position: absolute;
    top: 0;
    left: 0;
}

.movie-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAKAAAABQCAMAAACpg44GAAAAbFBMVEUiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiIiK2HsfSAAAAJHRSTlPMxr63q6OahzclHRULBgHCsZOOf3p1cGtmYVxXUk1IQz4wLBAszz8hAAAAhklEQVRo3u3OxY0DAAADsBxDmZn337FTVPIjnsAZ4/zgCOcHhzg/+MBlgPOD/zg/+Ifzg3dcbrj84vzgFZcLLmdcTrgccTngssdlh8sWlw0ua1xWuCxxWeDyg/ODc1xmuHzj/OAXzg9+4vzgFJcPnB98x/nBCS5vOD9YVVVVVVVVVVVVr/IEUdzLcLhtP2AAAAAASUVORK5CYII=) repeat-x bottom;
}

.movie-overlay .movie-info {
    color: #fff;
    position: absolute;
    bottom: 7px;
    width: 100%;
}

.movie-score {
    color: orange;
    font-weight: bold;
    font-style: italic;
    float: right;
    margin-right: 10px;
}

.movie-title {
    font-size: 16px;
    line-height: 22px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin: 0 10px;
}

.movie-hover {
    position: absolute;
    top: -50px;
    left: -49px;
    width: 218px;
    height: 300px;
    box-shadow: 0 0 16px #fff, 0 0 6px rgba(0, 0, 0, .2);
    z-index: 5;
    cursor: pointer;
}

.movie-hover-info {
    padding: 16px;
    height: 150px;
    box-sizing: border-box;
    position: absolute;
    z-index: 11;
    top: 150px;
    background: #fff;
    width: 218px;

    .movie-hover-title {
        font-size: 16px;
        line-height: 16px;
        color: #999;
        margin-top: 12px;
        width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;

        .name {
            position: relative;
            font-size: 20px;
            line-height: 20px;
            color: #333;
            width: 140px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            float: left;
            top: -2px;
        }

        .score {
            float: right;
            margin-right: 5px;
            color: orange;
            font-weight: bold;
            font-size: 20px;
            font-style: italic;
        }

        .key {
            margin-top: 6px;
            color: #333;
            line-height: 20px;

        }
    }


}
</style>
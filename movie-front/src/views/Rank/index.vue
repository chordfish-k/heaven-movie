<script setup>
import { computed, onMounted, onUpdated, ref, toRefs } from 'vue';
import { getRankAPI } from '@/apis/rank';
import router from '@/router';

const props = defineProps({
    rankType: Number
})

const rankType = ref(parseInt(props.rankType))


let navSetting = ref([
    {
        label: "评分排行",
        rankType: 0,
        valueClass: 'score-num'
    },
    {
        label: "热度排行",
        rankType: 1,
        valueClass: 'view-num'
    },
    {
        label: "好评排行",
        rankType: 2,
        valueClass: 'like-num'
    },
    {
        label: "综合排行",
        rankType: 3,
        valueClass: 'comp-num'
    },
])

const rankTypeList = computed(() => {
    return navSetting.value.map(e => {
        return e.label
    })
})

const rankList = ref([])
const getRankData = async (index) => {
    const res = await getRankAPI(navSetting.value[index].rankType)
    rankList.value = res.data
}

const onRankTypeChange = (index) => {
    rankType.value = navSetting.value[index].rankType

    router.push('/rank/' + rankType.value)
    // getRankData(rankType.value)
}

const getValueClass = () => {
    return navSetting.value[rankType.value]?.valueClass
}

onUpdated(() => {
    getRankData(rankType.value)
})

onMounted(() => {
    getRankData(rankType.value)
})
</script>

<template>
    <el-main class="rank-main">
        <MovieNavBar class="navbar"
                     :list="rankTypeList"
                     :index="rankType"
                     @nav-change="onRankTypeChange" />
        <div class="claim">
            排行榜每日0点更新
        </div>
        <dl class="rank-wrapper">
            <dd v-for="(item, index) in rankList">
                <i class="rank-index"
                   :class="'rank-index-' + (index + 1)">
                    {{ index + 1 }}
                </i>
                <a :title="item.name"
                   class="image-link"
                   :href="`/movie/${item.movieId}`">
                    <img :alt="item.name"
                         :src="'//localhost:8080/img/' + item.movieId + '.jpg'"
                         class="rank-img">
                </a>
                <div class="rank-item-main">
                    <div class="rank-item-content">
                        <div class="movie-item-info">
                            <p class="name">
                                <a :href="`/movie/${item.movieId}`">
                                    {{ item.name }}
                                </a>
                            </p>
                            <p class="staff">
                                主演：{{ item.staff.split(",").slice(0, 3).join(" / ") }}
                            </p>
                            <p class="releasetime">
                                上映时间：{{ item.releaseTime }}
                            </p>
                        </div>
                        <div class="movie-item-number"
                             :class="getValueClass()">
                            <p v-if="rankType == 0"
                               class="score">
                                <i class="integer">{{ item.rankValue.split(".")[0] }}.</i>
                                <i class="fraction">{{ item.rankValue.split(".")[1] }}</i>
                            </p>
                            <p v-if="rankType == 1"
                               class="view">
                                播放次数：<i class="value">{{ item.rankValue }}</i>次
                            </p>
                            <p v-if="rankType == 2"
                               class="like">
                                点赞数：<i class="value">{{ item.rankValue }}</i>
                            </p>
                            <p v-if="rankType == 3"
                               class="score">
                            <div>
                                <i class="integer">{{ item.rankValue.split(",")[0].split(".")[0] }}.</i>
                                <i class="fraction">{{ item.rankValue.split(",")[0].split(".")[1] }}</i>
                            </div>
                            <div>
                                {{ item.rankValue.split(",")[1] }}人评分
                            </div>
                            </p>
                        </div>
                    </div>
                </div>
            </dd>
        </dl>
    </el-main>
</template>

<style lang="scss" scoped>
.rank-main {
    // margin: auto;
    padding: 0;
    overflow: hidden;
}

.claim {
    text-align: center;
    margin-top: 5px;
}

.rank-wrapper {
    margin-top: 40px;
    text-align: center;

    dd {
        margin-bottom: 30px;
        font-size: 0;
        position: relative;
        overflow: hidden;
    }

    .image-link {
        display: inline-block;
        width: 160px;
        height: 220px;
        margin-left: 80px;
        position: relative;
    }
}

.rank-img {
    vertical-align: middle;
}

.rank-index {
    display: inline-block;
    width: 50px;
    height: 50px;
    line-height: 50px;
    text-align: center;
    background: #f7f7f7;
    font-size: 18px;
    color: #999;
    font-weight: 700;
    position: absolute;
    top: 85px;
}

.rank-index-1 {
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAMAAAAp4XiDAAAAXVBMVEXvQjj////hOTDqPjToPTPtQDbthoHlPDL75OL2xMHnX1jjOjH63dvvlJD++PjyqaXwnZnvmZTmV1DjRT3+9/f99fT51dP3yMX0tbLzr6vrfHboaGLkTETjSD/jRDtUdlqqAAAAmUlEQVRIx+3PNw7EMAxEUY5EZWdvTvc/5gYs4NDQglu9is0HMVQUxX5aUya2ljN/xKaJOqsYPeDHjY02REYFfAT1vbVY1ENi7vHTM6fBSk3yqDqLP9tVaFlI3Bkr0QgJX1fFyZGA61Vi5fk3LBxeJDERCxcnJ08s3A2J1BEzXpEsVZipmWQuYOZhSMYtJsHRBobVxGkqimKPN2KiBE+fgK2MAAAAAElFTkSuQmCC);
    font-size: 0;
}

.rank-index-2,
.rank-index-3 {
    background-color: $mainColor;
    color: white;
}

.rank-item-main {
    display: inline-block;
    width: 680px;
    margin-left: 30px;
    height: 219px;
    line-height: 219px;
    border-bottom: 1px solid #333;
}

.rank-item-content {
    display: inline-block;
    vertical-align: middle;
    width: 680px;
    font-size: 0;
    line-height: 1;
    text-align: left;

    .name a {
        font-size: 26px;
        color: white;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        width: 420px;
        display: block;
    }

    .staff {
        margin-top: 18px;
        color: white;
    }

    .releasetime {
        margin-top: 8px;
        color: $textColor;
    }
}

.movie-item-info {
    font-size: 16px;
    display: inline-block;
    width: 420px;
    vertical-align: middle;
}

.movie-item-number {
    display: inline-block;
    text-align: right;
    width: 260px;
    vertical-align: top;
    margin-top: 10px;
    font-size: 16px;
}

.score-num,
.comp-num {
    color: #ffb400;

    .score {
        padding-right: 4px;
    }

    .integer {
        font-size: 56px;
        font-weight: 700;
    }

    .fraction {
        font-size: 26px;
        font-weight: 700;
    }
}

.view-num {
    color: $mainColor;

    .view {
        padding-right: 4px;
    }

    .value {
        font-size: 26px;
        font-weight: 700;
        margin-right: 5px;
    }
}

.like-num {
    color: $errorColor;

    .like {
        padding-right: 4px;
    }

    .value {
        font-size: 26px;
        font-weight: 700;
        margin-right: 5px;
    }
}

dd,
dl,
h4,
li,
p,
ul {
    padding: 0;
    margin: 0;
}

i {
    font-style: italic;
}
</style>
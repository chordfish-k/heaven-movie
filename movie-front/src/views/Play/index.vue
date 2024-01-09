<script setup>
import { ref, toRefs, watchEffect, onMounted, computed, h } from 'vue';
import { getMovieByIdAPI } from '@/apis/movie'
import { ElMessage } from 'element-plus';
import router from '@/router';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore()

const props = defineProps({
    id: String
})

const { id } = toRefs(props)

const info = ref({})
const videoPath = ref("")

// 查询该电影信息
const getMovieData = async () => {
    console.log(id.value)
    const res = await getMovieByIdAPI(id.value)
    info.value = res.data
    console.log(res.data)
    videoPath.value = "//localhost:8080/user/video/" + id.value + "?token=" + userStore.userInfo.token
}

// 简介
const splitedBrief = computed(() => {
    return String(info.value.brief).split('\n')
})

onMounted(async () => {
    await getMovieData()
    // 检测是否为vip用户
    if (info.value.vip == 1 && userStore.userInfo.vip == 0) {
        ElMessage({
            message: h('p', null, [
                h('span', null, '抱歉，您还不是 '),
                h('i', { style: 'color: orange' }, 'VIP用户'),
            ],
            ),
        })
        router.replace("/movie/" + id.value)
    }
})

</script>

<template>
    <el-main>
        <div center>
            <div class="main">

                <div class="video-container">

                    <div class="title">
                        正在播放：
                        <a @click="$router.back"
                           style="cursor: pointer;
                           text-decoration: underline;">{{ info.name }}</a>
                    </div>
                    <div v-if="videoPath"
                         class="video-box">
                        <iframe v-if="userStore.userInfo.token"
                                :src="videoPath"
                                scrolling="no"
                                border="0"
                                frameborder="no"
                                framespacing="0"
                                allowfullscreen="true"
                                class="video">
                        </iframe>
                        <div v-else
                             class="video">
                            <div class="miss">
                                您还未
                                <a style="text-decoration: underline;cursor: pointer;"
                                   @click="$router.push('/login')">
                                    登录
                                </a>
                            </div>
                        </div>
                    </div>

                </div>

                <!-- 介绍模块 -->
                <div class="module">
                    <!-- 剧情简介小模块 -->
                    <div class="module-title">
                        <h2>剧情简介</h2>
                    </div>
                    <div class="module-content">
                        <span class="dra">
                            <p v-for="p in splitedBrief">
                                {{ p }}
                            </p>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </el-main>
</template>

<style lang="scss" scoped>
.el-main {
    height: 100%;
    width: 100%;
    margin: 0;
    padding: 0;
    color: $textColor;
    overflow-x: hidden;
}


.main {
    display: relative;
    z-index: 1000;
    border-top: 1px solid #333;
}

.title {
    margin: 0 auto;
    margin-top: 20px;
    position: relative;
    max-width: 668px;
    text-align: left;
    left: 0;
}

.video-container {
    width: 100%;
    background-color: #111;
    padding: 10px;
    margin-top: 5px;
}

.video-box {
    margin: 0 auto;
    width: 668px;
}

.miss {
    font-size: 26px;
    text-align: center;
    margin: auto;
    line-height: 422px;
    background-color: black;
}

.video {

    margin-top: 10px;
    width: 668px;
    height: 422px;
}

.module {
    text-align: left;
    max-width: 1000px;
    margin: 40px auto;

    .module-title {
        h2 {
            display: inline-block;
        }


        .more {
            margin-left: 20px;
            cursor: pointer;
            font-size: 14px;
            color: #999;
            padding-right: 14px;
            background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAOCAYAAAASVl2WAAAABmJLR0QA/wD/AP+gvaeTAAAAv0lEQVQY013RTUpDQRAE4G8eghcR8ScgKCIugpJFjuIjqAvBc7jxj0muEnCjiIQQJOImB3GnbnpkfL1qpqqrunpSzvkDPxjhGdq2VarBF3q4wRHknP8RzvCEQzzguCalaHZwiwHecY6XogCf8TjFHh7Rh9Tx3AylIZa4TgWpSBuY4BSrYlFXKsr4bjrTW5HkJJa9SBW4jbtukmKxG5MDLOKqfzEPcB9LzQN8LSdfwxj7eMMlZvV/NFiPzFddEH4Bt5Y1mf3fnDwAAAAASUVORK5CYII=) no-repeat 100%;
        }

        h2 {
            margin: 0;
            padding: 0;
            font-weight: 200;
            font-size: 18px;
            color: $textColor;
            line-height: 18px;
        }

        h2::before {
            float: left;
            content: "";
            width: 4px;
            height: 18px;
            margin-right: 6px;
            background-color: #ef4238;
        }
    }

    .module-content {
        margin-top: 20px;
        margin-bottom: 40px;

        .dra {
            word-break: normal;
            width: auto;
            display: block;
            // white-space: pre-wrap;
            // word-wrap: break-word;
            // overflow: hidden;

            p {
                text-indent: 2em;
                margin-bottom: 10px;
            }
        }
    }
}
</style>
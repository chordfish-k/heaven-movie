<script setup>
import { ref, toRefs, onMounted, computed, h, onBeforeUnmount, reactive } from 'vue';
import { getMovieByIdAPI } from '@/apis/movie'
import { getMovieFileNameByIdAPI } from '@/apis/video'
import { ElMessage } from 'element-plus';
import router from '@/router';
import { useUserStore } from '@/stores/userStore';
import axios, { Axios } from 'axios';


const userStore = useUserStore()

const props = defineProps({
    id: String
})

const { id } = toRefs(props)

const playerOptions = ref({
    // height: 200,
    // width: document.documentElement.clientWidth, //播放器宽度
    // src: '',
    playbackRates: [0.5, 1.0, 1.5, 2.0], // 播放速度
    autoplay: 'any', // 如果true,浏览器准备好时开始回放。
    muted: false, // 静音
    loop: true, // 导致视频一结束就重新开始。
    preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
    language: 'zh-CN',
    aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
    fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
    notSupportedMessage: '此视频暂无法播放，请稍后再试', // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
    controls: true,
    controlBar: {
        timeDivider: true,
        durationDisplay: true,
        remainingTimeDisplay: false,
        fullscreenToggle: true // 全屏按钮
    },
})


const info = ref({})
const videoPath = ref("")


// 查询该电影信息
const getMovieData = async () => {
    const res = await getMovieByIdAPI(id.value)
    info.value = res.data
    // videoPath.value = "//localhost:8080/v/rick.mp4"// + id.value + "?token=" + userStore.userInfo.token
    // videoPath.value = `//localhost:8080/v/${id.value}.mp4`
    // const lres = await getMovieFileNameByIdAPI(id.value)
    console.log(userStore.userInfo)

    const lres = await getMovieFileNameByIdAPI(id.value, userStore.userInfo.token)
    console.log(lres)
    const blob = new Blob([lres.data], { type: lres.data.type })
    // videoPath.value = `//localhost:8080/v/${lres.data.src}`
    videoPath.value = URL.createObjectURL(blob)
    console.log(videoPath.value)
}


// 简介
const splitedBrief = computed(() => {
    return String(info.value.brief).split('\n')
})

onMounted(async () => {
    await getMovieData()

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


                        <!--<video-player v-if="userStore.userInfo.token"
                                      :src="videoPath"
                                      :options="playerOptions"
                                      :volume="0.6"
                                      controlsList="nodownload" />-->
                        <video v-if="userStore.userInfo.token"
                               controls
                               autoplay
                               class="video"
                               controlslist="nodownload">
                            <source :src="videoPath"
                                    type="video/mp4">
                        </video>
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
    background-color: black;
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
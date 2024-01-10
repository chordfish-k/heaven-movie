<script setup>
import router from '@/router';
import { ref, toRefs, onMounted, computed, h } from 'vue';
import { getMovieByIdAPI } from '@/apis/movie'
import { getMovieLikeAPI, putMovieLikeAPI } from '@/apis/like'
import { ElMessage } from 'element-plus';
import { useUserStore } from '@/stores/userStore';
// 修复该页提示信息出现在底部的问题
import 'element-plus/dist/index.css'


const userStore = useUserStore()

const props = defineProps({
    id: String
})

const { id } = toRefs(props)

const info = ref({})
const likeFlag = ref(0)

// 查询该电影信息
const getMovieData = async () => {
    // console.log(id.value)
    const res = await getMovieByIdAPI(id.value)
    info.value = res.data

    if (userStore.userInfo.token) {
        const lres = await getMovieLikeAPI(id.value)
        likeFlag.value = lres.data
    }
}

onMounted(async () => {
    getMovieData()
    window.scrollTo(0, 0)
})

// 标签页切换
const activeName = ref('brief')

const onTabClicked = (tab, event) => {
    // console.log(tab, event)
}

const onStaffClicked = (staff) => {
    window.open("/list?staff=" + staff)
}

const onStaffMoreClicked = () => {
    window.scrollTo(0, 0);
    activeName.value = 'staff';
}

// 简介
const splitedBrief = computed(() => {
    return String(info.value.brief).split('\n')
})

// 去在线播放页
const onPlayClicked = () => {
    if (info.value.vip && !userStore.userInfo.vip) {
        ElMessage({
            message: h('p', null, [
                h('span', null, '抱歉，您还不是 '),
                h('i', { style: 'color: orange' }, 'VIP用户'),
            ],
            ),
        })
    }
    else {
        router.push("/play/" + id.value)
    }
}

// 按下like
const onLikeClicked = async () => {
    // 登录才能点赞
    if (userStore.userInfo.token) {
        await putMovieLikeAPI(id.value)
        getMovieData()
    }
    else {
        router.push("/login")
        ElMessage({
            type: 'warning',
            message: '请登录'
        })
    }

}

</script>

<template>
    <el-main>
        <div class="movie-banner"
             :style="'background:' + 'url(http://localhost:8080/img/' + id + '.jpg) no-repeat;background-size:100% 100%;filter:brightness(20%) blur(5px);'">
        </div>
        <el-row :gutter="20"
                class="movie-info">
            <el-col :span="3"></el-col>
            <el-col :span="4">
                <img class="movie-poster"
                     :src="'http://localhost:8080/img/' + id + '.jpg'" />
            </el-col>
            <el-col :span="8"
                    class="movie-info-box">
                <div class="movie-name">{{ info.name }}</div>
                <table>
                    <tr class="movie-type">
                        <th>类型：</th>
                        <td>{{ info.type?.replaceAll(',', ' / ') }}</td>
                    </tr>
                    <tr class="movie-time">
                        <th>上映时间：</th>
                        <td>{{ info.releaseTime }}</td>
                    </tr>
                    <tr class="movie-zoom">
                        <th>制片国家/地区：</th>
                        <td>{{ info.zoom?.replaceAll(',', ' / ') }}</td>
                    </tr>
                    <tr class="movie-duration">
                        <th>片长：</th>
                        <td>{{ info.duration }}</td>
                    </tr>
                </table>
                <div class="movie-opt">
                    <div class="movie-vl-line">
                        <span class="movie-views">
                            <el-icon>
                                <span style="font-size: 10px;
                                      border: 1px solid #aaa;
                                      padding: 0 5px;
                                      border-radius: 3px; 
                                      position: relative; 
                                      top: -2px;
                                      margin-right: 3px;">▶</span>
                            </el-icon>
                            <span class="value">{{ info.views }}</span>
                        </span>
                        <span class="movie-likes"
                              :class="{ 'like-active': likeFlag }"
                              @click="onLikeClicked">
                            <el-icon>
                                ❤
                            </el-icon>
                            <span class="value">{{ info.likes }}</span>
                        </span>
                    </div>
                    <el-button class="movie-opt-btn"
                               type="primary"
                               @click="onPlayClicked">
                        <div v-if="info.vip"
                             class="movie-opt-vip">
                            VIP
                        </div>
                        在线观看
                    </el-button>

                </div>
            </el-col>
            <el-col class="movie-score-box"
                    :span="5">
                <div><span style="font-weight: bold;">豆瓣评分</span></div>
                <span class="movie-score-num">
                    <span class="integer">{{ info.score?.split(".")[0] }}</span>
                    .
                    <span class="fraction">{{ info.score?.split(".")[1] }}</span>

                </span>
                <div class="star-wrapper">
                    <div class="star-on"
                         :style="'width: ' + (Number(info.score) * 10) + '%;'"></div>
                </div>
                <div class="movie-eval-num">{{ info.evalNum }}人评分</div>
            </el-col>
        </el-row>
        <div class="movie-detail">
            <el-tabs v-model="activeName"
                     class="movie-detail-tabs"
                     @tab-click="onTabClicked">
                <el-tab-pane label="简介"
                             name="brief">
                    <template #label>
                        <span class="custom-tabs-label">
                            介绍
                        </span>
                    </template>
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
                        <!-- 部分演职人员小模块 -->
                        <div class="module-title">
                            <h2>演职人员</h2>
                            <a @click="onStaffMoreClicked"
                               class="more">全部</a>
                        </div>
                        <div class="module-content">
                            <div class="staffs">
                                <div v-if="info.staff"
                                     v-for="staff in info.staff?.split(',').slice(0, 8)"
                                     class="staff"
                                     @click="onStaffClicked(staff)">
                                    <img class="staff-pic" />
                                    <div class="staff-name">{{ staff }}</div>
                                </div>
                            </div>

                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="演职人员"
                             name="staff">
                    <template #label>
                        <span class="custom-tabs-label">
                            演职人员
                        </span>
                    </template>
                    <!-- 演职人员模块 -->
                    <div class="staffs">
                        <div v-if="info.staff"
                             v-for="staff in info.staff.split(',')"
                             class="staff"
                             @click="onStaffClicked(staff)">
                            <img class="staff-pic" />
                            <div class="staff-name">{{ staff }}</div>
                        </div>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>

    </el-main>
</template>

<style lang="scss" scoped>
.el-main {
    margin: auto;
    overflow: hidden;
    padding: 0;
}

.movie-banner {
    height: 300px;
    width: 100vw;
    // background: rgb(202, 252, 209);
}

.movie-info {
    margin-top: -250px;

    .movie-info-box {
        line-height: 26px;
        margin-bottom: 5px;
        padding: 5px;
    }

    .movie-poster {
        border: 5px solid white;
        max-height: 300px;
    }

    .movie-name {
        font-weight: 700;
        line-height: 32px;
        font-size: 26px;
        margin-bottom: 10px;
    }
}

.movie-score-box {
    // border-left: 1px solid $textColor;
    // height: 120px;
    padding-top: 10px;

    .movie-score-num {
        color: $mainColor;
        font-weight: bold;
        font-size: 20px;
        font-style: italic;

        .integer {
            font-size: 70px;

        }

        .fraction {
            font-size: 40px;
        }

    }

    .movie-eval-num {
        margin: 10px 5px;
    }

    .star-wrapper {
        transform: scale(1.5);
        width: 60px;
        height: 12px;
        margin-left: 20px;
        position: relative;
        background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAAXNSR0IArs4c6QAAAAlwSFlzAAALEwAACxMBAJqcGAAABBdpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IlhNUCBDb3JlIDUuNC4wIj4KICAgPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4KICAgICAgPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIKICAgICAgICAgICAgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIgogICAgICAgICAgICB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIKICAgICAgICAgICAgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiCiAgICAgICAgICAgIHhtbG5zOnRpZmY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vdGlmZi8xLjAvIj4KICAgICAgICAgPHhtcDpDcmVhdG9yVG9vbD5BZG9iZSBQaG90b3Nob3AgQ0MgMjAxNCAoTWFjaW50b3NoKTwveG1wOkNyZWF0b3JUb29sPgogICAgICAgICA8eG1wTU06RG9jdW1lbnRJRD54bXAuZGlkOjA3NTk2QTNEM0MyMzExRTZCOTc2ODYzMkJCN0E1QjAwPC94bXBNTTpEb2N1bWVudElEPgogICAgICAgICA8eG1wTU06RGVyaXZlZEZyb20gcmRmOnBhcnNlVHlwZT0iUmVzb3VyY2UiPgogICAgICAgICAgICA8c3RSZWY6aW5zdGFuY2VJRD54bXAuaWlkOjhDNDdBODM5Mzc2ODExRTZBRjRDRjM1OUE2QTdDRTcyPC9zdFJlZjppbnN0YW5jZUlEPgogICAgICAgICAgICA8c3RSZWY6ZG9jdW1lbnRJRD54bXAuZGlkOjhDNDdBODNBMzc2ODExRTZBRjRDRjM1OUE2QTdDRTcyPC9zdFJlZjpkb2N1bWVudElEPgogICAgICAgICA8L3htcE1NOkRlcml2ZWRGcm9tPgogICAgICAgICA8eG1wTU06SW5zdGFuY2VJRD54bXAuaWlkOjA3NTk2QTNDM0MyMzExRTZCOTc2ODYzMkJCN0E1QjAwPC94bXBNTTpJbnN0YW5jZUlEPgogICAgICAgICA8dGlmZjpPcmllbnRhdGlvbj4xPC90aWZmOk9yaWVudGF0aW9uPgogICAgICA8L3JkZjpEZXNjcmlwdGlvbj4KICAgPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KqdsfSQAAAQpJREFUKBVdkL9KA0EQh2/vLiZYiyksLCxiE0gjpLXygaxsAyL4FkZS5xFCQAIhIBJI8AEEK3uF5Dbfb27nCP7gu/mzM7Nzm2VHijEWCrEjeE6+5ZoyDoIKoZUKzvG/4QcuU66Fn0NoGtNBSfIFXGOc/H/RKckruIMpuKrkTLA30IWgdV6ZcAsXR5Ni8n2FX+JPmOu6DZyBtGOArAqFgj10YJB8e5V7CiWt4atYXFVN+EBDLU7asFQF0i0u9xckSlXrqYoQwh/+R91ua+zwheuNGjUX6qqU5eqTPLcXtElemawGSrGk0/6S4us6l62wa1DjEHrgZ5YkNs34vsMTQ76UYYU+5hG2ilE8ABzT5mRcPmFIAAAAAElFTkSuQmCC);
        background-repeat: repeat-x;
        margin-bottom: 4px;

        .star-on {
            height: 12px;
            background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAAXNSR0IArs4c6QAAAAlwSFlzAAALEwAACxMBAJqcGAAABBdpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IlhNUCBDb3JlIDUuNC4wIj4KICAgPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4KICAgICAgPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIKICAgICAgICAgICAgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIgogICAgICAgICAgICB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIKICAgICAgICAgICAgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiCiAgICAgICAgICAgIHhtbG5zOnRpZmY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vdGlmZi8xLjAvIj4KICAgICAgICAgPHhtcDpDcmVhdG9yVG9vbD5BZG9iZSBQaG90b3Nob3AgQ0MgMjAxNCAoTWFjaW50b3NoKTwveG1wOkNyZWF0b3JUb29sPgogICAgICAgICA8eG1wTU06RG9jdW1lbnRJRD54bXAuZGlkOjA3NTk2QTM5M0MyMzExRTZCOTc2ODYzMkJCN0E1QjAwPC94bXBNTTpEb2N1bWVudElEPgogICAgICAgICA8eG1wTU06RGVyaXZlZEZyb20gcmRmOnBhcnNlVHlwZT0iUmVzb3VyY2UiPgogICAgICAgICAgICA8c3RSZWY6aW5zdGFuY2VJRD54bXAuaWlkOjhDNDdBODNEMzc2ODExRTZBRjRDRjM1OUE2QTdDRTcyPC9zdFJlZjppbnN0YW5jZUlEPgogICAgICAgICAgICA8c3RSZWY6ZG9jdW1lbnRJRD54bXAuZGlkOjhDNDdBODNFMzc2ODExRTZBRjRDRjM1OUE2QTdDRTcyPC9zdFJlZjpkb2N1bWVudElEPgogICAgICAgICA8L3htcE1NOkRlcml2ZWRGcm9tPgogICAgICAgICA8eG1wTU06SW5zdGFuY2VJRD54bXAuaWlkOjA3NTk2QTM4M0MyMzExRTZCOTc2ODYzMkJCN0E1QjAwPC94bXBNTTpJbnN0YW5jZUlEPgogICAgICAgICA8dGlmZjpPcmllbnRhdGlvbj4xPC90aWZmOk9yaWVudGF0aW9uPgogICAgICA8L3JkZjpEZXNjcmlwdGlvbj4KICAgPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KSbv7AwAAAVZJREFUKBVNkb1KQ0EQhc+sm5BYWIkgFjaCaQQ7g11aKxvfwd7Oxh+QgFpY+AIqWPgSQTAGjIgIirUIgmChFnox945nZpOQC3uZPfPN7wIjnyrG7KrX2OXZd/siaUOMkChFvUXJgTamCL/xfGgbs67Rp9sIxg4D3dFCJHiiXSgTKO1TA0choWMcGaYZO0fHOiJW0XNEWVPwh3MojtjYC5bwLtrBGYUG88x4U1kfTmkFFRo/+GXoM63LQPiRxqR3l6GnAxCuKOGciSqsvEg29yE43AaFQ7r6vMMWyjkhYrvLsSnLaKaBPnFM4QZVB4tUxP+FVB3ucM4DU4KtVFZ4Be6RUKtiY6fR0/9KGmyX7xSxlrBCUfZyJTZng1qYpbEk6hawA40iqe8QUEOZzm90ucoHNhcJ1zGBeWo1evyLA4NhLXzhjvtuSh2vpvPhFgjvceVPzm1B/wEemoKi/w27MQAAAABJRU5ErkJggg==);
            background-repeat: repeat-x;
        }
    }
}

.movie-detail {
    margin-top: 50px;
    margin-left: 150px;
    margin-right: 150px;
    margin-bottom: 100px;

    .custom-tabs-label {
        font-size: 26px;
    }

    .el-tab-pane {
        color: $textColor;
    }
}

.module {
    margin-top: 40px;

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

.staffs {
    display: flex;
    flex-wrap: wrap;

    .staff {
        margin-right: 20px;
        margin-top: 20px;
        display: block;
        cursor: pointer;

        .staff-pic {
            width: 128px;
            height: 170px;
        }

        .staff-name {
            text-align: center;
            margin-top: 15px;
            max-width: 128px;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }
    }

}

.movie-opt {
    // margin-top: 75px;
    position: absolute;
    bottom: 0;

    .movie-vl-line {
        display: flex;
        margin-left: 5px;
    }

    .movie-views {
        line-height: 30px;
        min-width: 50px;

        .value {
            position: relative;
            top: -250pxpx;
            margin-left: 5px;
        }

    }

    .movie-likes {
        margin-left: 10px;
        line-height: 30px;
        min-width: 50px;
        cursor: pointer;

        .value {
            position: relative;
            top: -250pxpx;
            margin-left: 5px;
        }

    }

    .like-active {
        color: #ef4238;
    }


    .movie-opt-btn {
        width: 200px;
        margin-top: 5px;
    }

    .movie-opt-vip {
        background-color: red;
        width: fit-content;
        font-size: 10px;
        line-height: 15px;
        font-weight: bold;
        border-radius: 3px;
        padding: 0 6px;
        position: absolute;
        top: -5px;
        left: -5px;
    }
}

table {
    border-collapse: collapse;
    border-spacing: 0;

    th {
        text-align: left;
    }
}
</style>
<script setup>
import { useUserStore } from '@/stores/userStore'
import router from '@/router'
import { getUserHistoryAPI } from "@/apis/history"
import { computed, onMounted, ref, toRefs } from 'vue';
import { getMovieQueryAPI } from '@/apis/movie'
import { putUserUpgradeAPI, putUserDowngradeAPI } from '@/apis/user'

const userStore = useUserStore()

const title = ref("观看历史")
const historyData = ref({})
const queryDTO = ref({
    page: 1,
    pageSize: 12
})

// 根据条件查询电影
const getUserHistoryData = async () => {
    const res = await getUserHistoryAPI(queryDTO.value)
    historyData.value = res.data
}

onMounted(() => {
    getUserHistoryData()
})

// 升级vip
const upgradeVIP = async () => {
    const res = await putUserUpgradeAPI()
    if (res.code == 1) {
        userStore.getUserInfo(userStore.loginParams)
    }
}

// 退出vip
const downgradeVIP = async () => {
    const res = await putUserDowngradeAPI()
    if (res.code == 1) {
        userStore.getUserInfo(userStore.loginParams)
    }
}
</script>


<template>
    <el-main class="main">
        <div class="user-banner">
            <div class="user-banner-inner">
                <div class="user-avatar">
                    <div class="user-avatar-symbol">
                        {{ userStore.userInfo.name.at(0) }}
                    </div>
                </div>
                <div class="user-name">
                    {{ userStore.userInfo.name }}
                </div>
                <div>
                    <el-popconfirm v-if="!userStore.userInfo.vip"
                                   @confirm="upgradeVIP"
                                   title="是否开通VIP?"
                                   confirm-button-text="确认"
                                   cancel-button-text="取消">
                        <template #reference>
                            <div class="user-vip">开通VIP</div>

                        </template>
                    </el-popconfirm>
                    <el-popconfirm v-else
                                   @confirm="downgradeVIP"
                                   width="200px"
                                   title="您已是VIP用户，是否退出VIP？"
                                   confirm-button-text="确认"
                                   cancel-button-text="取消">
                        <template #reference>
                            <div class="user-vip vip">VIP用户</div>
                        </template>
                    </el-popconfirm>
                </div>
            </div>
        </div>
        <div class="user-content">
            <div class="movie-history">
                <MovieModule class="module-history"
                             :title="title"
                             :data="historyData"
                             :max-count="12" />
            </div>
        </div>
    </el-main>
</template>


<style scoped lang="scss">
.main {
    margin: auto;
    padding: 0;
    overflow: hidden;
}

.user-banner {
    width: 100%;
    height: 150px;
    background-color: #222;

    .user-banner-inner {
        display: flex;
        position: relative;
        top: 45px;
        justify-items: center;
        align-items: end;
        width: 75%;
        // max-width: 75%;
        height: 100px;
        margin: auto;

        .user-avatar {
            width: 100px;
            height: 100px;
            border: 3px solid white;
            background-color: black;
            border-radius: 5px;
            text-align: center;
            justify-self: center;
        }

        .user-avatar-symbol {
            font-size: 2.5em;
            line-height: 100px;
        }

        .user-name {
            margin-left: 30px;
            font-size: 1.8em;
            font-weight: 700;
            color: white;
        }

        .user-vip {
            margin: 5px;
            margin-left: 50px;
            background-color: $mainColor;
            border-radius: 3px;
            padding: 5px;
            color: white;
            font-weight: 700;
            cursor: pointer;
        }

        .vip {
            background-color: red;
        }
    }

    .user-content {
        margin-top: 12px;
        width: 75%;
    }
}

.movie-history {
    margin: auto;
}
</style>
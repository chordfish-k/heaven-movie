<script setup>
import { computed, onMounted, ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import router from '@/router';
import { useUserStore } from '@/stores/userStore';
import { getMovieQueryAPI } from '@/apis/movie';
import { useRoute } from 'vue-router';

const route = useRoute()
const userStore = useUserStore()

const navList = ref([
    {
        label: "首页",
        url: "/",
    },
    {
        label: "电影",
        url: "/list?showType=0",
    },
    {
        label: "排行",
        url: "/rank/0",
    },
])

// 搜索输入框文本
const searchInput = ref("")

// 跳转首页
const toHomePage = () => {
    router.push("/")
}

// 登出账号
const logout = () => {
    // 清除缓存的用户信息
    userStore.clearUserInfo()
}



// 定义搜索事件
const emits = defineEmits(['handleSearch'])

// 搜索
const onSearchClicked = () => {
    if (searchInput.value) {
        emits("handleSearch", searchInput.value)
    }
}

</script>

<template>
    <!-- <div class="header"> -->
    <div class="header">
        <div class="header-inner">
            <div class="header-title"
                 @click="toHomePage">
                天堂<span class="strong">电影</span>
                <ul class="header-nav">
                    <li v-for="item in navList">
                        <a class="header-nav-item"
                           :href="item.url">{{ item.label }}</a>
                    </li>
                </ul>
            </div>
            <div class="header-search">
                <el-input v-model.trim="searchInput"
                          placeholder="根据名称搜索"
                          @keyup.enter.native="onSearchClicked">
                    <template #append>
                        <el-button :icon="Search"
                                   :disabled="false"
                                   @click="onSearchClicked" />
                    </template>
                </el-input>
            </div>
            <div class="header-userInfo"
                 :class="{ vip: userStore.userInfo.vip }">
                <RouterLink to="/user">
                    {{ userStore.userInfo.name }}
                </RouterLink>

                <!-- <el-popconfirm v-if="!userStore.userInfo.vip"
                               @confirm="upgradeVIP"
                               title="是否开通VIP?"
                               confirm-button-text="确认"
                               cancel-button-text="取消">
                    <template #reference>
                        {{ userStore.userInfo.name }}
                    </template>
                </el-popconfirm>
                <el-popconfirm v-else
                               @confirm="downgradeVIP"
                               width="200px"
                               title="您已是VIP用户，是否退出VIP？"
                               confirm-button-text="确认"
                               cancel-button-text="取消">
                    <template #reference>
                        {{ userStore.userInfo.name }}
                    </template>
                </el-popconfirm> -->

            </div>
            <div class="header-login">
                <RouterLink v-if="!userStore.userInfo.token"
                            to="/login">
                    <el-link class="header-user-link"
                             :underline="false"
                             type="primary">
                        登录
                    </el-link>
                </RouterLink>
                <el-popconfirm v-else
                               @confirm="logout"
                               title="确认登出吗?"
                               confirm-button-text="确认"
                               cancel-button-text="取消">
                    <template #reference>
                        <el-link class="header-user-link"
                                 :underline="false"
                                 type="primary">登出</el-link>
                    </template>
                </el-popconfirm>
            </div>
        </div>

    </div>
    <!-- </div> -->
</template>


<style lang="scss" scoped>
.header {
    width: 100vw;
    height: 60px;
    background-color: rgb(16, 16, 16);
    border-bottom: 1px solid rgb(31, 31, 31);
    box-shadow: 1px 1px 2px rgb(31, 31, 31);
    display: flex;
    position: fixed;
    z-index: 10;
    top: 0;
    left: 0;
    flex-wrap: nowrap;

}

.header-inner {
    margin: 0 auto;
    display: flex;
}

.header-title {
    color: white;
    font-size: 22px;
    font-weight: bold;
    text-align: left;
    margin: auto 0;
    letter-spacing: 3px;
    cursor: pointer;
    text-wrap: nowrap;
    min-width: 750px;

    .strong {
        background-color: $mainColor;
        padding-left: 3px;
        border-radius: 5px;
        margin-right: 3px;
        color: #111;
    }
}

.header-nav {
    display: inline-block;
    padding: 0;
    margin-left: 15px;
    margin-top: 5px;
    height: 60px;
    text-wrap: nowrap;
    font-weight: 400;

    li {
        position: relative;
        top: -5px;
        display: inline-block;
        list-style: none;
        margin-right: 3px;

    }

    li:hover {
        background-color: #222;
        border-radius: 3px;
    }

    .header-nav-item {
        display: block;
        font-size: 14px;
        line-height: 60px;
        padding: 0 10px;
        color: white;
        cursor: pointer;
    }
}

.header-search {
    margin: auto 30px;
    text-align: center;
}

.header-userInfo {
    margin: auto 0;
    padding-left: 20px;
    padding-right: 20px;
    margin-right: 10px;
    text-align: right;
    line-height: 60px;
    cursor: pointer;
}

.header-userInfo:hover {
    background-color: #535353;
    border-radius: 3px;
}

.header-login {
    margin: auto 0;
    // min-width: 200px;
    text-align: right;

    .header-user-linkbtn {
        position: relative;
        right: 5px;
        text-align: right;
    }
}

.header-user-link {
    text-align: left;
    letter-spacing: 2px;
    text-decoration: none;
    color: orange;
    text-wrap: nowrap;
}

.vip,
.vip a {
    color: red;
}
</style>
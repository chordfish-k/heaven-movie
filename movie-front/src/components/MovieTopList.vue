<script setup>
import { ref, defineProps, toRefs } from 'vue'
const props = defineProps({
    data: {
        key: String,
        title: String,
        detailedLink: String,
        list: {
            content: String,
            value: String
        }
    }
})

const emits = defineEmits(['item-clicked'])
const { data } = toRefs(props)

const handleClicked = (index) => {
    console.log(index)
    emits('item-clicked', data.value.key, index)
}
</script>

<template>
    <div class="toplist">
        <div class="toplist-header">
            <div class="toplist-title">
                <span class="toplist-title-text">
                    {{ data.title }}
                </span>
                <a v-if="data.detailedLink"
                   :href="data.detailedLink"
                   class="toplist-all-btn">
                    <span class="toplist-all-btn-text">查看全部榜单</span>
                    <el-icon>
                        <ArrowRightBold />
                    </el-icon>
                </a>
            </div>
        </div>
        <ul class="toplist-list">
            <li v-for="(item, index) in data.list"
                @click="handleClicked(index)"
                class="toplist-list-item">
                <span class="number"
                      :class="{ top3: index < 3 }">{{ index + 1 }}</span>
                <span class="content">{{ item.name }}</span>
                <span class="value">{{ item.rankValue }}</span>
            </li>
        </ul>
    </div>
</template>


<style lang="scss" scoped>
.toplist {
    padding: 20px;
    margin: auto;
    width: 340px;
}

.toplist-header {
    width: 100%;
    margin: auto;
    display: flex;
    height: 50px;
}

.toplist-title {
    color: orange;
    font-size: 26px;
    margin-left: 30px;
    letter-spacing: 3px;
    width: 100%;
    line-height: 40px;
    // border-bottom: 1px solid orange;
}

.toplist-all-btn {
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

.toplist-all-btn-text {
    padding-bottom: 5px;
    position: relative;
    text-align: center;
    line-height: 35px;
}


.toplist-all-btn:hover {
    cursor: pointer;
}

.toplist-list {
    margin: 10px 0 0 10px;
}

ul,
ul li {
    list-style: none;
    padding: 0;
    margin: 0;
}

.toplist-list-item {
    cursor: pointer;
    margin-left: 20px;
    padding: 4px;
    transition: background-color 0.3s ease;

    .number {
        font-style: italic;
        font-size: 22px;
        margin-right: 10px;
    }

    .content {
        padding: 0 0 0 10px;
        position: relative;
        top: -3px;
    }

    .value {
        float: right;
        position: relative;
        top: 5px;
        color: orange;
    }

    .top3 {
        color: red;
    }
}

.toplist-list-item:hover {
    background-color: #00000055;
}
</style>
<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getCategoryByTypeAPI } from '@/apis/movie'
import { useCategoryStore } from '@/stores/cateStore';

const route = useRoute()

const cats = ref({})
const cateStore = useCategoryStore()

// 自定义事件
const emits = defineEmits(['tab-change'])

// 获取标签tag
// const list = ["type", "zoom", "releaseTime", "vip"]
const list = ["catId", "sourceId", "yearId", "vip"]
const getCategoryData = async () => {
    for (let i = 0; i < list.length; i++) {
        const res = await getCategoryByTypeAPI(i + 1)
        cats.value[list[i]] = res.data
        cats.value[list[i]].active = 0
    }

}

// 标签切换，发送事件
const onTabChange = (lineName, tabIndex) => {
    cats.value[lineName].active = tabIndex
    let content = cats.value[lineName].tags[cats.value[lineName].active]

    emits("tab-change", lineName, content, cats.value[lineName].active)
}

onMounted(async () => {
    await getCategoryData()

    // 保存tag信息
    cateStore.setCategoryInfo(cats.value)

    list.forEach(name => {
        if (route.query[name]) {
            cats.value[name].active = parseInt(route.query[name])
        }
    })

    // if (route.query.yearBegin) {
    //     const yb = route.query.yearBegin
    //     let i = cats.value[list[2]].tags.indexOf(yb)
    //     if (i < 0) {
    //         if (parseInt(yb) >= 2000 && parseInt(yb) <= 2009) {
    //             i = cats.value[list[2]].tags.indexOf('2000-2010')
    //         }
    //         else if (yb.startsWith('19') && yb[2] >= '6') {
    //             i = cats.value[list[2]].tags.indexOf(yb[2] + '0年代')
    //             if (i < 0) {
    //                 i = cats.value[list[2]].tags.indexOf('更早')
    //             }
    //         }
    //         else if (parseInt(yb) < 1970) {
    //             i = cats.value[list[2]].tags.indexOf('更早')
    //         }
    //         else if (parseInt(yb) > new Date().getFullYear()) {
    //             i = cats.value[list[2]].tags.indexOf('未上映')
    //         }

    //     }
    //     cats.value[list[2]].active = i
    // }

    // cats.value[list[3]].active = route.query.vip ? 1 : 0
})


</script>

<template>
    <div class="tags-panel">
        <ul class="tags-lines">
            <li v-for="(value, name) in cats"
                class="tags-line">
                <div class="tags-title">{{ value.typeName }}：</div>
                <ul class="tags">
                    <li v-for="(tag, tabIndex) in value.tags"
                        :class="{ active: tabIndex == value.active }"
                        @click="onTabChange(name, tabIndex)">
                        {{ tag }}
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</template>

<style lang="scss" scoped>
.tags-panel {
    padding: 0 20px;
    margin: 0 auto;
    width: calc(6*185px);
    border-radius: 5px;
    border: 1px solid #e5e5e5;
}

.tags-title {
    height: 24px;
    line-height: 24px;
    float: left;
    color: #999;
    font-size: 14px;
}

.tags-panel li,
.tags-panel ul {
    margin: 0;
    padding: 0;
    list-style-type: none;
}

.tags {
    margin-left: 40px !important;
}

.tags li {
    border-radius: 14px;
    padding: 3px 9px;
    display: inline-block;
    margin-left: 12px;
    cursor: pointer;
}


.tags-line {
    padding: 10px 0 !important;
}

.active {
    background: $mainColor;
    color: #333;
    font-weight: 600;
}
</style>
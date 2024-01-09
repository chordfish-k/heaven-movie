<script setup>
import { ref, toRefs } from 'vue';

const props = defineProps({
    list: Array,
    index: { type: Number, default: 0 }
})


const { list } = toRefs(props)

const emits = defineEmits(['nav-change'])

const current = ref(parseInt(props.index))

const onShowTypeChange = (index) => {
    current.value = index
    emits('nav-change', current.value)
}
</script>

<template>
    <ul class="navbar">
        <li v-for="(type, index) in list">
            <a href="javascript:;"
               class="navbar-item"
               @click="onShowTypeChange(index)"
               :class="{ active: index == current }">
                {{ type }}
            </a>
        </li>
    </ul>
</template>

<style lang="scss" scoped>
.navbar {
    position: relative;
    // top: 0;
    // left: 0;
    background-color: $mainColor;
    text-align: center;
    min-width: 1200px;
    width: 100%;
    height: 60px;

    ul {
        display: inline-block;
        overflow: hidden;
    }

    li {
        display: inline-block;
        list-style: none;

        a {
            position: relative;
            left: 0;
            top: 0;
        }

        .active {
            color: #111;
            font-weight: 600;
        }

        .active:before {
            content: "";
            width: 2px;
            height: 0;
            position: absolute;
            left: 50%;
            top: 54px;
            margin-left: -8px;
            border-bottom: 7px solid $bgColor;
            border-left: 8px solid transparent;
            border-right: 8px solid transparent;
            border-top: none;
        }
    }

    .navbar-item {
        display: block;
        font-size: 18px;
        line-height: 60px;
        padding: 0 40px;
        color: white;
        cursor: pointer;
    }


}
</style>
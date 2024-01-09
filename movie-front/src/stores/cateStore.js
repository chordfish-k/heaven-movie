// 管理用户数据相关
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useCategoryStore = defineStore('category', () => {

  const categoryInfo = ref({})

  const setCategoryInfo = async(category) => {
    categoryInfo.value = category
  }


  const clearCategoryInfo = () => {
    userInfo.value = {};
  }

  return {
    categoryInfo ,
    setCategoryInfo,
    clearCategoryInfo,
  }
}, 
{
  persist: true,
})
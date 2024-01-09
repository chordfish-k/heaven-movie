// axios基础的封装
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/userStore'
import router from '@/router'

const httpInstance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000
})


// 拦截器

// axios请求拦截器
httpInstance.interceptors.request.use(config => {
  // 1. 从pinia获取token数据
  const userStore = useUserStore()
  // 2. 按照后端的要求拼接token数据
  const token = userStore.userInfo.token
  if (token) {
    config.headers.token = token
  }
  return config
}, e => Promise.reject(e))

// axios响应式拦截器
httpInstance.interceptors.response.use(res => res, e => {
  let msg = e.message;
  if (e.response.status == 401) {
    msg = "登录状态已失效，请重新登录"

  } 
  // 统一错误提示
  ElMessage({
    type: 'warning',
    message: msg
  })
  return Promise.reject(e)
})

export const http = (options) => {
  return new Promise((resolve, reject)=>{
    httpInstance(options)
        .then((response) => {
          // console.log(response)
          const res = response.data;
          if (response.status >= 200 && response.status < 300 && res.code == 1) {
              // console.log(res.data);
              resolve(res)
          } 
          else {
              // 3.3 其他错误 -> 根据后端错误信息轻显示
              console.log((res.data).msg || '请求错误')
              reject(res)
          }
        })
        .catch((error) => {
          console.log(error)
          if (error.response.status == 401) {
            // 3.2 401(Token过期)错误 -> 清理用户信息，跳转到登录页
            const memberStore = useUserStore()
            memberStore.clearUserInfo()
            router.replace({path:'/login'})
          } else {
            reject(error)
          }
      })
  })
}

export default http
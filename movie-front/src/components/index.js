// 把components中的所组件都进行全局化注册
// 通过插件的方式
import MovieHeader from './MovieHeader.vue'
import MovieModule from './MovieModule.vue'
import MoviePosterItem from './MoviePosterItem.vue'
import MovieTopList from './MovieTopList.vue'
import MovieTopListGroup from './MoviePosterItem.vue'
import MovieNavBar from './MovieNavBar.vue'

export const componentPlugin = {
  install (app) {
    // app.component('组件名字'，组件配置对象)
    app.component('MovieHeader', MovieHeader)
    app.component('MovieModule', MovieModule)
    app.component('MoviePosterItem', MoviePosterItem)
    app.component('MovieTopList', MovieTopList)
    app.component('MovieTopListGroup', MovieTopListGroup)
    app.component('MovieNavBar', MovieNavBar)
  }
}
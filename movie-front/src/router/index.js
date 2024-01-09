import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/Home/index.vue'
import ListView from '@/views/List/index.vue'
import LoginView from '@/views/Login/index.vue'
import RegisterView from '@/views/Register/index.vue'
import MovieView from '@/views/Movie/index.vue'
import PlayView from '@/views/Play/index.vue'
import RankView from '@/views/Rank/index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/list',
      name: 'list',
      props: true,
      component: ListView
    },
    {
      path: '/rank/:rankType',
      name: 'rank',
      props: true,
      component: RankView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },    
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/movie/:id',
      name: 'movie',
      props: true,
      component: MovieView
    },
    {
      path: '/play/:id',
      name: 'play',
      props: true,
      component: PlayView
    },
  ]
})

export default router

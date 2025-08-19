import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MenuView from '../views/MenuView.vue'
import SearchView from '../views/userInfo/SearchView.vue'
import DetailView from '../views/userInfo/DetailView.vue'
import MainLayout  from '@/layouts/MainLayout.vue'
const router = createRouter({
  // const router = createRouter({
  history: createWebHistory('process.env.BASE_URL'),  
  // history: createWebHistory(import.meta.env.BASE_URL),
  // history: createWebHistory('/'),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView
    },
    {
      path: '/menu',
      name: 'menu',
      component: MenuView
    },
    {
      path: '/search',
      name: 'search',
      component: SearchView
    },
    {
      path: '/detail',
      name: 'detail',
      component: DetailView
    },
    {
      path: '/app', 
      name: 'app',
      component:MainLayout
    }
  ],
});


export default router
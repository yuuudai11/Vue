import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MenuView from '../views/MenuView.vue'
import SearchView from '../views/userInfo/SearchView.vue'
import DetailView from '../views/userInfo/DetailView.vue'
const router = createRouter({
  // const router = createRouter({
//   history: createWebHistory('/'),   // 直接 '/'/'/ });
  // history: createWebHistory(import.meta.env.BASE_URL),
  history: createWebHistory('/'),
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
    }
  ],
});


export default router

// import { createRouter, createWebHistory } from 'vue-router';
// import LoginView from '@/views/LoginView.vue';

// const routes = [
//   { path: '/',     component: LoginView },   // トップでログイン
//   { path: '/login', component: LoginView },  // or /login
//   // ほかのルート…
// ];

// /* ==== ここを修正 ==== */
// const router = createRouter({
//   history: createWebHistory('/'),   // 直接 '/' と書く
//   routes,
// });

// export default router;
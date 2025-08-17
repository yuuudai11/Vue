import './assets/main.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import vuetify from "./vuetify";
const app = createApp(App)
app.use(createPinia())
app.use(router)   // src直下にフォルダを作成しindex.jsを作成。利用できるよう記述。at 20250531
app.use(vuetify)  // src直下にフォルダを作成しindex.jsを作成。利用できるよう記述。at 20250531
app.mount('#app')

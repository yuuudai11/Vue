<template>
  <v-app>
    <!-- 上部バー -->
    <!-- <v-app-bar app density="comfortable">
      <v-app-bar-nav-icon class="d-md-none" @click="drawer = !drawer" />
      <v-toolbar-title>メイン</v-toolbar-title>
      <v-spacer />
      <v-btn variant="text" @click="onLogout">ログアウト</v-btn>
    </v-app-bar> -->

    <!-- サイドバー：PCは開きっぱなし、モバイルはトグル -->
    <!-- <v-navigation-drawer app v-model="drawer" :temporary="isMobile">
      <v-list density="compact">
        <v-list-subheader>操作</v-list-subheader>
        <v-list-item title="新規登録" prepend-icon="mdi-plus" />
        <v-list-item title="検索"     prepend-icon="mdi-magnify" />
        <v-list-item title="更新"     prepend-icon="mdi-pencil" disabled />
        <v-list-item title="削除"     prepend-icon="mdi-delete" disabled />
      </v-list>
    </v-navigation-drawer> -->

    <!-- メイン領域：当面は既存 MenuView を表示。後で TabHost に差し替える -->
    <!-- <v-main>
      <v-container class="py-6" fluid>
        <MenuView />
      </v-container>
    </v-main> -->
    <v-main>
      <v-container class="py-6" fluid>
        <h2 class="text-h5">
          /app（MainLayout）に到達。ここにタブを入れていきます。
        </h2>
      </v-container>
    </v-main>
  </v-app>
</template>

<!-- <script setup>
//import { ref, computed } from 'vue'
//import { useDisplay } from 'vuetify'
//import { useRouter } from 'vue-router'
// import MenuView from '@/views/MenuView.vue'

// const router = useRouter()
// const { mdAndDown } = useDisplay()
// const isMobile = computed(() => mdAndDown.value)
// const drawer = ref(!isMobile.value)   // PC: 開いたまま / モバイル: 閉じた状態

// function onLogout () {
  // いまは画面遷移だけ。後で Pinia の auth に置き換える
//   router.push({ name: 'login' })
// }
 </script> -->

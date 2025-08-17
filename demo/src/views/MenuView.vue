<template>
  <div>
    <v-card class="d-flex flex-column my-6 mx-auto" width="400" height="600" color="#fff">
      <v-form ref="form" v-model="valid">
        <v-card-title class="d-flex justify-center pa-0 mt-6"
          >メニュー</v-card-title
        >
        <br>
        <br>
        <br>
        <br>
        <br>
        <v-card-text class="d-flex justify-center flex-column">
          <div class="text-center">
            <v-btn class="primary" @click="toUserInfo">ユーザ情報管理</v-btn>
          </div>
          <br>
          <div class="text-center">
            <v-btn class="primary" @click="toUserInfo">勤怠管理</v-btn>
          </div>
        </v-card-text>
        <br>
        <br>
        <br>
        <br>
        <br>
      </v-form>
      <div class="text-center">
        <v-btn class="primary" @click="tologin">ログアウト</v-btn>
      </div>
    </v-card>
  </div>
</template>

<script>
  export default {
    data() {
      return {
      };
    },
    methods: {
      tologin() {
        this.$router.push({ path: '/' });
      },
      toUserInfo() {
        this.$router.push({ path: 'search' , query: { mode: 'init' } });
      }
    },
  };
</script>
<style>
  .text-center {
    text-align: center;
  }
</style>

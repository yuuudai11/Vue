<template>
  <div>
    <v-card class="d-flex flex-column my-6 mx-auto" width="400" height="600" color="#fff">
      <v-card-title class="d-flex justify-center pa-0 mt-6"
        >ログイン</v-card-title
      >
      <v-message v-if="!isValid" color="red" class="text-center">
        {{ msg }}
      </v-message>
      <v-card-text class="d-flex justify-center flex-column">
        <v-form class="mx-9" ref="form" v-model="valid">
          <v-text-field
            placeholder="メールアドレス"
            outlined
            dense
            :rules="mailRules"
            v-model="mailAddress"
          ></v-text-field>
          <v-text-field
            placeholder="パスワード"
            outlined
            dense
            :rules="pwRules"
            v-model="password"
          ></v-text-field>
          <div class="text-center">
            <v-btn class="primary" :disabled="!valid" @click="login">ログイン</v-btn>
          </div>
        </v-form>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      msg: '',
      isValid: true,
      valid: false,
      mailRules: [
        (v) => !!v || "mail is required",
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',],
      pwRules: [(v) => !!v || "password is required"],
    };
  },
  methods: {
    validate() {
      this.$refs.form.validate();
    },
    async login() {
      const data = { userId: this.mailAddress, password: this.password };
      const url = 'http://localhost:8080/demo/login/login';
      axios.post(url,data)
      .then((response) => {
        if (response.data.httpStatus == '200' && response.data.userInfoList != null) {
          // ログイン成功時
          var successMessage = response.data.successMessage;
          console.log(successMessage);
          this.$router.push({ path: 'menu' , query: { mode: 'init' } });
        } else {
          // ログイン失敗時
          var errMessage = response.data.errMessage;
          this.isValid = false;
          this.msg = errMessage;
          return;
        }
      })
      .catch((error) => {
        console.error('Error:', error);
        return;
      });
    },
  },
};
</script>
<style>
.text-center {
  text-align: center;
}
</style>

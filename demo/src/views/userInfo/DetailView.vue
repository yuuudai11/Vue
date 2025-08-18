<template>
  <div>
    <v-card class="d-flex flex-column my-6 mx-auto" width="400" height="600" color="#fff">
      <v-form ref="form" v-model="valid">
        <v-card-title class="d-flex justify-center pa-0 mt-6 mb-3"
          >ユーザー情報 {{ dipayTitle }}</v-card-title
        >
        <v-card-text class="d-flex justify-center flex-column">
          <div class="mx-9">
            <v-text-field
              label="ﾕｰｻﾞID"
              placeholder="8文字以内"
              outlined
              dense
              :rules="idRules"
              v-model="id"
            ></v-text-field>
            <v-text-field
              label="名前"
              placeholder="15文字以内"
              outlined
              dense
              :rules="nameRules"
              v-model="name"
            ></v-text-field>
            <v-text-field
              label="ﾒｰﾙｱﾄﾞﾚｽ"
              placeholder="mail@example.com"
              outlined
              dense
              :rules="mailRules"
              v-model="mail"
            ></v-text-field>
            <v-text-field
              label="ﾊﾟｽﾜｰﾄﾞ"
              placeholder="8文字以上の半角英数記号"
              outlined
              dense
              :rules="pwRules"
              v-model="password"
            ></v-text-field>
          </div>
          <div class="text-center">
            <v-btn class="primary" :disabled="!valid" @click="toDetail">{{ buttonTitle }}</v-btn>
          </div>
        </v-card-text>
      </v-form>
      <div class="text-center">
        <v-btn class="primary" @click="tologin">ログアウト</v-btn>&nbsp;
        <v-btn class="primary" @click="toSearch">戻る</v-btn>
      </div>
    </v-card>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      valid: false,
      idRules: [
        (v) => !!v || "ﾕｰｻﾞIDは必須です。",
        (v) => (v && v.length <= 8) || "最大8文字です。",
      ],
      nameRules: [
        (v) => !!v || "ﾕｰｻﾞ名称は必須です。",
        (v) => (v && v.length <= 15) || "最大15文字です。",
      ],
      mailRules: [
        (v) => !!v || "ﾒｰﾙｱﾄﾞﾚｽは必須です。",
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',],
      pwRules: [
        (v) => !!v || "ﾊﾟｽﾜｰﾄﾞは必須です。",
        (v) => (v && v.length <= 4) || "最大4文字です。",
      ],
      // 各項目の初期化
      id: '',
      name: '',
      mail: '',
      password: ''
    };
  },
  mounted() {
    if (this.$route.query.mode == 'regist') {
      this.id = '99999999';
    } else {
      const data = { mode: this.$route.query.mode , id: this.$route.query.id };
      const url = 'http://localhost:8080/demo/detail/init';
      axios.post(url,data)
      .then(response => {
          this.id = response.data.userInfo.id;
          this.name = response.data.userInfo.name;
          this.mail = response.data.userInfo.mail;
          this.password = response.data.userInfo.password
      })
      .catch(error => {
        console.error('APIエラー:', error);
      });
    }
  },
  methods: {
    validate() {
      this.$refs.form.validate();
    },
    toDetail() {
      // 新規／更新／削除処理
      const data = {
          mode: this.$route.query.mode ,
          id: this.$route.query.id ,
          name: this.name,
          mail: this.mail,
          password: this.password
      };
      const url = 'http://localhost:8080/demo/detail/' + this.$route.query.mode;
      axios.post(url,data)
      .then(response => {
          this.id = response.data.userInfo.id;
          this.name = response.data.userInfo.name;
          this.mail = response.data.userInfo.mail;
          this.password = response.data.userInfo.password
      })
      .catch(error => {
        console.error('APIエラー:', error);
      });
      this.$router.push({ path: 'search' , query: { mode: 'search' } });
    },
    tologin() {
      this.$router.push({ path: '/' });
    },
    toSearch() {
      this.$router.push({ path: 'search' , query: { mode: 'search' } });
    }
  },
  computed: {
    dipayTitle() {
      var dipayTitle = "";
      const mode = this.$route.query.mode;
      if (mode == 'regist') {
        dipayTitle = '新規登録';
      } else if (mode == 'update') {
        dipayTitle = '更新';
      } else {
        dipayTitle = '削除';
      }
      return dipayTitle;
    },
    buttonTitle() {
      var dipayTitle = "";
      const mode = this.$route.query.mode;
      if (mode == 'regist') {
        dipayTitle = '新規登録';
      } else if (mode == 'update') {
        dipayTitle = '更新';
      } else {
        dipayTitle = '削除';
      }
      return dipayTitle;
    }
  }
};
</script>

<template>
  <div>
    <v-card class="d-flex flex-column my-6 mx-auto" width="400" height="600" color="#fff">
      <v-form ref="form" v-model="valid">
          <v-card-title class="d-flex justify-center pa-0 mt-6 mb-3">ユーザー情報 検索</v-card-title>
          <v-container style="width: 400px">
            <v-row>
              <v-col>
                <div class="text-center">
                  <v-btn class="primary" @click="toSearch">検索</v-btn>&nbsp;
                  <v-btn class="primary" @click="toDetailRegist">新規</v-btn>&nbsp;
                  <v-btn class="primary" :disabled="!(this.selected.length > 0)" @click="toDetailUpdate">更新</v-btn>&nbsp;
                  <v-btn class="primary" :disabled="!(this.selected.length > 0)" @click="toDetailDelete">削除</v-btn>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field
                  v-model="search"
                  :loading="loading"
                  append-inner-icon="mdi-magnify"
                  density="compact"
                  label="検索結果のフィルター文字列"
                  variant="solo"
                  hide-details
                  single-line
                  @click:append-inner="onClick"
                ></v-text-field>
                <v-data-table
                  :headers="headers"
                  :items="items"
                  :search="search"
                  :item-value="item => `${item.id},${item.name}`"
                  items-per-page="3"
                  :items-per-page-options="[2, 3, 4]"
                  select-strategy="single"
                  show-select
                  v-model="selected"
                ></v-data-table>
              </v-col>
            </v-row>
          </v-container>
      </v-form>
      <!--
      <div>
        <h3>選択された行:</h3>
        <pre>{{ selected }}</pre>
      </div>
      -->
      <div class="text-center">
        <v-btn class="primary" @click="toMenu">メニュー</v-btn>&nbsp;
        <v-btn class="primary" @click="tologin">ログアウト</v-btn>
      </div>
    </v-card>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data: () => (
    {
      search: "",
      headers: [
        { title: "ﾕｰｻﾞID", key: "id" },
        { title: "名前", key: "name" },
      ],
      items: [
      ],
      selected: [
      ],
    }
  ),
  mounted() {
    if (this.$route.query.mode == 'search') {
      const data = {};
      const url = 'http://localhost:8080/demo/search/search';
      axios.post(url,data)
      .then((response) => {
        if (response.data.httpStatus == '200' && response.data.userInfoList != null) {
          // データ取得時
          this.items = response.data.userInfoList;
        }
      })
      .catch((error) => {
        console.error('Error:', error);
        return;
      });
    }
  },
  methods : {
    tologin() {
      this.$router.push({ path: '/' });
    },
    toMenu() {
      this.$router.push({ path: 'menu' });
    },
    toSearch() {
      const data = {};
      const url = 'http://localhost:8080/demo/search/search';
      axios.post(url,data)
      .then((response) => {
        if (response.data.httpStatus == '200' && response.data.userInfoList != null) {
          // データ取得時
          this.items = response.data.userInfoList;
        }
      })
      .catch((error) => {
        console.error('Error:', error);
        return;
      });
    },
    toDetailRegist() {
      this.$router.push({ path: 'detail' , query: { mode: 'regist' , id: ''} });
    },
    toDetailUpdate() {
      if (this.selected.length > 1) {
        alert('選択は１件のみにしてください！');
        return;
      } else {
        const row = this.selected;
        const rowAry = row.toString().split(",");
        const selectedId = rowAry[0];
        this.$router.push({ path: 'detail' , query: { mode: 'update' , id: selectedId} });
      }
    },
    toDetailDelete() {
      if (this.selected.length > 1) {
        alert('選択は１件のみにしてください！');
        return;
      } else {
        const row = this.selected;
        const rowAry = row.toString().split(",");
        const selectedId = rowAry[0];
        this.$router.push({ path: 'detail' , query: { mode: 'delete' , id: selectedId} });
      }
    }
  }
};
</script>

const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,

  pluginOptions: {
    vuetify: {
      // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
    },
  },
  devServer: {
    port: 8080, // ← フロントは 8080
    proxy: {
      "/demo": {
        target: "http://localhost:8081",
        changeOrigin: true,
      },
    },
  },
});

'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.
const path = require('path');
const Timestamp = new Date().getTime();
//打包输出路径
let outputDir = "../docker/app";
module.exports = {
  dev: {
    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
      "/": {
        target: 'https://1249.67888288.co',
        changeOrigin: true,
        secure: false, //配置关闭证书签名验证
      }
    },

    // Various Dev Server settings
    //host: 'localhost', // can be overwritten by process.env.HOST
    // 局域网和localhost同时可以访问
     host: '127.0.0.1', // can be overwritten by process.env.HOST
    port: 8080, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-


    /**
     *https://www.yhgj1688.com Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    cssSourceMap: true
  },
  configureWebpack: { // webpack 配置
    output: { // 输出重构  打包编译后的 文件名称  【模块名称.版本号.时间戳】
      filename: `js/[name].${Timestamp}.js`,
      chunkFilename: `js/[name].${Timestamp}.js`
    },
  },
  build: {
    // Template for index.html
    index: path.resolve(__dirname, outputDir + '/index.html'),

    // Paths
    // assetsRoot: path.resolve(__dirname, '../dist'),
    assetsRoot: path.resolve(__dirname, outputDir),
    assetsSubDirectory: './',
    assetsPublicPath: './',

    /**
     * Source Maps
     */

    productionSourceMap: true,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  }
}

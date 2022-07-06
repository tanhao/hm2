'use strict';
const path = require('path');
const resolve = (dir) => path.resolve(__dirname, dir);
// 是否为生产环境
const isProduction = process.env.NODE_ENV !== 'development';
// gzip压缩
const Timestamp = new Date().getTime();
console.log(process.env.outputDir)
module.exports = {
    publicPath: '', // 基本路径
    outputDir: process.env.outputDir || 'dist', // 输出文件目录
    productionSourceMap: false, // 关闭生产环境的 source map
    devServer: {
        overlay: {
            warnings: false,
            errors: false,
        },
        open: true,
        // host: 'localhost',
        host: '0.0.0.0',
        port: 10010,
        https: false,
        hotOnly: true,
        proxy: {
            '/': {
                secure: false, //配置关闭证书签名验证
                //target: 'http://result.678kai.co', //测试环境
                target: 'http://114.112.254.19:23330', //测试环境
            },
        },
    },
    css: {
        loaderOptions: { // 向 CSS 相关的 loader 传递选项
            less: {
                javascriptEnabled: true
            }
        },
        extract:{
            filename: `css/[name].${Timestamp}.css`,
            chunkFilename: `css/[name].${Timestamp}.css`
        }
    },
    configureWebpack: { // webpack 配置
        output: { // 输出重构  打包编译后的 文件名称  【模块名称.版本号.js】
            filename: `js/[name].${Timestamp}.js`,
            chunkFilename: `js/[name].${Timestamp}.js`
        },
    },
    chainWebpack: config => {
        config.resolve.alias
            .set('@', resolve('src')) // key,value自行定义，比如.set('@@', resolve('src/components'))
            .set('_c', resolve('src/components'))
    },
};

'use strict'
let argv = process.argv.slice(2);
let HOST = argv[0] || 'prod';
let active = argv[1] || 'h5';

//如果是app打包需要固定請求地址
let baseUrl = active==='h5'?'/':'https://2252.67888288.co/';
//打包输出路径
let outputDir = "../docker/app";

console.log("loading prod init", HOST, active, baseUrl, outputDir)
module.exports = {
  NODE_ENV: '"production"',
  HOST: '"' + HOST + '"',
  baseUrl: '"' + baseUrl + '"',
  active: '"' + active + '"',
  outputDir: '"' + outputDir + '"',
  region: '"' + HOST + '"',
}

'use strict'
let argv = process.argv.slice(2);
let HOST = argv[0] || 'prod';
let baseUrl = "/";
//打包输出路径
let outputDir = "../docker/app";
console.log("loading prod init", HOST, baseUrl, outputDir)
module.exports = {
  NODE_ENV: '"production"',
  HOST: '"' + HOST + '"',
  baseUrl: '"' + baseUrl + '"',
  outputDir: '"' + outputDir + '"',
  region: '"' + HOST + '"',
}

'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

//开发环境数据，覆盖编译环境参数
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  HOST: '"dev"',
  baseUrl: '"/"',
  active: '"h5"',
  outputDir: '"dist"',
  region: '"test"',
})

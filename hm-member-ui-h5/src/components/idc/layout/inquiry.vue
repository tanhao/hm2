<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-bdVaJa jaFIbq otherpage">
          <my-header top="true" title="变更密码"></my-header>
          <div class="ui-content jqm_content">
            <div class="div_bg" style="padding:5px">
              交易日期：
              <!--这里你自己显示你自己的日期控件-->
              <div class="ui-block-a">
                 <!--这里是开始的日期-->
              </div>

              <div class="ui-block-b">
                <!--这里是结束的日期-->
              </div>
              <input type="button" value="查询" id="cha_flow" data-role="none" class="btn_gong" style="margin-top:10px;">
            </div>

            <div class="jqm_xd_table">
              <table border="0" cellspacing="0" cellpadding="0">
                <tbody>
                <tr>
                  <th>时间</th>
                  <th width="35">类型</th>
                  <th>金额</th>
                  <th>余额</th>
                  <th>备注</th>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <notice></notice>
    </div>
    <left-menu></left-menu>
  </div>
</template>






<script>
  import {mapGetters, mapActions} from 'vuex'
  import MyHeader from '@/components/idc/layout/header'
  import LeftMenu from '@/components/idc/layout/leftmenu'
  import notice from '@/components/notice'
  import { formatDate } from '@/components/comm/date.js'
  import Bet from '@/axios/api-bet.js'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'
  export default {
    components: {
      MyHeader,
      LeftMenu,
      notice,
    },
    data() {
      return {

      }
    },
    computed: {
      ...mapGetters(['gameMenu','showMenu','gameId']),
    },
    mounted(){

    },
    methods:{

    },
    filters: {

    }
  }
</script>

<style scoped>
  table {
    width: 100%;
    border-collapse: collapse;
  }
  .otherpage {
    background: #fff !important;
    height: calc(100% - 4px) !important;
  }
  .div_bg{
    font-size: 1.15em;
    line-height: 1.3;
  }
  .jqm_content {
    padding: 0px;
  }

  .ui-content {
    border-width: 0;
    overflow: visible;
    overflow-x: hidden;
  }
  .ui-grid-a > .ui-block-a, .ui-grid-a > .ui-block-b {
    width: 50%;
  }
  .ui-block-a {
    clear: left;
  }
  .ui-block-a, .ui-block-b {
    margin: 0;
    padding: 0;
    border: 0;
    float: left;
    min-height: 1px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
  }
  .ui-input-text input {
    font-size: 1em !important;
    color: inherit;
    text-shadow: inherit;
    margin: 0;
    min-height: 2.2em;
    text-align: left;
    border: 0;
    background: transparent none;
    -webkit-appearance: none;
    -webkit-border-radius: inherit;
    border-radius: inherit;
  }
  .ui-input-text input, textarea.ui-input-text {
    padding: .4em;
    line-height: 1.4em;
    display: block;
    width: 100%;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    outline: 0;
  }
  .btn_gong {
    background-color: #C82834;
    height: 37px;
    width: 100%;
    color: #fff;
    border: none;
    border-radius: 5px !important;
    box-shadow: 4px 4px 3px #cccccc;
    font-size: 16px;
    cursor: pointer;
  }
  .jqm_xd_table {
    width: 100%;
  }

  .jqm_xd_table tr td {
    text-align: center;
    border: 1px solid #EFC0A7;
    height: 30px;
    line-height: 18px;
    font-size: 12px;
  }

  .jqm_xd_table tr th {
    border: 1px solid #EFC0A7;
    text-align: center;
    background-image: url("../../images/tb_bg.jpg");
    font-size: 12px !important;
    color: #4A1A04;
    font-weight: bold;
    height: 30px;
    line-height: 30px;
  }

</style>


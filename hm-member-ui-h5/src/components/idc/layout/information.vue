<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-bdVaJa jaFIbq otherpage">
          <my-header top="false"  userInfomation="true" @lotteryLimit="lotteryLimit" title="信用资料"></my-header>
          <div class="ui-content">
            <div class="jqm_x_zl">
              <ul>
                <li class="jqm_right_bu">会员帐号：
                  <span id="con_memberno">{{member.username}}({{market}}盘)</span>
                </li>
                <li class="jqm_right_bu" >
                  <span id="con_tishi">信用额度：</span>
                  <span id="con_creditquota" >{{member.credit | moneyFmt}}</span>
                </li>
                <li class="jqm_right_bottom_bu" >可用金额：
                  <span id="con_allowcreditquota" >{{member.balance | moneyFmt}}</span>
                </li>
              </ul>
            </div>
            <div class="jqm_x_cp">
              <table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tbody class="biaoti_tbody">
                  <tr>
                    <th colspan="4" class="gtitle_td">{{$t(limitLotteryKey)}}</th>
                  </tr>
                  <tr>
                    <th>交易类型</th>
                    <th>{{market}}盘退水</th>
                    <th>最高单注限额</th>
                    <th>单期限额</th>
                  </tr>
                  <tr style="display:none">
                    <td>禁止频繁请求数据,下单机器人开发商请直接联系本网,我们将协助提供安全的连接方案</td>
                  </tr>

                  <template v-for="(item,index) in orderList">
                    <tr height="22" align="center">
                      <td align="center">{{$t(item.kindKey)}}
                        <span id="wid0" style="display:none;">205</span>
                      </td>
                      <td align="center">{{item.regress}}%</td>
                      <td align="right" style="padding-right:5px;">{{item.maxBetLimit | moneyFmt}}</td>
                      <td align="right" style="padding-right:5px;">{{item.maxPeriodLimit | moneyFmt}}</td>
                    </tr>
                  </template>


                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <notice></notice>
    </div>
  </div>
</template>


<script>
  import {mapGetters, mapActions} from 'vuex'
  import MyHeader from '@/components/idc/layout/header'
  import notice from '@/components/notice'
  import Utils from '@/components/comm/Utils'
  import to from "await-to-js";
  export default {
    components: {
      MyHeader,
      notice,
    },
    data() {
      return {
        orderList:[],
        limitLotteryKey:'bjpk10'
      }
    },
    computed: {
      ...mapGetters(['gameMenu','balance','market','member','game','markets'])
    },
    filters: {
      moneyFmt(val){
        if(!val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      },

    },
    methods:{
      async lotteryLimit(data){
        let obj = this.gameMenu.find(val=>{
          if(val.title == data.lotteryKey){
            return val;
          }
        });
        this.limitLotteryKey=obj.title;
        let [err,res] = await to(this.$api.game.getLotteryLimit(obj.index));
        if(err || !res.success){
          return;
        }
        this.orderList = res.data;
      }
    },
    mounted(){
      if(this.game.lotteryId){
        this.limitLotteryKey=this.game.lotteryKey;
        this.lotteryLimit({'lotteryKey':this.game.lotteryKey});
      }else{
        this.lotteryLimit({'lotteryKey':'bjpk10'});
      }
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

  .ui-content {
    padding: 5px;
  }

  .ui-content {
    border-width: 0;
    overflow: auto;
    position: relative;
    height: calc(100% - 55px);
  }

  .jqm_x_zl {
    float: left;
    width: 100%;
  }

  .jqm_x_zl ul, .jqm_x_cp ul {
    list-style: none;
    margin: 0px;
    padding: 0px;
  }

  .jqm_x_zl ul li {
    border-top: 1px solid #EFC0A7;
    border-left: 1px solid #EFC0A7;
    height: 30px;
    line-height: 30px;
    font-size: 12px;
    padding-left: 5px;
  }

  .jqm_right_bu {
    border-right: 1px solid #EFC0A7;
  }

  .jqm_right_bottom_bu {
    border-right: 1px solid #EFC0A7;
    border-bottom: 1px solid #EFC0A7;
  }

  .jqm_x_cp {
    float: left;
    width: 100%;
    margin-top: 10px;
  }

  .jqm_x_cp tr td {
    border: 1px solid #EFC0A7;
    height: 30px;
    line-height: 30px;
    font-size: 12px;
    width: 25%;
  }

  .jqm_x_cp tr th {
    border: 1px solid #EFC0A7;
    text-align: center;
    background: linear-gradient(360deg, rgb(239, 192, 167) 0%, rgb(253, 248, 245) 100%);
    font-size: 12px !important;
    color: #4A1A04;
    font-weight: bold;
    height: 30px;
    line-height: 30px;
  }


</style>


<template>
  <div style="margin:0px 5px 0px 0px;padding-bottom: 5px">
    <div class="user_info_table">
      <table class="table user_info">
        <thead>
        <tr>
          <th colspan="2">会员资料</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <th>会员账号</th>
          <td>{{member.username}}</td>
        </tr>
        <tr>
          <th>会员名称</th>
          <td>{{member.nickName}}</td>
        </tr>
        <tr>
          <th>所属盘口</th>
          <td>{{market}}盘</td>
        </tr>
        <tr>
          <th>账户状态</th>
          <td>启用</td>
        </tr>
        <tr>
          <th>快开彩额度</th>
          <td>{{member.credit | moneyFmt}} (余额:{{balance | moneyFmt}})</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div>
      <div class="tab-wrapper mt">
        <a id="info" href="info?lottery=BJPK10" class="active">彩票类</a>

      </div>
    </div>
    <div class="info_body">
      <div class="game_class">
        <ul>
          <li><span>快开彩</span>
            <template v-for="(item,index) in gameMenu">
              <a style="cursor:pointer" :class="selectGameTitle==item.lotteryKey?'selected':''" @click="selectLotteryLimit(item.id,item.lotteryKey)">{{$t(item.lotteryKey)}}</a>
            </template>
          </li>
        </ul>

      </div>
      <table class="list table mt data_table">
        <thead>
        <tr>
          <th>玩法</th>
          <th>{{market}}盘退水</th>
          <th>单注最低</th>
          <th>单注最高</th>
          <th>单期最高</th>
        </tr>
        </thead>
        <tbody>
        <template v-for="(item,index) in orderList">
          <tr class="">
            <th>{{$t(item.kindKey)}}</th>
            <td class="cm">{{item.regress}}%</td>
            <td>{{item.minBetLimit}}</td>
            <td>{{item.maxBetLimit}}</td>
            <td>{{item.maxPeriodLimit}}</td>
          </tr>
        </template>

        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
  import {mapGetters,mapActions} from 'vuex'
  import Utils from '@/components/comm/Utils.js'
  import to from "await-to-js";
    export default {
        name: "userInfo",
      data(){
        return {
          orderList:[],
          params:{
            'lotteryId':101
          },
          selectGameTitle:'bjpk10'
        }
      },
      filters: {
        moneyFmt(val){
          if(!val){
            return '0.00';
          }
          return Utils.formatMoney(val, 2);
        },
      },
      computed: {
        ...mapGetters(['gameMenu','balance','market','member','gameId','game']),

      },
      methods:{
        async selectLotteryLimit(id,title){
          this.params.lotteryId = id;
          this.selectGameTitle = title;
          let [err,data] = await to(this.$api.Lottery.getLotteryLimit(this.params.lotteryId));
          if(data.success){
            this.orderList = data.data;
          }
        }
      },
      mounted(){
        if(this.game.lotteryId && this.game.lotteryKey){
          this.selectLotteryLimit(this.game.lotteryId,this.game.lotteryKey);
        }else{
          this.selectLotteryLimit(101,'bjpk10');
        }
      }
    }
</script>

<style scoped>

</style>

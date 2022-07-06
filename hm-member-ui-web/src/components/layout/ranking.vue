<template>
  <div>
    <table v-if="gameId != 401 && gameId!=402 && gameId!=403 && gameId!=404 && gameId!=405"
           id="changlong">
      <thead>
      <tr>
        <th colspan="2" class="table_side">两面长龙排行</th>
      </tr>
      </thead>
      <tbody>
      <template v-for="(item,index) in longDragonList">
        <tr v-if="gameId==101 || gameId ==102 || gameId ==103 || gameId ==104 || gameId ==105 || gameId ==106
      || gameId ==107 || gameId ==108 || gameId ==109 || gameId ==110 || gameId ==111 || gameId ==112">
          <th>
            <a href="#">

              {{$t(item.type)}} -
              {{$t(item.oddsKey.toUpperCase())}}


            </a>
          </th>
          <td>{{item.number}} 期</td>
        </tr>

        <tr v-if="gameId==201 || gameId ==202 || gameId ==203 || gameId ==204 || gameId ==205 || gameId ==206
|| gameId ==207 || gameId ==208 || gameId ==209 || gameId ==210 || gameId ==211">
          <th>
            <a href="#">

              {{$t(item.type)}} -
              {{$t(item.oddsKey.toUpperCase())}}

              <!--<template v-if="gameId==601">
                {{$t('gd11x5_'+item.type)}} -
                {{$t(item.oddsKey.toUpperCase())}}
              </template>-->
            </a>
          </th>
          <td>{{item.number}} 期</td>
        </tr>
        <tr v-if="gameId==301 || gameId ==302 || gameId ==303 || gameId ==304">
          <th>
            <a href="#">{{$t('gdkl10lz_'+item.type)}} - {{$t(item.oddsKey.toUpperCase())}}</a>
          </th>
          <td>{{item.number}} 期</td>
        </tr>

        <tr v-if="gameId==501 || gameId ==502">
          <th>
            <a href="#">{{$t(item.type)}} -
              {{$t(item.oddsKey.toUpperCase())}}
            </a>
          </th>
          <td>{{item.number}} 期</td>
        </tr>

        <tr v-if="gameId==601">

          <th>
            <a href="#">{{$t('gd11x5_'+item.type)}} -
              {{$t(item.oddsKey.toUpperCase())}}
            </a>
          </th>
          <td>{{item.number}} 期</td>
        </tr>

        <tr v-if="gameId==701">
          <th>
            <a href="#">{{$t('gxkl10lz_'+item.type)}} -
              {{$t(item.oddsKey.toUpperCase())}}
            </a>
          </th>
          <td>{{item.number}} 期</td>
        </tr>
      </template>

      </tbody>
    </table>
    <div id="drawTable" v-if="gameId == 401 || gameId==402 || gameId==403 || gameId==404 || gameId==405">
      <table :class="k3YxxCss" style="width: 168px">
        <thead>
        <tr>
          <th colspan="6" class="table_side">近期开奖结果</th>
        </tr>
        </thead>
        <tbody>
        <template v-for="(item,index) in kjlistK3">
          <tr v-if="item.result!=null && item.result!='' " class="">
            <td class="period">{{item.gameNo.substring(item.gameNo.length-2,item.gameNo.length)}}期</td>
            <template v-for="(obj,i) in item.result">
              <td class="name">
                <span :class="'b'+obj">{{obj}}</span>
              </td>
            </template>
            <td class="other">{{item.special[0]}}</td>

            <td class="other" :style="item.special[1]=='OVER'?'color:red;':''">{{$t(item.special[1])}}</td>
          </tr>
        </template>

        </tbody>
      </table>
    </div>
    <!--会員智能投注-->
    <table v-if="intelligenceBet" id="hislist" class="mt10">
      <thead>
      <tr>
        <th colspan="3" class="table_side">会員智能投注</th>
      </tr>
      </thead>
      <tbody>
        <template v-for="(item,index) in planList">
          <tr class="zylist">
            <td @click="planInfoFun(item.planId)" >{{item.planName}}</td>
            <td width="15px"><span :class="item.planStatus?'rest green':'rest red'">●</span></td>
            <td width="15px" @click="delPlan(item.planId)"><span class="close">×</span></td>
          </tr>
        </template>
      <tr>
        <td colspan="3" @click="addPlanModel" class="hyzthov">
          <span>新增计划</span>
        </td>
      </tr>
      </tbody>
    </table>
    <!--弹出-->
    <div v-if="planModel" >
    <div class="ztn ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-draggable ui-resizable"
         style="position: absolute; height: auto; left:240px;width: 400px; margin: 0 auto; top: 20%; display: block; z-index: 101;">
      <div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix ui-draggable-handle"><span
        id="ui-id-1" class="ui-dialog-title">{{planAddOrUpdate}}</span>
        <button @click="planModel=false" type="button"
                class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close"
                role="button" title="Close">
          <span class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span
          class="ui-button-text">Close</span></button>
      </div>
      <div style="display: block; width: auto; min-height: 91px; max-height: none; height: auto;"
           class="ui-dialog-content ui-widget-content">
        <div style="width: auto;margin: 0 auto">
          <div style="padding-bottom: 10px;display: table;margin: 0 auto" v-if="planAddOrUpdate=='新增计划'">计划名称
            <input type="text" v-model="planParams.planName"></input>
          </div>
          <div class="segmented segmented-round">
            <button class="button button-round button-outline button-active">定位智投</button>
            <button class="button button-round button-outline">双面智投</button>
          </div>
          <!--定位智投-->
          <div>
            <table class="mt10" style="width: 100%">
              <thead>
              <tr>
                <th class="table_side">位置</th>
                <th class="table_side">选择号码</th>
                <th class="table_side">金额</th>
              </tr>
              </thead>
              <tbody>
                <template v-for="(item,index) in plays">
                  <tr v-if="(game.groupId==100 && item.playKey!='gyh') || (game.groupId==200 && item.playKey.indexOf('qiu')!=-1) || (game.groupId==300 && item.playKey.indexOf('qiu')!=-1)">
                    <th class="zylist">{{item.playName}}</th>
                    <th class="zylist"><input style="width: 180px" v-bind:disabled="planAddOrUpdate!='新增计划'?true:false" v-model="item.numbers" type="text" oninput="this.value = this.value.replace(/[^\d\,]/g,'')" placeholder="1-10,多于一个用逗號分隔,空白為沒选擇"></th>
                    <th class="zylist"><input style="width: 55px" v-bind:disabled="planAddOrUpdate!='新增计划'?true:false" v-model="item.amount" maxlength="5" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" type="text" placeholder="最大五位"></th>
                  </tr>
                </template>

              <tr>
                <th colspan="3" class="zylist">
                  <label>
                    <input name="betModel" v-model="planParams.betModel" type="radio" value="quota">
                    定额投注
                  </label>
                  <!--<label>
                    <input name="betModel" v-model="planParams.betModel" type="radio" value="fixed">
                    倍数投注
                  </label>-->
                </th>
              </tr>

              </tbody>
            </table>
          </div>
        </div>
        <div style="width: auto;margin: 0 auto;display: table;">
          <label>
            <input name="settingbet" v-model="planParams.planStatus" type="radio" value="1">
            启动
          </label>
          <label>
            <input name="settingbet" v-model="planParams.planStatus" type="radio" value="2">
            停用
          </label>
          <!--<label>
            <input name="settingbet" v-model="planParams.planStatus" type="radio" value="3">
            修改
          </label>-->
          <!--<label>
            <input name="settingbet" v-model="planParams.planStatus" type="radio" value="4">
            删除
          </label>-->
          <br>
          <br>
        </div>
        <div style="display: table;margin: 0 auto">
          <input @click="okPlanUpdate" type="button" class="button" value="确定">
        </div>
      </div>

    </div>
    <div class="ui-widget-overlay ui-front" style="z-index: 100;"></div>
    </div>
  </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'

  export default {
    name: "ranking",
    data() {
      return {
        planModel:false,
        planAddOrUpdate:'新增计划',
        planParams:{
          planStatus:1,
          planName:'',
          betModel:'quota',
          lotteryId:'',
          locations:[],
          gameNo:'',
        },
        planList:[],
        updatePlanStatus:true,
        updatePlanId:0,
        intelligenceBet:process.env.region=="test"?true:false
      }
    },
    computed: {
      ...mapGetters(['longDragonList','gameInfo','game', 'gameId', 'kjlistK3', 'k3YxxCss','plays']),
    },
    watch:{
      gameId(){
        if(this.intelligenceBet){
          this.$api.Bet.getBetPlanList(this.gameId).then(val=>{
            this.planList = val.data.dataList;
          })
        }

      }
    },
    methods: {
      ...mapActions(['setPromptInformation']),
      addPlanModel(){
        this.planModel = true;
        this.planAddOrUpdate = '新增计划';
        this.planParams={
          planStatus:1,
            planName:'',
            betModel:'quota',
            lotteryId:'',
            locations:[],
            gameNo:'',
        };
        for(let key in this.plays){
          this.$set(this.plays[key],"numbers","");
          this.$set(this.plays[key],"amount","");
        }
      },
      okPlanUpdate(){
        let self = this;
        self.planParams.locations = [];
        if(self.planAddOrUpdate=='新增计划'){
          if(!self.planParams.planName){
            self.setPromptInformation('请输入计划名！');
            return;
          }
          for(let key in this.plays){
            if((this.plays[key].numbers && !this.plays[key].amount) || (!this.plays[key].numbers && this.plays[key].amount)){
              self.setPromptInformation('请确保'+this.plays[key].playName+'号码与金额都设置正确!');
            }else{
              if(this.plays[key].numbers && this.plays[key].amount ){
                let flag = false;
                let arr = this.plays[key].numbers.split(',');
                if(new Set(arr).size !== arr.length){

                  self.setPromptInformation(this.plays[key].playName+'存在相同号码!');
                  return;
                }
                if(self.game.groupId==100){

                  arr.forEach(num=>{
                    if(num<1 || num>10){
                      flag = true;
                    }
                  })
                }else if(self.game.groupId==200){

                  arr.forEach(num=>{
                    if(num<0 || num>9){
                      flag = true;
                    }
                  })
                }else if(self.game.groupId==300){

                  arr.forEach(num=>{
                    if(num<1 || num>20){
                      flag = true;
                    }
                  })
                }
                if(flag){
                  self.setPromptInformation(this.plays[key].playName+'号码格式不正确!');
                  self.planParams.locations = [];
                  return;
                }
                let obj = {playId:key,numbers:this.plays[key].numbers,amount:this.plays[key].amount};
                self.planParams.locations.push(obj);




              }
            }
          }
          if(self.planParams.locations.length>0){
            self.planParams.gameNo = self.gameInfo.gameNo;
            self.planParams.lotteryId = self.gameId;
            this.$api.Bet.addBetPlan(self.planParams).then(res=>{
              if(res.success){
                setTimeout(()=>{
                  this.$api.Bet.getBetPlanList(this.gameId).then(val=>{
                    this.planList = val.data.dataList;
                  })
                },1000)
              }else{
                self.setPromptInformation('添加计划失败!'+res.code);
              }

            })
          }
        }else{
          if(self.planParams.planStatus!=self.updatePlanStatus){
            self.$api.Bet.updatePlan(self.updatePlanId).then(res=>{
              if(res.success){
                setTimeout(()=>{
                  this.$api.Bet.getBetPlanList(this.gameId).then(val=>{
                    this.planList = val.data.dataList;
                  })
                },1000)
              }else{
                self.setPromptInformation('添加计划失败!'+res.code);
              }

            })

          }
        }
        self.planModel = false;



      },
      planInfoFun(planId){
        for(let key in this.plays){
          this.$set(this.plays[key],"numbers","");
          this.$set(this.plays[key],"amount","");
        }
        console.log('??',this.plays);
        this.$api.Bet.getBetPlanInfo(planId).then(val=>{
          for(let key in this.plays){
            val.data.locations.forEach(obj=>{
              if(obj.playId==key){
                Object.assign(this.plays[key],obj);
              }
            })
          }
          this.planModel = true;
          this.planAddOrUpdate = val.data.planName;
          this.updatePlanId = val.data.planId;
          if(val.data.planStatus){
            this.planParams.planStatus = 1;
            this.updatePlanStatus = 1;
          }else{
            this.planParams.planStatus = 2;
            this.updatePlanStatus = 2;
          }

        })

      },
      delPlan(params){
        let self = this;
        self.$messageBox({$type:'confirm',message:'确认删除吗？',title:'提示',closeOnClickModal:false,showCancelButton:true}).then(action => {
          if (Object.is(action, 'confirm')) {
            this.$api.Bet.delPlan(params).then(val => {
              if (val.success) {
                self.$api.Bet.getBetPlanList(this.gameId).then(val=>{
                  self.planList = val.data.dataList;
                })
              }
            })
          }
        }).catch((err) => {
          if (Object.is(err, 'cancen')) {
            return;
          }
        }).finally(() => {

        });
      }
    },
    mounted(){
      if(this.intelligenceBet){
        setTimeout(()=>{

            this.$api.Bet.getBetPlanList(this.gameId).then(val=>{
              this.planList = val.data.dataList;
            })

        },1200)
      }
    }
  }
</script>

<style scoped>

</style>

<template>
  <div class="kjlist">
    <select id="kjlist" name="lottery" v-model="params.lotteryId" @change="select">
      <template v-for="item in gameMenu">
        <option  :value="item.id" :key="item.id">{{$t(item.lotteryKey)}}</option>
      </template>


    </select>
    日期：
    <!--<input  @click="openPicker()" :value="showDate" readonly>
    <mt-datetime-picker
      ref="picker"
      type="date"
      v-model="currentDate"
      @confirm="handleChange">
    </mt-datetime-picker>-->
    <input type="date" ref="selectDate" @change="getLotteryResult" :max="currentDate.toString()"></input>



    <!--结束-->
    <div id="drawTable">
      <table :class="resultCss" v-if="params.lotteryId==101 || params.lotteryId==102 ||
                    params.lotteryId==104 || params.lotteryId==103 ||
                    params.lotteryId==105 || params.lotteryId==106 || params.lotteryId  == 107||
                    params.lotteryId==108 || params.lotteryId == 109 || params.lotteryId==110 ||
                     params.lotteryId==111 || params.lotteryId==112">
        <thead>
        <tr>
          <th>期数</th>
          <th>开奖时间</th>
          <th colspan="10">开出号码</th>
          <th colspan="3" class="strong">冠亚军和</th>
          <th colspan="5" class="strong">1～5 龙虎</th>
        </tr>
        </thead>
        <tbody>
        <template v-for="(item,index) in results">
          <tr v-if="item.result!=null && item.result!=''" class="">
            <td class="period">{{item.gameNo}}</td>
            <td class="drawTime">{{item.actionTime*1000 | formatDate}}</td>
            <template v-for="(obj,i) in item.result">
              <td class="name ballname"><span :class="'b'+obj">{{obj}}</span></td>
            </template>
            <td >{{item.special[0]}}</td>

            <td :class="item.special[1]=='OVER'?'other GDX_D':''">{{$t(item.special[1])}}</td>

            <td :class="item.special[2]=='EVEN'?'other GDX_D':''">{{$t(item.special[2])}}</td>

            <td :class="item.special[3]=='DRAGON'?'other GDX_D':''">{{$t(item.special[3])}}</td>

            <td :class="item.special[4]=='DRAGON'?'other GDX_D':''">{{$t(item.special[4])}}</td>

            <td :class="item.special[5]=='DRAGON'?'other GDX_D':''">{{$t(item.special[5])}}</td>

            <td :class="item.special[6]=='DRAGON'?'other GDX_D':''">{{$t(item.special[6])}}</td>

            <td :class="item.special[7]=='DRAGON'?'other GDX_D':''">{{$t(item.special[7])}}</td>
          </tr>
        </template>
        </tbody>
      </table>

      <table :class="resultCss" v-else-if="params.lotteryId==201 || params.lotteryId==203 || params.lotteryId==202
                  || params.lotteryId==204 || params.lotteryId==205 || params.lotteryId==206 || params.lotteryId==207
                  || params.lotteryId==208 || params.lotteryId==209 || params.lotteryId==210 || params.lotteryId==211">
        <thead>
        <tr>
          <th>期数</th>
          <th>开奖时间</th>
          <th colspan="5">开出号码</th>
          <th colspan="3" class="strong">总和</th>
          <th  class="strong">龙虎</th>
          <th  class="strong">前三</th>
          <th  class="strong">中三</th>
          <th  class="strong">后三</th>
        </tr>
        </thead>
        <tbody>
        <template v-for="(item,index) in results">
          <tr v-if="item.result!=null && item.result!=''" class="">
            <td class="period">{{item.gameNo}}</td>
            <td class="drawTime">{{item.actionTime*1000 | formatDate}}</td>
            <template v-for="(obj,i) in item.result">

              <td class="name ballname"><span :class="'b'+obj">{{obj}}</span></td>
            </template>
            <td >{{item.special[0]}}</td>

            <td :class="item.special[1]=='OVER'?'other GDX_D':''">{{$t(item.special[1])}}</td>

            <td :class="item.special[2]=='EVEN'?'other GDX_D':''">{{$t(item.special[2])}}</td>

            <td :class="item.special[3]=='DRAGON'?'other GDX_D':''">{{$t(item.special[3])}}</td>

            <td >{{$t(item.special[4])}}</td>

            <td >{{$t(item.special[5])}}</td>

            <td >{{$t(item.special[6])}}</td>

          </tr>
        </template>
        </tbody>
      </table>

      <table :class="resultCss" v-else-if="params.lotteryId==301 || params.lotteryId==303 || params.lotteryId==302 || params.lotteryId==304">
        <thead>
        <tr>
          <th>期数</th>
          <th>开奖时间</th>
          <th colspan="8">开出号码</th>
          <th colspan="4" class="strong">总和</th>
          <th colspan="4" class="strong">1~4龙虎</th>
        </tr>
        </thead>
        <tbody>
        <template v-for="(item,index) in results">
          <tr v-if="item.result!=null && item.result!=''" class="">
            <td class="period">{{item.gameNo}}</td>
            <td class="drawTime">{{item.actionTime*1000 | formatDate}}</td>
            <template v-for="(obj,i) in item.result">

              <td class="name ballname"><span :class="'b'+obj">{{obj}}</span></td>
            </template>
            <td >{{item.special[0]}}</td>

            <td :class="item.special[1]=='OVER'?'other GDX_D':''">{{$t(item.special[1])}}</td>

            <td :class="item.special[2]=='EVEN'?'other GDX_D':''">{{$t(item.special[2])}}</td>

            <td :class="item.special[3]=='weiover'?'other GDX_D':''">{{$t(item.special[3])}}</td>

            <td :class="item.special[4]=='DRAGON'?'other GDX_D':''">{{$t(item.special[4])}}</td>

            <td :class="item.special[5]=='DRAGON'?'other GDX_D':''">{{$t(item.special[5])}}</td>

            <td :class="item.special[6]=='DRAGON'?'other GDX_D':''">{{$t(item.special[6])}}</td>

            <td :class="item.special[7]=='DRAGON'?'other GDX_D':''">{{$t(item.special[7])}}</td>
          </tr>
        </template>
        </tbody>
      </table>


      <table class="list table_ball table jsdd" v-else-if="params.lotteryId==401 || params.lotteryId==402 || params.lotteryId==403">
        <thead>
        <tr>
          <th>期数</th>
          <th>开奖时间</th>
          <th colspan="3">开出号码</th>
          <th class="strong">总和</th>
        </tr>
        </thead>
        <tbody>
        <template v-for="(item,index) in results">
          <tr v-if="item.result!=null && item.result!=''" class="">
            <td class="period">{{item.gameNo}}</td>
            <td class="drawTime">{{item.actionTime*1000 | formatDate}}</td>
            <template v-for="(obj,i) in item.result">
              <td class="name ballname"><span :class="'n_'+obj">{{obj}}</span></td>
            </template>
            <td class="period" style="width: 127px"><span :class="'n_'+item.special[0]" style="margin: 0 auto">{{item.special[0]}}</span></td>
          </tr>
        </template>
        </tbody>
      </table>
    </div>
    <!--公用分页样式,完成后放回最下面-->
    <div id="nav">
      <ul class="pagination margin">
        <li class="pageall">共<span>{{totalPage}}</span>页</li>
        <li @click="selectPage(params.page-1)"><a >❮</a></li>
        <template v-for="(item,index) in pageList">
          <li @click="selectPage(item)"><a :class="item==params.page?'active':''">{{item}}</a></li>
        </template>

        <li @click="selectPage(params.page+1)"><a >❯</a></li>
        <li>跳至
        <input style="width:40px;" ref="jumpPageValue" @keyup.enter="jumpPageFun($event.target.value)" @blur="jumpPageFun($event.target.value)" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');" type="number"/> 页</li>
      </ul>
    </div>
  </div>
</template>

<script>
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'
  import {mapGetters,mapActions} from 'vuex'
  import to from "await-to-js";
  export default {
    name: "kjlist",
    data() {
      return{
        results:[],
        params:{
          lotteryId:101,
          accountDay:Utils.formatDate(new Date(),'yyyy-MM-dd'),
          size:20,
          page:1
        },
        jumpPage:1,
        pageList:[],
        currentPage:1,
        totalPage:1,
        currentDate:Utils.formatDate(new Date(),'yyyy-MM-dd'),
        currentSelect:'',
        refreshTimer:null,
        resultCss:'list table_ball table pk10'
      }
    },
    filters: {
      formatDate(time) {
        var date = new Date(time);
        return Utils.formatDate(date, 'yyyy-MM-dd hh:mm:ss'); // 年月日 格式自己定义   'yyyy : MM : dd'  例 2018年12月5日的格式
      },
      resultFilter(val){
        let obj = '';
        if(val.substring(0,1)==='0'){
          obj = val.substring(1,2);
        }else{
          obj = val;
        }
        return obj;
      }
    },
    mounted(){
      let self = this;
      Indicator.open({text:'加载中...'});
      if(self.gameId){
        self.params.lotteryId = self.gameId;
      }
      let date = new Date();
      let hh = date.getHours();
      if (hh < 7) {
        date.setDate(date.getDate() - 1);
        let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
        let spliceArr = dateParam.split('-');
        this.currentDate = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy-MM-dd');
        this.params.accountDay = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy-MM-dd');
      }
      self.$refs.selectDate.value = self.currentDate;
      this.$nextTick(function(){
        self.getLotteryResult();
      })
      self.refreshTimer = setInterval(()=>{
        self.getLotteryResult();
      },10000);
    },
    beforeDestroy(){
        clearInterval(this.refreshTimer);
        this.refreshTimer= null;
    },
    destroyed() {

      clearInterval(this.refreshTimer);
      this.refreshTimer= null;
    },
    computed: {
      ...mapGetters(['gameMenu','showMenu', 'game', 'gameId']),
      showDate(){
        if(this.currentDate){
          return Utils.formatDate(this.currentDate,'yyyy-MM-dd');
        }
      },
    },
    methods:{
      jumpPageFun(pageValue){
        if(this.jumpPage==pageValue){
          return;
        }
        this.jumpPage = pageValue;
        this.jumpPage==0?this.jumpPage = 1:this.jumpPage>this.totalPage?this.jumpPage = this.totalPage:1;
        this.params.page = this.jumpPage;
        this.selectPage(this.jumpPage);
      },
      pageListDownOrUp(size){
        let self = this;
        self.pageList=[];
        self.currentPage=parseInt(size);
        if(this.$refs['jumpPageValue']){
          this.$refs['jumpPageValue'].value = self.currentPage;
        }
        for(let i = 1;i<=self.totalPage;i++){
          if(self.pageList.length==7){
            return;
          }
          if(i-self.currentPage>=-3 || i-self.currentPage>=3){
            self.pageList.push(i);
          }
          if(i==self.totalPage){
            break;
          }
        }
      },
      selectPage(page){
        if(page==0||page>this.totalPage){
          return;
        }
        this.params.page = page;
        this.getLotteryResult();
        this.pageListDownOrUp(page);


      },
      async getLotteryResult(){
        Indicator.open({text:'加载中...'});
        let self = this;
        self.params.accountDay = self.$refs.selectDate.value;
        let [err,data] = await to(this.$api.Lottery.lotteryResult(self.params));
        if(data.success){
          if(data.data.dataList){
            data.data.dataList.forEach((resultItem)=>{

              /*if(self.params.lotteryId==102 || self.params.lotteryId==201 || self.params.lotteryId==402||self.params.lotteryId==403
                ||self.params.lotteryId==404 || self.params.lotteryId==106|| self.params.lotteryId==107|| self.params.lotteryId==204
                || self.params.lotteryId==205 || self.params.lotteryId==208 || self.params.lotteryId==301|| self.params.lotteryId==303
                || self.params.lotteryId==601){
                resultItem.gameNo = resultItem.gameNo.substr(4);
              }*/
              if(typeof resultItem.result != 'undefined' && resultItem.result){
                let param = [];
                if(this.params.lotteryId==101 || this.params.lotteryId==102 ||
                  this.params.lotteryId ==104 || this.params.lotteryId==103 ||
                  this.params.lotteryId ==105 || this.params.lotteryId==106 ||
                  this.params.lotteryId ==107 || this.params.lotteryId==108 ||
                  this.params.lotteryId ==109 || this.params.lotteryId ==110||
                  this.params.lotteryId ==111 || this.params.lotteryId ==112){
                  resultItem.result.split(',').forEach((obj)=>{
                    if(obj.substring(0,1)==='0'){
                      param.push(obj.substring(1));
                    }else{
                      param.push(obj);
                    }
                  });

                  let resultArr = resultItem.result.split(',');
                  let arr = [];
                  let gyh = parseInt(resultArr[0])+parseInt(resultArr[1]);
                  let gyhOU = gyh>11?'OVER':'UNDER';
                  let gyhOE = (gyh%2===1)?'ODD':'EVEN';
                  let no1DT = parseInt(resultArr[0])>parseInt(resultArr[9])?'DRAGON':'TIGER';
                  let no2DT = parseInt(resultArr[1])>parseInt(resultArr[8])?'DRAGON':'TIGER';
                  let no3DT = parseInt(resultArr[2])>parseInt(resultArr[7])?'DRAGON':'TIGER';
                  let no4DT = parseInt(resultArr[3])>parseInt(resultArr[6])?'DRAGON':'TIGER';
                  let no5DT = parseInt(resultArr[4])>parseInt(resultArr[5])?'DRAGON':'TIGER';
                  arr = [gyh+'',gyhOU,gyhOE,no1DT,no2DT,no3DT,no4DT,no5DT];
                  resultItem.special = arr;
                }else if(this.params.lotteryId==201 || this.params.lotteryId == 203 || this.params.lotteryId==202||
                  this.params.lotteryId==204 || this.params.lotteryId == 205 || this.params.lotteryId==206||
                  this.params.lotteryId==207 || this.params.lotteryId == 208 || this.params.lotteryId==209||
                  this.params.lotteryId==210 || this.params.lotteryId ==211){
                  resultItem.result.split(',').forEach((obj)=>{
                    param.push(obj);
                  });
                  let resultArr = resultItem.result.split(',');
                  let arr = [];
                  let theSum = parseInt(resultArr[0])+parseInt(resultArr[1])+parseInt(resultArr[2])+parseInt(resultArr[3])+parseInt(resultArr[4]);
                  let theSumOU = theSum>22?'OVER':'UNDER';
                  let theSumOE = (theSum%2===1)?'ODD':'EVEN';
                  let theSumDT = parseInt(resultArr[0])>parseInt(resultArr[4])?'DRAGON':'TIGER';
                  if(parseInt(resultArr[0])==parseInt(resultArr[4])){
                    theSumDT = 'tie';
                  }
                  let top3 = '';
                  if(parseInt(resultArr[0]) == parseInt(resultArr[1]) && parseInt(resultArr[1]) == parseInt(resultArr[2])){
                    top3 = 'baozi';
                  }else if(parseInt(resultArr[0]) == parseInt(resultArr[1]) || parseInt(resultArr[1]) == parseInt(resultArr[2]) || parseInt(resultArr[0]) == parseInt(resultArr[2])){
                    top3 = 'duizi';
                  }else if(self.threeBrotherNumTakeAlgorithm(parseInt(resultArr[0]),parseInt(resultArr[1]),parseInt(resultArr[2]),'take')){
                    top3 = 'STRAIGHT';
                  }else if((parseInt(resultArr[0])-parseInt(resultArr[1])==1 || parseInt(resultArr[0]) - parseInt(resultArr[1])==-1 ||
                    parseInt(resultArr[0])-parseInt(resultArr[1])==9 || parseInt(resultArr[0]) - parseInt(resultArr[1])==-9) ||
                    (parseInt(resultArr[0])-parseInt(resultArr[2])==1 || parseInt(resultArr[0]) - parseInt(resultArr[2])==-1 ||
                      parseInt(resultArr[0])-parseInt(resultArr[2])==9 || parseInt(resultArr[0]) - parseInt(resultArr[2])==-9) ||
                    (parseInt(resultArr[1])-parseInt(resultArr[2])==1 || parseInt(resultArr[1]) - parseInt(resultArr[2])==-1 ||
                      parseInt(resultArr[1])-parseInt(resultArr[2])==9 || parseInt(resultArr[1]) - parseInt(resultArr[2])==-9)){
                    top3 = 'bansun';
                  }else{
                    top3 = 'zaliu';
                  }
                  let mid3 = '';
                  if(parseInt(resultArr[1]) == parseInt(resultArr[2]) && parseInt(resultArr[2]) == parseInt(resultArr[3])){
                    mid3 = 'baozi';
                  }else if(parseInt(resultArr[1]) == parseInt(resultArr[2]) || parseInt(resultArr[2]) == parseInt(resultArr[3])|| parseInt(resultArr[1]) == parseInt(resultArr[3])){
                    mid3 = 'duizi';
                  }else if(self.threeBrotherNumTakeAlgorithm(parseInt(resultArr[1]),parseInt(resultArr[2]),parseInt(resultArr[3]),'take')){
                    mid3 = 'STRAIGHT';
                  }else if((parseInt(resultArr[1])-parseInt(resultArr[2])==1 || parseInt(resultArr[1]) - parseInt(resultArr[2])==-1 ||
                    parseInt(resultArr[1])-parseInt(resultArr[2])==9 || parseInt(resultArr[1]) - parseInt(resultArr[2])==-9) ||
                    (parseInt(resultArr[1])-parseInt(resultArr[3])==1 || parseInt(resultArr[1]) - parseInt(resultArr[3])==-1 ||
                      parseInt(resultArr[1])-parseInt(resultArr[3])==9 || parseInt(resultArr[1]) - parseInt(resultArr[3])==-9) ||
                    (parseInt(resultArr[2])-parseInt(resultArr[3])==1 || parseInt(resultArr[2]) - parseInt(resultArr[3])==-1 ||
                      parseInt(resultArr[2])-parseInt(resultArr[3])==9 || parseInt(resultArr[2]) - parseInt(resultArr[3])==-9)){
                    mid3 = 'bansun';
                  }else{
                    mid3 = 'zaliu';
                  }
                  let end3 = '';
                  if(parseInt(resultArr[2]) == parseInt(resultArr[3]) && parseInt(resultArr[3]) == parseInt(resultArr[4])){
                    end3 = 'baozi';
                  }else if(parseInt(resultArr[2]) == parseInt(resultArr[3]) || parseInt(resultArr[3]) == parseInt(resultArr[4])|| parseInt(resultArr[2]) == parseInt(resultArr[4])){
                    end3 = 'duizi';
                  }else if(self.threeBrotherNumTakeAlgorithm(parseInt(resultArr[2]),parseInt(resultArr[3]),parseInt(resultArr[4]),'take')){
                    end3 = 'STRAIGHT';
                  }else if((parseInt(resultArr[2])-parseInt(resultArr[3])==1 || parseInt(resultArr[2]) - parseInt(resultArr[3])==-1 ||
                    parseInt(resultArr[2])-parseInt(resultArr[3])==9 || parseInt(resultArr[2]) - parseInt(resultArr[3])==-9) ||
                    (parseInt(resultArr[2])-parseInt(resultArr[4])==1 || parseInt(resultArr[2]) - parseInt(resultArr[4])==-1 ||
                      parseInt(resultArr[2])-parseInt(resultArr[4])==9 || parseInt(resultArr[2]) - parseInt(resultArr[4])==-9) ||
                    (parseInt(resultArr[3])-parseInt(resultArr[4])==1 || parseInt(resultArr[3]) - parseInt(resultArr[4])==-1 ||
                      parseInt(resultArr[3])-parseInt(resultArr[4])==9 || parseInt(resultArr[3]) - parseInt(resultArr[4])==-9)){
                    end3 = 'bansun';
                  }else{
                    end3 = 'zaliu';
                  }
                  arr = [theSum+'',theSumOU,theSumOE,theSumDT,top3,mid3,end3];
                  resultItem.special = arr;
                }else if(this.params.lotteryId==301 || this.params.lotteryId==302 || this.params.lotteryId==303 || this.params.lotteryId==304){
                  let he = 0;
                  resultItem.result.split(',').forEach((obj)=>{
                    he+=parseInt(obj);
                    if(obj.substring(0,1)==='0'){
                      param.push(obj.substring(1,2));
                    }else{
                      param.push(obj);
                    }
                  });
                  let resultArr = resultItem.result.split(',');
                  let arr = [];
                  let heOU = '';
                  if(he>=85 && he <=132){
                    heOU = 'OVER';
                  }else if(he >= 36 && he<=83){
                    heOU = 'EVEN';
                  }else if(he==84){
                    heOU = 'draw';
                  }

                  let heOE = (he%2===1)?'ODD':'EVEN';
                  let sumOU = parseInt(he.toString().substring(he.toString().length-1))>=5?'weiover':'weiunder';
                  let no1DT = parseInt(resultArr[0])>parseInt(resultArr[7])?'DRAGON':'TIGER';
                  let no2DT = parseInt(resultArr[1])>parseInt(resultArr[6])?'DRAGON':'TIGER';
                  let no3DT = parseInt(resultArr[2])>parseInt(resultArr[5])?'DRAGON':'TIGER';
                  let no4DT = parseInt(resultArr[3])>parseInt(resultArr[4])?'DRAGON':'TIGER';
                  arr = [he+'',heOU,heOE,sumOU,no1DT,no2DT,no3DT,no4DT];
                  resultItem.special = arr;

                }else if(this.params.lotteryId==401 || this.params.lotteryId==402 || this.params.lotteryId==403 ){
                  let he = 0;
                  resultItem.result.split(',').forEach((obj)=>{
                    he+=parseInt(obj);
                    if(obj.substring(0,1)==='0'){
                      param.push(obj.substring(1,2));
                    }else{
                      param.push(obj);
                    }
                  });
                  let arr = [he];
                  resultItem.special = arr;
                }

                resultItem.result = param;


              }
            });


            self.results = data.data.dataList;
          }
          self.totalPage = data.data.total%self.params.size==0?data.data.total/self.params.size:(parseInt(data.data.total/self.params.size)+1);
          self.pageList=[];
          self.pageListDownOrUp(self.currentPage);
        }


        if(this.params.lotteryId==101 || this.params.lotteryId==102 || this.params.lotteryId==103 ||
          this.params.lotteryId==104 || this.params.lotteryId==105
          || this.params.lotteryId==106 || this.params.lotteryId==108 || this.params.lotteryId==110
          || this.params.lotteryId==111 || this.params.lotteryId==112){
          this.resultCss =  'list table_ball table pk10';
        }else if(this.params.lotteryId==107 || this.params.lotteryId==109){
          this.resultCss =  'list table_ball table smn';
        }else if(this.params.lotteryId==301 || this.params.lotteryId==302 || this.params.lotteryId==304 || this.params.lotteryId == 601){
          this.resultCss = 'list table_ball table klsf';
        }else if(this.params.lotteryId==303){
          this.resultCss = 'list table_ball table xync';
        }else if(this.params.lotteryId==201 || this.params.lotteryId==203 || this.params.lotteryId==202
          || this.params.lotteryId==204 || this.params.lotteryId==205 || this.params.lotteryId==206 || this.params.lotteryId==207
          || this.params.lotteryId==208 || this.params.lotteryId==209 || this.params.lotteryId==210 || this.params.lotteryId==211){
          this.resultCss = 'list table_ball table ssc';
        }
      Indicator.close();
      },
      threeBrotherNumTakeAlgorithm(one, two, three, type) {
        let judgeOne = '';
        let judgeTwo = '';
        let judgethree = '';
        if (type == 'take') {
          if (one == two || two == three || one == three) {
            return false;
          }
          judgeOne = one - two == 1 || one - two == -1 || one - two == -9 || one - two == 9 ? true : false;
          judgeTwo = one - three == 1 || one - three == -1 || one - three == -9 || one - three == 9 ? true : false;
          judgethree = two - three == 1 || two - three == -1 || two - three == -9 || two - three == 9 ? true : false;
        } else {
          if (one == two || two == three || one == three) {
            return true;
          }
          judgeOne = one - two != 1 && one - two != -1 && one - two != -9 && one - two != 9 ? true : false;
          judgeTwo = one - three != 1 && one - three != -1 && one - three != -9 && one - three != 9 ? true : false;
          judgethree = two - three != 1 && two - three != -1 && two - three != -9 && two - three != 9 ? true : false;
        }
        if (judgeOne && judgeTwo) {
          return true;
        } else if (judgeOne && judgethree) {
          return true;
        } else if (judgeTwo && judgethree) {
          return true;
        } else {
          return false;
        }


      },
      select(){
        this.params.page = 1;
        this.currentPage = 1;
        this.getLotteryResult();

      },
      openPicker() {
        this.$refs.picker.open();
      },
      handleChange(value) {
        this.params.accountDay = Utils.formatDate(value,'yyyy/MM/dd');
        this.getLotteryResult();
      }
    }
  }
</script>

<style>
  .kjlist{
    margin: 0;
    padding: 4px;
    font-size: 12px;
  }
  #kjlist{
    width: 120px;
  }
  #date {
    width: 80px;
  }

  .table th, .table td {
    text-align: center;
    height: 28px;
    padding: 0;
  }
  input[type=date]::-webkit-inner-spin-button { visibility: hidden; }
  input[type="date"]::-webkit-clear-button{
    display:none;
  }
  a{
    cursor:pointer;
  }
  input::-webkit-outer-spin-button,
  input::-webkit-inner-spin-button {
    -webkit-appearance: none;
  }
  input[type="number"]{
    -moz-appearance: textfield;
  }

</style>

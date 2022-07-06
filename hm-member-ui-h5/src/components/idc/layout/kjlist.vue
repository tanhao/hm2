<template>
  <div id="idc">
  <div class="home">
    <div class="sc-bZQynM" style="height: 100%;">
      <my-header title="历史开奖结果" top="false"></my-header>
      <div class="sc-htoDjs biwSnT">
        <div><div class="tb_tit">
          <select id="gm_type" v-model="params.lotteryId" @change="select">
            <template v-for="item in gameMenu">
              <option  :value="item.index" :key="item.index">{{$t(item.title)}}</option>
            </template>
          </select>
        </div></div>
      </div>

      <div class="list">
        <ul>
          <li class="item-content item-input item-input-with-value">
            <div class="item-inner">
              <div class="item-title item-label">日期</div>
              <div class="item-input-wrap">
                <input  @click="openPicker()" :value="showDate" readonly>
                <mt-datetime-picker
                  ref="picker"
                  type="date"
                  v-model="currentDate"
                  @confirm="handleChange">
                </mt-datetime-picker>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div class="sc-gZMcBi uLYqo">
        <div class="sc-kAzzGY zxykL">
          <div class="sc-kGXeez ljfNBe">
            <div class="sc-chPdSV">期数</div>
            <div class="sc-kkGfuU fLJeyS">时间</div>
          </div>
          <div class="sc-kGXeez kbmxrg">
            <div class="sc-kgoBCf">号码</div>
          </div>
          <div class="ref" @click="getLotteryResult"><a class="refresh-icon">Refresh</a></div>
        </div>

        <div class="sc-jzJRlG bxxJAa">
          <!--开奖列表-->
          <!--循环结果-->
          <template v-for="(item,i) in results">
            <template v-if="item.result!=null && item.result!=''">
              <div class="sc-cSHVUG cfhhpx" v-if="item.lotteryId==101 || item.lotteryId==102 ||
                    item.lotteryId==104 || item.lotteryId==103 ||
                    item.lotteryId==105 || item.lotteryId==106 ||
                    item.lotteryId==107 || item.lotteryId==108 ||item.lotteryId==109 || item.lotteryId==110
                    || item.lotteryId ==111 || item.lotteryId ==112">
                <div class="sc-kGXeez ljfNBe">
                  <span class="title">{{item.gameNo}} </span>
                  <span class="title">{{item.actionTime*1000 | formatDate}} </span>
                </div>
                <div class="sc-kGXeez kbmxrg pk10" id="list">
                  <template v-for="(obj,index) in item.result">
                    <div :class="'n_'+obj">
                      <span >{{obj}}
                      </span>
                    </div>
                  </template>
                </div>
              </div>
              <div class="sc-cSHVUG cfhhpx" v-else-if="item.lotteryId==201 || item.lotteryId==203 || item.lotteryId==202
              || item.lotteryId==203 || item.lotteryId==204 || item.lotteryId==205
              || item.lotteryId==206 || item.lotteryId==207
              || item.lotteryId==208 || item.lotteryId==209 || item.lotteryId==210 || item.lotteryId==211">
                <div class="sc-kGXeez ljfNBe">
                  <span class="title">{{item.gameNo}} </span>
                  <span class="title">{{item.actionTime*1000 | formatDate}} </span>
                </div>
                <div class="sc-kGXeez kbmxrg ssc" id="list">
                  <template v-for="(obj,index) in item.result">
                    <div :class="'n_'+obj">
                      <span>{{obj}}
                      </span>
                    </div>
                  </template>
                </div>
              </div>
              <div class="sc-cSHVUG cfhhpx" v-else-if="item.lotteryId==301 || item.lotteryId==302 || item.lotteryId==303 || item.lotteryId==304">
                <div class="sc-kGXeez ljfNBe">
                  <span class="title">{{item.gameNo}} </span>
                  <span class="title">{{item.actionTime*1000 | formatDate}} </span>
                </div>
                <div :class="item.lotteryId==303?'sc-kGXeez kbmxrg xync':'sc-kGXeez kbmxrg klsf'" id="list">
                  <template v-for="(obj,index) in item.result">
                    <div :class="'n_'+obj">
                      <span>{{obj}}
                      </span>
                    </div>
                  </template>
                </div>
              </div>
              <div class="sc-cSHVUG cfhhpx"  v-else-if="item.lotteryId==401 || item.lotteryId==402 || item.lotteryId==403">
                <div class="sc-kGXeez ljfNBe">
                  <span class="title">{{item.gameNo}} </span>
                  <span class="title">{{item.actionTime*1000 | formatDate}} </span>
                </div>
                <div class="sc-kGXeez kbmxrg jsdd" id="list">
                  <template v-for="(obj,index) in item.result">

                    <div v-if="index<=2" :class="'n_'+obj">
                      <span >
                        {{obj}}

                      </span>
                    </div>
                    <span class="addico" v-if="index<2">+</span>
                    <div v-else-if="index==2">
                      <span class="addico" >=</span>
                    </div>
                    <div v-else-if="index==3" :class="'n_'+obj['he']">
                      <span  >{{obj['he']}}</span>
                    </div>

                  </template>

                </div>
              </div>

            </template>
          </template>
          <!--结束结果-->
          <div class="sc-cSHVUG cfhhpx">
            <div class="sc-kGXeez ljfNBe">
              <div class="Pagination">
                <div title="共">共<span>{{totalPage}}</span>页</div>
                <a class="tips" title="首页" @click="pageDownOrUp(1)">首页</a>
                <a class="tips" title="上一页" @click="pageDownOrUp(params.page-1)">上一页</a>
                <div><input class="form-control" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" @blur="inputPages" v-model="showPage"></div>
                <a class="tips" title="下一页" @click="pageDownOrUp(params.page+1)">下一页</a>
                <a class="tips" title="末页" @click="pageDownOrUp(totalPage)">末页</a>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
      <notice></notice>



  </div>
  </div>
</template>
<script>
  import {mapGetters,mapActions} from 'vuex'
  import MyHeader from '@/components/idc/layout/header'
  import MyFooter from '@/components/idc/layout/footer'
  import notice from '@/components/notice'
  import Utils from '@/components/comm/Utils'
  import { formatDate } from '@/components/comm/date.js'
  import {Indicator} from 'mint-ui'
  import to from "await-to-js";
  export default {
    components: {
      MyHeader,
      MyFooter,
      notice
    },
    data() {
      return{
        results:[],
        params:{
          lotteryId:101,
          accountDay:Utils.formatDate(new Date(),'yyyy-MM-dd'),
          size:8,
          page:1
        },
        showPage:1,
        totalPage:1,
        currentDate:new Date(),
        currentSelect:'',
        refreshTimer:null
      }
    },
    beforeDestroy(){
      if(this.refreshTimer){
        clearInterval(this.refreshTimer);
      }
    },
    filters: {
      formatDate(time) {
        var date = new Date(time);
        return formatDate(date, 'hh:mm:ss'); // 年月日 格式自己定义   'yyyy : MM : dd'  例 2018年12月5日的格式
      },
      resultFilter(val){
        let obj = '';
        if(val.at(0)==='0'){
          obj = val.at(1);
        }else{
          obj = val;
        }
        return obj;
      }
    },
    mounted(){
      let self = this;
      if(self.gameId){
        self.params.lotteryId = self.gameId;
      }else{
        self.params.lotteryId = self.gameMenu[0].index;
      }
      let date = new Date();
      let hh = date.getHours();
      if (hh < 7) {
        date.setDate(date.getDate() - 1);
        let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
        let spliceArr = dateParam.split('-');
        this.currentDate = new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]);
        this.params.accountDay = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy-MM-dd');
      }

      this.$nextTick(function(){
        self.getLotteryResult();
        self.refreshTimer = setInterval(()=>{
          setTimeout(self.getLotteryResult(),0);
        },10000);
      })



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
      inputPages(){
        if(this.showPage!=''){

          if(this.params.page == this.totalPage && this.showPage>this.totalPage){
            this.showPage = this.totalPage;
            return;
          }
          this.params.page = this.showPage;
          if(this.showPage>this.totalPage){
            this.showPage = this.totalPage;
            this.params.page = this.totalPage;
          }else if(this.showPage==0){
            this.params.page = 1;
            this.showPage = 1;
          }
          this.getLotteryResult();
        }

      },
      pageDownOrUp(page){
        if(page==0||page>this.totalPage){
          return;
        }
        this.params.page = page;
        this.showPage = this.params.page;
        this.getLotteryResult();
      },
      async getLotteryResult(){
        Indicator.open({text:'加载中...'});
        let self = this;
        let [err,data] = await to(this.$api.game.lotteryResult(self.params));
        if(data.success){
          if(data.data.dataList){
            data.data.dataList.forEach((resultItem)=>{
              let param = [];
              if(self.params.lotteryId==102 || self.params.lotteryId==201 || self.params.lotteryId==402||self.params.lotteryId==403
                || self.params.lotteryId==404 || self.params.lotteryId==106|| self.params.lotteryId==107|| self.params.lotteryId==204
                || self.params.lotteryId==205 || self.params.lotteryId==208|| self.params.lotteryId==301|| self.params.lotteryId==303
                || self.params.lotteryId==601){
                resultItem.gameNo = resultItem.gameNo.toString().substr(4);
              }
              if(typeof resultItem.result != 'undefined'){

                if(this.params.lotteryId==101 || this.params.lotteryId==102 ||
                  this.params.lotteryId ==104 || this.params.lotteryId==103 ||
                  this.params.lotteryId ==105 || this.params.lotteryId==106 ||
                  this.params.lotteryId==107 ||
                  this.params.lotteryId ==108 || this.params.lotteryId==109
                  || this.params.lotteryId==110 || this.params.lotteryId==111
                  || this.params.lotteryId==112){
                  resultItem.result.split(',').forEach((obj)=>{
                    if(obj.at(0)==='0'){
                      param.push(obj.at(1));
                    }else{
                      param.push(obj);
                    }
                  });
                }else if(this.params.lotteryId==201 || this.params.lotteryId == 203 || this.params.lotteryId==202||
                  this.params.lotteryId==204 || this.params.lotteryId == 205 || this.params.lotteryId==206||
                  this.params.lotteryId==207 || this.params.lotteryId == 208 || this.params.lotteryId==209||
                  this.params.lotteryId==210){
                  resultItem.result.split(',').forEach((obj)=>{
                    param.push(obj);
                  });

                }else if(this.params.lotteryId==401||this.params.lotteryId==402||this.params.lotteryId==403||
                  this.params.lotteryId==404||this.params.lotteryId==405){
                  let he = 0;
                  let record = [];
                  resultItem.result.split(',').forEach((obj)=>{
                    he+=Number.parseInt(obj);
                    param.push(obj);
                  });
                  record['he']=he;
                  record['oe'] = he % 2 === 0 ? self.$t('EVEN') :  self.$t('ODD');
                  record['ou'] = he>10?self.$t('H5_OVER'):self.$t('H5_UNDER');
                  record['smz'] = self.$t('he_'+Utils.getSmzResult(he));
                  param.push(record);
                }else if(this.params.lotteryId==801){
                  let he = 0;
                  let record = [];
                  resultItem.result.split(',').forEach((obj)=>{
                    he+=Number.parseInt(obj);
                    param.push(obj);
                  });
                  record['he']=he;

                  param.push(record);
                }else if(this.params.lotteryId==301 || this.params.lotteryId==302 || this.params.lotteryId==303
                  || this.params.lotteryId==304){
                  resultItem.result.split(',').forEach((obj)=>{
                    if(obj.at(0)==='0'){
                      param.push(obj.at(1));
                    }else{
                      param.push(obj);
                    }
                  });

                }else if(this.params.lotteryId==701 ){
                  resultItem.result.split(',').forEach((obj)=>{
                    if(obj.at(0)==='0'){
                      param.push(obj.at(1));
                    }else{
                      param.push(obj);
                    }
                  });

                }else if(this.params.lotteryId==501 ||this.params.lotteryId==502){
                  resultItem.result.split(',').forEach((obj)=>{
                    if(obj.at(0)==='0'){
                      param.push(obj.at(1));
                    }else{
                      param.push(obj);
                    }
                  });

                }else if(this.params.lotteryId==601 ){
                  resultItem.result.split(',').forEach((obj)=>{
                    if(obj.at(0)==='0'){
                      param.push(obj.at(1));
                    }else{
                      param.push(obj);
                    }
                  });

                }

                resultItem.result = param;
              }
            });
            self.results = data.data.dataList;
            self.totalPage = data.data.total % self.params.size==0?data.data.total/self.params.size:parseInt(data.data.total/self.params.size)+1;
            if(data.data.total==0){
              self.showPage = 1;
              self.params.page = 1;
            }
          }
        }
        Indicator.close();
      },
      select(){
        this.params.page = 1;
        this.showPage = 1;
        this.getLotteryResult();
      },
      openPicker() {
        this.$refs.picker.open();
      },
      handleChange(value) {
        this.params.accountDay = Utils.formatDate(value,'yyyy-MM-dd');
        this.getLotteryResult();
      }
    }
  }
</script>

<style scoped>
  .PVZIk > div {
    -webkit-flex: 1 1 50%;
    -ms-flex: 1 1 50%;
    flex: 1 1 50%;
    box-sizing: border-box;
    height: 100%;
    text-align: center;
  }
  .biwSnT div {
    color: #666;
    width: 100%;
    font-weight: 400;
    font-size: 0.8125rem;
    border: 1px solid rgba(255,0,0,0);
    background-color: rgba(255,255,255,0);
    line-height: 2.5rem;
  }
  .tb_tit select {
    padding: 0 0 0 10px;
    background-color: #fff;
    height: 40px;
    width: 100%;
    color: #131313;
    border-radius: 4px;
    text-align: center;
    font-size: 14px;
    font-weight: bold;
    margin-right: 5px;
  }
  option{
    padding: 0 0 0 10px;
  }
  .zxykL .ref a.refresh-icon{
    vertical-align: middle;
    height: 20px;
    width: 20px;
    margin-right: 6px;
    margin-left: 6px;
    display: inline-block;
    background: url(../../images/shuaxin.png) center / contain no-repeat;
  }
  .zxykL .ref a{
    font-size: 0;
  }
  .biwSnT {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    padding: 5px;
    border-right: 1px solid #eaeaea;
  }
  .uLYqo {
    height: calc(100% - 105px);
  }
  .zxykL {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: space-around;
    -webkit-justify-content: space-around;
    -ms-flex-pack: space-around;
    justify-content: space-around;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    height: 40px;
    border-bottom: 1px solid #ccc;
    box-shadow: 0 0 5px #ccc;
    background-color: #fff;
  }
  .ljfNBe {
    -webkit-flex: 3;
    -ms-flex: 3;
    flex: 3;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: space-around;
    -webkit-justify-content: space-around;
    -ms-flex-pack: space-around;
    justify-content: space-around;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    height: 100%;
  }
  .kbmxrg {
    margin-right: 3px;
    -webkit-flex: 5;
    -ms-flex: 5;
    flex: 5;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: space-around;
    -webkit-justify-content: space-around;
    -ms-flex-pack: space-around;
    justify-content: space-around;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    height: 100%;
  }
  .bxxJAa {
    position: relative;
    height: calc(100% - 90px);
    overflow-x: hidden;
    overflow-y: auto;
    width: 100%;
    background-color: #fff;
  }
  .cfhhpx {
    height: 45px;
    width: 100%;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: space-evenly;
    -webkit-justify-content: space-evenly;
    -ms-flex-pack: space-evenly;
    justify-content: space-evenly;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    border-bottom: 1px solid #ccc;
  }
  .tips{
    font-size: 14px;
    margin: 0 8px;
  }
  .list {
    color: #666 !important;
    margin: 1px 0;
    font-size: 14px;
    position: relative;
    z-index: 1;
  }
  .list ul {
    list-style: none;
    margin: 0;
    padding: 0;
    position: relative;
    background: #fff;
  }
  .list .item-content {
    min-height: 44px;
    padding-left: 20px;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
  }
  .list .item-inner {
    position: relative;
    width: 100%;
    padding-top: 8px;
    padding-bottom: 8px;
    min-width: 0;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-align-self: stretch;
    -ms-flex-item-align: stretch;
    align-self: stretch;
    min-height: 44px;
  }
  #listkl8{
    display: inline-flex;
    flex-flow: row wrap;
  }

  .item-label {
    overflow: visible;
    font-size: 14px;
    width: 35%;
    line-height: 1.4;
    -webkit-align-self: flex-start;
    -ms-flex-item-align: start;
    align-self: flex-start;
    padding-top: 3px;
  }
  .list .item-title {
    min-width: 0;
    -webkit-flex-shrink: 1;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    white-space: nowrap;
    position: relative;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 100%;
  }
  .list .item-input-wrap {
    padding-right: 15px;
    margin-top: -8px;
    margin-left: 5px;
    margin-bottom: -8px;
    width: 100%;
    -webkit-flex-shrink: 1;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    position: relative;
  }
  .list input{
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    border: none;
    -webkit-box-shadow: none;
    box-shadow: none;
    border-radius: 0;
    outline: 0;
    display: block;
    padding: 0;
    margin: 0;
    resize: none;
    font-size: inherit;
    font-family: inherit;
    text-align: right;
    width: 100%;
    height: 44px;
    color: #000;
    font-size: 14px !important;
    line-height: 44px;
  }
</style>


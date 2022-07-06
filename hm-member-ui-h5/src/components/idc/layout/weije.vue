<template>
  <div class="home">
    <div class="sc-bZQynM OQRyf">
      <my-header title="这里是你取到的值"></my-header>
      <div class="sc-htoDjs UDzZc">
        <div class="table">
        <div class="table-header">
          <div class="col col2">注单号</div>
          <div class="col col3">类型</div>
          <div class="col col1-5">玩法</div>
          <div class="col col1-5">下注</div>
          <div class="col col1">可赢<a class="refresh-icon">Refresh</a></div>
        </div>
        <div class="rough_lines"></div>
        <div class="table-content">
          <template v-for="(list, index) in orderList">
            <div class="table-row">
              <div class="col col2">
                <div class="green_color">{{list.noteNumber}}</div>
                <div>{{list.noteDate}}</div>
                <div>{{list.noteTime}}</div>
              </div>
              <div class="col col3">
                <div>{{list.noteType}}</div>
                <div class="green_color">{{list.noteTypeNumber}}</div>
                <div class="blue_color">{{list.noteTypePosition}}</div>
              </div>
              <div class="col col1-5">
                <div class="blue_color">{{list.form}}</div>
                <div class="red_color">
                  <span class="blue_color">@</span>
                  {{list.formSubordinate}}
                </div>
              </div>
              <div class="col col1-5">{{list.BetsMoney}}</div>
              <div class="col col1 blue_color">{{list.win}}</div>
            </div>
          </template>
        </div>
        <div class="rough_lines"></div>
        <div class="table-footer pagination">
            <div class="pagination sc-gzVnrw oPkft">
              <div class="page-navi">
                <span class="prev disabled"><a></a></span>
                <span class="current">
                  <form class="page-jump">
                    <input type="text"  value="" placeholder="1">
                  </form><span>页</span></span>
                <span class="next disabled"><a></a></span>
              </div>
            </div>
          <div>
          <div class="footer-inline"><div class="title">注数(总计)</div><span class="content">{{this.orderTotal}}</span></div>
          <div class="footer-inline"><div class="title">本页下注金额</div><span class="content">{{this.pageOrderMoney}}</span></div>
          <div class="footer-inline"><div class="title">本页结果</div><span class="content">{{this.pageOrderWin}}</span></div>
        </div>
        </div>

      </div>
      </div>
    </div>
    <my-footer></my-footer>
  </div>
</template>
<script>

  import MyHeader from '@/components/idc/layout/header'
  import MyFooter from '@/components/idc/layout/footer'
  import to from "await-to-js";
  //在个位数的时间前加0
  function formatZero(n){
    if(n>=0&&n<=9){
      return "0"+n;
    }else{
      return n;
    }
  }

  //获取当前日期和时间
  function getCurrentDateTime(){
    var t=new Date();
    var year=t.getFullYear();
    var month=t.getMonth()+1;
    var day=t.getDate();
    var hours=t.getHours();
    var minutes=t.getMinutes();
    var seconds=t.getSeconds();
    return year+"-"+formatZero(month)+"-"+formatZero(day)+" "+formatZero(hours)+":"+formatZero(minutes)+":"+formatZero(seconds);
  }

  export default {
    components: {
      MyHeader,
      MyFooter,
    },
    data() {
      return {
        orderList:[],
        orderTotal:'',
        pageOrderMoney:0,
        pageOrderWin:0
      }
    },
    computed: {

    },
    mounted(){
      const arr = [];
      for(let i = 0;i<10;i++){
        arr.push({noteNumber:81400+i,
          noteDate:getCurrentDateTime().substring(0,10),
          noteTime:getCurrentDateTime().substring(11,19),
          noteType:'安徽快三',
          noteTypeNumber:'第 20190430009 期',
          noteTypePosition:'盘口(A)',
          form:'围骰 '+(i+1)+''+(i+1)+''+(i+1),
          formSubordinate:'213',
          BetsMoney:10,
          win:2130.00
          })
        this.pageOrderMoney+=arr[i].BetsMoney;
        this.pageOrderWin += arr[i].win;
        arr[i].win = arr[i].win.toFixed(2);
      }
      this.pageOrderWin=this.pageOrderWin.toFixed(2);
      this.orderTotal = arr.length;
      this.orderList = arr;
    }
  }
</script>
<style scoped>
  .UDzZc {
    height: calc(100% - 46px);
    position: relative;
  }
  .table {
    height: 100%;
  }
  .table {
    -webkit-box-flex: 1;
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-flex-direction: column;
    -ms-flex-direction: column;
    flex-direction: column;
  }
  .table .table-header {
    height: 30px;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    border-bottom: 1px solid #eaeaea;
    background-color: #fff;
  }
  .table .table-header > div {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    border-right: 1px solid #eaeaea;
    box-sizing: border-box;
  }
  .table .table-header > div {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    border-right: 1px solid #eaeaea;
    box-sizing: border-box;
  }
  .table .col {
    font-size: 10px;
  }
  .table .col3 {
    -webkit-flex: 3;
    -ms-flex: 3;
    flex: 3;
  }
  .table .col2 {
    -webkit-flex: 2;
    -ms-flex: 2;
    flex: 2;
  }
  .table .col1 {
    -webkit-flex: 1;
    -ms-flex: 1;
    flex: 1;
  }
  .table .col1-5 {
    -webkit-flex: 1.5;
    -ms-flex: 1.5;
    flex: 1.5;
  }
  .table .table-header > div:last-child {
    border-right: 0;
  }
  .table .table-header > div:last-child {
    border-right: 0;
  }
  .table .table-content {
    height: 100%;
    background-color: #ebebeb;
  }
  .table .table-content {
    overflow: auto;
    -webkit-flex: 1;
    -ms-flex: 1;
    flex: 1;
  }
  .table .table-content > .table-row {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    width: 100%;
    height: 50px;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    box-sizing: border-box;
    border-bottom: 1px solid #eaeaea;
    background-color: #fff;
  }
  .table .table-content > .table-row > * {
    text-align: center;
  }
  .table .table-footer.pagination {
    height: 70px;
    display: block;
  }
  .table .table-footer {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    height: 30px;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    background-color: #fff;
    border-top: 1px solid #eaeaea;
    position: relative;
  }
  .table .table-footer .pagination {
    position: absolute;
    right: 0;
    bottom: 0;
    height: 40px;
    width: 200px;
  }
  .table .table-footer .pagination {
    position: absolute;
    right: 0;
    bottom: 0;
    height: 40px;
    width: 200px;
  }
  .oPkft {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    height: 3rem;
    line-height: 3rem;
    background-color: #fff;
  }
  .oPkft > div {
    -webkit-box-flex: 1;
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    text-align: center;
    position: relative;
  }
  .oPkft .page-navi {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
  }
  .oPkft .page-info, .oPkft .page-navi {
    font-size: 14px;
  }
  .oPkft .page-navi span {
    -webkit-flex: 1 1 33%;
    -ms-flex: 1 1 33%;
    flex: 1 1 33%;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    position: relative;
  }
  .oPkft .page-navi span.disabled a {
    pointer-events: none;
    background-color: #ccc;
    color: #424242;
  }
  .oPkft .page-navi span.prev a, .oPkft .page-navi span.next a {
    border-radius: 4px;
    width: 28px;
    height: 30px;
    margin: auto;
    color: #fff;
    font-size: 0;
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-image: url(../../images/you.png);
    background-color: #2061b3;
    background-position: center;
    background-size: 50% 50%;
    background-repeat: no-repeat;
  }
  .oPkft .page-navi span.next a {
    -webkit-transform: scaleX(-1);
    -ms-transform: scaleX(-1);
    transform: scaleX(-1);
  }
  .oPkft .page-navi span.current {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    line-height: 40px;
  }
  .oPkft .page-navi span.current > * {
    -webkit-box-flex: 1;
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    -webkit-flex-shrink: 1;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    margin: 0 2px;
  }
  .oPkft .page-navi span {
    -webkit-flex: 1 1 33%;
    -ms-flex: 1 1 33%;
    flex: 1 1 33%;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    position: relative;
  }
  .oPkft .page-navi span {
    -webkit-flex: 1 1 33%;
    -ms-flex: 1 1 33%;
    flex: 1 1 33%;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    position: relative;
  }
  .oPkft .page-navi span.current {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    line-height: 40px;
  }
  .oPkft .page-jump input {
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 100%;
    height: 23px;
    margin: 8px 0px;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    background: transparent;
    box-sizing: border-box;
  }
  .oPkft .page-navi span.disabled a {
    pointer-events: none;
    background-color: #ccc;
    color: #424242;
  }
  .oPkft .page-navi span.disabled a {
    pointer-events: none;
    background-color: #ccc;
    color: #424242;
  }

  .table .table-footer .footer-inline:first-child {
    margin-top: 4px;
  }
  .table .table-footer .footer-inline {
    height: 20px;
    line-height: 20px;
    padding: 0 20px;
    box-sizing: border-box;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    width: 100%;
    font-size: 12px;
  }
  .table .table-footer .footer-inline .title {
    width: 100px;
    color: #aaa;
    display: block;
    -webkit-flex: 1 1 30%;
    -ms-flex: 1 1 30%;
    flex: 1 1 30%;
  }
  .table .table-footer .footer-inline .content {
    width: calc(100% - 100px);
    display: block;
    -webkit-flex: 1 1 70%;
    -ms-flex: 1 1 70%;
    flex: 1 1 70%;
    color: #5f5f5f;
  }

</style>


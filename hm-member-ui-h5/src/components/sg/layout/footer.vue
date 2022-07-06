<template>
  <div class="guIOEz">
    <a class="shortcut bet" @click="goMain"><span>{{$t(buttonList[0])}}</span></a>
    <a class="shortcut result" @click="goKjlist"><span>{{$t(buttonList[1])}}</span></a>
    <a class="shortcut stats" @click="goWeije"><span>{{$t(buttonList[2])}}</span></a>
    <a class="shortcut deposit" @click="goYije"><span>{{$t(buttonList[3])}}</span></a>
  </div>
</template>
<script>
  import {mapGetters,mapActions} from 'vuex'

  export default {
    data(){
      return {
        buttonList:[ 'betting','results','ticketList','todayWl' ]
      }
    },
    computed:{
      ...mapGetters(['gameMenu','game','member','pagePosition'])
    },
    mounted(){
      let self = this;


    },
    methods: {
      goMain() {
        if(this.game.lotteryId){
          for(let i = 0;i<this.gameMenu.length;i++){
            if(this.gameMenu[i].index==this.game.lotteryId){
              this.$router.push('/sg/'+this.gameMenu[i].title);
            }
          }
        }else{
          this.$router.push('/sg/main');
        }


      },
      goKjlist() {
        if(this.$router.history.current.name=='kjlist'){
          this.$router.go(0);
        }else{
          this.$router.push('/sg/kjlist');
        }


      },
      goWeije() {
        if(this.$router.history.current.name=='weijie'){
          this.$router.go(0);
        }else{
          let param ={lotteryId:this.game.lotteryId};
          this.$router.push({name:'weijie',query:param});
        }

      },
      goYije() {
        if(this.$router.history.current.name=='yijie'){
          this.$router.go(0);
        }else{
          let param ={lotteryId:this.game.lotteryId};
          this.$router.push({name:'yijie',query:param});
        }

      },
    }
  }
</script>
<style scoped>
  .guIOEz {
    width: 100%;
    height: 50px;
    background-color: #fff;
    box-sizing: border-box;
    left: 0;
    bottom: 0;
    position: absolute;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
  }
  .guIOEz:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    background-color: #ebebeb;
    width: 100%;
    height: 6px;
  }
  .guIOEz .shortcut.bet {
    background-image: url(../../images/touzhu.png);
  }
  .guIOEz .shortcut.result {
    background-image: url(../../images/jilu.png);
  }
  .guIOEz .shortcut.stats {
    background-image: url(../../images/weijie.png);
  }
  .guIOEz .shortcut.deposit {
    background-image: url(../../images/yijie.png);
  }
  .guIOEz .shortcut.service {
    background-image: url(../../images/kefu.png);
  }
  .guIOEz .shortcut span {
    position: absolute;
    font-size: 0.625rem;
    left: 0;
    right: 0;
    bottom: 3px;
    text-align: center;
  }
  .guIOEz .shortcut {
    text-decoration: none;
    display: block;
    height: 46px;
    margin-top: 6px;
    box-sizing: border-box;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -webkit-box-flex: 1;
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    -webkit-transition: all 200ms ease-in-out;
    transition: all 200ms ease-in-out;
    background-size: auto 50%;
    background-position: center 10%;
    background-repeat: no-repeat;
    position: relative;
  }
</style>

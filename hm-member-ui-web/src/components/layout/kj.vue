<template>
  <div class="menu1">
    <div id="result_info" class="draw_number">
      <div>{{$t(gameInfo.lotteryId)}}</div>
      <div>{{gameInfo.prevGameNo}}期开奖</div>
    </div>
    <a v-if="gameInfo.lotteryId == 101 || gameInfo.lotteryId == 102 || gameInfo.lotteryId == 103
            || gameInfo.lotteryId == 104 || gameInfo.lotteryId == 105 || gameInfo.lotteryId == 106"

       id="result_balls" target="_blank" :class="pk10ResultCss">
      <template v-if="game.lotteryId==gameInfo.lotteryId" v-for="(item,index) in gameInfo.prevResult">
        <span>
          <b :class="'b'+item">{{item}}</b></span>
      </template>
    </a>
    <a v-if="gameInfo.lotteryId == 201 || gameInfo.lotteryId == 202 ||gameInfo.lotteryId == 203 ||gameInfo.lotteryId == 204
||gameInfo.lotteryId == 205"
       target="_blank" class="T_SSC L_SSCJSC">
      <template v-if="game.lotteryId==gameInfo.lotteryId"
                v-for="(item,index) in gameInfo.prevResult">
        <span>
          <b :class="'b'+item">{{item}}</b></span>
      </template>
    </a>

    <a v-if="gameInfo.lotteryId == 301 || gameInfo.lotteryId == 302 ||gameInfo.lotteryId == 303
            || gameInfo.lotteryId == 304 "
       target="_blank" :class="klsfResultCss">
      <template v-if="game.lotteryId==gameInfo.lotteryId"
        v-for="(item,index) in gameInfo.prevResult">
        <span>
          <b :class="'b'+item">{{item}}</b></span>
      </template>
    </a>
    <!--新加PCDD开奖样式-->
    <a v-if="gameInfo.lotteryId == 401 || gameInfo.lotteryId == 402 || gameInfo.lotteryId == 403"
       target="_blank" class="T_PCDD T_PCDDD jsdd">

      <template v-if="game.lotteryId==gameInfo.lotteryId" v-for="(item,index) in gameInfo.prevResult">
        <span ><b :class="'n'+item">{{item}}</b>
          <i v-if="index<gameInfo.prevResult.length-1" style="margin:-25px 0px 0px 40px">+</i>
          <i v-else style="margin:-25px 0px 0px 40px">=</i>
        </span>
      </template>
      <span v-if="gameInfo.numHe && gameInfo.numHe > 0" class="num"><b :class="'n_'+gameInfo.numHe">{{gameInfo.numHe}}</b></span>
    </a>
  </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import io from 'socket.io-client'

  export default {
    name: "kj",
    data() {
      return {
      }
    },
    computed: {
      ...mapGetters(['gameInfo', 'game', 'member', 'gameId']),
      klsfResultCss(){
        if(this.gameId==301 || this.gameId==302 || this.gameId==304 || this.gameId==601){
          return 'T_KLSF L_GDKLSF';
        }else if(this.gameId==303){
          return 'T_XYNC L_XYNC';
        }
      },
      pk10ResultCss(){
        if(this.gameId==101 || this.gameId == 102 || this.gameId == 103 || this.gameId == 104 || this.gameId == 105
          || this.gameId == 106 || this.gameId == 108 || this.gameId == 110 || this.gameId == 111 || this.gameId == 112){
          return 'T_PK10 L_BJPK10';
        }else{
          return 'T_SMN';
        }
      }
    },
    methods: {
      ...mapActions(['setLotteryNow', 'setLotteryNow', 'setBalances','setBetGameNo','setLatesNoteList']),
    },
    mounted() {
    }
  }
</script>

<style scoped>

</style>

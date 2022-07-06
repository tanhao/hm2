<template>
  <div>
    <div :class="showShuyin?'sc-kkGfuU hkRtka':'sc-kkGfuU hkRtka cEVbRO'"  v-show="showShuyin"><!--缩要加cEVbRO-->
      <div class="col ">
        <div class="title">{{$t('userBalance')}}</div>
        <div class="content blue_color">{{balance|moneyFmt}}</div>
      </div>
      <div class="col">
        <div class="title">未结算金额</div>
        <div class="content othco">{{betWaiting|moneyFmt}}</div>
      </div>
      <div class="col">
        <div class="title">{{$t('wl')}}</div>
        <div class="content othco" id="user_sy">
          <template v-if="parseFloat(winLose) >= 0">
            <span class="blue_color">{{winLose | moneyFmt}}</span>

          </template>
          <template v-if="parseFloat(winLose) < 0">
            <span class="red_color">{{winLose | moneyFmt}}</span>

          </template>
        </div>
      </div>
    </div>
    <div class="sc-iAyFgw jnBEKs">
      <div :class="showShuyin?'uq_icon':'uq_icon uq_icon2'" @click="showhide()"></div><!--缩要加uq_icon2-->
    </div>
  </div>
</template>
<script>
  import {mapActions,mapGetters} from 'vuex'
  import Utils from '@/components/comm/Utils.js'
  export default {
    data() {
      return{
        showShuyin:true
      }
    },
    computed: {
      ...mapGetters(['member','balance','betWaiting','winLose']),
      balanceFormat(){
        return Utils.formatMoney(this.member.balance,2);
      },
      wlFormat(){
        return Utils.formatMoney(this.member.winLose,2);
      },
    },
    methods: {
      showhide(){
        if(this.showShuyin){
          this.showShuyin=false;
        }
        else{
          this.showShuyin=true;
        }

        let data = {
          showShuyin:this.showShuyin
        };
        this.$emit('showShuyin',data);
      }
    },
    filters:{

      moneyFmt(val){
        if(!val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      }
    }
  }
</script>
<style scoped>

</style>

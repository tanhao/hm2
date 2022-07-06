<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-gzVnrw flXAPw">
          <my-header maintop="true" top="true" name="true"></my-header>
          <div class="sc-htoDjs PCytj">
            <div class="user-profile">
              <div class="profile-pic"></div>
            </div>
            <div class="profile-detail">
              <div class="profile-name">
                {{member.username}}
              </div>
              <div class="profile-balance">
                总余额：
                <span>{{Utils.formatMoney(member.balance,2)}}</span>
                <a @click="refreshBalanceFun" class="refresh-icon">Refresh</a>
              </div>
            </div>
          </div>
          <div class="rough_lines"></div>
          <div class="scroll-wrapper-home after-login">
            <div class="lottery-list">
              <template v-for="(list, index) in gameMenu">
                <a :class="'lottery-item '+list.title.toUpperCase()" @click="goGames(list.index,list.title)"><span>{{$t(list.title)}}</span></a>
              </template>
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
  import to from "await-to-js";
  export default {
    components: {
      MyHeader,
      notice,
    },
    data() {
      return {}
    },
    computed: {
      ...mapGetters(['gameMenu','socket', 'siteName', 'modeId', 'member', 'showMenu']),
    },
    methods: {
      ...mapActions(['changeMenu','setClearPosition', 'setBalances', 'setLotteryNow', 'selectGame', 'setCurrentGame', 'setPagePosition', 'setPlayType', 'setGames']),
      goGames(id, title) {
        this.$router.push('/idc/' + title);
        /*this.selectGame(id).finally(() => {

        });*/
      },
      async refreshBalanceFun() {
        let [err,data] = await to(this.$api.mem.balanceInfo());
        if(data.success){
          this.setBalances(data.data);
        }
      },
      async loadLottery() {
        let [err,data] = await to(this.$api.game.getLotteryList({}));
        if(data.success){
          this.setGames(data.data);

        }
      }
    },
    mounted() {
      //this.setPagePosition('main');
      this.setPlayType(null);
      this.changeMenu(false);
      this.loadLottery();
      this.setClearPosition();
      document.title = this.siteName + '系统';
    }
  }
</script>


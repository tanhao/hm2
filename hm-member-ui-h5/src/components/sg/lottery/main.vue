<template>
   <div class="home">
        <div class="sc-bZQynM OQRyf">
          <div class="sc-gzVnrw flXAPw">
            <my-header maintop="true" apiRequest = "true"></my-header>
            <div class="sc-htoDjs PCytj">
              <div class="user-profile">
                <div class="profile-pic"></div>
              </div>
              <div class="profile-detail">
                <div class="profile-name">
                  {{member.username}}
                </div>
                <div class="profile-balance">
                  {{$t('userBalance')}}：
                  <span>{{balance | moneyFmt}}</span>
                  <a class="refresh-icon" @click="refreshBalance">Refresh</a><span @click="refreshBalance">刷新</span>
                </div>
              </div>
            </div>
            <notice></notice>
            <div class="scroll-wrapper-home after-login">
              <div class="lottery-list">
                <template v-for="(list, index) in gameMenu">
                  <template >
                    <a :class="'lottery-item '+list.title.toUpperCase()" @click="goGames(list.index,list.title)"><span>{{$t(list.title)}}</span></a>
                  </template>
                </template>
              </div>
            </div>
          </div>
        </div>
       <my-footer mainSearchResultAll="true"></my-footer>
      </div>
</template>
<script>
  import {mapGetters,mapActions} from 'vuex'
  import notice from '@/components/notice'
  import MyHeader from '@/components/sg/layout/header'
  import MyFooter from '@/components/sg/layout/footer'
  import member from '@/axios/api-mem.js'
  import Utils from '@/components/comm/Utils'
  import LotteryApi from '@/axios/api-game.js'
  import to from "await-to-js";
  export default {
    components: {
      MyHeader,
      MyFooter,
      notice,
    },
    data() {
      return{
      }
    },
    computed: {
      ...mapGetters(['gameMenu','socket','balance','siteName','showMenu','member']),
    },
    methods:{
      ...mapActions(['changeMenu','selectGame','setClearPosition','setBalances','setPlayType','setGames']),
      goGames(id,title){
        let self = this;
        this.$router.push('/sg/'+title);
        /*self.selectGame(id).finally(()=>{

        });*/
      },
      async refreshBalance(){
        let self = this;
        let [err,data] = await to(member.balanceInfo());
        if(data.success){
          self.setBalances(data.data);
        }
      },
      async lotterysInit(){
        let self = this;
        let [err,data] = await to(LotteryApi.getLotteryList({}));
        if(data.success){
          self.setGames(data.data);

        }
      }
    },
    filters:{
      moneyFmt(val){
        if(!val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      }
    },
    mounted(){
      this.setPlayType(null);
      this.changeMenu(false);
      //this.selectGame(null);
      this.lotterysInit();
      this.setClearPosition();
      setTimeout(()=>{
        document.title = this.siteName+'系统';
      },1000);
    },
  }
</script>

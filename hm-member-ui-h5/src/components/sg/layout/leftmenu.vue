<template>
  <div>
    <div :class="showMenu?'sc-bxivhb gzjRIk':'sc-bxivhb'" @click="hideMenu"></div><!--显示侧边加gzjRIk-->
    <div :class="showMenu?'sc-ifAKCX gLhExP hSRnbX':'sc-ifAKCX gLhExP'"><!--显示侧边加hSRnbX-->
      <div class="sidebar-header">
        <h3 class="username">
          {{member.username}}
        </h3>
        <h3 class="balance">总余额：{{balance | moneyFmt}}</h3>
      </div>
      <div class="rough_lines"></div>
      <div class="sidebar-content">
        <template v-for="list in leftMenuList">
          <a class="sidebar-item"  @click="jumpPages(list.href)">
            <div :class="'sidebar-item-icon mtd_icon'+list.icon"></div>
            <div class="sidebar-item-title">{{list.title}}</div>
          </a>
        </template>
        <div class="rough_lines"></div>
        <a class="sidebar-item" href="#" @click="logout">
          <div class="sidebar-item-icon mtd_icon15"></div>
          <div class="sidebar-item-title">退出</div>
        </a>
      </div>
      <div class="sidebar-footer"></div>
    </div>
  </div>
</template>
<script>
  import {mapGetters, mapActions} from 'vuex'
  import UserApi from '@/axios/api-mem'
  import Utils from '@/components/comm/Utils.js'
  export default {
    data() {
      return {
        params: {
          loginName: 'mfktdkj',
          balance: '99804.60',
          loginMode: 'MOBILE',
          code: '',
        },
        leftMenuList: []
      }
    },
    computed: {
      ...mapGetters(['gameMenu','socket','showMenu','member','game','balance']),
    },
    methods: {
      ...mapActions(['setPromptInformation','changeMenu','setLogout','selectGame']),
      hideMenu() {
        this.changeMenu(false);
      },
      jumpPages(url){
        if(url=='main'){
          this.selectGame(null);
        }
        this.changeMenu(false).finally(()=>{
          if(url=='weije'){
            this.$router.push({path:'/sg/weije',query:{lotteryId:null}});
          }else if(url=='yije'){

            this.$router.push({path:'/sg/yije',query:{lotteryId:null}});
          }else if(url=='rules'){
            this.$router.push({path:'/sg/rules',query:{lotteryKey:this.game.lotteryKey}});
          }
          else{
            this.$router.push('/sg/'+url);
          }

        });

      },
      logout(){
        let self = this;
        self.$messageBox.close();
        self.$messageBox({$type:'confirm',message:'确认退出吗？',title:'提示',closeOnClickModal:false,showCancelButton:true}).then(action=>{
          if(Object.is(action,'confirm')){
            UserApi.logout().then(val=>{
              if(val){
                if(val.code===10000){
                  this.setLogout();
                  if(self.socket && self.socket.ws.readyState == 1) {
                    self.socket.send('{"code":"odds_unlottery"');
                  }
                  window.location.href = '/';


                }
              }
            })
          }
        }).catch((err)=>{
          if(Object.is(err,'cancel')){
            return;
          }
        }).finally(()=>{

        });

      }
    },
    mounted() {
      this.leftMenuList.push(
        {title: '首页', icon: 1, href: 'main'},
        {title: '个人资讯', icon: 2, href: 'userinfo'},
        {title: '修改密码', icon: 4, href: 'password'},
        // {title: '资金管理', icon: 3, href: '#'},
        {title: '未结明细', icon: 6, href: 'weije'},
        {title: '今天已结', icon: 7, href: 'yije'},
        {title: '两周报表', icon: 8, href: 'history'},
        {title: '开奖结果', icon: 9, href: 'kjlist'},
        {title: '规则', icon: 10, href: 'rules'}
      );
    },
    filters:{
      moneyFmt(val){
        if(!val || 0 == val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      }
    }
  }
</script>

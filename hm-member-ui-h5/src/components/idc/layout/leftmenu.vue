<template>
  <div id="idc">
    <div :class="showMenu?'sc-bxivhb gzjRIk':'sc-bxivhb'" @click="hideMenu"></div>
    <div class="sc-ifAKCX gLhExP hSRnbX">
      <div class="sidebar-header">
        <h3 class="username">
          {{member.username}}
        </h3>
        <h3 class="balance">总余额：{{member.balance?Utils.formatMoney(member.balance,2):0.00}}</h3>
      </div>
      <div class="sidebar-content">
        <template v-for="list in leftMenuList">
          <a class="sidebar-item" @click="jumpPages(list.href)" >
            <div :class="'sidebar-item-icon mtd_icon'+list.icon"></div>
            <div class="sidebar-item-title">{{list.title}}</div>
          </a>
        </template>
        <a class="sidebar-item" @click="logout">
          <div class="sidebar-item-icon mtd_icon9"></div>
          <div class="sidebar-item-title">安全退出</div>
        </a>
      </div>
      <div class="sidebar-footer"></div>
    </div>
  </div>
</template>
<script>
  import {mapGetters, mapActions} from 'vuex'
  import member from '@/axios/api-mem.js'
  import Utils from '@/components/comm/Utils'
  export default {
    data() {
      return {
        leftMenuList: []
      }
    },
    computed: {
      ...mapGetters(['showMenu','socket','member','playType','game']),
    },
    methods: {
      ...mapActions(['setPromptInformation','changeMenu','setPlayType','selectGame','setLogout']),
      hideMenu() {
        this.changeMenu(false);
      },
      jumpPages(href){
        if(href=='main'){
          this.selectGame(null);
        }
        if(href!='/idc/details/'){
          this.setPlayType(null);
        }
        this.changeMenu(false);
        if(href=='/idc/details/'){
          this.$router.push({path:'/idc/details/',query:{lotteryId:null}});
        }else{

          this.$router.push(href);
        }
      },
      logout(){
        let self = this;
        self.$messageBox.close();
        self.$messageBox({$type:'confirm',message:'确认退出吗？',title:'提示',closeOnClickModal:false,showCancelButton:true}).then(action=>{
          if(action==='confirm'){
            member.logout().then(val=>{
              if(val){
                if(val.code===10000){
                  if(self.socket && self.socket.ws.readyState == 1) {
                    self.socket.send('{"code":"odds_unlottery"');
                  }
                  window.location.href='/';
                  this.setLogout();
                }
              }
            })
          }
        }).catch(error=>{
          return;
        });

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
    mounted() {
      this.leftMenuList.push(
        {title: '游戏大厅', icon: 1, href: '/idc/main'},
        {title: '信用资料', icon: 2, href: '/idc/information'},
        /*{title: '帐务查询', icon: 4, href: '/idc/inquiry/'},*/
        {title: '下注明细', icon: 3, href: '/idc/details/'},
        {title: '结算报表', icon: 6, href: '/idc/profitlos/'},
        {title: '历史开奖', icon: 7, href: '/idc/kjlist/'},
        {title: '修改密码', icon: 5, href: '/idc/password/'},
        {title: '规则说明', icon: 8, href: '/idc/rules/'},
        // {title: '安全退出', icon: 9, href: '#'},
      );
    }
  }
</script>

<template>
  <div id="header" class="header">
    <div class="logo">
      <span>{{siteName}}</span>
    </div>
    <div class="top">
      <div @mouseenter="gamesMenuShow(false)" class="menu">
        <topkj ></topkj>
        <toplist  @changeSkin="changeSkin"></toplist>
        <div class="menu3"><a href="#" @click="logout" class="logout">退出</a></div>
        <div style="clear:both;"></div>
      </div>
      <div class="lotterys">
        <div id="lotterys" style="display: none">
        </div>
        <div class="show ">
          <template v-for="(list,index) in showGameMenu.menuFirst">
            <!--选中的游戏的加 class="selected" -->
            <a style="cursor:pointer" :class="gameId==list.id?'selected':''" @click="changeMenu(list,true)"><span @mouseenter="gamesMenuShow(false)">{{$t(list.lotteryKey)}}</span></a>
          </template>
        </div>
        <template v-if="showGameMenu.menuLast.length>1">
        <a class="more_game" :class="moreGameSelect.css" style="display: block;"><span @mouseenter="gamesMenuShow(true)">{{$t(moreGameSelect.title)}}</span></a>
        </template>
        <template v-if="showGameMenu.menuLast.length==1">
          <template v-for="(list,index) in showGameMenu.menuLast">
            <a style="cursor:pointer" :class="gameId==list.id?'selected':''" @click="changeMenu(list,true)"><span>{{$t(list.lotteryKey)}}</span></a>
          </template>
        </template>
        <a class="setting" @mouseenter="gamesMenuShow(false)" @click="dragMenuTableShow">设置</a>

        <template>

        </template>
      </div>
      <div class="sub">
        <div>
          <template v-for="(list,index) in categoryList">

            <a style="margin-right: 5px;cursor:pointer;"  :class="list==categorySelect?'selected':''" @click="categorySelection(list)">{{$t(list)}}</a>
          </template>
        </div>
      </div>
    </div>




  </div>


</template>

<script>
  import topkj from '@/components/layout/kj'
  import toplist from '@/components/layout/toplist'
  import {mapGetters, mapActions} from 'vuex'
  export default {
    components: {
      toplist,
      topkj,
    },
    data() {
      return {
      }
    },

    computed:{
      ...mapGetters(['gameMenu','siteName','socket','showGameMenu','pagePosition','gameId','categoryList','categorySelect','gameInfo']),
      moreGameSelect(){
        let self = this;
        let returnObj = {'css':'','title':''};
        let index = self.showGameMenu.menuLast.findIndex((value,index,arr)=>{
          return value.id==self.gameId;
        })
        if(index!=-1){
          self.$set(returnObj,"css",'selected');
          self.$set(returnObj,"title",self.gameId);
        }else{
          self.$set(returnObj,"css",'');
          self.$set(returnObj,"title",'moreGame');
        }
        return returnObj;
      },
    },
    watch:{

    },
    methods: {
      ...mapActions(['setWhetherSwitch','setLatesNoteList','setPlanList','setPromptInformation','setGames', 'setCategorySelect', 'setLogout', 'selectGame', 'setPlayType']),
      gamesMenuShow(flag) {
        this.$emit('gamesMenuShow', flag);
      },
      categorySelection(title){
        let self = this;
        this.setCategorySelect(title);
        if(this.pagePosition.indexOf('lottery')==-1){
          let item = '';
          this.gameMenu.findIndex((value,index,arr)=>{
            if(value.id==self.gameId){
              item = Object.assign({},value);
            }
          });
          this.changeMenu(item,false);
        }
      },
      changeSkin(color){
        this.$emit('changeSkin',color);
      },
      dragMenuTableShow(){
        this.$emit('dragMenuTableShow');
      },
      lotteryInsert(item) {
        let self = this;
        this.setPlayType(1);
        self.setWhetherSwitch(true);
        this.$router.push('/lottery/'+item.lotteryKey+'/');
        setTimeout(()=>{
          self.$emit('initialization',true);
        },500);

      },
      changeLottery(item) {
        this.lotteryInsert(item);
      },
      changeMenu(item,whetherSwitchFlag) {
        let self = this;
        self.setPlayType(1);
        self.setWhetherSwitch(whetherSwitchFlag);
        let newPageIndex = item.id.toString().substring(0,2);
        let flag = false;
        if(self.gameId.toString().substring(0,2).indexOf(newPageIndex)!=-1){
          flag = true;
        }
        if(flag){
          setTimeout(()=>{

            self.$emit('initialization',true);
            this.$router.push('/lottery/'+item.lotteryKey+'/');
          },300);
        }else{
          this.$router.push('/lottery/'+item.lotteryKey+'/');
        }

      },
      logout() {
        let self = this;
        self.$messageBox.close();
        self.$messageBox({$type:'confirm',message:'确认退出吗？',title:'提示',closeOnClickModal:false,showCancelButton:true}).then(action => {
          if (Object.is(action, 'confirm')) {
            this.$api.Member.logout().then(val => {
              if (val) {
                if (val.code === 10000) {
                  if(self.socket && self.socket.ws.readyState == 1) {
                    self.socket.send('{"code":"odds_unlottery"}');
                  }
                  window.location.href= '/';
                  this.setLogout();

                }
              }
            })
          }
        }).catch((err) => {
          if (Object.is(err, 'cancen')) {
            return;
          }
        }).finally(() => {

        });
      }
    },
    mounted() {

    },
    created(){

    }
  }
</script>

<style scoped>
  .header .logo{
    left: 20px;
    color: #fff;
    font-size: 25px;
    position: absolute;
    top: 13px;
  }
</style>

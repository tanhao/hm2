<template>
  <div id="main">
     <sideleft></sideleft>
      <div class="frame">
        <!--<Myframe></Myframe>-->
        <!--<router-link to="/bjpk10/">aba</router-link>-->
        <router-view @quickSum="quickSum" ref="lotteryPage" @bet="bet"></router-view>
        <Ranking v-show="playType==1"></Ranking>

        <div v-show="dragMenuTable" class="ui-dialog" style="position: absolute;width: 300px; right: 0;  z-index: 9999;top:0px;">
          <div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix ui-draggable-handle"><span
            class="ui-dialog-title">选择显示彩种</span>
            <button type="button" @click="hideDragMenuModel"
                    class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close"
                    role="button" title="Close"><span
              class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span class="ui-button-text">Close</span>
            </button>
          </div>
          <div id="lotteryChoose" class="ui-dialog-content ui-widget-content">
            <draggable element="ul" v-model="dragMenu" class="ui-sortable">

              <li v-for="(item,i) in dragMenu"><input type="checkbox" @change="dragMenuChange(item)" :value="item.display" v-model="item.disPlay"/>{{$t(item.lotteryKey)}}</li>
            </draggable>
            <p>注：可拖动彩种位置来改变彩种排序。</p></div>
          <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
            <div class="ui-dialog-buttonset">
              <button @click="decisionDragMenu" type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                      role="button">
                <span class="ui-button-text">确定</span>
              </button>
              <button @click="hideDragMenuModel" type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                      role="button">
                <span class="ui-button-text">取消</span>
              </button>
            </div>
          </div>
        </div>
        <div v-show="dragMenuTable" class="ui-widget-overlay ui-front" style="z-index: 100;"></div>
      </div>
  </div>
</template>

<script>
  import sideleft from '@/components/layout/sideleft'
  import Ranking from "@/components/layout/ranking";
  import draggable from 'vuedraggable'
  import {mapGetters,mapActions} from 'vuex'
  import to from "await-to-js";
    export default {
      components: {
        Ranking,
        sideleft,
        draggable

      },
      data(){
        return{
          dragMenuTable:false,
          disPlayList:[],
          dragMenuMoveList:[],
          lotteryPageFlag:false,
        }
      },
      mounted(){
        let self = this;
        self.setUPUN(null);
        self.setPlayType(1);

        self.init();


      },
      computed:{
        ...mapGetters(['playType','socket','gameMenu','game','member','betToken','gameId','gameInfo','betState','betGameNo','markets','quickSumList','selectList']),
        dragMenu:{
          get(){
            return this.gameMenu;
          },
          set(newVal){
            let self = this;
            self.dragMenuMoveList = Object.assign([],self.gameMenu);
            self.setGameMenu(newVal);

          }
        }
      },
      watch:{

      },
      methods:{
        ...mapActions(['setGames','setUPUN','setGameMenu', 'selectGame','setLatesNoteList','setBalances','setQuickSumList','setBetToken','setPlayType']),
        quickSum(){
          this.$emit('quickSum');
        },
        async init(){
          let self = this;
          let [err,data] = await to(this.$api.Lottery.getLotteryList());
          if(data.success){
            self.setGames(data.data);
            /*let gameInterval =setInterval(()=>{
              if(self.socket){
                self.selectGame(self.gameId);
                this.$refs.lotteryPage.initSelectList(true);
                clearInterval(gameInterval);
              }
            },100)*/
            if(this.game.lotteryKey){
              let item = {'index':this.game.lotteryId,'title':this.game.lotteryKey};
              this.$router.push('/lottery/'+this.game.lotteryKey+'/');
            }else{
              this.$router.push('/lottery/'+this.gameMenu[0].lotteryKey+'/');
            }

          }

        },
        dragMenuChange(item){
          let self = this;
          let searchIndex = self.disPlayList.findIndex((value,index,arr)=>{
            if(value.index==item.index){
              value.disPlay = item.disPlay;
            }
          })
          if(searchIndex==-1){
            self.disPlayList.push(Object.assign({},item));
          }
        },
        hideDragMenuModel(){
          let self = this;
          self.gameMenu.forEach(val=>{
            self.disPlayList.forEach(disPlayItem=>{
              if(disPlayItem.id==val.id){
                self.$set(val,"disPlay",!disPlayItem.disPlay);
              }
            })
          })
          let flag = true;
          if(self.dragMenuMoveList.length>0){
            for(let i =0;i<self.gameMenu.length;i++){
              if(self.gameMenu[i].id!=self.dragMenuMoveList[i].id){
                flag = false;
                break;
              }
            }
          }

          if(flag){
            self.setGames(self.gameMenu);
          }else{
            self.setGameMenu(self.dragMenuMoveList);
          }

          self.disPlayList = [];
          self.dragMenuTable = false;
        },
        bet(){
          this.$emit('bet');
        },
        decisionDragMenu(){
          let self = this;
          self.gameMenu.forEach(val=>{
            self.disPlayList.forEach(disPlayItem=>{
              if(disPlayItem.id==val.id){
                self.$set(val,"disPlay",disPlayItem.disPlay);
              }
            })
          })
          self.setGames(self.gameMenu);
          self.dragMenuMoveList = [];
          self.disPlayList = [];
          self.dragMenuTable = false;
        }
      }
    }
</script>

<style scoped>

</style>

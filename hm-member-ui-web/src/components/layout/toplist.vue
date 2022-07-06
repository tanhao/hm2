<template>
  <div class="menu2">
    <span style="cursor:pointer"><a  @click="goPage('/betList/')" >未结明细</a></span> |
    <span style="cursor:pointer"><a  @click="goPage('/betssettlement/')">今天已结</a></span> |
    <span style="cursor:pointer"><a  @click="goPage('/twoweekreport/')">两周报表</a></span> |
    <span style="cursor:pointer"><a  @click="goPage('/kjlist/')">开奖结果</a></span> <br>
    <span style="cursor:pointer"><a  @click="goPage('/userInfo/')">个人资讯</a></span> |
    <span style="cursor:pointer"><a  @click="goPage('/updatePassword/')">修改密码</a></span> |
    <span style="cursor:pointer"><a  @click="goPage('/helpPage/')">游戏规则</a></span> |
    <span style="cursor:pointer" id="skinPanel"><ul>
      <li class="red" :class="skinColor=='red'?'active':''"><i style="background:#dc2f39">
        <a @click="changeSkin('red')"></a></i></li><li class="blue" :class="skinColor=='blue'?'active':''"><i style="background:#5382bc"><a
      @click="changeSkin('blue')"></a></i></li><li class="orange" :class="skinColor=='orange'?'active':''"><i style="background:#d45000"><a
      @click="changeSkin('orange')"></a></i></li><li class="green" :class="skinColor=='green'?'active':''"><i style="background:#61a000"><a
      @click="changeSkin('green')"></a></i></li></ul></span>
  </div>
</template>

<script>
  import {mapGetters,mapActions} from 'vuex'
    export default {
        name: "toplist",
      data(){
          return{
          }
      },
      computed:{
        ...mapGetters(['skinColor','game'])
      },
      methods:{
        ...mapActions(['setPlayType','setSkinColor']),

          goPage(path){
            this.setPlayType(0);
            if(path=='/helpPage/'){
              this.$router.push({path:'/helpPage/',query:{lotteryKey:this.game.lotteryKey}});
            }else{
              this.$router.push(path);
            }

          },
        changeSkin(color){
          this.$emit('changeSkin',color);
          this.setSkinColor(color);
        }
      },
      mounted(){
        this.$nextTick(()=>{
          this.changeSkin(this.skinColor);
        })
      }
    }
</script>

<style scoped>

</style>

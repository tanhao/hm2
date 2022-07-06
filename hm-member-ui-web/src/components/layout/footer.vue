<template>
  <div id="footer">
    <div class="info">
      <marquee id="notices" scrollamount="2" onmouseover="this.stop()" onmouseout="this.start()">
        <template v-for="(item,index) in noticeList">
          <span v-if="new Date(item.startTime*1000) < new Date() && new Date(item.endTime*1000) > new Date()">
            {{item.content}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </span>
        </template>
      </marquee>
    </div>
    <a href="javascript:void(0)" @click="messageNotice" class="more">更多</a></div>

</template>

<script>
  import member from '@/axios/api-mem.js'
  export default {
    name: "Myfooter",
    data(){
      return{
        noticeList:[]
      }
    },
    methods: {
      messageNotice(){
        this.$emit('noticeModelShow',this.noticeList);
      }
    },
    mounted(){
      member.getNotice({}).then(val=>{
        this.noticeList = val.data.notices;
      });
    },
  }
</script>

<style scoped>

</style>

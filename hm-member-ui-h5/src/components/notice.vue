<template>
  <div class="foottxt">
    <marquee scrollamount="9" scrolldelay="160" onmouseover="this.stop()" onmouseout="this.start()" width="100%" class="idcgg">
      <template v-for="(item,index) in noticeList">
        <span v-if="new Date(item.startTime*1000) < new Date() && new Date(item.endTime*1000) > new Date()">
          {{item.content}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </span>
      </template>
    </marquee>
  </div>
</template>
<script>
  import member from '@/axios/api-mem.js'
  import Utils from '@/components/comm/Utils.js'
  export default {
    data(){
      return{
        noticeList:[]
      }
    },
    methods: {

    },
    mounted(){
      member.getNotice({}).then(val=>{
        this.noticeList = val.data.notices;
      });
    },
    filters:{

    }
  }
</script>
<style scoped>
  .idcgg {
    position: relative;
    top: 1px;
    color: #5d5d5d;
    font-size: 12px;
    text-shadow: 0 1px 0 #eee;
    font-weight: 700;
  }

</style>

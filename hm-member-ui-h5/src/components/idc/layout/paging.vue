<template>
    <div>
      <a href="javascript:void(0);" >共 {{transSum}} 期记录 共 {{total}} 页</a>
      <a href="javascript:void(0);" @click="prevPage"  >前一页</a>
      <span >『 <em style="color:blue;font-weight: bold;">{{curPage}}</em> 』</span>
      <a href="javascript:void(0);" @click="nextPage"  >后一页</a>

    </div>
</template>

<script>
    export default {
        name: "paging",
      props:[
        "pageSize","curPage","total","transSum"
      ],
      data(){
          return {
            showFirstPage:true,
            showLastPage:true,
            showPrevPage:true,
            showNextPage:true,
            rouNum:this.pageSize,
            gotoPage:''
          }
      },
      mounted() {
        this.$nextTick(() => { this.initPager() }) //需要延迟初始化
      },
      computed:{
          buttonShowFun(){
            this.showFirstPage = this.curPage> 1
            this.showLastPage = this.curPage< this.total
            this.showPrevPage = this.curPage> 1
            this.showNextPage = this.curPage< this.total
        }
      },
      methods:{
        nextPage(){//下一页
          if(this.curPage+1>this.total){
            return;
          }
          this.$emit('setPage', this.curPage+ 1 ) //调用父组件方法
        },
        prevPage(){//上一页
          if(this.curPage-1==0){
            return;
          }
          this.$emit('setPage', this.curPage- 1 ) //调用父组件方法
        },
        gotoNextPage(){//跳转页面
          if(this.gotoPage && /[1-9][0-9]*/.test(this.gotoPage)){
            var pg = parseInt(this.gotoPage)
            if(pg > 0 && pg <= this.pageSize){
              this.$emit('setPage', pg ) //调用父组件方法
            }else{
              this.gotoPage = ""
            }
          }else{
            this.gotoPage = ""
          }
        },
        firstPage(){
          this.$emit('setPage', 1 ) //调用父组件方法
        },
        lastPage(){
          this.$emit('setPage', this.total ) //调用父组件方法
        },
        rowNumChanged(){
          this.$emit('setRowNum', this.rowNum )
        },
        initPager(){
          this.showFirstPage = this.curPage> 1
          this.showLastPage = this.curPage< this.total
          this.showPrevPage = this.curPage> 1
          this.showNextPage = this.curPage< this.total
          this.gotoPage = ""
        },
      },
      watch:{
        'rowNum':'rowNumChanged' //监控rowNum
      }
    }
</script>

<style>
  .pager>span,.pager>a,.pager>select,.pager>div{
    float: left;
    margin-left: 10px;
  }

  .pager{
    width: 600px;
    margin: 0 auto;
    margin-top: 10px;
    height: 48px;
    line-height: 48px;
    margin-bottom: 40px;
  }

  .pager input,.pager select{
    height:40px;
    line-height:40px;
    outline:none;
    border:1px solid #888;
    padding:10px;
    box-sizing:border-box;
  }
  .pager input{
    width: 50px;
  }
  .pager .goto{
    margin-left: 20px;
  }
  .pager a{
    color: black;
    font-weight: bold;
    text-decoration: none;
  }
  .pager a:hover{
    color: green;
  }

</style>

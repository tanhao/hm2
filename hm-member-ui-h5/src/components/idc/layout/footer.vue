<template>
  <div class="guIOEz">

  </div>
</template>
<script>
  export default {
    data(){
      return {

      }
    },
    methods: {

    }
  }
</script>
<style scoped>
  .guIOEz {
    width: 100%;
    height: 50px;
    background-color: #fff;
    box-sizing: border-box;
    left: 0;
    bottom: 0;
    position: relative;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
  }
  .guIOEz:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    background-color: #ebebeb;
    width: 100%;
    height: 6px;
  }
  .guIOEz .shortcut.bet {
    background-image: url(../../images/touzhu.png);
    /*background-image: url(../../../assets/images/touzhu.png);*/
  }
  .guIOEz .shortcut.result {
    background-image: url(../../images/jilu.png);
  }
  .guIOEz .shortcut.stats {
    background-image: url(../../images/weijie.png);
  }
  .guIOEz .shortcut.deposit {
    background-image: url(../../images/yijie.png);
  }
  .guIOEz .shortcut.service {
    background-image: url(../../images/kefu.png);
  }
  .guIOEz .shortcut span {
    position: absolute;
    font-size: 0.625rem;
    left: 0;
    right: 0;
    bottom: 3px;
    text-align: center;
  }
  .guIOEz .shortcut {
    text-decoration: none;
    display: block;
    height: 46px;
    margin-top: 6px;
    box-sizing: border-box;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -webkit-box-flex: 1;
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    -webkit-transition: all 200ms ease-in-out;
    transition: all 200ms ease-in-out;
    background-size: auto 50%;
    background-position: center 10%;
    background-repeat: no-repeat;
    position: relative;
  }
</style>

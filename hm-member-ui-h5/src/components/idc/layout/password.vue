<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-bdVaJa jaFIbq otherpage">
          <my-header top="true" :back="resetPasswordStatus?true:false"  title="变更密码"></my-header>
          <div class="ui-content jqm_content">
            <div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset">
              <input type="password" id="oldPassword" ref="oldPassword" v-model="oldPassword" placeholder="旧密码">
            </div>
            <div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset">
              <input type="password" id="newPassword" ref="newPassword" v-model="newPassword" placeholder="新密码">
            </div>
            <div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset">
              <input type="password" id="confirmPassword" ref="confirmPassword" v-model="confirmPassword" placeholder="确认新密码">
            </div>
            <input  type="button" value="确认修改" @click="updatePwd"  class="btn_gong" style="margin-top:5px">
          </div>
        </div>
      </div>
      <notice></notice>
    </div>
  </div>
</template>


<script>
  import {mapGetters, mapActions} from 'vuex'
  import MyHeader from '@/components/idc/layout/header'
  import notice from '@/components/notice'
  import { formatDate } from '@/components/comm/date.js'
  import Member from '@/axios/api-mem.js'

  export default {
    components: {
      MyHeader,
      notice,
    },
    data() {
      return {
        oldPassword:'',
        newPassword:'',
        confirmPassword:'',
        resetUserName:null,
      }
    },
    computed: {
      ...mapGetters(['gameMenu','socket','resetPasswordStatus','showMenu','member','gameId']),
    },
    mounted(){
      if(this.resetPasswordStatus){

        this.resetUserName = this.$route.query.username;
        this.$messageBox({$type:'alert',message:this.resetPasswordStatus,title:'提示'});
      }
      this.$refs.oldPassword.focus();
      document.getElementById('oldPassword').focus();
    },
    methods:{
      ...mapActions(['setPromptInformation','setLogout']),
      updatePwd(){
        let self = this;
        if(!self.oldPassword){
          this.$refs.oldPassword.focus();
          document.getElementById('oldPassword').focus();
          return this.betError('原密码不能为空!');
        }else if(!self.newPassword){
          this.$refs.newPassword.focus();
          document.getElementById('newPassword').focus();
          return this.betError('新密码不能为空!');
        }else if(!self.confirmPassword){
          this.$refs.confirmPassword.focus();
          document.getElementById('confirmPassword').focus();
          return this.betError('请重复输入新密码以便确认!');
        }
        if(self.newPassword!=self.confirmPassword){
          this.$refs.newPassword.focus();
          document.getElementById('newPassword').focus();
          this.newPassword = '';
          this.confirmPassword = '';
          return this.betError('新密码与确认密码不一致!');
        }
        if(self.newPassword.length<6){
          this.$refs.newPassword.focus();
          document.getElementById('newPassword').focus();
          return this.betError('新密码长度不能少于6位!');
        }
        if(self.oldPassword == self.newPassword && self.oldPassword == self.confirmPassword){
          this.newPassword = '';
          this.confirmPassword = '';
          this.$refs.newPassword.focus();
          document.getElementById('newPassword').focus();
          return this.betError('输入的旧密码与新密码不能重复!');
        }
        self.$messageBox.close();
        self.$messageBox({$type:'confirm',message:'确认修改密码吗？',title:'提示',closeOnClickModal:false,showCancelButton:true}).then(action=>{
          if(Object.is(action,'confirm')){
            if(!self.resetUserName){
              Member.updatePassword({'oldPwd':self.oldPassword,'newPwd':self.confirmPassword}).then(val=>{
                if(val.code==10000){
                  self.$messageBox.close();
                  self.$messageBox({$type:'alert',message:val.message,title:'提示',closeOnClickModal:false});
                  Member.logout().then(()=>{
                    self.setLogout();
                    window.location.href='/';
                  });

                }else{
                  return this.betError(val.message);
                }
              });
            }else{
              Member.updatePwdNotoken({'oldPwd':self.oldPassword,'newPwd':self.confirmPassword,'username':self.resetUserName}).then(val=>{
                if(val.code==10000){
                  self.$messageBox.close();
                  self.$messageBox({$type:'alert',message:val.message,title:'提示',closeOnClickModal:false});
                  Member.logout().then(()=>{
                    self.setLogout();
                    window.location.href='/';
                  });

                }else{
                  return this.betError(val.message);
                }
              })
            }
          }
        }).catch(err=>{
          if(Object.is(err,'cancen')){

          }
        }).finally(()=>{
        });
      },
      betError(msg) {
        this.$toast({
          message: msg,
          iconClass: 'erroico',
          duration:1500
        })
      }
    },
  }
</script>

<style scoped>
  .otherpage {
    background: #fff !important;
    height: calc(100% - 4px) !important;
  }

  .jqm_content {
    padding: 0px;
  }

  .ui-content {
    padding: 5px;
    border-width: 0;
    overflow: visible;
    overflow-x: hidden;
  }
  .ui-body-inherit{
    background-color: #fff;
    border-color: #ddd;
    color: #333;
    text-shadow: 0 1px 0 #f3f3f3;
  }
  .ui-input-text{
    margin: .5em 0;
    border-width: 1px;
    border-style: solid;
  }
  .ui-shadow-inset {
    -webkit-box-shadow: inset 0 1px 3px rgba(0,0,0,.2);
    -moz-box-shadow: inset 0 1px 3px rgba(0,0,0,.2);
    box-shadow: inset 0 1px 3px rgba(0,0,0,.2);
  }
  .ui-corner-all {
    -webkit-background-clip: padding;
    background-clip: padding-box;
  }
  .ui-corner-all {
    -webkit-border-radius: .3125em;
    border-radius: .3125em;
  }
  .ui-input-text input {
    font-size: 1em !important;
    color: inherit;
    text-shadow: inherit;
    margin: 0;
    min-height: 2.2em;
    text-align: left;
    border: 0;
    background: transparent none;
    -webkit-appearance: none;
    -webkit-border-radius: inherit;
    border-radius: inherit;
  }
  .ui-input-text input, textarea.ui-input-text {
    padding: .4em;
    line-height: 1.4em;
    display: block;
    width: 100%;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    outline: 0;
  }
  .btn_gong {
    background-color: #C82834;
    height: 37px;
    width: 100%;
    color: #fff;
    border: none;
    border-radius: 5px !important;
    box-shadow: 4px 4px 3px #cccccc;
    font-size: 16px;
    cursor: pointer;
  }

</style>


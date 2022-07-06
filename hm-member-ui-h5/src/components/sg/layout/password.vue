<template>
  <div class="home">
    <div class="sc-bZQynM OQRyf">
      <my-header title="修改密码" :back="resetPasswordStatus?null:true" left="false"></my-header>
      <div class="sc-htoDjs UDzZc">
        <div class="ghAhQy">
          <form class="password-form component">
            <div class="cwGwpk">
              <div class="rnti_text ">
                <div class="rntit_count">旧密码</div>
                <input name="oldPassword" type="password"  id="oldPassword" ref="oldPassword" class="input" v-model="oldPassword" placeholder="请输入旧密码" autocomplete="off"
                       value="">
              </div>
            </div>
            <div class="cwGwpk">
              <div class="rnti_text ">
                <div class="rntit_count">新密码</div>
                <input name="newPassword" type="password" ref="newPassword" id="newPassword" v-model="newPassword" class="input" placeholder="6-20字符必须包含大小写和数字"
                       autocomplete="off" value="">
              </div>
            </div>
            <div class="cwGwpk">
              <div class="rnti_text ">
                <div class="rntit_count">确认密码</div>
                <input name="confirmPassword" type="password" ref="confirmPassword" id="confirmPassword" v-model="confirmPassword" class="input" placeholder="确认密码" autocomplete="off"
                       value="">
              </div>
            </div>
            <div class="jamQxo">
              <button type="button" @click="updatePwd">确定</button>
              <button type="button" @click="reset">重置</button>
            </div>
          </form>
        </div>

      </div>
    </div>
    <my-footer v-if="resetPasswordStatus?null:true"></my-footer>
  </div>
</template>
<script>

  import MyHeader from '@/components/sg/layout/header'
  import MyFooter from '@/components/sg/layout/footer'
  import {formatDate} from '@/components/comm/date.js'
  import {mapGetters, mapActions} from 'vuex'
  import Member from '@/axios/api-mem.js'

  export default {
    components: {
      MyHeader,
      MyFooter,
    },
    data() {
      return {
        oldPassword:'',
        newPassword:'',
        confirmPassword:'',
        resetUserName:null,
      }
    },
    filters: {},
    computed: {
      ...mapGetters(['gameMenu','socket','resetPasswordStatus', 'showMenu', 'gameId']),

  }
  ,

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
        if(Object.is(err,'cancel')){

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
    },
    reset(){
      this.oldPassword = '';
      this.newPassword = '';
      this.confirmPassword = '';
    }
  }
  ,
  mounted()
  {
    if(this.resetPasswordStatus){

      this.resetUserName = this.$route.query.username;
      this.$messageBox({$type:'alert',message:this.resetPasswordStatus,title:'提示'});
    }
    this.$refs.oldPassword.focus();
    document.getElementById('oldPassword').focus();
  }
  }
</script>
<style scoped>
  ::-webkit-input-placeholder { /* WebKit, Blink, Edge */
    font-size: 14px;
    color:    #dbdbdbd;
    opacity: .5;
  }
  :-moz-placeholder { /* Mozilla Firefox 4 to 18 */
    font-size: 14px;
    color:    #dbdbdbd;
    opacity: .5;
  }
  ::-moz-placeholder { /* Mozilla Firefox 19+ */
    font-size: 14px;
    color:    #dbdbdbd;
    opacity: .5;
  }
  :-ms-input-placeholder { /* Internet Explorer 10-11 */
    font-size: 14px;
    color:    #dbdbdbd;
    opacity: .5;
  }
  .UDzZc {
    height: calc(100% - 46px);
    position: relative;
  }
  .ghAhQy {
    width: 93%;
    position: relative;
    margin: 0px auto;
  }
  .ghAhQy .password-form {
    padding: 12px 0px;
  }
  .cwGwpk {
    padding: 8px 0px;
  }
  .cwGwpk .rnti_text {
    background-color: rgb(255, 255, 255);
    height: 19px;
    position: relative;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(204, 204, 206);
    border-image: initial;
    border-radius: 7px;
    padding: 15px 10px;
  }
  .cwGwpk .rnti_text .rntit_count {
    margin-top: -2px;
    color: rgb(153, 153, 153);
    font-size: 16px;
    margin-left: 5px;
    width: 75px;
    float: left;
    position: absolute;
  }
  .cwGwpk .rnti_text input {
    box-sizing: border-box;
    height: 39px;
    float: right;
    width: calc(80% - 2rem);
    font-size: 17px;
    color: rgb(32, 97, 179);
    outline: medium;
    margin: -10px 0px 0px;
    border-width: initial;
    border-style: none;
    border-color: initial;
    border-image: initial;
  }
  .jamQxo {
    display: flex;
    padding: 8px 0px;
  }
  .jamQxo > button:disabled {
    /*opacity: 0.5;*/
  }
  .jamQxo > button:first-child {
    margin-left: 0px;
  }
  .jamQxo > button {
    font-size: 16px;
  }
  .jamQxo > button {
    background-color: rgb(32, 97, 179);
    color: rgb(255, 255, 255);
    width: 100%;
    text-align: center;
    display: block;
    border-radius: 7px;
    border-width: initial;
    border-style: none;
    border-color: initial;
    border-image: initial;
    padding: 10px 0px;
    margin: 0px 8px;
  }
</style>


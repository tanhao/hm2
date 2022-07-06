<template>
  <div class="main">
    <div class="left"></div>
    <div class="right"></div>
    <div class="shu"></div>
    <div class="ji"></div>
    <div class="panel">
      <div class="login">
        <div class="home-header before-login">
          <div class="logo">
<!--            <img src="../assets/images/logo.png">-->
          </div>
        </div>
        <div class="login-wrapper">
          <div class="login-input">
            <div class="input-field">
              <label class="username">登录名</label>
              <input type="text" name="account" ref="input" v-model="user.username" value="" placeholder="请输入您的账号">
            </div>
            <div class="input-field" >
              <label class="password">密码</label>
              <input type="password" name="password" id = "inputPwd" ref="inputPwd" v-model="user.password" value="" placeholder="您的密码"  @keyup.enter="goLogin">
            </div>
          </div>
<!--          <button name="button" type="button" class="btn mob" @click="">-->
<!--            <span>手机版登入</span>-->
<!--          </button>-->
          <input type="hidden" name="act" value="login">
          <button name="button" type="button" id="loginBtn" class="btn outline" @click="goLogin()">
            <span v-if="!loading">{{$t('login')}}</span>
            <span v-else>登录...</span>
          </button>
        </div>
        </div>
      </div>
  </div>

</template>
<script>
  import axios from 'axios'
  import {mapActions, mapGetters} from 'vuex'

  export default {
    data() {
      return {
        getCheckCode: '',
        user: {
          username: '',
          password: '',
          loginMode: 'PC',
        },
        loading:false
      }
    },
    computed:{
      ...mapGetters(['promptInformation','updatepasswordBlackUserName','resetPasswordStatus','socket'])
    },
    methods: {
      ...mapActions(['setBetClear','setUPUN','setResetPasswordStatus','setGameClear','setMemberClear','setPromptInformation','setToken', 'setGames', 'selectGame', 'loginSuccess', 'setBalances', 'setLogout', 'setMarket']),
      gohome() {


      },
      refreshJpg() {
        let self = this;
        axios.get('/v1/auth/getCheckCode', {
          responseType: 'arraybuffer',
        }).then(res => {
          return 'data:image/png;base64,' + btoa(
            new Uint8Array(res.data)
              .reduce((data, byte) => data + String.fromCharCode(byte), '')
          );
        }).then(data => {
          self.getCheckCode = data //图片地址 <img src='data' />

        })
      },
      goLogin() {
        this.user.loginMode = 'PC';
        this.user.loginType = 'MEMBER';
        let self = this;
        let market = '';
        if(self.loading){
          return;
        }
        self.loading = true;
        this.$api.Member.login(this.user).then(resLogin => {

          /*console.log("AAAAAAAAAAAAAAAAAA")
          lottery.specialFormatList({lotteryId:100101}).then(res => {
            console.log("BBBBBBBBB",res)
          })*/


          if (resLogin.success) {
            self.setToken(resLogin.data.uid);
            self.loginSuccess(resLogin.data);
            self.setResetPasswordStatus(false);
            axios.all([self.$api.Member.balanceInfo() ]).then(axios.spread(function (resBalanceInfo) {
              self.setBalances(resBalanceInfo.data);


            }))
            this.$router.push('/homeNotice/');


          }else if(resLogin.code==23018 || resLogin.code==23019){
            self.setUPUN(self.user.username);
            self.setResetPasswordStatus(resLogin.message);
            this.$router.push({path:'/resetPassword/',query:{'username':self.user.username}});
            self.loading = false;
            return;
          } else {
            self.user.password = '';
            self.$refs.inputPwd.focus();
            document.getElementById('inputPwd').focus();
            self.loading = false;
          }

        }).catch(e => {
          console.log(e);
          self.loading = false;
        });
      }
    },
    mounted() {
      //this.refreshJpg();
      this.setLogout();
      this.setBetClear();
      this.setGameClear();
      this.setMemberClear();
      if(this.updatepasswordBlackUserName){
        this.user.username = this.updatepasswordBlackUserName;
        this.$refs.inputPwd.focus();
      }else{
        this.$refs.input.focus();
      }
    }
  }
</script>


<style scoped>
  .main {
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    position: fixed;
    position: fixed;
    display: -webkit-box;
    display: -ms-flexbox;
    display: -webkit-flex;
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    -webkit-justify-content: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    -webkit-align-items: center;
    align-items: center;
    /*background: url(../assets/images/shubg.jpg) top / cover no-repeat;*/
    background: linear-gradient(135deg, #132e7b, #00c9ca);

  }

  .before-login {
    margin: 0 auto;
    display: table;
    height: 147px;

  }

  .before-login .logo {
    display: none;
    margin-top: 20px;
    width: 100%;
    background-position: 50%;
    background-size: contain;
    background-repeat: no-repeat;
  }

  .before-login .logo img {
    margin: 0 auto;
    width: 180px;
  }

  .login-wrapper {
    vertical-align: middle;
  }

  .login-wrapper .input-field {
    border-radius: 2rem;
    height: 2.5rem;
    width: 100%;
    background-color: #fff;
    margin: 10px auto;
    position: relative;
  }

  .login-wrapper .input-field label {
    position: absolute;
    font-size: 0;
    width: 20px;
    height: 20px;
    left: 25px;
    top: 10px;
  }

  .login-wrapper .input-field label.username {
    background: url(../assets/images/louser.png) 50%/contain no-repeat;
  }

  .login-wrapper .input-field label.password {
    background: url(../assets/images/pwd.png) 50%/contain no-repeat;
  }

  .login-wrapper .input-field input {
    border-radius: 2rem;
    width: 100%;
    height: 100%;
    text-align: left;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding-left: 60px;
    background-color: transparent;
    outline: none;
    border: 0;
    font-size: 14px;
  }

  .login-wrapper .btn.outline {
    border: 1px solid #0792ae;
    font-size: 1rem;
  }

  .login-wrapper .btn.mob {
    color: #333;
    background-color: #e6e6e6;
    border-color: #adadad;
    font-size: .8125rem;
  }

  .login-wrapper .btn {
    height: 40px;
    margin: 10px auto;
    font-size: .8125rem;
    line-height: 40px;
    width: 100%;
    display: block;
    border-radius: 2rem;
    background-color: #13317c;
    border: 0;
    outline: none;
    color: #fff;
    height: 45px;
    margin: 20px auto;
    font-size: 1rem;
    font-weight: 700;
    text-align: center;
    line-height: 45px;
  }

  .login {
    margin: 0 auto;
    width: 100%;
  }

  .panel {
    margin: 0 100px 0 80px;
      height: 400px;
  }


</style>

<template>
  <div class="eGXSwp">
    <div class="home-header before-login">
      <div class="logo"></div>
    </div>
    <form id="loginForm" class="with-download">
      <div class="login-wrapper">
        <div class="login-input">
          <div class="input-field">
            <label class="username">{{$t('userName')}}</label>
            <input name="username" id="username" ref="inputName" v-model="user.username" type="text" :placeholder="$t('userName')"
                   value="">
          </div>
          <div class="input-field">
            <label class="password">{{$t('passWord')}}</label>
            <input name="passwd" id="passwd" ref="passwd" type="password" v-model="user.password" :placeholder="$t('passWord')"
                   value="" @keyup.enter="goLogin">
          </div>
        </div>
        <input type="hidden" name="act" value="login">
        <button name="button" type="button" id="loginBtn" class="btn outline" @click="goLogin()">
          <span v-if="!loading">{{$t('login')}}</span>
          <span v-else>登录...</span>
        </button>
      </div>
     </form>
      <!--<a class="footer-app-download" target="_blank" href="#">去桌面版</a>-->

    <div class="bb">
      <span class="mg5">状态</span>
      <span style="font-size: 12px;color: #f9e48e">{{user.State}}</span>
      <span class="mg5">网络</span>
      <span style="font-size: 12px;color: #f9e48e">{{user.mess}}</span>
      <span class="mg5">更新日期</span>
      <span style="font-size: 12px;color: #f9e48e;margin-right: 0px;">{{user.version}}</span>
    </div>
  </div>
</template>
<script>
  import {Toast} from 'mint-ui'
  import member from '@/axios/api-mem.js'
  import {mapActions, mapGetters} from 'vuex'
  import axios from 'axios'
  import io from 'socket.io-client'
  import {MessageBox} from 'mint-ui'

  history.pushState(null, null, document.URL);
  window.addEventListener('popstate', function () {
    history.pushState(null, null, document.URL);
  });
  export default {
    data() {
      return {
        user: {
          username: '',
          password: '',
          loginMode: 'H5',
          mess: 'Wifi网络',
          State: '连接成功',
          version: '2020.10.09'
        },
        loading:false
      }
    },
    computed: {
      ...mapGetters(['modeId','member','resetPasswordStatus']),
    },
    //这里所运行的只是运用到APP模式
    mounted: function () {
      let _this = this;
      this.socketConnect();
      document.addEventListener('online', function () {
        _this.user.State = "连接成功";
        _this.$router.push('/');
        if (navigator.connection.type == Connection.WIFI) {
          _this.user.mess = "Wifi网络";
        } else
        if (navigator.connection.type == Connection.CELL_4G) {
          _this.user.mess = "4G网络";
        }  else
        if (navigator.connection.type == Connection.CELL_3G) {
          _this.user.mess = "3G网络";
          let instance = Toast('您当前使用网络为3G，网络可能会有延时。');
          setTimeout(() => {
            instance.close();
          }, 8000);
        }
      }, false);
      //网络断开
      document.addEventListener('offline', function () {
        _this.user.State = "已经断开";
        _this.$router.push('/network/');
      }, false);
      document.addEventListener('deviceready', () => {
        let chcp = window.chcp;
        chcp.fetchUpdate(function(error, data) {
          if (!error) {
            //this.user.version = parseInt(this.user.version) + 1;
            MessageBox({
              title: '版本更新提示',
              message: '<p style="text-align: left">发现新的版本，本次更新修复已知BUG,忧化网络加载速度，正在下载新版本，请稍等</p>',
              showCancelButton: true,
              confirmButtonText: '立即更新',
              cancelButtonText: '不更新',
            }).then(action => {
              if (Object.is(action, 'confirm')) {
                let instance = Toast('正在升级，升级完毕应用将自动重启');
                setTimeout(() => {
                  instance.close();
                }, 8000);
                chcp.installUpdate(function (error) {
                  Toast({
                    message: '更新成功',
                    position: 'bottom',
                    duration: 8000
                  });
                })
              }
            })
          } else {
            Toast({
              message: '你当前是最新版本',
              position: 'bottom',
              duration: 5000
            });
          }
        })
      });
      this.setLogout();
      this.setBetClear();
      this.setMemberClear();
      this.setGameClear();
      this.$refs.inputName.focus();
    },
    methods: {
      ...mapActions(['setMemberClear','setSiteName','setResetPasswordStatus','setBetClear','setGameClear','setLogout','setPromptInformation','setToken', 'setGames', 'loginSuccess', 'setBalances', 'setLogout', 'setMarket']),

      socketConnect() {

      },
      goLogin() {
        this.user.loginMode = 'H5';
        this.user.loginType = 'MEMBER';
        let self = this;
        let market = '';
        if(self.loading){
          return;
        }
        self.loading = true;
        member.login(this.user).then(resLogin => {
          let loginModel = '';

          if (resLogin.success) {
            self.setToken(resLogin.data.uid);
            market = resLogin.data.market;
            loginModel = resLogin.data.marketSelect;
            if (null != resLogin.data.userId) {
              self.loginSuccess(resLogin.data);



                  axios.all([member.balanceInfo(), member.getLogos()]).then(axios.spread(function (resBalanceInfo, resLogos) {
                    if(resBalanceInfo.success){
                      self.setMarket(resBalanceInfo.data.market);
                      self.setBalances(resBalanceInfo.data);
                    }

                    if(resLogos.success){
                      self.setSiteName(resLogos.data);
                    }
                  })).finally(() => {

                    self.setResetPasswordStatus(false);
                    if (-1 != loginModel.indexOf('SG')) {
                      self.$router.push({path:'/sg/notice/'});
                      self.loading = false;
                    } else if (-1 != loginModel.indexOf('IDC')) {
                      self.$router.push({path:'/idc/notice/',query:{'username':self.user.username}});
                      self.loading = false;
                    }
                  });
            }
          }else if(resLogin.code==23018 || resLogin.code==23019){
            self.setResetPasswordStatus(resLogin.message);
            if (resLogin.code==23018) {
              self.$router.push({path:'/sg/password',query:{'username':self.user.username}});
              self.loading = false;

            } else if (resLogin.code==23019) {
              self.$router.push({path:'/idc/password',query:{'username':self.user.username}});
              self.loading = false;
            }
            return;
          } else {
            //this.setPromptInformation(resLogin.message);
            self.user.password = '';
            self.$refs.passwd.focus();
            document.getElementById('passwd').focus();
            self.loading = false;
          }

        }).catch(e=>{
          console.log(e);
         self.loading = false;
        });
      },
    }
  }
</script>
<style>
  .bb {
    position: absolute;
    height: 22px;
    /*background-color: #2e69a9;*/
    width: 100%;
    text-align: center;
    color: #fff;
    bottom: 8px;
    font-size: 12px;
    color: #fff;
  }

  .bb span {
    letter-spacing: 2px;
  }

  .nodis {
    display: none;
  }
</style>

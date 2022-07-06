<template>
  <!--    修改密码-->
  <div class="main password">
    <table class="table user_info pass_panel input_panel">
      <thead>
      <tr>
        <th colspan="2">修改密码</th>
      </tr>
      </thead>
      <form></form>
      <tbody>
      <tr>
        <th>旧密码</th>
        <td><input id="oldPassword" ref="oldPassword" v-model="oldPassword" type="password"></td>
      </tr>
      <tr>
        <th>新密码</th>
        <td><input id="newPassword" ref="newPassword" v-model="newPassword" type="password"></td>
      </tr>
      <tr>
        <th>确认密码</th>
        <td><input id="confirmPassword" ref="confirmPassword" v-model="confirmPassword" type="password"></td>
      </tr>
      </tbody>
    </table>
    <div class="control"><input type="button" class="button" value="确定" @click="updatePwd"> <input
      type="reset" class="button" value="重置" @click="reset()"></div>

  </div>
</template>

<script>
  import {formatDate} from '@/components/comm/date.js'
  import {mapGetters, mapActions} from 'vuex'
  import Member from '@/axios/api-mem'

  export default {
    name: "changePassword",
    data() {
      return {
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
        resetUserName:null,
      }
    },
    computed: {
      ...mapGetters(['gameMenu', 'socket', 'showMenu','updatepasswordBlackUserName','resetPasswordStatus', 'gameId']),

    },
    methods: {
      ...mapActions(['setPromptInformation', 'setLogout']),
      updatePwd() {
        let self = this;
        if (!self.oldPassword) {
          this.$refs.oldPassword.focus();
          document.getElementById('oldPassword').focus();
          return this.betError('原密码不能为空!');
        } else if (!self.newPassword) {
          this.$refs.newPassword.focus();
          document.getElementById('newPassword').focus();
          return this.betError('新密码不能为空!');
        } else if (!self.confirmPassword) {
          this.$refs.confirmPassword.focus();
          document.getElementById('confirmPassword').focus();
          return this.betError('请重复输入新密码以便确认!');
        }
        if (self.newPassword != self.confirmPassword) {
          this.$refs.newPassword.focus();
          document.getElementById('newPassword').focus();
          this.newPassword = '';
          this.confirmPassword = '';
          return this.betError('新密码与确认密码不一致!');
        }
        if (self.newPassword.length < 6) {
          this.$refs.newPassword.focus();
          document.getElementById('newPassword').focus();
          return this.betError('新密码长度不能少于6位!');
        }
        if (self.oldPassword == self.newPassword && self.oldPassword == self.confirmPassword) {
          this.newPassword = '';
          this.confirmPassword = '';
          this.$refs.newPassword.focus();
          document.getElementById('newPassword').focus();
          return this.betError('输入的旧密码与新密码不能重复!');
        }
        //self.$messageBox.close();
        self.$messageBox({
          $type: 'confirm',
          message: '确认修改密码吗？',
          title: '提示',
          closeOnClickModal: false,
          showCancelButton: true
        }).then(action => {
          if (Object.is(action, 'confirm')) {
            if(!self.resetUserName) {
              this.$api.Member.updatePassword({
                'oldPwd': self.oldPassword,
                'newPwd': self.confirmPassword
              }).then(val => {

                if (val.code == 10000) {
                  self.setPromptInformation(val.message);
                  Member.logout().then(() => {
                    self.setLogout();
                    window.location.href = '/';
                  });

                } else if (val.code == 23020) {
                  self.oldPassword = '';
                  self.$refs.oldPassword.focus();
                  document.getElementById('oldPassword').focus();
                } else if (val.code == 888888) {
                  self.newPassword = '';
                  self.$refs.newPassword.focus();
                  document.getElementById('newPassword').focus();
                }
              });
            }else{
              this.$api.Member.updatePwdNotoken({
                'oldPwd': self.oldPassword,
                'newPwd': self.confirmPassword,
                username: self.resetUserName
              }).then(val => {

                if (val.code == 10000) {
                  self.setPromptInformation(val.message);
                  Member.logout().then(() => {
                    self.setLogout();
                    window.location.href = '/';
                  });

                } else if (val.code == 23020) {
                  self.oldPassword = '';
                  self.$refs.oldPassword.focus();
                  document.getElementById('oldPassword').focus();
                } else if (val.code == 888888) {
                  self.newPassword = '';
                  self.$refs.newPassword.focus();
                  document.getElementById('newPassword').focus();
                }
              });
            }
          }
        }).catch(err => {
          if (Object.is(err, 'cancen')) {

          }
        }).finally(() => {
        });
      },
      betError(msg) {
        this.$toast({
          message: msg,
          iconClass: 'erroico',
          duration: 1500
        })
      },
      reset() {
        this.oldPassword = '';
        this.newPassword = '';
        this.confirmPassword = '';
      }
    },
    mounted() {
      let self = this;
      if(self.resetPasswordStatus){
        self.resetUserName = self.$route.query.username;
        self.setPromptInformation(self.resetPasswordStatus);
        setTimeout(()=>{
          self.$refs.oldPassword.focus();
          document.getElementById('oldPassword').focus();
        },2000)
      }

    }
  }
</script>

<style scoped>

</style>

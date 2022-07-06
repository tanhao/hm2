<template>
    <div>
        <div id="wrapper_div"></div>
        <div class="mainContainer">
            <div class="modal-content">
                <div class="brand"> <img src="../../assets/AdminDefaultTheme/logo.png"> </div>
                <div class="main-title">企业协同办公平台</div>
                <!-- <div class="sub-title"></div>-->
                <a-form
                        id="components-login"
                        :form="form"
                        layout='horizontal'
                        class="login-form"

                        :wrapper-col="{ span: 24 }"
                        @submit="handleSubmit"
                >
                    <a-form-item >
                        <a-input ref="input"
                                 v-decorator="[
                                  'username',
                                  { rules: [{ required: true, message: '请输入用户名!' }] },
                                ]"
                                 placeholder="登入名"
                        >
                            <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)"/>
                        </a-input>
                    </a-form-item>
                    <a-form-item >
                        <a-input ref="pwd"
                                 v-decorator="[
                              'password',
                              { rules: [{ required: true, message: '请输入密码!' }] },
                            ]"
                                 type="password"
                                 placeholder="密码"
                        >
                            <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)"/>
                        </a-input>
                    </a-form-item>
                    <div class="goto"></div>
                    <a-form-item :wrapper-col="{ span: 24, offset: 0 }">
                        <a-button html-type="submit" class="btn-primary ipt-b" :loading="iconLoading">
                            登　录
                        </a-button>
                    </a-form-item>
                </a-form>
                <!--<form class="loginForm">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="邮箱或用户名" name="email">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="登录密码" name="password">
                    </div>
                    <div class="third-wrapper clearfix"></div>
                    <button class="btn-primary btn-block" type="submit" name="loginsubmit" value="true">登 录</button>
                </form>-->
            </div>

        </div>
        <!--修改密码-->
        <a-modal width="500" v-model="modalpassword" title="修改用户密码" centered @ok="updatePwd">
            <div>
                <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                       style="border-collapse: separate;width: 100%">
                    <tbody>
                    <tr v-if="showTitle">
                        <td colspan="2">
                            <b class="red">密码14天已使用，为安全起见请更换密码</b>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">新设密码</td>
                        <td class="">
                            <a-input-password style="width: 200px" ref="newPwd" v-model="userPwd.newPwd"
                                              size="small"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="txtright">
                            <span class="red" style="font-size: 12px;"><i
                                color="red">*</i> [8-16]長度,最少二个字母(0-9,a-z,@)</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">确认密码</td>
                        <td>
                            <a-input-password placeholder="确认密码" ref="okPwd" @focus="focus($event)"
                                              v-model="userPwd.okPassword" size="small" style="width: 200px"/>
                        </td>
                    </tr>
                    </tbody>
                </table>

            </div>
        </a-modal>

    </div>


</template>

<script>
import {mapActions} from 'vuex'

export default {
    data() {
        return {
            showTitle: false,
            iconLoading: false,
            modalpassword: false,
            userPwd: {
                username:'',
                newPwd: "", //新密码
                okPassword: "", //确定新密码
                oldPwd: "",
            },
        };
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, {name: 'normal_login'});
    },
    methods: {
        ...mapActions(['setLoginSuccess']),
        handleSubmit(e) {
            e.preventDefault();
            if (this.iconLoading) {
                return;
            }
            this.form.validateFields((err, data) => {
                if (!err) {
                    this.iconLoading = true;
                    let params = data;
                    params.loginMode = "web";
                    params.loginType = "AGENT";
                    let oldPwd= params.password;
                    let username= params.username;
                    this.$api.user.login(params).then(res => {
                        if (res.success) {
                            this.setLoginSuccess(res.data);
                            this.$router.push("/home");
                        } else {
                            this.iconLoading = false;
                            this.form.setFieldsValue({password: '',});
                            if (res.code == 23017) {
                                this.showUpdatePwd(false,oldPwd,username);
                            } else if (res.code == 23016) {
                                //14天密码到期
                                this.showUpdatePwd(true,oldPwd,username);
                            } else {
                                this.$swal({
                                        title: res.message,
                                        icon: 'error',
                                        confirmButtonText: '确定',
                                        timer: 1000,
                                    }
                                );
                            }

                        }
                    }).catch(e => {
                        this.iconLoading = false;
                        this.$swal({
                                title: "登录失败，请稍后重试",
                                icon: 'error',
                                confirmButtonText: '确定',
                                timer: 1000,
                            }
                        )
                    })
                }
            });
        },
        showUpdatePwd(showTitle,oldPwd,username) {
            this.showTitle = showTitle;
            this.modalpassword = true;
            this.userPwd = {
                username,
                newPwd: "", //新密码
                okPassword: "", //确定新密码
                oldPwd
            }
        },
        updatePwd() {/*修改登入密码*/
            if (this.checkUser()) {
                let params = this.userPwd;
                this.$api.user.updatePwdNotoken(params).then((res) => {
                    if (res.success) {
                        this.modalpassword = false;
                        this.$refs.pwd.focus();
                        this.$utils.handleThen(res, this, "修改密码成功 请重新登入!", false);
                    } else {
                        if (res.code === 23030) {
                            this.userPwd.newPwd = "";
                            this.userPwd.okPassword = "";
                            this.$refs.newPwd.focus();
                        }
                        this.$utils.handleThen(res, this);
                    }
                    this.iconLoading = false;
                });
            }
            this.$refs.input.focus();
        },
        checkUser() {
            let checked = false;
            this.checkCallBack(() => {
                if (this.userPwd.newPwd === this.userPwd.oldPwd) {
                    throw new Error("新旧密码不能重复,请重新输入!");
                }
                if (this.userPwd.newPwd !== this.userPwd.okPassword) {
                    this.userPwd.newPwd = "";
                    this.userPwd.okPassword = "";
                    this.$refs.newPwd.focus();
                    throw new Error("两次输入的密码不一样,请重新输入!");
                }
                this.$utils.checkPassword(
                    this.userPwd.newPwd,
                    "密码格式不符合,请重新设置!"
                );
                checked = true;
            });
            return checked;
        },
    },
    mounted() {
        this.$refs.input.focus()
    },
};
</script>
<style>
#components-login {
    padding-top: 10px;
    margin: 0 auto;
    width: 90%;
}

.ant-form label {
    font-weight: 500;
}
</style>

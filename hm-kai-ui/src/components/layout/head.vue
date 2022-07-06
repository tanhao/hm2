<template>
    <div class="bgdiv">
        <div class="headboxf">
            <div class="headboxh">
                <div class="headboxhc">
                    <div class="headboxhr">
                        <ul>
<!--                            <li class=""> <a @click="showAlert" class="login">登录</a> <a @click="showAlert" class="register">注册</a>-->
<!--                                <div id="namevd"></div>-->
<!--                            </li>-->
<!--                            <li class="logonli liline">|</li>-->
                            <li class="logonli"> <a href="#" onclick="SetHome(window.location)">设为首页</a> </li>
                            <li class="logonli liline">|</li>
                            <li class="logonli"> <a href="#" onclick="javascript:addFavorite2()">收藏本站</a> </li>
                            <li class="logonli liline">|</li>
                            <li class="logonli"> <a @click="showAlert">帮助中心</a> </li>
                            <li class="logonli liline">|</li>
                            <li class="miniIphone"> <a @click="showAlert">手机版</a> </li>
                        </ul>
                    </div>
                    <div class="headboxhl" id="hotbox">
                        <div class="hoticon">最新公告：</div>
                        <div class="hotcontent">
                            <marquee direction="left" behavior="scroll" scrollamount="6" onmouseover="this.stop()"
                                     onmouseout="this.start()">
                                <span class="textspace" id="noticeContent">678导航站：https://www.678.com</span>
                            </marquee>
                        </div>
                    </div>
                </div>
            </div>
            <div class="headboxh2">
                <div class="headbox">
                    <div> <a class="logobox" alt="678彩票开奖网"></a> </div>
                    <div class="advertisebox" id="advertisebox">
                        <div> <a class="guanggao1" target="_blank" rel="nofollow"> <img src="@/assets/ads.jpg" alt=""> </a> </div>
                    </div>
                    <div class="loginbox" style="display: none">
                        <ul>
                            <li class="logonli jianyue">
                                <div class="new_mobiledetail"> <img src="@/assets/img/Revision_img/168phone.png" alt="">
                                    <p class="sweep">扫一扫，下载移动客户端</p>
                                    <p class="remind">（IOS、Android均可扫码下载）</p>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <MyMenu></MyMenu>
    </div>
</template>
<script>
    import MyMenu from '@/components/layout/menu'

    export default {
        data() {
            return {}
        },
        components: {
            MyMenu,
        },
        methods: {
            showAlert() {
                this.$swal({
                    position: 'center',
                    title: '功能正在开发中！',
                    icon: 'info',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '取消',
                    timer: 1000,
                })
            },
        },
    }
</script>
<style scoped>

</style>

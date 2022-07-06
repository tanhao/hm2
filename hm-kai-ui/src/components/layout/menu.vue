<template>
    <div class="menubannbox head">
        <div class="menubox">
            <div class="menuboxr">
                <ul>
                    <li class="li_checked nav_li"> <a href="#/">首页</a> </li>
                    <li class="cphost nav_li"> <a href="">彩票大厅</a>
                        <div class="cphost_content">
                            <div class="content_middle">
                                <div class="RMC">
                                    <div class="LineOne">
                                        <span class="RMC_span">
                                            <span>热门彩</span>
                                        </span>
                                        <ul class="RMC_List" >
<!--                                            <li> <a class="6hch" href="/MarkSix">香港彩</a> </li>-->
                                            <li v-for="(item,index) in hotLotteryList" @click="saveLotteryKeyFun(item.lotteryKey)">
                                                <a :class="item.lotteryKey" target="_blank" :href="item.groupId==100?'/#/pk10/':item.groupId==200?'/#/ssc/':item.groupId==300?'/#/klsf':''">
                                                    {{$t(item.lotteryKey)}}
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="GPC" >
                                    <div class="LineOne">
                                        <span class="RMC_span">
                                            <span>高频彩</span>
                                        </span>
                                        <ul class="RMC_List">
                                            <li v-for="(item,index) in lotteryList" @click="saveLotteryKeyFun(item.lotteryKey)" v-if="item.groupId==100">
                                                <a :class="item.lotteryKey" target="_blank" :href="item.groupId==100?'/#/pk10/':''">
                                                    {{$t(item.lotteryKey)}}
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="GPC" >
                                    <div class="LineOne">
                                        <span class="RMC_span">
                                            <span>时时彩</span>
                                        </span>
                                        <ul class="RMC_List">
                                            <li v-for="(item,index) in lotteryList" @click="saveLotteryKeyFun(item.lotteryKey)" v-if="item.groupId==200">
                                                <a :class="item.lotteryKey" target="_blank" :href="item.groupId==200?'/#/ssc/':''">
                                                    {{$t(item.lotteryKey)}}
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="GPC" >
                                    <div class="LineOne">
                                        <span class="RMC_span">
                                            <span>低频彩</span>
                                        </span>
                                        <ul class="RMC_List">
                                            <li v-for="(item,index) in lotteryList" @click="saveLotteryKeyFun(item.lotteryKey)" v-if="item.groupId==300">
                                                <a :class="item.lotteryKey" target="_blank" :href="item.groupId==300?'/#/klsf/':''">
                                                    {{$t(item.lotteryKey)}}
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                               <!-- <div class="JWC" style="display: none">
                                    <div class="LineOne"> <span class="JWC_span"> <span>境外彩</span> </span>
                                        <ul>
                                            <li> <a href="#">澳洲幸运5</a> </li>
                                            <li> <a href="#">澳洲幸运8</a> </li>
                                            <li> <a href="#">澳洲幸运10</a> </li>
                                            <li> <a href="#">澳洲幸运20</a> </li>
                                            <li> <a href="#">台湾宾果</a> </li>
                                            <li> <a href="#">极速赛车</a> </li>
                                            <li> <a href="#">极速时时彩</a> </li>
                                            <li> <a href="#">极速飞艇</a> </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="QGC" style="display: none">
                                    <div class="LineOne"> <span class="QGC_span"> <span>全国彩</span> </span>
                                        <ul>
                                            <li> <a href="#">双色球</a> </li>
                                            <li> <a href="#">福彩3D</a> </li>
                                            <li> <a href="#">七乐彩</a> </li>
                                            <li> <a href="#">大乐透</a> </li>
                                            <li> <a href="#">排列3</a> </li>
                                            <li> <a href="#">排列5</a> </li>
                                            <li> <a href="#">七星彩</a> </li>
                                        </ul>
                                    </div>
                                </div>-->
                            </div>
                        </div>
                    </li>
                    <li id="zhusaHao_li"> <a  @click="showAlert" id="zhusaHao"> 站长推荐 <img class="master_pic1" src="@/assets/img/Revision_img/cpdt_white.png" alt=""> <img class="master_pic2" src="@/assets/img/Revision_img/cpdt_red.png" alt=""> </a> <img src="@/assets/img/icon/news.png" alt="" id="zhusaHao_img">
                        <div class="zshContent" style="display: none">
                            <div class="zshMiddle">
                                <div class="lotteryType" id="lotteryType">
                                    <ul id="pk10Set">
                                        <li id="" class="disabled czName">
                                            <p><i></i>PK拾系列 </p>
                                        </li>
                                        <li class="pk10Bg check" data-text="pk10">北京PK拾 <img
                                            src="@/assets/img/zshImage/HOT.png" alt=""> </li>
                                        <li class="jisuscBg" data-text="jisusc">极速赛车 <img
                                            src="@/assets/img/zshImage/HOT.png" alt=""> </li>
                                        <li class="aozxy10Bg" data-text="aozxy10">澳洲幸运10</li>
                                        <li class="jsftBg" data-text="xingyft">极速飞艇 <img
                                            src="@/assets/img/zshImage/HOT.png" alt=""> </li>
                                    </ul>
                                    <ul id="sscSet">
                                        <li id="" class="disabled czName">
                                            <p><i></i>时时彩系列 </p>
                                        </li>
                                        <li class="cqsscNav" data-text="cqssc">重庆时时彩 <img
                                            src="@/assets/img/zshImage/HOT.png" alt=""> </li>
                                        <li class="tjsscNav" data-text="tjssc">天津时时彩</li>
                                        <li class="xjsscNav" data-text="xjssc">新疆时时彩 <img
                                            src="@/assets/img/zshImage/HOT.png" alt=""> </li>
                                        <li class="jisusscNav" data-text="jisussc">极速时时彩 <img
                                            src="@/assets/img/zshImage/HOT.png" alt=""> </li>
                                        <li class="aozxy5Nav" data-text="aozxy5">澳洲幸运5 <img
                                            src="@/assets/img/zshImage/HOT.png" alt=""> </li>
                                    </ul>
                                    <ul id="shiyx5Set">
                                        <li id="" class="disabled czName">
                                            <p><i></i>11选5系列 </p>
                                        </li>
                                        <li class="gdsyxwNav" data-text="gdsyxw">广东11选5</li>
                                        <li class="zjefNav" data-text="zjef">浙江11选5</li>
                                        <li class="jxefNav" data-text="jxef">江西11选5</li>
                                        <li class="jsefNav" data-text="jsef">江苏11选5</li>
                                        <li class="ahefNav" data-text="ahef">安徽11选5</li>
                                        <li class="shefNav" data-text="shef">上海11选5</li>
                                        <li class="lnefNav" data-text="lnef">辽宁11选5</li>
                                        <li class="hbefNav" data-text="hbef">湖北11选5</li>
                                        <li class="gxefNav" data-text="gxef">广西11选5</li>
                                        <li class="jlefNav" data-text="jlef">吉林11选5</li>
                                        <li class="nmgefNav" data-text="nmgef">内蒙古11选5</li>
                                        <li class="sdsyydjNav" data-text="sdsyydj">十一运夺金</li>
                                    </ul>
                                    <ul id="kuai3Set">
                                        <li id="" class="disabled czName">
                                            <p><i></i>快3系列 </p>
                                        </li>
                                        <li class="jsksanNav" data-text="jsksan">江苏快3</li>
                                        <li class="gxftNav" data-text="gxft">广西快3</li>
                                        <li class="jlftNav" data-text="jlft">吉林快3</li>
                                        <li class="hebftNav" data-text="hebft">河北快3</li>
                                        <li class="nmgftNav" data-text="nmgft">内蒙古快3</li>
                                        <li class="ahftNav" data-text="ahft">安徽快3</li>
                                        <li class="fjftNav" data-text="fjft">福建快3</li>
                                        <li class="hubftNav" data-text="hubft">湖北快3</li>
                                        <li class="bjftNav" data-text="bjft">北京快3</li>
                                    </ul>
                                    <ul id="otherSet">
                                        <li id="" class="disabled czName">
                                            <p><i></i>其他 </p>
                                        </li>
                                        <li class="egxy28Nav" data-text="egxy28">PC蛋蛋幸运28</li>
                                        <li class="gdklsfNav" data-text="gdklsf">广东快乐十分</li>
                                        <li class="aozxy8Nav" data-text="aozxy8">澳洲幸运8</li>
                                        <li class="tjklsfNav" data-text="tjklsf">天津快乐十分</li>
                                        <li class="cqxyncNav" data-text="cqxync">重庆幸运农场</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li id="new_zhao"> <a  @click="showAlert">两面推荐</a> <img src="@/assets/img/icon/news.png" alt="" id="ZSHao_img"> </li>
                    <li> <a  @click="showAlert">长龙提醒</a> </li>
                    <!--<li class="zstb_nav nav_li"> <a @click="showAlert">走势图表</a> </li>-->
<!--                    <li class="zixun_nav nav_li"> <a @click="showAlert">香港彩</a> </li>-->
<!--                    <li class="zixun_nav nav_li"> <a @click="showAlert">资讯</a> </li>-->
                    <li class="zixun_nav nav_li"> <a @click="showAlert">杀号计划 </a> </li>
                    <li class="zixun_nav nav_li"> <a @click="showAlert">购彩计划</a> </li>
                    <li class="lasli">
                        <div class="lasli_div">
                            <a id="msit" @click="showAlert"
                               class="phoneicon">简约版 </a>
                            <div class="mobilebox mobiledetailnew">
                                <div class="mobiledetail"> <img src="" alt=""/>
                                    <p class="sweep">扫一扫，下载移动客户端</p>
                                    <p class="remind">（IOS、Android均可扫码下载）</p>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>
<script>
    import {mapActions, mapGetters} from 'vuex'
    export default {
        data() {
            return {}
        },
        computed:{
            ...mapGetters(['lotteryList','hotLotteryList']),
        },
        methods: {
            ...mapActions(['setLotteryKey','setLotteryList','setPageTabSelect']),
            saveLotteryKeyFun(key){
                this.setLotteryKey(key);
            },
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
        method(){

        }
    }
</script>
<style scoped>

</style>

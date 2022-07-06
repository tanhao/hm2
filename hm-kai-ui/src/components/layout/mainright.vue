<template>
    <aside style="margin-top: 15px;">
        <div class="announce_down">
            <h2>推荐彩种
                <span clss="tjspan">RECOMMEND LOTTERY</span>
            </h2>
            <div class="tjlottey">
                <ul>
                    <template v-if="lotteryList && lotteryList.length>0" v-for="(item,index) in lotteryList">
                        <li @click="saveLotteryKeyFun(item.lotteryKey)">
                            <a :class="item.lotteryKey" target="_blank" :href="item.groupId==100?'/#/pk10/':item.groupId==200?'/#/ssc/':item.groupId==300?'/#/klsf':''"></a>
                            <a target="_blank" :href="item.groupId==100?'/#/pk10/':item.groupId==200?'/#/ssc/':item.groupId==300?'/#/klsf':''">{{$t(item.lotteryKey)}}</a>
                        </li>
                    </template>

                </ul>
                <span class="movedowm" style="display: none">
                    <span></span>
                </span>
            </div>
        </div>
        <div class="jiahu">
            <ul>
                <li>
                    <a href="/#/pk10gh/" class="pkxl">
                        <div class="bgdiv bigbg">
                            <div>
                                <h2>PK10系列</h2>
                                <p>
                                    <span>购彩计划</span>
                                    <span>稳赢杀号</span>
                                </p>
                            </div>
                        </div>
                    </a>
                </li>
                <!--<li class="havemargin">-->
                    <!--<a  href="/#/sscgh/"-->
                       <!--class="sscxl">-->
                        <!--<div class="bgdiv">-->
                            <!--<div>-->
                                <!--<h2>时时彩系列</h2>-->
                                <!--<p>-->
                                    <!--<span>购彩计划</span>-->
                                    <!--<span>稳赢杀号</span>-->
                                <!--</p>-->
                            <!--</div>-->
                        <!--</div>-->
                    <!--</a>-->
                <!--</li>-->
                <!--<li>-->
                    <!--<a @click="showAlert" class="pcddxl">-->
                        <!--<div class="bgdiv">-->
                            <!--<div>-->
                                <!--<h2>PC蛋蛋系列</h2>-->
                                <!--<p>-->
                                    <!--<span>购彩计划</span>-->
                                    <!--<span>稳赢杀号</span>-->
                                <!--</p>-->
                            <!--</div>-->
                        <!--</div>-->
                    <!--</a>-->
                <!--</li>-->
            </ul>
        </div>
        <div class="otherr" style="display: none">
            <div class="program">
                <dl>
                    <dt>方案预测</dt>
                    <dd>
                        <a href="/news/fanganyuce" target="_blank">更多</a>
                    </dd>
                </dl>
            </div>
            <div class="newslist">
                <ul id="fanganyc">
                    <li>
                        <a target="_blank" href="news/detail-9749.html">
                            幸运飞艇开户我们可以自由选择
                        </a>
                    </li>

                    <li>
                        <a target="_blank" href="news/detail-9748.html">
                            七星彩玩法技巧之分析大小比选号
                        </a>
                    </li>

                    <li>
                        <a target="_blank" href="news/detail-9747.html">
                            广东快乐十分技巧之欢乐五道菜
                        </a>
                    </li>

                    <li>
                        <a target="_blank" href="news/detail-9746.html">
                            江苏快3技巧之如何精准判断和值走势
                        </a>
                    </li>

                    <li>
                        <a target="_blank" href="news/detail-9732.html">
                            9月26日吉林快三号码分析
                        </a>
                    </li>

                    <li>
                        <a target="_blank" href="news/detail-9731.html">
                            9月26日浙江11选5号码分析
                        </a>
                    </li>

                    <li>
                        <a target="_blank" href="news/detail-9720.html">
                            学会忍是提升投注幸运飞艇的必备
                        </a>
                    </li>

                    <li>
                        <a target="_blank" href="news/detail-9719.html">
                            体彩七星彩玩法技巧之如何取舍号码
                        </a>
                    </li>

                    <li>
                        <a target="_blank" href="news/detail-9718.html">
                            江苏快3技巧之特殊形态下周期判断方法
                        </a>
                    </li>

                    <li>
                        <a target="_blank" href="news/detail-9716.html">
                            广东快乐十分：捕捉落号关注连号不容
                        </a>
                    </li>

                </ul>
                <!--                <ul id="fanganyc">正在加载...</ul>-->
            </div>
            <div class="adviertisement">
                <a href="#" target="_blank">
                    <img src="@/assets/img/adviertisement.png" alt="">
                </a>
            </div>
            <div class="tools">
                <div class="toolhead">
                    <dl>
                        <dt>分析工具</dt>
                    </dl>
                </div>
                <div class="toolsbox">
                    <div class="toolslist">
                        <div class="listhead">
                            <div class="toolsr1">
                                <div class="tooll">
                                    <img src="@/assets/img/index_r/beijinpk10.png">
                                </div>
                                <div class="toolr">北京pk10</div>
                            </div>
                            <div class="toolsr2">
                                <dl>
                                    <dt class="toolicon1"><i></i>走势
                                    </dt>
                                    <dd>
                                        <a href="#">更多</a>
                                    </dd>
                                </dl>
                            </div>
                            <div class="toolsr3">
                                <ul>
                                    <li>
                                        <a href="#">冠亚和走势</a>
                                    </li>
                                    <li>
                                        <a href="#">位置走势</a>
                                    </li>
                                    <li>
                                        <a href="#">号码走势</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="toolsr2 toolborder">
                                <dl>
                                    <dt class="toolicon1"><i></i>遗漏
                                    </dt>
                                    <dd>
                                        <a href="#">更多</a>
                                    </dd>
                                </dl>
                            </div>
                            <div class="toolsr3">
                                <ul>
                                    <li>
                                        <a href="#">冠亚和</a>
                                    </li>
                                    <li>
                                        <a href="#">冠军遗漏</a>
                                    </li>
                                    <li>
                                        <a
                                            href="#">亚军遗漏
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                            href="#">第三名遗漏
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                            href="#">第四名遗漏
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="toolslist">
                        <div class="listhead">
                            <div class="toolsr1">
                                <div class="tooll">
                                    <img src="@/assets/img/index_r/chongqingssc.png">
                                </div>
                                <div class="toolr">重庆时时彩</div>
                            </div>
                            <div class="toolsr2">
                                <dl>
                                    <dt class="toolicon1"><i></i>走势
                                    </dt>
                                    <dd>
                                        <a href="#">更多</a>
                                    </dd>
                                </dl>
                            </div>
                            <div class="toolsr3">
                                <ul>
                                    <li>
                                        <a
                                            href="#">基本走势
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                            href="#">龙虎走势
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                            href="#">形态走势
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="toolsr2 toolborder">
                                <dl>
                                    <dt class="toolicon1"><i></i>遗漏
                                    </dt>
                                    <dd>
                                        <a href="#">更多</a>
                                    </dd>
                                </dl>
                            </div>
                            <div class="toolsr3">
                                <ul>
                                    <li>
                                        <a
                                            href="#">综合分析
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                            href="#">定位走势
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                            href="#">第二球走势
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                            href="#">第三球走势
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                            href="#">第四球走势
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </aside>
</template>

<script>
    import {mapActions, mapGetters} from 'vuex'
    export default {
        name: "mainright",
        date(){
           return {

           }
        },
        computed:{
            ...mapGetters(['lotteryList','hotLotteryList']),
        },
        methods:{
            ...mapActions(['setLotteryKey','setLotteryList','setHotLotteryList']),
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
        mounted(){
            this.$api.Lottery.getLotList().then(val=>{
                this.setLotteryList(val.data);

                //热门彩排序
                let arr = [];
                for(let d of val.data){
                    if(d.hotChk==1){
                        arr.push(d)
                    }
                }
                const handle = (res) => {
                    return function(a,b){
                        const val1 = a[res];
                        const val2 = b[res];
                        return val1 - val2;
                    }
                }
                arr.sort(handle('hotSort'));
                this.setHotLotteryList(arr);
            });
        }
    }
</script>

<style scoped>

</style>

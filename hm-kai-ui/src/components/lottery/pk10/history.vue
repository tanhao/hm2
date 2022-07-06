<template>
    <div class="bodybox">
        <MyHeader></MyHeader>
        <!--主采种开始-->
        <PKtop></PKtop>
        <!--开奖历史记录-->
        <div class="zhlzbox margt20">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>开奖记录</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" :class="dayType==0?'checked':''"  @click="changeDate(0)">今天</span>
                        <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                        <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <datepicker v-model="dateStr" :format="customFormatter" :language="languages.zh"
                                        @selected="getHisList" name="uniquename"></datepicker>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="kaijiangjl zhlzbox margt0">
            <div class="listdiv">
                <div class="hmfb displayblock botborder">
                    <div class="head">
                        <ul class="zoushimap" id="chakanchfb">
                            <li class="kaijiltit">查看车号分布：</li>
                            <li class="01"> <a href="javascript:">号码1</a> <i></i> </li>
                            <li class="02"> <a href="javascript:">号码2</a> <i></i> </li>
                            <li class="03"> <a href="javascript:">号码3</a> <i></i> </li>
                            <li class="04"> <a href="javascript:">号码4</a> <i></i> </li>
                            <li class="05"> <a href="javascript:">号码5</a> <i></i> </li>
                            <li class="06"> <a href="javascript:">号码6</a> <i></i> </li>
                            <li class="07"> <a href="javascript:">号码7</a> <i></i> </li>
                            <li class="08"> <a href="javascript:">号码8</a> <i></i> </li>
                            <li class="09"> <a href="javascript:">号码9</a> <i></i> </li>
                            <li class="10"> <a href="javascript:">号码10</a> <i></i> </li>
                            <!--<li class="reset">
                                              还原
                                          </li>-->
                        </ul>
                    </div>
                    <div class="head head2">
                        <ul class="zoushimap" id="daxiaodsfb">
                            <li class="kaijiltit">大小单双分布：</li>
                            <li id="dannum"> <a href="javascript:">单</a> <i></i> </li>
                            <li id="shuangnum"> <a href="javascript:">双</a> <i></i> </li>
                            <li id="danum"> <a href="javascript:">大</a> <i></i> </li>
                            <li id="xiaonum"> <a href="javascript:">小</a> <i></i> </li>
                            <li id="duizinum"> <a href="javascript:">对子号</a> <i></i> </li>
                            <li class="reset"> 还原 </li>
                        </ul>
                    </div>
                </div>
                <div class="jrsmhmtj" id="jrsmhmtj">
                    <table id="jrsmhmtjTab" cellpadding="1" cellspacing="1" border="0"
                           style="background: rgb(212, 212, 212);">
                        <tbody>
                        <tr>
                            <th>时间</th>
                            <th>期数</th>
                            <th id="numberbtn" class="numberbtn"><span id="xshm" class="spanselect">显示号码</span><span
                                id="xsdx">显示大小</span><span id="xsds">显示单双</span></th>
                            <th colspan="3">冠亚和</th>
                            <th colspan="5">1-5龙虎</th>
                        </tr>
                        <tr v-for="item in hisList">
                            <td>{{item.actionTimeStr}}</td>
                            <td>{{item.gameNo}}</td>
                            <td>
                                <ul class="imgnumber" v-for="num in item.result">
                                    <li :class="'numsm'+num"><i>{{num}}</i></li>
                                </ul>
                            </td>
                            <td>{{item.gyh15lh.zh}}</td>
                            <td :style="item.gyh15lh.dx=='OVER'?'color:red':''">{{$t(item.gyh15lh.dx)}}</td>
                            <td :style="item.gyh15lh.ds=='EVEN'?'color:red':''">{{$t(item.gyh15lh.ds)}}</td>
                            <td :style="item.gyh15lh.lh1=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh1)}}</td>
                            <td :style="item.gyh15lh.lh2=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh2)}}</td>
                            <td :style="item.gyh15lh.lh3=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh3)}}</td>
                            <td :style="item.gyh15lh.lh4=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh4)}}</td>
                            <td :style="item.gyh15lh.lh5=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh5)}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <MyFoot></MyFoot>
    </div>
</template>
<script>
    import MyHeader from '@/components/layout/head'
    import MyFoot from '@/components/layout/foot'
    import PKtop from '@/components/lottery/pk10/top'
    import Datepicker from 'vuejs-datepicker';
    import {en, ja, zh} from 'vuejs-datepicker/dist/locale'
    import {mapActions, mapGetters} from 'vuex'
    export default {
        data() {
            return {
                jrsmList: [],
                gylhMap: {},
                hisList:[],
                dayType:0,
                dateStr:"",
                languages:{en: en,zh: zh,jp: ja},
                highlightedFn: {
                    customPredictor(date) {
                        if (date.getDate() === (new Date()).getDate()) {
                            return true;
                        }
                    }
                },
            }
        },
        components: {
            MyHeader,
            PKtop,
            MyFoot,
            Datepicker,
        },
        computed:{
            ...mapGetters(['lotteryKey','pageTabSelect'])
        },
        methods: {
            customFormatter(date) {
                return this.$moment(date).format('YYYY-MM-DD')
            },
            changeDate(type){
                this.dayType = type;
                let dateTime=new Date();
                dateTime=dateTime.setDate(dateTime.getDate()+type);
                dateTime=new Date(dateTime)
                this.dateStr =  this.$moment(dateTime).format('YYYY-MM-DD');
                this.getHisList();
            },
            getHisList() {
                this.$api.Lottery.getHisByDayList(this.lotteryKey+"/"+this.dateStr).then(val => {
                    this.hisList = [];
                    if (val.success) {
                        this.hisList = val.data;
                        this.hisList.forEach(items => {
                            if (items.result) {
                                let nums = items.result.split(",");
                                let map = [];
                                for (let i = 0; i < nums.length; i++) {
                                    map.push(nums[i])
                                }
                                items.result = map;
                                items.gyh15lh = this.computeGYH15LH(items.result);
                            }
                        })
                    }
                })
            },
        },
        mounted() {
            this.dateStr =  this.$moment(new Date()).format('YYYY-MM-DD')
            this.getHisList();
        }
    }
</script>
<style scoped>

</style>

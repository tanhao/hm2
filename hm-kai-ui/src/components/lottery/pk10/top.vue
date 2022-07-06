<template>
    <div>
        <div class="haomabox" :class="gameInfo.lotteryKey">
            <div class="waring" id="waringbox">
                <div class="flash"><i></i></div>
                温馨提示：因网络问题，开奖结果会有延迟，所以您需要去喝杯咖啡等一会儿！ </div>
                 <div v-if="gameInfo.lotteryKey" class="haomaqu" id="pk10">
                    <div class="haomaqu_resetPar">
                        <div class="haomaqul">
                            <div class="haomaline">
                                <div class="haomaimg"> <a :class="gameInfo.lotteryKey"></a> </div>
                                <div class="numberqu">
                                    <div class="nuberqutit"> <span class="pk10tit">{{$t(gameInfo.lotteryKey)}}</span>第 <span class="preDrawIssue">{{gameInfo.gameNo}}</span> 期开奖号码 </div>
                                    <div class="kajianhaoPar">
                                        <div class="kajianhao">
                                            <ul id="jnumber" class="numberbox">
                                                <template v-for="(num,i) in gameInfo.result">

                                                    <!--注意幸运农场的格式不一样 -->
<!--                                                    <li class="ncnum01"></li>-->
                                                    <li v-if="gameInfo.groupId==100" :class="'numsm'+num"></li>
                                                    <li v-if="gameInfo.groupId==200" class="numblueHead" style="padding-top: 0px; line-height: 36px;">{{num}}</li>
                                                    <li v-if="gameInfo.groupId==300 && gameInfo.lotteryKey!='xync'" :class="num>=19?'numblueHead numred':'numblueHead'" style="padding-top: 0px; line-height: 36px;">{{num}}</li>
                                                    <li v-if="gameInfo.lotteryKey=='xync'" :class="'ncnum'+num" style="padding-top: 0px; line-height: 36px;"></li>
                                                </template>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="line drawCountDiv">已开<span class="drawCount">{{gameInfo.gameCount}}</span>期，还有<span
                                        class="sdrawCount">{{gameInfo.nextRound}}</span>期 </div>
                                </div>
                            </div>
                            <div style="display: none;">
                                <table>
                                    <tbody>
                                    <tr class="longhu">
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td class="sumFS"></td>
                                        <td class="sumBigSamll"></td>
                                        <td class="sumSingleDouble"></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="haomaqur">
                            <div class="haomaqur_l">
                                <div class="line linetit">距<span class="nextIssue">{{gameInfo.nextGameNo}}</span>期开奖仅有</div>
                                <div class="line linetime" id="timebox">
                                    <div v-show="!gameInfo.isOpenStatus" class="opening opentyle" style="display: block;">开奖中...</div>
                                    <div v-show="gameInfo.isOpenStatus" class="cuttime" style="display: inline-block;"> <span class="bgtime hour" style="display: none;">00</span>
                                        <span class="hourtxt" style="display: none;">时</span>
                                        <span class="bgtime minute">{{returnLotteryTime(gameInfo).m}}</span> <span>分</span>
                                        <span class="bgtime second">{{returnLotteryTime(gameInfo).s1}}{{returnLotteryTime(gameInfo).s2}}</span> <span>秒</span> </div>
                                </div>
                            </div>
                            <div class="line soundId haomaqur_r">
                                <div @click="tabSelectFun" class="soundline ifSoundSet" id="ifSoundOpen">
                                    <div class="ifSoundOpen"><i>关闭声音</i>
                                        <div class="ifSoundIcon"></div>
                                    </div>
                                </div>
                                <!--关闭铃声-->
                                <div class="soundline soundSet" id="soundSet">
                                    <div @click="tabSelectFun" class="soundbtn" style="background: rgb(255, 123, 0);"><i>铃声设置</i>
                                        <div class="soundDefY" id="soundKindsIcon">
                                            <audio src="@/assets/media/RING_01.wav" controls="controls" id="audioid"> Your
                                                browser does not support the audio tag. </audio>
                                        </div>
                                        <div class="soundpanel">
                                            <div class="close"></div>
                                            <div class="soundlist">
                                                <ul>
                                                    <li>
                                                        <label>
                                                            <input type="radio" name="sound" value="RING_01"
                                                                   checked="checked">
                                                            &nbsp;默认(闹钟铃)</label>
                                                    </li>
                                                    <li>
                                                        <label>
                                                            <input type="radio" name="sound" value="RING_02">
                                                            &nbsp;声音二(上课铃)</label>
                                                    </li>
                                                    <li>
                                                        <label>
                                                            <input type="radio" name="sound" value="RING_03">
                                                            &nbsp;声音三(打锣声)</label>
                                                    </li>
                                                    <li>
                                                        <label>
                                                            <input type="radio" name="sound" value="RING_04">
                                                            &nbsp;声音四(打鼓声)</label>
                                                    </li>
                                                    <li>
                                                        <label>
                                                            <input type="radio" name="sound" value="RING_05">
                                                            &nbsp;声音五(点滴声)</label>
                                                    </li>
                                                    <li> 提示时间：
                                                        <select>
                                                            <option value="5" selected="selected">开奖前5秒</option>
                                                            <option value="20">开奖前20秒</option>
                                                            <option value="10">开奖前10秒</option>
                                                            <option value="30">开奖前30秒</option>
                                                            <option value="40">开奖前40秒</option>
                                                            <option value="50">开奖前50秒</option>
                                                            <option value="60">开奖前60秒</option>
                                                            <option value="begin">开奖后</option>
                                                        </select>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--设置铃声-->
                            </div>
                            <div @click="tabSelectFun" class="line margt20" id="startVideo"> <img src="@/assets/img/video/video.png"> </div>
                        </div>
                    </div>
            </div>
        </div>
        <div class="haomaqu_resetSon">
            <div class="homaline2">
                <div class="margt30 li_td">
                    <ul class="zoushimap" v-if="gameInfo.groupId=='100'">
                        <li class="list" @click="tabSelectFun('instantDraw')" :class="pageTabSelect=='instantDraw'?'checked':''"> <a style="cursor:pointer" >即时开奖</a> </li>
                        <li class="list" @click="tabSelectFun('roadA')" :class="pageTabSelect=='roadA'?'checked':''"> <a style="cursor:pointer" >路珠分析</a> </li>
                        <li class="list" @click="tabSelectFun('lrfx')" :class="pageTabSelect=='lrfx'?'checked':''"> <a style="cursor:pointer" >冷热分析</a> </li>
                        <!--<li class="list" @click="tabSelectFun('gyhT')" :class="pageTabSelect=='gyhT'?'checked':''"> <a style="cursor:pointer" >冠亚和走势</a> </li>
                        <li class="list" @click="tabSelectFun('positionT')" :class="pageTabSelect=='positionT'?'checked':''"> <a style="cursor:pointer" >位置走势</a> </li>-->
                        <li class="list" @click="tabSelectFun('dtRoad')" :class="pageTabSelect=='dtRoad'?'checked':''"> <a style="cursor:pointer" >龙虎路珠</a> </li>
                        <li class="list" @click="tabSelectFun('dsdxls')" :class="pageTabSelect=='dsdxls'?'checked':''"> <a style="cursor:pointer" >单双大小历史</a> </li>
                        <li class="list" @click="tabSelectFun('lhtj')" :class="pageTabSelect=='lhtj'?'checked':''"> <a style="cursor:pointer" >龙虎统计</a> </li>
                        <li class="list" @click="tabSelectFun('numberR')" :class="pageTabSelect=='numberR'?'checked':''"> <a style="cursor:pointer" >号码前后路珠</a> </li>
                        <li class="list" @click="tabSelectFun('lmsjtj')" :class="pageTabSelect=='doubleS'?'checked':''"> <a style="cursor:pointer" >两面统计</a> </li>
                        <li class="list" @click="tabSelectFun('gyhLZ')" :class="pageTabSelect=='gyhLZ'?'checked':''"> <a style="cursor:pointer" >冠亚和路珠</a> </li>
                        <li class="list" @click="tabSelectFun('sdRoad')" :class="pageTabSelect=='sdRoad'?'checked':''"> <a style="cursor:pointer" >单双大小路珠</a> </li>
                        <!--<li class="list" @click="tabSelectFun('numberRuleS')" :class="pageTabSelect=='numberRuleS'?'checked':''"> <a style="cursor:pointer" >号码规律统计</a> </li>-->
                        <li class="list" @click="tabSelectFun('jrhmtj')" :class="pageTabSelect=='jrhmtj'?'checked':''"> <a style="cursor:pointer" >今日号码统计</a> </li>
                        <!--<li class="list" @click="tabSelectFun('numberTrend')" :class="pageTabSelect=='numberTrend'?'checked':''"> <a style="cursor:pointer" >号码走势</a> </li>-->
                        <li class="list" @click="tabSelectFun('gyhlmls')" :class="pageTabSelect=='gyhlmls'?'checked':''"> <a style="cursor:pointer" >冠亚和两面历史</a> </li>
                         <!--<li class="list" @click="tabSelectFun('cltj')" :class="pageTabSelect=='cltj'?'checked':''"> <a style="cursor:pointer" >长龙统计</a> </li>-->
                        <!--<li class="list" @click="tabSelectFun('rulesPlay')" :class="pageTabSelect=='rulesPlay'?'checked':''"> <a style="cursor:pointer" >玩法规则</a> </li>-->
                    </ul>
                    <ul class="zoushimap" v-if="gameInfo.groupId=='200'">
                        <li class="list" @click="tabSelectFun('instantDraw')" :class="pageTabSelect=='instantDraw'?'checked':''"> <a style="cursor:pointer" >即时开奖</a> </li>
                        <li class="list" @click="tabSelectFun('roadA')" :class="pageTabSelect=='roadA'?'checked':''"> <a style="cursor:pointer" >路珠分析</a> </li>
                        <li class="list" @click="tabSelectFun('zhfx')" :class="pageTabSelect=='zhfx'?'checked':''"> <a style="cursor:pointer" >综合分析</a> </li>
                        <li class="list" @click="tabSelectFun('jbzs')" :class="pageTabSelect=='jbzs'?'checked':''"> <a style="cursor:pointer" >基本走势</a> </li>
                        <li class="list" @click="tabSelectFun('sdRoad')" :class="pageTabSelect=='sdRoad'?'checked':''"> <a style="cursor:pointer" >单双大小路珠</a> </li>
                        <li class="list" @click="tabSelectFun('noRoad')" :class="pageTabSelect=='noRoad'?'checked':''"> <a style="cursor:pointer" >号码路珠</a> </li>
                        <li class="list" @click="tabSelectFun('lrfx')" :class="pageTabSelect=='lrfx'?'checked':''"> <a style="cursor:pointer" >冷热分析</a> </li>
                        <!--<li class="list" @click="tabSelectFun('jrhmtj')" :class="pageTabSelect=='jrhmtj'?'checked':''"> <a style="cursor:pointer" >今日号码统计</a> </li>-->
                        <li class="list" @click="tabSelectFun('dsdxls')" :class="pageTabSelect=='dsdxls'?'checked':''"> <a style="cursor:pointer" >单双大小历史</a> </li>
                        <li class="list" @click="tabSelectFun('sumRoad')" :class="pageTabSelect=='sumRoad'?'checked':''"> <a style="cursor:pointer" >总和路珠</a> </li>
                        <li class="list" @click="tabSelectFun('hmlstj')" :class="pageTabSelect=='hmlstj'?'checked':''"> <a style="cursor:pointer" >号码历史统计</a> </li>
                        <li class="list" @click="tabSelectFun('lmsjtj')" :class="pageTabSelect=='lmsjtj'?'checked':''"> <a style="cursor:pointer" >两面统计</a> </li>

                    </ul>
                    <ul class="zoushimap" v-if="gameInfo.groupId=='300'">
                        <li class="list" @click="tabSelectFun('instantDraw')" :class="pageTabSelect=='instantDraw'?'checked':''"> <a style="cursor:pointer" >即时开奖</a> </li>
                        <li class="list" @click="tabSelectFun('roadA')" :class="pageTabSelect=='roadA'?'checked':''"> <a style="cursor:pointer" >路珠分析</a> </li>
                        <li class="list" @click="tabSelectFun('sumRoad')" :class="pageTabSelect=='sumRoad'?'checked':''"> <a style="cursor:pointer" >总和路珠</a> </li>
                        <li class="list" @click="tabSelectFun('zfbRoad')" :class="pageTabSelect=='zfbRoad'?'checked':''"> <a style="cursor:pointer" >中发白路珠</a> </li>
                        <li class="list" @click="tabSelectFun('noRoad')" :class="pageTabSelect=='noRoad'?'checked':''"> <a style="cursor:pointer" >号码路珠</a> </li>
                        <li class="list" @click="tabSelectFun('dsdxls')" :class="pageTabSelect=='dsdxls'?'checked':''"> <a style="cursor:pointer" >单双大小历史</a> </li>
                        <li class="list" @click="tabSelectFun('heoeRoad')" :class="pageTabSelect=='heoeRoad'?'checked':''"> <a style="cursor:pointer" >合数单双路珠</a> </li>
                        <li class="list" @click="tabSelectFun('wsouRoad')" :class="pageTabSelect=='wsouRoad'?'checked':''"> <a style="cursor:pointer" >尾数大小路珠</a> </li>
                        <li class="list" @click="tabSelectFun('dtRoad')" :class="pageTabSelect=='dtRoad'?'checked':''"> <a style="cursor:pointer" >龙虎路珠</a> </li>
                        <li class="list" @click="tabSelectFun('sdRoad')" :class="pageTabSelect=='sdRoad'?'checked':''"> <a style="cursor:pointer" >单双大小路珠</a> </li>
                        <li class="list" @click="tabSelectFun('jrhmtj')" :class="pageTabSelect=='jrhmtj'?'checked':''"> <a style="cursor:pointer" >今日号码统计</a> </li>
                        <li class="list" @click="tabSelectFun('fwRoad')" :class="pageTabSelect=='fwRoad'?'checked':''"> <a style="cursor:pointer" >东南西北路珠</a> </li>
                        <li class="list" @click="tabSelectFun('lmsjtj')" :class="pageTabSelect=='lmsjtj'?'checked':''"> <a style="cursor:pointer" >两面统计</a> </li>
                        <li class="list" @click="tabSelectFun('hmlstj')" :class="pageTabSelect=='hmlstj'?'checked':''"> <a style="cursor:pointer" >历史号码统计</a> </li>

                        <!--
                        <li class="list" @click="tabSelectFun('basicTrend')" :class="pageTabSelect=='basicTrend'?'checked':''"> <a style="cursor:pointer" >基本走势</a> </li>


                       <li class="list" @click="tabSelectFun('todayCLS')" :class="pageTabSelect=='todayCLS'?'checked':''"> <a style="cursor:pointer" >每日长龙统计</a> </li>
                        <li class="list" @click="tabSelectFun('todayCLS')" :class="pageTabSelect=='todayCLS'?'checked':''"> <a style="cursor:pointer" >每日长龙统计</a> </li>
                        <li class="list" @click="tabSelectFun('sizeTrend')" :class="pageTabSelect=='sizeTrend'?'checked':''"> <a style="cursor:pointer" >大小走势</a> </li>
                        <li class="list" @click="tabSelectFun('singleDoubleTrend')" :class="pageTabSelect=='singleDoubleTrend'?'checked':''"> <a style="cursor:pointer" >单双走势</a> </li>-->
                        <!--<li class="list" @click="tabSelectFun('rulesPlay')" :class="pageTabSelect=='rulesPlay'?'checked':''"> <a style="cursor:pointer" >玩法规则</a> </li>-->
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import {mapActions, mapGetters} from 'vuex'
    export default {
        name: "top",
        data(){
            return {
                lotteryRefreshTimer:null,
                serverTimer:null,
                serverTime:'',
                resultItems:[],
                gameInfo:{lotteryKey:null,groupId:null},

            }
        },
        beforeDestroy() {
            clearInterval(this.serverTimer);
            this.serverTimer = null;
            clearInterval(this.lotteryRefreshTimer);
            this.lotteryRefreshTimer = null;

        },
        destroyed() {
            clearInterval(this.serverTimer);
            this.serverTimer = null;
            clearInterval(this.lotteryRefreshTimer);
            this.lotteryRefreshTimer = null;

        },
        computed:{
            ...mapGetters(['lotteryKey','pageTabSelect']),
            returnLotteryTime(item){
                let self = this;
                return (item)=>{
                    let open_time = parseInt((new Date(item.nextActionTime).getTime()/1000) - self.serverTime/1000);
                    if(open_time>0){
                        if(this.lotteryRefreshTimer){
                            clearInterval(this.lotteryRefreshTimer);
                            this.lotteryRefreshTimer = null;
                        }
                        self.$set(item,"isOpenStatus",true);
                        clearInterval(item.isRefreshResult);
                        self.$set(item,'isRefreshResult',null);
                        let m = self.formatTime(parseInt(open_time / 60));
                        let s = self.formatTimeTwo(parseInt(open_time % 60));
                        let obj = {'m': m, 's1': s.charAt(1), 's2': s.charAt(2)};
                        return obj;
                    }else{
                        if(!this.lotteryRefreshTimer){
                            this.lotteryRefreshTimer = setInterval(()=>{
                                this.initLotterys();
                            },5000);
                        }


                        if(item.isOpenStatus){
                            item.isRefreshResult = setInterval(()=>{
                                item.result.sort(function(){
                                    return Math.random()-0.5
                                })
                            },100);
                        }
                        self.$set(item,"isOpenStatus",false);
                        return {'m':0,'s1':0,'s2':0};
                    }

                }
            }
        },
        methods:{
            ...mapActions(['setLotteryKey','setPageTabSelect']),
            tabSelectFun(title){
                if(title==this.pageTabSelect){
                    return;
                }
                this.setPageTabSelect(title);
                if(title=='dtRoad' || title=='gyhLZ' || title=='sdRoad' || title=='roadA' || title=='numberR'
                    || title=='sumRoad'  || title=='zfbRoad'  || title=='numRoad'  || title=='singleDoubleR'
                    || title=='mantissaSizeR'  || title=='allDirectionsR'

                    || title=='noRoad' || title=='sumRoad' || title=='zfbRoad' || title=='heoeRoad' || title=='wsouRoad' || title=='fwRoad'){
                    this.$emit('clearFilter');
                    this.$emit('luzhuActionFun',this.lotteryKey);
                }else if(title=='instantDraw'|| title=='zhfx'|| title=='lrfx'|| title=='dsdxls' || title=='jrhmtj' || title=='hmlstj'|| title=='lmsjtj'|| title=='gyhlmls'|| title=='lhtj'){
                    this.$emit('getHisList');
                }else{
                    this.$swal({
                        position: 'center',
                        title: '功能正在开发中！',
                        icon: 'info',
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: '取消',
                        timer: 1000,
                    });
                }
            },
            setTime(time) {
                let m = this.formatTime(parseInt(time / 60));
                let s = this.formatTimeTwo(parseInt(time % 60));
                let obj = {'m': m, 's1': s.charAt(1), 's2': s.charAt(2)};
                return obj;
            },
            formatTime(time) {
                if (time >= 100) {
                    return time + '';
                } else if (time >= 10) {
                    return '' + time;
                } else {
                    return '0' + time;
                }
            },
            formatTimeTwo(time) {
                if (time >= 10) {
                    return '0' + time;
                } else {
                    return '00' + time;
                }
            },
            closeAllTime() {
                clearInterval(this.serverTimer);
                this.serverTimer = null;
            },
            initLotterys(){
                let self = this;
                this.$api.Lottery.getLotteryNow(self.lotteryKey).then(val=>{
                    if(val.success){
                        if(val.data){
                            self.closeAllTime();
                            self.serverTime = val.serverTime;
                            self.serverTimer = setInterval(()=>{
                                self.serverTime += 1000;
                            },1000);
                            let resultArr = val.data.result.split(',');
                            if(val.data.groupId==300){

                                for(let i = 0;i<resultArr.length;i++){
                                    if(resultArr[i]<10){
                                        resultArr[i] = '0'+resultArr[i];
                                    }

                                }
                            }

                            val.data.result = resultArr;
                            if(self.gameInfo.lotteryKey){
                                self.$set(self.gameInfo,'isOpenStatus',true);

                                self.gameInfo.result = resultArr;
                                if(self.gameInfo.isRefreshResult){
                                    clearInterval(self.gameInfo.isRefreshResult);
                                    self.gameInfo.isRefreshResult = null;
                                }
                                //self.gameInfo.result = [];
                                if(val.data.gameNo > self.gameInfo.gameNo){
                                    if(self.pageTabSelect=='instantDraw'){
                                        setTimeout(()=>{
                                            self.$emit('getHisList');
                                        },1500);
                                    }else if(self.pageTabSelect=='dtRoad' || self.pageTabSelect=='gyhLZ' || self.pageTabSelect=='sdRoad'
                                        || self.pageTabSelect=='roadA' || self.pageTabSelect=='numberR'  || self.pageTabSelect=='sumRoad'
                                        || self.pageTabSelect=='noRoad' || self.pageTabSelect=='zfbRoad' || self.pageTabSelect=='heoeRoad'
                                        || self.pageTabSelect=='wsouRoad' || self.pageTabSelect=='fwRoad'){
                                        setTimeout(()=> {
                                            self.$emit('luzhuActionFun', this.lotteryKey);
                                        },1500);
                                    }
                                    Object.assign(self.gameInfo,val.data);
                                    let time_i = 100;
                                    self.$set(self.gameInfo,'result',[]);
                                    resultArr.forEach(val=>{
                                        setTimeout(()=>{
                                            self.gameInfo.result.push(val);
                                        },time_i);
                                        time_i+=100;
                                    })
                                    return;
                                }

                            }else{
                                self.gameInfo = val.data;
                            }
                        }
                    }
                }).finally(()=>{

                })
            }
        },
        mounted(){
            setTimeout(()=>{
                this.initLotterys();
            },300);
            clearInterval(this.lotteryRefreshTimer);
            this.lotteryRefreshTimer = null;
            this.lotteryRefreshTimer = setInterval(()=>{
                this.initLotterys();
            },5000);
        }
    }
</script>
<style scoped>

</style>



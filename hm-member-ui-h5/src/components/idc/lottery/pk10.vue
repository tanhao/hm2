<template>
    <div class="gm_main" id="idc">
        <div class="home">
            <div class="sc-bZQynM OQRyf">
                <my-header back="ture" @activetypeselect="selectActiveType" @refreshTime="initSelectList"></my-header>
                <div class="sc-bdVaJa jaFIbq">
                    <my-kj @clearSpecialSelect="initSelectList" ref="resetTime"></my-kj>
                    <!--主布局开始-->
                    <div class="sc-gzVnrw setbetright">
                        <div class="sc-htoDjs bTnXwf pk10">
                            <div :class="activeType==='lm'?'tab tab-active':'tab'">
                                <template v-for="(list, index) in pk10Odds.lm">
                                    <div class="bet-panel-wrapper wrap">
                                        <div class="tz_box pk10 sptz_box">
                                            <div class="bet-panel-header">
                                                <div class="title">{{$t(index)}}</div>
                                            </div>
                                            <ul v-show="!list.showHide">
                                                <div v-for="(item,key) in list">
                                                    <li>
                                                        <div :class="!pk10Odds.lm[index][key].choose?'wf_box':'wf_box bcn_back'"
                                                             v-tap="(e)=>selectOdds(pk10Odds.lm[index][key],e)">
                                                            <div class="wf_info">
                                                                <input class="chk" type="checkbox"
                                                                       style="display: none;">
                                                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
                                                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                                                            </div>
                                                            <div class="inp"><input type="text" maxlength="7"></div>
                                                        </div>
                                                    </li>
                                                </div>
                                            </ul>
                                        </div>
                                    </div>
                                </template>


                            </div>
                            <div :class="activeType==='gyh'?'tab tab-active':'tab'">
                                <template>
                                    <div class="bet-panel-wrapper wrap">
                                        <div class="tz_box pk10 sptz_box">
                                            <div class="bet-panel-header">
                                                <div class="title">{{$t('gyh')}}</div>
                                            </div>
                                            <ul v-show="gyhShowOrHide">
                                                <div v-for="(item,key) in pk10Odds.firstAddSecond">
                                                    <li>
                                                        <div :class="!pk10Odds.firstAddSecond[key].choose?'wf_box':'wf_box bcn_back'"
                                                             v-tap="(e)=>selectOdds(pk10Odds.firstAddSecond[key],e)">
                                                            <div class="wf_info">
                                                                <input class="chk" type="checkbox"
                                                                       style="display: none;">
                                                                <span class="qiu"><em>{{$t('gyh'+item.oddsKey)}}</em></span>
                                                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                                                            </div>
                                                            <div class="inp"><input type="text" maxlength="7"></div>
                                                        </div>
                                                    </li>
                                                </div>
                                            </ul>
                                        </div>
                                    </div>
                                </template>


                            </div>
                            <div :class="activeType==='yds'?'tab tab-active':'tab'">
                                <template v-for="(list, index) in pk10Odds.oneToFive">
                                    <div class="bet-panel-wrapper wrap">
                                        <div class="tz_box pk10 sptz_box">
                                            <div class="bet-panel-header">
                                                <div class="title">{{$t('ydw'+index)}}</div>
                                            </div>
                                            <ul v-show="!list.showHide">
                                                <div v-for="(item,key) in list">
                                                    <li v-if="item.categoryKey!='lm'">
                                                        <div :class="!pk10Odds.oneToFive[index][key].choose?'wf_box':'wf_box bcn_back'"
                                                             v-tap="(e)=>selectOdds(pk10Odds.oneToFive[index][key],e)">
                                                            <div class="wf_info">
                                                                <input class="chk" type="checkbox"
                                                                       style="display: none;">
                                                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
                                                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                                                            </div>
                                                            <div class="inp"><input type="text" maxlength="7"></div>
                                                        </div>
                                                    </li>
                                                </div>
                                                <div v-for="(item,key) in list">
                                                    <li v-if="item.categoryKey=='lm'">
                                                        <div :class="!pk10Odds.oneToFive[index][key].choose?'wf_box':'wf_box bcn_back'"
                                                             v-tap="(e)=>selectOdds(pk10Odds.oneToFive[index][key],e)">
                                                            <div class="wf_info">
                                                                <input class="chk" type="checkbox"
                                                                       style="display: none;">
                                                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
                                                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                                                            </div>
                                                            <div class="inp"><input type="text" maxlength="7"></div>
                                                        </div>
                                                    </li>
                                                </div>
                                                <li>
                                                    <div :class="!allSearchList[index]?'wf_box':'wf_box bcn_back'"
                                                         @click="allSearchFun(list,index)">
                                                        <div class="wf_info">
                                                            <input class="chk" type="checkbox" style="display: none;">
                                                            <span class="qiu">1~10</span>
                                                            <span class="odds">{{betState?'全选':'封盘'}}</span>
                                                        </div>
                                                        <div class="inp"><input type="text" maxlength="7"></div>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </template>
                                <template v-for="(list, index) in pk10Odds.sixToTen">
                                    <div class="bet-panel-wrapper wrap">
                                        <div class="tz_box pk10 sptz_box">
                                            <div class="bet-panel-header">
                                                <div class="title">{{$t('lds'+index)}}</div>
                                            </div>
                                            <ul v-show="!list.showHide">
                                                <div v-for="(item,key) in list">
                                                    <li v-if="item.categoryKey!='lm'">
                                                        <div>
                                                            <div :class="!pk10Odds.sixToTen[index][key].choose?'wf_box':'wf_box bcn_back'"
                                                                 v-tap="(e)=>selectOdds(pk10Odds.sixToTen[index][key],e)">
                                                                <div class="wf_info">
                                                                    <input class="chk" type="checkbox"
                                                                           style="display: none;">
                                                                    <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
                                                                    <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                                                                </div>
                                                                <div class="inp"><input type="text" maxlength="7"></div>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </div>
                                                <div v-for="(item,key) in list ">
                                                    <li v-if="item.categoryKey=='lm'">
                                                        <div :class="!pk10Odds.sixToTen[index][key].choose?'wf_box':'wf_box bcn_back'"
                                                             v-tap="(e)=>selectOdds(pk10Odds.sixToTen[index][key],e)">
                                                            <div class="wf_info">
                                                                <input class="chk" type="checkbox"
                                                                       style="display: none;">
                                                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
                                                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                                                            </div>
                                                            <div class="inp"><input type="text" maxlength="7"></div>
                                                        </div>
                                                    </li>
                                                </div>
                                                <li>
                                                    <div :class="!allSearchList[index+5]?'wf_box':'wf_box bcn_back'"
                                                         @click="allSearchFun(list,(index+5))">
                                                        <div class="wf_info">
                                                            <input class="chk" type="checkbox" style="display: none;">
                                                            <span class="qiu">1~10</span>
                                                            <span class="odds">{{betState?'全选':'封盘'}}</span>
                                                        </div>
                                                        <div class="inp"><input type="text" maxlength="7"></div>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </template>

                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <myendbet ref="betPageF" @clearSpecialSelect="clearSpecialSelect"></myendbet>
        </div>
    </div>
</template>
<script>
    import MyHeader from '@/components/idc/layout/header'
    import MyKj from '@/components/idc/layout/kj'
    import Myendbet from '@/components/idc/layout/footbet'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        components: {
            MyHeader,
            MyKj,
            Myendbet,
        },
        data() {
            return {
                //activeType: 'yds',
                showBetBox: false,
                gyhShowOrHide: true,
                leftMenu: [
                    {value: 'lm', title: '两面盘'},
                    {value: 'gyh', title: '冠亚和'},
                    {value: 'yds', title: '1~10名'},
                ],
                allSearchList: [false, false, false, false, false, false, false, false, false, false]

            }
        },
        computed: {
            ...mapGetters(['game', 'pk10Odds', 'betState', 'selectList', 'betGameNo', 'gameInfo', 'member', 'balances', 'gameId', 'categoryList', 'playType', 'userOddsCljps', 'userOddsCloses', 'userOddsJumps', 'userOddsNows', 'userOddss']),
            activeType: {
                get() {
                    return this.playType;
                },
                set(newVal) {
                    this.setPlayType(newVal);
                }

            },
            oddsReturn(odds) {
                let self = this;
                return (odds) => {
                    let {categoryId, oddsId} = odds;

                    let now = this.userOddsNows[oddsId];
                    let o1 = now ? this.userOddsNows[oddsId].reduce((pre, cur) => pre + cur, 0) : 0;
                    let jump = this.userOddsJumps[oddsId];
                    let o2 = jump ? this.userOddsJumps[oddsId].reduce((pre, cur) => pre + cur, 0) : 0;
                    let cljp = this.userOddsCljps[oddsId];
                    let o3 = cljp ? this.userOddsCljps[oddsId].reduce((pre, cur) => pre + cur, 0) : 0;
                    let close = this.userOddsCloses[oddsId];
                    let o4 = close ? this.userOddsCloses[oddsId].reduce((pre, cur) => pre + cur, 0) : 0;
                    let userOdds = this.userOddss[categoryId];
                    let o5 = userOdds ? this.userOddss[categoryId].reduce((pre, cur) => pre + cur, 0) : 0;
                    self.$set(odds, 'odds', Math.round((o1 + o2 + o3 + o4 + o5) * 100000) / 100000);
                    return Math.round((o1 + o2 + o3 + o4 + o5) * 100000) / 100000 > 0 ? Math.round((o1 + o2 + o3 + o4 + o5) * 100000) / 100000 : 0;
                }
            },
        },
        methods: {
            ...mapActions(['setSelectList', 'setSocketResetStatus', 'setBetGameNo', 'setPagePosition', 'setLotteryNow', 'setCurrentGame', 'setPlayType']),
            refreshTime(flag) {
                let self = this;

            },
            allSearchFun(item, index) {
                let self = this;
                if (!this.betState) {
                    return;
                }
                self.allSearchList[index] = !self.allSearchList[index];

                if (self.allSearchList[index]) {


                    item.forEach(val => {
                        if (val.categoryKey != 'lm' && val.status == '0') {

                            if (!val.check && !val.choose) {

                                self.selectOdds(val);
                            }
                        }
                    })
                } else {
                    item.forEach(val => {
                        if (val.categoryKey != 'lm' && val.status == '0') {
                            self.cancelChooseOdds(val);
                        }
                    })
                }

            },
            selectActiveType(type) {
                this.activeType = type;
                this.setPlayType(type);
                this.initSelectList();

            },
            initSelectList(flag) {
                let self = this;
                setTimeout(() => {
                    if (flag) {
                        //self.$refs.resetTime.closeAllTime();
                        self.setSocketResetStatus(false);
                        self.$refs.resetTime.infoObtain();
                        self.$refs.resetTime.fiveRecentResultShow = false;
                        self.$refs.betPageF.chip = '';
                        self.$refs.betPageF.pageAmount = '';
                        self.$refs.resetTime.fiveRecentResultList = [];
                    }
                }, 500)
                console.log('pk10', self.pk10Odds);
                for (let k in self.pk10Odds.lm) {
                    for (let index in self.pk10Odds.lm[k]) {
                        self.$delete(self.pk10Odds.lm[k][index], 'choose');
                        self.$delete(self.pk10Odds.lm[k][index], 'betAmt');
                    }
                }

                for (let k in self.pk10Odds.firstAddSecond) {
                    self.$delete(self.pk10Odds.firstAddSecond[k], 'choose');
                    self.$delete(self.pk10Odds.firstAddSecond[k], 'betAmt');
                }
                for (let k in self.pk10Odds.oneToFive) {
                    for (let index in self.pk10Odds.oneToFive[k]) {
                        self.$delete(self.pk10Odds.oneToFive[k][index], 'choose');
                        self.$delete(self.pk10Odds.oneToFive[k][index], 'betAmt');
                    }
                }
                for (let k in self.pk10Odds.sixToTen) {
                    for (let index in self.pk10Odds.sixToTen[k]) {
                        self.$delete(self.pk10Odds.sixToTen[k][index], 'choose');
                        self.$delete(self.pk10Odds.sixToTen[k][index], 'betAmt');
                    }
                }

                if (!self.pk10Odds) {
                    console.log('pk10Odds is undefined');
                    return;
                }
                if (!self.pk10Odds.lm) {
                    console.log('lm is undefined');
                    return;
                }
                if (!self.pk10Odds.firstAddSecond) {
                    console.log('firstAddSecond is undefined');
                    return;
                }
                if (!self.pk10Odds.oneToFive) {
                    console.log('oneToFive is undefined');
                    return;
                }
                if (!self.pk10Odds.sixToTen) {
                    console.log('sixToTen is undefined');
                    return;
                }


                this.allSearchList = [false, false, false, false, false, false, false, false, false, false];
                self.setSelectList(null);

            },
            showIsHide(list) {
                this.$set(list, 'showHide', !list.showHide);

            },
            selectOdds(item) {
                if (!this.betState || item.status == '1') {
                    return;
                }
                this.$set(item, 'choose', !item.choose);
                if (item.choose) {
                    this.chooseOdds(item);
                } else {
                    this.cancelChooseOdds(item);
                }

            },
            chooseOdds(item) {//选中陪率
                if (this.betGameNo !== this.gameInfo.gameNo) {
                    this.setBetGameNo(this.gameInfo.gameNo);
                }
                this.$set(item, 'choose', true);
                this.$set(item, 'check', true);
                item.choose = true;
                item.check = true;
                let index = this.findChoose(item);
                if (index === -1) {
                    this.setSelectList(Object.assign({}, item));
                }
            },
            findChoose(item) {//选取的陪率在列表中的位置
                return this.selectList.findIndex(function (value, index, arr) {
                    return value.oddsId === this.oddsId;
                }, {oddsId: item.oddsId});
            },
            cancelChooseOdds(item) {//取消选中陪率
                this.$set(item, 'choose', false);
                this.$set(item, 'check', false);
                this.$set(item, 'betAmt', "");
                item.choose = false;
                item.check = false;
                let index = this.findChoose(item);
                if (index !== -1) {
                    this.selectList.splice(index, 1);
                }
            },
            clearSpecialSelect(flag) {
                console.log('clearSelect');
                this.initSelectList(flag);

            }

        },
        mounted() {
            let self = this;
            self.initSelectList(true);
        }
    }
</script>
<style scoped>
    .gm_main .wrap .tz_box .wf_box {
        touch-action: manipulation !important;
        line-height: 30px;
        height: 30px;
        border: 1px solid #deaf85;
        margin: 2px 2px;
        padding: 0px;
    }

    .gm_main .wrap .tz_box .wf_box .odds {
        float: right;
        margin-right: 10px;
        font-size: 14px;
        color: red;
    }

    .gm_main .wrap .tz_box .wf_box .qiu {
        font-weight: 700;
        margin-left: 4px;
        float: left;
    }

    .wf_info .qiu {
        padding-bottom: 0px;
    }
</style>

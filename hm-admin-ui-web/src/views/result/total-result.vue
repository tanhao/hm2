<template>
    <div class="autofutllwith">
        <div class="df">
            <a-spin :spinning="spinning">
                <div class="df">
                    <div style="padding-right: 10px;width: 890px">
                        <div class="mb10 df">
                            <span class="maintxt mlr10">选择日期:</span>
                            <a-date-picker size="small" v-model="selectDay" :disabled-date="disabledDate"
                                           placeholder="选择日期" style="width: 120px" @change="changeDate"/>
                            <div class="pl10">
                                <a-button type="primary" icon="sync" size="small" @click="loadLottery()">
                                    刷新赛果({{refreshTime}})
                                </a-button>
                            </div>
                        </div>
                        <table class="tableborder leftlist" border="0" align="center" cellpadding="2" cellspacing="1">
                            <thead>
                            <tr>
                                <th width="80px">彩种</th>
                                <th width="50px">监控</th>
                                <th width="60px">未派彩</th>
                                <th width="90px">期数</th>
                                <th width="90px">开奖时间</th>
                                <th width="276px">开出号码</th>
                                <th width="40px">状态</th>
                                <th width="140px">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <template v-for="lottery in lotterys">
                                <tr v-for="(item,i) in totalList.get(lottery.lotteryKey)" >
                                    <td :rowspan="totalList.get(lottery.lotteryKey).length" v-if="i===0"
                                        class="forumrow">
                                        <a @click="showNoResult(lottery)">
                                            {{lottery.lotteryName}}
                                        </a>
                                    </td>
                                    <td :rowspan="totalList.get(lottery.lotteryKey).length" v-if="i===0"
                                        class="forumrowhighlight">
                                        <a-icon class="font18 mb10 green" type="bulb"
                                                :class="{'yellow':lottery.countNull===2,'red':lottery.countNull>2}"
                                                two-tone-color="#42ca01"/>
                                        <br/>
                                        <span>{{lottery.countNull}}/{{lottery.countTotal}}</span>
                                    </td>
                                    <td :rowspan="totalList.get(lottery.lotteryKey).length" v-if="i===0"
                                        class="forumrowhighlight">
                                        {{lottery.countWaiting}}
                                    </td>
                                    <td class="forumrowhighlight">{{item.gameNo}}</td>
                                    <td class="forumrowhighlight">
                                        {{moment(item.actionTime*1000).format('YYYY-MM-DD HH:mm:ss')}}
                                    </td>
                                    <td class="forumrowhighlight">
                                        <div class="innVmp">
                                            <div :class="lottery.lotteryKey"
                                                 style="margin: 0px auto; display: inherit;">
                                                <div :class="'n_'+parseInt(num)" v-for="num in item.numbers">
                                                    {{num}}
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="forumrowhighlight" :class="item.status==='WAITING'?'red':'green'">
                                        {{$t(item.status)}}
                                    </td>
                                    <td class="forumrowhighlight">
                                        <template v-if="lottery.isZkc===false">
                                            <a-button class="mr5" type="primary" size="small"
                                                      v-show="item.status==='WAITING' && item.result===null"
                                                      @click="showAction(lottery,item,1)">手动
                                            </a-button>
                                        </template>
                                        <template v-else>
                                            <template v-if="username==='jin' || username==='hm88'">
                                            <a-button class="mr5" type="primary" size="small"
                                                      v-show="item.status==='WAITING' && item.result===null"
                                                      @click="showActionZkc(lottery,item)">手动
                                            </a-button>
                                            </template>
                                        </template>
                                        <a-button class="mr5" type="primary" size="small"
                                                  v-show="item.status==='WAITING' && item.result!==null"
                                                  @click="savePayoutResult(item)">派彩
                                        </a-button>
                                        <a-button class="mr5 green" size="small" v-show="item.status==='WAITING'"
                                                  @click="saveCancelResult(item)">撤单
                                        </a-button>
                                        <!-- <a-button type="danger" size="small"  v-show="item.status==='DIVIDEND'"
                                                   @click="showAction(lottery,item,2)">重派
                                         </a-button>-->
                                    </td>
                                </tr>
                            </template>
                            </tbody>
                        </table>
                    </div>
                    <div style="padding-right: 2px">
                        <a-spin :spinning="noSpinning">
                            <div v-show="noResultList.length>0">
                                <div class="mb10 df">
                                    <a-button type="primary" icon="sync" size="small"
                                              @click="showNoResult(selectLottery)">
                                        当天全部
                                    </a-button>
                                </div>
                                <table class="tableborder leftlist" border="0" align="center" cellpadding="2"
                                       cellspacing="1">
                                    <thead>
                                    <tr>
                                        <th colspan="5">{{selectLottery.lotteryName}} - <span class="red">当天全部</span>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th width="90px">期数</th>
                                        <th width="90px">开奖时间</th>
                                        <th width="276px">开出号码</th>
                                        <th width="40px">状态</th>
                                        <th width="140px">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="item in noResultList">
                                        <td class="forumrow">{{item.gameNo}}</td>
                                        <td class="forumrowhighlight">
                                            {{moment(item.actionTime*1000).format('YYYY-MM-DD HH:mm:ss')}}
                                        </td>
                                        <td class="forumrowhighlight">

                                            <div class="innVmp">
                                                <div :class="item.lotteryKey"
                                                     style="margin: 0px auto; display: inherit;">
                                                    <div :class="'n_'+parseInt(num)" v-for="num in item.numbers">
                                                        {{num}}
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="forumrowhighlight" :class="item.status==='WAITING'?'red':'green'">
                                            {{$t(item.status)}}
                                        </td>
                                        <td class="forumrowhighlight">
                                            <template v-if="selectLottery.isZkc===false">
                                                <a-button class="mr5" type="primary" size="small"
                                                          v-show="item.status==='WAITING' && item.result===null"
                                                          @click="showAction(selectLottery,item,1)">手动
                                                </a-button>
                                            </template>
                                            <template v-else>
                                                <template v-if="username==='jin' || username==='hm88'">
                                                    <a-button class="mr5" type="primary" size="small"
                                                              v-show="item.status==='WAITING'  "
                                                              @click="showActionZkc(selectLottery,item)">手动
                                                    </a-button>
                                                </template>
                                            </template>
                                            <a-button class="mr5" type="primary" size="small"
                                                      v-show="item.status==='WAITING' && item.result!==null"
                                                      @click="savePayoutResult(item)">派彩
                                            </a-button>
                                            <a-button class="mr5 green" size="small" v-show="item.status==='WAITING'"
                                                      @click="saveCancelResult(item)">撤单
                                            </a-button>
                                            <!-- <a-button class="mr5 green" size="small" v-show="item.status==='DIVIDEND'"
                                                       @click="saveCancelResult(item)">取消
                                             </a-button>-->
                                            <!--<a-button type="danger" size="small"  v-show="item.status==='DIVIDEND'"
                                                      @click="saveRePayoutResult(item)">重派
                                            </a-button>-->
                                            <template>
                                            <a-button type="danger" size="small"
                                                      @click="showAction(selectLottery,item,2)">重派
                                            </a-button>
                                            </template>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </a-spin>
                    </div>
                </div>
                <!--手动开奖-->
                <a-modal
                        width="auto"
                        v-model="actionModel"
                        :title="`${title}：${$t(actionParams.lotteryKey)} 第${actionParams.gameNo}期`"
                        centered
                        :confirmLoading="actionLoading"
                        @ok="saveAction()"
                >
                    <a-spin :spinning="actionLoading">
                        <div>
                            <div>
                                <a-checkbox :checked="actionType==='OLD'" @click="actionType='OLD'"
                                            v-show="type===1"></a-checkbox>
                                <span class="font14 mr10" v-if="type===1">开奖号码:</span>
                                <span class="font14 mr10" v-else>已派结果:</span>
                                <template v-for="i in actionMaxSize">

                                    <a-input v-model="actionNumbers[i-1]" size="small" :disabled="type===2"
                                             oninput="if(value.length>2)value=value.slice(0,2)" class="mr10"
                                             style="width: 35px"/>
                                </template>
                            </div>
                            <div class="mt16" v-if="actionGainNum.length>0 ">
                                <a-checkbox :checked="actionType==='NEW'" @click="actionType='NEW'"
                                            v-show="type===1"></a-checkbox>
                                <span class="font14 mr10" v-if="type===1">重新获取:</span>
                                <span class="font14 mr10" v-else>重派结果:</span>
                                <template v-for="i in actionMaxSize">
                                    <a-input v-model="actionGainNum[i-1]" size="small"
                                             oninput="if(value.length>2)value=value.slice(0,2)" class="mr10"
                                             style="width: 35px"/>
                                </template>
                            </div>
                            <div v-if="actionMessage" class="mt16">
                                <span class="font14 mr10 red">{{actionMessage}}</span>
                            </div>
                            <div class="mt16" v-if="type===1">
                                <span class="red" style="font-size: 12px;"><i>注释：</i> 手动开奖设置值[{{actionCodeNums}}]</span>
                            </div>
                            <div class="mt16" v-if="type===1">
                                <a-checkbox v-model="actionParams.autoPayout">
                                    <span class="red">开奖后自动派彩</span>
                                </a-checkbox>
                            </div>
                        </div>
                    </a-spin>
                </a-modal>

                <!--手动开奖 自开彩-->
                <a-modal
                        width="auto"
                        v-model="actionModelZkc"
                        :title="`${title}：${$t(actionParams.lotteryKey)} 第${actionParams.gameNo}期`"
                        centered
                        :confirmLoading="actionLoading"
                        @ok="saveActionZkc()"
                >
                    <a-spin :spinning="actionLoading">
                        <div>
                            <div>
                                <a-checkbox :checked="actionType==='OLD'" @click="actionType='OLD'">
                                    <span class="font14 mr10">手动:</span>
                                </a-checkbox>
                                <template v-for="i in actionMaxSize">
                                    <a-input v-model="actionNumbers[i-1]" size="small"
                                             oninput="if(value.length>2)value=value.slice(0,2)" class="mr10"
                                             style="width: 35px"/>
                                </template>
                            </div>
                            <div class="mt16" v-for="(item,y) in resultZkc">
                                <a-checkbox :checked="actionType===''+y" @click="actionType=''+y">
                                    <span class="font14 mr10">机选{{y+1}}</span>
                                </a-checkbox>
                                <template v-for="i in actionMaxSize">
                                    <a-input v-model="item.resultStr[i-1]" size="small" disabled
                                             oninput="if(value.length>2)value=value.slice(',')" class="mr10"
                                             style="width: 35px"/>
                                </template>
                                <span :class="$utils.getColorCssG(item.winAmt)">输赢:{{$utils.getAnsZ(item.winAmt||0)}}</span>
                                <span :class="$utils.getColorCssG(item.commAmt)"> 退水:{{$utils.getAnsZ(item.commAmt||0)}}</span>
                                <span :class="$utils.getColorCssG(item.commAmt+item.winAmt)"> 合总:{{ Number($utils.getAnsZ(item.winAmt||0) + $utils.getAnsZ(item.commAmt||0))}}</span>

                            </div>
                            <div class="mt16">
                                <span class="red" style="font-size: 12px;"><i>注释：</i> 手动开奖设置值[{{actionCodeNums}}]</span>
                            </div>
                            <div class="mt16">
                                <a-checkbox v-model="actionParams.autoPayout">
                                    <span class="red">开奖后自动派彩</span>
                                </a-checkbox>
                            </div>
                        </div>
                    </a-spin>
                </a-modal>
            </a-spin>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                spinning: false,
                showNo: false,
                lotterys: [],
                selectDay: this.todayDate(),
                totalList: new Map(),
                countList: [],
                refreshTime: 10,
                timer: null,
                title: "",
                type: 1,
                actionType: "OLD",
                actionMaxSize: 10,
                actionLoading: false,
                actionModel: false,
                actionModelZkc: false,
                actionNumbers: [],
                actionGainNum: [],
                actionMessage: '',
                actionCodeNums: '',
                actionParams: {
                    gameNo: null,
                    result: null,
                    lotteryKey: null,
                    autoPayout: true,
                },
                noSpinning: false,
                selectLottery: {},
                noResultList: [],
                result: "",
                resultZkc: [],
                username: this.$store.state.user.info.username,
            };
        },
        computed:{

        },
        methods: {
            groupClass(groupId){
                if(groupId===100) return 'pk10';
                if(groupId===200) return 'ssc';
                if(groupId===300) return 'klsf';
                if(groupId===400) return 'kl8';
                return "";
            },
            disabledDate(current) {
                return current && current > this.moment().endOf('day');
            },
            changeDate(v) {
                if (v.format('YYYY-MM-DD') === this.todayDate().format('YYYY-MM-DD')) {
                    this.startCountdown();
                } else {
                    clearInterval(this.timer);
                }
                this.loadLottery();
            },
            loadLottery() {
                this.refreshTime = 10;
                this.spinning = true;
                this.$api.ctrl.getLotteryCompany().then(res => {
                    if (res.success) {
                        this.lotterys = res.data;
                        this.loadTotalList();
                        this.loadCountResult();
                    }
                }).finally(e => {
                    this.spinning = false;
                })
            },
            loadTotalList() {
                let day = this.selectDay.format('YYYY-MM-DD');
                this.$api.result.getTotalList(day).then(res => {
                    if (res.success) {
                        let strMap = new Map();
                        res.data.forEach(item => {
                            if (item.result) {
                                item.numbers = item.result.split(',');
                            }
                            if (strMap.has(item.lotteryKey)) {
                                strMap.get(item.lotteryKey).push(item)
                            } else {
                                strMap.set(item.lotteryKey, [item])
                            }
                        })
                        this.totalList = strMap;
                    }
                })
            },
            loadCountResult() {
                let day = this.selectDay.format('YYYY-MM-DD');
                this.$api.result.getCountResult(day).then(res => {
                    if (res.success) {
                        this.countList = res.data;
                        this.lotterys.forEach(item => {
                            let count = this.countList.find(k => k.lotteryId === item.lotteryId);
                            if (count) {
                                this.$set(item, 'countNull', count.countNull);
                                this.$set(item, 'countTotal', count.countTotal);
                                this.$set(item, 'countWaiting', count.countWaiting);
                            }
                        })
                    }
                })
            },
            showAction(lottery, item, type) {
                if (type === 1) {
                    this.title = "修改赛果";
                    this.type = 1;
                    this.actionType = "OLD";
                } else {
                    this.title = "重派";
                    this.type = 2;
                    this.actionType = "NEW";
                }
                this.result = item.result;
                this.actionNumbers = [];
                this.actionGainNum = [];
                this.actionMessage = '';
                this.actionParams.gameNo = item.gameNo;
                this.actionParams.lotteryKey = item.lotteryKey;
                this.actionParams.id = item.id;
                if (lottery) {
                    this.actionParams.groupId = lottery.groupId;
                    this.actionParams.lotteryId = lottery.lotteryId;
                    this.actionCodeNums = lottery.codeNums;
                    this.actionMaxSize = lottery.resultSize;
                }
                this.actionParams.autoPayout = true;
                this.actionModel = true;
                if (item.result) {
                    this.actionNumbers = item.result.split(",");
                }
                this.actionLoading = true;
                this.$api.result.getResultSearch(item.lotteryKey, item.gameNo).then(res => {
                    if (res.success) {
                        this.actionGainNum = res.data.split(",")
                    } else {
                        this.actionMessage = res.message;
                    }
                }).finally(e => {
                    this.actionLoading = false
                })
            },
            showActionZkc(lottery, item) {
                this.title = "修改赛果";
                this.actionType = "OLD";
                this.result = item.result;
                this.actionNumbers = [];
                this.actionGainNum = [];
                this.actionMessage = '';
                this.actionParams.gameNo = item.gameNo;
                this.actionParams.lotteryKey = item.lotteryKey;
                this.actionParams.id = item.id;
                if (lottery) {
                    this.actionParams.groupId = lottery.groupId;
                    this.actionParams.lotteryId = lottery.lotteryId;
                    this.actionCodeNums = lottery.codeNums;
                    this.actionMaxSize = lottery.resultSize;
                }
                this.actionParams.autoPayout = true;
                this.actionModelZkc = true;
                if (item.result) {
                    this.actionNumbers = item.result.split(",");
                }
                this.actionLoading = true;
                this.$api.result.getResultSearch(item.lotteryKey, item.gameNo).then(res => {
                    if (res.success) {
                        this.actionGainNum = res.data.split(",")
                    } else {
                        this.actionMessage = res.message;
                    }
                });
                let str = {"lotteryKey": item.lotteryKey, "lotteryId": lottery.lotteryId, "gameNo": item.gameNo};
                this.resultZkc = [];
                this.$api.result.resultZkc(str).then(res => {
                    if (res.success) {
                        res.data.forEach(item => {
                            item.resultStr = item.resultStr.split(",")
                            //this.resultZkc.push({"resultStr":resultStr,"winAmt":item.winAmt,"commAmt":item.commAmt})
                            this.resultZkc.push(item)
                        });
                    }
                }).finally(e => {
                    this.actionLoading = false
                })
            },
            refrain(arr) {/*验证输入的赛果是否有重复的值*/
                let result = arr.split(",");
                var tmp = [];
                if (Array.isArray(result)) {
                    result.forEach((a, b) => {
                        if (Number(a) < 10) {
                            result[b] = '' + Number(a);
                        }
                    });
                    result.concat().sort().sort(function (a, b) {
                        if (a == b && tmp.indexOf(a) === -1) tmp.push(a);
                    });
                }
                if (tmp.length > 0) {
                    return true;
                }
                return false;
            },
            checkResult(item) {/*验证输入的赛果是否正确*/
                let checked = false;
                let result = {};
                if (item.result != null) {
                    result = item.result.split(",");
                }
                this.checkCallBack(() => {
                    if (item.groupId === 100) {
                        /*北京赛车 极速赛车 澳洲幸运10 极速飞艇 幸运飞艇 */
                        result.forEach(val => {
                            if (val > 10) {
                                throw new Error(this.$t(item.lotteryKey) + " 的赛果不能大于10 请重新输入!");
                            }
                        });
                        if (this.refrain(this.actionParams.result)) {
                            throw new Error(this.$t(item.lotteryKey) + " 的赛果不能有重复的值 请重新输入!");
                        }

                    } else if (item.groupId === 200) {
                        result.forEach(val => {
                            if (val > 9) {
                                throw new Error(this.$t(item.lotteryKey) + " 的赛果不能大于9 请重新输入!");
                            }
                        });
                    }
                    if (item.groupId === 300) {
                        result.forEach(val => {
                            if (val > 20) {
                                throw new Error(this.$t(item.lotteryKey) + " 的赛果不能大于20 请重新输入!");
                            }
                        });
                        if (this.refrain(this.actionParams.result)) {
                            throw new Error(this.$t(item.lotteryKey) + " 的赛果不能有重复的值 请重新输入!");
                        }
                    }
                    checked = true;
                });
                return checked;
            },
            saveActionZkc() {
                if (this.actionType === "OLD") {
                    this.actionParams.result = this.actionNumbers.join(",");
                } else {
                    this.actionParams.result = this.resultZkc[this.actionType].resultStr.join(",");
                }
                //console.log(this.actionParams)
                if (this.checkResult(this.actionParams)) {//验证是否重复和是否大于赛果值
                    this.actionLoading = true;
                    this.$api.result.saveManualResult(this.actionParams).then(res => {
                        this.$utils.handleThen(res, this);
                        if (res.success) {
                            this.loadTotalList();
                            if (this.selectLottery) {
                                this.showNoResult(this.selectLottery);
                            }
                            this.actionModelZkc = false;
                        }
                    }).finally(e => {
                        this.actionLoading = false;
                    })
                }
            },
            saveAction() {
                let action = null;
                if (this.actionType === "OLD") {
                    this.actionParams.result = this.actionNumbers.join(",");
                    action = this.actionNumbers.join(",");
                } else {
                    this.actionParams.result = this.actionGainNum.join(",");
                    action = this.actionGainNum.join(",");
                }
                if (this.result != null) {
                    let oldResult = this.result.split(",").map(Number).join(",");
                    let newResult = action.split(",").map(Number).join(",");
                    if (oldResult === newResult) {//是否有修改
                        return this.$swal({title: "请修改赛果后 在进行重派!", icon: 'warning', confirmButtonText: '确定'})
                    }
                }
                if (this.type === 1) {
                    if (this.checkResult(this.actionParams)) {//验证是否重复和是否大于赛果值
                        //console.log("actionParams",this.actionParams);
                        this.actionLoading = true;
                        this.$api.result.saveManualResult(this.actionParams).then(res => {
                            this.$utils.handleThen(res, this);
                            if (res.success) {
                                this.loadTotalList();
                                if (this.selectLottery) {
                                    this.showNoResult(this.selectLottery);
                                }
                                this.actionModel = false;
                            }
                        }).finally(e => {
                            this.actionLoading = false;
                        })
                    }
                } else {
                    //重派
                    this.saveRePayoutResult(this.actionParams);
                }
            },
            saveCancelResult(item) {
                const self = this;
                this.$confirm({
                    title: '确定撤销 ' + this.$t(item.lotteryKey) + " 第" + item.gameNo + "期注单？",
                    okText: '确认',
                    onOk() {
                        self.$api.result.cancelResult(item.id).then(res => {
                            self.$utils.handleThen(res, self);
                            if (res.success) {
                                self.loadTotalList();
                                let content = self.$t('' + item.lotteryKey) + ',期号为' + item.gameNo + " 进行撤销！";
                                self.saveNotice("CANCEL", content);
                            } else {
                                self.$utils.handleThen(res, self, "撤单失败!");
                            }
                        });
                    },
                });
            },
            savePayoutResult(item) {/*派彩 */
                const self = this;
                this.$confirm({
                    title: '确定派彩 ' + this.$t(item.lotteryKey) + " 第" + item.gameNo + "期注单？",
                    okText: '确认',
                    onOk() {
                        self.$swal({
                            showConfirmButton: false,
                            icon: "warning",
                            title: '派彩中！',
                            showCancelButton: false,
                            allowOutsideClick :false,
                        });
                        self.$api.result.savePayoutResult(item.id).then(res => {
                            self.$utils.handleThen(res, self);
                            if (res.success) {
                                self.loadLottery()
                                //self.loadTotalList();
                            } else {
                                self.$utils.handleThen(res, self, "派彩失败!");
                            }
                        })
                    },
                })
            },
            saveNotice(type, content) {/*添加通知表数据*/
                let params = {
                    userId: this.$store.state.user.info.primaryId,
                    type: type,
                    content: content,
                };
                // console.log("params",params);
                this.$api.result.addNotice(params).then()
            },
            saveRePayoutResult(item) {
                const self = this;
                this.$confirm({
                    title: this.$t(item.lotteryKey) + " 第" + item.gameNo + "期？重派结果为: " + item.result,
                    okText: '确认',
                    onOk() {
                        if (self.checkResult(item)) {//验证是否重复和是否大于赛果值
                            self.$swal({
                                showConfirmButton: false,
                                icon: "warning",
                                title: '重新派彩中！',
                                showCancelButton: false,
                                allowOutsideClick :false,
                            });
                            self.$api.result.rePayoutResult(item).then(res => {
                                if (res.success) {
                                    let content = self.$t('' + item.lotteryKey) + ',期号为' + item.gameNo + " 进行重派  重派结果为: " + item.result;
                                    self.saveNotice('REDIVIDEND', content);
                                    self.$utils.handleThen(res, self);
                                    self.actionModel = false;
                                    self.showNoResult(self.selectLottery);
                                } else {
                                    self.$utils.handleThen(res, self);
                                }
                            })
                        }
                    },
                })
            },
            showNoResult(lottery) {
                this.selectLottery = lottery;
                this.showNo = true;
                this.noResultList = [];
                let day = this.selectDay.format('YYYY-MM-DD');
                this.noSpinning = true;
                this.$api.result.getNullResultList(lottery.lotteryId, day).then(res => {
                    if (res.success) {
                        res.data.forEach(item => {
                            if (item.result) {
                                item.numbers = item.result.split(',');
                            }
                        })
                        this.noResultList = res.data;
                    }
                }).finally(e => {
                    this.noSpinning = false;
                })

            },
            startCountdown() {
                clearInterval(this.timer);
                this.timer = setInterval(() => {
                    this.refreshTime -= 1;
                    if (this.refreshTime === 0) {
                        this.loadLottery();
                        this.refreshTime = 10;
                    }
                }, 1000);
            },
        },
        mounted() {
            this.loadLottery();
            this.startCountdown();
        },
        destroyed() {
            clearInterval(this.timer);
        }
    };
</script>

<style scoped>

</style>

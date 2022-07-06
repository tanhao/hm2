<template>
    <div class="autofutllwith">
        <div style="min-width: 1200px">
            <div class="kjlist">
                <a-spin :spinning="spinning">
                    <a-row :gutter="14">
                        <a-col :span="2">
                            <table class="tableborder leftlist" border="0" align="center" cellpadding="5"
                                   cellspacing="1">
                                <tr>
                                    <th width="100px">选择彩种</th>
                                </tr>
                                <tbody>
                                <template v-for="item in lotterys">
                                    <tr class="nohover">
                                        <td class="forumrow" :class="{select:params.lotteryId===item.lotteryId}">
                                            <a @click="changeLottery(item)">{{ item.lotteryName }}</a>
                                        </td>
                                    </tr>
                                </template>
                                </tbody>
                            </table>
                        </a-col>
                        <a-col :span="22">
                            <div class="mb10 df">
                                <span class="maintxt mlr10">选择日期:</span>
                                <a-date-picker size="small" v-model="selectDay" :disabled-date="disabledDate"
                                               placeholder="选择日期" style="width: 120px"/>
                                <span class="maintxt mlr10">输入期号:</span>
                                <a-input-search style="width: 120px" size="small" placeholder="输入期号"
                                                v-model="params.gameNo"/>
                                <div class="pl10">
                                    <a-button type="primary" icon="search" size="small" @click="search()">
                                        查询
                                    </a-button>
                                </div>
                                <div class="pl10">
                                    <a-button type="primary" icon="calendar" size="small" @click="searchYesterday(1)"
                                              v-if="isToday">
                                        昨日
                                    </a-button>
                                    <a-button type="primary" icon="calendar" size="small" @click="searchYesterday(0)"
                                              v-if="!isToday">
                                        今日
                                    </a-button>
                                </div>
                            </div>
                            <!--赛车飞艇组-->
                            <table v-if="checkShow('scft')" class="tableborder leftlist" border="0" align="center"
                                   cellpadding="2"
                                   cellspacing="1">
                                <tr>
                                    <th width="120px">期数</th>
                                    <th width="150px">开奖时间</th>
                                    <th width="220px">开出号码</th>
                                    <th colspan="3" width="60px">冠亚军和</th>
                                    <th colspan="5" width="100px">1~5龙虎</th>
                                    <th>冠军</th>
                                    <th>亚军</th>
                                    <th>三名</th>
                                    <th>四名</th>
                                    <th>五名</th>
                                    <th>六名</th>
                                    <th>七名</th>
                                    <th>八名</th>
                                    <th>九名</th>
                                    <th>十名</th>
                                </tr>
                                <tr v-for="item in results" :key="item.id">
                                    <td class="forumrow">{{ item.gameNo }}</td>
                                    <td class="forumrowhighlight">
                                        {{ moment(item.actionTime * 1000).format('YYYY-MM-DD HH:mm:ss') }}
                                    </td>
                                    <template v-if="item.result">
                                        <td class="forumrowhighlight">
                                            <div class="innVmp">
                                                <div :class="item.lotteryKey"
                                                     style="margin: 0px auto; display: inherit;">
                                                    <div :class="'n_'+parseInt(num)" v-for="num in item.numbers">
                                                        {{ num }}
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="forumrowhighlight">
                                            {{ parseInt(item.numbers[0]) + parseInt(item.numbers[1]) }}
                                        </td>
                                        <td class="forumrowhighlight "
                                            :class="parseInt(item.numbers[0])+parseInt(item.numbers[1]) > 11 ? 'red' : 'no'">
                                            {{ parseInt(item.numbers[0]) + parseInt(item.numbers[1]) > 11 ? '大' : '小' }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classDs(parseInt(item.numbers[0])+parseInt(item.numbers[1]))">
                                            {{ hzDs(parseInt(item.numbers[0]) + parseInt(item.numbers[1])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[0]),parseInt(item.numbers[9]))">
                                            {{ hzLh(parseInt(item.numbers[0]), parseInt(item.numbers[9])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[1]),parseInt(item.numbers[8]))">
                                            {{ hzLh(parseInt(item.numbers[1]), parseInt(item.numbers[8])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[2]),parseInt(item.numbers[7]))">
                                            {{ hzLh(parseInt(item.numbers[2]), parseInt(item.numbers[7])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[3]),parseInt(item.numbers[6]))">
                                            {{ hzLh(parseInt(item.numbers[3]), parseInt(item.numbers[6])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[4]),parseInt(item.numbers[5]))">
                                            {{ hzLh(parseInt(item.numbers[4]), parseInt(item.numbers[5])) }}
                                        </td>
                                        <td v-for="i in 10" class="forumrowhighlight"
                                            :class="classDs(parseInt(item.numbers[i-1]))">
                                            {{ hzDs(parseInt(item.numbers[i - 1])) }}
                                        </td>
                                    </template>
                                    <template v-else>
                                        <td class="forumrowhighlight no" v-for="i in 19"></td>
                                    </template>
                                </tr>
                                <tr>
                                    <td colspan="22" class="forumrowhighlight nohover" v-if="results.length===0">
                                        <a-empty/>
                                    </td>
                                </tr>
                            </table>
                            <!--幸运农场-->
                            <table v-if="checkShow('xync')" class="tableborder leftlist" border="0" align="center"
                                   cellpadding="2"
                                   cellspacing="1">
                                <tr>
                                    <th width="120px">期数</th>
                                    <th width="150px">开奖时间</th>
                                    <th width="290px">开出号码</th>
                                    <th colspan="4" width="80px">总和</th>
                                    <th colspan="4" width="80px">1~4龙虎</th>
                                    <th>一球</th>
                                    <th>二球</th>
                                    <th>三球</th>
                                    <th>四球</th>
                                    <th>五球</th>
                                    <th>六球</th>
                                    <th>七球</th>
                                    <th>八球</th>
                                </tr>
                                <tr v-for="item in results" :key="item.id">
                                    <td class="forumrow">{{ item.gameNo }}</td>
                                    <td class="forumrowhighlight">
                                        {{ moment(item.actionTime * 1000).format('YYYY-MM-DD HH:mm:ss') }}
                                    </td>
                                    <template v-if="item.result">
                                        <td class="forumrowhighlight">
                                            <div class="innVmp">
                                                <div :class="item.lotteryKey">
                                                    <div :class="'n_'+parseInt(num)" v-for="num in item.numbers">
                                                        {{ num }}
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="forumrowhighlight">{{ item.total }}
                                        </td>
                                        <td class="forumrowhighlight" :class="item.total > 84 ?'red':'no'">
                                            {{ item.total > 84 ? '大' : (item.total == 84 ? '和' : '小') }}
                                        </td>
                                        <td class="forumrowhighlight" :class="classDs(item.total)">
                                            {{ hzDs(item.total) }}
                                        </td>
                                        <td class="forumrowhighlight" :class="classMantissa(item.numbers)">
                                            {{ mantissa(item.numbers) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[0]),parseInt(item.numbers[7]))">
                                            {{ hzLh(parseInt(item.numbers[0]), parseInt(item.numbers[7])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[1]),parseInt(item.numbers[6]))">
                                            {{ hzLh(parseInt(item.numbers[1]), parseInt(item.numbers[6])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[2]),parseInt(item.numbers[5]))">
                                            {{ hzLh(parseInt(item.numbers[2]), parseInt(item.numbers[5])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[3]),parseInt(item.numbers[4]))">
                                            {{ hzLh(parseInt(item.numbers[3]), parseInt(item.numbers[4])) }}
                                        </td>
                                        <td v-for="i in 8" class="forumrowhighlight"
                                            :class="classDs(parseInt(item.numbers[i-1]))">
                                            {{ hzDs(parseInt(item.numbers[i - 1])) }}
                                        </td>
                                    </template>
                                    <template v-else>
                                        <td class="forumrowhighlight no" v-for="i in 18"></td>
                                    </template>
                                </tr>
                                <tr>
                                    <td colspan="20" class="forumrowhighlight nohover" v-if="results.length===0">
                                        <a-empty/>
                                    </td>
                                </tr>
                            </table>
                            <!--时时彩组-->
                            <table v-if="checkShow('ssc')" class="tableborder leftlist" border="0" align="center"
                                   cellpadding="2"
                                   cellspacing="1">
                                <tr>
                                    <th width="120px">期数</th>
                                    <th width="150px">开奖时间</th>
                                    <th width="220px">开出号码</th>
                                    <th colspan="3" width="60px">总和</th>
                                    <th>龙虎</th>
                                    <th>前三</th>
                                    <th>中三</th>
                                    <th>后三</th>
                                    <th>一球</th>
                                    <th>二球</th>
                                    <th>三球</th>
                                    <th>四球</th>
                                    <th>五球</th>
                                </tr>
                                <tr v-for="item in results" :key="item.id">
                                    <td class="forumrow">{{ item.gameNo }}</td>
                                    <td class="forumrowhighlight">
                                        {{ moment(item.actionTime * 1000).format('YYYY-MM-DD HH:mm:ss') }}
                                    </td>
                                    <template v-if="item.result">
                                        <td class="forumrowhighlight">
                                            <div class="innVmp">
                                                <div :class="item.lotteryKey">
                                                    <div :class="'n_'+parseInt(num)" v-for="num in item.numbers">
                                                        {{ num }}
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="forumrowhighlight">{{ item.total }}</td>
                                        <td class="forumrowhighlight" :class="item.total > 22 ?'red':'no'">
                                            {{ item.total > 22 ? '大' : '小' }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classDs(item.total)">
                                            {{ hzDs(item.total) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[0]),parseInt(item.numbers[4]))">
                                            {{ hzLh(parseInt(item.numbers[0]), parseInt(item.numbers[4])) }}
                                        </td>
                                        <td class="forumrowhighlight">
                                            {{ cardType(parseInt(item.numbers[0]), parseInt(item.numbers[1]), parseInt(item.numbers[2])) }}
                                        </td>
                                        <td class="forumrowhighlight">
                                            {{ cardType(parseInt(item.numbers[1]), parseInt(item.numbers[2]), parseInt(item.numbers[3])) }}
                                        </td>
                                        <td class="forumrowhighlight">
                                            {{ cardType(parseInt(item.numbers[2]), parseInt(item.numbers[3]), parseInt(item.numbers[4])) }}
                                        </td>
                                        <td v-for="i in 5" class="forumrowhighlight"
                                            :class="classDs(parseInt(item.numbers[i-1]))">
                                            {{ hzDs(parseInt(item.numbers[i - 1])) }}
                                        </td>
                                    </template>
                                    <template v-else>
                                        <td class="forumrowhighlight no" v-for="i in 13"></td>
                                    </template>
                                </tr>
                                <tr>
                                    <td colspan="16" class="forumrowhighlight nohover" v-if="results.length===0">
                                        <a-empty/>
                                    </td>
                                </tr>
                            </table>
                            <!--广东快乐10组-->
                            <table v-if="checkShow('happy')" class="tableborder leftlist" border="0" align="center"
                                   cellpadding="2"
                                   cellspacing="1">
                                <tr>
                                    <th width="120px">期数</th>
                                    <th width="150px">开奖时间</th>
                                    <th width="290px">开出号码</th>
                                    <th colspan="4" width="80px">总和</th>
                                    <th colspan="4" width="80px">1~4龙虎</th>
                                    <th>一球</th>
                                    <th>二球</th>
                                    <th>三球</th>
                                    <th>四球</th>
                                    <th>五球</th>
                                    <th>六球</th>
                                    <th>七球</th>
                                    <th>八球</th>
                                </tr>
                                <tr v-for="item in results" :key="item.id">
                                    <td class="forumrow">{{ item.gameNo }}</td>
                                    <td class="forumrowhighlight">
                                        {{ moment(item.actionTime * 1000).format('YYYY-MM-DD HH:mm:ss') }}
                                    </td>
                                    <template v-if="item.result">
                                        <td class="forumrowhighlight">
                                            <div class="innVmp">
                                                <div :class="item.lotteryKey">
                                                    <div :class="'n_'+parseInt(num)" v-for="num in item.numbers">
                                                        {{ num }}
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="forumrowhighlight">{{ item.total }}
                                        </td>
                                        <td class="forumrowhighlight" :class="item.total > 84 ?'red':'no'">
                                            {{ item.total > 84 ? '大' : (item.total == 84 ? '和' : '小') }}
                                        </td>
                                        <td class="forumrowhighlight" :class="classDs(item.total)">
                                            {{ hzDs(item.total) }}
                                        </td>
                                        <td class="forumrowhighlight" :class="classMantissa(item.numbers)">
                                            {{ mantissa(item.numbers) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[0]),parseInt(item.numbers[7]))">
                                            {{ hzLh(parseInt(item.numbers[0]), parseInt(item.numbers[7])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[1]),parseInt(item.numbers[6]))">
                                            {{ hzLh(parseInt(item.numbers[1]), parseInt(item.numbers[6])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[2]),parseInt(item.numbers[5]))">
                                            {{ hzLh(parseInt(item.numbers[2]), parseInt(item.numbers[5])) }}
                                        </td>
                                        <td class="forumrowhighlight"
                                            :class="classLh(parseInt(item.numbers[3]),parseInt(item.numbers[4]))">
                                            {{ hzLh(parseInt(item.numbers[3]), parseInt(item.numbers[4])) }}
                                        </td>
                                        <td v-for="i in 8" class="forumrowhighlight"
                                            :class="classDs(parseInt(item.numbers[i-1]))">
                                            {{ hzDs(parseInt(item.numbers[i - 1])) }}
                                        </td>
                                    </template>
                                    <template v-else>
                                        <td class="forumrowhighlight no" v-for="i in 17"></td>
                                    </template>
                                </tr>
                                <tr>
                                    <td colspan="20" class="forumrowhighlight nohover" v-if="results.length===0">
                                        <a-empty/>
                                    </td>
                                </tr>
                            </table>

                            <!--PC蛋蛋类 加拿大28-->
                            <table v-if="checkShow('pcdd')" class="tableborder leftlist" border="0" align="center"
                                   cellpadding="2"
                                   cellspacing="1">
                                <tr>
                                    <th width="120px">期数</th>
                                    <th width="150px">开奖时间</th>
                                    <th width="290px">开出号码</th>
                                    <th colspan="2">两面</th>
                                </tr>
                                <tr v-for="item in results" :key="item.id">
                                    <td class="forumrow">{{ item.gameNo }}</td>
                                    <td class="forumrowhighlight">
                                        {{ moment(item.actionTime * 1000).format('YYYY-MM-DD HH:mm:ss') }}
                                    </td>
                                    <td class="forumrowhighlight">
                                        <div class="innVmp" v-if="item.numbers">
                                            <div class="pcdd">
                                                <div :class="'n_'+item.numbers[0]">{{ item.numbers[0] }}</div>
                                                <i>+</i>
                                                <div :class="'n_'+item.numbers[1]">{{ item.numbers[1] }}</div>
                                                <i>+</i>
                                                <div :class="'n_'+item.numbers[2]">{{ item.numbers[2] }}</div>
                                                <i>=</i>
                                                <div class="kend">
                                                    <div :class="'n_'+item.total">{{ item.total }}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="forumrowhighlight"><span v-if="item.total">{{ hzDs(item.total) }}</span>
                                    </td>
                                    <td class="forumrowhighlight"><span
                                        v-if="item.total">{{ item.total > 13 ? '大' : '小' }}</span></td>
                                </tr>
                            </table>

                            <div class="p10" style="text-align: center;">
                                <a-pagination size="small" :total="resultCount" show-size-changer show-quick-jumper
                                              :current="params.page"
                                              :pageSize="params.size"
                                              @change="changePage" @showSizeChange="changePage"
                                              :show-total="total => `共 ${total} 条`"/>
                            </div>
                        </a-col>
                    </a-row>
                </a-spin>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            spinning: true,
            lotterys: [],
            selectDay: this.todayDate(),
            resultCount: 0,
            params: {
                page: 1,
                size: 10,
                accountDay: null,
                lotteryId: null,
                gameNo: null,
            },
            results: [],
            isToday: true,
            refreshTime: 10,
            timer: null,
        };
    },
    computed: {
        checkShow(type) {
            return (type) => {
                let scft = [101, 102, 103, 104, 105, 106];
                let ssc = [201, 202, 203, 204, 205];
                let happy = [301, 302, 303, 304];
                let pcdd = [401, 402, 403];
                if (type == "scft" && scft.filter(id => id === this.params.lotteryId).length > 0) {
                    return true;
                }
                if (type == "ssc" && ssc.filter(id => id === this.params.lotteryId).length > 0) {
                    return true;
                }
                if (type == "happy" && happy.filter(id => id === this.params.lotteryId).length > 0) {
                    return true;
                }
                if (type == "pcdd" && pcdd.filter(id => id === this.params.lotteryId).length > 0) {
                    return true;
                }

                return false;
            }
        },
        hzDs(number) {
            return (number) => {
                return number % 2 === 0 ? '双' : '单';
            }
        },
        classDs(number) {
            return (number) => {
                return number % 2 === 0 ? 'red' : 'no';
            }
        },
        hzLh(no1, no2) {
            return (no1, no2) => {
                if (no1 == no2) return "和";
                return no1 > no2 ? '龙' : '虎';
            }
        },
        classLh(no1, no2) {
            return (no1, no2) => {
                return no1 > no2 ? 'red' : 'no';
            }
        },
        mantissa(numbers) {
            return (numbers) => {
                let rcash = 0
                for (let i = 0; i < numbers.length; i++) {
                    rcash += parseInt(numbers[i]);
                }
                return rcash % 10 > 4 ? '尾大' : '尾小'
            }
        },
        classMantissa(numbers) {
            return (numbers) => {
                let rcash = 0
                for (let i = 0; i < numbers.length; i++) {
                    rcash += parseInt(numbers[i]);
                }
                return rcash % 10 > 4 ? 'red' : 'no'
            }
        },
        cardType(no1, no2, no3) {
            return (no1, no2, no3) => {
                let sort = [no1, no2, no3].sort();
                no1 = sort[0];
                no2 = sort[1];
                no3 = sort[2];
                if (no1 === no2 && no2 === no3) {
                    return "豹子";
                } else if (no1 === no2 || no2 === no3 || no1 === no3) {
                    return "对子";
                } else if (no1 + 1 === no2 && no2 + 1 === no3) {
                    return "顺子";
                } else if (no1 + 1 === no2 || no2 + 1 === no3) {
                    return "半顺";
                } else {
                    return "杂六";
                }
            }
        },
    },
    methods: {
        disabledDate(current) {
            return current && current > this.moment().endOf('day');
        },
        loadLottery() {
            this.$api.ctrl.getLotteryCompany().then(res => {
                if (res.success) {
                    this.lotterys = [];
                    res.data.forEach(l => {
                        if (l.lotteryId !== 101 && l.lotteryId !== 201 && l.lotteryId !== 204 && l.lotteryId !== 301 && l.lotteryId !== 303) {
                            this.lotterys.push(l)
                        }
                    })
                    this.changeLottery(res.data[0]);
                }
            })
        },
        loadResult() {
            this.params.accountDay = this.selectDay.format('YYYY-MM-DD');
            this.$api.result.getResultList(this.params).then(res => {
                if (res.success) {
                    this.results = [];
                    this.resultCount = res.data.total;
                    let results = res.data.dataList;
                    results.forEach(result => {
                        if (result.result) {
                            result.numbers = result.result.split(',');
                            result.total = result.numbers.reduce((a, b) => {
                                return parseInt(a) + parseInt(b)
                            });
                        }
                    })
                    this.results = results;
                }
            }).finally(e => {
                this.spinning = false;
            })
        },
        changePage(page, pageSize) {
            this.spinning = true;
            this.params.page = page;
            this.params.size = pageSize;
            this.loadResult();
        },
        changeLottery(item) {
            this.params.lotteryId = item.lotteryId;
            this.changePage(1, this.params.size);
        },
        search() {
            this.changePage(1, this.params.size);
        },
        searchYesterday(sub) {
            this.isToday = !sub;
            this.selectDay = this.moment().subtract(sub, "days");
            this.search();
        },
        startCountdown() {
            clearInterval(this.timer);
            this.timer = setInterval(() => {
                this.refreshTime -= 1;
                if (this.refreshTime === 0) {
                    this.loadResult();
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

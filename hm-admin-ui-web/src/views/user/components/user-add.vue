<template>
    <!--新增-->
    <a-modal
            :title="addUserName"
            :width="750"
            :visible="userAddShow"
            :body-style="{ paddingBottom: '0px'}"
            :maskClosable="maskClosable"
            dialogClass="tk"
            @cancel="onClose"
        >
        <template slot="footer">
            <a-button key="back" @click="onClose" :loading="saveLoading">
                取消
            </a-button>
            <a-button type="primary" size="small" @click="saveUser()" :loading="saveLoading">
                <span v-if="!saveLoading">确定</span> <span v-else> 提交中...</span>
            </a-button>
        </template>
        <a-spin :spinning="spinning">
            <table class="popright noinptonum" border="0" align="center" cellpadding="5" cellspacing="1"
                   style="border-collapse: separate;width: 100%">
                <tbody>
                <tr>
                    <td width="90" class="forumrow txtright">{{basicUpper.usernameLabel}}账号</td>
                    <td class="forumrowhighlight txtleft"><span>{{basicUpper.username}}</span></td>
                </tr>
                <tr>
                    <td class="forumrow txtright">{{basicUpper.adduserLabel}}账号</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" ref="USERNAME" allow-clear style="width: 160px" @focus="focus($event)" v-model="basicOwn.username"/>
                        <span class="red"><i color="red">*</i>  [2-10]长度，字母，@，数字组合</span>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">登陆密码</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input-password allow-clear placeholder="登陆密码" size="small" style="width: 160px" @focus="focus($event)"
                                          v-model="basicOwn.password"/>
                        <span class="red"><i color="red">*</i> [8-16]長度,最少二个字母(0-9,a-z,@)</span>

                    </td>
                </tr>
                <tr v-if="basicUpper.userLevel===1">
                    <td class="forumrow txtright">安保密码</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input-password size="small" allow-clear style="width: 160px" :maxLength="8" placeholder="安保密码" @focus="focus($event)"
                                          v-model="basicOwn.secondary"/>
                        <span class="red"><i color="red">*</i> [8-16]長度,最少二个字母(0-9,a-z,@)</span>
                    </td>
                </tr>
                <tr v-if="basicUpper.userLevel===1">
                    <td class="forumrow txtright">搜索码</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear style="width: 160px" v-model="basicOwn.securityCode" @focus="focus($event)"
                                 :maxLength="5"/>
                        <span class="red"><i color="red">*</i> [5]长度，数字组合</span>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">{{basicUpper.adduserLabel}}名称</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" @focus="focus($event)" allow-clear style="width: 160px" v-model="basicOwn.nickName"/>
                        <span> [2-8]长度，可以输入中文</span>
                    </td>
                </tr>
                <tr v-if="basicUpper.userLevel===1">
                    <td class="forumrow txtright">网站名称</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" @focus="focus($event)" allow-clear style="width: 160px" v-model="basicOwn.siteName"/>
                        <span>[5]长度，可以输入中文，空值默认取HM后桶账号数据</span>
                    </td>
                </tr>
                <tr v-if="basicUpper.userLevel===1 && editUserType==='AGENT'">
                    <td class="forumrow txtright">可开会员数</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input-number size="small" style="width: 160px" v-model="basicOwn.maxUser" :min="1" @focus="focus($event)"
                                        :max="999999999999"/>
                    </td>
                </tr>

                <tr>
                    <td class="forumrow txtright">绑定登陆IP</td>
                    <td class="forumrowhighlight txtleft">
                        <a-textarea placeholder="绑定登陆IP" @focus="focus($event)" :rows="2" allow-clear v-model="basicOwn.loginIpBound"/>
                        <span>最多绑定五个IP，使用","分割</span>
                    </td>
                </tr>
                <tr v-if="basicUpper.userLevel!==1">
                    <td class="forumrow txtright">额度模式</td>
                    <td class="forumrowhighlight txtleft">
                        <a-radio-group name="radioGroup" v-model="basicOwn.reserveModel">
                            <a-radio value="OMNIBUS"
                                     v-if="basicUpper.reserveModel==='OMNIBUS' && editUserType==='AGENT' ">
                                综合账户
                            </a-radio>
                            <a-radio value="CREDIT"
                                     v-if="basicUpper.reserveModel==='OMNIBUS'|| basicUpper.reserveModel==='CREDIT'">
                                信用账户
                            </a-radio>
                            <a-radio value="RECHARGE"
                                     v-if="basicUpper.reserveModel==='OMNIBUS'|| basicUpper.reserveModel==='RECHARGE'">
                                充值账户
                            </a-radio>
                        </a-radio-group>
                        <span class="red" v-if="basicUpper.reserveModel==='OMNIBUS' && editUserType==='AGENT'"><i color="red">*</i> 三选一</span>
                        <span class="red" v-if="basicUpper.reserveModel==='OMNIBUS' && editUserType==='MEMBER'"><i color="red">*</i> 二选一</span>
                    </td>
                </tr>
                <template v-if="basicUpper.userLevel!==1">
                    <tr v-if="basicOwn.reserveModel==='OMNIBUS'|| basicOwn.reserveModel==='CREDIT' ">
                        <td class="forumrow txtright">信用额度</td>
                        <td class="forumrowhighlight txtleft">
                            <div class="components-input-demo-presuffix">
                                <a-input-number size="small" style="width: 160px" @focus="focus($event)"
                                                v-model="basicOwn.creditBalance"
                                                :min="0"
                                />
                                <span class="red" v-if="basicUpper.userLevel>2">（*必须在[0-{{$utils.getAnsZ(basicUpper.useCreditBalance)}}]范围内,会员账号每日额度还原）</span>
                                <span class="red" v-if="basicUpper.userLevel<=2">无限</span>
                                <br/>
                            </div>
                            <a>{{basicOwn.creditBalance|number_chinese}}</a>
                        </td>
                    </tr>
                    <tr v-if="basicOwn.reserveModel==='OMNIBUS'|| basicOwn.reserveModel==='RECHARGE'">
                        <td class="forumrow txtright">充值额度</td>
                        <td class="forumrowhighlight txtleft">
                            <div class="components-input-demo-presuffix">
                                <a-input-number size="small" style="width: 160px" @focus="focus($event)"
                                                v-model="basicOwn.rechargeBalance"
                                                :min="0"
                                />
                                <span class="red" v-if="basicUpper.userLevel>2"> 最大额度限制：{{$utils.getAnsZ(basicUpper.useRechargeBalance)}} </span>
                                <span class="red" v-if="basicUpper.userLevel<=2">无限</span>
                            </div>
                            <a>{{basicOwn.rechargeBalance|number_chinese}}</a>
                        </td>
                    </tr>
                </template>
                <tr v-if="basicUpper.userLevel===1">
                    <td class="forumrow txtright">盘口</td>
                    <td class="forumrowhighlight txtleft">
                        A盘：<a-input-number :min="0" @focus="focus($event)" :max="10" v-model="basicUpper.marketValue[0]" :step="0.3" style="width: 80px"></a-input-number>
                        B盘：<a-input-number :min="0" @focus="focus($event)" :max="10" v-model="basicUpper.marketValue[1]" :step="0.3" style="width: 80px"></a-input-number>
                        C盘：<a-input-number :min="0" @focus="focus($event)" :max="10" v-model="basicUpper.marketValue[2]" :step="0.3" style="width: 80px"></a-input-number>
                        D盘：<a-input-number :min="0" @focus="focus($event)" :max="10" v-model="basicUpper.marketValue[3]" :step="0.3" style="width: 80px"></a-input-number>
                    </td>
                </tr>
                <tr v-if="editUserType==='MEMBER'">
                    <td class="forumrow txtright">最高盈利</td>
                    <td class="forumrowhighlight txtleft">
                        <a-radio-group v-model="maxWinRadio">
                            <a-radio  value="wxz">无限制</a-radio>
                            <a-radio  value="zdz">设置金额</a-radio>
                        </a-radio-group>
                        <a-input style="width: 160px;margin-right: 10px" @focus="focus($event)" v-if="maxWinRadio==='zdz'" v-model="basicOwn.maxWin"></a-input>
                    </td>
                </tr>
                <template v-if="basicUpper.userLevel>1">
                    <template v-if="editUserType==='AGENT'">
                        <tr>
                            <td class="forumrow txtright">选择投注模式</td>
                            <td class="forumrowhighlight txtleft">
                                <a-checkbox-group v-model="basicOwn.handicapSelect" @change="onChangeMarketSelect"
                                                  :default-value="basicUpper.handicapSelect" >
                                    <a-checkbox v-for="k in basicUpper.handicapSelect" :value="k">
                                        {{k}}
                                    </a-checkbox>
                                </a-checkbox-group>
                            </td>
                        </tr>
                        <tr v-if="basicUpper.userLevel>=3">
                            <td class="forumrow txtright">开放盘口</td>
                            <td class="forumrowhighlight txtleft">
                                <a-checkbox-group v-model="basicOwn.handicap" @change="onChangeHandicap"
                                                  :defaultValue="basicUpper.handicap">
                                    <a-checkbox v-for="k in basicUpper.handicap" :value="k">
                                        {{k}}
                                    </a-checkbox>
                                </a-checkbox-group>
                            </td>
                        </tr>
                    </template>
                    <tr v-if="editUserType==='MEMBER'">
                        <td class="forumrow txtright">选择投注模式</td>
                        <td class="forumrowhighlight txtleft">
                            <a-radio-group v-model="basicOwn.marketSelect">
                                <a-radio v-for="k in basicUpper.handicapSelect" :value="k">{{k}}</a-radio>
                            </a-radio-group>
                            <span class="red">* 二选一</span>
                        </td>
                    </tr>
                    <tr v-if="editUserType==='MEMBER'">
                        <td class="forumrow txtright">开放盘口</td>
                        <td class="forumrowhighlight txtleft">
                            <a-radio-group v-model="handicaps">
                                <a-radio v-for="k in basicUpper.markets" :value="k" > {{k}}盘</a-radio>
                            </a-radio-group>
                            <span class="red">* 四选一</span>
                        </td>
                    </tr>
                </template>
                <tr v-if="basicUpper.userLevel!==1 && editUserType==='MEMBER'">
                    <td class="forumrow txtright">赚取退水</td>
                    <td class="forumrowhighlight txtleft">
                        <div v-if="editUserType==='AGENT'">
                            <a-input-number size="small" allow-clear style="width: 160px" :min="0" :max="60" @focus="focus($event)"
                                            v-model="basicOwn.commBack"/>
                            元/万
                            <span class="red">(*必须在[0-60]范围内,所有盘口等比减去退水)</span>
                        </div>
                        <div v-else>
                            <a-select default-value="0" v-model="basicOwn.commBack" style="width: 120px"
                                      @change="handleChangeCommBack">
                                <a-select-option :value="0"> 水全退到底</a-select-option>
                                <a-select-option :value="1"> 赚取0.1%退水</a-select-option>
                                <a-select-option :value="3"> 赚取0.3%退水</a-select-option>
                                <a-select-option :value="5"> 赚取0.5%退水</a-select-option>
                                <a-select-option :value="10"> 赚取1.0%退水</a-select-option>
                                <a-select-option :value="15"> 赚取1.5%退水</a-select-option>
                                <a-select-option :value="1000"> 赚取所有退水</a-select-option>
                            </a-select>
                        </div>
                    </td>
                </tr>
                <template v-if="basicUpper.userLevel===2 && editUserType==='AGENT'">
                    <tr >
                        <td class="forumrow txtright">是否标准盘</td>
                        <td class="forumrowhighlight txtleft">
                            <a-radio-group v-model="basicOwn.disk">
                                <a-radio :value="true">标准</a-radio>
                                <a-radio :value="false">特殊</a-radio>
                            </a-radio-group>
                            <span class="red">标准盘按公司正常营运盘口调整赔率,特殊盘设置非正常营运盘口使用,退水/赔率随意设置</span>
                        </td>
                    </tr>
                    <template v-if="basicOwn.disk===true">
                        <tr >
                            <td class="forumrow txtright">开放盘口</td>
                            <td class="forumrowhighlight txtleft">
                                <a-checkbox-group v-model="basicOwn.handicap" @change="onChangeHandicap"
                                                  :defaultValue="basicUpper.handicap">
                                    <a-checkbox v-for="(k,i) in basicUpper.handicap" :value="k" disabled >
                                        {{k}}盘 <a-input-number disabled :min="0" @focus="focus($event)" :max="10" v-model="basicUpper.marketValue[i]" :step="0.3" style="width: 80px;"></a-input-number>
                                    </a-checkbox>
                                </a-checkbox-group>
                            </td>
                        </tr>
                    </template>
                    <template v-else>
                        <tr >
                            <td class="forumrow txtright">开放盘口</td>
                            <td class="forumrowhighlight txtleft">
                                <a-checkbox-group v-model="basicOwn.handicap" @change="onChangeHandicap"
                                                  :defaultValue="basicUpper.handicap">
                                    <a-checkbox v-for="(k,i) in basicUpper.handicap" :value="k" disabled>
                                        {{k}}盘 <a-input-number  :min="0" :max="10" @focus="focus($event)" v-model="basicUpper.marketValue[i]" :step="0.3" style="width: 80px;"></a-input-number>
                                    </a-checkbox>
                                </a-checkbox-group>
                            </td>
                        </tr>
                    </template>
                </template>
                <tr v-if="basicUpper.userLevel!==1 && basicUpper.gainOdds==='OPEN' && editUserType==='AGENT'">
                    <td class="forumrow txtright">赚赔</td>
                    <td class="forumrowhighlight txtleft">
                        <a-switch checked-children="开" un-checked-children="关"
                                  v-model="basicOwn.gainOdds==='OPEN'?true:false"  @change="handleGainOdds" />
                       <!-- <template v-if="basicUpper.userLevel===2">
                            <a-input-number  :min="0" :max="2" v-model="basicOwn.maxDiff" style="width: 60px"></a-input-number>
                        </template>-->
                    </td>
                </tr>
                <tr v-if="(basicUpper.userLevel===2||basicUpper.userLevel===3) && basicUpper.controlOdds==='OPEN' && editUserType==='AGENT'">
                    <td class="forumrow txtright">控盘</td>
                    <td class="forumrowhighlight txtleft">
                        <a-switch checked-children="开" un-checked-children="关"
                                  v-model="basicOwn.controlOdds==='OPEN'?true:false" @change="handleControlOdds" />
                    </td>
                </tr>
                <tr v-if="basicUpper.userLevel!==1 && editUserType!=='MEMBER' && basicUpper.bhStatus===true">
                    <td class="forumrow txtright">开放补货</td>
                    <td class="forumrowhighlight txtleft">
                        <a-switch checked-children="开" un-checked-children="关"
                                  v-model="basicOwn.bhStatus" @change="handleBhStatus" />
                    </td>
                </tr>
                <tr v-if="basicUpper.userLevel===1">
                    <td class="forumrow txtright">租金</td>
                    <td class="forumrowhighlight txtleft">
                        <div class="components-input-demo-presuffix">
                            <a-input-number size="small" style="width: 160px" v-model="basicOwn.rental" :min="0" @focus="focus($event)"
                                            :max="999999999999"/>
                            <span class="mlr10">起租日期</span>
                            <a-date-picker size="small" @change="changeDate" format="YYYY-MM-DD"
                                           :disabled-date="disabledDate"/>
                        </div>
                        <a>{{basicOwn.rental|number_chinese}}</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="forumrow">
                        <table class="othertable" border="0" align="center" cellpadding="5" cellspacing="1"
                               style="width: 100%;border-collapse:separate;">
                            <thead>
                            <tr>
                                <th rowspan="2" style="width: 80px;">彩种综合占成设置</th>
                                <th colspan="1" rowspan="2" style="width: 160px;">
                                    <div>
                                        <template v-if="basicUpper.userLevel===1">
                                            <span>后桶占成</span>
                                        </template>
                                        <template v-else>
                                            <span>{{basicUpper.usernameLabel}}最高占成</span>
                                        </template>
                                    </div>
                                </th>
                                <template v-if="basicUpper.userLevel!==1 && editUserType!=='MEMBER'">
                                    <th colspan="2" rowspan="1">
                                        <div><span>{{basicUpper.adduserLabel}}占成</span></div>
                                    </th>
                                    <!--<th colspan="1" rowspan="2" style="width: 100px;">
                                        <div><span>开放补货</span><br>（按占成分配）</div>
                                    </th>-->
                                </template>
                            </tr>
                            <template v-if="basicUpper.userLevel!==1 && editUserType!=='MEMBER'">
                                <tr>
                                    <th colspan="1" rowspan="1">
                                        <div><span>最低</span></div>
                                    </th>

                                    <th colspan="1" rowspan="1">
                                        <div><span>最高</span></div>
                                    </th>
                                </tr>
                            </template>
                            </thead>
                            <tbody>
                            <template v-for="([name,items],i) in types">
                                <tr class="pk10">
                                    <td class="forumrow fontwe" @click="toggle(i)">
                                        <span style="cursor:pointer">{{name}}
                                            <a-icon type="down"/>
                                        </span>
                                    </td>
                                    <td class="forumrowhighlight">
                                        <a-input-number style="width: 60px"
                                                        size="small" @focus="focus($event)"
                                                        v-model="ownPre[name].ptPct"
                                                        :min="upperPre[name].ptMinPct"
                                                        :max="upperPre[name].ptMaxPct"
                                                        @change="onChangePre(name,'ptPct')"
                                        />
                                        %
                                        ({{upperPre[name].ptMinPct}}% 至 {{upperPre[name].ptMaxPct}}%)
                                    </td>
                                    <template v-if="basicUpper.userLevel!==1 && editUserType!=='MEMBER'">
                                        <td class="forumrowhighlight">
                                            <a-input-number style="width: 60px"
                                                            size="small" @focus="focus($event)"
                                                            v-model="ownPre[name].ptMinPct"
                                                            :min="0"
                                                            :max="ownPre[name].ptMaxPct||0"
                                                            @change="onChangePre(name,'ptMinPct')"
                                            />
                                            %
                                            (最低 0%)
                                        </td>
                                        <td class="forumrowhighlight">
                                            <a-input-number style="width: 60px"
                                                            size="small" @focus="focus($event)"
                                                            v-model="ownPre[name].ptMaxPct"
                                                            :min="0"
                                                            :max="upperPre[name].ptMaxPct"
                                                            @change="onChangePre(name,'ptMaxPct')"
                                            />
                                            %
                                            (最高 {{upperPre[name].ptMaxPct}}%)
                                        </td>
                                        <!--<td class="forumrowhighlight">
                                            <a-switch checked-children="开" un-checked-children="关"
                                                      v-model="ownPre[name].ptBh" @change="changeBh(name)"/>
                                        </td>-->
                                    </template>
                                </tr>
                                <template v-if="listShow[i]">
                                    <tr v-for="item in items">
                                        <td class="forumrow fontwe">
                                            {{item}}
                                        </td>
                                        <td class="forumrowhighlight">
                                            <a-input-number style="width: 60px"
                                                            size="small" @focus="focus($event)"
                                                            v-model="ownPre[item].ptPct"
                                                            :min="upperPre[item].ptMinPct"
                                                            :max="upperPre[item].ptMaxPct"
                                                            @change="onChangeMinMaxPt(item)"
                                            />
                                            %
                                            ({{upperPre[item].ptMinPct}}% 至 {{upperPre[item].ptMaxPct}}%)
                                        </td>
                                        <template v-if="basicUpper.userLevel!==1  && editUserType!=='MEMBER'">
                                            <td class="forumrowhighlight">
                                                <a-input-number style="width: 60px"
                                                                size="small" @focus="focus($event)"
                                                                v-model="ownPre[item].ptMinPct"
                                                                :min="0"
                                                                :max="ownPre[item].ptMaxPct||0"
                                                />
                                                %
                                                (最低 0%)
                                            </td>
                                            <td class="forumrowhighlight">
                                                <a-input-number style="width: 60px"
                                                                size="small" @focus="focus($event)"
                                                                v-model="ownPre[item].ptMaxPct"
                                                                :min="0"
                                                                :max="upperPre[item].ptMaxPct"
                                                />
                                                %
                                                (最高 {{upperPre[item].ptMaxPct}}%)
                                            </td>
                                            <!--<td class="forumrowhighlight">
                                                <a-tooltip>
                                                    <template slot="title" v-if="!upperPre[item].ptBh">
                                                        上级无权限
                                                    </template>
                                                    <a-switch checked-children="开" un-checked-children="关"
                                                              v-model="ownPre[item].ptBh" :disabled="!upperPre[item].ptBh"/>
                                                </a-tooltip>
                                            </td>-->
                                        </template>
                                    </tr>
                                </template>

                            </template>
                            </tbody>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </a-spin>
<!--        <div class="opnewinright">-->
<!--            <a-button :style="{ marginRight: '12px' }" size="small" @click="onClose" :loading="saveLoading">-->
<!--                取消-->
<!--            </a-button>-->
<!--            <a-button type="primary" size="small" @click="saveUser()" :loading="saveLoading">-->
<!--                <span v-if="!saveLoading">确定</span> <span v-else> 提交中...</span>-->
<!--            </a-button>-->
<!--        </div>-->

    </a-modal>
</template>

<script>
    import to from "await-to-js";
    import moment from 'moment';
    export default {
        name: "user-add",
        props: {
            userAddShow: {
                type: Boolean
            },
            editParentId: null,
            editUserType: null,
            addUserName: ''
        },
        data() {
            return {
                value: 0,
                maskClosable:false,
                recording: false,
                listShow: [false, false, false],
                types: null,
                upperPre: [],
                ownPre: [],
                basicUpper: {},
                handicaps: '',
                basicOwn: {},
                saveLoading: false,
                spinning:false,
                //hList:[0.6,1,2,3],//默认的盘口值
                maxWinRadio: 'wxz',
            }
        },
        methods: {
            moment,
            range(start, end) {
                const result = [];
                for (let i = start; i < end; i++) {
                    result.push(i);
                }
                return result;
            },
            focus(event) {
                event.currentTarget.select();
            },
            onClose(type) {
                if (!this.saveLoading) {
                    this.$emit('update:userAddShow', false)
                    if(type===1){//没操作的不刷新
                        this.$parent.loadUserList();//调用父页面方法刷新
                    }
                }
            },
            changeDate(date, str) {
                this.basicOwn.rentalStartDate = str;
            },
            disabledDate(current) {
                return  current < moment().subtract(1, 'day');
            },
            changeBh(ownPre) {
                for (let k in this.ownPre) {
                    let v = this.ownPre[k];
                    if (v.groupName === ownPre && this.upperPre[k].ptBh) {
                        v.ptBh = this.ownPre[ownPre].ptBh;
                    }
                }
            },
            onChangeMinMaxPt(item) {
                if (this.basicUpper.userLevel === 1) {//后桶开公司时
                    this.ownPre[item]['ptMaxPct'] = 100 - this.ownPre[item]['ptPct'];
                    this.ownPre[item]['ptMinPct'] = 100 - this.ownPre[item]['ptPct'];
                }
            },
            onChangePre(ownPre, type) {
                for (let k in this.ownPre) {
                    let v = this.ownPre[k];
                    if (ownPre === "赛车飞艇组") {//赛车飞艇组的修改全部 其他的修改自己组
                        v[type] = this.ownPre[ownPre][type];
                    } else {
                        if (v.groupName === ownPre) {
                            v[type] = this.ownPre[ownPre][type];
                        }
                    }
                    if(type==="ptPct"){
                        if(v[type]<this.upperPre[k]['ptMinPct']){//当群改值 小于最少值给能添加的最少值
                            v[type]=this.upperPre[k]['ptMinPct'];
                        }
                    }
                    if(type==="ptMinPct"){
                        if(v[type]>this.ownPre[k]['ptMaxPct']){//当群改值 大于最大值给能添加的最大值
                            v[type]=this.ownPre[k]['ptMaxPct'];
                        }
                    }else {
                        if (v[type] > this.upperPre[k]['ptMaxPct']) {//当群改值 大于最大值给能添加的最大值
                            v[type] = this.upperPre[k]['ptMaxPct'];
                        }
                    }
                    // console.log(v);
                    if (this.basicUpper.userLevel === 1) {//后桶开公司时
                        v['ptMaxPct'] = 100 - v[type];
                        v['ptMinPct'] = 100 - v[type];
                    }
                }
                // console.log("this.ownPre",this.ownPre)
            },
            toggle: function (i) {
                this.$set(this.listShow, i, !this.listShow[i]);
            },
            async loadUserPrePt() {
                let [err, data] = await to(this.$api.user.findPrePt(this.editParentId));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                let strMap = new Map();
                data.data.forEach(item => {
                    if (strMap.has(item.groupName)) {
                        strMap.get(item.groupName).push(item.lotteryName)
                    } else {
                        strMap.set(item.groupName, [item.lotteryName])
                    }
                    this.upperPre[item.lotteryName] = item;
                });
                this.types = strMap;
                strMap.forEach((pre, key) => {
                    let group = data.data.filter(e => {
                        return e.groupName === key;
                    });
                    let ptMaxPct = Math.max.apply(Math, group.map(o => {
                        return o.ptMaxPct
                    }));
                    let ptMinPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMinPct
                    }));
                    let ptPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMinPct
                    }));
                    this.upperPre[key] = {ptMaxPct, ptMinPct, ptPct, ptBh: true};
                });
                for (let key in this.upperPre) {
                    let pre = this.upperPre[key];
                    let ptPct = pre.ptMinPct;
                    let ptMaxPct = pre.ptMaxPct;
                    let ptMinPct = 0;
                    if (!pre.lotteryId) {
                        ptPct = pre.ptPct;
                        let group = data.data.filter(e => {
                            return e.groupName === key;
                        });
                        ptMaxPct = Math.min.apply(Math, group.map(o => {
                            return o.ptMaxPct
                        }));
                    }
                    if (this.basicUpper.userLevel === 1) {
                        ptPct = 5;
                        ptMinPct = 100 - ptPct;
                        ptMaxPct = 100 - ptPct;
                    }
                    this.$set(this.ownPre, key, {
                        groupName: pre.groupName,
                        lotteryId: pre.lotteryId,
                        ptBh: pre.ptBh,
                        ptMinPct: ptMinPct,
                        ptMaxPct: ptMaxPct,
                        ptPct: ptPct
                    });
                }
                this.spinning = false;
            },
            async loadUserBase() {
                this.spinning = true;
                //this.hList=[0.6,1,2,3];//默认的盘口值
                this.basicOwn = {
                    //marketSelect: ['SG','IDC'],
                    nickName: '',
                    username: '',
                    password: '',
                    securityCode: Math.floor(Math.random() * (99999 - 10000)) + 10000,
                    siteName: '',
                    loginIpBound: '',
                    creditBalance: 0,
                    rechargeBalance: 0,
                    reserveModel: '',
                    commBack: 0,
                    rental: 0,
                    ptModel: 'MEMBER_PT',
                    handicap: [],
                    handicapSelect: [],
                    maxUser: 300,
                    gainOdds: 'OPEN',
                    controlOdds: 'OPEN',
                    disk: true,
                    bhStatus:true,
                    marketValue:[],
                    maxDiff:0,
                    maxWin: 10000,
                };
                //打开页面时清空之前数据
                //console.log(this.editParentId)
                let [err, data] = await to(this.$api.user.findUserBasic(this.editParentId));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.basicUpper = data.data;
                this.basicUpper.usernameLabel = this.$t(this.basicUpper.userType + this.basicUpper.userLevel);
                if (this.editUserType === 'AGENT') {
                    this.basicUpper.adduserLabel = this.$t(this.basicUpper.userType + (this.basicUpper.userLevel + 1));
                } else {
                    this.basicUpper.adduserLabel = this.$t('MEMBER');
                }
                if (this.basicUpper.userLevel <= 2) {
                    this.basicUpper.rechargeBalance = 9999999999999;
                    this.basicUpper.creditBalance = 9999999999999;
                }
                this.basicUpper.addlowerLabel = this.$t(this.basicUpper.userType + (this.basicUpper.userLevel + 2));
                this.basicOwn.gainOdds = data.data.gainOdds;
                this.basicOwn.controlOdds = data.data.controlOdds;
                this.basicOwn.bhStatus = data.data.bhStatus;
                this.basicUpper.markets = data.data.marketOpen.split(",");
                this.basicUpper.marketValue = data.data.marketValue.split(",");
                this.basicUpper.handicap = this.basicUpper.marketOpen.split(",");
                this.basicUpper.handicapSelect = this.basicUpper.marketSelect.split(",");
                this.basicOwn.reserveModel = this.basicUpper.reserveModel;
                if (this.basicUpper.reserveModel === 'OMNIBUS') {
                    this.basicOwn.reserveModel = '';
                }
                if (this.editUserType === 'MEMBER') {
                    this.basicUpper.handicaps = this.basicUpper.marketOpen.split(",");
                } else {
                    this.basicOwn.handicap = data.data.marketOpen.split(",");
                    this.basicOwn.handicapSelect = data.data.marketSelect.split(",");
                }
                this.$refs.USERNAME.focus();
                this.loadUserPrePt();
            },
            async initAddInfo() {
                this.loadUserBase();
            },
            saveUser() {
                if (this.checkUser()) {
                    this.saveLoading = true;
                    this.basicOwn.superId = this.basicUpper.userId;
                    this.basicOwn.userType = this.editUserType;
                    //if (this.editUserType === 'AGENT') {
                        this.basicOwn.commBack = this.basicOwn.commBack / 10;
                    //}
                    let params = Object.assign({prePt: []}, this.basicOwn);
                    params.parentId = this.editParentId;
                    for (let key in this.ownPre) {
                        let pre = this.ownPre[key];
                        if (pre.lotteryId) {
                            params.prePt.push(pre);
                        }
                    }
                    params.marketValue=this.basicUpper.marketValue.join(",");
                    if (this.maxWinRadio === 'wxz') {
                        params.maxWin = -1;
                    }
                    //console.log("params",params)
                    //this.saveLoading = false;
                    this.$api.user.saveUserBasic(params).then(res => {
                        if (res.success) {
                            this.saveLoading = false;
                            this.onClose(1);
                            if (res.data.userLevel === 2) {
                                this.loadUserBase();
                                this.$parent.showCopyCompanyModal(res.data.userId, res.data.username);//调用父页面方法刷新
                            }else if (res.data.userLevel === 3) {
                                this.$parent.showCopyAgent1Modal(res.data.userId, res.data.username);//调用父页面方法刷新
                            } else {
                                this.$parent.showZhuanRegressModal(res.data.userId, res.data.username);
                                //this.$utils.handleThen(res, this, "添加成功！");
                            }
                            if(res.data.userType==="MEMBER"){//开会员后刷新 用户管理页面显示会员
                                this.$parent.showLower(this.basicUpper,'member');
                            }
                        } else {
                            this.saveLoading = false;
                            this.$utils.handleThen(res, this, "添加失败！");
                        }
                    });
                }
            },
            toCopyHandicap(res) {
                const self = this;
                this.saveLoading = false;
                this.$confirm({
                    title: '提示',
                    content: '是否需要复制盘口设置',
                    okText: '确认',
                    onOk() {
                        self.onClose(1);
                        self.$parent.showOddsModal(res.data.userId, res.data.username);//调用父页面方法刷新
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.$utils.handleThen(res, self);
                        self.onClose(1);
                    },
                });
            },
            checkUser() {
                let checked = false;
                this.checkCallBack(() => {
                    this.$utils.checkUserName(this.basicOwn.username, this.basicOwn.username + "帐号格式不符合,请重新设置！");
                    this.$utils.checkPassword(this.basicOwn.password, "密码格式不符合,请重新设置!");
                    if (this.basicUpper.userLevel === 1) {
                        this.basicOwn.bhStatus=true;//开公司时
                        this.basicOwn.reserveModel='OMNIBUS';
                        this.$utils.checkSecondary(this.basicOwn.secondary, "安保密码格式不符合,请重新设置!");
                    }
                    this.$utils.checkIP(this.basicOwn.loginIpBound, "IP地址错误");
                    if(this.basicOwn.nickName!==""){
                        let name="";
                        if(this.basicUpper.userLevel === 1){
                            name="公司";
                        }else{
                            name=this.editUserType==="AGENT"?this.$t('AGENT'+(this.basicUpper.userLevel+1)):"会员";
                        }
                        this.$utils.checkNickName(this.basicOwn.nickName,name+"名称长度应该在2-8位,请重新设置!");
                    }
                    if(this.basicOwn.siteName!==""){
                        this.$utils.checkSiteName(this.basicOwn.siteName,"网站名称长度应该在1-5位,请重新设置!");
                    }
                    this.checkNull(this.basicOwn.reserveModel, "必须选择一个额度模式！");
                    this.$utils.checkSecurityCode(this.basicOwn.securityCode, "搜索码格式不符合,请重新设置!");
                    if (this.basicUpper.userLevel > 1) {
                        if (this.handicaps) {
                            this.basicOwn.handicap = [this.handicaps];
                        }
                        this.checkNull(this.basicOwn.handicap, "必须选择一个盘口！");
                        if(this.editUserType==="AGENT"){
                            this.checkNull(this.basicOwn.handicapSelect, "必须选择一个投注模式！");
                            this.basicOwn.marketSelect = this.basicOwn.handicapSelect.join(",");
                        }else{
                            this.checkNull(this.basicOwn.marketSelect, "必须选择一个投注模式！");
                        }
                        this.basicOwn.handicap.sort((a, b) => a.replace("IDC", "SG").localeCompare(b.replace("IDC", "SG")));
                        this.basicOwn.marketOpen = this.basicOwn.handicap.join(",");

                    }else {//添加公司时
                        this.basicOwn.marketSelect ="SG,IDC";
                    }
                    checked = true;
                });
                return checked;
            },
            onChangeHandicap(checkedValues) {
                this.basicOwn.handicap = checkedValues;
            },
            onChangeMarketSelect(checkedValues) {
                this.basicOwn.marketSelect = checkedValues;
                console.log(this.basicOwn.marketSelect)
            },
            handleChangeCommBack(value) {
                this.basicOwn.commBack = value;
            },
            handleGainOdds(checked){/*赚赔开关*/
                if(checked){
                    this.basicOwn.gainOdds="OPEN";
                }else{
                    this.basicOwn.gainOdds="CLOSE";
                }
            },
            handleControlOdds(checked){/*控盘开关*/
                if(checked){
                    this.basicOwn.controlOdds="OPEN";
                }else{
                    this.basicOwn.controlOdds="CLOSE";
                }
            },
            handleBhStatus(checked){
                this.basicOwn.bhStatus=checked;
            }
        },
        watch: {
            'userAddShow': {
                handler: function (val, old) {
                    if (val) {
                        Object.assign(this.$data, this.$options.data());
                        this.initAddInfo();
                    }
                }
            }
        }
    }
</script>

<style scoped>
    .container a-input-number::-webkit-outer-spin-button,
    .container a-input-number::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }

    .container a-input-number {
        -moz-appearance: textfield;
    }
</style>

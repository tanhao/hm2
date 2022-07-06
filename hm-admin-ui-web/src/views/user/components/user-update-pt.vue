<template>
    <!--修改-->
    <a-modal
            :title="updatePtName"
            :width="1000"
            :visible="userUpdatePtShow"
            :body-style="{ paddingBottom: '0px'}"
            :maskClosable="maskClosable"
            dialogClass="tk"
            @cancel="onClose"
    >
        <template slot="footer">
            <!--<a-button key="back" @click="onClose">
                取消
            </a-button>-->
            <a-button key="submit" v-if="!toBetShow" type="primary" size="small" @click="updatePt">
                修改占成
            </a-button>
            <a-button key="submit" v-else type="primary" size="small" @click="updatePresetPt">
                修改占成
            </a-button>
        </template>
        <a-spin :spinning="spinning">
            <!--公司至十级代修改占成-->
            <table class="othertable noinptonum" v-if="editUserType==='AGENT' && editUserLevel>2" border="0" align="center" cellpadding="5" cellspacing="1"
                   style="width: 100%;border-collapse:separate;">
                <thead>
                <tr>
                    <th rowspan="2">彩种综合占成设置</th>
                    <th colspan="3">当前占成</th>
                    <th colspan="3">预设占成</th>
                </tr>
                <tr>
                    <th>{{$t(editUserType+(editUserLevel-1)) }}最高占成</th>
                    <th>下层最低</th>
                    <th>下层最高</th>
                    <th>{{$t(editUserType+(editUserLevel-1))}}最高占成</th>
                    <th>下成最低</th>
                    <th>下层最高</th>
                </tr>
                </thead>
                <tbody>
                    <template  v-for="([name,items],i) in types">
                        <tr class="pk10"  v-if="presetOwnPre[name]">
                            <td class="forumrow fontwe">{{name}}</td>
                            <template v-if="toBetShow"><!-- 下线有注单时 -->
                                <td class="forumrowhighlight">
                                    {{ownPre[name].ptPct}}
                                    ({{upperPre[name].ptMinPct}}% 至 {{upperPre[name].ptMaxPct}}%)
                                </td>
                                <td class="forumrowhighlight">
                                    {{ownPre[name].ptMinPct}}
                                    (最低 0%)
                                </td>
                                <td class="forumrowhighlight">
                                    {{ownPre[name].ptMaxPct}}
                                    (最高 {{upperPre[name].ptMaxPct}}%)
                                </td>
                                <!-- 预设 -->
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="presetOwnPre[name].prePct"
                                                    :min="upperPre[name].preMinPct"
                                                    :max="upperPre[name].preMaxPct"
                                                    @change="onChangePrePt(name,'prePct')"
                                    />
                                    ({{upperPre[name].preMinPct}}% 至 {{upperPre[name].preMaxPct}}%)
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="presetOwnPre[name].preMinPct"
                                                    :min="0"
                                                    :max="presetOwnPre[name].preMaxPct||0"
                                                    @change="onChangePrePt(name,'preMinPct')"
                                    />
                                    (最低 0%)
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="presetOwnPre[name].preMaxPct"
                                                    :min="0" :max="upperPre[name].preMaxPct"
                                                    @change="onChangePrePt(name,'preMaxPct')" />
                                    (最高 {{upperPre[name].preMaxPct}}%)
                                </td>
                            </template>
                            <template v-else><!-- 下线没有注单时 -->
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="ownPre[name].ptPct"
                                                    :min="upperPre[name].ptMinPct"
                                                    :max="upperPre[name].ptMaxPct"
                                                    @change="onChangePre(name,'ptPct')"
                                    />
                                    ({{upperPre[name].ptMinPct}}% 至 {{upperPre[name].ptMaxPct}}%)
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="ownPre[name].ptMinPct"
                                                    :min="0"
                                                    :max="ownPre[name].ptMaxPct||0"
                                                    @change="onChangePre(name,'ptMinPct')"
                                    />
                                    (最低 0%)
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="ownPre[name].ptMaxPct"
                                                    :min="0"
                                                    :max="upperPre[name].ptMaxPct"
                                                    @change="onChangePre(name,'ptMaxPct')"
                                    />
                                    (最高 {{upperPre[name].ptMaxPct}}%)
                                </td>
                                <!-- 预设 -->
                                <td class="forumrowhighlight">
                                    {{presetOwnPre[name].prePct}}
                                    ({{upperPre[name].preMinPct}}% 至 {{upperPre[name].preMaxPct}}%)
                                </td>
                                <td class="forumrowhighlight">
                                    {{presetOwnPre[name].preMinPct}}
                                    (最低 0%)
                                </td>
                                <td class="forumrowhighlight">
                                    {{presetOwnPre[name].preMaxPct}}
                                    (最高 {{upperPre[name].preMaxPct}}%)
                                </td>
                            </template>
                        </tr>
                        <tr v-for="item in items" v-if="presetOwnPre[name]">
                            <td class="forumrow fontwe">{{item}}</td>
                            <template v-if="toBetShow"><!-- 下线有注单时 -->
                                <td class="forumrowhighlight">
                                    {{ownPre[item].ptPct}}
                                    ({{upperPre[item].ptMinPct}}% 至 {{upperPre[item].ptMaxPct}}%)
                                </td>
                                <td class="forumrowhighlight">
                                    {{ownPre[item].ptMinPct}}
                                    (最低 0%)
                                </td>
                                <td class="forumrowhighlight">
                                    {{ownPre[item].ptMaxPct}}
                                    (最高 {{upperPre[item].ptMaxPct}}%)
                                </td>
                                <!-- 预设 -->
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="presetOwnPre[item].prePct"
                                                    :min="upperPre[item].preMinPct"
                                                    :max="upperPre[item].preMaxPct"
                                                    @change="onChangeMinMaxPre(item)"/>
                                    ({{upperPre[item].preMinPct}}% 至 {{upperPre[item].preMaxPct}}%)
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="presetOwnPre[item].preMinPct"
                                                    :min="0"
                                                    :max="presetOwnPre[item].preMaxPct||0" />
                                    (最低 0%)
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="presetOwnPre[item].preMaxPct"
                                                    :min="0"
                                                    :max="upperPre[item].preMaxPct" />
                                    (最高 {{upperPre[item].preMaxPct}}%)
                                </td>
                            </template>
                            <template v-else><!-- 下线没有注单时 -->
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="ownPre[item].ptPct"
                                                    :min="upperPre[item].ptMinPct"
                                                    :max="upperPre[item].ptMaxPct"
                                                    @change="onChangeMinMaxPt(item)"  />
                                    ({{upperPre[item].ptMinPct}}% 至 {{upperPre[item].ptMaxPct}}%)
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="ownPre[item].ptMinPct"
                                                    :min="0" :max="ownPre[item].ptMaxPct||0" />
                                    (最低 0%)
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input-number style="width: 40px"
                                                    size="small" @focus="focus($event)"
                                                    v-model="ownPre[item].ptMaxPct"
                                                    :min="0" :max="upperPre[item].ptMaxPct" />
                                    (最高 {{upperPre[item].ptMaxPct}}%)
                                </td>
                                <!-- 预设 -->
                                <td class="forumrowhighlight">
                                    {{presetOwnPre[item].prePct}}
                                    ({{upperPre[item].preMinPct}}% 至 {{upperPre[item].preMaxPct}}%)
                                </td>
                                <td class="forumrowhighlight">
                                   {{presetOwnPre[item].preMinPct}}(最低 0%)
                                </td>
                                <td class="forumrowhighlight">
                                    {{presetOwnPre[item].preMaxPct}}(最高 {{upperPre[item].preMaxPct}}%)
                                </td>
                            </template>
                        </tr>
                    </template>

                </tbody>
            </table>
            <!--后桶修改公司-->
            <table class="othertable noinptonum" v-if="editUserType==='MEMBER' || editUserLevel===2" border="0" align="center" cellpadding="5" cellspacing="1"
                   style="width: 100%;border-collapse:separate;">
               <thead>
                <tr>
                    <th rowspan="2">彩种综合占成设置</th>
                    <th>当前占成</th>
                    <th>预设占成</th>
                </tr>
                <tr>
                    <th>{{$t("AGENT"+(editUserLevel-1))}}占成</th>
                    <th>{{$t("AGENT"+(editUserLevel-1))}}预设占成</th>
                </tr>
               </thead>
                <tbody>
                    <template  v-for="([name,items],i) in types">
                    <tr v-if="presetOwnPre[name]" class="pk10">
                        <td class="forumrow fontwe">{{name}}</td>
                        <template v-if="toBetShow">
                           <td class="forumrowhighlight">
                               {{ownPre[name].ptPct}}
                               ({{upperPre[name].ptMinPct}}% 至 {{upperPre[name].ptMaxPct}}%)
                           </td>
                           <td class="forumrowhighlight">
                               <a-input-number style="width: 40px"
                                               size="small" @focus="focus($event)"
                                               v-model="presetOwnPre[name].prePct"
                                               :min="upperPre[name].preMinPct"
                                               :max="upperPre[name].preMaxPct"
                                               @change="onChangePrePt(name,'prePct')"/>
                               ({{upperPre[name].preMinPct}}% 至 {{upperPre[name].preMaxPct}}%)</td>
                        </template>
                        <template v-else>
                            <td class="forumrowhighlight">
                                <a-input-number style="width: 40px"
                                                size="small" @focus="focus($event)"
                                                v-model="ownPre[name].ptPct"
                                                :min="upperPre[name].ptMinPct"
                                                :max="upperPre[name].ptMaxPct"
                                                @change="onChangePre(name,'ptPct')" />
                                ({{upperPre[name].ptMinPct}}% 至 {{upperPre[name].ptMaxPct}}%)</td>
                            <td class="forumrowhighlight">
                                {{presetOwnPre[name].prePct}}
                                ({{upperPre[name].preMinPct}}% 至 {{upperPre[name].preMaxPct}}%)
                            </td>
                        </template>
                    </tr>
                    <tr v-for="item in items" v-if="presetOwnPre[name]">
                        <td class="forumrow fontwe">{{item}}</td>
                        <template v-if="toBetShow">
                            <td class="forumrowhighlight">
                                {{ownPre[item].ptPct}}
                                ({{upperPre[item].ptMinPct}}% 至 {{upperPre[item].ptMaxPct}}%)
                            </td>
                            <td class="forumrowhighlight">
                                <a-input-number style="width: 40px"
                                                size="small" @focus="focus($event)"
                                                v-model="presetOwnPre[item].prePct"
                                                :min="upperPre[item].preMinPct"
                                                :max="upperPre[item].preMaxPct"
                                                @change="onChangeMinMaxPre(item)"/>
                                ({{upperPre[item].preMinPct}}% 至 {{upperPre[item].preMaxPct}}%)
                            </td>
                        </template>
                        <template v-else>
                            <td class="forumrowhighlight">
                                <a-input-number style="width: 40px"
                                                size="small" @focus="focus($event)"
                                                v-model="ownPre[item].ptPct"
                                                :min="upperPre[item].ptMinPct"
                                                :max="upperPre[item].ptMaxPct"
                                                @change="onChangeMinMaxPt(item)" />
                                ({{upperPre[item].ptMinPct}}% 至 {{upperPre[item].ptMaxPct}}%)
                            </td>
                            <td class="forumrowhighlight">
                                {{presetOwnPre[item].prePct}}
                                ({{upperPre[item].preMinPct}}% 至 {{upperPre[item].preMaxPct}}%)
                            </td>
                        </template>
                    </tr>
                </template>
                </tbody>
            </table>


<!--            <div style="margin: 0 auto;width: auto;padding-top: 20px;width: 90px;">-->
<!--            <a-button  v-if="!toBetShow" type="primary" size="small" @click="updatePt">-->
<!--                修改占成-->
<!--            </a-button>-->
<!--            <a-button v-else  type="primary" size="small" @click="updatePresetPt">-->
<!--                修改预设占成-->
<!--            </a-button>-->
<!--            </div>-->
        </a-spin>
<!--        <div class="opnewinright">-->
<!--            <a-button :style="{ marginRight: '12px' }" size="small" @click="onClose">-->
<!--                关闭-->
<!--            </a-button>-->
<!--        </div>-->
    </a-modal>
</template>

<script>
    import to from "await-to-js";
    export default {
        name: "user-update-pt",
        data() {
            return {
                spinning: false,
                maskClosable:false,
                listShow: [false, false, false],
                upperPre: {},
                ownPre: {},
                oldUpperPre: [],
                oldOwnPre: [],
                types:null,
                superUser: {
                    markets: [],
                    usernameLabel: '',
                },
                toBetShow:false,//没有注单
                presetOwnPre: {},
                presetTypes:null,
                oldValue:{},//
            }
        },
        props: {
            userUpdatePtShow: {
                type: Boolean
            },
            editUserId: null,
            editParentId: null,
            updatePtName: '',
            editUserType: null,
            editUserLevel: null
        },
        methods: {
            onClose() {
                this.$emit('update:userUpdatePtShow', false);
                this.$parent.loadUserList();//调用父页面方法刷新
            },
            isPtValueEqual(a, b, userId,type) {
                let ptLogs = [];
                //取对象a和b的属性名
                let aProps = Object.getOwnPropertyNames(a);
                let bProps = Object.getOwnPropertyNames(b);
                let name="";
                //循环取出属性名，再判断属性值是否一致
                for (let i = 0; i < aProps.length; i++) {
                    let propName = aProps[i];
                    //过滤vue绑定属性ob
                    if (propName !== '__ob__') {
                        if (a[propName] !== b[propName]) {
                            if(type===1){
                                if(a[i].ptPct!==b[i].ptPct){
                                    name=a[i].lotteryName+" 占成";
                                    ptLogs.push({createType: name, oldValue: a[i].ptPct, newValue: b[i].ptPct, userId})
                                }else if(a[i].ptMinPct!==b[i].ptMinPct){
                                    name=a[i].lotteryName+" 最小占成";
                                    ptLogs.push({createType: name, oldValue: a[i].ptMinPct, newValue: b[i].ptMinPct, userId})
                                }else if(a[i].ptMaxPct!==b[i].ptMaxPct){
                                    name=a[i].lotteryName+" 最大占成";
                                    ptLogs.push({createType: name, oldValue: a[i].ptMaxPct, newValue: b[i].ptMaxPct, userId})
                                }
                            }else {
                                if(a[i].prePct!==b[i].prePct){
                                    name=a[i].lotteryName+" 预设占成";
                                    ptLogs.push({createType: name, oldValue: a[i].prePct, newValue: b[i].prePct, userId})
                                }else if(a[i].preMinPct!==b[i].preMinPct){
                                    name=a[i].lotteryName+" 最小预设占成";
                                    ptLogs.push({createType: name, oldValue: a[i].preMinPct, newValue: b[i].preMinPct, userId})
                                }else if(a[i].preMaxPct!==b[i].preMaxPct){
                                    name=a[i].lotteryName+" 最大预设占成";
                                    ptLogs.push({createType: name, oldValue: a[i].preMaxPct, newValue: b[i].preMaxPct, userId})
                                }
                            }
                        }
                    }
                }
                return ptLogs;
            },
            updatePt(){/*正常修改占成*/
                this.spinning = true;
                let params = Object.assign({prePt: [],userId:this.editUserId});
                for (let key in this.ownPre) {
                    let pre = this.ownPre[key];
                    if (pre.lotteryId) {
                        params.prePt.push(pre);
                    }
                }
                let valueEqual = this.isPtValueEqual(this.oldValue, params.prePt, params.userId,1);
                if (valueEqual.length === 0) {
                    this.spinning = false;
                   return this.$swal({ title: "无修改",confirmButtonText: '确定', icon: 'error',showConfirmButton: false});
                }
                this.$api.user.putUserPt(params).then(res => {
                    if (res.success) {
                        this.$utils.handleThen(res, this, "修改占成成功!");
                        this.$api.logs.pushRecordLogList({hmLogs: valueEqual});
                    } else {
                        this.$utils.handleThen(res, this, "修改占成失败!");
                    }
                });
                this.spinning = false;
            },
            updatePresetPt(){/*修改预设占成*/
                this.spinning = true;
                let params = Object.assign({prePt: [],userId:this.editUserId});
                for (let key in this.presetOwnPre) {
                    let pre = this.presetOwnPre[key];
                    if (pre.lotteryId) {
                        params.prePt.push(pre);
                    }
                }
                let valueEqual = this.isPtValueEqual(this.oldValue, params.prePt, params.userId,2);
                //console.log("params",params)
                if (valueEqual.length === 0) {
                    this.spinning = false;
                    return this.$swal({ title: "无修改", icon: 'error',showConfirmButton: false } );
                }
                this.$api.user.putUserPre(params).then(res => {
                    if (res.success) {
                        this.$utils.handleThen(res, this, "修改预设占成成功!");
                        this.$api.logs.pushRecordLogList({hmLogs: valueEqual});
                    } else {
                        this.$utils.handleThen(res, this, "修改预设占成失败!");
                    }
                });
                this.spinning = false;
            },
            focus(event) {
                event.currentTarget.select();
            },
            onChangeMinMaxPt(item) {
                if (this.superUser.userLevel === 1) {//后桶开公司时
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
                    if (this.superUser.userLevel === 1) {//后桶开公司时
                        v['ptMaxPct'] = 100 - v[type];
                        v['ptMinPct'] = 100 - v[type];
                    }
                }
            },
            onChangeMinMaxPre(item) {
                if (this.superUser.userLevel === 1) {//后桶开公司时
                    this.presetOwnPre[item]['preMaxPct'] = 100 - this.presetOwnPre[item]['prePct'];
                    this.presetOwnPre[item]['preMinPct'] = 100 - this.presetOwnPre[item]['prePct'];
                }
            },
            onChangePrePt(ownPre, type) {
                for (let k in this.presetOwnPre) {
                    let v = this.presetOwnPre[k];
                    if (ownPre === "赛车飞艇组") {//赛车飞艇组的修改全部 其他的修改自己组
                        v[type] = this.presetOwnPre[ownPre][type];
                    } else {
                        if (v.groupName === ownPre) {
                            v[type] = this.presetOwnPre[ownPre][type];
                        }
                    }
                    if(type==="prePct"){
                        if(v[type]<this.upperPre[k]['preMinPct']){//当群改值 小于最少值给能添加的最少值
                            v[type]=this.upperPre[k]['preMinPct'];
                        }
                    }
                    if(type==="preMinPct"){
                        if(v[type]>this.presetOwnPre[k]['preMaxPct']){//当群改值 大于最大值给能添加的最大值
                            v[type]=this.presetOwnPre[k]['preMaxPct'];
                        }
                    }else {
                        if (v[type] > this.upperPre[k]['preMaxPct']) {//当群改值 大于最大值给能添加的最大值
                            v[type] = this.upperPre[k]['preMaxPct'];
                        }
                    }
                    if (this.superUser.userLevel === 1) {//后桶开公司时
                        v['preMaxPct'] = 100 - v[type];
                        v['preMinPct'] = 100 - v[type];
                    }
                }
            },
            async loadUserPrePt() {
                this.spinning = true;
                let [err, data] = await to(this.$api.user.findPrePt(this.editUserId));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                let strMap = new Map();
                this.oldValue = JSON.parse(JSON.stringify(data.data))//{ ...data.data};
                data.data.forEach(item => {
                    if (strMap.has(item.groupName)) {
                        strMap.get(item.groupName).push(item.lotteryName)
                    } else {
                        strMap.set(item.groupName, [item.lotteryName])
                    }
                    if(item.prePct===null){
                        //自己预设占成是否为空
                        item.prePct=item.ptPct;
                        item.preMinPct=item.ptMinPct;
                        item.preMaxPct=item.ptMaxPct;
                    }
                    this.$set(this.ownPre, item.lotteryName, item)
                });
                strMap.forEach((pre, key) => {
                    let group = data.data.filter(e => {
                        return e.groupName === key;
                    });
                    let ptMaxPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMaxPct
                    }));
                    let ptMinPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMinPct
                    }));
                    let ptPct = Math.min.apply(Math, group.map(o => {
                        return o.ptPct
                    }));
                    let preMaxPct = Math.min.apply(Math, group.map(o => {
                        return o.preMaxPct
                    }));
                    let preMinPct = Math.max.apply(Math, group.map(o => {
                        return o.preMinPct
                    }));
                    let prePct = Math.max.apply(Math, group.map(o => {
                        return o.prePct
                    }));
                    this.$set(this.ownPre, key, {
                        ptMaxPct,
                        ptMinPct,
                        ptPct,
                        preMaxPct,
                        preMinPct,
                        prePct,
                        ptBh: true
                    })
                });
                let [err1, data1] = await to(this.$api.user.findPrePt(this.editParentId));
                if (err1 || !data1.success) {
                    this.spinning = false;
                    return;
                }
                let strMap1 = new Map();
                data1.data.forEach(item => {
                    if (strMap1.has(item.groupName)) {
                        strMap1.get(item.groupName).push(item.lotteryName)
                    } else {
                        strMap1.set(item.groupName, [item.lotteryName])
                    }
                    if(item.prePct===null){//上级数据预设占成是否为空
                        item.preMinPct=item.ptMinPct;
                        item.preMaxPct=item.ptMaxPct;
                    }
                    this.upperPre[item.lotteryName] = item;
                });
                this.types = strMap1;
                strMap1.forEach((pre, key) => {
                    let group = data1.data.filter(e => {
                        return e.groupName === key;
                    });
                    let ptMaxPct = Math.max.apply(Math, group.map(o => {
                        return o.ptMaxPct
                    }));
                    let ptMinPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMinPct
                    }));
                    let ptPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMaxPct
                    }));
                    let preMaxPct = Math.min.apply(Math, group.map(o => {
                        return o.preMaxPct
                    }));
                    let preMinPct = Math.max.apply(Math, group.map(o => {
                        return o.preMinPct
                    }));
                    let prePct = Math.max.apply(Math, group.map(o => {
                        return o.prePct
                    }));
                    this.upperPre[key] = {ptMaxPct, ptMinPct, ptPct, preMaxPct, preMinPct, prePct};
                });
                //this.spinning = false;
                this.countOrder();
            },
            async countOrder(){/*是否有注单*/
                //this.spinning = true;
                let [err, data] = await to(this.$api.user.countOrder(this.editUserId));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.toBetShow=data.data.preset;
                if(this.toBetShow){//有注单 上级没有预设占成用上级占成 上级有预设占成用上级预设占成
                    let strMap = new Map();
                    data.data.list.forEach(item => {
                        if (strMap.has(item.groupName)) {
                            strMap.get(item.groupName).push(item.lotteryName)
                        } else {
                            strMap.set(item.groupName, [item.lotteryName])
                        }
                        if(item.prePct===null){//上级数据预设占成是否为空
                            item.prePct=item.ptPct;
                            item.preMinPct=item.ptMinPct;
                            item.preMaxPct=item.ptMaxPct;
                        }
                    });
                    this.presetTypes = strMap;
                    strMap.forEach((pre, key) => {
                        let group = data.data.list.filter(e => {
                            return e.groupName === key;
                        });
                        let preMaxPct = Math.min.apply(Math, group.map(o => {
                            return o.preMaxPct
                        }));
                        let preMinPct = Math.max.apply(Math, group.map(o => {
                            return o.preMinPct
                        }));
                        let prePct = Math.max.apply(Math, group.map(o => {
                            return o.prePct
                        }));
                    });
                    this.presetOwnPre = Object.assign([], this.ownPre);
                }else{//没有注单 用当前自己占成
                    this.presetOwnPre = Object.assign([], this.ownPre);
                    this.presetTypes = this.types;
                }
                this.spinning=false;
            },
        },
        watch: {
            'userUpdatePtShow': {
                handler: function (val, old) {
                    if (val) {
                        Object.assign(this.$data, this.$options.data());
                        this.loadUserPrePt();
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>

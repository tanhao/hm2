<template>
    <div>
        <a-spin :spinning="spinning" size="large">
            <div class="mb10 df">
                <div class="pl10">
                    <a-button type="primary" icon="search" size="small" @click="loadSonList">
                        刷新
                    </a-button>
                </div>
            </div>
            <a-row :gutter="16">
                <a-col :span="16">
                    <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                        <tbody>
                        <tr>
                            <th>账号名称</th>
                            <th>姓名</th>
                            <th>新增日期</th>
                            <th v-if="!enabledSon">账号状态</th>
                            <th>登录状态</th>
                            <th>功能</th>
                        </tr>
                        <tr v-for="item in sonList">
                            <td class="forumrow blue">{{ item.username }}</td>
                            <td class="forumrowhighlight">{{ item.nickName }}</td>
                            <td class="forumrowhighlight"><span>{{ moment(item.created).format("YYYY-MM-DD") }}</span>
                            </td>
                            <td class="forumrowhighlight"  v-if="!enabledSon">
                                <div style="text-align:center ">
                                    <a-radio-group name="radioGroup" v-model="item.status"
                                                   @change="changeSonStatus(item)">
                                        <a-radio value="OPEN">
                                            启用
                                        </a-radio>
                                        <a-radio value="CLOSE">
                                            停用
                                        </a-radio>
                                    </a-radio-group>
                                </div>
                            </td>
                            <td class="forumrowhighlight">
                                <div style="text-align:center ">
                                    <span v-if="item.passwordError<5">
                                        正常({{item.passwordError}})
                                    </span>
                                    <div v-else>
                                        锁定({{item.passwordError}})
                                        <a-button type="danger" size="small" @click="changeResetPwd(item.username)">
                                            解锁
                                        </a-button>
                                    </div>
                                </div>
                            </td>
                            <td class="forumrowhighlight">
                                <a-button type="primary" icon="edit" size="small" @click="showuser(item)" v-if="!enabledSon">
                                    修改
                                </a-button>
                                <a-button type="primary"  size="small" @click="userRecordShow=true;params.userId=item.sonId">
                                    记录
                                </a-button>
                            </td>
                        </tr>
                        <tr v-if="sonList.length===0">
                            <td colspan="6" class="forumrowhighlight nohover">
                                <a-empty/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </a-col>
                <a-col :span="8" v-if="!enabledSon">
                    <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1"
                           style="border-collapse: separate;width: 100%">
                        <tr>
                            <th height="22" colspan="2" sytle="line-height:150%" class="txtleft pl10">新增子账号</th>
                        </tr>
                        <tbody>
                        <tr>
                            <td width="80" class="forumrow txtright">账号</td>
                            <td class="forumrowhighlight txtleft">
                                <a-input size="small" allow-clear style="width: 160px" v-model="addSon.username"/>
                                <span class="red"><i color="red">*</i>  [2-10]长度，字母，@，数字组合</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright">名称</td>
                            <td class="forumrowhighlight txtleft">
                                <a-input size="small" allow-clear style="width: 160px" v-model="addSon.nickName"/>
                                <span> [2-8]长度，可以输入中文</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright">密码</td>
                            <td class="forumrowhighlight txtleft">
                                <a-input-password allow-clear placeholder="登陆密码" size="small" style="width: 160px"
                                                  v-model="addSon.password"/>
                                <span class="red"><i color="red">*</i> [8-16]長度,最少二个字母(0-9,a-z,@)</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright">权限</td>
                            <td class="forumrowhighlight txtleft">
                                <a-tree v-model="checkedKeys" checkable
                                        :expanded-keys="expandedKeys"
                                        :selected-keys="selectedKeys"
                                        :tree-data="treeMenu"
                                        @check="onCheck"
                                        @expand="onExpand"/>
                            </td>
                        </tr>
                        <tr>
                            <td height="50" align="right" class="forumrow txtright"></td>
                            <td class="forumrowhighlight txtleft">
                                <a-button type="primary" icon="plus" size="small" @click="saveSon">
                                    新增
                                </a-button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </a-col>
            </a-row>
            <!--修改抽屉-->
            <a-modal title="修改子账号" :width="450" :visible="visible" :maskClosable="maskClosable" dialogClass="tk"
                     :body-style="{ paddingBottom: '0px'}"
                     @cancel="onClose" @ok="updateSon">
                <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1"
                       style="border-collapse: separate;width: 100%">
                    <tbody>
                    <tr>
                        <td width="80" class="forumrow txtright">账号</td>
                        <td class="forumrowhighlight txtleft">
                            <a-input size="small" allow-clear style="width: 160px" placeholder="sun"
                                     v-model="editSon.username" disabled/>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">名称</td>
                        <td class="forumrowhighlight txtleft">
                            <a-input size="small" allow-clear style="width: 160px" v-model="editSon.nickName"/>
                            <br>
                            <span class="red mt5 dstable"><i color="red">*</i> 【2-10】长度，字母，@，数字组合 </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">新密码</td>
                        <td class="forumrowhighlight txtleft">
                            <a-input-password allow-clear placeholder="登陆密码" v-model="editSon.password" size="small"
                                              style="width: 160px"/>
                            <br>
                            <span class="red mt5 dstable"><i color="red">*</i> 【8-16】长度，最少二个字母，@，数字组合 例 xx123456</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">权限</td>
                        <td class="forumrowhighlight txtleft">
                            <a-tree v-model="editCheckedKeys" checkable
                                    :expanded-keys="editExpandedKeys"
                                    :selected-keys="editSelectedKeys"
                                    :tree-data="treeMenu"
                                    @check="onEditCheck"
                                    @expand="onEditExpand"/>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <!--                <div class="opnewinright">-->
                <!--                    <a-button :style="{ marginRight: '12px' }" size="small" @click="onClose">-->
                <!--                        取消-->
                <!--                    </a-button>-->
                <!--                    <a-button type="primary" size="small" @click="updateSon">-->
                <!--                        确定-->
                <!--                    </a-button>-->
                <!--                </div>-->
            </a-modal>

            <!--查询日志-->
            <a-modal
                    title="操作记录"
                    :width="800"
                    :visible="userRecordShow"
                    :maskClosable="maskClosable"
                    dialogClass="tk"
                    :footer="null"
                    @cancel="userSonClose"
            >
                <div>
                    <div class="mb10 df">
                        <span class="maintxt mlr10">日期:</span>
                        <a-range-picker @change="onChanges" size="small" :disabled-date="disabledDate"
                                        :value="[moment(params.startTime, 'YYYY-MM-DD'), moment(params.endTime,'YYYY-MM-DD')]"style="width: 250px">
                            <a-icon slot="suffixIcon" type="smile" />
                        </a-range-picker>
                        <div class="pl10">
                            <a-button type="primary" icon="search" size="small" @click="toSel">
                                查询
                            </a-button>
                        </div>
                    </div>
                    <a-spin :spinning="spinning" >
                        <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1"
                               style="border-collapse: separate;width: 100%;">
                            <tbody>
                            <tr>
                                <th class="popth">变更时间</th>
                                <th class="popth">变更类别</th>
                                <th class="popth">原始值</th>
                                <th class="popth">变更值</th>
                                <th class="popth">变更人</th>
                                <th class="popth">IP</th>
                                <th class="popth">IP归属地</th>
                            </tr>
                            <tr v-for="item in logsList">
                                <td width="80" class="forumrow">{{moment(item.createTime).format("YYYY-MM-DD HH:mm:ss")}}</td>
                                <td width="150" class="forumrowhighlight">{{item.createType }}</td>
                                <td width="80" class="forumrowhighlight">{{/^\d+$/.test(item.oldValue)?item.oldValue:$t(item.oldValue)}}</td>
                                <td width="80" class="forumrowhighlight">{{/^\d+$/.test(item.newValue)?item.newValue:$t(item.newValue)}}</td>
                                <td width="" class="forumrowhighlight">{{item.createUser }}</td>
                                <td width="" class="forumrowhighlight">{{item.createIp }}</td>
                                <td width="" class="forumrowhighlight">{{item.address}}</td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="" style="text-align: center;">
                            <a-pagination size="small" :total="userCount" show-size-changer show-quick-jumper :current="params.page" :pageSize="params.size" @change="changePage" @showSizeChange="changePage" :show-total="total => `共 ${total} 条`" />
                        </div>
                    </a-spin>
                </div>
            </a-modal>
        </a-spin>
    </div>
</template>

<script>
export default {
    data() {
        return {
            enabledSon:this.$store.state.user.info.enabledSon,
            maskClosable: false,
            spinning: false,
            visible: false,
            treeMenu: [],
            expandedKeys: [],
            checkedKeys: [],
            selectedKeys: [],
            sonList: [],
            sonCount: 0,
            userParams: {
                page: 1,
                size: 1000,
            },
            addSon: {
                username: '',
                password: '',
                nickName: '',
                menus: [],
            },
            editSon: {
                username: '',
                password: '',
                nickName: '',
                menus: [],
            },
            editMenu: [],
            editExpandedKeys: [],
            editCheckedKeys: [],
            editSelectedKeys: [],
            userRecordShow:false,
            logsList:{},
            params: {
                page: 1,
                size: 10,
                userId:'',
                startTime:'',
                endTime:'',
            },
            userCount: 0,
        };
    },
    methods: {
        disabledDate(current) {
            return current && current > this.moment().endOf('day');
        },
        toSel(){
            this.params.page=1;
            this.selHmUserSonLogList();
        },
        onChanges(value, dateString) {
            this.params.startTime=dateString[0];
            this.params.endTime=dateString[1];
            this.selHmUserSonLogList();
        },
        userSonClose() {
            this.userRecordShow=false;
        },
        changePage(page, pageSize) {
            this.params.page = page;
            this.params.size = pageSize;
            this.selHmUserSonLogList();
        },
        selHmUserSonLogList(){
            this.$api.logs.selHmUserSonLogIdList(this.params).then(res =>{
                if(res.success){
                    this.logsList = res.data.dataList;
                    this.userCount = res.data.total;
                }
            })
        },
        showuser(item) {
            this.editSon = item;
            this.visible = true;
            this.editCheckedKeys = item.menus;
            this.treeMenu.forEach(tree => {
                if (tree.children.length > 0) {
                    this.editCheckedKeys = this.editCheckedKeys.filter(o => o !== tree.key)
                }
            })
        },
        onClose() {
            this.visible = false;
        },
        onExpand(expandedKeys) {
            this.expandedKeys = expandedKeys;
        },
        onCheck(checkedKeys, e) {
            this.addSon.menus = [...checkedKeys, ...e.halfCheckedKeys]
        },
        onEditExpand(expandedKeys) {
            this.editExpandedKeys = expandedKeys;
        },
        onEditCheck(checkedKeys, e) {
            this.editSon.menus = [...checkedKeys, ...e.halfCheckedKeys]
        },
        loadSonList() {
            this.spinning = true;
            this.$api.son.getSonList(this.userParams).then(res => {
                this.sonList = res.data.dataList;
                this.sonCount = res.data.total;
            }).finally(e => {
                this.spinning = false;
            })
        },
        loadMenus() {
            this.$api.user.getRouters(0).then(res => {
                if (res.success) {
                    res.data.routers.forEach(router => {
                        let children = [];
                        router.children.forEach(rou => {
                            children.push({
                                title: rou.menuName,
                                key: rou.menuId,
                            })
                        })
                        this.treeMenu.push({
                            title: router.menuName,
                            key: router.menuId,
                            children
                        });
                    })
                }
            })
        },
        saveSon() {
            this.spinning = true;
            if (this.checkUser()) {
                this.$api.son.saveSonInfo(this.addSon).then(res => {
                    this.$utils.handleThen(res, this, "添加子帐号成功!", false);
                    if (res.success) {
                        this.loadSonList();
                        this.addSon = {
                            username: '',
                            password: '',
                            nickName: '',
                            menus: [],
                        }
                    }

                })
            }
            this.spinning = false
        },
        checkUser() {
            let checked = false;
            this.checkCallBack(() => {
                this.$utils.checkUserName(this.addSon.username, "帐号格式不符合,请重新设置！");
                this.$utils.checkPassword(this.addSon.password, "密码格式不符合,请重新设置!");
                if (this.addSon.nickName !== "") {
                    this.$utils.checkNickName(this.addSon.nickName, "名称长度应该在2-8位,请重新设置!");
                }
                checked = true;
            });
            return checked;
        },
        updateSon() {
            this.spinning = true;
            this.$api.son.updateSon(this.editSon).then(res => {
                this.$utils.handleThen(res, this, "修改子帐号成功!", false);
                if (res.success) {
                    this.onClose();
                    this.loadSonList();
                    this.editSon = {
                        username: '',
                        password: '',
                        nickName: '',
                        menus: [],
                    }
                }
            }).finally(e => {
                this.spinning = false
            })
        },
        changeSonStatus(item) {
            let params = {sonId: item.sonId, status: item.status}
            this.$api.son.updateSonStatus(params).then(res => {
                this.$utils.handleThen(res, this, "修改子帐号状态成功!", false);
                this.loadSonList();
            })
        },
        changeResetPwd(username){
            this.$swal({
                position: 'top-end',
                title: '解锁'+username+'账号?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '是',
                cancelButtonText: '取消'
            }).then((res) => {
                if (res.value) {
                    this.$api.user.resetPwd(username).then((res) => {
                        this.$utils.handleThen(res, this, "解锁成功！");
                        this.loadSonList();
                    });
                }
            })
        }
    },
    mounted() {
        this.loadSonList();
        this.loadMenus();
    },
    watch: {
        'userRecordShow': {
            handler: function (val, old) {
                if (val) {
                    this.params.startTime =this.todayStr();
                    this.params.endTime =this.todayStr();
                    this.selHmUserSonLogList();
                }
            }
        }
    }
};
</script>

<style scoped>
</style>
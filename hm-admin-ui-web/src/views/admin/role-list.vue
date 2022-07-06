<template>
    <div>
        <a-spin :spinning="spinning" size="large">
            <div class="mb10">
                <a-button type="primary" size="small" @click="showEditRole()"> 添加角色</a-button>
            </div>
            <div>
                <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                    <tbody>
                    <tr>
                        <th width="150">角色名称</th>
                        <th width="80">状态</th>
                        <th>备注</th>
                        <th width="50">等级</th>
                        <th width="190">操作</th>
                    </tr>
                    <tr v-for="role in roleList">
                        <td class="forumrow">{{ role.roleName}}</td>
                        <td class="forumrow">{{ role.status? '开启' : '关闭' }}</td>
                        <td class="forumrow">{{ role.remark}}</td>
                        <td class="forumrow">{{ role.userLevel}}</td>
                        <td class="forumrowhighlight">
                            <a-button type="primary" size="small" @click="showEditRole(role)">
                                修改
                            </a-button>
                            <a-button type="primary" size="small" class="mlr5" @click="deleteRole(role.roleId)">
                                删除
                            </a-button>
                            <a-button type="primary" size="small" class="mlr5" @click="selMenu(role)">
                                选择菜单
                            </a-button>
                        </td>
                    </tr>
                    <tr v-if="roleList.length==0">
                        <td colspan="5" class="forumrowhighlight nohover">
                            <a-empty/>
                        </td>
                    </tr>

                    </tbody>
                </table>

                <!--添加或修改-->
                <a-modal
                        centered
                        width="600"
                        :title="modalTitle"
                        :visible="roleAddShow"
                        :confirm-loading="confirmLoading"
                        @ok="roleAddOk"
                        @cancel="roleAddCancel"
                >
                    <a-form-model :model="roles" :label-col="labelCol" :wrapper-col="wrapperCol">
                        <a-form-model-item label="角色名称" placeholder="请输入角色名称" size="small">
                            <a-input v-model="roles.roleName" size="small"/>
                        </a-form-model-item>
                        <a-form-model-item label="状态" prop="region">
                            <a-select v-model="roles.status" placeholder="请选择状态" size="small">
                                <a-select-option :value="true"> 开启 </a-select-option>
                                <a-select-option :value="false"> 关闭 </a-select-option>
                            </a-select>
                        </a-form-model-item>
                        <a-form-model-item label="等级" prop="region">
                            <a-select v-model="roles.userLevel" placeholder="请选择等级" size="small">
                                <a-select-option value="1">1</a-select-option>
                                <a-select-option value="2">2</a-select-option>
                                <a-select-option value="3">3</a-select-option>
                                <a-select-option value="4">4</a-select-option>
                                <a-select-option value="5">5</a-select-option>
                                <a-select-option value="10">10</a-select-option>
                            </a-select>
                        </a-form-model-item>
                        <a-form-model-item label="备注" placeholder="请输入备注">
                            <a-textarea v-model="roles.remark" :rows="4" size="small"/>
                        </a-form-model-item>
                    </a-form-model>
                </a-modal>

                <!--选择菜单-->
                <a-modal
                        title="选择菜单"
                        :visible="roleMenuShow"
                        :confirm-loading="confirmLoading"
                        @ok="roleMenuOk"
                        @cancel="roleMenuCancel"
                >
                    <a-tree
                            v-model="editCheckedKeys" checkable
                            :expanded-keys="editExpandedKeys"
                            :selected-keys="editSelectedKeys"
                            :tree-data="treeMenu"
                            @expand="onExpand"
                            @check="onCheck"
                    />

                </a-modal>
            </div>
        </a-spin>
    </div>
</template>

<script>
    export default {
        components: {},
        data() {
            return {
                spinning:false,
                roleList: [],
                roles: {
                    status:true,
                    userLevel:null,
                },
                roleCount: 50,//分页 数
                confirmLoading: false,
                roleAddShow: false,
                roleMenuShow: false,
                modalTitle: '',
                labelCol: {span: 7},
                wrapperCol: {span: 17},

                editCheckedKeys: [],
                treeMenu: [],
                editSelectedKeys:[],
                editExpandedKeys:[],
                menus:[],
            };
        },
        mounted() {
            this.initRoleList();
        },
        methods: {
            onExpand(expandedKeys) {/*展开节点*/
                this.editExpandedKeys = expandedKeys;
            },
            onCheck(checkedKeys, e) {/*点击选中和取消*/
                this.menus = [...checkedKeys,...e.halfCheckedKeys];
            },
            selMenu(row) {/*选择菜单*/
                Object.assign(this.roles, row);
                this.editCheckedKeys= [];
                this.treeMenu= [];
                this.roleMenuShow = true;
                this.$api.menu.getSysTree(row ? row.roleId : 0).then((res) => {
                    if (res.success) {
                        res.data.routers.forEach(router => {
                            let children = [];
                            router.children.forEach(rou => {
                                children.push({
                                    title: rou.menuName,
                                    key: rou.menuId,
                                })
                            });
                            this.treeMenu.push({
                                title: router.menuName,
                                key: router.menuId,
                                children
                            });
                        });
                        this.editCheckedKeys = res.data.menuIds;
                        this.treeMenu.forEach(tree => {
                            if (tree.children.length > 0) {
                                this.editCheckedKeys = this.editCheckedKeys.filter(o => o !== tree.key)
                            }
                        })
                    }
                });
            },
            initRoleList() {/*查询角色*/
                this.spinning=true;
                this.$api.menu.getRoleList().then(res => {
                    console.log(res);
                    if (res.success) {
                        this.roleList = res.data;
                        console.log(this.roleList);
                    }
                });
                this.spinning=false;
            },
            showEditRole(row) {/*添加或者修改角色*/
                if (row) {
                    Object.assign(this.roles, row)
                } else {
                    this.roles = {
                        id: '',
                        roleName: '',
                        remark: '',
                        status: false,
                        userLevel: ''
                    };
                }
                this.roleAddShow = true;
                this.modalTitle = "添加角色"
            },
            error() {
                this.$swal({
                    title: '功能未开放!',
                    icon: 'error',
                    confirmButtonText: '确定'
                });
            },
            deleteRole(roleId) {/*删除角色*/
                const self = this;
                this.$confirm({
                    title: '删除角色',
                    content: '是否删除当前角色',
                    okText: '确认',
                    onOk() {
                        self.$api.menu.delRole(roleId).then((res) => {
                            console.log("del", res);
                            if (res.success) {
                                self.initRoleList();
                                self.$utils.handleThen(res,self);
                            } else {
                                self.$utils.handleThen(res,self);
                            }
                        });
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.$message.info('已取消删除');
                    },
                });

            },
            roleAddOk() {/*添加和修改角色*/
                this.confirmLoading = true;
                if (this.roles.roleId) {
                    this.$api.menu.updateRole(this.roles).then((res) => {
                        if (res.success) {
                            this.initRoleList();
                            this.$utils.handleThen(res,this);
                        } else {
                            this.$utils.handleThen(res,this);
                        }
                    });
                } else {
                    this.$api.menu.addRole(this.roles).then((res) => {
                        if (res.success) {
                            this.initRoleList();
                            this.$utils.handleThen(res,this);
                        } else {
                            this.$utils.handleThen(res,this);
                        }
                    });
                }
                this.roleAddShow = false;
                this.confirmLoading = false;
            },
            roleAddCancel(e) {/*关闭添加框*/
                console.log('Clicked cancel button');
                this.roleAddShow = false;
            },
            roleMenuOk() {/*选择菜单框*/
                this.$api.menu.addRoleMenu({roleId: this.roles.roleId, menuIds: this.menus}).then((res) => {//添加
                    if (res.success) {
                        this.roleMenuShow = false;
                        this.menus=[];
                        this.initRoleList();
                    }
                });
            },
            roleMenuCancel() {/*关闭选择菜单框*/
                this.roleMenuShow = false;
            },
        },
    };
</script>

<style scoped>
    .ant-badge-count {
        background: blue;
    }
</style>

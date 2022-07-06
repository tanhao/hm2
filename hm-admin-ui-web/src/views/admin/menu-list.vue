<template>
    <div>
        <a-spin :spinning="spinning" size="large">
            <div class="mb10">
                <a-button type="primary"  size="small" @click="editMenu({})"> 添加菜单 </a-button>
                <a-divider type="vertical" />
                <a-button type="primary"  size="small" @click="search()"> 返回 </a-button>

            </div>
            <div>
                <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                    <tbody>
                        <tr>
                            <th width="150">名称</th>
                            <th >路由地址</th>
                            <th width="50">排序</th>
                            <th width="120">code</th>
                            <th width="50">类型</th>
                            <th width="80">下级列表</th>
                            <th width="120" >操作</th>
                        </tr>
                        <tr v-for="menu in menuList">
                            <td class="forumrow">{{ menu.menuName }}</td>
                            <td class="forumrow">{{ menu.url }} </td>
                            <td class="forumrow">{{ menu.sort }}</td>
                            <td class="forumrow">{{ menu.code }}</td>
                            <td class="forumrow">{{ getMtype(menu.mtype) }}</td>
                            <td class="forumrow" ><a @click="search(menu.menuId)"> <a-badge show-zero class="mlr10" :count="menu.subCount"/></a> </td>
                            <td class="forumrowhighlight">
                                <a-button type="primary" size="small" @click="editMenu(menu)">
                                    修改
                                </a-button>
                                <a-button type="primary" size="small" class="mlr5" @click="delMenu(menu.menuId)">
                                    删除
                                </a-button>
                            </td>
                        </tr>
                        <tr v-if="menuList.length==0">
                            <td colspan="12" class="forumrowhighlight nohover">
                                <a-empty/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <a-modal
                        centered
                        width="600"
                        :title="modalTitle"
                        :visible="menuAddShow"
                        :confirm-loading="confirmLoading"
                        @ok="menuAddOk"
                        @cancel="menuAddCancel"
                >
                    <a-form-model :model="menus" :label-col="labelCol" :wrapper-col="wrapperCol">
                        <a-form-model-item label="菜单名称" placeholder="请输入菜单名称" >
                            <a-input v-model="menus.menuName" size="small"/>
                        </a-form-model-item>
                        <a-form-model-item label="路由地址" placeholder="请输入路由地址" >
                            <a-input v-model="menus.url" size="small"/>
                        </a-form-model-item>
                        <a-form-model-item label="code" placeholder="请输入code" >
                            <a-input v-model="menus.code" size="small"/>
                        </a-form-model-item>
                        <a-form-model-item label="类型" prop="region">
                            <a-select v-model="menus.mtype" placeholder="请选择类型" size="small">
                                <a-select-option :value="1">
                                    目录
                                </a-select-option>
                                <a-select-option :value="2">
                                    菜单
                                </a-select-option>
                                <a-select-option :value="3">
                                    按钮
                                </a-select-option>
                            </a-select>
                        </a-form-model-item>
                        <a-form-model-item label="排序" placeholder="请输入排序" >
                            <a-input v-model="menus.sort" size="small"/>
                        </a-form-model-item>
                    </a-form-model>
                </a-modal>
            </div>
        </a-spin>
    </div>
</template>

<script>
    import MenuUpdate from "./menu-update";
    import MenuAdd from "./menu-add";
    export default {
        components: {MenuUpdate,MenuAdd},
        data() {
            return {
                spinning:false,
                menuUpdateShow:false,//修改状态
                menuAddShow:false,//添加状态
                menuDelShow:false,
                modalTitle:'',
                menuList:[],//菜单数据
                menuCount: 50,//分页 数
                parentId: 0,//上下级Id
                ids:[],//id集合
                menus: {},
                confirmLoading: false,
                labelCol: { span: 7 },
                wrapperCol: { span: 17 },
            };
        },
        mounted() {
            this.initUserList();
        },
        computed:{

        },
        methods: {
            search(parentId){/*返回*/
                if(parentId){
                    if(this.parentId){
                        this.ids.push(this.parentId);
                        this.parentId=parentId;
                    }else {
                        this.parentId=parentId;
                    }
                }else {
                    this.parentId = this.ids.pop() || 0;
                }
                this.initUserList();
            },
            initUserList() {/*查询菜单*/
                this.spinning=true;
                this.$api.menu.getMenuList(this.parentId).then(res => {
                    if (res.success) {
                        this.menuList=res.data.menus;
                        console.log(this.menuList);
                    }
                });
                this.spinning=false;
            },
            editMenu(row) {/*添加和修改菜单*/
                if (row.menuId) {
                    this.modalTitle="修改菜单";
                    this.menus = Object.assign({}, row);
                } else {
                    this.menus = {
                        menuName: '',
                        url: '',
                        icon: '',
                        sort: 2000,
                        mtype: 1,
                        status: "OPEN",
                        parentId: this.parentId
                    }
                    this.modalTitle="添加菜单";
                }
                this.menuAddShow = true;
            },
            error() {
                this.$swal({
                    title: '功能未开放!',
                    icon: 'error',
                    confirmButtonText: '确定'
                });
            },
            delMenu(menuId){/*删除菜单*/
                const self = this;
                this.$confirm({
                    title: '删除菜单',
                    content: '是否删除当前菜单',
                    okText: '确认',
                    onOk() {
                        self.$api.menu.delMenu(menuId).then((res) => {
                            if (res.success) {
                                self.initUserList();
                                self.$utils.handleThen(res,self);
                            }else {
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
            menuAddOk() {/*添加和修改菜单*/
                this.confirmLoading = true;
                this.menus.status= this.menus.status==="OPEN"? true : false
                if(this.menus.menuId){
                    this.$api.menu.updateMenu(this.menus).then((res) => {
                        if (res.success) {
                            this.initUserList();
                            this.$utils.handleThen(res,this);
                        }else {
                            this.$utils.handleThen(res,this);
                        }
                    });
                }else{
                    this.$api.menu.addMenu(this.menus).then((res) => {
                        if (res.success) {
                            this.initUserList();
                            this.$utils.handleThen(res,this);
                        }else {
                            this.$utils.handleThen(res,this);
                        }
                    });
                }
                this.confirmLoading = false;
                this.menuAddShow = false;
            },
            menuAddCancel(e) {/*关闭添加框*/
                this.menuAddShow = false;
            },
            getMtype(type){/*查询类型*/
                if(type===1){
                    return "目录";
                }else if(type===2){
                    return "菜单";
                }else{
                    return "按钮";
                }
            },
        },
    };
</script>

<style scoped>
    .ant-badge-count{
        background: blue;
    }
</style>


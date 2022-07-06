<template>
    <!--彩种-->
    <a-modal class="czn"
            title="彩种设置"
            :width="600"
            :visible="userLotteryShow"
            :body-style="{ paddingBottom: '0px'}"
             :maskClosable="maskClosable"
             dialogClass="tk"
             :footer="null"
            @cancel="onClose"
    >
        <div>
            <a-row type="flex" justify="start" :gutter="16">
                <a-col :span="8"  v-for="([name,items],i) in types" class="mb10">
                    <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1"
                           style="border-collapse: separate;width: 100%;">
                        <tbody>
                        <tr>
                            <th class="popth">{{name}}</th>
                            <th class="popth">
                                <a-switch checked-children="开" v-model="group[i].num>0?true:false"  @change="updateGroupIdLottery(group[i].gId,group[i].num)" un-checked-children="关" default-checked/>
                            </th>
                        </tr>
                        <tr v-for="item in items">
                            <td width="90" class="forumrow">{{item.lotteryName}}</td>
                            <td class="forumrowhighlight">
                                <a-switch checked-children="开" v-model="item.isOpenSelf" @change="updateUserIdLottery(item.isOpenSelf,item.id,i)" un-checked-children="关" default-checked/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </a-col>
            </a-row>
        </div>
    </a-modal>
</template>

<script>
    import to from "await-to-js";
    export default {
        name: "user-lottery",
        data() {
            return {
                maskClosable:false,
                /*types: {
                    '赛车类': ['北京赛车', '幸运飞艇', '极速赛车', '澳洲幸运10', '极速飞艇', 'EPS赛马', '凤凰赛车', '太阳城赛马', '幸运飞艇3', 'SG飞艇'],
                    '时时彩类': ['重庆时时彩', '极速时时彩', '澳洲幸运5', '凤凰时时彩', '凤凰时时彩', '太阳城时时彩', '腾讯分分彩', '腾讯三分彩', '台湾时时彩'],
                    '快乐十分类': ['广东快乐十分', '极速快乐十分', '澳洲幸运8']
                },*/
                types: {},
                group:[]
            }
        },
        props: {
            userLotteryShow: {
                type: Boolean
            },
            editUserId:null,
        },
        methods: {
            updateUserIdLottery(isOpen,id,i) {
                this.spinning=true;
                let params ={id:id,isOpenUp:isOpen};
                this.$api.user.updateUserIdLottery(params).then(res =>{
                   if(res.success){
                       if(isOpen===true){
                           this.group[i].num=this.group[i].num+1;
                       }else {
                           this.group[i].num=this.group[i].num-1;
                       }
                       this.$utils.handleThen(res, this, "修改成功!",false);
                   }else{
                       this.$utils.handleThen(res, this, "修改失败!");
                   }
                });
                this.spinning=false;
            },
            updateGroupIdLottery(groupId,isOpen) {
                this.spinning=true;
                let params ={groupId:groupId,isOpenUp:isOpen>0?false:true,userId:this.editUserId};
                console.log(params);
                this.$api.user.updateGroupIdLottery(params).then(res =>{
                    if(res.success){
                        this.selUserIdList();//更新
                        this.$utils.handleThen(res, this, "修改成功!",false);
                    }else{
                        this.$utils.handleThen(res, this, "修改失败!");
                    }
                });
                this.spinning=false;
            },
            onClose() {
                this.$emit('update:userLotteryShow', false)
            },
            async selUserIdList(){ /* 按ID查询 */
                this.spinning=true;
                let [err, data] = await to(this.$api.user.getUserLottery(this.editUserId));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                let strMap = new Map();
                let sc=0,ss=0,gd=0,xy=0;
                data.data.forEach(item => {
                    if (strMap.has(item.groupName)) {
                        strMap.get(item.groupName).push(item)
                    } else {
                        strMap.set(item.groupName, [item])
                    }
                    if(item.groupId===100 && item.isOpenSelf!==false){
                        sc=sc+1;
                    }else  if(item.groupId===200 && item.isOpenSelf!==false){
                        ss=ss+1;
                    }else if(item.groupId===300 && item.isOpenSelf!==false){
                        gd=gd+1;
                    }else if(item.groupId==400 && item.isOpenSelf!==false){
                        xy=xy+1;
                    }
                });
                this.group=[{isOpen:false,num:sc,gId:100},{isOpen:false,num:ss,gId:200},{isOpen:false,num:gd,gId:300},{isOpen:false,num:xy,gId:400}];
                //console.log("str",strMap);
                this.types = strMap;
                this.spinning=false;
            },
        },watch: {
            'userLotteryShow': {
                handler: function (val, old) {
                    if (val) {
                        this.selUserIdList();
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>

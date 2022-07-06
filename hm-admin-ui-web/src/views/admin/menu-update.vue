<template>
    <!--修改-->
    <a-drawer
            title="修改--菜单"
            :width="300"
            :visible="menuUpdateShow"
            :body-style="{ paddingBottom: '80px'}"
            @close="onClose"
    >
        <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1"
               style="border-collapse: separate;width: 100%">
            <tbody>
                <tr>
                    <td width="110" class="forumrow txtright">名称</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear style="width: 160px" placeholder="hzh02" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">路由地址</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear style="width: 160px" placeholder="list" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">排序</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input allow-clear placeholder="1" size="small" style="width: 160px;margin-right: 10px"/>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">code</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear placeholder="AAAA" style="width: 160px;margin-right: 10px"/>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">类型</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear  placeholder="目录" style="width: 160px;margin-right: 10px"/>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">下级列表</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear  placeholder="1" style="width: 160px;margin-right: 10px"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <div
                :style="{
                      position: 'absolute',
                      right: 0,
                      bottom: 0,
                      width: '100%',
                      borderTop: '1px solid #fff',
                      padding: '10px 16px',
                      background: '#d5e9ff',
                      textAlign: 'right',
                      zIndex: 1,
                    }"
        >
            <a-button :style="{ marginRight: '12px' }" size="small" @click="onClose">
                取消
            </a-button>
            <a-button type="primary" size="small">
                确定
            </a-button>
        </div>
    </a-drawer>
</template>

<script>
    export default {
        name: "menu-update",
        data() {
            return {
                listShow:[false,false,false],
            }
        },
        props: {
            menuUpdateShow: {
                type: Boolean
            }
        },
        methods: {
            onClose() {
                this.$emit('update:menuUpdateShow', false)
            },
            onChange(value) {
                console.log('changed', value);
            },
            toggle: function(i) {
                this.$set(this.listShow,i, !this.listShow[i]);
            },
        }

    }
</script>

<style scoped>

</style>

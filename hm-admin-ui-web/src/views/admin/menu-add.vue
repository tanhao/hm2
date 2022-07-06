<template>
    <div>
        <!--新增-->
        <a-drawer
                title="新增菜单"
                :width="720"
                :visible="meunAddShow"
                :body-style="{ paddingBottom: '80px'}"
                @close="onClose"
        >
            <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1"
                   style="border-collapse: separate;width: 100%">
                <tbody>
                    <tr>
                        <td width="110" class="forumrow txtright">名称</td>
                        <td class="forumrowhighlight txtleft">
                            <a-input size="small" allow-clear style="width: 160px" placeholder="" disabled/>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">路由地址</td>
                        <td class="forumrowhighlight txtleft">
                            <a-input size="small" allow-clear style="width: 160px" placeholder="" disabled/>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">排序</td>
                        <td class="forumrowhighlight txtleft">
                            <a-input allow-clear placeholder="" size="small" style="width: 160px;margin-right: 10px"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">code</td>
                        <td class="forumrowhighlight txtleft">
                            <a-input size="small" allow-clear placeholder="" style="width: 160px;margin-right: 10px"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">类型</td>
                        <td class="forumrowhighlight txtleft">
                            <a-input size="small" allow-clear  placeholder="" style="width: 160px;margin-right: 10px"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">下级列表</td>
                        <td class="forumrowhighlight txtleft">
                            <a-input size="small" allow-clear  placeholder="" style="width: 160px;margin-right: 10px"/>
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
    </div>
</template>

<script>
    export default {
        name: "menu-add",
        props:{
            meunAddShow: {
                type: Boolean
            }
        },
        data() {
            return {
                value: 0,

            }
        },
        methods: {
            onClose() {
                this.$emit('update:menuAddShow', false)
            },
            onChange(value) {
                console.log('changed', value);
            },
            toggle: function() {
                this.alllottery = !this.alllottery //取反
            },
        }
    }
</script>

<style scoped>

</style>

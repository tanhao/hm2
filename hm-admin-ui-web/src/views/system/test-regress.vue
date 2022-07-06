<template>
    <div class="buhuo allhigit">

        <a-row :gutter="8">
            <template>
                <a-col :span="14" class="df">
                    <span class="maintxt mlr5">安保密码:</span>
                    <a-input-password size="small" placeholder="请输入安保密码" style="width: 150px"/>
                    <div class="pl10">
                        <a-button type="primary" icon="key" size="small">
                            确定
                        </a-button>
                    </div>
                    <div class="red dstable mlr10 fontwe">需要修改时，先输入安保密码</div>
                </a-col>
                <a-col :span="8" style="float: right;display: table;width: auto;">
                    <template>
                        赚取退水：
                        <a-input size="small" style="width: 120px"/>
                        <a-button type="primary" icon="setting" size="small" class="mlr5">
                            快速设置
                        </a-button>
                    </template>
                    <a-button type="primary" icon="edit" size="small">
                        修改日志
                    </a-button>
                </a-col>

            </template>
        </a-row>


        <a-row :gutter="2" class="mt16">
            <a-col :span="24">
                <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%"
                       style="border-collapse: separate;">
                    <tr>
                        <th colspan="8">大项快速设置</th>
                    </tr>
                    <tr>
                        <th width="180">调整项目</th>
                        <th>A盘(%)</th>
                        <th>B盘(%)</th>
                        <th>C盘(%)</th>
                        <th>D盘(%)</th>
                        <th>注单限额</th>
                        <th>单期限额</th>
                        <th width="60px">修改</th>
                    </tr>
                    <tr v-for="i in ['号码类(球号 车号 正码等)','两面类（大小 单双 龙虎 三军等）','多项类（方位 中发白 总和 过关等）','连码类（任选二 任选三 前二组等）','其它（冠亚和 前中后三等）']">
                        <td class="forumrow">{{i}}</td>
                        <td class="forumrowhighlight">
                            <a-input size="small" style="width: 60px" default-value="0.63"/>
                        </td>
                        <td class="forumrowhighlight">
                            <a-input size="small" style="width: 60px" default-value="1.63"/>
                        </td>
                        <td class="forumrowhighlight">
                            <a-input size="small" style="width: 60px" default-value="2.63"/>
                        </td>
                        <td class="forumrowhighlight">
                            <a-input size="small" style="width: 60px" default-value="3.63"/>
                        </td>
                        <td class="forumrowhighlight">
                            <a-input size="small" style="width: 60px" default-value="2000"/>
                        </td>
                        <td class="forumrowhighlight">
                            <a-input size="small" style="width: 60px" default-value="40000"/>
                        </td>
                        <td class="forumrowhighlight">
                            <a-button type="primary" size="small">
                                修改
                            </a-button>
                        </td>
                    </tr>
                </table>
            </a-col>
            <template v-for="tit in ['北京赛车','幸运飞艇','极速赛车','澳洲幸运10','极速飞艇']">
                <a-col :span="24">
                    <div class="demotit">{{tit}}</div>
                </a-col>
                <a-col :span="10">

                    <table class="tableborder" border="0" align="center" cellpadding="2" cellspacing="1" width="100%"
                           style="border-collapse: separate;">

                         <template>
                            <tr>
                                <th colspan="5">当前值</th>
                            </tr>
                            <tr>
                                <th width="150">种类</th>
                                <th>A盘(%)</th>
                                <th>B盘(%)</th>
                                <th>C盘(%)</th>
                                <th>D盘(%)</th>
                            </tr>
                            <tr v-for="i in ['1-10车号','两面','冠亚和大双','冠亚和小单','冠亚和']">
                                <td class="forumrow">{{i}}</td>
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="0.63"/>
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="1.63"/>
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="2.63"/>
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="3.63"/>
                                </td>
                            </tr>

                        </template>

                    </table>


                </a-col>
                <a-col :span="8">

                    <table class="tableborder gree" border="0" align="center" cellpadding="2" cellspacing="1"
                           width="100%" style="border-collapse: separate;">

                        <template>
                            <tr>
                                <th colspan="4">预修改值*隔天生效</th>
                            </tr>
                            <tr>
                                <th>A盘(%)</th>
                                <th>B盘(%)</th>
                                <th>C盘(%)</th>
                                <th>D盘(%)</th>

                            </tr>
                            <tr v-for="i in 5">
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="0"/>
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="0"/>
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="0"/>
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="0"/>
                                </td>
                            </tr>

                        </template>

                    </table>


                </a-col>
                <a-col :span="6">

                    <table class="tableborder tabend" border="0" align="center" cellpadding="2" cellspacing="1"
                           width="100%" style="border-collapse: separate;">

                        <template>
                            <tr>
                                <th colspan="2">当前值</th>
                            </tr>
                            <tr>
                                <th>注单限额</th>
                                <th>单期限额</th>
                            </tr>
                            <tr v-for="i in 5">
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="2000"/>
                                </td>
                                <td class="forumrowhighlight">
                                    <a-input size="small" style="width: 60px" default-value="4000"/>
                                </td>
                            </tr>

                        </template>

                    </table>


                </a-col>
            </template>
            <div class="mt16" style="text-align: center">

                <a-button type="primary" icon="save" size="small" class="mlr10">
                    一键保存
                </a-button>

            </div>
        </a-row>

    </div>
</template>
<style scoped>
</style>

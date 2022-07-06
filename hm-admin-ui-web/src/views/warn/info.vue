<template>
    <div class="info">
        <a-spin :spinning="spinning">
            <a-row :gutter="8">
                <a-col :span="4">
                    <table class="tableborder leftlist" border="0" align="center" cellpadding="5" cellspacing="1">
                        <tr>
                            <th width="100px">彩种</th>
                            <th>金额</th>
                        </tr>
                        <tbody>
                            <tr class="nohover">
                                <td class="forumrow ">极速赛车</td>
                                <td class="forumrowhighlight selectGroup"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">幸运飞艇</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">澳洲幸运10</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">极速飞艇</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">北京赛车</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">EPS赛马</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">凤凰赛车</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">太阳城赛马</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">幸运飞艇(3)</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">SG飞艇</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">极速时时彩</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">澳洲幸运5</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">重庆时时彩</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">广东快乐十分</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">幸运农场</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">澳洲幸运8</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>
                            <tr>
                                <td class="forumrow">北京快乐8</td>
                                <td class="forumrowhighlight selected"><span>0</span></td>
                            </tr>

                        </tbody>
                    </table>
                </a-col>
                <a-col :span="20">
                    <div class="mb10 df">
                        <span class="maintxt mlr10">当前选中彩种:</span>
                        <a-select :default-value="provinceData[0]" style="width: 120px" @change="handleProvinceChange" size="small">
                            <a-select-option v-for="province in provinceData" :key="province">
                                {{ province }}
                            </a-select-option>
                        </a-select>
                        <span class="maintxt mlr10">玩法:</span>
                        <a-select v-model="secondCity" style="width: 120px" size="small">
                            <a-select-option v-for="city in cities" :key="city">
                                {{ city }}
                            </a-select-option>
                        </a-select>
                        <span class="maintxt mlr10">显示最近:</span>
                        <a-select default-value="6" style="width: 80px" size="small">
                            <a-select-option value="1">
                                10秒
                            </a-select-option>
                            <a-select-option value="2">
                                20秒
                            </a-select-option>
                            <a-select-option value="3">
                                30秒
                            </a-select-option>
                            <a-select-option value="4">
                                40秒
                            </a-select-option>
                            <a-select-option value="5">
                                50秒
                            </a-select-option>
                            <a-select-option value="6">
                                60秒
                            </a-select-option>
                        </a-select>
                        <div class="pl10">
                            <a-button type="primary" icon="sync" size="small">
                                刷新
                            </a-button>
                        </div>
                        <a-badge class="mlr10" count="8" />
                        <a-select default-value="10秒" style="width: 70px" size="small">
                            <a-select-option value="手动">
                                手动
                            </a-select-option>
                            <a-select-option value="10秒">
                                10秒
                            </a-select-option>
                            <a-select-option value="20秒">
                                20秒
                            </a-select-option>
                            <a-select-option value="30秒">
                                30秒
                            </a-select-option>
                            <a-select-option value="40秒">
                                40秒
                            </a-select-option>
                            <a-select-option value="50秒">
                                50秒
                            </a-select-option>
                            <a-select-option value="60秒">
                                60秒
                            </a-select-option>
                            <a-select-option value="99秒">
                                99秒
                            </a-select-option>
                        </a-select>
                    </div>
                    <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                        <tr>
                            <th colspan="3">第一次金额预警</th>
                            <th colspan="3">第二次金额预警</th>
                            <th colspan="3">选择分类金额预警</th>
                        </tr>
                        <tr>
                            <th class="forumrow">序号</th>
                            <th class="forumrowhighlight">项目</th>
                            <th class="forumrowhighlight">金额</th>
                            <th class="forumrow">序号</th>
                            <th class="forumrowhighlight">项目</th>
                            <th class="forumrowhighlight">金额</th>
                            <th class="forumrow">序号</th>
                            <th class="forumrowhighlight">项目</th>
                            <th class="forumrowhighlight">金额</th>
                        </tr>
                        <tr>
                            <td colspan="9" class="forumrowhighlight nohover">
                                <a-empty />
                            </td>
                        </tr>
                    </table>
                </a-col>
            </a-row>
        </a-spin>
    </div>
</template>

<script>
const provinceData = [
    "极速赛车",
    "幸运飞艇",
    "澳洲幸运10",
    "澳洲幸运5",
    "极速飞艇",
    "北京赛车",
    "幸运农场",
    "重庆时时彩",
    "EPS赛马",
    "凤凰赛车",
    "太阳城赛马",
    "幸运飞艇(3)",
    "SG飞艇",
    "北京快乐8"
];
const cityData = {
    极速赛车: ["1-10车号", "两面", "冠亚和大双", "冠亚和小单", "冠亚和"],
    幸运飞艇: ["1-10车号", "两面", "冠亚和大双", "冠亚和小单", "冠亚和"]
};
import to from "await-to-js";
export default {
    data() {
        return {
            spinning: false,
            value: 0,
            editdown: false,
            provinceData,
            cityData,
            cities: cityData[provinceData[0]],
            secondCity: cityData[provinceData[0]][0]
        };
    },
    methods: {
        showdwon() {
            this.editdown = true;
        },
        onClose() {
            this.editdown = false;
        },
        handleProvinceChange(value) {
            this.cities = cityData[value];
            this.secondCity = cityData[value][0];
        }
    }
};
</script>

<style scoped>
</style>
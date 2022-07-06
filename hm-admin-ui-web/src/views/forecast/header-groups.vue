<template>
    <table class="popright fontwe" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%;">
        <tbody>
            <tr>
                <th v-for="group in groups" :key="group.groupId" width="6.25%" class="popth">{{group.name}}</th>
            </tr>
            <tr>
                <td v-for="(group,index) in groups" :key="group.groupId" :class="index==selectIndex?'forumrow selectGroup':'forumrow'" @click="changeGroup(index)">
                    <a :class="groupAmt(index)>=0?'blue':'red'">{{groupAmt(index)}}</a>
                </td>
            </tr>
        </tbody>
    </table>
</template>
<script>
export default {
    name: "header-groups",
    props: {
        mapOdds: Object,
        userStats: Object,
        groups: Array,
    },
    data() {
        return {
            selectIndex: 0,
        };
    },
    computed: {
        groupAmt(index) {
            return (index) => {
                if (Object.keys(this.mapOdds) == 0) {
                    return 0;
                }
                let amt = 0;
                let group = this.groups[index];
                group.types.forEach((type) => {
                    let { col, row, oddss: os } = type;
                    row.forEach((r, ri) => {
                        col.forEach((c, ci) => {
                            let odds = this.mapOdds[c][r];
                            if (odds) {
                                let stats = this.userStats[odds.oddsId];
                                amt += stats ? stats.betAmt : 0;
                            }
                        });
                    });
                });
                return amt.toFixed(2);
            };
        },
    },
    mounted() {},
    watch: {},
    methods: {
        changeGroup(index) {
            this.selectIndex = index;
            this.$emit("change-group", index);
        },
    },
};
</script>
<style>
</style>
<style scoped>
td {
    font-weight: bold;
}

.type {
    background-color: #f8f8f9;
}

img {
    width: 18px;
    height: 18px;
}
</style>

<template>
    <table class="tableborder fontwe" border="0" align="center" cellpadding="1" cellspacing="1" style="border-collapse: separate;width: 100%;">
        <tbody>
            <tr>
                <th colspan="2">遗漏</th>
            </tr>
            <template v-for="item in yls">
                <tr :key="item.name">
                    <td class="forumrow">{{item.name}}</td>
                    <td class="forumrow"><span class="red">{{item.value}}</span></td>
                </tr>
            </template>
        </tbody>
    </table>
</template>
<script>
export default {
    name: "yl",
    props: {
        yls: Array,
    },
    data() {
        return {};
    },
    computed: {},
    mounted() {},
    watch: {},
    methods: {},
};
</script>
<style>
</style>
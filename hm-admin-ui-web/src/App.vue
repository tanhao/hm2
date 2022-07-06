<template>
    <div id="app">
        <a-config-provider :locale="zh_CN">
            <router-view />
        </a-config-provider>
    </div>
</template>

<script>
import zh_CN from "ant-design-vue/lib/locale-provider/zh_CN";
export default {
    name: "App",
    data() {
        return {
            zh_CN,
        };
    },
    methods: {},
};
</script>

<style lang="less">
</style>

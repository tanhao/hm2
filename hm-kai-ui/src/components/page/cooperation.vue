<template>
    <div class="bodybox">
        <MyHeader></MyHeader>

        <MyFoot></MyFoot>
    </div>
</template>

<script>
    import MyHeader from '@/components/layout/head'
    import MyFoot from '@/components/layout/foot'
    export default {
        data() {
            return {}
        },
        components: {
            MyHeader,

            MyFoot,
        },
        methods: {},
    }
</script>

<style scoped>

</style>

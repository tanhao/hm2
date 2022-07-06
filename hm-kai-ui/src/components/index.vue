<template>
    <div class="bodybox">
        <MyHeader></MyHeader>
        <MyHome></MyHome>
        <MyFoot></MyFoot>
    </div>
</template>

<script>
    import MyHeader from '@/components/layout/head'
    import MyHome from '@/components/layout/home'
    import MyFoot from '@/components/layout/foot'

    export default {
        data() {
            return {}
        },
        components: {
            MyHeader,
            MyHome,
            MyFoot,
        },
        methods: {},
    }
</script>

<template>
    <div>
      <router-view/>
      <left-menu v-show="showMenu"></left-menu>
    </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import LeftMenu from '@/components/sg/layout/leftmenu'
    export default {
        name: "sghome",
      components: {
        LeftMenu
      },
      computed: {
        ...mapGetters(['showMenu']),
      }
    }
</script>

<style scoped>

</style>

<template>
  <div class="idc">
    <router-view/>
    <left-menu v-show="showMenu"></left-menu>
  </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import LeftMenu from '@/components/idc/layout/leftmenu'

  export default {
    name: "idchome",
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

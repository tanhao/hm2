<template>
  <div id="app" :class="'skin_'+skinColor">
    <router-view @changeSkin="changeSkin"/>
  </div>
</template>

<script>
export default {
  name: 'App',
  data(){
    return{
      skinColor:''
    }
  },
  computed:{


  },
  methods:{
    changeSkin(color){
      this.skinColor = color;
    }
  },

}
</script>

<style>
#app {

}
</style>

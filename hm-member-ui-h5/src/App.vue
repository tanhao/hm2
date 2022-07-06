<template>
  <div id="app">
    <div class="dizAbS">
      <div class="anim_app">
        <router-view/>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'App'
  }
</script>

<style>
  #app {
    -webkit-overflow-scrolling: touch;
    overflow: auto;
    height: 100%;
    width: 100%;
    background-color: #ebebeb;
  }
</style>

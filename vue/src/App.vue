<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'game' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="this.$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>

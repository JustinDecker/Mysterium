<template>
  <div class="home">
    <h1>Home</h1>
    <p>You are {{ this.$store.state.user.username }}</p>
  </div>
</template>

<script>
export default {
  name: "home"
};
</script>

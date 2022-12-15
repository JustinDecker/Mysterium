<template>
  <div class="game">
    <h1>Home</h1>
    <p>You are {{ this.$store.state.user.username }}</p>
  </div>
</template>

<script>
export default {
  name: "game"
};
</script>

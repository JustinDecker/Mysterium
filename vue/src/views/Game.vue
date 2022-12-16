<template>
  <div class="game">
    <h1>Home</h1>
    <p>You are {{ this.$store.state.user.username }}</p>
    <div v-if="this.updateStatus == 'received'">
    <p>{{this.GameState}}</p>
    </div>
  </div>
</template>

<script>
import gameStateService from "@/services/GameStateService.js";
export default {
  name: "game",
  data() {
    return {
      GameState: {},
      updateStatus: "unset"
    }
  },
  created() {
    this.getGameStateByUsername(this.$store.state.user.username);
  },
  methods: {
    getGameStateByUsername(){
      gameStateService.getGameStateByUsername().then((response) => {
        if(response.status == 200){
          this.GameState = response.data;
          this.updateStatus = "received";
        }
      })
  },
},
};
</script>

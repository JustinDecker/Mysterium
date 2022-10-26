package com.techelevator.model;

public class Prediction {

    private int playerId;
    private int foreignPlayerId;
    private boolean prediction;


    public Prediction(int playerId, int foreignPlayerId, boolean prediction){
        this.playerId = playerId;
        this.foreignPlayerId = foreignPlayerId;
        this.prediction = prediction;
    }
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public boolean isPrediction() {
        return prediction;
    }

    public void setPrediction(boolean prediction) {
        this.prediction = prediction;
    }

    public int getForeignPlayerId() {
        return foreignPlayerId;
    }

    public void setForeignPlayerId(int foreignPlayerId) {
        this.foreignPlayerId = foreignPlayerId;
    }
}

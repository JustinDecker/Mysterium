package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Psychic {

    private int playerId;
    private int remainingGuesses;
    private int investigationPhase;
    private int currentGuess;
    private List <Vision> visionList;

    private List<Prediction> predictionList;

    public Psychic(){
        this.visionList = new ArrayList<>();
        this.predictionList = new ArrayList<>();
        this.currentGuess = -1;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    public void setRemainingGuesses(int remainingGuesses) {
        this.remainingGuesses = remainingGuesses;
    }

    public int getInvestigationPhase() {
        return investigationPhase;
    }

    public void setInvestigationPhase(int investigationPhase) {
        this.investigationPhase = investigationPhase;
    }

    public int getCurrentGuess() {
        return currentGuess;
    }

    public void setCurrentGuess(int currentGuess) {
        this.currentGuess = currentGuess;
    }

    public List<Vision> getVisionList() {
        return visionList;
    }

    public void addVisionToList(Vision vision) {
        this.visionList.add(vision);
    }

    public List<Prediction> getPredictionList() {
        return predictionList;
    }

    public void addPredictionToList(Prediction prediction) {
        this.predictionList.add(prediction);
    }
}

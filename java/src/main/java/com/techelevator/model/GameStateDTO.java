package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class GameStateDTO {

    private int gameId;
    private int night;

    private int phase;

    private List <Psychic> psychicList;

    public GameStateDTO(){
        this.psychicList = new ArrayList<>();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getNight() {
        return night;
    }

    public void setNight(int night) {
        this.night = night;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public List<Psychic> getPsychicList() {
        return psychicList;
    }

    public void addPsychicToList(Psychic psychic) {
        this.psychicList.add(psychic);
    }
}

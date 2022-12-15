package com.techelevator.model;

import java.util.List;

public class GhostStateDTO {

    private int ghostId;
    private List<Vision> visions;
    private List<Murder> murders;

    public int getGhostId() {
        return ghostId;
    }

    public void setGhostId(int ghostId) {
        this.ghostId = ghostId;
    }

    public List<Vision> getVisions() {
        return visions;
    }

    public void setVisions(List<Vision> visions) {
        this.visions = visions;
    }

    public List<Murder> getMurders() {
        return murders;
    }

    public void setMurders(List<Murder> murders) {
        this.murders = murders;
    }
}

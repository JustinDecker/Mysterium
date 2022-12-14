package com.techelevator.model;

public class Murder {
    private int murderId;
    private int playerId;
    private int personId;
    private int locationId;
    private int weaponId;

    public int getMurderId() {
        return murderId;
    }

    public void setMurderId(int murderId) {
        this.murderId = murderId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(int weaponId) {
        this.weaponId = weaponId;
    }
}

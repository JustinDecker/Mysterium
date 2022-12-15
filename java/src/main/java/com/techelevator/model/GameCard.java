package com.techelevator.model;

public class GameCard {

    private int gameCardId;
    private int cardTypeId;
    private String imgUrl;

    public GameCard(int gameCardId, int cardTypeId, String imgUrl){
        this.gameCardId = gameCardId;
        this.cardTypeId = cardTypeId;
        this.imgUrl = imgUrl;
    }

    public int getGameCardId() {
        return gameCardId;
    }

    public void setGameCardId(int gameCardId) {
        this.gameCardId = gameCardId;
    }

    public int getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(int cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

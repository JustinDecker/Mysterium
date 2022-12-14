package com.techelevator.model;

public class Vision {
    private int visionId;
    private String zone;
    private String imgUrl;

    public Vision(int visionId, String zone, String imgUrl){
        this.visionId = visionId;
        this.zone = zone;
        this.imgUrl = imgUrl;
    }

    public int getVisionId() {
        return visionId;
    }

    public void setVisionId(int visionId) {
        this.visionId = visionId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

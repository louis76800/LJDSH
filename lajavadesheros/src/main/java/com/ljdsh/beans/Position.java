package com.ljdsh.beans;
public class Position {
    //création des variables
    private String longitude;
    private String lattitude;
    //getter pour longitude
    public String getLongitude() {
        return longitude;
    }
    //setter pour longitude
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    //getter pour lattitude
    public String getLattitude() {
        return lattitude;
    }

    /**
     * @param lattitude  setter lattitude
     */
    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }
}
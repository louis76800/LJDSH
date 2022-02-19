package com.ljdsh.beans;

public class Super {
    //création des variables
    private String pseudo;
    private String ville;
    private String telephone;
    private String longitude;
    private String lattitude;
    // getter pour longitude
    public String getLongitude() {
        return longitude;
    }
    // setter pour longitude
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    // getter pour lattitude
    public String getLattitude() {
        return lattitude;
    }
    // setter pour lattitude
    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }
    // getter pour pseudo
    public String getPseudo() {
        return pseudo;
    }
    // setter pour pseudo
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    // getter pour ville
    public String getVille() {
        return ville;
    }
    // setter pour ville
    public void setVille(String ville) {
        this.ville = ville;
    }
    // getter pour telephone
    public String getTelephone() {
        return telephone;
    }
    // setter pour telephone
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

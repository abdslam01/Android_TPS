package com.example.androidexamproject.database;

public class Chantier {
    private String avenue, ville, lat, lng, dateD, dateF, observ;

    public Chantier(String avenue, String ville, String lat, String lng, String dateD, String dateF, String observ) {
        this.avenue = avenue;
        this.ville = ville;
        this.lat = lat;
        this.lng = lng;
        this.dateD = dateD;
        this.dateF = dateF;
        this.observ = observ;
    }

    public String getAvenue() {
        return avenue;
    }

    public String getVille() {
        return ville;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getDateD() {
        return dateD;
    }

    public String getDateF() {
        return dateF;
    }

    public String getObserv() {
        return observ;
    }
}

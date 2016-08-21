package com.android.connal.planit;

/**
 * Created by Connal on 2016-08-20.
 */
public class FoursquareVenue {
    private String name;
    private String city;
    private int distance;
    private int checkins;
    private int here;
    private int score;

    private String category;

    public FoursquareVenue() {
        this.name = "";
        this.city = "";
        this.setCategory("");
    }

    public void setHere(int here){
        this.here = here;
    }

    public int getHere(){
        return this.here;
    }

    public void setCheckins(int checkins){
        this.checkins = checkins;
    }

    public int getCheckins(){
        return this.checkins;
    }

    public String getCity() {
        if (city.length() > 0) {
            return city;
        }
        return city;
    }

    public void setDistance(int distance){this.distance = distance;}

    public int getDistance(){return this.distance;}

    public void setCity(String city) {
        if (city != null) {
            this.city = city.replaceAll("\\(", "").replaceAll("\\)", "");
            ;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString(){
        return "Name: " + this.name + " Distance: " + this.distance;
    }

}

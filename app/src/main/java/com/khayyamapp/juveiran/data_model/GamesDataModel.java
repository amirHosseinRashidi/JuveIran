
package com.khayyamapp.juveiran.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GamesDataModel {

    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("homepic")
    @Expose
    private String homepic;
    @SerializedName("guest")
    @Expose
    private String guest;
    @SerializedName("guestpic")
    @Expose
    private String guestpic;
    @SerializedName("studiom")
    @Expose
    private String studiom;
    @SerializedName("studiompic")
    @Expose
    private String studiompic;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("league")
    @Expose
    private String league;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getHomepic() {
        return homepic;
    }

    public void setHomepic(String homepic) {
        this.homepic = homepic;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getGuestpic() {
        return guestpic;
    }

    public void setGuestpic(String guestpic) {
        this.guestpic = guestpic;
    }

    public String getStudiom() {
        return studiom;
    }

    public void setStudiom(String studiom) {
        this.studiom = studiom;
    }

    public String getStudiompic() {
        return studiompic;
    }

    public void setStudiompic(String studiompic) {
        this.studiompic = studiompic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

}
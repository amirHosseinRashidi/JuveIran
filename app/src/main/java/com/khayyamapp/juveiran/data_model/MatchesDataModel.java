package com.khayyamapp.juveiran.data_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchesDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("league_id")
    @Expose
    private Integer leagueId;
    @SerializedName("fasl_id")
    @Expose
    private Integer faslId;
    @SerializedName("home_id")
    @Expose
    private Integer homeId;
    @SerializedName("away_id")
    @Expose
    private Integer awayId;
    @SerializedName("home_goals")
    @Expose
    private Integer homeGoals;
    @SerializedName("away_goals")
    @Expose
    private Integer awayGoals;
    @SerializedName("home_per")
    @Expose
    private List<List<String>> homePer = null;
    @SerializedName("away_per")
    @Expose
    private List<List<String>> awayPer = null;
    @SerializedName("home_match_state")
    @Expose
    private List<List<String>> homeMatchState = null;
    @SerializedName("away_match_state")
    @Expose
    private List<List<String>> awayMatchState = null;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("away")
    @Expose
    private String away;
    @SerializedName("homepic")
    @Expose
    private String homepic;
    @SerializedName("awaypic")
    @Expose
    private String awaypic;
    @SerializedName("league")
    @Expose
    private String league;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public Integer getFaslId() {
        return faslId;
    }

    public void setFaslId(Integer faslId) {
        this.faslId = faslId;
    }

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public Integer getAwayId() {
        return awayId;
    }

    public void setAwayId(Integer awayId) {
        this.awayId = awayId;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Integer getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }

    public List<List<String>> getHomePer() {
        return homePer;
    }

    public void setHomePer(List<List<String>> homePer) {
        this.homePer = homePer;
    }

    public List<List<String>> getAwayPer() {
        return awayPer;
    }

    public void setAwayPer(List<List<String>> awayPer) {
        this.awayPer = awayPer;
    }

    public List<List<String>> getHomeMatchState() {
        return homeMatchState;
    }

    public void setHomeMatchState(List<List<String>> homeMatchState) {
        this.homeMatchState = homeMatchState;
    }

    public List<List<String>> getAwayMatchState() {
        return awayMatchState;
    }

    public void setAwayMatchState(List<List<String>> awayMatchState) {
        this.awayMatchState = awayMatchState;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getHomepic() {
        return homepic;
    }

    public void setHomepic(String homepic) {
        this.homepic = homepic;
    }

    public String getAwaypic() {
        return awaypic;
    }

    public void setAwaypic(String awaypic) {
        this.awaypic = awaypic;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

}
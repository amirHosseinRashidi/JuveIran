
package com.khayyamapp.juveiran.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeagueTableDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("fasl_id")
    @Expose
    private Integer faslId;
    @SerializedName("team_id")
    @Expose
    private String teamId;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("deference")
    @Expose
    private Integer deference;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFaslId() {
        return faslId;
    }

    public void setFaslId(Integer faslId) {
        this.faslId = faslId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getDeference() {
        return deference;
    }

    public void setDeference(Integer deference) {
        this.deference = deference;
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

}
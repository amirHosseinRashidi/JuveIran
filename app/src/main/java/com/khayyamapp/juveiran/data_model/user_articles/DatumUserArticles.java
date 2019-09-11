package com.khayyamapp.juveiran.data_model.user_articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumUserArticles {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("title")
@Expose
private String title;
@SerializedName("text")
@Expose
private String text;
@SerializedName("sharetext")
@Expose
private String sharetext;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("views")
@Expose
private Integer views;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("image")
@Expose
private String image;
@SerializedName("date")
@Expose
private String date;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getText() {
return text;
}

public void setText(String text) {
this.text = text;
}

public String getSharetext() {
return sharetext;
}

public void setSharetext(String sharetext) {
this.sharetext = sharetext;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public Integer getViews() {
return views;
}

public void setViews(Integer views) {
this.views = views;
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

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

}
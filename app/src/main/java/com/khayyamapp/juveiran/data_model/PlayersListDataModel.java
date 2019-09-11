package com.khayyamapp.juveiran.data_model;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayersListDataModel implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("enname")
    @Expose
    private String enname;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("minute")
    @Expose
    private Integer minute;
    @SerializedName("match_count")
    @Expose
    private Integer matchCount;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("static1")
    @Expose
    private List<String> static1 = null;
    @SerializedName("static2")
    @Expose
    private List<String> static2 = null;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("pic")
    @Expose
    private String pic;
    public final static Parcelable.Creator<PlayersListDataModel> CREATOR = new Creator<PlayersListDataModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PlayersListDataModel createFromParcel(Parcel in) {
            return new PlayersListDataModel(in);
        }

        public PlayersListDataModel[] newArray(int size) {
            return (new PlayersListDataModel[size]);
        }

    }
            ;
    private final static long serialVersionUID = 5918506755124712723L;

    protected PlayersListDataModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.enname = ((String) in.readValue((String.class.getClassLoader())));
        this.age = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.number = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.minute = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.matchCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.static1, (java.lang.String.class.getClassLoader()));
        in.readList(this.static2, (java.lang.String.class.getClassLoader()));
        this.role = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.pic = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PlayersListDataModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getStatic1() {
        return static1;
    }

    public void setStatic1(List<String> static1) {
        this.static1 = static1;
    }

    public List<String> getStatic2() {
        return static2;
    }

    public void setStatic2(List<String> static2) {
        this.static2 = static2;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(enname);
        dest.writeValue(age);
        dest.writeValue(number);
        dest.writeValue(minute);
        dest.writeValue(matchCount);
        dest.writeValue(country);
        dest.writeList(static1);
        dest.writeList(static2);
        dest.writeValue(role);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(pic);
    }

    public int describeContents() {
        return 0;
    }

}
package com.example.assignmentiwizards.repository.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Dnyaneshwar Patil <dnyaneshwar1926@gmail.com>
 * @version 1.0, $date 2021/23/03 08:43 PM
 * */
@Entity(tableName = "jobs_table")
public class JobsModel implements Parcelable {
    @SerializedName("id")
    @Expose
    @NonNull
    @PrimaryKey
    private String id;
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("created_at")
    @Expose
    @ColumnInfo(name = "created_at")
    private String createdAt;
    @SerializedName("company")
    @Expose
    @ColumnInfo(name = "company")
    private String company;
    @SerializedName("company_url")
    @Expose
    @ColumnInfo(name = "company_url")
    private String companyUrl;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("how_to_apply")
    @Expose
    @ColumnInfo(name = "how_to_apply")
    private String howToApply;
    @SerializedName("company_logo")
    @Expose
    @ColumnInfo(name = "company_logo")
    private String companyLogo;
    @SerializedName("applied")
    @Expose
    @ColumnInfo(name = "applied")
    private boolean isApplied;

    public JobsModel() {
    }

    protected JobsModel(Parcel in) {
        id = in.readString();
        type = in.readString();
        url = in.readString();
        createdAt = in.readString();
        company = in.readString();
        companyUrl = in.readString();
        location = in.readString();
        title = in.readString();
        description = in.readString();
        howToApply = in.readString();
        companyLogo = in.readString();
        isApplied = in.readByte() != 0;
    }

    public static final Creator<JobsModel> CREATOR = new Creator<JobsModel>() {
        @Override
        public JobsModel createFromParcel(Parcel in) {
            return new JobsModel(in);
        }

        @Override
        public JobsModel[] newArray(int size) {
            return new JobsModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHowToApply() {
        return howToApply;
    }

    public void setHowToApply(String howToApply) {
        this.howToApply = howToApply;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean applied) {
        isApplied = applied;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(createdAt);
        dest.writeString(company);
        dest.writeString(companyUrl);
        dest.writeString(location);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(howToApply);
        dest.writeString(companyLogo);
        dest.writeByte((byte) (isApplied ? 1 : 0));
    }
}

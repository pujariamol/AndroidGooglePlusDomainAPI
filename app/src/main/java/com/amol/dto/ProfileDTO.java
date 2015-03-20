package com.amol.dto;

import com.amol.util.Constants;
import com.google.android.gms.plus.model.people.Person;

import java.io.Serializable;

/**
 * Created by Amol on 3/11/2015.
 */
public class ProfileDTO implements Serializable{
    private String displayPictureURL;
    private String birthday;
    private String displayName;
    private String aboutMe;
    private String gender;

    public ProfileDTO(Person profileInfo){
        this.displayName = profileInfo.hasDisplayName() ? profileInfo.getDisplayName():Constants.NA ;
        this.displayPictureURL = profileInfo.getImage().getUrl().toString();

        this.birthday = profileInfo.hasBirthday() ? profileInfo.getBirthday():Constants.NA  ;
        this.gender = Constants.getGender(profileInfo.getGender());
        this.aboutMe = profileInfo.hasAboutMe() ?profileInfo.getAboutMe() :  Constants.NA ;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayPictureURL() {
        return displayPictureURL;
    }

    public void setDisplayPictureURL(String displayPictureURL) {
        this.displayPictureURL = displayPictureURL;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

package com.kwala.app.models;

import android.support.annotation.ColorInt;

import com.kwala.app.enums.Filter;
import com.kwala.app.enums.Gender;
import com.kwala.app.enums.Interest;
import com.kwala.app.enums.MatchState;
import com.kwala.app.helpers.Tools;
import com.kwala.app.models.generic.RString;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author jacobamuchow@gmail.com
 */
@GenerateRealmFieldNames
public class RMatch extends RealmObject {
    private static final String TAG = RMatch.class.getSimpleName();

    @PrimaryKey private String matchId;
    @Required private Double score;
    @Required private String matchStateValue;
    @Required private Date expirationDate;

    @Required private String userId;
    @Required private String firstName;
    @Required private String lastName;
    @Required private String profileImageId;
    @Required private String genderValue;
    @Required private Integer age;
    @Required private String profileColor;
    @Required private String bio;

    private RealmList<RString> filterValues;
    private RealmList<RString> interestValues;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getMatchStateValue() {
        return matchStateValue;
    }

    public void setMatchStateValue(String matchStateValue) {
        this.matchStateValue = matchStateValue;
    }

    public MatchState getMatchState() {
        return MatchState.fromNetworkValue(getMatchStateValue());
    }

    public void setMatchState(MatchState matchState) {
        setMatchStateValue(matchState.getNetworkValue());
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(String profileImageId) {
        this.profileImageId = profileImageId;
    }

    public String getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
    }

    public Gender getGender() {
        return Gender.fromNetworkValue(getGenderValue());
    }

    public void setGender(Gender gender) {
        setGenderValue(gender.getNetworkValue());
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProfileColor() {
        return profileColor;
    }

    public void setProfileColor(String profileColor) {
        this.profileColor = profileColor;
    }

    @ColorInt
    public int getProfileColorAsInt() {
        String colorHex = getProfileColor();
        return Tools.hexToColorInt(colorHex == null ? "ffa9deef" : colorHex);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public RealmList<RString> getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(RealmList<RString> filterValues) {
        this.filterValues = filterValues;
    }

    public ArrayList<Filter> getFilters() {
        RealmList<RString> values = getFilterValues();
        ArrayList<Filter> filters = new ArrayList<>(values.size());

        for (RString value : values) {
            filters.add(Filter.fromNetworkString(value.getValue()));
        }

        return filters;
    }

    public RealmList<RString> getInterestValues() {
        return interestValues;
    }

    public void setInterestValues(RealmList<RString> interestValues) {
        this.interestValues = interestValues;
    }

    public ArrayList<Interest> getInterests() {
        RealmList<RString> values = getInterestValues();
        ArrayList<Interest> interests = new ArrayList<>(values.size());

        for (RString value : values) {
            interests.add(Interest.fromNetworkString(value.getValue()));
        }

        return interests;
    }
}

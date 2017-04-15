package com.kwala.app.models;

import com.kwala.app.enums.Filter;
import com.kwala.app.enums.MatchState;
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
    @Required private String userId;
    @Required private Double score;

    private RealmList<RString> filterValues;
    private RealmList<RString> interestValues;

    @Required private String matchStateValue;
    @Required private Date expirationDate; //TODO

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    //TODO: getter for interests


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
}

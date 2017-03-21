package com.kwala.app.models;

import android.graphics.Color;

import com.kwala.app.enums.FilterCategory;
import com.kwala.app.service.realm.RealmWrites;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import java.util.Date;

import io.realm.Realm;
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
    @Required private String filterCategoryValue;

    @Required private String firstName;
    @Required private String lastName;
    @Required private String profileImageId;
    private int profileColor;
    @Required private Date birthDate;


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

    public String getFilterCategoryValue() {
        return filterCategoryValue;
    }

    public void setFilterCategoryValue(String filterCategoryValue) {
        this.filterCategoryValue = filterCategoryValue;
    }

    public FilterCategory getFilterCategory() {
        return FilterCategory.fromNetworkString(getFilterCategoryValue());
    }

    public void setFilterCategory(FilterCategory filterCategory) {
        setFilterCategoryValue(filterCategory.getNetworkString());
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

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    public String getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(String profileImageId) {
        this.profileImageId = profileImageId;
    }

    public int getProfileColor() {
        return profileColor;
    }

    public void setProfileColor(int profileColor) {
        this.profileColor = profileColor;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    public static void generateTestData(final boolean clearAll) {

        RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                RealmList<RMatch> matches = new RealmList<>();

                realm.delete(RMatch.class);
                if (clearAll) {
                    return null;
                }

                RMatch match = realm.createObject(RMatch.class, "1");
                match.setScore(100.0);
                match.setFilterCategory(FilterCategory.COFFEE_BUDDY);
                match.setFirstName("Jacob");
                match.setLastName("Muchow");
                match.setProfileImageId("f89c8f68-69da-4def-8776-885f9fbe71b3");
                match.setProfileColor(Color.CYAN);
                match.setBirthDate(new Date(1996, 5, 2));
                matches.add(match);

                match = realm.createObject(RMatch.class, "2");
                match.setScore(89.7);
                match.setFilterCategory(FilterCategory.ONE_NIGHT_STAND);
                match.setFirstName("Brandon");
                match.setLastName("Erbschloe");
                match.setProfileImageId("f89c8f68-69da-4def-8776-885f9fbe71b3");
                match.setProfileColor(Color.MAGENTA);
                match.setBirthDate(new Date(1994, 8, 12));
                matches.add(match);

                match = realm.createObject(RMatch.class, "3");
                match.setScore(71.9);
                match.setFilterCategory(FilterCategory.COFFEE_BUDDY);
                match.setFirstName("Sijae");
                match.setLastName("Schiefer");
                match.setProfileImageId("f89c8f68-69da-4def-8776-885f9fbe71b3");
                match.setProfileColor(Color.GREEN);
                match.setBirthDate(new Date(1992, 11, 28));
                matches.add(match);

                match = realm.createObject(RMatch.class, "4");
                match.setScore(85.0);
                match.setFilterCategory(FilterCategory.WORKOUT_BUDDY);
                match.setFirstName("Andrew");
                match.setLastName("Wise");
                match.setProfileImageId("f89c8f68-69da-4def-8776-885f9fbe71b3");
                match.setProfileColor(Color.RED);
                match.setBirthDate(new Date(1993, 5, 17));
                matches.add(match);

                return null;
            }
        });
    }
}

package com.kwala.app.models;

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author jacobamuchow@gmail.com
 */
@GenerateRealmFieldNames
public class RFilter extends RealmObject {
    private static final String TAG = RFilter.class.getSimpleName();

    @PrimaryKey private static String filterId;

    @Required private Date createdAt;
    private boolean active;
    @Required private String category; //TODO: enum?
    private String gender; //TODO: enum?, required?

    public static String getFilterId() {
        return filterId;
    }

    public static void setFilterId(String filterId) {
        RFilter.filterId = filterId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

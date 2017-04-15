package com.kwala.app.models;

import com.kwala.app.enums.Filter;
import com.kwala.app.enums.Gender;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author jacobamuchow@gmail.com
 */
@GenerateRealmFieldNames
public class RFilter extends RealmObject {
    private static final String TAG = RFilter.class.getSimpleName();

    @PrimaryKey private String filterId;

    private boolean active;
    @Required private String categoryValue;
    private String genderValue;

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public Filter getCategory() {
        return Filter.fromNetworkString(getCategoryValue());
    }

    public void setCategory(Filter category) {
        setCategoryValue(category.getNetworkString());
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
}

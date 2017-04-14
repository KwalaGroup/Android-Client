package com.kwala.app.models;

import com.kwala.app.enums.FilterCategory;
import com.kwala.app.enums.Gender;
import com.kwala.app.service.realm.RealmWrites;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import io.realm.Realm;
import io.realm.RealmList;
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

    public FilterCategory getCategory() {
        return FilterCategory.fromNetworkString(getCategoryValue());
    }

    public void setCategory(FilterCategory category) {
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

    public static void generateTestData(final boolean clearAll) {

        RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                RealmList<RFilter> filters = new RealmList<>();

                realm.delete(RFilter.class);
                if (clearAll) {
                    return null;
                }

                RFilter filter = realm.createObject(RFilter.class, "1");
                filter.setActive(true);
                filter.setCategory(FilterCategory.LOVE_INTEREST);
                filter.setGenderValue("Both");
                filters.add(filter);

                filter = realm.createObject(RFilter.class, "2");
                filter.setActive(true);
                filter.setCategory(FilterCategory.COFFEE_BUDDY);
                filter.setGenderValue("Female");
                filters.add(filter);

                filter = realm.createObject(RFilter.class, "3");
                filter.setActive(true);
                filter.setCategory(FilterCategory.WORKOUT_BUDDY);
                filter.setGenderValue("Male");
                filters.add(filter);

                filter = realm.createObject(RFilter.class, "4");
                filter.setActive(false);
                filter.setCategory(FilterCategory.BUDDY);
                filter.setGenderValue("Male");
                filters.add(filter);

                return null;
            }
        });
    }
}

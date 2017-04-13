package com.kwala.app.models;

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
public class RFilter extends RealmObject {
    private static final String TAG = RFilter.class.getSimpleName();

    @PrimaryKey private String filterId;

    @Required private Date createdAt;
    private boolean active;
    @Required private String categoryValue;
    private String gender; //TODO: enum?, required?

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
                filter.setCreatedAt(new Date());
                filter.setActive(true);
                filter.setCategory(FilterCategory.LOVE_INTEREST);
                filter.setGender("Both");
                filters.add(filter);

                filter = realm.createObject(RFilter.class, "2");
                filter.setCreatedAt(new Date());
                filter.setActive(true);
                filter.setCategory(FilterCategory.COFFEE_BUDDY);
                filter.setGender("Female");
                filters.add(filter);

                filter = realm.createObject(RFilter.class, "3");
                filter.setCreatedAt(new Date());
                filter.setActive(true);
                filter.setCategory(FilterCategory.WORKOUT_BUDDY);
                filter.setGender("Male");
                filters.add(filter);

                filter = realm.createObject(RFilter.class, "4");
                filter.setCreatedAt(new Date());
                filter.setActive(false);
                filter.setCategory(FilterCategory.BUDDY);
                filter.setGender("Male");
                filters.add(filter);

                return null;
            }
        });
    }
}

package com.kwala.app.models;

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
    @Required private String category; //TODO: enum?
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

    public static void generateTestData() {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmList<RFilter> filters = new RealmList<>();

                    realm.delete(RFilter.class);

                    RFilter filter = realm.createObject(RFilter.class, "1");
                    filter.setCreatedAt(new Date());
                    filter.setActive(true);
                    filter.setCategory("Study Buddy");
                    filter.setGender("Both");
                    filters.add(filter);

                    filter = realm.createObject(RFilter.class, "2");
                    filter.setCreatedAt(new Date());
                    filter.setActive(true);
                    filter.setCategory("F*** Buddy");
                    filter.setGender("Female");
                    filters.add(filter);

                    filter = realm.createObject(RFilter.class, "3");
                    filter.setCreatedAt(new Date());
                    filter.setActive(true);
                    filter.setCategory("Lifting Buddy");
                    filter.setGender("Male");
                    filters.add(filter);

                    filter = realm.createObject(RFilter.class, "4");
                    filter.setCreatedAt(new Date());
                    filter.setActive(false);
                    filter.setCategory("Drinking Buddy");
                    filter.setGender("Male");
                    filters.add(filter);

                    realm.copyToRealmOrUpdate(filters);
                }
            });

        } finally {
            realm.close();
        }
    }
}

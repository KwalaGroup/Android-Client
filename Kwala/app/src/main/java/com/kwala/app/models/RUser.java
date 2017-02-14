package com.kwala.app.models;

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author jacobamuchow@gmail.com
 */
@GenerateRealmFieldNames
public class RUser extends RealmObject {
    private static final String TAG = RUser.class.getSimpleName();

    @PrimaryKey private String userId;

    @Required private String firstName;
    @Required private String lastName;

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
}

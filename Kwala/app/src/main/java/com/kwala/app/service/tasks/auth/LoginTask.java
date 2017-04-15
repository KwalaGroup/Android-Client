package com.kwala.app.service.tasks.auth;

import android.util.Log;

import com.kwala.app.service.UserData;
import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.realm.RealmSyncs;
import com.kwala.app.service.realm.RealmWrites;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com
 */

public class LoginTask extends NetworkTask<Void> {
    private static final String TAG = LoginTask.class.getSimpleName();

    private String email;
    private String hashedPassword;

    public LoginTask(String email, String hashedPassword) {
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new APIEndpoint(APIPaths.Auth.LOGIN, Endpoint.Method.POST)
                .addParam("email", email)
                .addParam("password", hashedPassword);
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {

        UserData.getInstance()
                .setEnteredEmail(email)
                .setHashedPassowrd(hashedPassword)
                .updateFromJson(jsonObject);

        //Sync filters
        final JSONArray filtersJsonArray = jsonObject.getJSONArray("filters");

        RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                try {
                    RealmSyncs.withRealm(realm).syncFilters(filtersJsonArray);
                } catch (JSONException e) {
                    Log.d(TAG, "Error syncing filters", e);
                }

                return null;
            }
        });

        return null;
    }
}

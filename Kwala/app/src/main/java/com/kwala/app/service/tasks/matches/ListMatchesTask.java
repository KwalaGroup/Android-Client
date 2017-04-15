package com.kwala.app.service.tasks.matches;

import android.util.Log;

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

public class ListMatchesTask extends NetworkTask<Void> {
    private static final String TAG = ListMatchesTask.class.getSimpleName();

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new APIEndpoint(APIPaths.MATCHES, Endpoint.Method.GET);
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {

        final JSONArray matchesJSONArray = jsonObject.getJSONArray("matches");

        return RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                try {
                    RealmSyncs.withRealm(realm).syncMatches(matchesJSONArray);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing matches list");
                }
                return null;
            }
        });
    }
}

package com.kwala.app.service.tasks;

import android.util.Log;

import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.GetQuizEndpoint;
import com.kwala.app.service.realm.RealmSyncs;
import com.kwala.app.service.realm.RealmWrites;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com
 */

public class GetQuizTask extends NetworkTask<Void> {
    private static final String TAG = GetQuizTask.class.getSimpleName();

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new GetQuizEndpoint();
    }

    @Override
    protected Void parse(final JSONObject jsonObject) throws JSONException {
        Log.d(TAG, jsonObject.toString());

        return RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {

                try {
                    JSONArray questionsJsonArray = jsonObject.getJSONArray("results");

                    RealmSyncs.withRealm(realm).syncQuizQuestions(questionsJsonArray);

                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing quiz questions", e);
                }

                return null;
            }
        });
    }
}

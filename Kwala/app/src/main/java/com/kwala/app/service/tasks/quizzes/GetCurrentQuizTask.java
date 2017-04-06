package com.kwala.app.service.tasks.quizzes;

import android.util.Log;

import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.realm.RealmSyncs;
import com.kwala.app.service.realm.RealmWrites;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com
 */

public class GetCurrentQuizTask extends NetworkTask<Void> {
    private static final String TAG = GetCurrentQuizTask.class.getSimpleName();

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new APIEndpoint(APIPaths.Quizzes.CURRENT, Endpoint.Method.GET);
    }

    @Override
    protected Void parse(final JSONObject jsonObject) throws JSONException {

        return RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {

                try {
                    RealmSyncs.withRealm(realm).syncQuiz(jsonObject);

                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing quiz questions", e);
                }

                return null;
            }
        });
    }
}

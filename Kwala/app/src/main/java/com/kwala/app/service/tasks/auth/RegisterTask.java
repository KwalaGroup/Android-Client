package com.kwala.app.service.tasks.auth;

import android.util.Log;

import com.kwala.app.service.RegistrationData;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.auth.RegisterEndpoint;
import com.kwala.app.service.realm.RealmSyncs;
import com.kwala.app.service.realm.RealmWrites;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com
 */

public class RegisterTask extends NetworkTask<Void> {
    private static final String TAG = RegisterTask.class.getSimpleName();

    private RegistrationData registrationData;

    public RegisterTask(RegistrationData registrationData) {
        this.registrationData = registrationData;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new RegisterEndpoint(registrationData);
    }

    @Override
    protected Void parse(final JSONObject jsonObject) {

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

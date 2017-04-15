package com.kwala.app.service.tasks.filters;

import android.util.Log;

import com.kwala.app.enums.Filter;
import com.kwala.app.enums.Gender;
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

public class CreateFilterTask extends NetworkTask<Void> {
    private static final String TAG = CreateFilterTask.class.getSimpleName();

    private Filter filter;
    private Gender gender;

    //TODO: filter time, interests
    public CreateFilterTask(Filter filter, Gender gender) {
        this.filter = filter;
        this.gender = gender;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        APIEndpoint endpoint = new APIEndpoint(APIPaths.FILTERS, Endpoint.Method.POST);

        endpoint.addParam("filter", filter.getNetworkString());
        endpoint.addParam("gender", gender.getNetworkValue());

        return endpoint;
    }

    @Override
    protected Void parse(final JSONObject jsonObject) throws JSONException {

        return RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                try {
                    RealmSyncs.withRealm(realm).syncFilter(jsonObject);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing newly created filter", e);
                }
                return null;
            }
        });
    }
}

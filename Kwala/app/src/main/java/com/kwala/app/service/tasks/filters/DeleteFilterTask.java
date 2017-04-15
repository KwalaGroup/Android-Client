package com.kwala.app.service.tasks.filters;

import com.kwala.app.models.RFilter;
import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.realm.RealmWrites;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com
 */

public class DeleteFilterTask extends NetworkTask<Void> {
    private static final String TAG = DeleteFilterTask.class.getSimpleName();

    private String filterId;

    public DeleteFilterTask(String filterId) {
        this.filterId = filterId;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new APIEndpoint(APIPaths.FILTERS, Endpoint.Method.DELETE)
                .addParam("id", filterId);
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {
        return RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                RealmWrites.withRealm(realm).deleteIfExists(RFilter.class, filterId);
                return null;
            }
        });
    }
}

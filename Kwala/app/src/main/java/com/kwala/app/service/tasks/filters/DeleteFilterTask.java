package com.kwala.app.service.tasks.filters;

import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

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
        return null;
    }
}

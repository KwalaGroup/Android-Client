package com.kwala.app.service.tasks.filters;

import com.kwala.app.enums.Filter;
import com.kwala.app.enums.Gender;
import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class CreateFilterTask extends NetworkTask<Void> {
    private static final String TAG = CreateFilterTask.class.getSimpleName();

    private Filter filter;
    private Gender gender;

    public CreateFilterTask(Filter filter, Gender gender) {
        this.filter = filter;
        this.gender = gender;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        APIEndpoint endpoint = new APIEndpoint(APIPaths.FILTERS, Endpoint.Method.POST);

        endpoint.addParam("filter", filter.getNetworkString());

        if (gender != Gender.UNKNOWN) {
            endpoint.addParam("gender", gender.getNetworkValue());
        }

        return endpoint;
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {
        return null;
    }
}

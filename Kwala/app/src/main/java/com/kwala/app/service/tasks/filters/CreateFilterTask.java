package com.kwala.app.service.tasks.filters;

import com.kwala.app.enums.FilterCategory;
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

    private FilterCategory filterCategory;
    private Gender gender;

    public CreateFilterTask(FilterCategory filterCategory) {
        this(filterCategory, Gender.UNKNOWN);
    }

    public CreateFilterTask(FilterCategory filterCategory, Gender gender) {
        this.filterCategory = filterCategory;
        this.gender = gender;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        APIEndpoint endpoint = new APIEndpoint(APIPaths.FILTERS, Endpoint.Method.POST);

        endpoint.addParam("filter", filterCategory.getNetworkString());

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

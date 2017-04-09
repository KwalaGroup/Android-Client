package com.kwala.app.service.tasks.location;

import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class UpdateLocationTask extends NetworkTask<Void> {
    private static final String TAG = UpdateLocationTask.class.getSimpleName();

    private double latitude;
    private double longitude;

    public UpdateLocationTask(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new APIEndpoint(APIPaths.LOCATIONS, Endpoint.Method.POST)
                .addParam("lat", latitude)
                .addParam("lon", longitude);
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {
        return null;
    }
}

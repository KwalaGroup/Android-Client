package com.kwala.app.service.endpoints;

import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author jacobamuchow@gmail.com
 */

public class JSONEndpoint extends Endpoint<JSONObject> {
    private static final String TAG = JSONEndpoint.class.getSimpleName();

    public JSONEndpoint(String url, Method method) {
        super(url, method);
    }

    public JSONEndpoint(String url, Method method, @Nullable Map<String, Object> params) {
        super(url, method, params);
    }

    @Override
    public JSONObject parse(int code, String response) throws Exception {
        return new JSONObject(response);
    }
}

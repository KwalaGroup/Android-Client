package com.kwala.app.service.endpoints;

import com.kwala.app.service.tasks.APIPaths;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class APIEndpoint extends JSONEndpoint {

    public APIEndpoint(String url, Method method) {
        super(APIPaths.BASE_URL + url, method);
    }

    public APIEndpoint(String url, Method method, JSONObject params) {
        super(APIPaths.BASE_URL + url, method, params);
    }
}

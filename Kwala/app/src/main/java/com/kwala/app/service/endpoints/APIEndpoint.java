package com.kwala.app.service.endpoints;

import android.support.annotation.Nullable;

import com.kwala.app.service.tasks.APIPaths;

import java.util.Map;

/**
 * @author jacobamuchow@gmail.com
 */

public class APIEndpoint extends JSONEndpoint {

    public APIEndpoint(String url, Method method) {
        super(APIPaths.BASE_URL + url, method);
    }

    public APIEndpoint(String url, Method method, @Nullable Map<String, Object> params) {
        super(APIPaths.BASE_URL + url, method, params);
    }
}

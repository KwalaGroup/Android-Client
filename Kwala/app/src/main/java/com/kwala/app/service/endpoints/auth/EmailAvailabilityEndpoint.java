package com.kwala.app.service.endpoints.auth;

import com.kwala.app.service.endpoints.APIPaths;
import com.kwala.app.service.endpoints.JSONEndpoint;

import java.util.HashMap;

/**
 * @author jacobamuchow@gmail.com
 */

public class EmailAvailabilityEndpoint extends JSONEndpoint {

    public EmailAvailabilityEndpoint(String email) {
        super(APIPaths.Auth.EMAIL_AVAILABILITY, Method.GET);

        HashMap<String, String> params = new HashMap<>();

        params.put("email", email);

        setParams(params);
    }
}

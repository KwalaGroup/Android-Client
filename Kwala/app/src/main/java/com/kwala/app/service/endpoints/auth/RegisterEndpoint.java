package com.kwala.app.service.endpoints.auth;

import com.kwala.app.service.RegistrationData;
import com.kwala.app.service.endpoints.APIPaths;
import com.kwala.app.service.endpoints.JSONEndpoint;

import java.util.HashMap;

/**
 * @author jacobamuchow@gmail.com
 */

public class RegisterEndpoint extends JSONEndpoint {
    private static final String TAG = RegisterEndpoint.class.getSimpleName();

    public RegisterEndpoint(RegistrationData registrationData) {
        super(APIPaths.Auth.REGISTER, Method.POST);

        HashMap<String, String> params = new HashMap<>();

        params.put("email", registrationData.getEmail());

        setParams(params);
    }
}

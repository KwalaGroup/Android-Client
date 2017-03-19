package com.kwala.app.service.endpoints.auth;

import com.kwala.app.service.endpoints.APIPaths;
import com.kwala.app.service.endpoints.JSONEndpoint;

import java.util.HashMap;

/**
 * @author jacobamuchow@gmail.com
 */

public class LoginEndpoint extends JSONEndpoint {
    private static final String TAG = LoginEndpoint.class.getSimpleName();

    public LoginEndpoint(String email, String hashedPassword) {
        super(APIPaths.Auth.LOGIN, Method.POST);

        HashMap<String, String> params = new HashMap<>();

        params.put("email", email);
        params.put("password", hashedPassword);

        setParams(params);
    }
}

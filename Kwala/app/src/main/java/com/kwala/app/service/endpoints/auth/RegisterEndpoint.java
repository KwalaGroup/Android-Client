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

        HashMap<String, Object> params = new HashMap<>();

        params.put("email", registrationData.getEmail());
        params.put("password", registrationData.getHashedPassword());
        params.put("image_id", registrationData.getProfileImageId());
        params.put("gender", registrationData.getGender().getNetworkValue());
        params.put("first_name", registrationData.getFirstName());
        params.put("last_name", registrationData.getLastName());
        params.put("age", registrationData.getAge());

        setParams(params);
    }
}

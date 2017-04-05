package com.kwala.app.service.tasks.auth;

import com.kwala.app.service.RegistrationData;
import com.kwala.app.service.UserData;
import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class RegisterTask extends NetworkTask<Void> {
    private static final String TAG = RegisterTask.class.getSimpleName();

    private RegistrationData registrationData;

    public RegisterTask(RegistrationData registrationData) {
        this.registrationData = registrationData;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new APIEndpoint(APIPaths.Auth.REGISTER, Endpoint.Method.POST)
                .addParam("email", registrationData.getEmail())
                .addParam("password", registrationData.getHashedPassword())
                .addParam("image_id", registrationData.getProfileImageId())
                .addParam("gender", registrationData.getGender().getNetworkValue())
                .addParam("first_name", registrationData.getFirstName())
                .addParam("last_name", registrationData.getLastName())
                .addParam("age", registrationData.getAge());
    }

    @Override
    protected Void parse(final JSONObject jsonObject) throws JSONException {

        UserData.getInstance().updateFromJson(jsonObject);

        RegistrationData.getInstance().clearAll();

        return null;
    }
}

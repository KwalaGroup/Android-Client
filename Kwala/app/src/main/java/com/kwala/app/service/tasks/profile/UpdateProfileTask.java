package com.kwala.app.service.tasks.profile;

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

public class UpdateProfileTask extends NetworkTask<Void> {
    private static final String TAG = UpdateProfileTask.class.getSimpleName();

    public String profileImageId;

    public UpdateProfileTask(String profileImageId) {
        this.profileImageId = profileImageId;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new APIEndpoint(APIPaths.PROFILE, Endpoint.Method.PUT)
                .addParam("image_id", profileImageId);
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {
        UserData.getInstance().setProfileImageId(profileImageId);
        return null;
    }
}

package com.kwala.app.service.endpoints.auth;

import com.kwala.app.service.endpoints.APIPaths;
import com.kwala.app.service.endpoints.JSONEndpoint;

/**
 * @author jacobamuchow@gmail.com
 */

public class LogoutEndpoint extends JSONEndpoint {
    private static final String TAG = LogoutEndpoint.class.getSimpleName();

    public LogoutEndpoint() {
        super(APIPaths.Auth.LOGOUT, Method.POST, null);
    }
}

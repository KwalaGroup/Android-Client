package com.kwala.app.service.endpoints;

/**
 * @author jacobamuchow@gmail.com
 */

public class LogoutEndpoint extends JSONEndpoint {
    private static final String TAG = LogoutEndpoint.class.getSimpleName();

    public LogoutEndpoint() {
        super(APIPaths.Auth.LOGOUT, Method.POST, null);
    }
}

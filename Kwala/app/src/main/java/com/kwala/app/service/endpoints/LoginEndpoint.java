package com.kwala.app.service.endpoints;

/**
 * @author jacobamuchow@gmail.com
 */

public class LoginEndpoint extends JSONEndpoint {
    private static final String TAG = LoginEndpoint.class.getSimpleName();

    public LoginEndpoint() {
        super(APIPaths.Auth.LOGIN, Method.POST, null);
    }
}

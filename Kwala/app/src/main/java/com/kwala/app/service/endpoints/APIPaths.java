package com.kwala.app.service.endpoints;

/**
 * @author jacobamuchow@gmail.com
 */

public final class APIPaths {
    private static final String TAG = APIPaths.class.getSimpleName();

    public static final String BASE_URL = "https://kwala.herokuapp.com";

    public static final class Auth {
        public static final String EMAIL_AVAILABILITY = BASE_URL + "/auth/email_availability";
        public static final String REGISTER = BASE_URL + "/auth/register";
        public static final String LOGIN = BASE_URL + "/auth/login";
        public static final String LOGOUT = BASE_URL + "/auth/logout";
    }
}

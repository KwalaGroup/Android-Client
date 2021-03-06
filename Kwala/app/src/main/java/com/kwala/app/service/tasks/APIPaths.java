package com.kwala.app.service.tasks;

/**
 * @author jacobamuchow@gmail.com
 */

public final class APIPaths {
    private static final String TAG = APIPaths.class.getSimpleName();

    public static final String BASE_URL = "https://kwala.herokuapp.com";

    public static final String AUTH = "/auth";
    public static final class Auth {
        public static final String EMAIL_AVAILABILITY =  AUTH + "/email_availability";
        public static final String REGISTER = AUTH + "/register";
        public static final String LOGIN = AUTH + "/login";
        public static final String LOGOUT = AUTH + "/logout";
    }

    public static final String QUIZZES = "/quizzes";
    public static final class Quizzes {
        public static final String CURRENT = QUIZZES + "/current";
        public static final String SUBMIT = QUIZZES + "/submit";
        public static final String ANSWERS = QUIZZES + "/answers";
    }

    public static final String PROFILE = "/profile";

    public static final String LOCATIONS = "/locations";

    public static final String FILTERS = "/filters";

    public static final String MATCHES = "/matches";
    public static final class Matches {
        public static final String ACCEPT = MATCHES + "/accept";
        public static final String REJECT = MATCHES + "/reject";
    }
}

package com.kwala.app.service.endpoints;

/**
 * @author jacobamuchow@gmail.com
 */

public class GetQuizEndpoint extends JSONEndpoint {
    private static final String TAG = GetQuizEndpoint.class.getSimpleName();

    public GetQuizEndpoint() {
        super("https://kwala.herokuapp.com/quizzes/", Method.GET, null);
    }
}

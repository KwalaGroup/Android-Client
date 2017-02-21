package com.kwala.app.service.endpoints;

/**
 * @author jacobamuchow@gmail.com
 */

public class QuestionsEndpoint extends JSONEndpoint {
    private static final String TAG = QuestionsEndpoint.class.getSimpleName();

    public QuestionsEndpoint() {
        super("https://kwala.herokuapp.com/questions/", Method.GET, null);
    }
}

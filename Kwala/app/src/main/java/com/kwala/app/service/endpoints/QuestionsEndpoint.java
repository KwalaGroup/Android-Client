package com.kwala.app.service.endpoints;

import android.util.Log;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class QuestionsEndpoint extends Endpoint<JSONObject> {
    private static final String TAG = QuestionsEndpoint.class.getSimpleName();

    public QuestionsEndpoint() {
        super("https://kwala.herokuapp.com/questions/", Method.GET, null);
    }

    @Override
    public JSONObject parse(int code, String response) throws Exception {
        Log.d(TAG, "parse: " + code + " " + response);

        return new JSONObject(response);
    }
}

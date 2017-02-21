package com.kwala.app.service.tasks;

import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.QuestionsEndpoint;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class QuestionsTask extends Task<JSONObject> {
    private static final String TAG = QuestionsTask.class.getSimpleName();

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new QuestionsEndpoint();
    }

    @Override
    protected void Parse(JSONObject jsonObject) {


    }
}

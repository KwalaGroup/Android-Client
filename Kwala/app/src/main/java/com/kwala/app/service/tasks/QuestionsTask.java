package com.kwala.app.service.tasks;

import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.QuestionsEndpoint;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class QuestionsTask extends Task<JSONObject> {

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new QuestionsEndpoint();
    }
}

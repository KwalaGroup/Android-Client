package com.kwala.app.service.tasks.quizzes;

import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author jacobamuchow@gmail.com
 */

public class SubmitQuizTask extends NetworkTask<Void> {
    private static final String TAG = SubmitQuizTask.class.getSimpleName();

    private HashMap<String, String> answersMap;

    public SubmitQuizTask(HashMap<String, String> answersMap) {
        this.answersMap = answersMap;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {

        JSONArray jsonArray = new JSONArray();

        for (HashMap.Entry<String, String> entry : answersMap.entrySet()) {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("question_id", entry.getKey());
                jsonObject.put("answer_id", entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);
        }

        return new APIEndpoint(APIPaths.Quizzes.ANSWERS, Endpoint.Method.PUT)
                .addParam("pairs", jsonArray.toString());
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {
        return null;
    }
}

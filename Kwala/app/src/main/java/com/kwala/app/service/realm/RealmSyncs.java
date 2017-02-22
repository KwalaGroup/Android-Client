package com.kwala.app.service.realm;

import android.util.Log;

import com.kwala.app.models.RQuizQuestion;
import com.kwala.app.models.generic.RString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * @author jacobamuchow@gmail.com
 */

public class RealmSyncs {
    private static final String TAG = RealmSyncs.class.getSimpleName();

    private Realm realm;

    private RealmSyncs(Realm realm) {
        this.realm = realm;
    }

    public static RealmSyncs withRealm(Realm realm) {
        return new RealmSyncs(realm);
    }

    public RealmList<RQuizQuestion> syncQuizQuestions(JSONArray jsonArray) throws JSONException {

        RealmList<RQuizQuestion> questions = new RealmList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject questionJsonObject = jsonArray.getJSONObject(i);

            try {
                RQuizQuestion question = syncQuizQuestion(questionJsonObject);
                questions.add(question);

            } catch (JSONException e) {
                Log.e(TAG, "Error parsing quiz question", e);
            }
        }

        return questions;
    }

    public RQuizQuestion syncQuizQuestion(JSONObject jsonObject) throws JSONException {

        String questionId = jsonObject.getString("id");
        RQuizQuestion question = RealmWrites.withRealm(realm).findOrCreate(RQuizQuestion.class, questionId);

        question.setQuestion(jsonObject.getString("question"));

        JSONArray answersJsonArray = jsonObject.getJSONArray("answers");
        RealmList<RString> answers = new RealmList<>();

        for (int i = 0; i < answersJsonArray.length(); i++) {
            JSONObject answerJsonObject = answersJsonArray.getJSONObject(i);

            RString string = realm.createObject(RString.class);
            string.setValue(answerJsonObject.getString("description"));

            answers.add(string);
        }

        question.setAnswers(answers);

        return question;
    }
}

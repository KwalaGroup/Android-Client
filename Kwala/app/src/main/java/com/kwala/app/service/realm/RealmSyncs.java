package com.kwala.app.service.realm;

import android.util.Log;

import com.kwala.app.models.RQuiz;
import com.kwala.app.models.RQuizAnswer;
import com.kwala.app.models.RQuizQuestion;

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

    public RQuiz syncQuiz(JSONObject jsonObject) throws JSONException {

        String quizId = jsonObject.getString("id");
        RQuiz quiz = RealmWrites.withRealm(realm).findOrCreate(RQuiz.class, quizId);

        RealmList<RQuizQuestion> questions = syncQuizQuestions(jsonObject.getJSONArray("questions"));
        quiz.setQuestions(questions);

        return quiz;
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
        RealmList<RQuizAnswer> answers = new RealmList<>();

        for (int i = 0; i < answersJsonArray.length(); i++) {
            JSONObject answerJsonObject = answersJsonArray.getJSONObject(i);

            String answerId = answerJsonObject.getString("id");
            RQuizAnswer answer = RealmWrites.withRealm(realm).findOrCreate(RQuizAnswer.class, answerId);

            answer.setAnswer(answerJsonObject.getString("answer"));

            answers.add(answer);
        }

        question.setAnswers(answers);

        return question;
    }
}

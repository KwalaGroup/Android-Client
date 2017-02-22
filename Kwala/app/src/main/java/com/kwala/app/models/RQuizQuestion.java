package com.kwala.app.models;

import com.kwala.app.models.generic.RString;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author jacobamuchow@gmail.com
 */
@GenerateRealmFieldNames
public class RQuizQuestion extends RealmObject {
    private static final String TAG = RQuizQuestion.class.getSimpleName();

    @PrimaryKey private String questionId;
    @Required private String question;
    private RealmList<RString> answers;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public RealmList<RString> getAnswers() {
        return answers;
    }

    public void setAnswers(RealmList<RString> answers) {
        this.answers = answers;
    }

    public static void mapJson(RQuizQuestion question, JSONObject jsonObject) throws JSONException {
    }
}

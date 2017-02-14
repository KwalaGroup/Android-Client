package com.kwala.app.models;

import com.kwala.app.models.generic.RString;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * @author jacobamuchow@gmail.com
 */
@GenerateRealmFieldNames
public class RQuizQuestion extends RealmObject {
    private static final String TAG = RQuizQuestion.class.getSimpleName();

    @Required private String quizId;
    @Required private String question;
    private RealmList<RString> answers;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
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
}

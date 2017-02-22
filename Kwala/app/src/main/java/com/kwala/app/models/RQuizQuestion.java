package com.kwala.app.models;

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

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
    private RealmList<RQuizAnswer> answers;

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

    public RealmList<RQuizAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(RealmList<RQuizAnswer> answers) {
        this.answers = answers;
    }
}

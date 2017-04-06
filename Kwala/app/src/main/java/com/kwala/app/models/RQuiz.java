package com.kwala.app.models;

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author jacobamuchow@gmail.com
 */
@GenerateRealmFieldNames
public class RQuiz extends RealmObject {
    private static final String TAG = RQuiz.class.getSimpleName();

    @PrimaryKey private String quizId;
    private RealmList<RQuizQuestion> questions;

    public String getQuizId() {
        return quizId;
    }

    public RealmList<RQuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(RealmList<RQuizQuestion> questions) {
        this.questions = questions;
    }
}

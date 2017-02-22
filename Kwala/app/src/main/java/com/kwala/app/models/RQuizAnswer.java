package com.kwala.app.models;

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author jacobamuchow@gmail.com
 */
@GenerateRealmFieldNames
public class RQuizAnswer extends RealmObject {
    private static final String TAG = RQuizAnswer.class.getSimpleName();

    @PrimaryKey private String answerId;
    @Required private String answer;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

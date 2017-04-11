package com.kwala.app.service.realm;

import android.support.annotation.MainThread;

import com.kwala.app.models.RMessage;
import com.kwala.app.models.RQuizQuestion;
import com.quarkworks.android.realmtypesafequery.generated.RQuizQuestionFieldNames;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author jacobamuchow@gmail.com
 */
public class RealmQueries {
    private static final String TAG = RealmQueries.class.getSimpleName();

    private Realm realm;

    /*
        Constructors
     */
    private RealmQueries(Realm realm) {
        this.realm = realm;
    }

    @MainThread
    public static RealmQueries withMainRealm() {
        return new RealmQueries(Realm.getDefaultInstance());
    }

    public static RealmQueries withRealm(Realm realm) {
        return new RealmQueries(realm);
    }

    /*
        Generics
     */
    public <T extends RealmModel> T get(Class<T> clazz, String primaryKey) {
        String primaryKeyFieldName = realm.getSchema().get(clazz.getSimpleName()).getPrimaryKey();
        return realm.where(clazz).equalTo(primaryKeyFieldName, primaryKey).findFirst();
    }

    public <T extends RealmObject> RealmResults<T> getAll(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    /*
     * Quizzes
     */
    public RealmResults<RQuizQuestion> getQuizQuestions() {
        return realm.where(RQuizQuestion.class)
                .findAllSorted(RQuizQuestionFieldNames.QUESTION_ID, Sort.ASCENDING);
    }

    /*
     * Chat
     */
    public RealmResults<RMessage> getMessages(String matchId) {
        return realm.where(RMessage.class)
                .findAllSorted("messageId", Sort.ASCENDING);
    }
}

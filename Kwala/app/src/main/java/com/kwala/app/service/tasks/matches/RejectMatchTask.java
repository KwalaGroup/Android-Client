package com.kwala.app.service.tasks.matches;

import com.kwala.app.enums.MatchState;
import com.kwala.app.models.RMatch;
import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.realm.RealmQueries;
import com.kwala.app.service.realm.RealmWrites;
import com.kwala.app.service.tasks.APIPaths;
import com.kwala.app.service.tasks.NetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com
 */

public class RejectMatchTask extends NetworkTask<Void> {
    private static final String TAG = RejectMatchTask.class.getSimpleName();

    private String matchId;

    public RejectMatchTask(String matchId) {
        this.matchId = matchId;
    }

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new APIEndpoint(APIPaths.Matches.REJECT, Endpoint.Method.POST)
                .addParam("id", matchId);
    }

    @Override
    protected void run() {
        super.run();

        RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
            @Override
            public Void execute(Realm realm) {
                RMatch match = RealmQueries.withRealm(realm).get(RMatch.class, matchId);
                if (match != null) {
                    match.setMatchState(MatchState.REJECT_SENT);
                }
                return null;
            }
        });
    }

    @Override
    protected Void parse(JSONObject jsonObject) throws JSONException {
        return null;
    }
}

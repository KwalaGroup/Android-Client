package com.kwala.app.service.endpoints;

import android.util.Log;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class ITunesRssEndpoint extends Endpoint<JSONObject> {
    private static final String TAG = ITunesRssEndpoint.class.getSimpleName();

    public ITunesRssEndpoint() {
        super("https://itunes.apple.com/us/rss/topaudiobooks/limit=10/json", Method.GET, null);
    }

    @Override
    public JSONObject parse(int code, String response) throws Exception {
        Log.d(TAG, "parse: " + code + " " + response);

        return new JSONObject(response);
    }
}

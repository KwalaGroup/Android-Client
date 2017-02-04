package com.kwala.app.service.tasks;

import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.ITunesRssEndpoint;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */

public class ITunesRssTask extends Task<JSONObject> {

    @Override
    protected Endpoint<JSONObject> buildEndpoint() {
        return new ITunesRssEndpoint();
    }
}

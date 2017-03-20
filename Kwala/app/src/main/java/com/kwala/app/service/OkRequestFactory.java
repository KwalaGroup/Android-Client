package com.kwala.app.service;

import android.util.Log;

import com.kwala.app.service.endpoints.Endpoint;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author jacobamuchow@gmail.com
 */
public class OkRequestFactory {
    private static final String TAG = OkRequestFactory.class.getSimpleName();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static <T> Request createRequest(Endpoint<T> endpoint) {
        return new Request.Builder()
                .url(endpoint.getUrl())
                .method(getOkMethod(endpoint), getOkBody(endpoint))
                .build();
    }

    private static <T> String getOkMethod(Endpoint<T> endpoint) {
        switch (endpoint.getMethod()) {
            case GET: return "GET";
            case POST: return "POST";
            case PUT: return "PUT";
            case DELETE: return "DELETE";
            default: throw new IllegalStateException("Method not supported: " + endpoint.getMethod());
        }
    }

    private static <T> RequestBody getOkBody(Endpoint<T> endpoint) {

        JSONObject jsonObject = endpoint.getParams() == null ? new JSONObject()
                : new JSONObject(endpoint.getParams());

        Log.d(TAG, "body: " + jsonObject.toString());

        return RequestBody.create(MEDIA_TYPE_JSON, jsonObject.toString());
    }
}

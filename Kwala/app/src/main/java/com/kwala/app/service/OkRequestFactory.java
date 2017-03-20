package com.kwala.app.service;

import android.util.Log;

import com.kwala.app.service.endpoints.Endpoint;

import org.json.JSONObject;

import okhttp3.HttpUrl;
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
                .url(getUrl(endpoint))
                .method(getMethod(endpoint), getBody(endpoint))
                .build();
    }

    private static <T> HttpUrl getUrl(Endpoint<T> endpoint) {

        HttpUrl.Builder builder = HttpUrl.parse(endpoint.getUrl()).newBuilder();

        if (endpoint.getMethod() == Endpoint.Method.GET && endpoint.getParams() != null) {
            for (String key : endpoint.getParams().keySet()) {
                builder.addQueryParameter(key, endpoint.getParams().get(key));
            }
        }

        return builder.build();
    }

    private static <T> String getMethod(Endpoint<T> endpoint) {
        switch (endpoint.getMethod()) {
            case GET: return "GET";
            case POST: return "POST";
            case PUT: return "PUT";
            case DELETE: return "DELETE";
            default: throw new IllegalStateException("Method not supported: " + endpoint.getMethod());
        }
    }

    private static <T> RequestBody getBody(Endpoint<T> endpoint) {
        if (endpoint.getMethod() == Endpoint.Method.GET) {
            return null;
        }

        JSONObject jsonObject = endpoint.getParams() == null ? new JSONObject()
                : new JSONObject(endpoint.getParams());

        Log.d(TAG, "body: " + jsonObject.toString());

        return RequestBody.create(MEDIA_TYPE_JSON, jsonObject.toString());
    }
}

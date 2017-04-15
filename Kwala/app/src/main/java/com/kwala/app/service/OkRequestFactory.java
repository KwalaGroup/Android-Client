package com.kwala.app.service;

import android.util.Log;

import com.kwala.app.service.endpoints.Endpoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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

        HttpUrl baseHttpUrl = HttpUrl.parse(endpoint.getUrl());
        if (baseHttpUrl == null) {
            throw new IllegalStateException("Malformed URL: " + endpoint.getUrl());
        }

        HttpUrl.Builder builder = baseHttpUrl.newBuilder();

        //Add params to query string if Method is GET
        if (endpoint.getParams() != null && useQueryParameters(endpoint)) {
            Iterator<String> iterator = endpoint.getParams().keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                try {
                    builder.addQueryParameter(key, endpoint.getParams().get(key).toString());
                } catch (JSONException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        return builder.build();
    }

    private static <T> boolean useQueryParameters(Endpoint<T> endpoint) {
        return endpoint.getMethod() == Endpoint.Method.GET
                || endpoint.getMethod() == Endpoint.Method.DELETE;
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
                : endpoint.getParams();

        if (endpoint.shouldLog()) {
            Log.d(TAG, "body: " + jsonObject.toString());
        }

        return RequestBody.create(MEDIA_TYPE_JSON, jsonObject.toString());
    }
}

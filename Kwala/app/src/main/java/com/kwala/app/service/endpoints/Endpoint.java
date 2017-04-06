package com.kwala.app.service.endpoints;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author muchow@hello.com
 */
public abstract class Endpoint<Result> {
    private static final String TAG = Endpoint.class.getSimpleName();

    public enum Method {
        GET, POST, PUT, DELETE
    }

    private String url;
    private Method method = Method.GET;

    @Nullable
    private JSONObject params;

    public Endpoint(String url, Method method) {
        this(url, method, null);
    }

    public Endpoint(String url, Method method, @Nullable JSONObject params) {
        this.url = url;
        this.method = method;
    }

    public abstract Result parse(int code, String response) throws Exception;

    /**
     * Builder methods
     */
    public Endpoint<Result> setParams(@Nullable JSONObject params) {
        this.params = params;
        return this;
    }

    public Endpoint<Result> addParam(@NonNull String key, @NonNull Object value) {
        if (params == null) {
            params = new JSONObject();
        }
        try {
            params.put(key, value);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
        return this;
    }

    public Endpoint<Result> addOptParam(@NonNull String key, @Nullable Object value) {
        return value == null ? this : addParam(key, value);
    }

    /**
     * Getters / setters
     */
    public String getUrl() {
        return url;
    }

    public Method getMethod() {
        return method;
    }

    @Nullable
    public JSONObject getParams() {
        return params;
    }
}

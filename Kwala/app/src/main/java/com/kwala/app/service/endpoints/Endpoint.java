package com.kwala.app.service.endpoints;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

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
    private Map<String, Object> params = null;

    public Endpoint(String url, Method method) {
        this(url, method, null);
    }

    public Endpoint(String url, Method method, @Nullable Map<String, Object> params) {
        this.url = url;
        this.method = method;
        this.params = params;
    }

    public abstract Result parse(int code, String response) throws Exception;

    /**
     * Builder methods
     */
    public Endpoint<Result> setParams(@Nullable Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public Endpoint<Result> addParam(@NonNull String key, @NonNull Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
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
    public Map<String, Object> getParams() {
        return params;
    }
}

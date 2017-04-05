package com.kwala.app.service.endpoints;

import android.support.annotation.Nullable;

import java.util.HashMap;

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
    private HashMap<String, Object> params = null;

    public Endpoint(String url, Method method) {
        this(url, method, null);
    }

    public Endpoint(String url, Method method, @Nullable HashMap<String, Object> params) {
        this.url = url;
        this.method = method;
        this.params = params;
    }

    public abstract Result parse(int code, String response) throws Exception;

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
    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(@Nullable HashMap<String, Object> params) {
        this.params = params;
    }
}

package com.kwala.app.service.endpoints;

import android.support.annotation.Nullable;

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
    private Map<String, String> params = null;

    public Endpoint(String url, Method method) {
        this(url, method, null);
    }

    public Endpoint(String url, Method method, @Nullable Map<String, String> params) {
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
    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(@Nullable Map<String, String> params) {
        this.params = params;
    }
}

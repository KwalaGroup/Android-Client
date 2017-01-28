package com.kwala.app.service;

import java.util.Map;

/**
 * @author muchow@hello.com
 */
class Endpoint<Result> {
    private static final String TAG = Endpoint.class.getSimpleName();

    public enum Method {
        GET, POST, PUT, DELETE
    }

    private String url;
    private Method method;
    private Map<String, String> params;

    public Endpoint(String url, Method method, Map<String, String> params) {
        this.url = url;
        this.method = method;
        this.params = params;
    }

    public Result parse(int code, String response) throws Exception {
        //TODO: check code
        return null;
    }

    /**
     * Getters / setters
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}

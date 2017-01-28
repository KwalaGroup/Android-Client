package com.kwala.app.service;

/**
 * @author muchow@hello.com
 */
public class EndpointRequest<Result> {
    private static final String TAG = EndpointRequest.class.getSimpleName();

    private enum Status {
        READY, PENDING, SUCCESS, FAILURE, CANCELLED
    }

    private Endpoint<Result> endpoint;
    private Status status = Status.READY;

    private Callback callback;

    public EndpointRequest(Endpoint<Result> endpoint, Callback callback) {
        this.endpoint = endpoint;
        this.callback = callback;
    }

    public static <T> EndpointRequest<T> create(Endpoint<T> endpoint, Callback callback) {
        return new EndpointRequest<>(endpoint, callback);
    }

    /**
     * Getters / setters
     */
    public Endpoint<Result> getEndpoint() {
        return endpoint;
    }

    public boolean isReady() {
        return status == Status.READY;
    }

    public boolean isPending() {
        return isReady() || status == Status.PENDING;
    }

    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }

    public boolean isFailed() {
        return status == Status.FAILURE;
    }

    public boolean isCancelled() {
        return status == Status.CANCELLED;
    }

    public boolean isResolved() {
        return isSuccessful() || isFailed() || isCancelled();
    }

    public interface Callback<R> {
        void success(int code, String response);
        void failure(Error error);
    }
}

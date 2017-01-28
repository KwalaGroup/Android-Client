package com.kwala.app.service;

import android.support.annotation.Nullable;

/**
 * @author jacobamuchow@gmail.com
 */
public abstract class Task<Result> {
    private static final String TAG = Task.class.getSimpleName();

    private enum Status {
        READY, PENDING, SUCCESS, FAILURE, CANCELLED
    }

    private Status status = Status.READY;
    @Nullable private Result result;

    protected abstract Endpoint<Result> buildEndpoint();

    protected void run() {

        status = Status.PENDING;

        final EndpointRequest<Result> endpointRequest;

        endpointRequest = DataStore.getInstance().getNetworkStore().performRequest(buildEndpoint(), new EndpointRequest.Callback() {
            @Override
            public void success(int code, String response) {

            }

            @Override
            public void failure(Error error) {

            }
        });
    }

    /**
     * Getters / setters
     */
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

    /**
     * Callback interface
     */
    public static class Callback<Result> {
        public void onSuccess(Result result) {}
    }
}

package com.kwala.app.service.tasks;

import android.support.annotation.Nullable;

import com.kwala.app.service.DataStore;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.EndpointRequest;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */
public abstract class Task<Result> {
    private static final String TAG = Task.class.getSimpleName();

    private enum Status {
        READY, PENDING, SUCCESS, FAILURE, CANCELLED
    }

    private Status mStatus = Status.READY;
    @Nullable private Result mResult;

    private Callback<Result> mCallback;

    protected abstract Endpoint<JSONObject> buildEndpoint();

    protected abstract Result parse(JSONObject jsonObject);

    public void start(Callback<Result> callback) {
        synchronized (this) {
            mStatus = Status.READY;
            mResult = null;

            this.mCallback = callback;
        }

        run();
    }

    protected void run() {
        mStatus = Status.PENDING;

        DataStore.getInstance().getNetworkStore().performRequest(buildEndpoint(), new EndpointRequest.Callback<JSONObject>() {
            @Override
            public void success(JSONObject result) {
                mResult = parse(result);
                mStatus = Status.SUCCESS;

                mCallback.onSuccess(mResult);
            }

            @Override
            public void failure(Exception e) {
                mResult = null;
                mStatus = Status.FAILURE;

                mCallback.onFailure(e);
            }
        });
    }

    /**
     * Getters / setters
     */
    public boolean isReady() {
        return mStatus == Status.READY;
    }

    public boolean isPending() {
        return isReady() || mStatus == Status.PENDING;
    }

    public boolean isSuccessful() {
        return mStatus == Status.SUCCESS;
    }

    public boolean isFailed() {
        return mStatus == Status.FAILURE;
    }

    public boolean isCancelled() {
        return mStatus == Status.CANCELLED;
    }

    public boolean isResolved() {
        return isSuccessful() || isFailed() || isCancelled();
    }

    /**
     * Callback interface
     */
    public interface Callback<Result> {
        void onSuccess(Result result);
        void onFailure(Exception e);
    }
}

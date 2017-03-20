package com.kwala.app.service.tasks;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kwala.app.service.DataStore;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.EndpointRequest;

import org.json.JSONException;
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

    @Nullable private Callback<Result> mCallback;

    protected abstract Endpoint<JSONObject> buildEndpoint();

    protected abstract Result parse(JSONObject jsonObject) throws JSONException;

    public void start(@Nullable Callback<Result> callback) {
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
                Log.d(TAG, "response: " + result);

                try {
                    mResult = parse(result);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing result", e);
                    failure(e);
                    return;
                }

                mStatus = Status.SUCCESS;

                if (mCallback != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onSuccess(mResult);
                        }
                    });
                }
            }

            @Override
            public void failure(final Exception e) {
                mResult = null;
                mStatus = Status.FAILURE;

                if (mCallback != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onFailure(e);
                        }
                    });
                }
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

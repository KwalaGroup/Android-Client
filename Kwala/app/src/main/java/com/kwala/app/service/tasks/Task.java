package com.kwala.app.service.tasks;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.kwala.app.service.endpoints.Endpoint;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */
public abstract class Task<Result> {
    private static final String TAG = Task.class.getSimpleName();

    public enum Status {
        READY, PENDING, SUCCESS, FAILURE, CANCELLED
    }

    protected Status mStatus = Status.READY;
    @Nullable protected Result mResult;

    @Nullable protected Callback<Result> mCallback;

    public void start(@Nullable Callback<Result> callback) {
        synchronized (this) {
            mStatus = Status.READY;
            mResult = null;

            this.mCallback = callback;
        }

        callRun();
    }

    private void callRun() {
        mStatus = Status.PENDING;
        run();
    }

    protected abstract void run();

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

    public void resolve(Result result) {
        this.mStatus = Status.SUCCESS;
        this.mResult = result;

        if (mCallback != null) {
            mCallback.onSuccess(result);
        }
    }

    public void resolveOnMain(final Result result) {
        this.mStatus = Status.SUCCESS;
        this.mResult = result;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onSuccess(mResult);
                }
            }
        });
    }

    public void reject(Exception e) {
        this.mStatus = Status.FAILURE;
        this.mResult = null;

        if (mCallback != null) {
            mCallback.onFailure(e);
        }
    }

    public void rejectOnMain(final Exception e) {
        this.mStatus = Status.FAILURE;
        this.mResult = null;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onFailure(e);
                }
            }
        });
    }

    /**
     * Callback interface
     */
    public interface Callback<Result> {
        void onSuccess(Result result);
        void onFailure(Exception e);
    }
}

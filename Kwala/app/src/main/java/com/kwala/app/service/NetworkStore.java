package com.kwala.app.service;

import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.kwala.app.KwalaApplication;
import com.kwala.app.helpers.KwalaConstants;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.EndpointRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jacobamuchow@gmail.com
 */
public class NetworkStore {
    private static final String TAG = NetworkStore.class.getSimpleName();

    private OkHttpClient okHttpClient;
    private AmazonS3Client s3Client;
    private TransferUtility transferUtility;

    public NetworkStore() {
        okHttpClient = new OkHttpClient.Builder()
                .build();

        KwalaApplication application = KwalaApplication.getInstance();

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(application,
                KwalaConstants.Network.AWS_IDENTITY_POOL_ID,
                Regions.US_EAST_1
        );

        // Initialize Amazon S3 and transfer utility
        s3Client = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3Client, application);
    }

    public <T> EndpointRequest<T> performRequest(final Endpoint<T> endpoint, final EndpointRequest.Callback<T> callback) {

        Request request = OkRequestFactory.createRequest(endpoint);

        okhttp3.Callback responseCallback = new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response);
                try {
                    T result = endpoint.parse(response.code(), response.body().string());
                    callback.success(result);
                } catch (Exception e) {
                    callback.failure(e);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(e);
            }
        };

        okHttpClient.newCall(request).enqueue(responseCallback);

        return new EndpointRequest<>(endpoint, callback);
    }

    public void uploadImage(File file) {

        String imageId = UUID.randomUUID().toString();

        TransferObserver observer = transferUtility.upload(KwalaConstants.Network.S3_BUCKET_NAME, imageId, file);

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d(TAG, "" + id + ": " + state.name());
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.d(TAG, "" + id + ": " + bytesCurrent + " / " + bytesTotal);
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e(TAG, "Error uploading image: " + id, ex);
            }
        });
    }
}

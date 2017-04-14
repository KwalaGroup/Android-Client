package com.kwala.app.service;

import android.net.Uri;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.kwala.app.helpers.KwalaConstants;
import com.kwala.app.main.KwalaApplication;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.EndpointRequest;
import com.kwala.app.service.endpoints.NetworkException;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jacobamuchow@gmail.com
 */
public class NetworkStore {
    private static final String TAG = NetworkStore.class.getSimpleName();

    private KwalaCookieStore kwalaCookieStore;
    private OkHttpClient okHttpClient;

    private AmazonS3Client s3Client;
    private TransferUtility transferUtility;

    public NetworkStore() {

        kwalaCookieStore = new KwalaCookieStore(KwalaApplication.getInstance());
        CookieManager cookieManager = new CookieManager(kwalaCookieStore, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(cookieManager);

        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
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

    public void clearData() {
        kwalaCookieStore.removeAll();
    }

    public <T> EndpointRequest<T> performRequest(final Endpoint<T> endpoint, final EndpointRequest.Callback<T> callback) {

        Request request = OkRequestFactory.createRequest(endpoint);

        if (endpoint.shouldLog()) {
            Log.d(TAG, "Request: " + request.toString());
        }

        okhttp3.Callback responseCallback = new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.code() != 200) {
                        if (!endpoint.shouldLog()) {
                            Log.e(TAG, "Silent endpoint failed");
                            Log.d(TAG, "Request: " + response);
                            Log.d(TAG, "Response: " + response);
                        }

                        callback.failure(new NetworkException(response.code(), response.message()));
                        return;
                    }

                    if (endpoint.shouldLog()) {
                        Log.d(TAG, "Response: " + response);
                    }

                    T result = endpoint.parse(response.code(), response.body().string());
                    callback.success(result);

                } catch (NetworkException e) {
                    callback.failure(e);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.failure(new NetworkException(e));
            }
        };

        okHttpClient.newCall(request).enqueue(responseCallback);

        return new EndpointRequest<>(endpoint, callback);
    }

    public void uploadImage(Uri imageUri, final ImageUploadObserver imageUploadObserver) {

        final File file = new File(imageUri.getPath());
        final String imageId = UUID.randomUUID().toString();

        final TransferObserver observer = transferUtility.upload(KwalaConstants.Network.S3_BUCKET_NAME, imageId, file);

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d(TAG, "" + id + ": " + state.name());
                imageUploadObserver.onStateChanged(imageId, state);
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.d(TAG, "" + id + ": " + bytesCurrent + " / " + bytesTotal);
                imageUploadObserver.onProgressChanged(imageId, bytesCurrent, bytesTotal);
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e(TAG, "Error uploading image: " + id, ex);
                imageUploadObserver.onError(imageId, ex);
            }
        });
    }

    public interface ImageUploadObserver {
        void onStateChanged(String imageId, TransferState state);
        void onProgressChanged(String imageId, long bytesCurrent, long bytesTotal);
        void onError(String imageId, Exception e);
    }
}

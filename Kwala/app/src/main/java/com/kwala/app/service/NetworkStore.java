package com.kwala.app.service;

import android.content.Intent;
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
import com.kwala.app.service.endpoints.APIEndpoint;
import com.kwala.app.service.endpoints.Endpoint;
import com.kwala.app.service.endpoints.EndpointRequest;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.realm.RealmSyncs;
import com.kwala.app.service.realm.RealmWrites;
import com.kwala.app.service.tasks.APIPaths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * @author jacobamuchow@gmail.com
 */
public class NetworkStore {
    private static final String TAG = NetworkStore.class.getSimpleName();

    /*
        References
     */
    private KwalaCookieStore kwalaCookieStore;
    private OkHttpClient okHttpClient;

    private AmazonS3Client s3Client;
    private TransferUtility transferUtility;

    /*
        Image upload observer
     */
    public interface ImageUploadObserver {
        void onStateChanged(String imageId, TransferState state);
        void onProgressChanged(String imageId, long bytesCurrent, long bytesTotal);
        void onError(String imageId, Exception e);
    }

    /*
        Constructor
     */
    NetworkStore() {

        kwalaCookieStore = new KwalaCookieStore(KwalaApplication.getInstance());
        CookieManager cookieManager = new CookieManager(kwalaCookieStore, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(cookieManager);

        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .authenticator(authenticator)
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

    private final Authenticator authenticator = new Authenticator() {
        @Override
        public synchronized Request authenticate(Route route, Response originalResponse) throws IOException {
            UserData userData = UserData.getInstance();
            if (!userData.isLoggedIn() || userData.getEnteredEmail() == null || userData.getHashedPassword() == null) {
                Log.e(TAG, "Unable to reauthenticate: missing data");
                forceLogout();
                return null;
            }

            Endpoint<JSONObject> endpoint = new APIEndpoint(APIPaths.Auth.LOGIN, Endpoint.Method.POST)
                    .addParam("email", userData.getEnteredEmail())
                    .addParam("password", userData.getHashedPassword());

            Response loginResponse = okHttpClient.newCall(OkRequestFactory.createRequest(endpoint)).execute();
            if (loginResponse.code() != 200) {
                Log.e(TAG, "Login retry failed");
                Log.e(TAG, loginResponse.toString());
                forceLogout();
                return null;
            }

            final JSONObject result;
            try {
                result = endpoint.parse(loginResponse.code(), loginResponse.body().string());
            } catch (NetworkException e) {
                Log.e(TAG, "Error parsing background login response", e);
                forceLogout();
                return null;
            }

            try {
                UserData.getInstance().updateFromJson(result);
            } catch (JSONException e) {
                Log.e(TAG, "Error updating UserData from background login response", e);
                forceLogout();
                return null;
            }

            try {
                //Sync filters
                final JSONArray filtersJsonArray = result.getJSONArray("filters");

                RealmWrites.withDefaultRealm().executeTransaction(new RealmWrites.Transaction<Void>() {
                    @Override
                    public Void execute(Realm realm) {
                        try {
                            RealmSyncs.withRealm(realm).syncFilters(filtersJsonArray);
                        } catch (JSONException e) {
                            Log.d(TAG, "Error syncing filters", e);
                        }

                        return null;
                    }
                });
            } catch (JSONException e) {
                Log.e(TAG, "Error syncing filters from background login response", e);
            }

            Log.d(TAG, "Login retry success!");
            return originalResponse.request();
        }
    };

    private void forceLogout() {
        KwalaApplication application = KwalaApplication.getInstance();

        //Stop location update service
        Intent intent = new Intent(application, LocationService.class);
        application.stopService(intent);

        DataStore.getInstance().clearAllData();

        //Restart the app
        intent = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        application.startActivity(intent);
    }
}

package com.kwala.app.service.tasks.image;

import android.net.Uri;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.kwala.app.service.DataStore;
import com.kwala.app.service.NetworkStore;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.tasks.Task;

/**
 * @author jacobamuchow@gmail.com
 */

public class UploadImageTask extends Task<String, NetworkException> {
    private static final String TAG = UploadImageTask.class.getSimpleName();

    private Uri imageUri;

    public UploadImageTask(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    protected void run() {
        DataStore.getInstance().getNetworkStore().uploadImage(imageUri, new NetworkStore.ImageUploadObserver() {
            @Override
            public void onStateChanged(String imageId, TransferState state) {
                switch (state) {
                    case COMPLETED:
                        resolveOnMain(imageId);
                        break;

                    case CANCELED:
                        rejectOnMain(new NetworkException("Cancelled"));
                        break;

                    case FAILED:
                        rejectOnMain(new NetworkException("Failed"));
                        break;
                }
            }

            @Override
            public void onProgressChanged(String imageId, long bytesCurrent, long bytesTotal) {
                //Ignoring for now
            }

            @Override
            public void onError(String imageId, Exception e) {
                rejectOnMain(new NetworkException(e));
            }
        });
    }
}

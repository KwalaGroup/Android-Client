package com.kwala.app.service.tasks;

import android.net.Uri;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.kwala.app.service.DataStore;
import com.kwala.app.service.NetworkStore;

/**
 * @author jacobamuchow@gmail.com
 */

public class UploadImageTask extends Task<String> {
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
                        rejectOnMain(new Exception("Cancelled"));
                        break;

                    case FAILED:
                        rejectOnMain(new Exception("Failed"));
                        break;
                }
            }

            @Override
            public void onProgressChanged(String imageId, long bytesCurrent, long bytesTotal) {
                //Ignoring for now
            }

            @Override
            public void onError(String imageId, Exception e) {
                rejectOnMain(e);
            }
        });
    }
}

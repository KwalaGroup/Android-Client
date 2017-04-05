package com.kwala.app.service.tasks.profile;

import android.net.Uri;
import android.util.Log;

import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.image.UploadImageTask;

/**
 * @author jacobamuchow@gmail.com
 */

public class UpdateProfileImageTask extends Task<Void> {
    private static final String TAG = UpdateProfileImageTask.class.getSimpleName();

    private Uri imageUri;

    public UpdateProfileImageTask(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    protected void run() {
        new UploadImageTask(imageUri).start(new Task.Callback<String>() {
            @Override
            public void onSuccess(String imageId) {
                Log.d(TAG, "image uploaded: " + imageId);
                updateProfile(imageId);
            }

            @Override
            public void onFailure(Exception e) {
                rejectOnMain(e);
            }
        });
    }

    private void updateProfile(String imageId) {
        new UpdateProfileTask(imageId).start(new Callback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "profile updated successfully");
                resolveOnMain(null);
            }

            @Override
            public void onFailure(Exception e) {
                rejectOnMain(e);
            }
        });
    }
}

package com.kwala.app.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.kwala.app.helpers.navigation.BaseActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

/**
 * @author jacobamuchow@gmail.com
 */

public class PhotoHelper {
    private static final String TAG = PhotoHelper.class.getSimpleName();

    private static Callback callback;

    public interface Callback {
        void onSuccess(Uri imageUri);
        void onFailure(Failure failure);
    }

    public enum Failure {
        PERMISSION_DENIED, CANCELLED, CROP_IMAGE_ERROR,
    }

    private static void callOnFailure(Failure failure) {
        if (callback != null) {
            callback.onFailure(failure);
        }
    }

    private static void callOnSuccess(Uri imageUri) {
        if (callback != null) {
            callback.onSuccess(imageUri);
        }
    }

    public static void showChooserDialog(final BaseActivity activity, final Callback callback) {
        PhotoHelper.callback = callback;

        Nammu.askForPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
            @Override
            public void permissionGranted() {
                Intent intent = CropImage.getPickImageChooserIntent(activity);
                activity.startActivityForResult(intent, CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
            }

            @Override
            public void permissionRefused() {
                callOnFailure(Failure.PERMISSION_DENIED);
            }
        });
    }

    public static boolean handleOnActivityResult(int requestCode, int resultCode, Intent data, BaseActivity activity) {

        /*
         * Image chooser result
         */
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                callOnFailure(Failure.CANCELLED);
                return true;
            }

            Uri imageUri = CropImage.getPickImageResultUri(activity, data);

            Intent intent = CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .setFixAspectRatio(true)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .getIntent(activity);

            activity.startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            return true;
        }

        /*
         * Image cropper result
         */
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                callOnFailure(Failure.CANCELLED);
                return true;
            }

            Log.d(TAG, "activity result for crop");

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result == null) {
                callOnFailure(Failure.CROP_IMAGE_ERROR);
                return true;
            }

            if (result.getError() != null) {
                Log.e(TAG, "Error cropping image", result.getError());
                callOnFailure(Failure.CROP_IMAGE_ERROR);
                return true;
            }

            if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                callOnFailure(Failure.CROP_IMAGE_ERROR);
                return true;
            }

            Log.d(TAG, "Photo cropped successfully: " + result.getUri());
            callOnSuccess(result.getUri());
            return true;
        }

        return false;
    }
}

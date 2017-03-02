package com.kwala.app.profile;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kwala.app.R;
import com.kwala.app.helpers.BaseFragment;
import com.kwala.app.service.DataStore;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

/**
 * @author jacobamuchow@gmail.com
 */

public class MyProfileFragment extends BaseFragment {
    private static final String TAG = MyProfileFragment.class.getSimpleName();

    private ImageView profileImageView;

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Nammu.init(getActivity());

        EasyImage.configuration(getActivity())
                .setImagesFolderName("Kwala");

        profileImageView = (ImageView) view.findViewById(R.id.my_profile_fragment_profile_image);
        Button takePhotoButton = (Button) view.findViewById(R.id.my_profile_fragment_take_photo_button);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Nammu.askForPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                    @Override
                    public void permissionGranted() {
//                        EasyImage.openChooserWithDocuments(MyProfileFragment.this, "", 0);

                        Intent intent = CropImage.getPickImageChooserIntent(getActivity());
                        startActivityForResult(intent, CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
                    }

                    @Override
                    public void permissionRefused() {
                        // :(
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
            Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);

            Intent intent = CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .setFixAspectRatio(true)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .getIntent(getActivity());

            startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.d(TAG, "activity result for crop");

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (result.getError() != null) {
                Log.e(TAG, "Error cropping image", result.getError());
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            } else {
                profileImageView.setImageURI(result.getUri());
                Log.d(TAG, "image set");
                Log.d(TAG, "bitmap null: " + (result.getBitmap() == null));

                File file = new File(result.getUri().getPath());
                DataStore.getInstance().getNetworkStore().uploadImage(file);
            }
        }

//        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
//            @Override
//            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
//
////                CropImage.
////                profileImageView.setImageURI(Uri.fromFile(imageFile));
//            }
//        });
    }
}

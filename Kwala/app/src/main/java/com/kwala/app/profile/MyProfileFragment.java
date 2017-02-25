package com.kwala.app.profile;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.kwala.app.R;
import com.kwala.app.helpers.BaseFragment;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
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
                        EasyImage.openChooserWithGallery(MyProfileFragment.this, "", 0);
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

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                profileImageView.setImageURI(Uri.fromFile(imageFile));
            }
        });
    }
}

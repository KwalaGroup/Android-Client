package com.kwala.app.profile;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.helpers.KwalaImages;
import com.kwala.app.helpers.PhotoHelper;
import com.kwala.app.helpers.navigation.BaseFragment;

/**
 * @author jacobamuchow@gmail.com
 */

public class MyProfileFragment extends BaseFragment {
    private static final String TAG = MyProfileFragment.class.getSimpleName();

    private ImageView profileImageView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView ageTextView;
    private ImageView colorImageView;
    private TextView descriptionTextView;

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

        profileImageView = (ImageView) view.findViewById(R.id.profile_image);
        firstNameTextView = (TextView) view.findViewById(R.id.profile_first_name);
        lastNameTextView = (TextView) view.findViewById(R.id.profile_last_name);
        ageTextView = (TextView) view.findViewById(R.id.profile_age);
        colorImageView = (ImageView) view.findViewById(R.id.profile_circle);
        descriptionTextView = (TextView) view.findViewById(R.id.profile_description);

        profileImageView.setOnClickListener(profileImageClickListener);

        KwalaImages.with(profileImageView).setProfileImageId("f89c8f68-69da-4def-8776-885f9fbe71b3");
    }

    /**
     * Listeners
     */
    private final View.OnClickListener profileImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PhotoHelper.showChooserDialog(getBaseActivity(), new PhotoHelper.Callback() {
                @Override
                public void onSuccess(Uri imageUri) {
                    //TODO: upload, set on server
                }

                @Override
                public void onFailure(PhotoHelper.Failure failure) {

                }
            });
        }
    };
}

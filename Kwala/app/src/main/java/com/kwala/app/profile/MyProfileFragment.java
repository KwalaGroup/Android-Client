package com.kwala.app.profile;

import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.helpers.KwalaImages;
import com.kwala.app.helpers.PhotoHelper;
import com.kwala.app.helpers.Tools;
import com.kwala.app.helpers.navigation.BaseFragment;
import com.kwala.app.helpers.views.ProgressImageLayout;
import com.kwala.app.service.UserData;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.profile.UpdateProfileImageTask;

/**
 * @author jacobamuchow@gmail.com
 */

public class MyProfileFragment extends BaseFragment {
    private static final String TAG = MyProfileFragment.class.getSimpleName();

    /*
        References
     */
    private ProgressImageLayout profileImageLayout;
    private TextView nameTextView;
    private TextView ageTextView;
    private ImageView colorImageView;
    private TextView bioTextView;

    /*
        Data
     */
    private Uri croppedImageUri;

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

        profileImageLayout = (ProgressImageLayout) view.findViewById(R.id.profile_image);
        nameTextView = (TextView) view.findViewById(R.id.profile_name);
        ageTextView = (TextView) view.findViewById(R.id.profile_age);
        colorImageView = (ImageView) view.findViewById(R.id.profile_circle);
        bioTextView = (TextView) view.findViewById(R.id.profile_bio);

        profileImageLayout.setOnClickListener(profileImageClickListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateViews();
    }

    public void updateViews() {
        if (getView() == null) {
            return;
        }

        UserData userData = UserData.getInstance();

        KwalaImages kwalaImages = KwalaImages.with(profileImageLayout.getImageView());
        if (croppedImageUri != null) {
            kwalaImages.setProfileImageUri(croppedImageUri);
        } else {
            kwalaImages.setProfileImageId(userData.getProfileImageId());
        }

        nameTextView.setText(Tools.formatString(getActivity(), R.string.profile_name_formatted,
                userData.getFirstName(), userData.getLastName()));

        Integer age = userData.getAge();
        ageTextView.setText(Tools.formatString(getActivity(), R.string.profile_age_formatted, age == null ? "?" : "" + age));

        GradientDrawable colorDrawable = (GradientDrawable) colorImageView.getDrawable();
        colorDrawable.setColor(userData.getColorAsInt());

        bioTextView.setText(userData.getBio());
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
                    croppedImageUri = imageUri;
                    updateProfileImage(imageUri);
                    updateViews();
                }

                @Override
                public void onFailure(PhotoHelper.Failure failure) {

                }
            });
        }
    };

    private void updateProfileImage(Uri imageUri) {
        new UpdateProfileImageTask(imageUri).start(new Task.Callback<Void, NetworkException>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "UI success");
                profileImageLayout.hideProgress();
                croppedImageUri = null;
            }

            @Override
            public void onFailure(NetworkException e) {
                Log.e(TAG, "UI failure", e);
                profileImageLayout.hideProgress();
                croppedImageUri = null;
            }
        });

        profileImageLayout.showProgress();
    }
}

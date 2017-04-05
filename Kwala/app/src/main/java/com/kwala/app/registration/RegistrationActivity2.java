package com.kwala.app.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kwala.app.R;
import com.kwala.app.enums.Gender;
import com.kwala.app.helpers.KwalaImages;
import com.kwala.app.helpers.PhotoHelper;
import com.kwala.app.helpers.SimpleTextWatcher;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.helpers.views.KwalaEditText;
import com.kwala.app.helpers.views.KwalaProgressSpinner;
import com.kwala.app.main.MainActivity;
import com.kwala.app.service.RegistrationData;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.image.UploadImageTask;
import com.kwala.app.service.tasks.auth.RegisterTask;

import static com.kwala.app.enums.Gender.FEMALE;
import static com.kwala.app.enums.Gender.MALE;
import static com.kwala.app.enums.Gender.UNKNOWN;

/**
 * Created by sijaebrown on 2/4/17.
 */

public class RegistrationActivity2 extends BaseActivity {
    private static final String TAG = RegistrationActivity2.class.getSimpleName();

    /*
        References
     */
    private ImageView profileImageView;
    private ImageView maleIcon;
    private ImageView femaleIcon;

    private KwalaEditText firstNameEditText;
    private KwalaEditText lastNameEditText;
    private KwalaEditText ageEditText;

    private KwalaProgressSpinner progressSpinner;
    private Button finishRegButton;

    private boolean networkPending = false;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity2.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration2_activity);

        /*
         * Get view references
         */
        profileImageView = (ImageView) findViewById(R.id.registration_profile_image);
        femaleIcon = (ImageView) findViewById(R.id.female_icon);
        maleIcon = (ImageView) findViewById(R.id.male_icon);

        firstNameEditText = (KwalaEditText) findViewById(R.id.first_name_box);
        lastNameEditText = (KwalaEditText) findViewById(R.id.last_name_box);
        ageEditText = (KwalaEditText) findViewById(R.id.age_box);

        progressSpinner = (KwalaProgressSpinner) findViewById(R.id.registration_progress_spinner);
        finishRegButton = (Button) findViewById(R.id.finish_reg_button);

        /*
         * Set listeners
         */
        profileImageView.setOnClickListener(profileImageClickListener);
        maleIcon.setOnClickListener(maleIconClickListener);
        femaleIcon.setOnClickListener(femaleIconClickListener);
        finishRegButton.setOnClickListener(finishClickListener);

        firstNameEditText.addTextChangedListener(textWatcher);
        lastNameEditText.addTextChangedListener(textWatcher);
        ageEditText.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RegistrationData registrationData = RegistrationData.getInstance();

        if (registrationData.getProfileImageId() != null) {
            KwalaImages.with(profileImageView).setProfileImageId(registrationData.getProfileImageId());
        }

        setGender(registrationData.getGender());

        firstNameEditText.setTextAppend(registrationData.getFirstName());
        lastNameEditText.setTextAppend(registrationData.getLastName());

        if (registrationData.getAge() != null) {
            ageEditText.setTextAppend("" + registrationData.getAge());
        }

        updateViews();
    }

    private boolean isFormComplete() {
        RegistrationData registrationData = RegistrationData.getInstance();

        return !TextUtils.isEmpty(firstNameEditText.getTextTrimmed())
                && !TextUtils.isEmpty(lastNameEditText.getTextTrimmed())
                && getAge() != null
                && registrationData.getGender() != UNKNOWN
                && registrationData.getProfileImageId() != null;
    }

    private void updateViews() {
        finishRegButton.setEnabled(isFormComplete() && !networkPending);

        progressSpinner.setVisibility(networkPending ? View.VISIBLE : View.GONE);
    }

    private void setGender(Gender gender) {
        RegistrationData.getInstance().setGender(gender);

        maleIcon.setColorFilter(null);
        femaleIcon.setColorFilter(null);

        if (gender == MALE) {
            maleIcon.setColorFilter(Color.DKGRAY);
        } else if (gender == FEMALE) {
            femaleIcon.setColorFilter(Color.DKGRAY);
        }

        updateViews();
    }

    @Nullable
    private Integer getAge() {
        try {
            return Integer.valueOf(ageEditText.getTextTrimmed());
        } catch (NumberFormatException e) {
            //Ignore
            return null;
        }
    }

    /**
     * Listeners
     */
    private final View.OnClickListener profileImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PhotoHelper.showChooserDialog(RegistrationActivity2.this, new PhotoHelper.Callback() {
                @Override
                public void onSuccess(Uri imageUri) {
                    Log.d(TAG, "Success!");
                    profileImageView.setImageURI(imageUri);

                    new UploadImageTask(imageUri).start(new Task.Callback<String>() {
                        @Override
                        public void onSuccess(String imageId) {
                            Log.d(TAG, "Image uploaded successfully: " + imageId);

                            RegistrationData.getInstance().setProfileImageId(imageId);
                            updateViews();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e(TAG, "Image upload failure :(", e);
                        }
                    });
                }

                @Override
                public void onFailure(PhotoHelper.Failure failure) {
                    Log.e(TAG, "Failure :( " + failure.name());
                }
            });
        }
    };

    private final View.OnClickListener maleIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setGender(MALE);
        }
    };

    private final View.OnClickListener femaleIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setGender(FEMALE);
        }
    };

    private final SimpleTextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            updateViews();
        }
    };

    private final View.OnClickListener finishClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String firstName = firstNameEditText.getTextTrimmed();
            String lastName = lastNameEditText.getTextTrimmed();

            RegistrationData.getInstance()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(getAge());

            new RegisterTask(RegistrationData.getInstance()).start(new Task.Callback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    networkPending = false;

                    Intent intent = MainActivity.newIntent(RegistrationActivity2.this);
                    startActivity(intent);
                    finishAffinity();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(RegistrationActivity2.this, "Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Failed to register", e);
                    networkPending = false;
                    updateViews();
                }
            });

            networkPending = true;
            updateViews();
        }
    };
}

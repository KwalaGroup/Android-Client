package com.kwala.app.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kwala.app.R;
import com.kwala.app.enums.Gender;
import com.kwala.app.helpers.PhotoHelper;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.helpers.views.KwalaEditText;
import com.kwala.app.main.MainActivity;
import com.kwala.app.service.RegistrationData;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.auth.RegisterTask;

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

    private Button finishRegButton;

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

        finishRegButton = (Button) findViewById(R.id.finish_reg_button);

        /*
         * Set listeners
         */
        profileImageView.setOnClickListener(profileImageClickListener);
        maleIcon.setOnClickListener(maleIconClickListener);
        femaleIcon.setOnClickListener(femaleIconClickListener);
        finishRegButton.setOnClickListener(finishClickListener);
    }

    private void highlightGender(ImageView image) {
        image.setColorFilter(Color.DKGRAY);
        if (image == maleIcon) {
            femaleIcon.setColorFilter(null);
        } else {
            maleIcon.setColorFilter(null);
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
                }

                @Override
                public void onFailure(PhotoHelper.Failure failure) {
                    Log.d(TAG, "Failure :( " + failure.name());
                }
            });
        }
    };

    private final View.OnClickListener maleIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RegistrationData.getInstance().setGender(Gender.MALE);
            highlightGender(maleIcon);
        }
    };

    private final View.OnClickListener femaleIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RegistrationData.getInstance().setGender(Gender.FEMALE);
            highlightGender(femaleIcon);
        }
    };

    private final View.OnClickListener finishClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String firstName = firstNameEditText.getTextTrimmed();
            String lastName = lastNameEditText.getTextTrimmed();
            String ageText = ageEditText.getTextTrimmed();

            Integer age = Integer.valueOf(ageText);

            RegistrationData.getInstance()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(age);

            new RegisterTask(RegistrationData.getInstance()).start(new Task.Callback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Intent intent = MainActivity.newIntent(RegistrationActivity2.this);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(RegistrationActivity2.this, "Error", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Failed to register", e);
                }
            });
        }
    };
}

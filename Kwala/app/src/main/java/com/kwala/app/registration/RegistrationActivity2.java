package com.kwala.app.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kwala.app.R;
import com.kwala.app.enums.Gender;
import com.kwala.app.service.RegistrationData;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.auth.RegisterTask;

/**
 * Created by sijaebrown on 2/4/17.
 */

public class RegistrationActivity2 extends AppCompatActivity {
    private static final String TAG = RegistrationActivity2.class.getSimpleName();

    /*
        References
     */
    private Button finishRegButton;
    private ImageView maleIcon;
    private ImageView femaleIcon;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText ageEditText;

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
        femaleIcon = (ImageView) findViewById(R.id.female_icon);
        maleIcon = (ImageView) findViewById(R.id.male_icon);

        firstNameEditText = (EditText) findViewById(R.id.first_name_box);
        lastNameEditText = (EditText) findViewById(R.id.last_name_box);
        ageEditText = (EditText) findViewById(R.id.age_box);
        finishRegButton = (Button) findViewById(R.id.finish_reg_button);

        /*
         * Set listeners
         */
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

            String firstName = firstNameEditText.getText().toString().trim();
            String lastName = lastNameEditText.getText().toString().trim();
            String ageText = ageEditText.getText().toString().trim();

            Integer age = Integer.valueOf(ageText);

            RegistrationData.getInstance()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(age);

            new RegisterTask(RegistrationData.getInstance()).start(new Task.Callback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
    };
}

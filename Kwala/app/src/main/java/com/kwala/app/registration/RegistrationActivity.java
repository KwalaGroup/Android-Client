package com.kwala.app.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.helpers.views.KwalaEditText;
import com.kwala.app.service.RegistrationData;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.auth.EmailAvailabilityTask;

/**
 * Created by sijaebrown on 1/24/17.
 */

public class RegistrationActivity extends BaseActivity {
    private static final String TAG = RegistrationActivity.class.getSimpleName();

    private KwalaEditText emailEditText;
    private KwalaEditText passwordEditText;
    private Button continueButton;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        emailEditText = (KwalaEditText) findViewById(R.id.registration_email_text);
        passwordEditText = (KwalaEditText) findViewById(R.id.registration_password_text);
        continueButton = (Button) findViewById(R.id.registration_continue_button);

        continueButton.setOnClickListener(continueClickListener);
    }

    private final View.OnClickListener continueClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = emailEditText.getTextTrimmed();
            String password = passwordEditText.getTextTrimmed();

            RegistrationData.getInstance()
                    .setEmail(email)
                    .setHashedPassword(password);

            new EmailAvailabilityTask(email).start(new Task.Callback<Boolean>() {
                @Override
                public void onSuccess(Boolean isAvailable) {
                    if (isAvailable) {
                        startActivity(RegistrationActivity2.newIntent(RegistrationActivity.this));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "This email is already in use", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e(TAG, "Error", e);
                }
            });
        }
    };
}

package com.kwala.app.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kwala.app.R;
import com.kwala.app.helpers.SimpleTextWatcher;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.helpers.views.KwalaEditText;
import com.kwala.app.helpers.views.KwalaProgressSpinner;
import com.kwala.app.service.RegistrationData;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.auth.EmailAvailabilityTask;

/**
 * Created by sijaebrown on 1/24/17.
 */

public class RegistrationActivity extends BaseActivity {
    private static final String TAG = RegistrationActivity.class.getSimpleName();

    private KwalaEditText emailEditText;
    private KwalaEditText passwordEditText;
    private KwalaProgressSpinner progressSpinner;
    private Button continueButton;

    private boolean networkPending = false;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        emailEditText = (KwalaEditText) findViewById(R.id.registration_email_text);
        passwordEditText = (KwalaEditText) findViewById(R.id.registration_password_text);
        progressSpinner = (KwalaProgressSpinner) findViewById(R.id.registration_progress_spinner);
        continueButton = (Button) findViewById(R.id.registration_continue_button);

        emailEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
        continueButton.setOnClickListener(continueClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RegistrationData registrationData = RegistrationData.getInstance();

        emailEditText.setTextAppend(registrationData.getEmail());
        passwordEditText.setTextAppend(registrationData.getHashedPassword());

        updateViews();
    }

    private boolean isFormComplete() {
        return !TextUtils.isEmpty(emailEditText.getTextTrimmed())
                && !TextUtils.isEmpty(passwordEditText.getTextTrimmed());
    }

    private void updateViews() {
        if (emailEditText == null) {
            return;
        }

        continueButton.setEnabled(isFormComplete() && !networkPending);

        progressSpinner.setVisibility(networkPending ? View.VISIBLE : View.GONE);
    }

    /**
     * Listeners
     */
    private final SimpleTextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
            updateViews();
        }
    };

    private final View.OnClickListener continueClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = emailEditText.getTextTrimmed();
            String password = passwordEditText.getTextTrimmed();

            RegistrationData.getInstance()
                    .setEmail(email)
                    .setHashedPassword(password);

            new EmailAvailabilityTask(email).start(new Task.Callback<Boolean, NetworkException>() {
                @Override
                public void onSuccess(Boolean isAvailable) {
                    networkPending = false;

                    if (isAvailable) {
                        startActivity(RegistrationActivity2.newIntent(RegistrationActivity.this));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "This email is already in use", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(NetworkException e) {
                    Log.e(TAG, "Error", e);
                    networkPending = false;
                    updateViews();
                }
            });

            networkPending = true;
            updateViews();
        }
    };
}

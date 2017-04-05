package com.kwala.app.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kwala.app.R;
import com.kwala.app.helpers.SimpleTextWatcher;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.helpers.views.KwalaEditText;
import com.kwala.app.helpers.views.KwalaProgressSpinner;
import com.kwala.app.main.MainActivity;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.auth.LoginTask;

/**
 * Created by sijaebrown on 1/24/17.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    /*
        References
     */
    private KwalaEditText emailEditText;
    private KwalaEditText passwordEditText;
    private Button loginButton;
    private KwalaProgressSpinner progressSpinner;

    private boolean networkPending = false;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        /*
         * Get view references
         */
        emailEditText = (KwalaEditText) findViewById(R.id.login_email);
        passwordEditText = (KwalaEditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_login_button);
        progressSpinner = (KwalaProgressSpinner) findViewById(R.id.login_progress_spinner);

        emailEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
        loginButton.setOnClickListener(loginClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateViews();
    }

    private boolean isFormComplete() {
        return !TextUtils.isEmpty(emailEditText.getTextTrimmed())
                && !TextUtils.isEmpty(passwordEditText.getTextTrimmed());
    }

    private void updateViews() {
        if (loginButton == null) {
            return;
        }

        loginButton.setEnabled(isFormComplete() && !networkPending);

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

    private final View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = emailEditText.getTextTrimmed();
            String password = passwordEditText.getTextTrimmed();

            new LoginTask(email, password).start(new Task.Callback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    networkPending = false;

                    Intent intent = MainActivity.newIntent(LoginActivity.this);
                    startActivity(intent);
                    finishAffinity();
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e(TAG, "onFailure", e);
                    networkPending = false;
                    updateViews();
                }
            });

            networkPending = true;
            updateViews();
        }
    };
}

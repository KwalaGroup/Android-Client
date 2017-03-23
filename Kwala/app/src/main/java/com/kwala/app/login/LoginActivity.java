package com.kwala.app.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseActivity;
import com.kwala.app.main.MainActivity;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.auth.LoginTask;

/**
 * Created by sijaebrown on 1/24/17.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText emailEditText;
    private EditText passwordEditText;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailEditText = (EditText) findViewById(R.id.login_email);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        Button loginButton = (Button) findViewById(R.id.login_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                new LoginTask(email, password).start(new Task.Callback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = MainActivity.newIntent(LoginActivity.this);
                        startActivity(intent);
                        finishAffinity();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "onFailure", e);
                    }
                });
            }
        });
    }
}

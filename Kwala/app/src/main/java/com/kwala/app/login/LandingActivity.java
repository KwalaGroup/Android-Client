package com.kwala.app.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.helpers.BaseActivity;
import com.kwala.app.registration.RegistrationActivity;

/**
 * Created by sijaebrown on 2/18/17.
 */

public class LandingActivity extends BaseActivity {
    private static final String TAG = LandingActivity.class.getSimpleName();

    private TextView textView;
    private Button loginButton;

    public static Intent newIntent(Context context) {
        return new Intent(context, LandingActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);

        textView = (TextView) findViewById(R.id.landing_register_button);
        loginButton = (Button) findViewById(R.id.login_button);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegistrationActivity.newIntent(LandingActivity.this));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.newIntent(LandingActivity.this));
            }
        });

    }


}

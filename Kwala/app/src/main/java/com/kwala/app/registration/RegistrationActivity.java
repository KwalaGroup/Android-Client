package com.kwala.app.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kwala.app.R;
import com.kwala.app.helpers.BaseActivity;

/**
 * Created by sijaebrown on 1/24/17.
 */

public class RegistrationActivity extends BaseActivity {
    private static final String TAG = RegistrationActivity.class.getSimpleName();

    private Button button;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        button = (Button) findViewById(R.id.registration_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity2.class);
                startActivity(intent);
            }
        });
    }
}

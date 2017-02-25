package com.kwala.app.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kwala.app.R;

/**
 * Created by sijaebrown on 1/24/17.
 */

public class RegistrationActivity extends AppCompatActivity{

    private Button button;

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

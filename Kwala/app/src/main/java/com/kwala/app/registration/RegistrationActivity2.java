package com.kwala.app.registration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kwala.app.MainActivity;
import com.kwala.app.R;

/**
 * Created by sijaebrown on 2/4/17.
 */

public class RegistrationActivity2 extends AppCompatActivity {

    private Button finishRegButton;
    private ImageView maleIcon;
    private ImageView femaleIcon;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText ageEditText;

    private boolean maleIconClicked = false;
    private boolean femaleIconClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration2_activity);
        firstNameEditText = (EditText) findViewById(R.id.first_name_box);
        lastNameEditText = (EditText) findViewById(R.id.last_name_box);
        ageEditText = (EditText) findViewById(R.id.age_box);

        finishRegButton = (Button) findViewById(R.id.finish_reg_button);
        finishRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        maleIcon = (ImageView) findViewById(R.id.male_icon);
        maleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleIconClicked = true;
                highlight(maleIcon);
            }
        });

        femaleIcon = (ImageView) findViewById(R.id.female_icon);
        femaleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femaleIconClicked = true;
                highlight(femaleIcon);
            }
        });

    }

    private void highlight(ImageView image) {
        image.setColorFilter(Color.DKGRAY);
        if (image == maleIcon) {
            femaleIcon.setColorFilter(null);
        } else {
            maleIcon.setColorFilter(null);
        }
    }
}

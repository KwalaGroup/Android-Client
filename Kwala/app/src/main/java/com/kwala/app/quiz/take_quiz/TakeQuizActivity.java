package com.kwala.app.quiz.take_quiz;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kwala.app.R;
import com.kwala.app.helpers.BaseToolbarActivity;

/**
 * @author jacobamuchow@gmail.com
 */

public class TakeQuizActivity extends BaseToolbarActivity {
    private static final String TAG = TakeQuizActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        return new Intent(context, TakeQuizActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_quiz_activity);
        setTitle("Personality Quiz");

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.take_quiz_activity_content_layout) == null) {
            Fragment fragment = QuestionsFragment.newInstance();
            Log.d(TAG, "tag: " + fragment.getClass().getSimpleName());

            fm.beginTransaction()
                    .replace(R.id.take_quiz_activity_content_layout, fragment, fragment.getTag())
                    .commit();
        }
    }
}

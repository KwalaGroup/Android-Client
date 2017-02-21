package com.kwala.app.quiz;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kwala.app.R;
import com.kwala.app.service.tasks.QuestionsTask;
import com.kwala.app.service.tasks.Task;

import org.json.JSONObject;

/**
 * @author jacobamuchow@gmail.com
 */
public class QuizFragment extends Fragment {
    private static final String TAG = QuizFragment.class.getSimpleName();

    public static QuizFragment newInstance() {
        return new QuizFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button beginButton = (Button) view.findViewById(R.id.quiz_fragment_begin_button);

        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: start quiz
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        new QuestionsTask().start(new Task.Callback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d(TAG, "UI success: " + jsonObject.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "UI failure", e);
            }
        });
    }
}

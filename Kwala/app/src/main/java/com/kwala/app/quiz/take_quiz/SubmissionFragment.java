package com.kwala.app.quiz.take_quiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseFragment;

import java.util.HashMap;

/**
 * @author jacobamuchow@gmail.com
 */

public class SubmissionFragment extends BaseFragment {
    private static final String TAG = SubmissionFragment.class.getSimpleName();

    private static final String ANSWERS_MAP_KEY = "answers_map";

    public static SubmissionFragment newInstance(HashMap<String, String> answersMap) {
        SubmissionFragment fragment = new SubmissionFragment();
        Bundle args = new Bundle();

        args.putSerializable(ANSWERS_MAP_KEY, answersMap);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.take_quiz_submission_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        HashMap<String, String> answersMap = (HashMap<String, String>) getArguments().getSerializable(ANSWERS_MAP_KEY);
        Log.d(TAG, answersMap.toString());

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                submitQuiz();
            }
        }, 3000);
    }

    private void submitQuiz() {
        Log.d(TAG, "Submitting quiz...");
    }
}

package com.kwala.app.quiz.take_quiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kwala.app.R;
import com.kwala.app.helpers.navigation.BaseFragment;
import com.kwala.app.helpers.views.KwalaProgressSpinner;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.quizzes.SubmitQuizTask;

import java.util.HashMap;

/**
 * @author jacobamuchow@gmail.com
 */

public class SubmissionFragment extends BaseFragment {
    private static final String TAG = SubmissionFragment.class.getSimpleName();

    private static final String ANSWERS_MAP_KEY = "answers_map";

    private KwalaProgressSpinner progressSpinner;
    private TextView spinnerTextView;
    private TextView successTextView;

    private HashMap<String, String> answersMap;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressSpinner = (KwalaProgressSpinner) view.findViewById(R.id.submission_progress_spinner);
        spinnerTextView = (TextView) view.findViewById(R.id.submission_spinner_text);
        successTextView = (TextView) view.findViewById(R.id.submission_success_text);
    }

    @Override
    public void onResume() {
        super.onResume();

        answersMap = (HashMap<String, String>) getArguments().getSerializable(ANSWERS_MAP_KEY);
        Log.d(TAG, answersMap.toString());

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                submitQuiz();
            }
        }, 2000);
    }

    private void submitQuiz() {
        Log.d(TAG, "Submitting quiz...");

        new SubmitQuizTask(answersMap).start(new Task.Callback<Void, NetworkException>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressSpinner.setVisibility(View.GONE);
                spinnerTextView.setVisibility(View.GONE);
                successTextView.setVisibility(View.VISIBLE);

                performDelayedFinish();
            }

            @Override
            public void onFailure(NetworkException e) {
                Log.d(TAG, "Error submitting quiz", e);
                Toast.makeText(getActivity(), "There was a problem submitting your quiz", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void performDelayedFinish() {
        if (getView() != null) {
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().finish();
                }
            }, 2000);
        }
    }
}

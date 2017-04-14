package com.kwala.app.quiz;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kwala.app.R;
import com.kwala.app.quiz.take_quiz.TakeQuizActivity;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.tasks.quizzes.GetCurrentQuizTask;
import com.kwala.app.service.tasks.Task;

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

        //TODO: lock quiz in some cases

        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TakeQuizActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        new GetCurrentQuizTask().start(new Task.Callback<Void, NetworkException>() {
            @Override
            public void onSuccess(Void result) {

            }

            @Override
            public void onFailure(NetworkException e) {
                Log.d(TAG, "UI failure", e);
            }
        });
    }
}

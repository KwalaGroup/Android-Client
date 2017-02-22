package com.kwala.app.quiz.take_quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.helpers.BaseToolbarActivity;
import com.kwala.app.models.RQuizQuestion;
import com.kwala.app.models.generic.RString;
import com.kwala.app.service.realm.RealmQueries;

import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */

public class TakeQuizActivity extends BaseToolbarActivity {
    private static final String TAG = TakeQuizActivity.class.getSimpleName();

    private TextView questionTextView;
    private RecyclerView answerRecyclerView;

    private int questionIndex = 0;

    public static Intent newIntent(Context context) {
        return new Intent(context, TakeQuizActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_quiz_activity);
        setTitle("Personality Quiz");

        questionTextView = (TextView) findViewById(R.id.take_quiz_question_text);
        answerRecyclerView = (RecyclerView) findViewById(R.id.take_quiz_answer_recycler_view);

        final RealmResults<RQuizQuestion> questions = RealmQueries.withMainRealm().getQuizQuestions();

        setupQuestion(questions.get(questionIndex));
    }

    public void setupQuestion(final RQuizQuestion question) {

        questionTextView.setText(question.getQuestion());

        Log.d(TAG, "num answers: " + question.getAnswers().size());
        for (RString answer : question.getAnswers()) {
            Log.d(TAG, answer.getValue());
        }
        Log.d(TAG, " ");

        answerRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(new AnswerCell(parent.getContext())) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                AnswerCell answerCell = (AnswerCell) holder.itemView;
                answerCell.setViewData(question.getAnswers().get(position).getValue());
            }

            @Override
            public int getItemCount() {
                return question.getAnswers().size();
            }
        });
    }
}

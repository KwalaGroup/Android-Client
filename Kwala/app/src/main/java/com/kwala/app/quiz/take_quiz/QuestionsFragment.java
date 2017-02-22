package com.kwala.app.quiz.take_quiz;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.models.RQuizAnswer;
import com.kwala.app.models.RQuizQuestion;
import com.kwala.app.service.realm.RealmQueries;

import java.util.HashMap;

import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com
 */

public class QuestionsFragment extends Fragment {
    private static final String TAG = QuestionsFragment.class.getSimpleName();

    /*
        References
     */
    private TextView questionTextView;
    private RecyclerView answerRecyclerView;

    /*
        Data
     */
    private RealmResults<RQuizQuestion> questions;
    private int questionIndex = 0;

    private HashMap<String, String> answersMap;

    /*
        Constructors
     */
    public static QuestionsFragment newInstance() {
        return new QuestionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.take_quiz_questions_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionTextView = (TextView) view.findViewById(R.id.take_quiz_question_text);
        answerRecyclerView = (RecyclerView) view.findViewById(R.id.take_quiz_answer_recycler_view);

        questions = RealmQueries.withMainRealm().getQuizQuestions();
        answersMap = new HashMap<>(questions.size());

        setupQuestion(questions.get(questionIndex));
    }

    public void setupQuestion(final RQuizQuestion question) {
        Log.d(TAG, "setupQuestion " + question.getQuestionId() + ": " + question.getQuestion());

        questionTextView.setText(question.getQuestion());

        answerRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                AnswerCell answerCell = new AnswerCell(parent.getContext());
                return new RecyclerView.ViewHolder(answerCell) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final AnswerCell answerCell = (AnswerCell) holder.itemView;

                final RQuizAnswer answer = question.getAnswers().get(position);

                answerCell.setViewData(answer);

                answerCell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answerQuestion(question, answer);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return question.getAnswers().size();
            }
        });
    }

    public void answerQuestion(final RQuizQuestion quizQuestion, final RQuizAnswer answer) {
        answersMap.put(quizQuestion.getQuestionId(), answer.getAnswerId());

        if (++questionIndex < questions.size()) {
            setupQuestion(questions.get(questionIndex));
        } else {
            //TODO: submit
        }
    }
}

package com.kwala.app.quiz.take_quiz;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.models.RQuizQuestion;
import com.kwala.app.service.realm.RealmQueries;

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
    private int questionIndex = 0;

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

        final RealmResults<RQuizQuestion> questions = RealmQueries.withMainRealm().getQuizQuestions();

        setupQuestion(questions.get(questionIndex));
    }

    public void setupQuestion(final RQuizQuestion question) {

        questionTextView.setText(question.getQuestion());

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

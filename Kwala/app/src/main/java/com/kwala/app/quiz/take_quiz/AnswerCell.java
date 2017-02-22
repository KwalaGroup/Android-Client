package com.kwala.app.quiz.take_quiz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kwala.app.R;
import com.kwala.app.models.RQuizAnswer;

/**
 * @author jacobamuchow@gmail.com
 */

public class AnswerCell extends RelativeLayout {
    private static final String TAG = AnswerCell.class.getSimpleName();

    private Button answerButton;

    public AnswerCell(Context context) {
        super(context);
        initialize();
    }

    public AnswerCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public AnswerCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.quiz_answer_cell, this);

        answerButton = (Button) findViewById(R.id.quiz_answer_cell_answer_text);
    }

    public void setViewData(RQuizAnswer answer) {

        answerButton.setText(answer.getAnswer());
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (answerButton != null) {
            answerButton.setOnClickListener(l);
        }
    }
}

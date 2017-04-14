package com.kwala.app.matches.chat;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.models.RMessage;

/**
 * @author jacobamuchow@gmail.com
 */

public class LeftChatCell extends RelativeLayout {
    private static final String TAG = LeftChatCell.class.getSimpleName();

    private TextView messageTextView;

    public LeftChatCell(Context context) {
        super(context);
        initialize();
    }

    public LeftChatCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public LeftChatCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.left_chat_cell, this);

        messageTextView = (TextView) findViewById(R.id.left_chat_cell_text);
    }

    public void setViewData(@Nullable RMessage message) {
        if (message == null) {
            messageTextView.setText("");
            return;
        }

        messageTextView.setText(message.getText());
    }
}

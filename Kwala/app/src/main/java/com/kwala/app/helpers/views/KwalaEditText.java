package com.kwala.app.helpers.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.EditText;

import com.kwala.app.R;
import com.kwala.app.helpers.TextViewAttrHelper;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaEditText extends EditText {
    private static final String TAG = KwalaEditText.class.getSimpleName();

    public KwalaEditText(Context context) {
        super(context);
        initialize(null);
    }

    public KwalaEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public KwalaEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(@Nullable AttributeSet attrs) {
        TextViewAttrHelper.applyAttributes(this, attrs, R.styleable.KwalaEditText, R.styleable.KwalaEditText_font);

        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edit_text_background));
    }

    public void setTextAppend(@Nullable String text) {
        setText("");
        if (text != null) {
            append(text);
        }
    }

    public String getTextTrimmed() {
        return getText().toString().trim();
    }
}

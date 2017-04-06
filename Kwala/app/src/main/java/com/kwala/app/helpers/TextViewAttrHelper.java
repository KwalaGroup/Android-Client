package com.kwala.app.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kwala.app.enums.Font;

/**
 * @author jacobamuchow@gmail.com
 */

public class TextViewAttrHelper {
    private static final String TAG = TextViewAttrHelper.class.getSimpleName();

    public static void applyAttributes(TextView textView, @Nullable AttributeSet attrs, @StyleableRes int[] styleableIds, @StyleableRes int fontStyableId) {
        Context context = textView.getContext();

        Typeface typeface = Font.MEDIUM.getTypeface(context);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, styleableIds);

            int fontId = typedArray.getInt(fontStyableId, Font.MEDIUM.getId());
            typeface = Font.fromId(fontId).getTypeface(context);

            typedArray.recycle();
        }

        textView.setTypeface(typeface);
    }
}

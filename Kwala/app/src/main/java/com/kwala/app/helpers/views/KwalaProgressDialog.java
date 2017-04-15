package com.kwala.app.helpers.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ProgressBar;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaProgressDialog extends ProgressDialog {
    private static final String TAG = KwalaProgressDialog.class.getSimpleName();

    private KwalaProgressDialog(Context context, String message) {
        super(context);
        setIndeterminate(true);
        setMessage(message);
    }

    /**
     * Static methods that create, show, and return an HProgressDialog
     */
    public static KwalaProgressDialog show(Context context, @StringRes int messageId) {
        return show(context, context.getString(messageId));
    }

    public static KwalaProgressDialog show(Context context, String message) {
        KwalaProgressDialog progressDialog = new KwalaProgressDialog(context, message);
        progressDialog.show();
        return progressDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Tint the spinner the primary theme color
         */
        ProgressBar progressBar = (ProgressBar) findViewById(android.R.id.progress);

        int color = ContextCompat.getColor(getContext(), R.color.kOrange);
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        DrawableCompat.setTintList(progressBar.getIndeterminateDrawable(), colorStateList);
    }
}

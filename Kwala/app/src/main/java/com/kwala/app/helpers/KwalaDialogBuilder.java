package com.kwala.app.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */

public class KwalaDialogBuilder extends AlertDialog.Builder {
    private static final String TAG = KwalaDialogBuilder.class.getSimpleName();

    //We force this to be true by default because Samsungs have it set to false (WHY?!?!)
    private boolean canceledOnTouchOutside = true;

    public static KwalaDialogBuilder with(Context context) {
        return new KwalaDialogBuilder(context);
    }

    public KwalaDialogBuilder(Context context) {
        super(context);
    }

    public KwalaDialogBuilder(Context context, int themeResId) {
        super(context, themeResId);
    }

    public KwalaDialogBuilder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    @Override
    public AlertDialog create() {
        final AlertDialog dialog = super.create();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

                //Negative button color
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if(negativeButton != null) {
                    negativeButton.setTextColor(ContextCompat.getColor(getContext(), R.color.kDarkGray));
                }

                //Positive button color
                Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if(positiveButton != null) {
                    positiveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                }
            }
        });

        return dialog;
    }
}

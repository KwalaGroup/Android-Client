package com.kwala.app.helpers.views;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kwala.app.R;

/**
 * @author jacobamuchow@gmail.com
 */

public class ProgressImageLayout extends FrameLayout {
    private static final String TAG = ProgressImageLayout.class.getSimpleName();

    private ImageView imageView;
    private View progressLayout;

    public ProgressImageLayout(@NonNull Context context) {
        super(context);
        initialize();
    }

    public ProgressImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ProgressImageLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.progress_image_layout, this);

        imageView = (ImageView) findViewById(R.id.progress_image_layout_image);
        progressLayout = findViewById(R.id.progress_image_layout_progress_layout);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setProgressVisible(boolean visible) {
        progressLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void showProgress() {
        setProgressVisible(true);
    }

    public void hideProgress() {
        setProgressVisible(false);
    }
}

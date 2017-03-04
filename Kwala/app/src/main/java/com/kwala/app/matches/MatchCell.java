package com.kwala.app.matches;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.helpers.KwalaImages;
import com.kwala.app.models.RMatch;

import java.util.Locale;

/**
 * @author muchow@hello.com
 */
public class MatchCell extends RelativeLayout {
    private static final String TAG = MatchCell.class.getSimpleName();

    /*
        References
     */
    private ImageView profileImageView;
    private TextView nameTextView;
    private TextView scoreTextView;
    private ImageView filterImageView;
    private TextView ageTextView;

    /*
        Constructors
     */
    public MatchCell(Context context) {
        super(context);
        initialize();
    }

    public MatchCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MatchCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.match_cell, this);

        profileImageView = (ImageView) findViewById(R.id.match_cell_profile_image);
        nameTextView = (TextView) findViewById(R.id.match_cell_name_text);
        scoreTextView = (TextView) findViewById(R.id.match_cell_score_text);
        filterImageView = (ImageView) findViewById(R.id.match_cell_filter_image);
        ageTextView = (TextView) findViewById(R.id.match_cell_age_text);
    }

    public void setViewData(@Nullable RMatch match) {
        if (match == null) {
            profileImageView.setImageDrawable(null);
            filterImageView.setImageDrawable(null);

            nameTextView.setText("");
            scoreTextView.setText("");
            ageTextView.setText("");
            return;
        }

        KwalaImages.with(profileImageView).setProfileImageId(match.getProfileImageId());

        filterImageView.setImageResource(match.getFilterCategory().getIconId());

        nameTextView.setText(match.getFullName());
        scoreTextView.setText(String.format(Locale.US, "%d%% match", match.getScore().intValue()));
        ageTextView.setText("age: 22");
    }
}

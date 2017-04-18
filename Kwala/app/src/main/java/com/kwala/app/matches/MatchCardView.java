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
 * @author jacobamuchow@gmail.com
 */

public class MatchCardView extends RelativeLayout {
    private static final String TAG = MatchCardView.class.getSimpleName();

    /*
        References
     */
    private ImageView profileImageView;
    private TextView nameTextView;
    private TextView ageTextView;

    /*
        Data
     */
    @Nullable private String matchId;

    /*
        Constructors
     */
    public MatchCardView(Context context) {
        super(context);
        initialize();
    }

    public MatchCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MatchCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.match_card_view, this);

        /*
         * Get view references
         */
        profileImageView = (ImageView) findViewById(R.id.match_card_view_profile_image);
        nameTextView = (TextView) findViewById(R.id.match_card_view_name_text);
        ageTextView = (TextView) findViewById(R.id.match_card_view_age_text);
    }

    public void setViewData(@Nullable RMatch match) {
        if (match == null) {
            matchId = null;
            profileImageView.setImageDrawable(null);
//            filterImageView.setImageDrawable(null);

            nameTextView.setText("");
            ageTextView.setText("");
//            scoreTextView.setText("");
            return;
        }

        matchId = match.getMatchId();
        KwalaImages.with(profileImageView).setProfileImageId(match.getProfileImageId());

//        ArrayList<Filter> filters = match.getFilters();
//        if (filters.size() > 0) {
//            filterImageView.setImageResource(filters.get(0).getIconId());
//        } else {
//            filterImageView.setImageBitmap(null);
//        }

        nameTextView.setText(match.getFullName());
        ageTextView.setText(String.format(Locale.US, "Age: %d", match.getAge()));
//        scoreTextView.setText(String.format(Locale.US, "%d%% match", match.getScore().intValue()));

//        MatchState matchState = match.getMatchState();
//        if (matchState == MatchState.SUCCESS) {
//            chatImageView.setVisibility(VISIBLE);
//            heartImageView.setVisibility(GONE);
//        } else {
//            chatImageView.setVisibility(GONE);
//            heartImageView.setVisibility(VISIBLE);
//
//            boolean hearted = matchState == MatchState.ACCEPT_SENT;
//            heartImageView.setImageResource(hearted ? R.drawable.heart_fill : R.drawable.heart_outline);
//            heartImageView.setEnabled(!hearted);
//        }
    }
}

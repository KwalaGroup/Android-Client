package com.kwala.app.matches.match_cards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.enums.Filter;
import com.kwala.app.enums.MatchState;
import com.kwala.app.helpers.KwalaImages;
import com.kwala.app.models.RMatch;

import java.util.ArrayList;
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
    private ImageView filterImageView;
    private TextView bioTextView;
    private TextView scoreTextView;

    private Button rejectButton;
    private Button acceptButton;
    private ImageButton chatButton;

    /*
        Data
     */
    @Nullable private String matchId;

    /*
        Listener
     */
    public interface Listener {
        void onRejectClicked(MatchCardView matchCardView);
        void onAcceptClicked(MatchCardView matchCardView);
        void onChatClicked(MatchCardView matchCardView);
    }

    @Nullable private Listener listener;

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
        filterImageView = (ImageView) findViewById(R.id.match_card_view_filter_image);
        bioTextView = (TextView) findViewById(R.id.match_card_view_bio_text);
        scoreTextView = (TextView) findViewById(R.id.match_card_view_score_text);

        rejectButton = (Button) findViewById(R.id.match_card_view_reject_button);
        acceptButton = (Button) findViewById(R.id.match_card_view_accept_button);
        chatButton = (ImageButton) findViewById(R.id.match_card_view_chat_button);

        rejectButton.setOnClickListener(rejectClickListener);
        acceptButton.setOnClickListener(acceptClickListener);
        chatButton.setOnClickListener(chatClickListener);
    }

    public void setListener(@Nullable Listener listener) {
        this.listener = listener;
    }

    @Nullable
    public String getMatchId() {
        return matchId;
    }

    public void setViewData(@Nullable RMatch match) {
        if (match == null) {
            matchId = null;
            profileImageView.setImageDrawable(null);
            filterImageView.setImageDrawable(null);

            nameTextView.setText("");
            ageTextView.setText("");
            bioTextView.setText("");
            scoreTextView.setText("");
            return;
        }

        matchId = match.getMatchId();

        KwalaImages.with(profileImageView).setProfileImageId(match.getProfileImageId(), match.getProfileColorAsInt());

        ArrayList<Filter> filters = match.getFilters();
        if (filters.size() > 0) {
            filterImageView.setImageResource(filters.get(0).getIconId());
        } else {
            filterImageView.setImageBitmap(null);
        }

        nameTextView.setText(match.getFullName());
        ageTextView.setText(String.format(Locale.US, "Age: %d", match.getAge()));
        bioTextView.setText(match.getBio());
        scoreTextView.setText(String.format(Locale.US, "%d%% match", match.getScore().intValue()));

        MatchState matchState = match.getMatchState();
        if (matchState == MatchState.SUCCESS) {
            chatButton.setVisibility(VISIBLE);
            acceptButton.setVisibility(GONE);
        } else {
            chatButton.setVisibility(GONE);
            acceptButton.setVisibility(VISIBLE);

            boolean acceptEnabled = matchState == MatchState.NEW;
            acceptButton.setEnabled(acceptEnabled);
            acceptButton.setAlpha(acceptEnabled ? 1.0f : 0.4f);
        }
    }

    /**
     * Listeners
     */
    private final OnClickListener rejectClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onRejectClicked(MatchCardView.this);
            }
        }
    };

    private final OnClickListener acceptClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onAcceptClicked(MatchCardView.this);
            }
        }
    };

    private final OnClickListener chatClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onChatClicked(MatchCardView.this);
            }
        }
    };
}

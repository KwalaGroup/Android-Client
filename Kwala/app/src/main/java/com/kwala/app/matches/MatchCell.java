package com.kwala.app.matches;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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

    private ImageView chatImageView;
    private ImageView heartImageView;

    /*
        Data
     */
    @Nullable private String matchId;

    /*
        Listener
     */
    public interface Listener {
        void onClick(MatchCell matchCell);
        boolean onLongClick(MatchCell matchCell);  //Return true if handled
        void onChatClicked(MatchCell matchCell);
        void onHeartClicked(MatchCell matchCell);
    }

    @Nullable private Listener listener;

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

        /*
         * Get view references
         */
        profileImageView = (ImageView) findViewById(R.id.match_cell_profile_image);
        nameTextView = (TextView) findViewById(R.id.match_cell_name_text);
        scoreTextView = (TextView) findViewById(R.id.match_cell_score_text);
        filterImageView = (ImageView) findViewById(R.id.match_cell_filter_image);

        chatImageView = (ImageView) findViewById(R.id.match_cell_chat_bubble_image);
        heartImageView = (ImageView) findViewById(R.id.match_cell_heart_image);

        /*
         * Set listeners
         */
        setOnClickListener(onClickListener);
        setOnLongClickListener(longClickListener);
        chatImageView.setOnClickListener(chatClickListener);
        heartImageView.setOnClickListener(heartClickListener);
    }

    @Nullable
    public String getMatchId() {
        return matchId;
    }

    public void setListener(@Nullable Listener listener) {
        this.listener = listener;
    }

    public void setViewData(@Nullable RMatch match) {
        if (match == null) {
            matchId = null;
            profileImageView.setImageDrawable(null);
            filterImageView.setImageDrawable(null);

            nameTextView.setText("");
            scoreTextView.setText("");
            return;
        }

        matchId = match.getMatchId();
        KwalaImages.with(profileImageView).setProfileImageId(match.getProfileImageId());

        ArrayList<Filter> filters = match.getFilters();
        if (filters.size() > 0) {
            filterImageView.setImageResource(filters.get(0).getIconId());
        } else {
            filterImageView.setImageBitmap(null);
        }

        nameTextView.setText(match.getFullName());
        scoreTextView.setText(String.format(Locale.US, "%d%% match", match.getScore().intValue()));

        MatchState matchState = match.getMatchState();
        if (matchState == MatchState.SUCCESS) {
            chatImageView.setVisibility(VISIBLE);
            heartImageView.setVisibility(GONE);
        } else {
            chatImageView.setVisibility(GONE);
            heartImageView.setVisibility(VISIBLE);

            boolean hearted = matchState == MatchState.ACCEPT_SENT;
            heartImageView.setImageResource(hearted ? R.drawable.heart_fill : R.drawable.heart_outline);
            heartImageView.setEnabled(!hearted);
        }
    }

    /**
     * Listeners
     */
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onClick(MatchCell.this);
            }
        }
    };

    private OnLongClickListener longClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (listener == null) {
                return false;
            }
            return listener.onLongClick(MatchCell.this);
        }
    };

    private OnClickListener chatClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onChatClicked(MatchCell.this);
            }
        }
    };

    private OnClickListener heartClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onHeartClicked(MatchCell.this);
            }
        }
    };
}

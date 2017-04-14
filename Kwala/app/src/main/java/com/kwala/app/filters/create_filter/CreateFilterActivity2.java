package com.kwala.app.filters.create_filter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kwala.app.R;
import com.kwala.app.enums.FilterCategory;
import com.kwala.app.helpers.navigation.BaseActivity;

import java.util.ArrayList;

/**
 * Created by sijaebrown on 3/4/17.
 */

public class CreateFilterActivity2 extends BaseActivity {
    private static final String TAG = CreateFilterActivity2.class.getSimpleName();

    private static final String FILTER_CATEGORY_KEY = "filter_category";
    private ArrayList<String> listOfInterests = new ArrayList<>();

    private RecyclerView interestsRecyclerView;
    private boolean maleRadioButtonChecked;
    private boolean femaleRadioButtonChecked;
    private boolean permanentButtonChecked;
    private boolean oneHourButtonChecked;
    private ImageView image;
    private TextView filterTitle;

    public static Intent newIntent(Context context, FilterCategory filterCategory) {
        Intent intent = new Intent(context, CreateFilterActivity2.class);

        intent.putExtra(FILTER_CATEGORY_KEY, filterCategory.getNetworkString());

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_filter_activity_2);
        setTitle("Create Filter");

        String filterValue = getIntent().getStringExtra(FILTER_CATEGORY_KEY);
        FilterCategory filterCategory = FilterCategory.fromNetworkString(filterValue);

        Log.d(TAG, filterCategory.name());

        permanentButtonChecked = false;
        oneHourButtonChecked = false;
        maleRadioButtonChecked = false;
        femaleRadioButtonChecked = false;

        image = (ImageView) findViewById(R.id.filter_activity_2_image);
        image.setBackgroundResource(filterCategory.getIconId());

        filterTitle = (TextView) findViewById(R.id.filter_activity_2_title);
        filterTitle.setText(filterCategory.getDisplayString());

        interestsRecyclerView = (RecyclerView) findViewById(R.id.create_filter_2_recycler_view);

        final String[] interestList = filterCategory.getInterests();
        interestsRecyclerView.setAdapter(new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                FilterInterestCell filterInterestCell = new FilterInterestCell(parent.getContext());
                return new RecyclerView.ViewHolder(filterInterestCell) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final FilterInterestCell filterInterestCell = (FilterInterestCell) holder.itemView;
                final String interest = interestList[position];

                filterInterestCell.setViewData(interest);
                filterInterestCell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Click");
                        if (!listOfInterests.contains(interest)) {
                            filterInterestCell.setSelected(true);
                            v.setBackgroundColor(Color.LTGRAY);
                            listOfInterests.add(interest);
                        } else {
                            filterInterestCell.setSelected(false);
                            v.setBackgroundColor(Color.TRANSPARENT);
                            listOfInterests.remove(interest);
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return interestList.length;
            }
        });
    }

    public void onFilter2RadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.filter_activity_2_permanent_radio:
                permanentButtonChecked = toggleBool(checked);
                break;
            case R.id.filter_activity_2_time_limit_radio:
                oneHourButtonChecked = toggleBool(checked);
                break;
            case R.id.filter_activity_2_male_radiobutton:
                maleRadioButtonChecked = toggleBool(checked);
                break;
            case R.id.filter_activity_2_female_radiobutton:
                femaleRadioButtonChecked = toggleBool(checked);
                break;
            default:
                Log.d(TAG, "Random radio button click. Not sure what happened??");
                break;
        }
    }

    private boolean toggleBool(boolean radioButtonValue) {
        if (radioButtonValue == true) {
            return false;
        } else {
            return true;
        }
    }
}

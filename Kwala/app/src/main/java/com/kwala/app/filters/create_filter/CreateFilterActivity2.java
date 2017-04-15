package com.kwala.app.filters.create_filter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kwala.app.R;
import com.kwala.app.enums.Filter;
import com.kwala.app.enums.Gender;
import com.kwala.app.enums.Interest;
import com.kwala.app.helpers.navigation.BaseToolbarActivity;
import com.kwala.app.helpers.views.KwalaProgressDialog;
import com.kwala.app.service.endpoints.NetworkException;
import com.kwala.app.service.tasks.Task;
import com.kwala.app.service.tasks.filters.CreateFilterTask;

import java.util.ArrayList;

/**
 * Created by sijaebrown on 3/4/17.
 */

public class CreateFilterActivity2 extends BaseToolbarActivity {
    private static final String TAG = CreateFilterActivity2.class.getSimpleName();

    private static final String FILTER_CATEGORY_KEY = "filter_category";

    /*
        References
     */
    private ImageView iconImageView;
    private TextView titleTextView;

    private RadioButton permanentRadioButton;
    private RadioButton oneHourRadioButton;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;

    private RecyclerView interestsRecyclerView;

    private ProgressDialog progressDialog;

    /*
        Data
     */
    private Filter filter;
    private Interest[] interests;
    private ArrayList<Interest> selectedInterests = new ArrayList<>();

    /*
        Constructors
     */
    public static Intent newIntent(Context context, Filter filter) {
        Intent intent = new Intent(context, CreateFilterActivity2.class);

        intent.putExtra(FILTER_CATEGORY_KEY, filter.getNetworkString());

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_filter_activity_2);
        setTitle("Create Filter");

        String filterValue = getIntent().getStringExtra(FILTER_CATEGORY_KEY);
        filter = Filter.fromNetworkString(filterValue);
        interests = filter.getInterests();

        /*
         * Get view references
         */
        iconImageView = (ImageView) findViewById(R.id.filter_activity_2_image);
        titleTextView = (TextView) findViewById(R.id.filter_activity_2_title);

        permanentRadioButton = (RadioButton) findViewById(R.id.filter_activity_2_permanent_radio);
        oneHourRadioButton = (RadioButton) findViewById(R.id.filter_activity_2_time_limit_radio);
        maleRadioButton = (RadioButton) findViewById(R.id.filter_activity_2_male_radiobutton);
        femaleRadioButton = (RadioButton) findViewById(R.id.filter_activity_2_female_radiobutton);

        interestsRecyclerView = (RecyclerView) findViewById(R.id.create_filter_2_recycler_view);

        /*
         * Set view data
         */
        iconImageView.setBackgroundResource(filter.getIconId());
        titleTextView.setText(filter.getDisplayString());

        interestsRecyclerView.setAdapter(createAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkmark_menu, menu);

        //Set enabled state of done button if form is complete
        MenuItem menuItem = menu.findItem(R.id.action_done);
        if (menuItem != null) {
            boolean enabled = isFormComplete();

            menuItem.setEnabled(enabled);
            menuItem.getIcon().setAlpha(enabled ? 255 : 102);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                createFilter();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFormComplete() {
        return (permanentRadioButton.isChecked() || oneHourRadioButton.isChecked())
                && (maleRadioButton.isChecked() || femaleRadioButton.isChecked());
    }

    private void createFilter() {
        if (!isFormComplete()) {
            return;
        }

        Gender gender = maleRadioButton.isChecked() ? Gender.MALE : Gender.FEMALE;

        //TODO: filter time, interests

        progressDialog = KwalaProgressDialog.show(this, "Creating filter...");
        new CreateFilterTask(filter, gender).start(new Task.Callback<Void, NetworkException>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(NetworkException e) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Log.e(TAG, "Error creating filter", e);
                Toast.makeText(getBaseActivity(), "Error creating filter", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onFilter2RadioButtonClicked(View view) {
        invalidateOptionsMenu();
    }

    private RecyclerView.Adapter createAdapter() {
        return new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                FilterInterestCell filterInterestCell = new FilterInterestCell(parent.getContext());
                return new RecyclerView.ViewHolder(filterInterestCell) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final FilterInterestCell filterInterestCell = (FilterInterestCell) holder.itemView;
                final Interest interest = interests[position];

                filterInterestCell.setViewData(interest);
                filterInterestCell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (v.isSelected()) {
                            filterInterestCell.setSelected(false);
                            selectedInterests.remove(interest);
                        } else {
                            filterInterestCell.setSelected(true);
                            selectedInterests.add(interest);
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return interests.length;
            }
        };
    }
}

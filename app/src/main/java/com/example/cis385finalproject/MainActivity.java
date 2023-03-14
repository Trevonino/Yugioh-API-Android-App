package com.example.cis385finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    public static final int SEARCH_REQUEST = 1;
    private static final String RECENT_SEARCH_TEXT = "";

    private TextView mPreviousSearch;
    private TextView mPreviousSearchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreviousSearch = (TextView)findViewById(R.id.previousSearchText);
        mPreviousSearchInfo = (TextView)findViewById(R.id.previousSearchInfo);

        if (savedInstanceState != null) {
            if (!savedInstanceState.getString(RECENT_SEARCH_TEXT).equals("")) {
                mPreviousSearchInfo.setVisibility(View.VISIBLE);

                mPreviousSearch.setText(savedInstanceState.getString(RECENT_SEARCH_TEXT));
                mPreviousSearch.setVisibility(View.VISIBLE);
            }
        }
    }

    public void startRandomCard(View view) {
        Intent intent = new Intent(this, RandomCard.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Change the label of the menu based on the state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else{
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Check if the correct item was clicked
        if(item.getItemId()==R.id.night_mode){
            // Get the night mode state of the app.
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            //Set the theme mode for the restarted activity
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_YES);
            }
// Recreate the activity for the theme change to take effect.
            recreate();
        }
        return true;
    }

    public void startSearchCard(View view) {
        Intent intent = new Intent(this, SearchCard.class);
        startActivityForResult(intent, SEARCH_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_REQUEST) {
            if (resultCode == RESULT_OK) {
                String search = data.getStringExtra(SearchCard.EXTRA_PREVIOUS_SEARCH);
                mPreviousSearchInfo.setVisibility(View.VISIBLE);

                mPreviousSearch.setText(search);
                mPreviousSearch.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        String recentSearchText = mPreviousSearch.getText().toString();
        super.onSaveInstanceState(outState);
        outState.putString(RECENT_SEARCH_TEXT, recentSearchText);
    }
}
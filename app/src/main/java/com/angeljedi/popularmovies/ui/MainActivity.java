package com.angeljedi.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.angeljedi.popularmovies.R;
import com.angeljedi.popularmovies.domain.Movie;
import com.angeljedi.popularmovies.util.Utility;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback{

    private static final String DETAILSFRAGMENT_TAG = "DFTAG";

    private boolean mTwoPaneLayout;
    private String mSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSortOrder = Utility.getPreferredSort(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            // two pane layout is present only in large-screen layouts and possibly landscape (layout/sw-600dp, layout/landscape)
            mTwoPaneLayout = true;
            // add the detail view to the activity when it is a two pane layout.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new DetailsFragment(), DETAILSFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPaneLayout = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String savedSortOrder = Utility.getPreferredSort(this);
        if (savedSortOrder != null && !savedSortOrder.equals(mSortOrder)) {
            MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
            if (fragment != null) {
                fragment.onSortChanged();
            }
        }
        mSortOrder = savedSortOrder;
    }

    @Override
    public void onItemSelected(Movie movie) {
        if (mTwoPaneLayout) {
            Bundle args = new Bundle();
            args.putParcelable(DetailsFragment.EXTRA_MOVIE, movie);
            DetailsFragment fragment = new DetailsFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment, DETAILSFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsFragment.EXTRA_MOVIE, movie);
            startActivity(intent);
        }
    }
}

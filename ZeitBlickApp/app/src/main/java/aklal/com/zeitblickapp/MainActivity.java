package aklal.com.zeitblickapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import aklal.com.zeitblickapp.view.DisplayResultsFragment;
import aklal.com.zeitblickapp.view.IntroFragment;
import aklal.com.zeitblickapp.view.util.ConstantTag;
import aklal.com.zeitblickapp.view.util.OnAnalysisLaunchedListener;

public class MainActivity extends AppCompatActivity
        implements OnAnalysisLaunchedListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Display first fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, IntroFragment.newInstance())
                .commit();
    }

    @Override
    public void onAnalysisLaunched(Uri uri) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,
                        DisplayResultsFragment.newInstance(uri),
                        ConstantTag.DISPLAY_RESULTS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }


    /**
     * To manage Up Navigation click
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

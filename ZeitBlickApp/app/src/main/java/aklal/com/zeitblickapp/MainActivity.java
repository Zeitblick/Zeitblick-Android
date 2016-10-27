package aklal.com.zeitblickapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import aklal.com.zeitblickapp.view.DisplayResultsFragment;
import aklal.com.zeitblickapp.view.IntroFragment;
import aklal.com.zeitblickapp.view.TakePhotoFragment;
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
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, IntroFragment.newInstance()).commit();
//                .add(R.id.fragment_container, TakePhotoFragment.newInstance()).commit();
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
}

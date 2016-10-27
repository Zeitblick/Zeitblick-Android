package aklal.com.zeitblickapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import aklal.com.zeitblickapp.MainActivity;
import aklal.com.zeitblickapp.R;
import aklal.com.zeitblickapp.presenter.AnalysisPresenter;
import aklal.com.zeitblickapp.presenter.PresenterViewContract;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by oliver on 10.10.16.
 */
public class DisplayResultsFragment extends Fragment implements PresenterViewContract.View {

    private static final String TAG = DisplayResultsFragment.class.getSimpleName();

    private static final String URI_TO_SELFIE = "uri_to_selfie";

    @BindView(R.id.iv_matching_mkg_photo)
    ImageView ivMatchingMkgPhoto;

    Context mContext;

    // used to display taken selfie in a circular view
    private CircleImageView mIvSubmittedPhoto;

    // Animation played during download process
    private AnimationDrawable mWaitingAnimation;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;


    private Unbinder unbinder;

    private Uri mSelfieUri;
    private MainActivity mActivity;


    private PresenterViewContract.Operations mAnalysisPresenter;

    public static DisplayResultsFragment newInstance(Uri selfieUri) {

        //todo: Olivier: line to delete
        Log.d(TAG, "newInstance");

        Bundle args = new Bundle();
        args.putString(URI_TO_SELFIE, selfieUri.toString());

        DisplayResultsFragment fragment = new DisplayResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSelfieUri = Uri.parse(getArguments().getString(URI_TO_SELFIE));

        //Note: n'est il pas possible d'injecter le presenter afin de pvoir l'interchanger facilement ?
        mAnalysisPresenter = new AnalysisPresenter(this, mSelfieUri, mActivity);

        // call presenter
        mAnalysisPresenter.analysePhoto();

        //todo: Olivier: line to delete
        Log.d(TAG, "onCreate - Uri: " + mSelfieUri);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.used_present_result_fragment_new_design, container, false);
        unbinder = ButterKnife.bind(this, view);

        mIvSubmittedPhoto = (CircleImageView) view.findViewById(R.id.iv_thumbnail_taken_photo);

        //Set taken photo in a circle view
        mIvSubmittedPhoto.setImageURI(mSelfieUri);

        // anime une sequence d'images, ok mais pas configurable
        mWaitingAnimation = (AnimationDrawable) ivMatchingMkgPhoto.getBackground();
        mWaitingAnimation.start();

        mProgressBar.bringToFront();
        mProgressBar.setVisibility(ProgressBar.VISIBLE);

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void displayProgress(boolean active) {
        //info code pris de Udacity Material Design
        //TODO: 10.10.16 create correct looping animation
/*        AnimatedVectorDrawable drawable = full ? emptyHeart : fillHeart;
        ivMatchingMkgPhoto.setImageDrawable(drawable);
        drawable.start();*/
    }



    @Override
    public void displayTextResult(String results) {
    }



    @Override
    public void displaySimilarPhoto(Uri uri) {
        Log.i(TAG, "displaySimilarPhoto: URI TO DISPLAY: " + uri);
        ivMatchingMkgPhoto.setImageURI(uri);
    }

    @Override
    public void displaySimilarPhoto(String name) {

        Log.i(TAG, "displaySimilarPhoto: name= " + name);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        //todo hard coded String
        String imageURI = "https://storage.googleapis.com/projektlisa_test/" + name + ".jpg";

        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.image_placeholder) // resource or drawable
//                .showImageForEmptyUri(R.drawable.on_empty_url) // resource or drawable
//                .showImageOnFail(R.drawable.on_fail) // resource or drawable
                .delayBeforeLoading(1000)
                .resetViewBeforeLoading(true)  // default
                .cacheInMemory(true) // default => false
                .cacheOnDisk(true) // default => false
                .build();

        imageLoader.displayImage(imageURI, ivMatchingMkgPhoto, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.i(TAG, "onLoadingStarted: Loading Started");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.i(TAG, "onLoadingStarted: Loading Failed");


                mProgressBar.setVisibility(ProgressBar.INVISIBLE);

                //Stop the animation to be able to display image result
                mWaitingAnimation.stop();

                ivMatchingMkgPhoto.setImageDrawable(getResources().getDrawable(R.drawable.error));

                Toast.makeText(mContext, "Something blöd happened, try again", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.i(TAG, "onLoadingStarted: Loading Complete");

                mProgressBar.setVisibility(ProgressBar.INVISIBLE);

                //Stop the animation to be able to display image result
                mWaitingAnimation.stop();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.i(TAG, "onLoadingStarted: Loading Cancelled");
            }
        });

        //info: to let the image be full screen
        //http://stackoverflow.com/questions/24463691/how-to-show-imageview-full-screen-on-imageview-click
        ivMatchingMkgPhoto.setLayoutParams(
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
        ivMatchingMkgPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void displayError() {
        Log.i(TAG, "onLoadingStarted: Loading Failed");

        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        //Stop the animation to be able to display image result
        mWaitingAnimation.stop();

        ivMatchingMkgPhoto.setImageDrawable(getResources().getDrawable(R.drawable.error));
        ivMatchingMkgPhoto.setLayoutParams(
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
        ivMatchingMkgPhoto.setScaleType(ImageView.ScaleType.FIT_XY);

        Toast.makeText(mContext, "Something blöd happened, try again", Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayPrevPhoto() {

    }


    @OnClick(R.id.btt_options)
    public void onOptionsClicked(){
/*        Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // layout to display
        dialog.setContentView(R.layout.option_dialog);

        // set color transpartent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        dialog.show();*/
    }
}

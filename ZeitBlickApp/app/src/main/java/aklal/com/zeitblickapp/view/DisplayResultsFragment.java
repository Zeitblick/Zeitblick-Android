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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;

import aklal.com.zeitblickapp.MainActivity;
import aklal.com.zeitblickapp.R;
import aklal.com.zeitblickapp.presenter.AnalysisPresenter;
import aklal.com.zeitblickapp.presenter.PresenterViewContract;
import aklal.com.zeitblickapp.view.util.CustomHorizontalScrollView;
import aklal.com.zeitblickapp.webdata.models.matching_image.MatchingImage;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static aklal.com.zeitblickapp.R.drawable.error_image_v2;

/**
 * Created by Aklal on 10.10.16.
 */
public class DisplayResultsFragment
        extends Fragment
        implements PresenterViewContract.View {

    private static final String TAG = DisplayResultsFragment.class.getSimpleName();

    private static final String URI_TO_SELFIE = "uri_to_selfie";

    @BindView(R.id.iv_matching_mkg_photo)
    ImageView ivMatchingMkgPhoto;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.btt_options)
    ImageButton mBttOptions;

    @BindView(R.id.btt_information)
    ImageButton mBttInformation;

    @BindView(R.id.hsv_matching_mkg_photo)
    CustomHorizontalScrollView mHsvMatchingPhoto;

    private boolean isEverythingOk;

    private Context mContext;

    private MatchingImage mMatchingImage;

    // Used to display taken selfie in a circular view
    private CircleImageView mIvSubmittedPhoto;

    // Animation played during download process
    private AnimationDrawable mWaitingAnimation;

    private Unbinder unbinder;

    private MainActivity mActivity;

    private byte[] mBitmapByteArray;
    private Uri mSelfieUri;
    private String mSelfieStringUri;

    private PresenterViewContract.Operations mAnalysisPresenter;

    public static DisplayResultsFragment newInstance(Uri selfieUri) {
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
        mSelfieStringUri = getArguments().getString(URI_TO_SELFIE);
        mSelfieUri = Uri.parse(mSelfieStringUri);

        mAnalysisPresenter = new AnalysisPresenter(this, mSelfieUri, mActivity);
        mAnalysisPresenter.analysePhoto();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.display_result_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        mIvSubmittedPhoto = (CircleImageView) view.findViewById(R.id.iv_thumbnail_taken_photo);

        // Buttons are unclickable until an Image is displayed
        mBttOptions.setClickable(false);
        mBttInformation.setClickable(false);

        // Set taken photo in a circle view
        mIvSubmittedPhoto.setImageURI(mSelfieUri);

        // Image coming from Front Camera has to be flipped
        mIvSubmittedPhoto.setRotationY(180);

        // Animate a sequence of image
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
    public void displaySimilarPhoto(Uri uri) {
        ivMatchingMkgPhoto.setImageURI(uri);
    }

    @Override
    public void displaySimilarPhoto(String name) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        //todo hard coded String
        String imageToDownloadURI = "https://storage.googleapis.com/projektlisa_test/" + name + ".jpg";

        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.image_placeholder) // resource or drawable
//                .showImageForEmptyUri(R.drawable.on_empty_url) // resource or drawable
//                .showImageOnFail(R.drawable.on_fail) // resource or drawable
                .delayBeforeLoading(1000)
                .resetViewBeforeLoading(true)  // default
                .cacheInMemory(true) // default => false
                .cacheOnDisk(true) // default => false
                .build();

        imageLoader.displayImage(imageToDownloadURI, ivMatchingMkgPhoto, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.i(TAG, "onLoadingStarted: Loading Started");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);

                // Stop the animation to be able to display image result
                mWaitingAnimation.stop();

                // Display error image
                displayError();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);

                //Set buttons clickable
                mBttInformation.setClickable(true);
                mBttOptions.setClickable(true);

                //Stop the animation to be able to display image result
                mWaitingAnimation.stop();

                isEverythingOk = true;

                mBitmapByteArray = convertBitmapToByteArray(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.i(TAG, "onLoadingStarted: Loading Cancelled");
            }
        });

        ivMatchingMkgPhoto.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
        ivMatchingMkgPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }


    @Override
    public void displayError() {
        isEverythingOk = false;

        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        //Stop the animation
        mWaitingAnimation.stop();

        // Disable scrolling on our custom horizontalscrollview
        mHsvMatchingPhoto.setScrollingEnabled(false);

        ivMatchingMkgPhoto.setImageDrawable(getResources().getDrawable(error_image_v2));
        ivMatchingMkgPhoto.setScaleType(ImageView.ScaleType.MATRIX);//image is quasi perfect (dimension and position)!!! but container is too big and one can scrolling  (outside of the image there is background)

        // Animate option button to rotate to 45° and become a close button
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.option_to_close_anim);
        anim.setDuration(400);
        anim.setFillAfter(true);
        mBttOptions.startAnimation(anim);//button keeps its final state
        mBttOptions.setClickable(true);


        // Animate information button to let it disappear
        Animation animAlpha = new AlphaAnimation(1.0f, 0.0f);
        animAlpha.setDuration(1000);
        animAlpha.setFillAfter(true);
        mBttInformation.startAnimation(animAlpha);
        mBttInformation.setClickable(false);

        Toast.makeText(mContext,
                getResources().getString(R.string.message_error), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void retrieveMatchingImage(MatchingImage image) {
        mMatchingImage = image;

        displaySimilarPhoto(image.getInventoryNo());
    }

    /**
     * Displays transparent fragment with all options when option button is clicked
     **/
    @OnClick(R.id.btt_options)
    public void onOptionsClicked(){
        if (isEverythingOk) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container,
                            OptionsTransparentFragment.newInstance(mSelfieStringUri, mBitmapByteArray))
                    .commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container,
                            TakePhotoFragment.newInstance())
                    .commit();
        }
    }

    @OnClick(R.id.btt_information)
    public void onInfoClicked(){
        if (null != mBitmapByteArray) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container,
                            InformationFragment.newInstance(mMatchingImage, mBitmapByteArray))
                    .addToBackStack(null)
                    .commit();
        } else{
            displayError();
        }

    }


    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        if (null != bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            return byteArray;
        } else {
            Log.e(TAG, "convertUriImageToByteArray: bitmap is null");
        }
        return null;
    }

}
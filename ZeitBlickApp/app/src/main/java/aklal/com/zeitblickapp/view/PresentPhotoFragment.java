package aklal.com.zeitblickapp.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import aklal.com.zeitblickapp.MainActivity;
import aklal.com.zeitblickapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Aklal on 08.10.16.
 */

public class PresentPhotoFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private Unbinder unbinder;

    @BindView(R.id.btt_los_gehts)
    Button mBttLosGehts;

    @BindView(R.id.btt_nochmal)
    Button mBttNochmal;


    // Key of selfie uri in Fragment's args
    private static final String SELFIE_URI = "SELFIE_URI";
    private Uri mSelfieUri;

    private ImageView mImageViewSelfie;

    public static PresentPhotoFragment newInstance(Uri uriSelfie) {
        Bundle args = new Bundle();

        args.putString(SELFIE_URI, uriSelfie.toString());
        PresentPhotoFragment fragment = new PresentPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSelfieUri = Uri.parse(getArguments().getString(SELFIE_URI));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.present_photo_fragment, container, false);

        // Set immersive mode
        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = decorView.getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(newUiOptions);

        mImageViewSelfie = (ImageView) view.findViewById(R.id.iv_taken_photo);

        mImageViewSelfie.setLayoutParams(
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
        mImageViewSelfie.setScaleType(ImageView.ScaleType.CENTER_CROP);

        unbinder = ButterKnife.bind(this, view);

        // Display photo
        mImageViewSelfie.setImageURI(mSelfieUri);

        // Image coming from Front Camera has to be flipped
        mImageViewSelfie.setRotationY(180);

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.btt_los_gehts)
    public void selfieAccepted() {
        MainActivity mainActivity = (MainActivity) getActivity();

        //fixme that is an hack!! if button are not unclickable they can still capture events..
        //fixme.. even if another fragment is displayed...
        mBttLosGehts.setClickable(false);
        mBttNochmal.setClickable(false);

        mainActivity.onAnalysisLaunched(mSelfieUri);
    }


    @OnClick(R.id.btt_nochmal)
    public void selfieRejected(){
        deleteSelfie();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TakePhotoFragment.newInstance()).commit();
    }

    private void deleteSelfie() {
        File file = new File(mSelfieUri.getPath());
        file.delete();
        if(file.exists()){
            try {
                file.getCanonicalFile().delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(file.exists()){
                getActivity().getApplicationContext().deleteFile(file.getName());
            }
        }
    }
}
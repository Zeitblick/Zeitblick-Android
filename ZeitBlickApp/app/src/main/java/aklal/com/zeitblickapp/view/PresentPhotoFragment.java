package aklal.com.zeitblickapp.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import aklal.com.zeitblickapp.MainActivity;
import aklal.com.zeitblickapp.R;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by oliver on 08.10.16.
 */

public class PresentPhotoFragment extends Fragment {

    public interface OnAnalysisLaunchedListener{
        void onAnalysisLaunched(Uri uri);
    }


    private final String TAG = getClass().getSimpleName();
    private Unbinder unbinder;

    // key of selfie uri in Fragment's args
    private static final String SELFIE_URI = "SELFIE_URI";
    private Uri mSelfieUri;


//    private com.github.siyamed.shapeimageview.RoundedImageView mImageViewSelfie;
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

        //todo: Olivier: line to delete
        Log.d(TAG, "onCreate - MSELFIEURI = " + mSelfieUri);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.present_photo_fragment_frame_layout_delete, container, false);
        View view = inflater.inflate(R.layout.used_present_photo_fragment_new_design, container, false);


        // Hide appbar/action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

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

        //info: to let the image be full screen
        //http://stackoverflow.com/questions/24463691/how-to-show-imageview-full-screen-on-imageview-click
        mImageViewSelfie.setLayoutParams(
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
        mImageViewSelfie.setScaleType(ImageView.ScaleType.FIT_XY);


        unbinder = ButterKnife.bind(this, view);

        //todo: Olivier: line to delete
        Log.d(TAG, "onCreateView - uri de la photo prise recue en arg: " + mSelfieUri);

        mImageViewSelfie.setImageURI(mSelfieUri);

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }



    @OnClick(R.id.btt_los_gehts)
    public void selfieAccepted(View view){
        //todo: Olivier: line to delete
        Log.d(TAG, "selfieAccepted - Selfie has been accepted!! It's time to go on the cloud!!");

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.onAnalysisLaunched(mSelfieUri);
    }

    @OnClick(R.id.btt_nochmal)
    public void selfieRejected(View view){
        //todo: Olivier: line to delete
        Log.d(TAG, "selfieAccepted - Selfie has been rejected!! May be do we want to take a new one?");

        deleteSelfie();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TakePhotoFragment.newInstance()).commit();
    }


    //TODO: 10.10.16 Ceci devrait etre fait dans un presenter car par directement liée à l'UI
    private void deleteSelfie() {
        // Je pense que si le MediaContent était informé de l'existence de mon selfie, je
        // pourrai appeler: getActivity().getContentResolver().delete(mSelfieUri, null, null);

        // comme ce n'est pas le cas je fais:
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
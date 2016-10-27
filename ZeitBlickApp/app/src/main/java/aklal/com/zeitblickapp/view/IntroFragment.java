package aklal.com.zeitblickapp.view;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import aklal.com.zeitblickapp.R;
import aklal.com.zeitblickapp.view.util.CreditsDialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static aklal.com.zeitblickapp.view.util.ConstantTag.DISPLAY_CREADITS_FRAGMENT_TAG;
import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Created by Aklal on 23.10.16.
 */

public class IntroFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.iv_animated_intro)
    ImageView mIvAnimated;

    @BindView(R.id.btt_information_intro)
    ImageButton mIbInfoIntro;

    public static IntroFragment newInstance() {

        //todo: Olivier: line to delete
        Log.d(TAG, "newInstance");

        IntroFragment fragment = new IntroFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.used_intro_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        // Hide appbar/action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        // Set immersive mode
        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = decorView.getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(newUiOptions);

        mIbInfoIntro.bringToFront();

        // ok mais pas configurable
        ((AnimationDrawable) mIvAnimated.getBackground()).start();

        return view;
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @OnClick(R.id.intro_layout)
    public void onClick() {

        //todo: Olivier: line to delete
        Log.d(TAG, "onClick on INTRO FRAGMENT");

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TakePhotoFragment.newInstance()).commit();
    }

    @OnClick(R.id.btt_information_intro)
    public void onInformationIntroClicked() {
        FragmentManager fm = getFragmentManager();
        CreditsDialogFragment creditsDialogFragment = CreditsDialogFragment.newInstance("Credits");
        creditsDialogFragment.show(fm, DISPLAY_CREADITS_FRAGMENT_TAG);
    }

}

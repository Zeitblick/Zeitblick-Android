package aklal.com.zeitblickapp.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import aklal.com.zeitblickapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static aklal.com.zeitblickapp.view.util.ConstantTag.DISPLAY_CREADITS_FRAGMENT_TAG;

/**
 * Created by Aklal on 23.10.16.
 */

public class IntroFragment extends Fragment {

    public static final String FRAGMENT_TAG = "Credits";

    Unbinder unbinder;

    @BindView(R.id.iv_animated_intro)
    ImageView mIvAnimated;

    @BindView(R.id.btt_information_intro)
    ImageButton mIbInfoIntro;

    @BindView(R.id.tv_app_name)
    TextView mTvAppName;

    private Typeface mACaslonItalicText;

    public static IntroFragment newInstance() {
        IntroFragment fragment = new IntroFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mACaslonItalicText = Typeface
                .createFromAsset(getActivity().getAssets(), "ACaslonPro-Italic.ttf");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

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

        //TODO: 29.10.16 should this be rather an icon ?
        mTvAppName.setTypeface(mACaslonItalicText);

        // Animate png in sequence //todo it is ok but can surely be better
        ((AnimationDrawable) mIvAnimated.getBackground()).start();

        return view;
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @OnClick(R.id.intro_layout)
    public void onClick() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TakePhotoFragment.newInstance()).commit();
    }

    @OnClick(R.id.btt_information_intro)
    public void onInformationIntroClicked() {
        FragmentManager fm = getFragmentManager();
        CreditsDialogFragment creditsDialogFragment = CreditsDialogFragment.newInstance(FRAGMENT_TAG);
        creditsDialogFragment.show(fm, DISPLAY_CREADITS_FRAGMENT_TAG);
    }
}

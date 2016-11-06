package aklal.com.zeitblickapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import aklal.com.zeitblickapp.R;
import aklal.com.zeitblickapp.webdata.models.matching_image.Event;
import aklal.com.zeitblickapp.webdata.models.matching_image.MatchingImage;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static aklal.com.zeitblickapp.R.id.iv_matching_mkg_image;

/**
 * Created by Aklal on 05.11.16.
 */

public class InformationFragment extends Fragment {

    private static final String TAG = InformationFragment.class.getSimpleName();

    private static final String IMAGE_TAG = "image_tag";
    private static final String BYTE_ARRAY_BITMAP_TAG = "byte_array_bitmap_tag";

    @BindView(R.id.tv_matching_mkg_title)
    TextView mTvMkgImageTitle;
    @BindView(R.id.iv_matching_mkg_image)
    ImageView mIvMkgImage;
    @BindView(R.id.tv_artist_content)
    TextView mTvContentArtist;
    @BindView(R.id.tv_description_content)
    TextView mTvContentDescription;
    @BindView(R.id.tv_licence_content)
    TextView mTvContentLicence;
    @BindView(R.id.tv_location_content)
    TextView mTvContentLocation;
    @BindView(R.id.tv_link_content)
    HtmlTextView mTvContentLink;


    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Bitmap mBitmap;
    private Unbinder unbinder;

    private Typeface mACaslonItalicText;
    // Object that contains all information about matching photo
    private MatchingImage mImage;

    public static InformationFragment newInstance(MatchingImage image, byte[] bitmapByte) {

        Bundle args = new Bundle();
        args.putParcelable(IMAGE_TAG, image);
        args.putByteArray(BYTE_ARRAY_BITMAP_TAG, bitmapByte);

        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mACaslonItalicText = Typeface
                .createFromAsset(getActivity().getAssets(), "ACaslonPro-Italic.ttf");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImage = getArguments().getParcelable(IMAGE_TAG);
        byte[] byteArray = getArguments().getByteArray(BYTE_ARRAY_BITMAP_TAG);
        mBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.information_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar_information));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_information);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));


        fillView();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void fillView() {
        // Set image in Coordinatorlayout
        mIvMkgImage.setImageBitmap(mBitmap);

        collapsingToolbarLayout.setTitle(mImage.getMkgMetadata().getTitle());

        //TODO: 03.11.16 Analyse title in case it contains other stuff
        String mkgImageTitle = mImage.getMkgMetadata().getTitle();
        // (like origin as in ' "Rechtsanwalt Dr.Julius" aus der Mappe "Hamburgische Männer aund ..." ')
        mTvMkgImageTitle.setText(mkgImageTitle);
        mTvMkgImageTitle.setTypeface(mACaslonItalicText);

        String unbekannt = getResources().getString(R.string.info_unknown_content);

        Event event = mImage.getMkgMetadata().getEvent();
        String content = (event.getEventActor().isEmpty()) ? unbekannt : event.getEventActor();
        mTvContentArtist.setText(content);

        content = (event.getLocation().isEmpty()) ? unbekannt : event.getLocation();
        mTvContentLocation.setText(content);

        String description = mImage.getMkgMetadata().getDescription();
        content = (description.isEmpty()) ? unbekannt : description;
        mTvContentDescription.setText(content);

        String licence = mImage.getMkgMetadata().getAdministrativeMetadata().getLicense();
        content = (licence.isEmpty()) ? unbekannt : licence;
        mTvContentLicence.setText(content);

        String link = mImage.getMkgMetadata().getAdministrativeMetadata().getInfoLink();
        content = (link.isEmpty()) ? unbekannt : link;
        if (!content.equals(unbekannt)) {

            String mkgAdd = getResources().getString(R.string.info_link_text);
            String htmlLink;
            String textLink;
            if (mkgImageTitle.length() > 20) {
                String troncatedTitle = mkgImageTitle.substring(0, 10);
                if (troncatedTitle.startsWith("\"")) {
                    textLink = "<i>" + troncatedTitle + "\" ...</i> " + mkgAdd;
                } else {
                    textLink = "<i>\"" + troncatedTitle + "\" ...</i> " + mkgAdd;
                }
            } else {

                if (mkgImageTitle.startsWith("\"")) {
                    textLink = "<i>" + mkgImageTitle + "\" ...</i> " + mkgAdd;
                } else {
                    textLink = "<i>\"" + mkgImageTitle + "\" ...</i> " + mkgAdd;
                }
            }
            htmlLink = "<a href=\"" + content + "\">" + textLink + "</a>";
            mTvContentLink.setHtml(htmlLink);

        } else {
            mTvContentLink.setHtml(content);
        }
    }

    @OnClick(R.id.fab_information)
    public void onFabClicked() {
        //todo implement sharing
    }

}

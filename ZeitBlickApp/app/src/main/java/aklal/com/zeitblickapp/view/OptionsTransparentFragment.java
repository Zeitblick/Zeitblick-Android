package aklal.com.zeitblickapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import aklal.com.zeitblickapp.R;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Aklal on 02.11.16.
 */

public class OptionsTransparentFragment extends Fragment {

    private static final String TAG = OptionsTransparentFragment.class.getSimpleName();

    private static final String BYTE_ARRAY_BITMAP_TAG = "byte_array_bitmap_tag";
    private static final String IMAGE_URI_TAG = "image_uri_tag";
    Context mContext;
    private Unbinder unbinder;
    // Bitmap of the matching image from mkg
    private Bitmap mBitmap;
    //Uri to the image
    private String mImageStringUri;

    public static OptionsTransparentFragment newInstance(String imageUri, byte[] bitmapByte) {

        Bundle args = new Bundle();
        args.putString(IMAGE_URI_TAG, imageUri);
        args.putByteArray(BYTE_ARRAY_BITMAP_TAG, bitmapByte);

        OptionsTransparentFragment fragment = new OptionsTransparentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public static OptionsTransparentFragment newInstance() {
        OptionsTransparentFragment fragment = new OptionsTransparentFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageStringUri= getArguments().getString(IMAGE_URI_TAG);
        byte[] byteArray = getArguments().getByteArray(BYTE_ARRAY_BITMAP_TAG);
        mBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.options_transparent_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

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

    // Close fragment -> previous one is shown (if ft.add used)
    @OnClick(R.id.btt_close_fragment)
    public void closeTransparentFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(this).commit();
    }


    @OnClick(R.id.ib_facebook)
    public void shareContentOnFacebook() {

        //todo: Olivier: line to delete
        Log.d(TAG, "SHARECONTENTONFACEBOOK");

        FacebookSdk.sdkInitialize(mContext);
        SharePhotoContent content = null;

        //ok
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(mBitmap)
                    .build();
             content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            ShareDialog shareDialog = new ShareDialog(this);
            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

        }else{
            Log.e(TAG, "shareContentOnFacebook: Impossible to share on Facebook");
        }
    }
}

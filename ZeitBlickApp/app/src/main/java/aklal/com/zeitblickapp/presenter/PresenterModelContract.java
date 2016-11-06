package aklal.com.zeitblickapp.presenter;

import android.net.Uri;

import aklal.com.zeitblickapp.webdata.models.matching_image.MatchingImage;

/**
 * Created by aklal on 10/18/16.
 */

public interface PresenterModelContract {

     interface Operations{
         void retrieveMatchingPhotoName(String name);
         void displayErrorPicture();
         void retrieveMkgMatchingImage(MatchingImage image);
         void retrieveLoadedPhotoUri(Uri uri);
     }
}

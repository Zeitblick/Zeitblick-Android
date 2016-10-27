package aklal.com.zeitblickapp.presenter;

import android.net.Uri;

/**
 * Created by aklal on 10/18/16.
 */

public interface PresenterModelContract {

     interface Operations{
        void retrieveMatchingPhotoName(String name);
        void displayErrorPicture();
        void retrieveLoadedPhotoUri(Uri uri);
     }
}

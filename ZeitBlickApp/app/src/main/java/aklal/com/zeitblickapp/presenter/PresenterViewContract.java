package aklal.com.zeitblickapp.presenter;

import android.net.Uri;

import aklal.com.zeitblickapp.webdata.models.matching_image.MatchingImage;

/**
 * Created by Aklal on 10.10.16.
 */
public interface PresenterViewContract {
    interface View{
        void displaySimilarPhoto(String name);
        void retrieveMatchingImage(MatchingImage image);
        void displaySimilarPhoto(Uri uri);
        void displayError();
    }

    interface Operations{
        void analysePhoto();
    }
}

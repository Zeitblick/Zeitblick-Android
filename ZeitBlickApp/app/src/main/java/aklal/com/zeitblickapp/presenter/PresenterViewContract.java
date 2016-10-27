package aklal.com.zeitblickapp.presenter;

import android.net.Uri;

/**
 * Created by oliver on 10.10.16.
 */
public interface PresenterViewContract {
    interface View{
        void displayProgress(boolean active);
        void displayTextResult(String results);

        void displaySimilarPhoto(String name);
        void displaySimilarPhoto(Uri uri);
        void displayError();
        void displayPrevPhoto();
    }

    interface Operations{
        void analysePhoto();
        void getMatchingPhoto();
    }
}

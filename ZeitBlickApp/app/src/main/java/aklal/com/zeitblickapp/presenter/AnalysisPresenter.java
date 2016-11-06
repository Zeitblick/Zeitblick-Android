package aklal.com.zeitblickapp.presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.FaceAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.common.collect.ImmutableList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import aklal.com.zeitblickapp.MainActivity;
import aklal.com.zeitblickapp.webdata.models.matching_image.MatchingImage;
import aklal.com.zeitblickapp.webdata.models.vision_api.HeadRotation;
import aklal.com.zeitblickapp.webdata.provider.ZeitBlickApiImpl;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Aklal on 09.10.16.
 * <p>
 * Manage all non ui operations
 */
public class AnalysisPresenter
        implements PresenterViewContract.Operations, PresenterModelContract.Operations {

    private static final String TAG = AnalysisPresenter.class.getSimpleName();

    private static final String CLOUD_VISION_API_KEY = " AIzaSyAAaK5Pg209PEA2GBJGO8KEOnGzBr6PQm0 ";
    private static int MAX_SIZE_AFTER_COMPRESSION = 600;

    private final PresenterViewContract.View mAnalysisView;

    //FIXME: 20.10.16 plutot que d'avoir une instance ZeitBlickApiImpl ne serait ce pas mieux de passer par une interface ?
    private ZeitBlickApiImpl zeitBlickController;

    private Uri mPhotoUri;
    private MainActivity mActivity;
    private Boolean mIsActive;
    private long startTime;

    public AnalysisPresenter(@NonNull PresenterViewContract.View analysisView,
                             @NonNull Uri photo, MainActivity activity) {
        mAnalysisView = checkNotNull(analysisView, "AnalysisView reference cannot be null!");
        mPhotoUri = checkNotNull(photo, "Uri to photo cannot be null!");

        //FIXME: 10.10.16 est ce correct/prudent d'avoir une reference vers Activity sans de precautions particulières
        mActivity = activity;

        zeitBlickController = new ZeitBlickApiImpl(this, mActivity.getApplicationContext());
        mIsActive = false;
    }

    /**
     * Prepare image and send it to analyse by Google Vision
     *
     * @param uri
     */
    private void analysePhotoOnGoogleVision(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(
                                        mActivity.getContentResolver(), uri),
                                MAX_SIZE_AFTER_COMPRESSION);

                // ask google vision to analyse the photo
                new VisionAnalysis().execute(bitmap);

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
        }
    }

    /**
     * @param bitmap
     * @throws IOException
     */
    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }


    private HeadRotation returnHeadRotation(BatchAnnotateImagesResponse response) {

        Log.i(TAG, "returnHeadRotation: response = " + response);

        HeadRotation headRotation = new HeadRotation();

        String message = "returnHeadRotation: ";

        List<FaceAnnotation> facemarks = response.getResponses().get(0)
                .getFaceAnnotations();
        if (facemarks == null) {
            message += ("I found nothing\n");
        } else {
            message += "\n\n";
            for (FaceAnnotation facemark : facemarks) {

                message += String.format(Locale.getDefault(), "RollAngle: %s\n",
                        facemark.getRollAngle());
                headRotation.setRoll((double) facemark.getRollAngle());

                message += String.format(Locale.getDefault(), "TiltAngle: %s\n",
                        facemark.getTiltAngle());
                headRotation.setTilt((double) facemark.getTiltAngle());

                message += String.format(Locale.getDefault(), "PanAngle: %s\n",
                        facemark.getPanAngle());
                headRotation.setPan((double) facemark.getPanAngle());

                message += ("\n");
            }
        }

        Log.i(TAG, "returnHeadRotation:  " + message);

        return headRotation;
    }


    /**
     * Call by view, the photo taken by user is sent to Google vision to
     * analyse it, then we ask our server if it has a photo with a similar
     * head rotation
     * <p>
     * //todo REFACTOR: it is not correct that selfie anaylse and server to
     * request are done with one call to analysePhotoOnGoogleVision!!
     */
    @Override
    public void analysePhoto() {
        analysePhotoOnGoogleVision(mPhotoUri);
    }

    @Override
    public void retrieveLoadedPhotoUri(Uri uri) {
        mAnalysisView.displaySimilarPhoto(uri);
    }

    /**
     * Get the name of a photo that matches the selfie we done
     * and call displaySimilarPhoto on the View to download and
     * display the matching photo
     *
     * @param name
     */
    @Override
    public void retrieveMatchingPhotoName(String name) {
        mAnalysisView.displaySimilarPhoto(name);
    }


    @Override
    public void displayErrorPicture() {
        mAnalysisView.displayError();
    }

    private void getPhotoOnServerWithSimilarRotation(HeadRotation headrotation) {
        zeitBlickController.getSimilarRotation(headrotation);
    }

    private class VisionAnalysis extends AsyncTask<Bitmap, Boolean, HeadRotation> {
        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected HeadRotation doInBackground(Bitmap... params) {
            final Bitmap locbitmap = params[0];

            mIsActive = true;
            publishProgress((Boolean) mIsActive);

            startTime = System.currentTimeMillis();

            try {
                HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                builder.setVisionRequestInitializer(new
                        VisionRequestInitializer(CLOUD_VISION_API_KEY));
                Vision vision = builder.build();

                BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                        new BatchAnnotateImagesRequest();
                batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                    AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                    // Add the image
                    Image base64EncodedImage = new Image();
                    // Convert the bitmap to a JPEG
                    // Just in case it's a format that Android understands but Cloud Vision
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    locbitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();

                    // Base64 encode the JPEG
                    base64EncodedImage.encodeContent(imageBytes);
                    annotateImageRequest.setImage(base64EncodedImage);

                    // add the features we want
                    annotateImageRequest.setFeatures(ImmutableList.of(
                            new Feature()
                                    .setType("FACE_DETECTION")
                                    .setMaxResults(10)));

                    // Add the list of one thing to the request
                    add(annotateImageRequest);
                }});

                Vision.Images.Annotate annotateRequest =
                        vision.images().annotate(batchAnnotateImagesRequest);
                // Due to a bug: requests to Vision API containing large images fail when GZipped.
                annotateRequest.setDisableGZipContent(true);
                Log.d(TAG, "created Cloud Vision request object, sending request");

                BatchAnnotateImagesResponse response = annotateRequest.execute();
                return returnHeadRotation(response);

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }

            return null;
        }


        /**
         * Used the result to query our db on the cloud to get matching photos
         *
         * @param result
         */
        protected void onPostExecute(HeadRotation result) {
            mIsActive = false;

            long difference = System.currentTimeMillis() - startTime;
            Log.i(TAG, "onPostExecute - Reponse = " + result + "(" + difference / 1000 + " s)");

            getPhotoOnServerWithSimilarRotation(result);
        }
    }


    @Override
    public void retrieveMkgMatchingImage(MatchingImage image) {
        mAnalysisView.retrieveMatchingImage(image);
    }
}

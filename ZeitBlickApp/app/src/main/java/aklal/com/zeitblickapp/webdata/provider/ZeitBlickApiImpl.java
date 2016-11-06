package aklal.com.zeitblickapp.webdata.provider;

import android.content.Context;
import android.util.Log;

import aklal.com.zeitblickapp.presenter.PresenterModelContract;
import aklal.com.zeitblickapp.webdata.ZeitBlickApi;
import aklal.com.zeitblickapp.webdata.models.matching_image.MatchingImage;
import aklal.com.zeitblickapp.webdata.models.vision_api.HeadRotation;
import aklal.com.zeitblickapp.webdata.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Aklal on 16.10.16.
 */

public class ZeitBlickApiImpl {

    private static final String TAG = ZeitBlickApiImpl.class.getSimpleName();

    /**
     * Allows access to application-specific resources and classes.
     */
    private Context mContext;


    /**
     * Defines methods that communicate with the Web Service.
     */
    private ZeitBlickApi mZeitBlickApi;

    private PresenterModelContract.Operations mPresenter;


    public ZeitBlickApiImpl(PresenterModelContract.Operations ops, Context context) {
        // Store the Application Context.
        mContext = context;
        mPresenter = ops;

        //----------------Interceptor to log request ------------------------
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors...

        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        //-------------------------------------------------------------------

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();

        mZeitBlickApi = retrofit.create(ZeitBlickApi.class);
    }



    public void getSimilarRotation(HeadRotation headRotation) {
        Call<MatchingImage> call = mZeitBlickApi.getSimilarHeadRotation(headRotation);
        call.enqueue(new Callback<MatchingImage>() {
            @Override
            public void onResponse(Call<MatchingImage> call, Response<MatchingImage> response) {

                if (response.isSuccessful()) {
                    mPresenter.retrieveMkgMatchingImage(response.body());
                } else {
                    mPresenter.displayErrorPicture();
                }
            }

            @Override
            public void onFailure(Call<MatchingImage> call, Throwable t) {

                //todo on Failure a particular photo with text must be displayed
            }
        });
    }

}

package aklal.com.zeitblickapp.webdata;

import aklal.com.zeitblickapp.webdata.models.Bild;
import aklal.com.zeitblickapp.webdata.models.HeadRotation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by oliver on 12.10.16.
 */

public interface ZeitBlickApi {
    String URL = "https://projekt-lisa.appspot.com";

    String HEAD_ROTATION_PATH = URL + "/SimilarHeadRotation";

    @POST(value = HEAD_ROTATION_PATH)
    Call<Bild> getSimilarHeadRotation(@Body HeadRotation u);

}

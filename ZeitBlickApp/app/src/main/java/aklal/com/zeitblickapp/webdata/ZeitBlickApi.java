package aklal.com.zeitblickapp.webdata;

import aklal.com.zeitblickapp.webdata.models.matching_image.MatchingImage;
import aklal.com.zeitblickapp.webdata.models.vision_api.HeadRotation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Aklal on 12.10.16.
 */

public interface ZeitBlickApi {
    String URL = "https://projekt-lisa.appspot.com";

    String HEAD_ROTATION_PATH = URL + "/SimilarHeadRotation";

    @POST(value = HEAD_ROTATION_PATH)
    Call<MatchingImage> getSimilarHeadRotation(@Body HeadRotation u);
}

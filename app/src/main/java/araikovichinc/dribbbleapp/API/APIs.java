package araikovichinc.dribbbleapp.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tigran on 19.12.2017.
 */

public interface APIs {
    @GET("shots")
    Call<ArrayList<Shot>> getShots (@Query("access_token") String accessToken, @Query("page") int page, @Query("per_page") int per_page);
}

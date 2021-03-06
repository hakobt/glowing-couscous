package hakob.task.news.api;

import hakob.task.news.data.response.FeedResponse;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.api
 */
public interface NewsApi {
    @GET("feed")
    Single<Response<FeedResponse>> getFeed();
}

package hakob.task.news.data.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.data.response
 */
public class VideoResponse {
    @SerializedName("title")
    private String title;
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;
    @SerializedName("youtubeId")
    private String youtubeId;

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getYoutubeId() {
        return youtubeId;
    }
}

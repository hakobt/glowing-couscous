package hakob.task.task.data.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.data.response
 */
public class Gallery {
    @SerializedName("title")
    private String title;
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;
    @SerializedName("contentUrl")
    private String contentUrl;

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }
}

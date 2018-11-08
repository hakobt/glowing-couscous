package hakob.task.news.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsItemResponse {

    @SerializedName("category")
    private String category;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;
    @SerializedName("shareUrl")
    private String shareUrl;
    @SerializedName("coverPhotoUrl")
    private String coverPhotoUrl;
    @SerializedName("date")
    private long date;
    @SerializedName("gallery")
    private List<GalleryResponse> galleryResponse;
    @SerializedName("video")
    private List<VideoResponse> videoResponse;

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public long getDate() {
        return date;
    }

    public List<GalleryResponse> getGalleryResponse() {
        return galleryResponse;
    }

    public List<VideoResponse> getVideoResponse() {
        return videoResponse;
    }
}

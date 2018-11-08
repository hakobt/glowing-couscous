package hakob.task.news.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Created by Hakob Tovmasyan on 11/1/18
 * Package hakob.task.task.data
 */
@Entity(tableName = "video",
        foreignKeys = @ForeignKey(entity =
                News.class,
                parentColumns = "shareUrl",
                childColumns = "parentKey",
                onDelete = CASCADE
        ),
        indices = @Index(value = "parentKey")
)
public class Video {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String parentKey;
    private String title;
    private String thumbnailUrl;
    private String youtubeId;

    public Video(String parentKey, String title, String thumbnailUrl, String youtubeId) {
        this.parentKey = parentKey;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.youtubeId = youtubeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        if (id != video.id) return false;
        if (parentKey != null ? !parentKey.equals(video.parentKey) : video.parentKey != null)
            return false;
        if (title != null ? !title.equals(video.title) : video.title != null) return false;
        if (thumbnailUrl != null ? !thumbnailUrl.equals(video.thumbnailUrl) : video.thumbnailUrl != null)
            return false;
        return youtubeId != null ? youtubeId.equals(video.youtubeId) : video.youtubeId == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (parentKey != null ? parentKey.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (thumbnailUrl != null ? thumbnailUrl.hashCode() : 0);
        result = 31 * result + (youtubeId != null ? youtubeId.hashCode() : 0);
        return result;
    }
}

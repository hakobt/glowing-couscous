package hakob.task.task.data;

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
        foreignKeys = @ForeignKey(entity = NewsEntity.class, parentColumns = "id", childColumns = "parentId", onDelete = CASCADE),
        indices = @Index(value = "parentId")
)
public class VideoEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int parentId;
    private String title;
    private String thumbnailUrl;
    private String youtubeId;

    public VideoEntity(int parentId, String title, String thumbnailUrl, String youtubeId) {
        this.parentId = parentId;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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
}

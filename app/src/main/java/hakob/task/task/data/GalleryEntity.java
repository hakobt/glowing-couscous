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
@Entity(tableName = "gallery",
        foreignKeys = @ForeignKey(
                entity = NewsEntity.class,
                parentColumns = "shareUrl",
                childColumns = "parentKey",
                onDelete = CASCADE
        ),
        indices = @Index(value = "parentKey")
)
public class GalleryEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String parentKey;
    private String title;
    private String thumbnailUrl;
    private String contentUrl;

    public GalleryEntity(String title, String thumbnailUrl, String contentUrl, String parentKey) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.contentUrl = contentUrl;
        this.parentKey = parentKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }
}

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
        foreignKeys = @ForeignKey(entity = NewsEntity.class, parentColumns = "id", childColumns = "parentId", onDelete = CASCADE),
        indices = @Index(value = "parentId")
)
public class GalleryEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int parentId;
    private String title;
    private String thumbnailUrl;
    private String contentUrl;

    public GalleryEntity(String title, String thumbnailUrl, String contentUrl, int parentId) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.contentUrl = contentUrl;
        this.parentId = parentId;
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

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}

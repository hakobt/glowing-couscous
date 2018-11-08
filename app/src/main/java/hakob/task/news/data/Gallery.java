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
@Entity(tableName = "gallery",
        foreignKeys = @ForeignKey(
                entity = News.class,
                parentColumns = "shareUrl",
                childColumns = "parentKey",
                onDelete = CASCADE
        ),
        indices = @Index(value = "parentKey")
)
public class Gallery {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String parentKey;
    private String title;
    private String thumbnailUrl;
    private String contentUrl;

    public Gallery(String title, String thumbnailUrl, String contentUrl, String parentKey) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gallery gallery = (Gallery) o;

        if (id != gallery.id) return false;
        if (parentKey != null ? !parentKey.equals(gallery.parentKey) : gallery.parentKey != null)
            return false;
        if (title != null ? !title.equals(gallery.title) : gallery.title != null) return false;
        if (thumbnailUrl != null ? !thumbnailUrl.equals(gallery.thumbnailUrl) : gallery.thumbnailUrl != null)
            return false;
        return contentUrl != null ? contentUrl.equals(gallery.contentUrl) : gallery.contentUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (parentKey != null ? parentKey.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (thumbnailUrl != null ? thumbnailUrl.hashCode() : 0);
        result = 31 * result + (contentUrl != null ? contentUrl.hashCode() : 0);
        return result;
    }
}

package hakob.task.task.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.data
 */

@Entity(tableName = "news")
public class NewsEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String shareUrl;
    private String title;
    private String category;
    private String body;
    private String coverPhotoUrl;
    private long date;
    private boolean isRead = false;

    public NewsEntity(String shareUrl, String title, String category, String body, String coverPhotoUrl, long date) {
        this.shareUrl = shareUrl;
        this.title = title;
        this.category = category;
        this.body = body;
        this.coverPhotoUrl = coverPhotoUrl;
        this.date = date;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsEntity that = (NewsEntity) o;

        if (id != that.id) return false;
        if (date != that.date) return false;
        if (isRead != that.isRead) return false;
        if (shareUrl != null ? !shareUrl.equals(that.shareUrl) : that.shareUrl != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        return coverPhotoUrl != null ? coverPhotoUrl.equals(that.coverPhotoUrl) : that.coverPhotoUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (shareUrl != null ? shareUrl.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (coverPhotoUrl != null ? coverPhotoUrl.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (isRead ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "id=" + id +
                ", shareUrl='" + shareUrl + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", coverPhotoUrl='" + coverPhotoUrl + '\'' +
                ", date=" + date +
                ", isRead=" + isRead +
                '}';
    }
}

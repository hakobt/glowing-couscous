package hakob.task.news.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import hakob.task.news.data.Gallery;
import hakob.task.news.data.News;
import hakob.task.news.data.Video;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.db
 */
@Dao
public abstract class NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertNews(List<News> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertGallery(List<Gallery> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertVideos(List<Video> video);

    @Query("UPDATE News SET isRead = :read WHERE shareUrl = :url")
    public abstract void setNewsItemRead(String url, boolean read);

    @Query("SELECT * FROM News ORDER BY date DESC")
    public abstract LiveData<List<News>> getAll();

    @Query("SELECT * FROM Gallery WHERE parentKey = :url")
    public abstract LiveData<List<Gallery>> getGalleriesWithNewsId(String url);

    @Query("SELECT * FROM Gallery WHERE id = :id")
    public abstract LiveData<Gallery> getGallery(int id);

    @Query("SELECT * FROM Video WHERE parentKey = :url")
    public abstract LiveData<List<Video>> getVideosWithNewsId(String url);

    @Query("SELECT * FROM News WHERE shareUrl = :url")
    public abstract LiveData<News> getNewsItemById(String url);

    @Query("SELECT * FROM News WHERE shareUrl = :url")
    public abstract News getNewsItem(String url);

    @Query("DELETE FROM News")
    public abstract void deleteAllNews();
}

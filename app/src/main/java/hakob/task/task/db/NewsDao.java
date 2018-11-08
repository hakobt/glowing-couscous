package hakob.task.task.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import hakob.task.task.data.GalleryEntity;
import hakob.task.task.data.NewsEntity;
import hakob.task.task.data.VideoEntity;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.db
 */
@Dao
public abstract class NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertNews(List<NewsEntity> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertGallery(List<GalleryEntity> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertVideos(List<VideoEntity> videoEntity);

    @Query("UPDATE news SET isRead = :read WHERE shareUrl = :url")
    public abstract void setNewsItemRead(String url, boolean read);

    @Query("SELECT * FROM news ORDER BY date DESC")
    public abstract LiveData<List<NewsEntity>> getAll();

    @Query("SELECT * FROM gallery WHERE parentKey = :url")
    public abstract LiveData<List<GalleryEntity>> getGalleriesWithNewsId(String url);

    @Query("SELECT * FROM gallery WHERE id = :id")
    public abstract LiveData<GalleryEntity> getGallery(int id);

    @Query("SELECT * FROM video WHERE parentKey = :url")
    public abstract LiveData<List<VideoEntity>> getVideosWithNewsId(String url);

    @Query("SELECT * FROM news WHERE shareUrl = :url")
    public abstract LiveData<NewsEntity> getNewsItemById(String url);

    @Query("SELECT * FROM news WHERE shareUrl = :url")
    public abstract NewsEntity getNewsItem(String url);

    @Query("DELETE FROM news")
    public abstract void deleteAllNews();

    @Query("DELETE FROM gallery")
    abstract void deleteAllGallery();

    @Query("DELETE FROM video")
    abstract void deleteAllVideos();

    @Transaction
    public void deleteAll() {
        deleteAllGallery();
        deleteAllVideos();
        deleteAllNews();
    }
}

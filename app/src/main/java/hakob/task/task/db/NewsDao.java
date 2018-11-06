package hakob.task.task.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
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
    public abstract long addNewsItem(NewsEntity item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addGallery(GalleryEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addVideo(VideoEntity videoEntity);

    @Update
    public abstract void updateNewsItem(NewsEntity newsEntity);

    @Query("SELECT * FROM news ORDER BY date DESC")
    public abstract LiveData<List<NewsEntity>> getAll();

    @Query("SELECT * FROM gallery WHERE parentId = :newsId")
    public abstract LiveData<List<GalleryEntity>> getGalleryWithId(int newsId);

    @Query("SELECT * FROM video WHERE parentId = :newsId")
    public abstract LiveData<List<VideoEntity>> getVideosWithId(int newsId);

    @Query("SELECT * FROM news WHERE id = :id")
    public abstract LiveData<NewsEntity> getNewsItemById(int id);

    @Query("DELETE FROM news")
    abstract void deleteAllNews();

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

    @Delete
    abstract void delete(NewsEntity newsEntity);
}

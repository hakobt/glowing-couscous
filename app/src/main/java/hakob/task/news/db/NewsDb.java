package hakob.task.news.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import hakob.task.news.data.Gallery;
import hakob.task.news.data.News;
import hakob.task.news.data.Video;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.db
 */
@Database(entities =
        {
                News.class,
                Gallery.class,
                Video.class
        },
        version = 5
)
public abstract class NewsDb extends RoomDatabase {

    public abstract NewsDao newsDao();
}

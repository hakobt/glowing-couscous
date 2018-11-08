package hakob.task.task.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import hakob.task.task.data.GalleryEntity;
import hakob.task.task.data.NewsEntity;
import hakob.task.task.data.VideoEntity;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.db
 */
@Database(entities =
        {
                NewsEntity.class,
                GalleryEntity.class,
                VideoEntity.class
        },
        version = 5
)
public abstract class NewsDb extends RoomDatabase {

    public abstract NewsDao newsDao();
}

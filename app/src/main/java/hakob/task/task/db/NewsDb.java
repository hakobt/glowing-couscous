package hakob.task.task.db;

import java.util.Date;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
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
        version = 3,
        exportSchema = false)
@TypeConverters(NewsDb.DateTimeConverter.class)
public abstract class NewsDb extends RoomDatabase {

    public abstract NewsDao newsDao();

    static class DateTimeConverter {

        @TypeConverter
        public Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public Long dateToTimestamp(Date date) {
            if (date == null) {
                return null;
            } else {
                return date.getTime();
            }
        }
    }
}

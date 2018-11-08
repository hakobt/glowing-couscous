package hakob.task.news.db;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.di
 */

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    public NewsDb db(Context context) {
        return Room.databaseBuilder(context, NewsDb.class, "news.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    public NewsDao newsDao(NewsDb db) {
        return db.newsDao();
    }
}

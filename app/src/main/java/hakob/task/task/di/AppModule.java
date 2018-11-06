package hakob.task.task.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.di
 */
@Module
public abstract class AppModule {

    @Binds
    public abstract Context context(Application application);
}

package hakob.task.news.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import hakob.task.news.NewsApp;
import hakob.task.news.db.DatabaseModule;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.di
 */
@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                NetworkModule.class,
                ViewModelModule.class,
                DatabaseModule.class,
                ActivityBuilderModule.class,
                FragmentBuilderModule.class
        }
)
public interface ApplicationComponent extends AndroidInjector<NewsApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

}

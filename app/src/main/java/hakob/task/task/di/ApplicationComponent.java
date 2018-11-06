package hakob.task.task.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import hakob.task.task.NewsApp;
import hakob.task.task.db.DatabaseModule;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.di
 */
@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AndroidSupportInjectionModule.class,
                AppModule.class,
                NetworkModule.class,
                ViewModelModule.class,
                DatabaseModule.class,
                MainActivityModule.class,
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

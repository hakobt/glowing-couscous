package hakob.task.news.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hakob.task.news.ui.MainActivity;
import hakob.task.news.ui.image.ImageViewActivity;

/**
 * Created by Hakob Tovmasyan on 10/28/18
 * Package hakob.task.task.di
 */

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract ImageViewActivity imageViewActivity();

}

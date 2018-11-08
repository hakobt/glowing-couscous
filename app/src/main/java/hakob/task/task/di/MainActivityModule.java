package hakob.task.task.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hakob.task.task.ui.MainActivity;
import hakob.task.task.ui.image.ImageViewActivity;

/**
 * Created by Hakob Tovmasyan on 10/28/18
 * Package hakob.task.task.di
 */

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract ImageViewActivity imageViewActivity();

}

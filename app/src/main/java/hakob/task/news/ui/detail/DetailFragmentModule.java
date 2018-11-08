package hakob.task.news.ui.detail;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Hakob Tovmasyan on 10/29/18
 * Package hakob.task.task.ui.detail
 */

@Module
public abstract class DetailFragmentModule {

    @ContributesAndroidInjector
    abstract DetailFragment detailFragment();
}

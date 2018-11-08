package hakob.task.news.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hakob.task.news.ui.detail.DetailFragment;
import hakob.task.news.ui.master.MasterFragment;

/**
 * Created by Hakob Tovmasyan on 10/29/18
 * Package hakob.task.task.di
 */

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract MasterFragment masterFragment();

    @ContributesAndroidInjector
    abstract DetailFragment detailFragment();
}

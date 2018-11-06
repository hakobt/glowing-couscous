package hakob.task.task.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hakob.task.task.ui.detail.DetailFragment;
import hakob.task.task.ui.master.MasterFragment;

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

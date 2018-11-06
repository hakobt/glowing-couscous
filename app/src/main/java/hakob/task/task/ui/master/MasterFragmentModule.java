package hakob.task.task.ui.master;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Hakob Tovmasyan on 10/29/18
 * Package hakob.task.task.ui.master
 */

@Module
public abstract class MasterFragmentModule {

    @ContributesAndroidInjector
    abstract MasterFragment masterFragment();

}

package hakob.task.news.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import hakob.task.news.ViewModelFactory;
import hakob.task.news.ui.MainActivityViewModel;
import hakob.task.news.ui.detail.DetailViewModel;
import hakob.task.news.ui.image.ImageViewModel;
import hakob.task.news.ui.master.MasterViewModel;

/**
 * Created by Hakob Tovmasyan on 10/28/18
 * Package hakob.task.task.di
 */

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MasterViewModel.class)
    abstract ViewModel bindMasterViewModel(MasterViewModel masterViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel detailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindMainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ImageViewModel.class)
    abstract ViewModel bindImageViewModel(ImageViewModel imageViewModel);
}

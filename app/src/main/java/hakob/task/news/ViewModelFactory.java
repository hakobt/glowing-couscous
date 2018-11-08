package hakob.task.news;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Hakob Tovmasyan on 10/28/18
 * Package hakob.task.task
 */
@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelProviderMap;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelProviderMap) {
        this.viewModelProviderMap = viewModelProviderMap;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (viewModelProviderMap.containsKey(modelClass)) {
            return (T) viewModelProviderMap.get(modelClass).get();
        } else {
            throw new IllegalArgumentException(modelClass.toString() + " not configured in ViewModelModule");
        }
    }
}

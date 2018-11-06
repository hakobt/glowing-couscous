package hakob.task.task.ui;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import hakob.task.task.data.NetworkStatus;
import hakob.task.task.data.NewsRepository;

/**
 * Created by Hakob Tovmasyan on 11/6/18
 * Package hakob.task.task.ui
 */
public class MainActivityViewModel extends ViewModel {

    @NonNull
    private final NewsRepository newsRepository;

    @Inject
    public MainActivityViewModel(@NonNull NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    void forceRefresh() {
        newsRepository.forceRefresh();
    }

    LiveData<NetworkStatus> getNetworkStatus() {
        return newsRepository.getNetworkStatus();
    }
}

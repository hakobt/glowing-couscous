package hakob.task.task.ui.master;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import hakob.task.task.data.NewsEntity;
import hakob.task.task.data.NewsRepository;

public class MasterViewModel extends ViewModel {

    private final NewsRepository newsRepository;

    @Inject
    public MasterViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        newsRepository.forceRefresh();
    }

    public LiveData<List<NewsEntity>> getNews() {
        return newsRepository.getAllNewsItems();
    }

    public void setNewsItemRead(NewsEntity newsEntity) {
        newsRepository.updateNewsItem(newsEntity);
    }
}

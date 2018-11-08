package hakob.task.news.ui.master;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import hakob.task.news.data.News;
import hakob.task.news.data.NewsRepository;

public class MasterViewModel extends ViewModel {

    private final NewsRepository newsRepository;

    @Inject
    public MasterViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        newsRepository.forceRefresh();
    }

    public LiveData<List<News>> getNews() {
        return newsRepository.getAllNewsItems();
    }

    public void setNewsItemRead(News news) {
        newsRepository.updateNewsItem(news);
    }
}

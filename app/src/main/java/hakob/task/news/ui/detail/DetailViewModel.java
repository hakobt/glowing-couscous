package hakob.task.news.ui.detail;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import hakob.task.news.data.Gallery;
import hakob.task.news.data.News;
import hakob.task.news.data.NewsRepository;
import hakob.task.news.data.Video;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.ui.details
 */
public class DetailViewModel extends ViewModel {

    @NonNull
    private final NewsRepository newsRepository;

    @Inject
    public DetailViewModel(@NonNull NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public LiveData<News> getNewsItem(String url) {
        return newsRepository.getNewsItemWithId(url);
    }

    public LiveData<List<Gallery>> getGalleryItem(String url) {
        return newsRepository.getGalleryWithNewsId(url);
    }

    public LiveData<List<Video>> getVideos(String url) {
        return newsRepository.getVideosWithNewsId(url);
    }
}

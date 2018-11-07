package hakob.task.task.ui.detail;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import hakob.task.task.data.GalleryEntity;
import hakob.task.task.data.NewsEntity;
import hakob.task.task.data.NewsRepository;
import hakob.task.task.data.VideoEntity;

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

    public LiveData<NewsEntity> getNewsItem(String url) {
        return newsRepository.getNewsItemWithId(url);
    }

    public LiveData<List<GalleryEntity>> getGalleryItem(String url) {
        return newsRepository.getGalleryWithNewsId(url);
    }

    public LiveData<List<VideoEntity>> getVideos(String url) {
        return newsRepository.getVideosWithNewsId(url);
    }

    public void setNewsItemRead(NewsEntity newsEntity) {
        newsEntity.setRead(true);
        newsRepository.updateNewsItem(newsEntity);
    }
}

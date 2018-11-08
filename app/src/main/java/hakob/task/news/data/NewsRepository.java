package hakob.task.news.data;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import hakob.task.news.api.NewsApi;
import hakob.task.news.common.SingleLiveEvent;
import hakob.task.news.data.response.FeedResponse;
import hakob.task.news.data.response.GalleryResponse;
import hakob.task.news.data.response.NewsItemResponse;
import hakob.task.news.data.response.VideoResponse;
import hakob.task.news.db.NewsDao;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Hakob Tovmasyan on 10/29/18
 * Package hakob.task.task.data
 */
@Singleton
public class NewsRepository {

    @NonNull
    private final NewsDao newsDao;

    @NonNull
    private final NewsApi newsApi;

    private final SingleLiveEvent<NetworkStatus> networkStatus = new SingleLiveEvent<>();

    private Scheduler dbScheduler = Schedulers.single();

    @Inject
    public NewsRepository(@NonNull NewsDao newsDao, @NonNull NewsApi newsApi) {
        this.newsDao = newsDao;
        this.newsApi = newsApi;
    }

    public LiveData<NetworkStatus> getNetworkStatus() {
        return networkStatus;
    }

    public LiveData<List<News>> getAllNewsItems() {
        return newsDao.getAll();
    }

    public LiveData<News> getNewsItemWithId(String url) {
        return newsDao.getNewsItemById(url);
    }

    public LiveData<List<Gallery>> getGalleryWithNewsId(String url) {
        return newsDao.getGalleriesWithNewsId(url);
    }

    public LiveData<List<Video>> getVideosWithNewsId(String url) {
        return newsDao.getVideosWithNewsId(url);
    }

    public void forceRefresh() {
        loadFromNetwork();
    }

    @SuppressLint("CheckResult")
    private void loadFromNetwork() {
        newsApi.getFeed()
                .doOnSubscribe(disposable -> networkStatus.postValue(NetworkStatus.LOADING))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapCompletable(response -> saveToDb(response).subscribeOn(dbScheduler))
                .subscribe(() -> {
                    networkStatus.postValue(NetworkStatus.SUCCESS);
                }, throwable -> {
                    throwable.printStackTrace();
                    networkStatus.postValue(NetworkStatus.ERROR);
                });
    }

    private Completable saveToDb(Response<FeedResponse> response) {
        if (!response.isSuccessful() || response.body() == null || !response.body().isSuccess()) {
            networkStatus.postValue(NetworkStatus.ERROR);
            return Completable.complete();
        }
        List<NewsItemResponse> data = response.body().getData();
        return Completable.create(emitter -> {
            ArrayList<News> newsEntities = new ArrayList<>();
            ArrayList<Gallery> galleryEntities = new ArrayList<>();
            ArrayList<Video> videoEntities = new ArrayList<>();
            for (NewsItemResponse newsItemResponse : data) {
                News news = new News(
                        newsItemResponse.getShareUrl(),
                        newsItemResponse.getTitle(),
                        newsItemResponse.getCategory(),
                        newsItemResponse.getBody(),
                        newsItemResponse.getCoverPhotoUrl(),
                        newsItemResponse.getDate()
                );
                News oldEntity = newsDao.getNewsItem(news.getShareUrl());
                if (oldEntity != null && oldEntity.isRead()) {
                    news.setRead(true);
                }
                newsEntities.add(news);
                String parentKey = newsItemResponse.getShareUrl();
                if (newsItemResponse.getGalleryResponse() != null) {
                    for (GalleryResponse galleryResponse : newsItemResponse.getGalleryResponse()) {
                        Gallery gallery = new Gallery(
                                galleryResponse.getTitle(),
                                galleryResponse.getThumbnailUrl(),
                                galleryResponse.getContentUrl(),
                                parentKey
                        );
                        galleryEntities.add(gallery);
                    }
                }
                if (newsItemResponse.getVideoResponse() != null) {
                    for (VideoResponse videoResponse : newsItemResponse.getVideoResponse()) {
                        Video video = new Video(
                                parentKey,
                                videoResponse.getTitle(),
                                videoResponse.getThumbnailUrl(),
                                videoResponse.getYoutubeId()
                        );
                        videoEntities.add(video);
                    }
                }
            }
            newsDao.insertNews(newsEntities);
            newsDao.insertGallery(galleryEntities);
            newsDao.insertVideos(videoEntities);
            emitter.onComplete();
        });
    }

    public void updateNewsItem(News entity) {
        Completable.create(emitter -> {
            newsDao.setNewsItemRead(entity.getShareUrl(), true);
        }).subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public LiveData<Gallery> getGalleryItem(int id) {
        return newsDao.getGallery(id);
    }
}

package hakob.task.task.data;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import hakob.task.task.api.NewsApi;
import hakob.task.task.data.response.FeedResponse;
import hakob.task.task.data.response.GalleryResponse;
import hakob.task.task.data.response.NewsItemResponse;
import hakob.task.task.data.response.Video;
import hakob.task.task.db.NewsDao;
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

    private final MutableLiveData<NetworkStatus> networkStatus = new MutableLiveData<>();

    private Scheduler dbScheduler = Schedulers.single();

    @Inject
    public NewsRepository(@NonNull NewsDao newsDao, @NonNull NewsApi newsApi) {
        this.newsDao = newsDao;
        this.newsApi = newsApi;
    }

    public LiveData<NetworkStatus> getNetworkStatus() {
        return networkStatus;
    }

    public LiveData<List<NewsEntity>> getAllNewsItems() {
        return newsDao.getAll();
    }

    public LiveData<NewsEntity> getNewsItemWithId(String url) {
        return newsDao.getNewsItemById(url);
    }

    public LiveData<List<GalleryEntity>> getGalleryWithNewsId(String url) {
        return newsDao.getGalleriesWithNewsId(url);
    }

    public LiveData<List<VideoEntity>> getVideosWithNewsId(String url) {
        return newsDao.getVideosWithNewsId(url);
    }

    public void forceRefresh() {
        loadFromNetwork();
    }

    @SuppressLint("CheckResult")
    private void loadFromNetwork() {
        networkStatus.postValue(NetworkStatus.LOADING);
        newsApi.getFeed()
                .doOnSuccess(response -> saveToDb(response).subscribeOn(dbScheduler).subscribe())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedResponseResponse -> {

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
            ArrayList<NewsEntity> newsEntities = new ArrayList<>();
            ArrayList<GalleryEntity> galleryEntities = new ArrayList<>();
            ArrayList<VideoEntity> videoEntities = new ArrayList<>();
            for (NewsItemResponse newsItemResponse : data) {
                NewsEntity newsEntity = new NewsEntity(
                        newsItemResponse.getShareUrl(),
                        newsItemResponse.getTitle(),
                        newsItemResponse.getCategory(),
                        newsItemResponse.getBody(),
                        newsItemResponse.getCoverPhotoUrl(),
                        newsItemResponse.getDate()
                );
                NewsEntity oldEntity = newsDao.getNewsItem(newsEntity.getShareUrl());
                if (oldEntity != null && oldEntity.isRead()) {
                    newsEntity.setRead(true);
                }
                newsEntities.add(newsEntity);
                String parentKey = newsItemResponse.getShareUrl();
                if (newsItemResponse.getGalleryResponse() != null) {
                    for (GalleryResponse galleryResponse : newsItemResponse.getGalleryResponse()) {
                        GalleryEntity galleryEntity = new GalleryEntity(
                                galleryResponse.getTitle(),
                                galleryResponse.getThumbnailUrl(),
                                galleryResponse.getContentUrl(),
                                parentKey
                        );
                        galleryEntities.add(galleryEntity);
                    }
                }
                if (newsItemResponse.getVideo() != null) {
                    for (Video video : newsItemResponse.getVideo()) {
                        VideoEntity videoEntity = new VideoEntity(
                                parentKey,
                                video.getTitle(),
                                video.getThumbnailUrl(),
                                video.getYoutubeId()
                        );
                        videoEntities.add(videoEntity);
                    }
                }
            }
            newsDao.insertNews(newsEntities);
            newsDao.insertGallery(galleryEntities);
            newsDao.insertVideos(videoEntities);
            networkStatus.postValue(NetworkStatus.SUCCESS);
            emitter.onComplete();
        });
    }

    public void updateNewsItem(NewsEntity entity) {
        Completable.create(emitter -> {
            newsDao.setNewsItemRead(entity.getShareUrl(), true);
        }).subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public LiveData<GalleryEntity> getGalleryItem(int id) {
        return newsDao.getGallery(id);
    }
}

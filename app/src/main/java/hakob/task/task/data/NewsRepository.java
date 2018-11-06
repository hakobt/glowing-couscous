package hakob.task.task.data;

import android.annotation.SuppressLint;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import hakob.task.task.api.NewsApi;
import hakob.task.task.data.response.FeedResponse;
import hakob.task.task.data.response.Gallery;
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

    public MutableLiveData<NetworkStatus> getNetworkStatus() {
        return networkStatus;
    }

    public LiveData<List<NewsEntity>> getAllNewsItems() {
        return newsDao.getAll();
    }

    public LiveData<NewsEntity> getNewsItemWithId(int id) {
        return newsDao.getNewsItemById(id);
    }

    public LiveData<List<GalleryEntity>> getGalleryWithNewsId(int id) {
        return newsDao.getGalleryWithId(id);
    }

    public LiveData<List<VideoEntity>> getVideosWithNewsId(int id) {
        return newsDao.getVideosWithId(id);
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
            newsDao.deleteAll();
            for (NewsItemResponse newsItemResponse : data) {
                NewsEntity newsEntity = new NewsEntity(
                        newsItemResponse.getShareUrl(),
                        newsItemResponse.getTitle(),
                        newsItemResponse.getCategory(),
                        newsItemResponse.getBody(),
                        newsItemResponse.getCoverPhotoUrl(),
                        newsItemResponse.getDate()
                );
                int parentId = (int) newsDao.addNewsItem(newsEntity);
                if (newsItemResponse.getGallery() != null) {
                    for (Gallery gallery : newsItemResponse.getGallery()) {
                        GalleryEntity galleryEntity = new GalleryEntity(
                                gallery.getTitle(),
                                gallery.getThumbnailUrl(),
                                gallery.getContentUrl(),
                                parentId
                        );
                        newsDao.addGallery(galleryEntity);
                    }
                }
                if (newsItemResponse.getVideo() != null) {
                    for (Video video : newsItemResponse.getVideo()) {
                        VideoEntity videoEntity = new VideoEntity(
                                parentId,
                                video.getTitle(),
                                video.getThumbnailUrl(),
                                video.getYoutubeId()
                        );
                        newsDao.addVideo(videoEntity);
                    }
                }
            }
            networkStatus.postValue(NetworkStatus.SUCCESS);
            emitter.onComplete();
        });
    }

    public void updateNewsItem(NewsEntity newsEntity) {
        Completable.create(emitter -> {
            newsDao.updateNewsItem(newsEntity);
        }).subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}

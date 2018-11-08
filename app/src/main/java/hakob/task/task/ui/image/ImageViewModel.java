package hakob.task.task.ui.image;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import hakob.task.task.data.GalleryEntity;
import hakob.task.task.data.NewsRepository;

/**
 * Created by Hakob Tovmasyan on 11/8/18
 * Package hakob.task.task.ui.image
 */
public class ImageViewModel extends ViewModel {

    private final NewsRepository newsRepository;

    private final MutableLiveData<String> imageUrl = new MutableLiveData<>();

    @Inject
    public ImageViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public LiveData<GalleryEntity> getImage(int id) {
        return newsRepository.getGalleryItem(id);
    }
}

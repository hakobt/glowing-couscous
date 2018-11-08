package hakob.task.task.ui.image;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import hakob.task.task.R;
import hakob.task.task.common.InjectableAppCompatActivity;

import static hakob.task.task.Constants.imageKey;

public class ImageViewActivity extends InjectableAppCompatActivity {

    @BindView(R.id.contentImage)
    ImageView contentImage;

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);

        if (getIntent() == null || getIntent().getExtras() == null || !getIntent().getExtras().containsKey(imageKey)) {
            finish();
        }

        int imageId = getIntent().getExtras().getInt(imageKey);

        ImageViewModel viewModel = ViewModelProviders.of(this, factory).get(ImageViewModel.class);

        viewModel.getImage(imageId).observe(this, galleryEntity -> {
            Picasso.get().load(galleryEntity.getContentUrl()).into(contentImage);
        });
    }
}

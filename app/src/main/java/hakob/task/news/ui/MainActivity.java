package hakob.task.news.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import hakob.task.news.Constants;
import hakob.task.news.R;
import hakob.task.news.common.InjectableAppCompatActivity;
import hakob.task.news.data.NetworkStatus;
import hakob.task.news.data.News;
import hakob.task.news.data.Video;
import hakob.task.news.ui.detail.DetailFragment;
import hakob.task.news.ui.image.ImageViewActivity;
import hakob.task.news.ui.master.MasterFragment;

import static hakob.task.news.Constants.imageKey;
import static hakob.task.news.Constants.isTablet;

public class MainActivity extends InjectableAppCompatActivity {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            MasterFragment fragment = MasterFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.list_container, fragment, fragment.toString())
                    .commit();
        }

        MainActivityViewModel vm = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);

        swipeRefreshLayout.setOnRefreshListener(vm::forceRefresh);

        vm.getNetworkStatus().observe(this, this::showNetworkStatus);
    }

    private void showNetworkStatus(NetworkStatus networkStatus) {
        switch (networkStatus) {
            case ERROR:
                Snackbar.make(swipeRefreshLayout, "Failed to load content", Snackbar.LENGTH_SHORT).show();
            case SUCCESS:
                swipeRefreshLayout.setRefreshing(false);
                break;
            case LOADING:
                swipeRefreshLayout.setRefreshing(true);
                break;
        }
    }

    public void showFullscreenImage(int imageId) {
        Intent intent = new Intent(this, ImageViewActivity.class);
        intent.putExtra(imageKey, imageId);
        startActivityForResult(intent, 42);
    }

    public void showDetailsScreen(News news) {
        DetailFragment detailFragment = DetailFragment.create(news.getShareUrl());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.details_container, detailFragment);

        if (!isTablet) {
            fragmentTransaction.addToBackStack(detailFragment.getClass().getSimpleName());
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showVideo(Video video) {
        String id = video.getYoutubeId();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}

package hakob.task.task.ui;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import hakob.task.task.R;
import hakob.task.task.common.InjectableAppCompatActivity;
import hakob.task.task.data.NetworkStatus;
import hakob.task.task.ui.detail.DetailFragment;
import hakob.task.task.ui.master.MasterFragment;

public class MainActivity extends InjectableAppCompatActivity {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
            case SUCCESS:
            case ERROR:
                swipeRefreshLayout.setRefreshing(false);
                break;
            case LOADING:
                swipeRefreshLayout.setRefreshing(true);
                break;
        }
    }

    public void showDetailsScreen(String url) {
        DetailFragment detailFragment = DetailFragment.create(url);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .add(R.id.details_container, detailFragment, DetailFragment.class.getSimpleName())
                .addToBackStack(DetailFragment.class.getSimpleName())
                .commit();
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        swipeRefreshLayout.setEnabled(true);
    }
}

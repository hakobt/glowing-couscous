package hakob.task.news.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import butterknife.BindView;
import butterknife.ButterKnife;
import hakob.task.news.R;
import hakob.task.news.common.InjectableFragment;
import hakob.task.news.data.News;
import hakob.task.news.databinding.DetailsBinding;
import hakob.task.news.ui.MainActivity;

import static hakob.task.news.Constants.detailKey;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.ui
 */
public class DetailFragment extends InjectableFragment {

    @Inject
    ViewModelProvider.Factory factory;

    @BindView(R.id.cover)
    ImageView cover;

    private String url;

    private DetailViewModel viewModel;
    private DetailsBinding binding;

    private GalleryAdapter galleryAdapter;

    /**
     * Use DetailFragment.create(int id) method
     *
     * @deprecated
     */
    public DetailFragment() {

    }

    public static DetailFragment create(String url) {
        Bundle args = new Bundle();

        args.putString(detailKey, url);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null || !getArguments().containsKey(detailKey)) {
            // details screen cannot exist without url
            throw new IllegalStateException("Cannot show details screen without url provided");
        }
        url = getArguments().getString("url");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailsBinding.inflate(inflater, container, false);
        ButterKnife.bind(this, binding.getRoot());

        cover.setTransitionName(url);

        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        Transition transition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_element_transition);
        transition.setDuration(225);
        transition.setInterpolator(new FastOutLinearInInterpolator());
        setSharedElementEnterTransition(transition);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryAdapter = new GalleryAdapter(galleryEntity -> {
            ((MainActivity) requireActivity()).showFullscreenImage(galleryEntity.getId());
        });

        viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        binding.setLifecycleOwner(this);
        viewModel.getNewsItem(url).observe(this, news -> {
            binding.setNews(news);
            binding.executePendingBindings();
            setupTransition(news);
        });
        viewModel.getGalleryItem(url).observe(this, list -> {
            if (list == null || list.isEmpty()) {
                binding.setHasGallery(false);
                return;
            }
            binding.setHasGallery(true);
            galleryAdapter.submitList(list);
        });
        viewModel.getVideos(url).observe(this, videos -> {
            if (videos == null || videos.isEmpty()) {
                binding.setHasVideos(false);
                return;
            }
            binding.setHasVideos(true);
        });
        RecyclerView galleryList = binding.getRoot().findViewById(R.id.gallery);
        galleryList.setAdapter(galleryAdapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        galleryList.setLayoutManager(layoutManager);
        galleryList.setNestedScrollingEnabled(false);
    }

    private void setupTransition(News news) {
        Picasso.get()
                .load(news.getCoverPhotoUrl())
                .into(cover, new Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        startPostponedEnterTransition();
                    }
                });
    }
}

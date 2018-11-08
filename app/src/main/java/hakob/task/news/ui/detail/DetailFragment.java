package hakob.task.news.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import butterknife.BindView;
import butterknife.ButterKnife;
import hakob.task.news.R;
import hakob.task.news.common.InjectableFragment;
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

    @BindView(R.id.gallery)
    RecyclerView galleryListView;

    @BindView(R.id.video_list)
    RecyclerView videoListView;

    private String url;

    private DetailViewModel viewModel;
    private DetailsBinding binding;

    private GalleryAdapter galleryAdapter;
    private VideoAdapter videoAdapter;

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
        binding.setLifecycleOwner(this);
        ButterKnife.bind(this, binding.getRoot());

        Transition transition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_element_transition);
        transition.setDuration(225);
        transition.setInterpolator(new FastOutLinearInInterpolator());
        setSharedElementEnterTransition(transition);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        viewModel.getNewsItem(url).observe(this, news -> {
            binding.setNews(news);
            binding.executePendingBindings();
        });
        setupGallery();
        setupVideos();
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
            videoAdapter.submitList(videos);
            binding.setHasVideos(true);
        });
    }

    private void setupVideos() {
        videoAdapter = new VideoAdapter(video -> {
            ((MainActivity) requireActivity()).showVideo(video);
        });
        videoListView.setAdapter(videoAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        videoListView.setLayoutManager(layoutManager);
    }

    private void setupGallery() {
        galleryAdapter = new GalleryAdapter(gallery -> {
            ((MainActivity) requireActivity()).showFullscreenImage(gallery.getId());
        });
        galleryListView.setAdapter(galleryAdapter);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireContext(), FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        galleryListView.setLayoutManager(layoutManager);
        galleryListView.setNestedScrollingEnabled(false);
    }
}

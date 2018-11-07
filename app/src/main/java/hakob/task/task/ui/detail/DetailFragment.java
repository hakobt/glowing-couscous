package hakob.task.task.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hakob.task.task.R;
import hakob.task.task.common.InjectableFragment;
import hakob.task.task.databinding.DetailsBinding;

import static hakob.task.task.Constants.detailKey;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.ui
 */
public class DetailFragment extends InjectableFragment {

    @Inject
    ViewModelProvider.Factory factory;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryAdapter = new GalleryAdapter();

        viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        binding.setLifecycleOwner(this);
        viewModel.getNewsItem(url).observe(this, news -> {
            binding.setNews(news);
            binding.executePendingBindings();
        });
        viewModel.getGalleryItem(url).observe(this, galleryAdapter::submitList);
        viewModel.getVideos(url).observe(this, videoEntities -> {

        });
        RecyclerView galleryList = binding.getRoot().findViewById(R.id.gallery);
        galleryList.setAdapter(galleryAdapter);
        galleryList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
    }
}

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

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.ui
 */
public class DetailFragment extends InjectableFragment {

    @Inject
    ViewModelProvider.Factory factory;

    private int itemId;

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

    public static DetailFragment create(int id) {
        Bundle args = new Bundle();

        args.putInt("id", id);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null || !getArguments().containsKey("id")) {
            // details screen cannot exist without id
            throw new IllegalStateException("Cannot show details screen without id provided");
        }
        itemId = getArguments().getInt("id");
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
        viewModel.getNewsItem(itemId).observe(this, news -> {
            binding.setNews(news);
            binding.executePendingBindings();
        });
        viewModel.getGalleryItem(itemId).observe(this, galleryAdapter::submitList);
        viewModel.getVideos(itemId).observe(this, videoEntities -> {
        });

        RecyclerView galleryList = binding.getRoot().findViewById(R.id.gallery);
        galleryList.setAdapter(galleryAdapter);
        galleryList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
    }
}

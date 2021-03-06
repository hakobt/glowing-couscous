package hakob.task.news.ui.master;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import hakob.task.news.R;
import hakob.task.news.common.InjectableFragment;
import hakob.task.news.ui.MainActivity;

public class MasterFragment extends InjectableFragment {

    @Inject
    ViewModelProvider.Factory factory;

    @BindView(R.id.news_list)
    RecyclerView newsListView;

    private MasterViewModel viewModel;

    public MasterFragment() {
    }

    public static MasterFragment newInstance() {
        return new MasterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsListView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        NewsListAdapter adapter = new NewsListAdapter(newsEntity -> {
            viewModel.setNewsItemRead(newsEntity);
            ((MainActivity) MasterFragment.this.requireActivity()).showDetailsScreen(newsEntity);
        });
        newsListView.setAdapter(adapter);
        newsListView.addItemDecoration(new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL));
        viewModel = ViewModelProviders.of(this, factory).get(MasterViewModel.class);
        viewModel.getNews().observe(this, list -> {
            adapter.submitList(list);
            Log.d("News", "items");
        });
    }
}

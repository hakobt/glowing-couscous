package hakob.task.news.ui.master;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import hakob.task.news.R;
import hakob.task.news.common.ItemClickListener;
import hakob.task.news.data.News;
import hakob.task.news.databinding.ItemNewsBinding;

/**
 * Created by Hakob Tovmasyan on 10/29/18
 * Package hakob.task.task.ui.master
 */
public class NewsListAdapter extends ListAdapter<News, NewsItemViewHolder> {

    private static final DiffUtil.ItemCallback<News> diffCallback = new DiffUtil.ItemCallback<News>() {
        @Override
        public boolean areItemsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.getShareUrl().equals(newItem.getShareUrl());
        }

        @Override
        public boolean areContentsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final ItemClickListener<News> itemClickListener;

    public NewsListAdapter(ItemClickListener<News> itemClickListener) {
        super(diffCallback);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        NewsItemViewHolder holder = new NewsItemViewHolder(binding);
        holder.itemView.setOnClickListener(v -> {
            News news = getItem(holder.getAdapterPosition());
            itemClickListener.onItemClicked(news);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        News entity = getItem(position);
        holder.bind(entity);
    }
}

class NewsItemViewHolder extends RecyclerView.ViewHolder {

    private final ItemNewsBinding binding;

    @BindView(R.id.cover)
    public View cover;

    public NewsItemViewHolder(ItemNewsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        ButterKnife.bind(this, binding.getRoot());
    }

    public void bind(News news) {
        binding.setNews(news);
        binding.executePendingBindings();
    }
}


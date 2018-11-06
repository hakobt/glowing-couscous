package hakob.task.task.ui.master;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import hakob.task.task.data.NewsEntity;
import hakob.task.task.databinding.ItemNewsBinding;

/**
 * Created by Hakob Tovmasyan on 10/29/18
 * Package hakob.task.task.ui.master
 */
public class NewsListAdapter extends ListAdapter<NewsEntity, NewsItemViewHolder> {

    private static final DiffUtil.ItemCallback<NewsEntity> diffCallback = new DiffUtil.ItemCallback<NewsEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsEntity oldItem, @NonNull NewsEntity newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsEntity oldItem, @NonNull NewsEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final ListItemClickListener itemClickListener;

    public NewsListAdapter(ListItemClickListener itemClickListener) {
        super(diffCallback);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        NewsItemViewHolder holder = new NewsItemViewHolder(binding);
        holder.itemView.setOnClickListener(v -> {
            NewsEntity newsEntity = getItem(holder.getAdapterPosition());
            itemClickListener.onItemClicked(newsEntity);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    interface ListItemClickListener {
        void onItemClicked(NewsEntity newsEntity);
    }
}

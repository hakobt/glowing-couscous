package hakob.task.task.ui.master;

import androidx.recyclerview.widget.RecyclerView;
import hakob.task.task.data.NewsEntity;
import hakob.task.task.databinding.ItemNewsBinding;

/**
 * Created by Hakob Tovmasyan on 10/29/18
 * Package hakob.task.task.ui.master
 */
public class NewsItemViewHolder extends RecyclerView.ViewHolder {

    private final ItemNewsBinding binding;

    public NewsItemViewHolder(ItemNewsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(NewsEntity item) {
        binding.setNewsEntity(item);
        binding.executePendingBindings();
    }
}

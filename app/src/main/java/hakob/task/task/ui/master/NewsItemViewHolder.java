package hakob.task.task.ui.master;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import hakob.task.task.R;
import hakob.task.task.data.NewsEntity;
import hakob.task.task.databinding.ItemNewsBinding;

/**
 * Created by Hakob Tovmasyan on 10/29/18
 * Package hakob.task.task.ui.master
 */
public class NewsItemViewHolder extends RecyclerView.ViewHolder {

    private final ItemNewsBinding binding;

    @BindView(R.id.cover)
    public View cover;

    public NewsItemViewHolder(ItemNewsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        ButterKnife.bind(this, binding.getRoot());
    }

    public void bind(NewsEntity item) {
        binding.setNewsEntity(item);
        binding.executePendingBindings();
    }
}

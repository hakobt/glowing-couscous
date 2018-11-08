package hakob.task.news.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import hakob.task.news.common.ItemClickListener;
import hakob.task.news.data.Video;
import hakob.task.news.databinding.ItemVideoBinding;

/**
 * Created by Hakob Tovmasyan on 11/8/18
 * Package hakob.task.news.ui.detail
 */
public class VideoAdapter extends ListAdapter<Video, VideoViewHolder> {

    private static final DiffUtil.ItemCallback<Video> DIFF_CALBACK = new DiffUtil.ItemCallback<Video>() {
        @Override
        public boolean areItemsTheSame(@NonNull Video oldItem, @NonNull Video newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Video oldItem, @NonNull Video newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final ItemClickListener<Video> itemClickListener;

    public VideoAdapter(ItemClickListener<Video> itemClickListener) {
        super(DIFF_CALBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideoBinding binding =
                ItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(binding);
        videoViewHolder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClicked(getItem(videoViewHolder.getAdapterPosition()));
        });
        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}

class VideoViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final ItemVideoBinding binding;

    public VideoViewHolder(@NonNull ItemVideoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Video video) {
        binding.setVideo(video);
        binding.executePendingBindings();
    }
}

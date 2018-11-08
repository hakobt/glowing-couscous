package hakob.task.news.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import hakob.task.news.data.Gallery;
import hakob.task.news.databinding.ItemGalleryBinding;

/**
 * Created by Hakob Tovmasyan on 11/7/18
 * Package hakob.task.task.ui.detail
 */
public class GalleryAdapter extends ListAdapter<Gallery, ImageViewHolder> {

    private static final DiffUtil.ItemCallback<Gallery> diffCallback = new DiffUtil.ItemCallback<Gallery>() {
        @Override
        public boolean areItemsTheSame(@NonNull Gallery oldItem, @NonNull Gallery newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Gallery oldItem, @NonNull Gallery newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final ListItemClickListener listItemClickListener;

    public GalleryAdapter(ListItemClickListener listItemClickListener) {
        super(diffCallback);
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGalleryBinding binding =
                ItemGalleryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(binding);
        imageViewHolder.itemView.setOnClickListener(v -> {
            listItemClickListener.onItemClicked(getItem(imageViewHolder.getAdapterPosition()));
        });
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    interface ListItemClickListener {
        void onItemClicked(Gallery gallery);
    }
}

class ImageViewHolder extends RecyclerView.ViewHolder {

    private ItemGalleryBinding itemGalleryBinding;

    public ImageViewHolder(ItemGalleryBinding itemGalleryBinding) {
        super(itemGalleryBinding.getRoot());
        this.itemGalleryBinding = itemGalleryBinding;
    }

    public void bind(Gallery gallery) {
        itemGalleryBinding.setGallery(gallery);
        itemGalleryBinding.executePendingBindings();
    }
}

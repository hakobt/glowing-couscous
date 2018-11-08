package hakob.task.task.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import hakob.task.task.data.GalleryEntity;
import hakob.task.task.databinding.ItemGalleryBinding;

/**
 * Created by Hakob Tovmasyan on 11/7/18
 * Package hakob.task.task.ui.detail
 */
public class GalleryAdapter extends ListAdapter<GalleryEntity, ImageViewHolder> {

    private static final DiffUtil.ItemCallback<GalleryEntity> diffCallback = new DiffUtil.ItemCallback<GalleryEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull GalleryEntity oldItem, @NonNull GalleryEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull GalleryEntity oldItem, @NonNull GalleryEntity newItem) {
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
        void onItemClicked(GalleryEntity galleryEntity);
    }
}

class ImageViewHolder extends RecyclerView.ViewHolder {

    private ItemGalleryBinding itemGalleryBinding;

    public ImageViewHolder(ItemGalleryBinding itemGalleryBinding) {
        super(itemGalleryBinding.getRoot());
        this.itemGalleryBinding = itemGalleryBinding;
    }

    public void bind(GalleryEntity galleryEntity) {
        itemGalleryBinding.setGallery(galleryEntity);
        itemGalleryBinding.executePendingBindings();
    }
}

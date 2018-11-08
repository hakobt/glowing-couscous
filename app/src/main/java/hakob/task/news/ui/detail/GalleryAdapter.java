package hakob.task.news.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayoutManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import hakob.task.news.R;
import hakob.task.news.common.ItemClickListener;
import hakob.task.news.data.Gallery;
import hakob.task.news.databinding.ItemGalleryBinding;

/**
 * Created by Hakob Tovmasyan on 11/7/18
 * Package hakob.task.task.ui.detail
 */
public class GalleryAdapter extends ListAdapter<Gallery, GalleryViewHolder> {

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
    private final ItemClickListener<Gallery> itemClickListener;

    public GalleryAdapter(ItemClickListener<Gallery> itemClickListener) {
        super(diffCallback);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGalleryBinding binding =
                ItemGalleryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        GalleryViewHolder galleryViewHolder = new GalleryViewHolder(binding);
        galleryViewHolder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClicked(getItem(galleryViewHolder.getAdapterPosition()));
        });
        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}

class GalleryViewHolder extends RecyclerView.ViewHolder {

    private ItemGalleryBinding itemGalleryBinding;

    private ImageView cover;

    public GalleryViewHolder(ItemGalleryBinding itemGalleryBinding) {
        super(itemGalleryBinding.getRoot());
        this.itemGalleryBinding = itemGalleryBinding;
        cover = itemGalleryBinding.getRoot().findViewById(R.id.cover);
    }

    public void bind(Gallery gallery) {
        itemGalleryBinding.setGallery(gallery);
        ViewGroup.LayoutParams lp = cover.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(1.0f);
        }
        itemGalleryBinding.executePendingBindings();
    }
}

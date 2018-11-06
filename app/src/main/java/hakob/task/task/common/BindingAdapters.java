package hakob.task.task.common;

import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.databinding.BindingAdapter;

/**
 * Created by Hakob Tovmasyan on 11/7/18
 * Package hakob.task.task.common
 */
public class BindingAdapters {

    @BindingAdapter("android:url")
    public static void loadImage(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }

    @BindingAdapter("android:date")
    public static void setDate(TextView textView, long timestamp) {
        if (timestamp == 0) return;
        Date date = new Date(timestamp * 1000);

        Log.d("TimeStamp", date.toString() + " ");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        String displayDate = formatter.format(date);

        textView.setText(displayDate);
    }

    @BindingAdapter("android:formattedText")
    public static void setFormattedText(TextView textView, String text) {
        if (text == null) return;
        textView.setText(Html.fromHtml(text));
    }

}

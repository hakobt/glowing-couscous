package hakob.task.task.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.data.response
 */
public class FeedResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("errors")
    private List<String> errors;
    @SerializedName("data")
    private List<NewsItemResponse> data;

    public boolean isSuccess() {
        return success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<NewsItemResponse> getData() {
        return data;
    }
}

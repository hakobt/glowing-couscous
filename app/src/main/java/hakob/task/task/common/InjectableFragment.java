package hakob.task.task.common;

import android.content.Context;

import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Hakob Tovmasyan on 10/28/18
 * Package hakob.task.task.common
 */
public abstract class InjectableFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}

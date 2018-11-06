package hakob.task.task.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import dagger.android.AndroidInjection;

/**
 * Created by Hakob Tovmasyan on 10/28/18
 * Package hakob.task.task.common
 */
public abstract class InjectableAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}

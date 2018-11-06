package hakob.task.task.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import hakob.task.task.R;

/**
 * Created by Hakob Tovmasyan on 10/29/18
 * Package hakob.task.task.ui
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if (isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

package hakob.task.task.di;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Hakob Tovmasyan on 10/28/18
 * Package hakob.task.task.di
 */

@Retention(RUNTIME)
@Target({METHOD, FIELD})
@MapKey
public @interface ViewModelKey {

    Class<? extends ViewModel> value();
}

package hakob.task.task.di;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hakob.task.task.BuildConfig;
import hakob.task.task.api.NewsApi;
import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hakob Tovmasyan on 10/27/18
 * Package hakob.task.task.di
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public NewsApi newsApi(Retrofit retrofit) {
        return retrofit.create(NewsApi.class);
    }

    @Provides
    @Singleton
    public Picasso picasso(Context context) {
        Picasso picasso = new Picasso.Builder(context)
                .loggingEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }

    @Provides
    @Singleton
    public OkHttpClient okHttpClient(
            HttpLoggingInterceptor httpLoggingInterceptor,
            Context context
    ) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
                .cache(new Cache(context.getCacheDir(), 100 * 1024 * 1024))
                .build();
    }

    @Provides
    @Singleton
    public Retrofit retrofit(
            Gson gson,
            OkHttpClient okHttpClient,
            RxJava2CallAdapterFactory rxJava2CallAdapterFactory
    ) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build();
    }

    @Provides
    @Singleton
    public Gson gson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
            Log.d("OkHttp", message);
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    public RxJava2CallAdapterFactory rxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }
}

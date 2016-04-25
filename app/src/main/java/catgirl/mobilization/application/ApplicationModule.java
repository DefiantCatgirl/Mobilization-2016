package catgirl.mobilization.application;

import android.content.Context;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import catgirl.mobilization.data.CachedDataProvider;
import dagger.Module;
import dagger.Provides;
import catgirl.mobilization.data.network.ServerApi;
import catgirl.mobilization.data.realm.RealmProvider;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class ApplicationModule {
    @Provides
    @Singleton
    public RealmProvider provideRealmProvider() {
        return new RealmProvider();
    }

    @Provides
    public Context provideApplicationContext() {
        return Application.getContextOfApplication();
    }

    @Provides
    public ServerApi provideApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.apiEndpoint)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(ServerApi.class);
    }

    @Provides
    public CachedDataProvider dataProvider(RealmProvider realmProvider, ServerApi api) {
        return new CachedDataProvider(realmProvider, api);
    }
}

package catgirl.mobilization.application;

import android.content.Context;
import android.util.Log;

import com.yandex.metrica.YandexMetrica;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.mapsvision.mobilization.R;

public class Application extends android.app.Application {

    private static Application appContext;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;

        // Database configuration
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .schemaVersion(1)
                .migration((realm, oldVersion, newVersion) -> {
                    Log.v("Realm", "Migrating: " + oldVersion + " -> " + newVersion);

                    if (oldVersion == 0) {
                        // Do nothing
                    }
                })
                .build();

        Realm.setDefaultConfiguration(config);

        // Migrations

        // Dagger component initialization
        applicationComponent = DaggerApplicationComponent.builder().build();

        // Analytics
        YandexMetrica.activate(getApplicationContext(), getString(R.string.metrics_token));
        YandexMetrica.setSessionTimeout(600);
        YandexMetrica.setCollectInstalledApps(false);
        YandexMetrica.setTrackLocationEnabled(false);
    }

    public static Context getContextOfApplication() {
        return appContext.getApplicationContext();
    }

    public static ApplicationComponent getApplicationComponent() {
        return appContext.applicationComponent;
    }
}

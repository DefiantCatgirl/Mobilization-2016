package catgirl.mobilization.application;

import javax.inject.Singleton;

import catgirl.mobilization.activity.route.ArtistDetailActivityModule;
import dagger.Component;
import catgirl.mobilization.activity.main.MainActivityComponent;
import catgirl.mobilization.activity.main.MainActivityModule;
import catgirl.mobilization.activity.route.ArtistDetailActivityComponent;
import catgirl.mobilization.data.realm.RealmProvider;

@Singleton
@Component(modules = {
        ApplicationModule.class,
})
public interface ApplicationComponent {
    // For those odd cases where it's tiresome to bother with injections
    RealmProvider getRealmProvider();

    // Activity scoped subcomponents
    MainActivityComponent plus(MainActivityModule module);
    ArtistDetailActivityComponent plus(ArtistDetailActivityModule module);

    // Fragment scoped subcomponents
}

package catgirl.mobilization.activity.main.fragments.artists;

import catgirl.mobilization.activity.main.fragments.artists.data.ArtistsProvider;
import catgirl.mobilization.activity.main.fragments.artists.presenter.ArtistsPresenter;
import catgirl.mobilization.data.CachedDataProvider;
import catgirl.mobilization.data.network.ServerApi;
import dagger.Module;
import dagger.Provides;

@Module
public class ArtistsModule {
    @Provides
    public ArtistsProvider getProvider(CachedDataProvider dataProvider) {
        return new ArtistsProvider(dataProvider);
    }

    @Provides
    public ArtistsPresenter getPresenter(ArtistsProvider provider) {
        return new ArtistsPresenter(provider);
    }
}

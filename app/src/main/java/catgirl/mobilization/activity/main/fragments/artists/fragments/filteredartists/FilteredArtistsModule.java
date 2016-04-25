package catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists;

import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.data.FilteredArtistsProvider;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.presenter.FilteredArtistsPresenterFactory;
import catgirl.mobilization.data.CachedDataProvider;
import dagger.Module;
import dagger.Provides;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.presenter.FilteredArtistsPresenter;
import catgirl.mobilization.data.network.ServerApi;

@Module
public class FilteredArtistsModule {
    @Provides
    FilteredArtistsProvider provideProvider(CachedDataProvider dataProvider) {
        return new FilteredArtistsProvider(dataProvider);
    }

    @Provides
    FilteredArtistsPresenterFactory provideFactory(FilteredArtistsProvider provider) {
        return new FilteredArtistsPresenterFactory(provider);
    }
}

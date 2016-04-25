package catgirl.mobilization.activity.route.fragment;

import catgirl.mobilization.activity.route.fragment.data.ArtistDetailProvider;
import catgirl.mobilization.activity.route.fragment.presenter.ArtistDetailPresenterFactory;
import catgirl.mobilization.data.CachedDataProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class ArtistDetailFragmentModule {
    @Provides
    public ArtistDetailProvider provideProvider(CachedDataProvider dataProvider) {
        return new ArtistDetailProvider(dataProvider);
    }

    @Provides
    public ArtistDetailPresenterFactory providePresenter(ArtistDetailProvider provider) {
        return new ArtistDetailPresenterFactory(provider);
    }
}

package catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.presenter;

import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.data.FilteredArtistsProvider;
import catgirl.mobilization.data.network.ServerApi;

public class FilteredArtistsPresenterFactory {
    private final FilteredArtistsProvider provider;

    public FilteredArtistsPresenterFactory(FilteredArtistsProvider provider) {
        this.provider = provider;
    }

    public FilteredArtistsPresenter createPresenter(String tag) {
        return new FilteredArtistsPresenter(provider, tag);
    }
}

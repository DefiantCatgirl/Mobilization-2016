package catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.data;

import catgirl.mobilization.data.CachedDataProvider;
import catgirl.mobilization.data.model.Artist;
import catgirl.mobilization.activity.common.data.model.LazyLoadResult;
import catgirl.mobilization.data.network.ServerApi;
import rx.Observable;

public class FilteredArtistsProvider {
    private final CachedDataProvider dataProvider;

    public FilteredArtistsProvider(CachedDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    // TODO: it should be a query to Realm but no time left
    public Observable<LazyLoadResult<Artist>> getData(String tag) {
        return dataProvider.getData()
                .concatMap(Observable::from)
                .filter(artist -> tag == null || artist.genres.contains(tag))
                .toList()
                .map(artists -> new LazyLoadResult<>(artists, true));
    }

    public Observable<LazyLoadResult<Artist>> refreshData(String tag) {
        return dataProvider.getFreshData()
                .concatMap(Observable::from)
                .filter(artist -> tag == null || artist.genres.contains(tag))
                .toList()
                .map(artists -> new LazyLoadResult<>(artists, true));
    }
}

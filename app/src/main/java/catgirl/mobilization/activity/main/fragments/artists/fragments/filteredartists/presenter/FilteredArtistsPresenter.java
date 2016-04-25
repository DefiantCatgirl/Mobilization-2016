package catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.presenter;

import android.widget.ImageView;

import catgirl.mobilization.data.model.Artist;
import catgirl.mobilization.activity.common.data.model.LazyLoadResult;
import catgirl.mobilization.activity.common.presenter.RefreshReloadLazyLoadPresenter;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.data.FilteredArtistsProvider;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.view.FilteredArtistsView;
import rx.Observable;

public class FilteredArtistsPresenter extends RefreshReloadLazyLoadPresenter<Artist, FilteredArtistsView> {

    private final FilteredArtistsProvider provider;
    private final String tag;

    public FilteredArtistsPresenter(FilteredArtistsProvider provider, String tag) {
        this.provider = provider;
        this.tag = tag;
    }

    @Override
    protected Observable<LazyLoadResult<Artist>> getMoreObservable() {
        return provider.getData(tag);
    }

    @Override
    protected Observable<LazyLoadResult<Artist>> getNewObservable() {
        return provider.refreshData(tag);
    }

    public void itemClicked(int position, ImageView view) {
        Artist artist = getItem(position);
        getView().switchToDetail(artist.id, artist.name, artist.cover.big, view);
    }
}

package catgirl.mobilization.activity.route.fragment.data;

import catgirl.mobilization.data.CachedDataProvider;
import catgirl.mobilization.data.model.Artist;
import rx.Observable;

public class ArtistDetailProvider {
    private final CachedDataProvider dataProvider;

    public ArtistDetailProvider(CachedDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public Observable<Artist> getArtist(int artistId) {
        return dataProvider.getArtist(artistId);
    }
}

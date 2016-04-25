package catgirl.mobilization.data;

import java.util.List;

import catgirl.mobilization.data.model.Artist;
import catgirl.mobilization.data.network.ServerApi;
import catgirl.mobilization.data.realm.RealmProvider;
import catgirl.mobilization.data.realm.model.RealmArtist;
import catgirl.mobilization.data.realm.model.RealmArtists;
import io.realm.Realm;
import rx.Observable;

/**
 * This class first tries to retrieve cached data and queries the server if it doesn't exist.
 * If necessary, it will only query for fresh data (e.g. pull to refresh)
 *
 * It's not caching the pictures - no time for that!
 */
public class CachedDataProvider {
    private final RealmProvider realmProvider;
    private final ServerApi api;

    public CachedDataProvider(RealmProvider realmProvider, ServerApi api) {
        this.realmProvider = realmProvider;
        this.api = api;
    }

    public Observable<List<Artist>> getData() {
        return Observable.concat(
                    getCachedData(),
                    getFreshData())
                .first(data -> data != null);
    }

    public Observable<List<Artist>> getCachedData() {
        return Observable.fromCallable(() -> {
            Realm realm = realmProvider.provideRealm();

            RealmArtists cache = realm.where(RealmArtists.class).findFirst();
            if (cache != null) {
                return cache.getImmutable();
            } else {
                return null;
            }
        });
    }

    public Observable<List<Artist>> getFreshData() {
        return api.getArtists()
                .doOnNext(artists -> {
                    Realm realm = realmProvider.provideRealm();
                    realm.beginTransaction();

                    RealmArtists cache = realm.where(RealmArtists.class).findFirst();
                    if (cache != null)
                        cache.removeFromRealm();
                    RealmArtists realmArtists = new RealmArtists(artists);
                    realm.copyToRealmOrUpdate(realmArtists);

                    realm.commitTransaction();
                    realm.close();
                });
    }

    public Observable<Artist> getArtist(int artistId) {
        return Observable.fromCallable(() -> {
            Realm realm = realmProvider.provideRealm();
            Artist artist = realm.where(RealmArtist.class)
                    .equalTo("id", artistId)
                    .findFirst()
                    .getImmutable();
            realm.close();
            return artist;
        });
    }
}

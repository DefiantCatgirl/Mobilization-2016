package catgirl.mobilization.activity.main.fragments.artists.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import catgirl.mobilization.data.CachedDataProvider;
import catgirl.mobilization.data.model.Artist;
import catgirl.mobilization.data.network.ServerApi;
import rx.Observable;

public class ArtistsProvider {
    private final CachedDataProvider provider;

    public ArtistsProvider(CachedDataProvider provider) {
        this.provider = provider;
    }

    // TODO: should be backed by a Realm query, but no time left and it's a small data set
    public Observable<List<String>> getData() {
        return provider.getData().map(artists -> {
            Set<String> tagSet = new HashSet<>();

            for (Artist artist : artists) {
                for (String tag : artist.genres) {
                    tagSet.add(tag);
                }
            }

            List<String> tags = new ArrayList<String>(tagSet);
            Collections.sort(tags);

            return tags;
        });
    }
}

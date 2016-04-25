package catgirl.mobilization.data.realm.model;

import java.util.ArrayList;
import java.util.List;

import catgirl.mobilization.data.model.Artist;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmArtists extends RealmObject {
    @PrimaryKey
    public int id = 1;

    public RealmList<RealmArtist> artists = new RealmList<>();

    public RealmArtists() {

    }

    public RealmArtists(List<Artist> artists) {
        for (Artist artist : artists) {
            this.artists.add(new RealmArtist(artist));
        }
    }

    public List<Artist> getImmutable() {
        List<Artist> artists = new ArrayList<>();
        for (RealmArtist realmArtist : this.artists) {
            artists.add(realmArtist.getImmutable());
        }
        return artists;
    }
}

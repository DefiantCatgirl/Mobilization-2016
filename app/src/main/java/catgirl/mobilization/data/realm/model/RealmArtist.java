package catgirl.mobilization.data.realm.model;

import catgirl.mobilization.data.model.Artist;
import catgirl.mobilization.data.model.Cover;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmArtist extends RealmObject {
    @PrimaryKey
    public int id;

    public String name;
    public RealmList<RealmGenre> genres = new RealmList<>();
    public int tracks;
    public int albums;
    public String link;
    public String description;
    public RealmCover cover;

    public RealmArtist() {

    }

    public RealmArtist(Artist artist) {
        id = artist.id;
        name = artist.name;
        for (String genre : artist.genres) {
            RealmGenre g = new RealmGenre();
            g.name = genre;
            genres.add(g);
        }
        tracks = artist.tracks;
        albums = artist.albums;
        link = artist.link;
        description = artist.description;
        cover = new RealmCover(artist.cover);
    }

    public Artist getImmutable() {
        Artist artist = new Artist();
        artist.id = id;
        artist.name = name;
        for (RealmGenre g : genres) {
            artist.genres.add(g.name);
        }
        artist.tracks = tracks;
        artist.albums = albums;
        artist.link = link;
        artist.description = description;
        artist.cover = cover.getImmutable();
        return artist;
    }
}

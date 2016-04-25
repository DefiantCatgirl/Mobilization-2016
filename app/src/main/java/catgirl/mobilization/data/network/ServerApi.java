package catgirl.mobilization.data.network;

import java.util.List;

import catgirl.mobilization.data.model.Artist;
import retrofit.http.GET;
import rx.Observable;

public interface ServerApi {
    @GET("/mobilization-2016/artists.json")
    Observable<List<Artist>> getArtists();
}

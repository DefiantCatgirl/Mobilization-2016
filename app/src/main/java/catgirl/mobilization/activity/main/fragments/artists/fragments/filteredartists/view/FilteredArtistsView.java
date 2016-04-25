package catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.view;

import android.widget.ImageView;

import catgirl.mobilization.data.model.Artist;
import catgirl.mobilization.activity.common.view.LazyLoadView;

public interface FilteredArtistsView extends LazyLoadView<Artist> {
    void switchToDetail(int id, String title, String coverUrl, ImageView view);
}

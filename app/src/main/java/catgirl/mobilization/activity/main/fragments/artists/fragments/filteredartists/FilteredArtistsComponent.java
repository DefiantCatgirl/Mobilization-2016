package catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists;

import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.view.FilteredArtistsFragment;
import dagger.Subcomponent;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.presenter.FilteredArtistsPresenter;

@Subcomponent(modules = {
        FilteredArtistsModule.class,
})
public interface FilteredArtistsComponent {
    void inject(FilteredArtistsFragment fragment);
}

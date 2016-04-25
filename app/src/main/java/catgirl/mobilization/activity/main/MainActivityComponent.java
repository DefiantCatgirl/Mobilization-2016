package catgirl.mobilization.activity.main;

import catgirl.mobilization.activity.main.fragments.artists.ArtistsComponent;
import catgirl.mobilization.activity.main.fragments.artists.ArtistsModule;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.FilteredArtistsComponent;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.FilteredArtistsModule;
import dagger.Subcomponent;

@Subcomponent(modules = {
        MainActivityModule.class,
})
public interface MainActivityComponent {
    void inject(MainActivity activity);

    FilteredArtistsComponent plus(FilteredArtistsModule filteredArtistsModule);
    ArtistsComponent plus(ArtistsModule artistsModule);
}

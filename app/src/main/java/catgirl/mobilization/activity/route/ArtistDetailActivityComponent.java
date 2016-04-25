package catgirl.mobilization.activity.route;

import dagger.Subcomponent;
import catgirl.mobilization.activity.route.fragment.ArtistDetailFragmentComponent;
import catgirl.mobilization.activity.route.fragment.ArtistDetailFragmentModule;

@Subcomponent(modules = {
        ArtistDetailActivityModule.class,
})
public interface ArtistDetailActivityComponent {
    void inject(ArtistDetailActivity activity);

    ArtistDetailFragmentComponent plus(ArtistDetailFragmentModule module);
}

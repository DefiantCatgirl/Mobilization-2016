package catgirl.mobilization.activity.route.fragment;

import catgirl.mobilization.activity.route.fragment.view.ArtistDetailFragment;
import dagger.Subcomponent;

@Subcomponent(modules = {
        ArtistDetailFragmentModule.class,
})
public interface ArtistDetailFragmentComponent {
    void inject(ArtistDetailFragment fragment);
}

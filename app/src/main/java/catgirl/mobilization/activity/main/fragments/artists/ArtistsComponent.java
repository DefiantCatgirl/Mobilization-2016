package catgirl.mobilization.activity.main.fragments.artists;

import catgirl.mobilization.activity.main.fragments.artists.presenter.ArtistsPresenter;
import catgirl.mobilization.activity.main.fragments.artists.view.ArtistsFragment;
import dagger.Subcomponent;

@Subcomponent(modules = {
        ArtistsModule.class,
})
public interface ArtistsComponent {
    void inject(ArtistsFragment fragment);

    ArtistsPresenter createPresenter();
}

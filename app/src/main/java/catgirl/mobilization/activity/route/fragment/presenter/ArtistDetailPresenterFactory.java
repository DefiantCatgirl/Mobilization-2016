package catgirl.mobilization.activity.route.fragment.presenter;

import catgirl.mobilization.activity.route.fragment.data.ArtistDetailProvider;

public class ArtistDetailPresenterFactory {
    private ArtistDetailProvider provider;

    public ArtistDetailPresenterFactory(ArtistDetailProvider provider) {
        this.provider = provider;
    }

    public ArtistDetailPresenter createPresenter(int artistId) {
        return new ArtistDetailPresenter(provider, artistId);
    }
}

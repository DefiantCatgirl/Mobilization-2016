package catgirl.mobilization.activity.route.fragment.presenter;

import android.os.Bundle;

import catgirl.mobilization.activity.route.fragment.data.ArtistDetailProvider;
import catgirl.mobilization.activity.route.fragment.view.ArtistDetailView;
import catgirl.mobilization.data.model.Artist;
import catgirl.mvp.implementations.BasePresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class ArtistDetailPresenter extends BasePresenter<ArtistDetailView> {
    private ArtistDetailProvider provider;
    private int artistId;
    private Subscription subscription;

    Artist artist;

    public ArtistDetailPresenter(ArtistDetailProvider provider, int artistId) {
        this.provider = provider;
        this.artistId = artistId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
    }

    @Override
    public void bindView(ArtistDetailView view) {
        super.bindView(view);

        if (artist != null)
            view.setHeader(artist);
    }

    private void getData() {
        if (subscription != null)
            return;

        subscription = provider.getArtist(artistId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            artist = result;
                            getView().setHeader(artist);
                            getView().showData(artist);
                            subscription = null;
                        },
                        error -> {
                            getView().showError();
                            subscription = null;
                        });
    }

    public void onBrowserClicked() {
        getView().goToUrl(artist.link);
    }
}

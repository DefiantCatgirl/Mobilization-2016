package catgirl.mobilization.activity.main.fragments.artists.presenter;

import android.os.Bundle;

import java.util.List;

import catgirl.mobilization.activity.main.fragments.artists.data.ArtistsProvider;
import catgirl.mobilization.activity.main.fragments.artists.view.ArtistsView;
import catgirl.mvp.implementations.BasePresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ArtistsPresenter extends BasePresenter<ArtistsView> {
    private final ArtistsProvider provider;
    private Subscription subscription;

    public List<String> items;
    boolean errorShown = false;

    public ArtistsPresenter(ArtistsProvider provider) {
        this.provider = provider;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
    }

    @Override
    public void bindView(ArtistsView view) {
        super.bindView(view);

        if (items != null) {
            getView().showTabs(items);
        }

        if (errorShown) {
            getView().showError();
        }
    }

    private void loadData() {
        if (subscription != null)
            return;

        subscription = provider.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        results -> {
                            items = results;
                            if (getView() != null)
                                getView().showTabs(results);

                            errorShown = false;
                            subscription = null;
                        },
                        error -> {
                            if (getView() != null)
                                getView().showError();

                            error.printStackTrace();

                            errorShown = true;
                            subscription = null;
                        });
    }


    public void reloadPressed() {
        if (getView() != null) {
            getView().showLoading();
        }

        loadData();
    }
}

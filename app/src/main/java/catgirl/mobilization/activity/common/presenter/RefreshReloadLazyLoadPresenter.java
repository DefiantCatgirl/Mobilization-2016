package catgirl.mobilization.activity.common.presenter;

import java.util.ArrayList;
import java.util.List;

import catgirl.mvp.implementations.BasePresenter;
import catgirl.mobilization.activity.common.data.model.LazyLoadResult;
import catgirl.mobilization.activity.common.view.LazyLoadView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class RefreshReloadLazyLoadPresenter<Model, View extends LazyLoadView<Model>> extends BasePresenter<View> {

    List<Model> items = new ArrayList<>();

    Subscription moreSubscription;
    Subscription newSubscription;

    boolean finished = false;
    boolean errorShown = false;

    @Override
    public void bindView(View view) {
        super.bindView(view);

        view.showExistingItems(items, finished);

//        if (newSubscription != null)
//            view.showLoadingNewItems();
        if (errorShown)
            view.showMoreItemsError(false);
    }

    public int getItemCount() {
        return items.size();
    }

    public Model getItem(int position) {
        return items.get(position);
    }

    protected abstract Observable<LazyLoadResult<Model>> getMoreObservable();
    protected abstract Observable<LazyLoadResult<Model>> getNewObservable();

    public void loadNew() {
        if (newSubscription != null)
            return;

        newSubscription = getNewObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            newSubscription = null;
                            moreSubscription = null;

                            items = new ArrayList<>(result.elements);
                            finished = result.finished;

                            errorShown = false;

                            if (getView() != null) {
                                getView().showExistingItems(items, finished);
                                getView().hideLoadingNewItems();
                            }

                        }, error -> {
                            error.printStackTrace();

                            newSubscription = null;

                            if (getView() != null)
                                getView().showNewItemsError();
                        }
                );
    }

    public void loadMore() {
        if (moreSubscription != null)
            return;

        errorShown = false;

        moreSubscription = getMoreObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            moreSubscription = null;

                            items.addAll(result.elements);
                            finished = result.finished;

                            if (getView() != null)
                                getView().showMoreItems(result.elements, finished);
                        }, error -> {
                            error.printStackTrace();

                            moreSubscription = null;

                            if (getView() != null)
                                getView().showMoreItemsError(true);
                        });
    }
}

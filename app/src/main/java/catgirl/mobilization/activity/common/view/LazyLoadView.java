package catgirl.mobilization.activity.common.view;

import java.util.List;

public interface LazyLoadView<T> {
    void showExistingItems(List<T> items, boolean finished);
    void showMoreItems(List<T> items, boolean finished);
    void showNewItems(List<T> items);
    void showLoadingNewItems();
    void hideLoadingNewItems();
    void showMoreItemsError(boolean showToast);
    void showNewItemsError();
    void showInitialState();
    void showLoadingMoreItems();
    void updateExistingItems(List<T> items);
}

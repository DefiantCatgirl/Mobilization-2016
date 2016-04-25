package catgirl.mobilization.activity.main.fragments.artists.view;

import java.util.List;

public interface ArtistsView {
    void showTabs(List<String> tabs);
    void showError();
    void showLoading();
}

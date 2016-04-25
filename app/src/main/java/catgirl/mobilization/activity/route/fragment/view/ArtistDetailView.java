package catgirl.mobilization.activity.route.fragment.view;

import catgirl.mobilization.data.model.Artist;

public interface ArtistDetailView {
    void setHeader(Artist artist);

    void showError();
    void showData(Artist artist);

    void goToUrl(String url);
}

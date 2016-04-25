package catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.view;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import javax.inject.Inject;

import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.FilteredArtistsComponent;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.presenter.FilteredArtistsPresenter;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.presenter.FilteredArtistsPresenterFactory;
import catgirl.mobilization.activity.route.fragment.view.ArtistDetailFragment;
import ru.mapsvision.mobilization.R;
import catgirl.mobilization.data.model.Artist;
import catgirl.mobilization.activity.common.view.LazyLoadFragment;
import catgirl.mobilization.activity.main.MainActivityModule;
import catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.FilteredArtistsModule;
import catgirl.mobilization.activity.route.ArtistDetailActivity;
import catgirl.mobilization.application.Application;

public class FilteredArtistsFragment
        extends LazyLoadFragment<Artist, FilteredArtistsPresenter, FilteredArtistsComponent>
        implements FilteredArtistsView {

    public final static String FILTER_TAG = "filter-tag";

    @Inject FilteredArtistsPresenterFactory factory;

    // Component

    @Override
    protected FilteredArtistsComponent createComponent() {
        return Application.getApplicationComponent().plus(new MainActivityModule()).plus(new FilteredArtistsModule());
    }

    @Override
    protected void onComponentCreated() {
        getComponent().inject(this);
    }

    @Override
    protected FilteredArtistsPresenter createPresenter() {
        return factory.createPresenter(getArguments() != null ?
                getArguments().getString(FILTER_TAG, null) : null);
    }

    // View

    @Override
    protected int getItemCount() {
        return getPresenter().getItemCount();
    }

    @Override
    protected long getItemId(int position) {
        return 0;
    }

    @Override
    protected int getItemViewType(int position) {
        return 0;
    }

    @Override
    protected void loadNew() {
        getPresenter().loadNew();
    }

    @Override
    protected void loadMore() {
        getPresenter().loadMore();
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new FilteredArtistsItemViewHolder(getActivity().getLayoutInflater().inflate(
                R.layout.item_artist, parent, false));
    }

    @Override
    protected void bindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FilteredArtistsItemViewHolder) holder).bind(getPresenter().getItem(position), view -> getPresenter().itemClicked(position, view));
    }

    // TODO
    @Override
    protected RecyclerView.ViewHolder createErrorViewHolder(ViewGroup parent) {
        return new RecyclerView.ViewHolder(new View(parent.getContext())) {};
    }

    // Shouldn't happen
    @Override
    protected View getEmptyMessage(ViewGroup parent) {
        View emptyMessage = getActivity().getLayoutInflater().inflate(R.layout.common_empty, parent, false);

        emptyMessage.findViewById(R.id.Common_Empty_Button)
                .setOnClickListener(button -> {
                    getPresenter().loadMore();
                    showInitialState();
                });

        return emptyMessage;
    }

    @Override
    protected void showMoreItemsErrorToast() {
        Toast.makeText(getContext(), getString(R.string.error_artists_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void showNewItemsErrorToast() {
        Toast.makeText(getContext(), getString(R.string.error_artists_reload_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected boolean hasStableIds() {
        return false;
    }

    @Override
    protected int getSpanCount() {
        return 1;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_refreshable_recycler_padding;
    }

    @Override
    public void switchToDetail(int id, String name, String coverUrl, ImageView cover) {
        Intent intent = new Intent(getActivity(), ArtistDetailActivity.class);
        intent.putExtra(ArtistDetailFragment.ARTIST_ID, id);
        intent.putExtra(ArtistDetailFragment.ARTIST_NAME, name);
        intent.putExtra(ArtistDetailFragment.ARTIST_COVER_URL, coverUrl);
//        ActivityOptionsCompat options = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(getActivity(), cover, "cover");
//        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        startActivity(intent);
    }
}

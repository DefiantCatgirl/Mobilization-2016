package catgirl.mobilization.activity.route.fragment.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import catgirl.mobilization.activity.route.fragment.ArtistDetailFragmentComponent;
import catgirl.mobilization.activity.route.fragment.presenter.ArtistDetailPresenter;
import catgirl.mobilization.activity.route.fragment.presenter.ArtistDetailPresenterFactory;
import catgirl.mobilization.data.model.Artist;
import catgirl.mvp.implementations.BasePresenterFragment;
import ru.mapsvision.mobilization.R;
import catgirl.mobilization.activity.route.ArtistDetailActivity;
import catgirl.mobilization.activity.route.ArtistDetailActivityModule;
import catgirl.mobilization.activity.route.fragment.ArtistDetailFragmentModule;
import catgirl.mobilization.application.Application;

public class ArtistDetailFragment
        extends BasePresenterFragment<ArtistDetailPresenter, ArtistDetailFragmentComponent>
        implements ArtistDetailView {

    final public static String ARTIST_NAME = "artist-name";
    final public static String ARTIST_ID = "artist-id";
    final public static String ARTIST_COVER_URL = "artist-cover-url";

    @Bind(R.id.CollapsingToolbar) CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.Toolbar) Toolbar toolbar;
    @Bind(R.id.ToolbarBackground) ImageView background;
    @Bind(R.id.Artist_Description) TextView description;
    @Bind(R.id.Artist_Genres) TextView genres;
    @Bind(R.id.Artist_Songs) TextView songs;


    @Inject
    ArtistDetailPresenterFactory presenterFactory;

    @Override
    protected ArtistDetailFragmentComponent createComponent() {
        return Application.getApplicationComponent()
                .plus(new ArtistDetailActivityModule())
                .plus(new ArtistDetailFragmentModule());
    }

    @Override
    protected void onComponentCreated() {
        getComponent().inject(this);
    }

    @Override
    protected ArtistDetailPresenter createPresenter() {
        return presenterFactory.createPresenter(getArguments().getInt(ARTIST_ID));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist, container, false);

        ButterKnife.bind(this, view);

        if (getArguments().containsKey(ARTIST_NAME))
            collapsingToolbar.setTitle(getArguments().getString(ARTIST_NAME));

        if (getArguments().containsKey(ARTIST_COVER_URL)) {
            Picasso.with(getContext()).load(getArguments().getString(ARTIST_COVER_URL)).into(background, new Callback() {
                @Override
                public void onSuccess() {
                    ActivityCompat.startPostponedEnterTransition(getActivity());
                }

                @Override
                public void onError() {
                    ActivityCompat.startPostponedEnterTransition(getActivity());
                }
            });
        }

        ((ArtistDetailActivity) getActivity()).setToolbar(toolbar);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.artist_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuItem_Browser:
                getPresenter().onBrowserClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setHeader(Artist artist) {
        if (artist.cover.big != null) {
            Picasso.with(getContext()).load(artist.cover.big).into(background);
        }
        collapsingToolbar.setTitle(artist.name);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.error_artists_load_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(Artist artist) {
        genres.setText(TextUtils.join(", ", artist.genres));

        // Locale-dependent quantity text formatting
        songs.setText(songs.getContext().getString(R.string.dot_joined_string,
                songs.getContext().getResources().getQuantityString(
                        R.plurals.number_of_albums, artist.albums, artist.albums),
                songs.getContext().getResources().getQuantityString(
                        R.plurals.number_of_songs, artist.tracks, artist.tracks)));

        // For some reason many descriptions start with a lower case letter so fixing it here
        // TODO: better fix it elsewhere (e.g. in data layer)
        String desc = artist.description.substring(0, 1).toUpperCase() + artist.description.substring(1);

        description.setText(desc);
    }

    @Override
    public void goToUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}

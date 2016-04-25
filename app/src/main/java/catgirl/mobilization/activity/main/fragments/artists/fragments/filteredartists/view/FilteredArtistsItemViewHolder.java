package catgirl.mobilization.activity.main.fragments.artists.fragments.filteredartists.view;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.mapsvision.mobilization.R;
import catgirl.mobilization.data.model.Artist;

public class FilteredArtistsItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.Item_Title) TextView title;
    @Bind(R.id.Item_Genres) TextView genres;
    @Bind(R.id.Item_Songs) TextView songs;
    @Bind(R.id.Item_Cover) ImageView cover;

    private ActionDelegate delegate;

    public FilteredArtistsItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(view -> {
            if (delegate != null)
                delegate.onClick(cover);
        });
    }

    public void bind(Artist item, ActionDelegate delegate) {
        this.delegate = delegate;

        title.setText(item.name);
        genres.setText(TextUtils.join(", ", item.genres));

        // Locale-dependent quantity text formatting
        songs.setText(songs.getContext().getString(R.string.comma_joined_string,
                        songs.getContext().getResources().getQuantityString(
                                R.plurals.number_of_albums, item.albums, item.albums),
                        songs.getContext().getResources().getQuantityString(
                                R.plurals.number_of_songs, item.tracks, item.tracks)));

        cover.setImageBitmap(null);
        Picasso.with(cover.getContext()).cancelRequest(cover);

        Picasso.with(cover.getContext())
                .load(item.cover.small)
                .into(cover);
    }

    public interface ActionDelegate {
        void onClick(ImageView view);
    }
}

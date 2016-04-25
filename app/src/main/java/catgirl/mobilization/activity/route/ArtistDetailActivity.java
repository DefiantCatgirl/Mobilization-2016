package catgirl.mobilization.activity.route;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;

import catgirl.mobilization.activity.route.fragment.view.ArtistDetailFragment;
import catgirl.mvp.implementations.BaseComponentActivity;
import ru.mapsvision.mobilization.R;
import catgirl.mobilization.application.Application;

public class ArtistDetailActivity extends BaseComponentActivity<ArtistDetailActivityComponent> {
    @Override
    public ArtistDetailActivityComponent createComponent() {
        return Application.getApplicationComponent().plus(new ArtistDetailActivityModule());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artist);

        getComponent().inject(this);

        if(getIntent().getExtras() == null) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            ActivityCompat.postponeEnterTransition(this);
            ArtistDetailFragment fragment = new ArtistDetailFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}

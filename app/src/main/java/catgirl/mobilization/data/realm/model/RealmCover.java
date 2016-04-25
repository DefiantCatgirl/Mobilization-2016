package catgirl.mobilization.data.realm.model;

import catgirl.mobilization.data.model.Cover;
import io.realm.RealmObject;

public class RealmCover extends RealmObject {
    public String small;
    public String big;

    public RealmCover() {

    }

    public RealmCover(Cover cover) {
        big = cover.big;
        small = cover.small;
    }

    public Cover getImmutable() {
        return new Cover(small, big);
    }
}

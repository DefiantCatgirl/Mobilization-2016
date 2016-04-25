package catgirl.mobilization.data.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmGenre extends RealmObject{
    @PrimaryKey
    String name;
}

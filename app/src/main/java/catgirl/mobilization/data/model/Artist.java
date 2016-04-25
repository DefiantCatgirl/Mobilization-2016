package catgirl.mobilization.data.model;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    public int id;
    public String name;
    public List<String> genres = new ArrayList<>();
    public int tracks;
    public int albums;
    public String link;
    public String description;
    public Cover cover;
}

package catgirl.mobilization.data.settings;

public interface SettingsProvider<T> {
    void commit(T model);
    T retrieve();
}

public interface Element <T extends Entity>{
    void accept(Vizitor<T> vizitor);
}

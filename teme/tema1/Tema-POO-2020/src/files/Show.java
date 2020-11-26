package files;

import fileio.ActionInputData;
import fileio.ShowInput;

import java.util.ArrayList;

/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class Show {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    private int favCnt = 0;
    private int viewCnt = 0;

    public Show(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }
    public Show(final ShowInput original) {
        this.title = original.getTitle();
        this.year = original.getYear();
        this.cast = original.getCast();
        this.genres = original.getGenres();
    }
    public Show() {
        this.title = null;
        this.year = 0;
        this.cast = null;
        this.genres = null;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }
    /**
     * The method is overridden in the classes that inherit Show (Movie and Serial)
     */
    public double getRating() {
        return 0;
    }
    /**
     * The method is overridden in the classes that inherit Show (Movie and Serial)
     */
    public int getDuration() {
        return 0;
    }
    /**
     * Getter for favCnt used to count how many times a show appears on users' favorite list
     */
    public int getFavCnt() {
        return favCnt;
    }
    /**
     * Setter for favCnt
     */
    public void setFavCnt(final int favCnt) {
        this.favCnt = favCnt;
    }
    /**
     * Getter for viewCnt used to count how many times a show has been seen
     */
    public int getViewCnt() {
        return viewCnt;
    }
    /**
     * Setter for viewCnt
     */
    public void setViewCnt(final int cnt) {
        this.viewCnt = cnt;
    }
    /**
     * The method calculates how many times each video from videos has been seen
     */
    public static void calculateViewCnt(final ArrayList<Show> videos, final ModifiableDB dataBase) {
        for (Show video
                : videos) {
            int cnt = 0;
            for (User user
                    : dataBase.getUsers()) {
                if (user.getHistory().containsKey(video.getTitle())) {
                    cnt += user.getHistory().get(video.getTitle());
                }
            }
            video.setViewCnt(cnt);
        }
    }
    /**
     * The method calculates how many times each video from videos appears on users' favorite list
     */
    public static void calculateFavCnt(final ArrayList<Show> videos, final ModifiableDB dataBase) {
        for (Show mv
                : videos) {
            int cnt = 0;
            for (User user
                    : dataBase.getUsers()) {
                if (user.getFavoriteMovies().contains(mv.getTitle())) {
                    cnt++;
                }
            }
            mv.setFavCnt(cnt);
        }
    }
    /**
     * The method is overridden in the classes that inherit Show (Movie and Serial)
     */
    public ArrayList<Show> checkFilters(final ModifiableDB dataBase, final ActionInputData action) {
        ArrayList<Show> suitableMovies = new ArrayList<>();
        return suitableMovies;
    }
    /**
     * The method checks if a show fits with all the provided filters
     */
    public boolean check(final Show video, final ActionInputData action) {
        Integer yr = video.getYear();
        if (action.getFilters().get(0) != null
                && action.getFilters().get(0).get(0) != null
                && action.getFilters().get(0).get(0).compareTo(yr.toString()) != 0) {
            return false;
        } else if (action.getFilters().get(1) != null
                && action.getFilters().get(1).get(0) != null) {
            for (String genre
                    : action.getFilters().get(1)) {
                if (!video.getGenres().contains(genre)) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * The method sorts an array of shows based on views and titles
     * @param videos = the array to be sorted
     * @param type = used to get the sorting type: ascendant or descendant
     */
    public static void sortBasedOnViews(final ArrayList<Show> videos, final String type) {
        for (int i = 0; i < videos.size() - 1; i++) {
            for (int j = i + 1; j < videos.size(); j++) {
                int ok = 0;
                if (type.equals("asc")) {
                    if (videos.get(i).getViewCnt() > videos.get(j).getViewCnt()) {
                        ok = 1;
                    } else if (videos.get(i).getViewCnt() == videos.get(j).getViewCnt()
                            && videos.get(i).getTitle().compareTo(videos.get(j).getTitle()) > 0) {
                        ok = 1;
                    }
                } else {
                    if (videos.get(i).getViewCnt() < videos.get(j).getViewCnt()) {
                        ok = 1;
                    } else if (videos.get(i).getViewCnt() == videos.get(j).getViewCnt()
                            && videos.get(i).getTitle().compareTo(videos.get(j).getTitle()) < 0) {
                        ok = 1;
                    }
                }
                if (ok == 1) {
                    Show aux = videos.get(i);
                    videos.set(i, videos.get(j));
                    videos.set(j, aux);
                }
            }
        }
    }
    /**
     * The method sorts an array of shows based on duration and titles
     * @param videos = the array to be sorted
     * @param type = used to get the sorting type: ascendant or descendant
     */
    public static void sortBasedOnDuration(final ArrayList<Show> videos, final String type) {
        for (int i = 0; i < videos.size() - 1; i++) {
            for (int j = i + 1; j < videos.size(); j++) {
                int ok = 0;
                if (type.equals("asc")) {
                    if (videos.get(i).getDuration() > videos.get(j).getDuration()) {
                        ok = 1;
                    } else if (videos.get(i).getDuration() == videos.get(j).getDuration()
                            && videos.get(i).getTitle().compareTo(videos.get(j).getTitle()) > 0) {
                        ok = 1;
                    }
                } else {
                    if (videos.get(i).getDuration() < videos.get(j).getDuration()) {
                        ok = 1;
                    } else if (videos.get(i).getDuration() == videos.get(j).getDuration()
                            && videos.get(i).getTitle().compareTo(videos.get(j).getTitle()) < 0) {
                        ok = 1;
                    }
                }
                if (ok == 1) {
                    Show aux = videos.get(i);
                    videos.set(i, videos.get(j));
                    videos.set(j, aux);
                }
            }
        }
    }
    /**
     * The method sorts an array of shows based on favCnt and titles
     * @param videos = the array to be sorted
     * @param type = used to get the sorting type: ascendant or descendant
     */
    public static void sortBasedOnFavCnt(final ArrayList<Show> videos, final String type) {
        for (int i = 0; i < videos.size() - 1; i++) {
            for (int j = i + 1; j < videos.size(); j++) {
                int ok = 0;
                if (type.equals("asc")) {
                    if (videos.get(i).getFavCnt() > videos.get(j).getFavCnt()) {
                        ok = 1;
                    } else if (videos.get(i).getFavCnt() == videos.get(j).getFavCnt()
                            && videos.get(i).getTitle().compareTo(videos.get(j).getTitle()) > 0) {
                        ok = 1;
                    }
                } else {
                    if (videos.get(i).getFavCnt() < videos.get(j).getFavCnt()) {
                        ok = 1;
                    } else if (videos.get(i).getFavCnt() == videos.get(j).getFavCnt()
                            && videos.get(i).getTitle().compareTo(videos.get(j).getTitle()) < 0) {
                        ok = 1;
                    }
                }
                if (ok == 1) {
                    Show aux = videos.get(i);
                    videos.set(i, videos.get(j));
                    videos.set(j, aux);
                }
            }
        }
    }
    /**
     * The method sorts an array of shows based on ratings
     * @param videos = the array to be sorted
     * @param type = used to get the sorting type: ascendant or descendant
     */
    public static void sortBasedOnRating(final ArrayList<Show> videos, final String type) {
        for (int i = 0; i < videos.size() - 1; i++) {
            for (int j = 0; j < videos.size(); j++) {
                if ((type.equals("asc")
                        && videos.get(i).getRating() > videos.get(j).getRating())
                        || (type.equals("desc")
                        && videos.get(i).getRating() < videos.get(j).getRating())) {
                    Show aux = videos.get(i);
                    videos.set(i, videos.get(j));
                    videos.set(j, aux);
                }
            }
        }
    }
}


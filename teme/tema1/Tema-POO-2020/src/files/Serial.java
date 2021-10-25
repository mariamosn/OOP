package files;

import entertainment.Season;
import fileio.ActionInputData;
import fileio.SerialInputData;
import java.util.ArrayList;

public class Serial extends Show {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private ArrayList<Season> seasons;

    public Serial(final SerialInputData serial) {
        super(serial.getTitle(), serial.getYear(), serial.getCast(), serial.getGenres());
        this.numberOfSeasons = serial.getNumberSeason();
        this.seasons = serial.getSeasons();
    }
    public Serial() {
        this.numberOfSeasons = 0;
    }

    /**
     * Getter for the number of seasons
     */
    public int getNumberSeason() {
        return numberOfSeasons;
    }
    /**
     * Getter for the list of seasons for the current serial
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }
    /**
     * Returns the rating of the current serial based on its seasons
     */
    @Override
    public double getRating() {
        if (seasons == null || seasons.size() == 0) {
            return 0;
        }
        int sum = 0;
        for (Season season
                : seasons) {
            sum += season.getRating();
        }
        return (double) sum / seasons.size();
    }
    /**
     * Returns the total duration of the current serial, based on its seasons
     */
    @Override
    public int getDuration() {
        int duration = 0;
        for (Season s
                : seasons) {
            duration += s.getDuration();
        }
        return duration;
    }

    /**
     * Returns an array with all the shows that fit the filters of the current action
     * @param action contains the filters that need to be applied
     * @param dataBase contains information about all the serials
     */
    @Override
    public ArrayList<Show> checkFilters(final ModifiableDB dataBase, final ActionInputData action) {
        ArrayList<Show> suitableSerials = new ArrayList<>();
        for (Show video
                : dataBase.getSerials()) {
            if (check(video, action)) {
                suitableSerials.add(video);
            }
        }
        return suitableSerials;
    }
}


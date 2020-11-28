package files;

import entertainment.Season;
import fileio.ActionInputData;
import fileio.SerialInputData;
import java.util.ArrayList;

public class Serial extends Show {
    /**
     * Number of seasons
     */
    private int numberOfSeasons;
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
     * Returns an array with all the seasons of the current serial
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
        return sum / seasons.size();
    }
    /**
     * Returns the total duration of the current serial, based on its seasons
     */
    public int getDuration() {
        int duration = 0;
        for (Season s
                : seasons) {
            duration += s.getDuration();
        }
        return duration;
    }
    /**
     * Transforms the current serial into a String
     */
    @Override
    public String toString() {
        return "Serial{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
    /**
     * Returns an array with all the shows that fit the filters of the current action
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


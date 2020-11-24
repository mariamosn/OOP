package files;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public class Serial extends fileio.ShowInput {
    /**
     * Number of seasons
     */
    private int numberOfSeasons;
    /**
     * Season list
     */
    private ArrayList<Season> seasons;
    public int favCnt = 0;
    public int viewCnt = 0;

    public Serial(final SerialInputData serial) {
        super(serial.getTitle(), serial.getYear(), serial.getCast(), serial.getGenres());
        this.numberOfSeasons = serial.getNumberSeason();
        this.seasons = serial.getSeasons();
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

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

    public int getDuration() {
        int duration = 0;
        for (Season s
                : seasons) {
            duration += s.getDuration();
        }
        return duration;
    }

    public int getFavCnt() {
        return favCnt;
    }
    public void setFavCnt(int favCnt) {
        this.favCnt = favCnt;
    }

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
}


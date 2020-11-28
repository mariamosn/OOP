package action;

import entertainment.Season;
import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import files.Serial;
import files.User;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Command {
    private ModifiableDB dataBase;
    private ActionInputData action;
    private User user;
    protected Command(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        this.user = User.getRightUser(dataBase, action.getUsername());
        commandSolver();
    }

    /**
     * The method checks the type of command the current one is
     * and dictates the next method to be executed.
     */
    private void commandSolver() {
        if (action.getType().equals("favorite")) {
            commandFavorite();
        } else if (action.getType().equals("view")) {
            commandView();
        } else if (action.getType().equals("rating")) {
            commandRating();
        } else {
            System.out.println("Invalid type of command!");
        }
    }

    /**
     * Method that adds a video to the favorite list of an user
     */
    private int commandFavorite() {
        // check if the movie has been seen
        if (!user.getHistory().containsKey(action.getTitle())
                || user.getHistory().get(action.getTitle()) == 0) {
            try {
                JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "error -> " + action.getTitle() + " is not seen");
                dataBase.getArrayResult().add(out);
                return -1;
            } catch (IOException e) {
                System.out.println("IOException");
                return -1;
            }
        }

        // check if the movie isn't already a favorite
        for (String movie
                : user.getFavoriteMovies()) {
            if (movie.equals(action.getTitle())) {
                try {
                    JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                            "error -> " + action.getTitle() + " is already in favourite list");
                    dataBase.getArrayResult().add(out);
                    return -1;
                } catch (IOException e) {
                    System.out.println("IOException");
                    return -1;
                }
            }
        }

        // add to favorite list
        user.getFavoriteMovies().add(action.getTitle());

        try {
            JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                    "success -> " + action.getTitle() + " was added as favourite");
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
            return -1;
        }
        return 0;
    }

    /**
     * Method that adds a view to a video
     */
    private int commandView() {
        int cnt;
        if (user.getHistory().containsKey(action.getTitle())) {
            cnt = user.getHistory().get(action.getTitle()) + 1;
        } else {
            cnt = 1;
        }
        user.getHistory().put(action.getTitle(), cnt);

        try {
            JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                    "success -> " + action.getTitle() + " was viewed with total views of " + cnt);
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
            return -1;
        }
        return 0;
    }

    /**
     * Method that adds a rating to video
     */
    private int commandRating() {
        // check if the video is seen
        if (!user.getHistory().containsKey(action.getTitle())
                || user.getHistory().get(action.getTitle()) == 0) {
            try {
                JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "error -> " + action.getTitle() + " is not seen");
                dataBase.getArrayResult().add(out);
                return -1;
            } catch (IOException e) {
                System.out.println("IOException");
                return -1;
            }
        }

        // check if the user didn't already rate the video
        if (user.getRated() != null && user.getRated().size() > 0) {
            for (String title
                    : user.getRated()) {
                StringBuilder str = new StringBuilder(action.getTitle());
                str.append(action.getSeasonNumber());
                if (title.equals(str.toString())) {
                    try {
                        JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(),
                                "", "error -> " + action.getTitle() + " has been already rated");
                        dataBase.getArrayResult().add(out);
                    } catch (IOException e) {
                        System.out.println("IOException");
                        return -1;
                    }
                    return -1;
                }
            }
        }

        int ssn = action.getSeasonNumber();
        // rating for a movie
        if (ssn == 0) {
            // get the right movie from the database
            Movie crt = null;
            for (Movie movie
                    : dataBase.getMovies()) {
                if (movie.getTitle().equals(action.getTitle())) {
                    crt = movie;
                    break;
                }
            }
            if (crt == null) {
                System.out.println("Invalid movie!");
                return -1;
            }

            crt.setSumOfRatings(crt.getSumOfRatings() + action.getGrade());
            crt.setNumberOfRatings(crt.getNumberOfRatings() + 1);

        // rating for a season
        } else {
            // get the right serial from the database
            Serial serial = null;
            for (Serial tempSerial
                    : dataBase.getSerials()) {
                if (tempSerial.getTitle().equals(action.getTitle())) {
                    serial = tempSerial;
                    break;
                }
            }
            ArrayList<Season> seasons = serial.getSeasons();
            if (seasons.size() + 1 <= ssn) {
                System.out.println("Invalid season!");
                return -1;
            } else {
                Season season = seasons.get(ssn - 1);
                season.setNumberOfRatings(season.getNumberOfRatings() + 1);
                season.setSumOfRatings(season.getSumOfRatings() + action.getGrade());
                List<Double> ratings = season.getRatings();
                ratings.add(action.getGrade());
                season.setRatings(ratings);
            }
        }

        StringBuilder str = new StringBuilder(action.getTitle());
        Integer num = action.getSeasonNumber();
        str.append(num.toString());
        user.getRated().add(str.toString());

        try {
            JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                    "success -> " + action.getTitle() + " was rated with "
                            + action.getGrade() + " by " + action.getUsername());
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
            return -1;
        }
        return 0;
    }
}

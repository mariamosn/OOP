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
    private final ModifiableDB dataBase;
    private final ActionInputData action;
    private final User user;

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
        switch (action.getType()) {
            case "favorite" -> commandFavorite();
            case "view" -> commandView();
            case "rating" -> commandRating();
            default -> System.out.println("Invalid type of command!");
        }
    }

    /**
     * Method that adds a video to the favorite list of an user
     */
    @SuppressWarnings("unchecked")
    private void commandFavorite() {
        // check if the movie has been seen
        if (checkHistory()) {
            return;
        }

        // check if the movie isn't already a favorite
        for (String movie
                : user.getFavoriteMovies()) {
            if (movie.equals(action.getTitle())) {
                try {
                    JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                            "error -> " + action.getTitle() + " is already in favourite list");
                    dataBase.getArrayResult().add(out);
                    return;
                } catch (IOException e) {
                    System.out.println("IOException");
                    return;
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
        }
    }

    /**
     * Method that adds a view to a video
     */
    @SuppressWarnings("unchecked")
    private void commandView() {
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
        }
    }

    /**
     * Method that adds a rating to video
     */
    @SuppressWarnings("unchecked")
    private void commandRating() {
        // check if the video is seen
        if (checkHistory()) {
            return;
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
                        return;
                    }
                    return;
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
                return;
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
            assert serial != null;
            ArrayList<Season> seasons = serial.getSeasons();
            if (seasons.size() + 1 <= ssn) {
                System.out.println("Invalid season!");
                return;
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
        int num = action.getSeasonNumber();
        str.append(num);
        user.getRated().add(str.toString());

        try {
            JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                    "success -> " + action.getTitle() + " was rated with "
                            + action.getGrade() + " by " + action.getUsername());
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    @SuppressWarnings("unchecked")
    private boolean checkHistory() {
        if (!user.getHistory().containsKey(action.getTitle())
                || user.getHistory().get(action.getTitle()) == 0) {
            try {
                JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "error -> " + action.getTitle() + " is not seen");
                dataBase.getArrayResult().add(out);
                return true;
            } catch (IOException e) {
                System.out.println("IOException");
                return true;
            }
        }
        return false;
    }
}

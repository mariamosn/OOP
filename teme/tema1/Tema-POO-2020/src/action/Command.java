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
        this.user = getRightUser();
        commandSolver();
    }
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

    private User getRightUser() {
        List<User> users = dataBase.getUsers();
        User user = null;

        // search the right user in the database
        for (User tempUsr
                : users) {
            if (tempUsr.getUsername().equals(action.getUsername())) {
                user = tempUsr;
                break;
            }
        }
        if (user == null) {
            System.out.println("Invalid user!");
        }
        return user;
    }

    private int commandFavorite() {
        // check if the movie is seen
        if (!user.history.containsKey(action.getTitle())
                || user.history.get(action.getTitle()) == 0) {
            try {
                JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "error -> " + action.getTitle() + " is not seen");
                dataBase.arrayResult.add(out);
                return -1;
            } catch (IOException e) {
                System.out.println("IOException");
                return -1;
            }
        }

        // check if the movie isn't already a favorite
        for (String movie
                : user.favoriteMovies) {
            if (movie.equals(action.getTitle())) {
                try {
                    JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                            "error -> " + action.getTitle() + " is already in favourite list");
                    dataBase.arrayResult.add(out);
                    return -1;
                } catch (IOException e) {
                    System.out.println("IOException");
                    return -1;
                }
            }
        }

        // add to favorite
        user.favoriteMovies.add(action.getTitle());

        try {
            JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                    "success -> " + action.getTitle() + " was added as favourite");
            dataBase.arrayResult.add(out);
        } catch (IOException e) {
            System.out.println("IOException");
            return -1;
        }
        return 0;
    }

    private int commandView() {
        int cnt;
        if (user.history.containsKey(action.getTitle())) {
            cnt = user.history.get(action.getTitle()) + 1;
        } else {
            cnt = 1;
        }
        user.history.put(action.getTitle(), cnt);
        try {
            JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                    "success -> " + action.getTitle() + " was viewed with total views of " + cnt);
            dataBase.arrayResult.add(out);
        } catch (IOException e) {
            System.out.println("IOException");
            return -1;
        }
        return 0;
    }

    private int commandRating() {
        User user = getRightUser();
        // check if the video is seen
        if (!user.history.containsKey(action.getTitle())
                || user.history.get(action.getTitle()) == 0) {
            try {
                JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "error -> " + action.getTitle() + " is not seen");
                dataBase.arrayResult.add(out);
                return -1;
            } catch (IOException e) {
                System.out.println("IOException");
                return -1;
            }
        }

        // check if the user didn't already rate the video
        if (user.rated != null && user.rated.size() > 0) {
            for (String title
                    : user.rated) {
                StringBuilder str = new StringBuilder(action.getTitle());
                str.append(action.getSeasonNumber());
                if (title.equals(str.toString())) {
                    try {
                        JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                                "error -> " + action.getTitle() + " has been already rated");
                        dataBase.arrayResult.add(out);
                    } catch (IOException e) {
                        System.out.println("IOException");
                        return -1;
                    }
                    return -1;
                }
            }
        }

        int ssn = action.getSeasonNumber();
        // rating for movie
        if (ssn == 0) {
            // get the right movie from the database
            Movie crt = null;
            for (Movie movie
                    : dataBase.moviesData) {
                if (movie.getTitle().equals(action.getTitle())) {
                    crt = movie;
                    break;
                }
            }
            if (crt == null) {
                System.out.println("Invalid movie!");
                return -1;
            }

            crt.sumOfRatings += action.getGrade();
            crt.numberOfRatings++;

        // rating for season
        } else {
            // get the right serial from the database
            Serial serial = null;
            for (Serial tempSerial
                    : dataBase.serialsData) {
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
                season.numberOfRatings++;
                season.sumOfRatings += action.getGrade();
                List<Double> ratings = season.getRatings();
                ratings.add(action.getGrade());
                season.setRatings(ratings);
            }
        }

        StringBuilder str = new StringBuilder(action.getTitle());
        Integer num = action.getSeasonNumber();
        str.append(num.toString());
        user.rated.add(str.toString());

        try {
            JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                    "success -> " + action.getTitle() + " was rated with "
                            + action.getGrade() + " by " + action.getUsername());
            dataBase.arrayResult.add(out);
        } catch (IOException e) {
            System.out.println("IOException");
            return -1;
        }
        return 0;
    }
}

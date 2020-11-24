package action.recommendation;

import fileio.ActionInputData;
import fileio.ShowInput;
import files.ModifiableDB;
import files.Movie;
import files.Serial;
import files.User;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchRecommendation {
    ModifiableDB dataBase;
    ActionInputData action;
    public SearchRecommendation(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        search();
    }

    private void search() {
        ArrayList<String> result = new ArrayList<>();
        User user = getRightUser();

        if (user.getSubscriptionType().equals("PREMIUM")) {
            ArrayList<ShowInput> videos = new ArrayList<>(dataBase.getMovies());
            for (Serial s
                    : dataBase.getSerials()) {
                videos.add(s);
            }

            // check filters
            ArrayList<ShowInput> suitable = new ArrayList<>();
            for (ShowInput video
                    : videos) {
                if (video.getGenres().contains(action.getGenre())
                && (!user.history.containsKey(video.getTitle())
                || user.history.get(video.getTitle()) == 0)) {
                    suitable.add(video);
                }
            }

            // sort videos based on rating and title
            for (int i = 0; i < suitable.size() - 1; i++) {
                for (int j = i + 1; j < suitable.size(); j++) {
                    if (suitable.get(i).getRating() > suitable.get(j).getRating()
                        || (suitable.get(i).getRating() == suitable.get(j).getRating()
                        && suitable.get(i).getTitle().compareTo(suitable.get(j).getTitle()) > 0)) {
                        ShowInput aux = suitable.get(i);
                        suitable.set(i, suitable.get(j));
                        suitable.set(j, aux);
                    }
                }
            }

            for (ShowInput video
                    : suitable) {
                result.add(video.getTitle());
            }
        }

        try {
            JSONObject out;
            if (result.size() == 0) {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "SearchRecommendation cannot be applied!");
            } else {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "SearchRecommendation result: " + result);
            }
            dataBase.arrayResult.add(out);
        } catch (IOException e) {
            System.out.println("IOException");
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
}
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

public class FavoriteRecommendation {
    ModifiableDB dataBase;
    ActionInputData action;
    public FavoriteRecommendation(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        favorite();
    }

    private void favorite() {
        String found = null;
        User user = getRightUser();

        if (user.getSubscriptionType().equals("PREMIUM")) {

            ArrayList<ShowInput> videos = new ArrayList<>(dataBase.getMovies());
            for (Serial s
                    : dataBase.getSerials()) {
                videos.add(s);
            }

            calculateFavCnt(videos);

            // sort videos desc based on the number of apparances on favorite lists
            for (int i = 0; i < videos.size() - 1; i++) {
                for (int j = i + 1; j < videos.size(); j++) {
                    if (videos.get(i).getFavCnt() < videos.get(j).getFavCnt()) {
                        ShowInput aux = videos.get(i);
                        videos.set(i, videos.get(j));
                        videos.set(j, aux);
                    }
                }
            }

            for (int i = 0; i < videos.size() && found == null; i++) {
                if (!user.history.containsKey(videos.get(i).getTitle())
                        || user.history.get(videos.get(i).getTitle()) == 0
                        && videos.get(i).getFavCnt() != 0) {
                    found = videos.get(i).getTitle();
                }
            }
        }

        try {
            JSONObject out;
            if (found == null) {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "FavoriteRecommendation cannot be applied!");
            } else {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "FavoriteRecommendation result: " + found);
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

    private void calculateFavCnt(ArrayList<ShowInput> videos) {
        for (ShowInput video
                : videos) {
            int cnt = 0;
            for (User user
                    : dataBase.getUsers()) {
                if (user.getFavoriteMovies().contains(video.getTitle())) {
                    cnt++;
                }
            }
            video.setFavCnt(cnt);
        }
    }
}

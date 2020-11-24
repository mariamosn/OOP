package action.recommendation;

import fileio.ActionInputData;
import fileio.ShowInput;
import files.ModifiableDB;
import files.Serial;
import files.User;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestRecommendation {
    ModifiableDB dataBase;
    ActionInputData action;
    public BestRecommendation(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        bestUnseen();
    }

    private void bestUnseen() {
        Map<ShowInput, Integer> pos = new HashMap<>();
        ArrayList<ShowInput> videos = new ArrayList<>();
        for (ShowInput s
                : dataBase.getMovies()) {
            videos.add(s);
            pos.put(s, videos.size() - 1);
        }
        for (ShowInput s
                : dataBase.getMovies()) {
            videos.add(s);
            pos.put(s, videos.size() - 1);
        }

        // sort videos desc based on ratings
        for (int i = 0; i < videos.size() - 1; i++) {
            for (int j = i + 1; j < videos.size(); j++) {
                if (videos.get(i).getRating() < videos.get(j).getRating()
                || (videos.get(i).getRating() == videos.get(j).getRating()
                && pos.get(videos.get(i)) > pos.get(videos.get(j)))) {
                    ShowInput aux = videos.get(i);
                    videos.set(i, videos.get(j));
                    videos.set(j, aux);
                }
            }
        }

        User user = getRightUser();

        String found = null;
        for (int i = 0; i < videos.size() && found == null; i++) {
            if (!user.history.containsKey(videos.get(i).getTitle())
            || user.history.get(videos.get(i).getTitle()) == 0) {
                found = videos.get(i).getTitle();
            }
        }

        try {
            JSONObject out;
            if (found == null) {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "BestRatedUnseenRecommendation cannot be applied!");
            } else {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "BestRatedUnseenRecommendation result: " + found);
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

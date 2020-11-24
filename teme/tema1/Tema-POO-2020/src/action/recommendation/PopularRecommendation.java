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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopularRecommendation {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public PopularRecommendation(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        popular();
    }

    private void popular() {
        String found = null;
        User user = getRightUser();

        if (user.getSubscriptionType().equals("PREMIUM")) {
            ArrayList<ShowInput> videos = new ArrayList<>(dataBase.getMovies());
            for (Serial s
                    : dataBase.getSerials()) {
                videos.add(s);
            }

            ArrayList<String> popular = getMostPopularGen(videos);

            for (int i = 0; i < popular.size() && found == null; i++) {
                for (ShowInput mv
                        : videos) {
                    if (mv.getGenres().contains(popular.get(i))
                        && (!user.history.containsKey(mv.getTitle()) || user.history.get(mv.getTitle()) == 0)
                        && found == null) {
                        found = mv.getTitle();
                    }
                }
            }
        }

        try {
            JSONObject out;
            if (found == null) {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "PopularRecommendation cannot be applied!");
            } else {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "PopularRecommendation result: " + found);
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

    private ArrayList<String> getMostPopularGen(ArrayList<ShowInput> videos) {
        calculateShowViews(videos);

        // build a map with all genres and their total view count
        Map<String, Integer> genres = new HashMap<>();
        for (ShowInput video
             : videos) {
            for (String gen
                    : video.getGenres()) {
                if (!genres.containsKey(gen)) {
                    genres.put(gen, video.getViewCnt());
                } else {
                    genres.put(gen, genres.get(gen) + video.getViewCnt());
                }
            }
        }

        ArrayList<String> genList = new ArrayList<>();
        for (String str
                : genres.keySet()) {
            genList.add(str);
        }
        for (int i = 0; i < genList.size() - 1; i++) {
            for (int j = i + 1; j < genList.size(); j++) {
                if (genres.get(genList.get(i)) < genres.get(genList.get(j))) {
                    String aux = genList.get(i);
                    genList.set(i, genList.get(j));
                    genList.set(j, aux);
                }
            }
        }
        return genList;
    }

    private void calculateShowViews(ArrayList<ShowInput> videos) {
        for (ShowInput video
                : videos) {
            int cnt = 0;
            for (User user
                    : dataBase.getUsers()) {
                if (user.history.containsKey(video.getTitle())) {
                    cnt += user.history.get(video.getTitle());
                }
            }
            video.setViewCnt(cnt);
        }
    }
}

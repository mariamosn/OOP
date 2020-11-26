package action.recommendation;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import files.Show;
import files.User;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PopularRecommendation {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public PopularRecommendation(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        popular();
    }

    private void popular() {
        String found = null;
        User user = User.getRightUser(dataBase, action.getUsername());

        if (user.getSubscriptionType().equals("PREMIUM")) {
            ArrayList<Show> videos = new ArrayList<>(dataBase.getMovies());
            for (Serial s
                    : dataBase.getSerials()) {
                videos.add(s);
            }

            ArrayList<String> popular = getMostPopularGen(videos);

            for (int i = 0; i < popular.size() && found == null; i++) {
                for (Show mv
                        : videos) {
                    if (mv.getGenres().contains(popular.get(i))
                            && (!user.getHistory().containsKey(mv.getTitle())
                            || user.getHistory().get(mv.getTitle()) == 0)
                            && found == null) {
                        found = mv.getTitle();
                    }
                }
            }
        }

        try {
            JSONObject out;
            if (found == null) {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "PopularRecommendation cannot be applied!");
            } else {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "PopularRecommendation result: " + found);
            }
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private ArrayList<String> getMostPopularGen(final ArrayList<Show> videos) {
        Show.calculateViewCnt(videos, dataBase);

        // build a map with all genres and their total view count
        Map<String, Integer> genres = new HashMap<>();
        for (Show video
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
}

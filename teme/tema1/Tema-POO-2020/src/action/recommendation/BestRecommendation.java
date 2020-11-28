package action.recommendation;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Show;
import files.User;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BestRecommendation {
    private ModifiableDB dataBase;
    private ActionInputData action;

    public BestRecommendation(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        bestUnseen();
    }

    /**
     * The method gets the result of the Best recommendation
     */
    private void bestUnseen() {
        // pos contains the position of a sho in the original order
        Map<Show, Integer> pos = new HashMap<>();
        // videos will contain all shows (both movies and serials)
        ArrayList<Show> videos = new ArrayList<>();
        for (Show s
                : dataBase.getMovies()) {
            videos.add(s);
            pos.put(s, videos.size() - 1);
        }
        for (Show s
                : dataBase.getMovies()) {
            videos.add(s);
            pos.put(s, videos.size() - 1);
        }

        // sort videos desc based on ratings and their original position
        for (int i = 0; i < videos.size() - 1; i++) {
            for (int j = i + 1; j < videos.size(); j++) {
                if (videos.get(i).getRating() < videos.get(j).getRating()
                || (videos.get(i).getRating() == videos.get(j).getRating()
                && pos.get(videos.get(i)) > pos.get(videos.get(j)))) {
                    Show aux = videos.get(i);
                    videos.set(i, videos.get(j));
                    videos.set(j, aux);
                }
            }
        }

        User user = User.getRightUser(dataBase, action.getUsername());

        // find the best recommendation
        String found = null;
        for (int i = 0; i < videos.size() && found == null; i++) {
            // check if the video hasn't already been seen
            if (!user.getHistory().containsKey(videos.get(i).getTitle())
            || user.getHistory().get(videos.get(i).getTitle()) == 0) {
                found = videos.get(i).getTitle();
            }
        }

        try {
            JSONObject out;
            if (found == null) {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "BestRatedUnseenRecommendation cannot be applied!");
            } else {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "BestRatedUnseenRecommendation result: " + found);
            }
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}

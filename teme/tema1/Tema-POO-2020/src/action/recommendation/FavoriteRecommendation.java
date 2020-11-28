package action.recommendation;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import files.Show;
import files.User;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class FavoriteRecommendation {
    private ModifiableDB dataBase;
    private ActionInputData action;

    public FavoriteRecommendation(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        favorite();
    }

    /**
     * The method gets the result of the Favorite recommendation.
     */
    private void favorite() {
        String found = null;
        User user = User.getRightUser(dataBase, action.getUsername());

        if (user.getSubscriptionType().equals("PREMIUM")) {
            // videos will contain all the shows (both movies and serials)
            ArrayList<Show> videos = new ArrayList<>(dataBase.getMovies());
            for (Serial s
                    : dataBase.getSerials()) {
                videos.add(s);
            }

            Show.calculateFavCnt(videos, dataBase);

            // sort videos desc based on the number of appearances on favorite lists
            for (int i = 0; i < videos.size() - 1; i++) {
                for (int j = i + 1; j < videos.size(); j++) {
                    if (videos.get(i).getFavCnt() < videos.get(j).getFavCnt()) {
                        Show aux = videos.get(i);
                        videos.set(i, videos.get(j));
                        videos.set(j, aux);
                    }
                }
            }

            // get the video that's on the most favorite lists
            // and hasn't already been seen by the user
            for (int i = 0; i < videos.size() && found == null; i++) {
                if (!user.getHistory().containsKey(videos.get(i).getTitle())
                        || user.getHistory().get(videos.get(i).getTitle()) == 0
                        && videos.get(i).getFavCnt() != 0) {
                    found = videos.get(i).getTitle();
                }
            }
        }

        try {
            JSONObject out;
            if (found == null) {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "FavoriteRecommendation cannot be applied!");
            } else {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "FavoriteRecommendation result: " + found);
            }
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}

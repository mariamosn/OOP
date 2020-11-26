package action.recommendation;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import files.Show;
import files.User;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class SearchRecommendation {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public SearchRecommendation(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        search();
    }

    private void search() {
        ArrayList<String> result = new ArrayList<>();
        User user = User.getRightUser(dataBase, action.getUsername());

        if (user.getSubscriptionType().equals("PREMIUM")) {
            ArrayList<Show> videos = new ArrayList<>(dataBase.getMovies());
            for (Serial s
                    : dataBase.getSerials()) {
                videos.add(s);
            }

            // check filters
            ArrayList<Show> suitable = new ArrayList<>();
            for (Show video
                    : videos) {
                if (video.getGenres().contains(action.getGenre())
                && (!user.getHistory().containsKey(video.getTitle())
                || user.getHistory().get(video.getTitle()) == 0)) {
                    suitable.add(video);
                }
            }

            // sort videos based on rating and title
            for (int i = 0; i < suitable.size() - 1; i++) {
                for (int j = i + 1; j < suitable.size(); j++) {
                    if (suitable.get(i).getRating() > suitable.get(j).getRating()
                        || (suitable.get(i).getRating() == suitable.get(j).getRating()
                        && suitable.get(i).getTitle().compareTo(suitable.get(j).getTitle()) > 0)) {
                        Show aux = suitable.get(i);
                        suitable.set(i, suitable.get(j));
                        suitable.set(j, aux);
                    }
                }
            }

            for (Show video
                    : suitable) {
                result.add(video.getTitle());
            }
        }

        try {
            JSONObject out;
            if (result.size() == 0) {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "SearchRecommendation cannot be applied!");
            } else {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "SearchRecommendation result: " + result);
            }
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}

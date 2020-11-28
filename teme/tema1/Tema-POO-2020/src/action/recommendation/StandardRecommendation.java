package action.recommendation;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import files.Serial;
import files.User;
import org.json.simple.JSONObject;
import java.io.IOException;

public class StandardRecommendation {
    private ModifiableDB dataBase;
    private ActionInputData action;

    public StandardRecommendation(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        standard();
    }

    /**
     * The method gets the result of the Standard recommendation.
     */
    private void standard() {
        User user = User.getRightUser(dataBase, action.getUsername());
        String video = null;
        // check movies
        for (Movie mv
                : dataBase.getMovies()) {
            if (!user.getHistory().containsKey(mv.getTitle())
            || user.getHistory().get(mv.getTitle()) == 0) {
                video = mv.getTitle();
                break;
            }
        }
        // check serials
        if (video == null) {
            for (Serial serial
                    : dataBase.getSerials()) {
                if (!user.getHistory().containsKey(serial.getTitle())
                        || user.getHistory().get(serial.getTitle()) == 0) {
                    video = serial.getTitle();
                    break;
                }
            }
        }

        try {
            JSONObject out;
            if (video == null) {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "StandardRecommendation cannot be applied!");
            } else {
                out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                        "StandardRecommendation result: " + video);
            }
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}

package action.recommendation;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Movie;
import files.Serial;
import files.User;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class StandardRecommendation {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public StandardRecommendation(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        standard();
    }
    
    private void standard() {
        User user = getRightUser();
        String video = null;
        for (Movie mv
                : dataBase.getMovies()) {
            if (!user.history.containsKey(mv.getTitle())
            || user.history.get(mv.getTitle()) == 0) {
                video = mv.getTitle();
                break;
            }
        }
        if (video == null) {
            for (Serial serial
                    : dataBase.getSerials()) {
                if (!user.history.containsKey(serial.getTitle())
                        || user.history.get(serial.getTitle()) == 0) {
                    video = serial.getTitle();
                    break;
                }
            }
        }

        try {
            JSONObject out;
            if (video == null) {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "StandardRecommendation cannot be applied!");
            } else {
                out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                        "StandardRecommendation result: " + video);
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

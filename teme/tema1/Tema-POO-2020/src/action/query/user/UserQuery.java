package action.query.user;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.User;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserQuery {
    private final ModifiableDB dataBase;
    private final ActionInputData action;

    public UserQuery(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        numOfRatings();
    }

    /**
     * The method gets the result of the query
     */
    @SuppressWarnings("unchecked")
    private void numOfRatings() {
        // sort users based on the number of videos they rated and their names
        List<User> users = dataBase.getUsers();
        for (int i = 0; i < users.size() - 1; i++) {
            for (int j = i + 1; j < users.size(); j++) {
                // ok indicates if the two current users need to be swapped
                int ok = 0;
                if (action.getSortType().equals("asc")) {
                    if (users.get(i).getRatingsNum() > users.get(j).getRatingsNum()) {
                        ok = 1;
                    } else if (users.get(i).getRatingsNum() == users.get(j).getRatingsNum()) {
                        if (users.get(i).getUsername().compareTo(users.get(j).getUsername()) > 0) {
                            ok = 1;
                        }
                    }
                } else {
                    if (users.get(i).getRatingsNum() < users.get(j).getRatingsNum()) {
                        ok = 1;
                    } else if (users.get(i).getRatingsNum() == users.get(j).getRatingsNum()) {
                        if (users.get(i).getUsername().compareTo(users.get(j).getUsername()) < 0) {
                            ok = 1;
                        }
                    }
                }
                if (ok == 1) {
                    User aux = dataBase.getUsers().get(i);
                    dataBase.getUsers().set(i, dataBase.getUsers().get(j));
                    dataBase.getUsers().set(j, aux);
                }
            }
        }

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < dataBase.getUsers().size()
                && queryRes.size() < action.getNumber(); i++) {
            if (dataBase.getUsers().get(i).getRatingsNum() != 0) {
                queryRes.add(dataBase.getUsers().get(i).getUsername());
            }
        }
        try {
            JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                    "Query result: " + queryRes);
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}

package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import files.Show;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SerialLongest {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public SerialLongest(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        longest();
    }

    private void longest() {
        // check filters
        Serial mv = new Serial();
        ArrayList<Show> suitableMovies = mv.checkFilters(dataBase, action);

        // sort movies based on duration and title
        Show.sortBasedOnDuration(suitableMovies, action.getSortType());

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableMovies.size() && queryRes.size() < action.getNumber(); i++) {
            queryRes.add(suitableMovies.get(i).getTitle());
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


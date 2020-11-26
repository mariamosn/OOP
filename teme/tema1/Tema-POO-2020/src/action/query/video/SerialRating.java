package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import files.Show;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SerialRating {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public SerialRating(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        ratings();
    }

    private void ratings() {
        // check filters
        Serial s = new Serial();
        ArrayList<Show> suitableSerials = s.checkFilters(dataBase, action);

        // sort serials based on ratings
        Show.sortBasedOnRating(suitableSerials, action.getSortType());

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableSerials.size() && queryRes.size() < action.getNumber(); i++) {
            if (suitableSerials.get(i).getRating() != 0) {
                queryRes.add(suitableSerials.get(i).getTitle());
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

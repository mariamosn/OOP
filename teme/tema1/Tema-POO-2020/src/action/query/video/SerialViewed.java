package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import files.Show;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class SerialViewed {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public SerialViewed(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        viewed();
    }

    private void viewed() {
        // check filters
        Serial sw = new Serial();
        ArrayList<Show> suitableSerials = sw.checkFilters(dataBase, action);

        Show.calculateViewCnt(suitableSerials, dataBase);

        // sort serials
        Show.sortBasedOnViews(suitableSerials, action.getSortType());

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableSerials.size() && queryRes.size() < action.getNumber(); i++) {
            if (suitableSerials.get(i).getViewCnt() != 0) {
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

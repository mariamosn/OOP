package action.query.video;

import fileio.ActionInputData;
import files.ModifiableDB;
import files.Serial;
import files.Show;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class SerialFavorite {
    private final ModifiableDB dataBase;
    private final ActionInputData action;

    public SerialFavorite(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        favorite();
    }

    @SuppressWarnings("unchecked")
    private void favorite() {
        // check filters
        Serial s = new Serial();
        ArrayList<Show> suitableSerials = s.checkFilters(dataBase, action);

        Show.calculateFavCnt(suitableSerials, dataBase);

        // sort serials
        Show.sortBasedOnFavCnt(suitableSerials, action.getSortType());

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableSerials.size() && queryRes.size() < action.getNumber(); i++) {
            if (suitableSerials.get(i).getFavCnt() != 0) {
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

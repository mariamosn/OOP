package action.query.actor;

import fileio.ActionInputData;
import files.Actor;
import files.ModifiableDB;
import org.json.simple.JSONObject;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorFilter {
    private ModifiableDB dataBase;
    private ActionInputData action;

    public ActorFilter(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        filter();
    }

    /**
     * The method gets the result of the Filter query.
     */
    private void filter() {
        // get the list of wanted words
        int ind = Utils.getFilterNum("words");
        List<String> listOfWords = action.getFilters().get(ind);

        // get the actors with all the wanted words in their description
        List<Actor> suitableActors = new ArrayList<>();
        for (Actor actor
                : dataBase.getActors()) {
            int ok = 1;
            String desc = actor.getCareerDescription().toLowerCase();
            for (String word
                    : listOfWords) {
                Pattern p = Pattern.compile("[!._,'@?/ -]" + word.toLowerCase() + "[!._,'@?/ -]");
                Matcher m = p.matcher(desc);
                if (!m.find()) {
                    ok = 0;
                    break;
                }
            }
            if (ok == 1) {
                suitableActors.add(actor);
            }
        }

        // sort actors based on their names
        for (int i = 0; i < suitableActors.size() - 1; i++) {
            for (int j = i + 1; j < suitableActors.size(); j++) {
                String act1 = suitableActors.get(i).getName();
                String act2 = suitableActors.get(j).getName();
                if (act1.compareTo(act2) > 0) {
                    Actor aux = suitableActors.get(i);
                    suitableActors.set(i, suitableActors.get(j));
                    suitableActors.set(j, aux);
                }
            }
        }

        ArrayList<String> queryRes = new ArrayList<>();
        if (action.getSortType().equals("asc")) {
            for (Actor actor
                    : suitableActors) {
                queryRes.add(actor.getName());
            }
        } else {
            for (int i = suitableActors.size() - 1; i >= 0; i--) {
                queryRes.add(suitableActors.get(i).getName());
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


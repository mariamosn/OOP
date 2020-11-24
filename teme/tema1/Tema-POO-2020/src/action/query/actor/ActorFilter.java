package action.query.actor;

import fileio.ActionInputData;
import files.Actor;
import files.ModifiableDB;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorFilter {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public ActorFilter (ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        filter();
    }

    private void filter() {
        List<String> listOfWords = action.getFilters().get(2);
        // get the actors with all the wanted words in their description
        List<Actor> suitableActors = new ArrayList<Actor>();
        for (Actor actor
                : dataBase.getActors()) {
            int ok = 1;
            String desc = actor.getCareerDescription().toLowerCase();
            if (actor.getName().equals("Aidan Quinn")) {
            }
            for (String word
                    : listOfWords) {
                StringBuilder str = new StringBuilder(" ");
                str.append(word.toLowerCase());
                str.append(" ");
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

        // sort actors
        for (int i = 0; i < suitableActors.size() - 1; i++) {
            for (int j = i + 1; j < suitableActors.size(); j++) {
                if (suitableActors.get(i).getName().compareTo(suitableActors.get(j).getName()) > 0){
                    Actor aux = suitableActors.get(i);
                    suitableActors.set(i, suitableActors.get(j));
                    suitableActors.set(j, aux);
                }
            }
        }
        ArrayList<String> queryRes = new ArrayList<>();
        if (action.getSortType().equals("asc")) {
            for (int i = 0; i < suitableActors.size(); i++) {
                queryRes.add(suitableActors.get(i).getName());
            }
        } else {
            for (int i = suitableActors.size() - 1; i >= 0; i--) {
                queryRes.add(suitableActors.get(i).getName());
            }
        }

        try {
            JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                    "Query result: " + queryRes);
            dataBase.arrayResult.add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}


package action.query.actor;

import actor.ActorsAwards;
import fileio.ActionInputData;
import files.Actor;
import files.ModifiableDB;
import jdk.jshell.execution.Util;
import org.json.simple.JSONObject;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActorAwards {
    private ModifiableDB dataBase;
    private ActionInputData action;
    public ActorAwards (ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        awards();
    }

    private void awards() {
        List<String> listOfAwards = action.getFilters().get(3);
        // get the actors with all the wanted awards
        List<Actor> suitableActors = new ArrayList<Actor>();
        for (Actor actor
                : dataBase.getActors()) {
            int ok = 1;
            for (String award
                    : listOfAwards) {
                ActorsAwards aw = Utils.stringToAwards(award);
                if (!actor.getAwards().containsKey(aw) || actor.getAwards().get(aw) == 0) {
                    ok = 0;
                    break;
                }
            }
            if (ok == 1) {
                suitableActors.add(actor);
            }
        }

        for (Actor actor
                : suitableActors) {
            calculateAwardsNumber(actor);
        }

        // sort actors
        if (action.getSortType().equals("asc")) {
            for (int i = 0; i < suitableActors.size() - 1; i++) {
                for (int j = i + 1; j < suitableActors.size(); j++) {
                    if (suitableActors.get(i).getAwardsCnt() > suitableActors.get(j).getAwardsCnt()
                            || (suitableActors.get(i).getAwardsCnt() == suitableActors.get(j).getAwardsCnt()
                            && suitableActors.get(i).getName().compareTo(suitableActors.get(j).getName()) > 0)){
                        Actor aux = suitableActors.get(i);
                        suitableActors.set(i, suitableActors.get(j));
                        suitableActors.set(j, aux);
                    }
                }
            }
        } else {
            for (int i = 0; i < suitableActors.size() - 1; i++) {
                for (int j = i + 1; j < suitableActors.size(); j++) {
                    if (suitableActors.get(i).getAwardsCnt() < suitableActors.get(j).getAwardsCnt()
                    || (suitableActors.get(i).getAwardsCnt() == suitableActors.get(j).getAwardsCnt()
                    && suitableActors.get(i).getName().compareTo(suitableActors.get(j).getName()) < 0)) {
                        Actor aux = suitableActors.get(i);
                        suitableActors.set(i, suitableActors.get(j));
                        suitableActors.set(j, aux);
                    }
                }
            }
        }

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < suitableActors.size(); i++) {
            queryRes.add(suitableActors.get(i).getName());
        }
        try {
            JSONObject out = dataBase.fileWriter.writeFile(action.getActionId(), "",
                    "Query result: " + queryRes);
            dataBase.arrayResult.add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private void calculateAwardsNumber(Actor actor) {
        int cnt = 0;
        List<Integer> awards = new ArrayList<>(actor.getAwards().values());
        for (Integer awardCnt
                : awards) {
            cnt += awardCnt;
        }
        actor.setAwardsCnt(cnt);
    }
}

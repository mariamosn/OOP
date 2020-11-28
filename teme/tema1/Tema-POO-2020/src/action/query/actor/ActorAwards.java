package action.query.actor;

import actor.ActorsAwards;
import fileio.ActionInputData;
import files.Actor;
import files.ModifiableDB;
import org.json.simple.JSONObject;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActorAwards {
    private ModifiableDB dataBase;
    private ActionInputData action;

    public ActorAwards(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        awards();
    }

    /**
     * The method gets the result of the Awards query
     */
    private void awards() {
        // get the list of the wanted awards
        int filterNum = Utils.getFilterNum("awards");
        List<String> listOfAwards = action.getFilters().get(filterNum);

        // get the actors with all the wanted awards
        List<Actor> actors = new ArrayList<Actor>();
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
                actors.add(actor);
            }
        }

        for (Actor actor
                : actors) {
            calculateAwardsNumber(actor);
        }

        // sort actors based on the number of awards they have and their names
        if (action.getSortType().equals("asc")) {
            for (int i = 0; i < actors.size() - 1; i++) {
                for (int j = i + 1; j < actors.size(); j++) {
                    if (actors.get(i).getAwardsCnt() > actors.get(j).getAwardsCnt()
                            || (actors.get(i).getAwardsCnt() == actors.get(j).getAwardsCnt()
                            && actors.get(i).getName().compareTo(actors.get(j).getName()) > 0)) {
                        Actor aux = actors.get(i);
                        actors.set(i, actors.get(j));
                        actors.set(j, aux);
                    }
                }
            }
        } else {
            for (int i = 0; i < actors.size() - 1; i++) {
                for (int j = i + 1; j < actors.size(); j++) {
                    if (actors.get(i).getAwardsCnt() < actors.get(j).getAwardsCnt()
                    || (actors.get(i).getAwardsCnt() == actors.get(j).getAwardsCnt()
                    && actors.get(i).getName().compareTo(actors.get(j).getName()) < 0)) {
                        Actor aux = actors.get(i);
                        actors.set(i, actors.get(j));
                        actors.set(j, aux);
                    }
                }
            }
        }

        ArrayList<String> queryRes = new ArrayList<>();
        for (int i = 0; i < actors.size(); i++) {
            queryRes.add(actors.get(i).getName());
        }
        try {
            JSONObject out = dataBase.getFileWriter().writeFile(action.getActionId(), "",
                    "Query result: " + queryRes);
            dataBase.getArrayResult().add(out);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    /**
     * The method gets the total number of awards for each actor in the
     * @param actor list of actors
     */
    private void calculateAwardsNumber(final Actor actor) {
        int cnt = 0;
        List<Integer> awards = new ArrayList<>(actor.getAwards().values());
        for (Integer awardCnt
                : awards) {
            cnt += awardCnt;
        }
        actor.setAwardsCnt(cnt);
    }
}

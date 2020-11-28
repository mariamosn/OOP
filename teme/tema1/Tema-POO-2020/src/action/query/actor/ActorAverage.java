package action.query.actor;

import fileio.ActionInputData;
import files.Actor;
import files.ModifiableDB;
import files.Movie;
import files.Serial;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActorAverage {
    private ModifiableDB dataBase;
    private ActionInputData action;

    public ActorAverage(final ModifiableDB dataBase, final ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        average();
    }

    /**
     * Method that gets the result of the Average query
     */
    private void average() {
        calculateAverage();

        ArrayList<String> queryRes = new ArrayList<>();
        List<Actor> actors = dataBase.getActors();

        // sort actors based on average ratings
        if (action.getSortType().equals("desc")) {
            for (int i = 0; i < actors.size() - 1; i++) {
                for (int j = i + 1; j < actors.size(); j++) {
                    double avg1 = actors.get(i).getAverage();
                    double avg2 = actors.get(j).getAverage();
                    if (avg1 < avg2
                            || (avg1 == avg2
                            && actors.get(i).getName().compareTo(actors.get(j).getName()) < 0)) {
                        Actor aux = actors.get(i);
                        actors.set(i, actors.get(j));
                        actors.set(j, aux);
                    }
                }
            }
        } else if (action.getSortType().equals("asc")) {
            for (int i = 0; i < actors.size() - 1; i++) {
                for (int j = i + 1; j < actors.size(); j++) {
                    double avg1 = actors.get(i).getAverage();
                    double avg2 = actors.get(j).getAverage();
                    if (avg1 > avg2
                            || (avg1 == avg2
                            && actors.get(i).getName().compareTo(actors.get(j).getName()) > 0)) {
                        Actor aux = actors.get(i);
                        actors.set(i, dataBase.getActors().get(j));
                        actors.set(j, aux);
                    }
                }
            }
        } else {
            System.out.println("Invalid sort type!");
        }

        for (int i = 0; queryRes.size() < action.getNumber() && i < actors.size(); i++) {
            if (actors.get(i).getAverage() != 0) {
                queryRes.add(actors.get(i).getName());
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

    /**
     * The method calculates for each actor the average rating
     * for all the shows they were casted in.
     */
    private void calculateAverage() {
        for (Actor actor
                : dataBase.getActors()) {
            List<String> filmography = actor.getFilmography();
            double sum = 0;
            int number = 0;
            // get each show in the actor's filmography
            for (String video
                    : filmography) {
                int ok = 0;
                for (Movie movie
                        : dataBase.getMovies()) {
                    if (movie.getTitle().equals(video)) {
                        ok = 1;
                        if (movie.getRating() != 0) {
                            sum += movie.getRating();
                            number++;
                        }
                        break;
                    }
                }
                if (ok == 0) {
                    for (Serial serial
                            : dataBase.getSerials()) {
                        if (serial.getTitle().equals(video)) {
                            if (serial.getRating() != 0) {
                                sum += serial.getRating();
                                number++;
                            }
                        }
                    }
                }
            }
            if (number == 0) {
                actor.setAverage(0);
            } else {
                actor.setAverage(sum / number);
            }
        }
    }
}

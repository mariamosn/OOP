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
    ModifiableDB dataBase;
    ActionInputData action;

    public ActorAverage(ModifiableDB dataBase, ActionInputData action) {
        this.dataBase = dataBase;
        this.action = action;
        average();
    }
    private void average() {
        calculateAverage();

        ArrayList<String> queryRes = new ArrayList<>();
        if (action.getSortType().equals("desc")) {
            for (int i = 0; i < dataBase.actorsData.size() - 1; i++) {
                for (int j = i + 1; j < dataBase.actorsData.size(); j++) {
                    double avg1 = dataBase.actorsData.get(i).getAverage();
                    double avg2 = dataBase.actorsData.get(j).getAverage();
                    if (avg1 < avg2 ||
                            (avg1 == avg2
                                    && dataBase.actorsData.get(i).getName().compareTo(dataBase.actorsData.get(j).getName()) < 0)) {
                        Actor aux = dataBase.actorsData.get(i);
                        dataBase.actorsData.set(i, dataBase.actorsData.get(j));
                        dataBase.actorsData.set(j, aux);
                    }
                }
            }
        } else if (action.getSortType().equals("asc")) {
            for (int i = 0; i < dataBase.actorsData.size() - 1; i++) {
                for (int j = i + 1; j < dataBase.actorsData.size(); j++) {
                    double avg1 = dataBase.actorsData.get(i).getAverage();
                    double avg2 = dataBase.actorsData.get(j).getAverage();
                    if (avg1 > avg2 ||
                            (avg1 == avg2
                                    && dataBase.actorsData.get(i).getName().compareTo(dataBase.actorsData.get(j).getName()) > 0)) {
                        Actor aux = dataBase.actorsData.get(i);
                        dataBase.actorsData.set(i, dataBase.actorsData.get(j));
                        dataBase.actorsData.set(j, aux);
                    }
                }
            }
        } else {
            System.out.println("Invalid sort type!");
        }

        int ok = 0;
        for (int i = 0; ok < action.getNumber() && i < dataBase.actorsData.size(); i++) {
            if (dataBase.actorsData.get(i).getAverage() != 0) {
                queryRes.add(dataBase.actorsData.get(i).getName());
                ok++;
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

    private void calculateAverage() {
        for (Actor actor
                : dataBase.actorsData) {
            List<String> filmography = actor.getFilmography();
            double sum = 0;
            int number = 0;
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

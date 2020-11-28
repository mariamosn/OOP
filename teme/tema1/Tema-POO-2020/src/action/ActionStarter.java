package action;

import action.query.Query;
import action.recommendation.Recommendation;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import files.ModifiableDB;
import org.json.simple.JSONArray;

public class ActionStarter {
    private ModifiableDB dataBase;
    public ActionStarter(final Input input, final Writer fileWriter, final JSONArray arrayResult) {
        dataBase = new ModifiableDB(input, fileWriter, arrayResult);
        for (int i = 0; i < input.getCommands().size(); i++) {
            ActionInputData next = input.getCommands().get(i);
            actionCaller(next);
        }
    }

    /**
     * The method checks what type of action the current one is and
     * creates an instance of a specific class accordingly.
     * @param action contains information about the current action type
     */
    private void actionCaller(final ActionInputData action) {
        if (action.getActionType().equals("command")) {
            new Command(dataBase, action);
        } else if (action.getActionType().equals("query")) {
            new Query(dataBase, action);
        } else if (action.getActionType().equals("recommendation")) {
            new Recommendation(dataBase, action);
        } else {
            System.out.println("Invalid type of action!");
        }
    }
}

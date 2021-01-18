import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import entity.DataBase;
import entity.MonthlyAction;
import input.Input;
import output.Output;

import java.io.File;

public final class Main {
    private Main() {
    }

    /**
     * Entry point for the program's execution
     * @param args - args[0] = the input file
     *             - args[1] = the output file
     */
    public static void main(final String[] args) throws Exception {
        // parsing the information into the input files
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File(args[0]), Input.class);

        // updating the information in the DataBase based on the input
        DataBase dataBase = DataBase.getInstance();
        dataBase.update(input);

        // run the energy system's simulation
        MonthlyAction action = new MonthlyAction();
        boolean distributorsLeft = true;
        for (int i = 0; i <= DataBase.getInstance().getNumberOfTurns() && distributorsLeft; i++) {
            distributorsLeft = action.monthAction(i);
        }

        // write the obtained results in the output file
        Output output = new Output();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ow.writeValue(new File(args[1]), output);

        // remove the information from the DataBase
        DataBase.getInstance().clean();
    }
}

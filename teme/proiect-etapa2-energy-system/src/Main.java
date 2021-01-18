import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import entity.DataBase;
import entity.MonthlyAction;
import input.Input;
import output.Output;

import java.io.File;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
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
        if (args[0].equals("/home/maria/t2/teme/teme/proiect-etapa2-energy-system/checker/resources/in/complex_4.json")) {
            ow.writeValue(new File("test.out"), output);
        }
        // System.out.println(args[0]);

        // remove the information from the DataBase
        DataBase.getInstance().clean();
    }
}

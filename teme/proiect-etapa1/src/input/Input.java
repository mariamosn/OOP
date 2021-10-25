package input;

import java.util.List;

public class Input {
    private int numberOfTurns;
    private Data initialData;
    private List<MonthlyUpdates> monthlyUpdates;

    /**
     * Getter for the number of turns
     */
    public int getNumberOfTurns() {
        return numberOfTurns;
    }
    /**
     * Setter for the number of turns
     * @param numberOfTurns - the total number of turns (months)
     */
    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    /**
     * Getter for the initial data
     */
    public Data getInitialData() {
        return initialData;
    }
    /**
     * Setter for the initial data
     * @param initialData - the initial data (about consumers and distributors)
     */
    public void setInitialData(final Data initialData) {
        this.initialData = initialData;
    }

    /**
     * Getter for the list of monthly updates
     */
    public List<MonthlyUpdates> getMonthlyUpdates() {
        return monthlyUpdates;
    }
    /**
     * Setter for the list of monthly updates
     * @param monthlyUpdates - the list of monthly updates that need to be done
     */
    public void setMonthlyUpdates(final List<MonthlyUpdates> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}

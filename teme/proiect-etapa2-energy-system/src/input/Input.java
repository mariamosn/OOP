package input;

import java.util.List;

public final class Input {
    private int numberOfTurns;
    private Data initialData;
    private List<MonthlyUpdates> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

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
     * @param initialData - the initial data (about consumers, distributors, producers)
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

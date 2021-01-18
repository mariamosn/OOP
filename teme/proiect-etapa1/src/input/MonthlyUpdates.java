package input;

import java.util.List;

public class MonthlyUpdates {
    private List<ConsumerInput> newConsumers;
    private List<CostChange> costsChanges;

    /**
     * Getter for the list of new consumers
     */
    public List<ConsumerInput> getNewConsumers() {
        return newConsumers;
    }
    /**
     * Setter for the list of new consumers
     * @param newConsumers - the list of consumers that need to be added
     */
    public void setNewConsumers(final List<ConsumerInput> newConsumers) {
        this.newConsumers = newConsumers;
    }

    /**
     * Getter for the list of distributor's cost changes
     */
    public List<CostChange> getCostsChanges() {
        return costsChanges;
    }
    /**
     * Setter for the list of distributor's cost changes
     * @param costsChanges - the distributors' cost changes
     */
    public void setCostsChanges(final List<CostChange> costsChanges) {
        this.costsChanges = costsChanges;
    }
}

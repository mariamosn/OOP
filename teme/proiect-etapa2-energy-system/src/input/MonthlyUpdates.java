package input;

import java.util.List;

public class MonthlyUpdates {
    private List<ConsumerInput> newConsumers;
    private List<DistributorChange> distributorChanges;
    private List<ProducerChange> producerChanges;

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

    public List<DistributorChange> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(List<DistributorChange> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public List<ProducerChange> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(List<ProducerChange> producerChanges) {
        this.producerChanges = producerChanges;
    }
}

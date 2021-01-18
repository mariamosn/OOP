package input;

import java.util.List;

public final class Data {
    private List<ConsumerInput> consumers;
    private List<DistributorInput> distributors;
    private List<ProducerInput> producers;

    /**
     * Getter for the list of consumers.
     */
    public List<ConsumerInput> getConsumers() {
        return consumers;
    }
    /**
     * Setter for the list of consumers.
     * @param consumers - the initial list of consumers
     */
    public void setConsumers(final List<ConsumerInput> consumers) {
        this.consumers = consumers;
    }

    /**
     * Getter for the list of distributors.
     */
    public List<DistributorInput> getDistributors() {
        return distributors;
    }
    /**
     * Setter for the list of distributors.
     * @param distributors - the initial list of distributors
     */
    public void setDistributors(final List<DistributorInput> distributors) {
        this.distributors = distributors;
    }

    public List<ProducerInput> getProducers() {
        return producers;
    }

    public void setProducers(List<ProducerInput> producers) {
        this.producers = producers;
    }
}

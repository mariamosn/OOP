package output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import consumer.Consumer;
import distributor.Distributor;
import entity.DataBase;
import producer.Producer;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "consumers", "distributors" })
public final class Output {
    @JsonProperty("consumers")
    private List<ConsumerOutput> consumers;
    @JsonProperty("distributors")
    private List<DistributorOutput> distributors;
    @JsonProperty("energyProducers")
    private List<ProducerOutput> energyProducers;

    public Output() {
        DataBase dataBase = DataBase.getInstance();
        consumers = new ArrayList<>();
        for (Consumer consumer
                : dataBase.getConsumers()) {
            ConsumerOutput out = new ConsumerOutput(consumer);
            consumers.add(out);
        }

        distributors = new ArrayList<>();
        for (Distributor distributor
                : dataBase.getDistributors()) {
            DistributorOutput out = new DistributorOutput(distributor);
            distributors.add(out);
        }

        energyProducers = new ArrayList<>();
        for (Producer producer
                : dataBase.getProducers()) {
            ProducerOutput out = new ProducerOutput(producer);
            energyProducers.add(out);
        }
    }

    /**
     * Getter for the final list of consumers
     */
    public List<ConsumerOutput> getConsumers() {
        return consumers;
    }
    /**
     * Setter for the final list of consumers
     * @param consumers - the final list of consumers
     */
    public void setConsumers(final List<ConsumerOutput> consumers) {
        this.consumers = consumers;
    }

    /**
     * Getter for the final list of distributors
     */
    public List<DistributorOutput> getDistributors() {
        return distributors;
    }
    /**
     * Setter for the final list of distributors
     * @param distributors - the final list of distributors
     */
    public void setDistributors(final List<DistributorOutput> distributors) {
        this.distributors = distributors;
    }

    public List<ProducerOutput> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(List<ProducerOutput> energyProducers) {
        this.energyProducers = energyProducers;
    }
}

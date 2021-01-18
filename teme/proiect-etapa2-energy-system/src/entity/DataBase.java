package entity;

import consumer.Consumer;
import distributor.Distributor;
import input.ConsumerInput;
import input.Data;
import input.Input;
import input.MonthlyUpdates;
import input.DistributorInput;
import input.ProducerInput;
import producer.Producer;

import java.util.ArrayList;
import java.util.List;

/**
 * The class stores information about the consumers, distributors and monthly updates
 */
public final class DataBase {
    private static DataBase instance = null;
    private int numberOfTurns;
    private List<Consumer> consumers;
    private List<Distributor> distributors;
    private List<Producer> producers;
    private List<MonthlyUpdates> monthlyUpdates;

    private DataBase() {
        this.numberOfTurns = 0;
        this.consumers = new ArrayList<>();
        this.distributors = new ArrayList<>();
        this.producers = new ArrayList<>();
        this.monthlyUpdates = null;
    }

    /**
     * Getter for the instance of the DataBase
     * @return - the instance
     */
    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    /**
     * Method that removes all the entities from the lists
     * of the DataBase instance
     */
    public void clean() {
        this.consumers.clear();
        this.distributors.clear();
        this.producers.clear();
        this.monthlyUpdates.clear();
    }

    /**
     * Method that updates the data stored in the DataBase instance
     * @param input - the input from the .json file
     */
    public void update(final Input input) {
        this.numberOfTurns = input.getNumberOfTurns();
        Data data = input.getInitialData();
        for (ConsumerInput consumer
                : data.getConsumers()) {
            Entity newCons = EntityFactory.getInstance().createEntity("consumer", consumer);
            this.consumers.add((Consumer) newCons);
        }
        for (DistributorInput distributor
                : data.getDistributors()) {
            Entity newDistr = EntityFactory.getInstance().createEntity("distributor",
                    distributor);
            this.distributors.add((Distributor) newDistr);
        }
        for (ProducerInput producer
                : data.getProducers()) {
            Entity newProd = EntityFactory.getInstance().createEntity("producer", producer);
            this.producers.add((Producer) newProd);
        }
        this.monthlyUpdates = input.getMonthlyUpdates();
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public List<MonthlyUpdates> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final List<MonthlyUpdates> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<Distributor> distributors) {
        this.distributors = distributors;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    /**
     * The method searches in the DataBase the wanted consumer.
     * @param id - the wanted consumer's id
     * @return - the consumer with the provided id or null if no such consumer exists
     */
    public Consumer getConsumer(final int id) {
        for (Consumer consumer
             : consumers) {
            if (consumer.getId() == id) {
                return consumer;
            }
        }
        return null;
    }

    /**
     * The method searches in the DataBase the wanted distributor.
     * @param id - the wanted distributor's id
     * @return - the distributor with the provided id or null if no such distributor exists
     */
    public Distributor getDistributor(final int id) {
        for (Distributor distributor
            : distributors) {
            if (distributor.getId() == id) {
                return distributor;
            }
        }
        return null;
    }

    /**
     * The method searches a specific producer, based on its id.
     * @param id - the wanted producer's id
     * @return - the producer with the provided id or null if no such producer exists
     */
    public Producer getProducer(final int id) {
        for (Producer producer
                : producers) {
            if (producer.getId() == id) {
                return producer;
            }
        }
        return null;
    }
}

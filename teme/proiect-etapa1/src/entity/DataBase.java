package entity;

import consumer.Consumer;
import distributor.Distributor;
import input.ConsumerInput;
import input.Data;
import input.DistributorInput;
import input.Input;
import input.MonthlyUpdates;

import java.util.ArrayList;
import java.util.List;

/**
 * The class uses the Singleton pattern
 * its purpose is to store information about the consumers, distributors and monthly updates
 */
public final class DataBase {
    private static DataBase instance = null;
    private int numberOfTurns;
    private List<Consumer> consumers;
    private List<Distributor> distributors;
    private List<MonthlyUpdates> monthlyUpdates;

    private DataBase() {
        this.numberOfTurns = 0;
        this.consumers = new ArrayList<>();
        this.distributors = new ArrayList<>();
        this.monthlyUpdates = null;
    }

    /**
     * Getter for the instance of the Singleton
     * @return - the Singleton instance
     */
    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    /**
     * Method that removes all the entities from the two lists
     * of the DataBase instance (consumers and distributors)
     */
    public void clean() {
        this.consumers.clear();
        this.distributors.clear();
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
            this.consumers.add((Consumer) EntityFactory.createEntity("consumer", consumer));
        }
        for (DistributorInput distributor
                : data.getDistributors()) {
            this.distributors.add((Distributor) EntityFactory.createEntity("distributor",
                    distributor));
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
}

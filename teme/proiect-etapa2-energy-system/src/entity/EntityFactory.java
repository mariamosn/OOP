package entity;

import consumer.Consumer;
import distributor.Distributor;
import input.ConsumerInput;
import input.DistributorInput;
import input.ProducerInput;
import producer.Producer;

/**
 * The class implements the Factory and Singleton pattern
 */
public final class EntityFactory {
    private static EntityFactory instance = null;

    private EntityFactory() {
    }

    /**
     * Getter for the instance of the Singleton
     * @return - the Singleton instance
     */
    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }
        return instance;
    }

    /**
     * Creates a new entity based on the type provided
     * @param type - the type of entity wanted (consumer, distributor or producer)
     * @param input - the original input object based on which the new entity will be created
     * @return - the new entity
     */
    public Entity createEntity(final String type, final Object input) {
        return switch (type) {
            case "consumer" -> new Consumer((ConsumerInput) input);
            case "distributor" -> new Distributor((DistributorInput) input);
            case "producer" -> new Producer((ProducerInput) input);
            default -> null;
        };
    }
}

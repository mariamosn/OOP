package entity;

import consumer.Consumer;
import distributor.Distributor;
import input.ConsumerInput;
import input.DistributorInput;

/**
 * The class is used to implement the Factory pattern
 */
public final class EntityFactory {
    private EntityFactory() {
    }

    /**
     * Creates a new entity based on the type provided
     * @param type - the type of entity wanted (consumer or distributor)
     * @param input - the original input object based on which the new entity will be created
     * @return - the new entity
     */
    public static Entity createEntity(final String type, final Object input) {
        if (type.equals("consumer")) {
            return new Consumer((ConsumerInput) input);
        } else if (type.equals("distributor")) {
            return new Distributor((DistributorInput) input);
        }
        return null;
    }
}

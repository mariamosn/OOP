package strategies;

import distributor.Distributor;
import producer.Producer;

import java.util.List;

public interface ProducerChooser {
    /**
     * The method determines the producers a distributor needs to take energy from
     * in order to acquire the needed energy, based on the producer strategy and
     * their current availability.
     * @param distr = the distributor that needs new producers
     * @return = a list with the new producers assigned to the distributor
     */
    List<Producer> chooseProducers(Distributor distr);
}

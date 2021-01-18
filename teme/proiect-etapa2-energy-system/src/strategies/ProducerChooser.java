package strategies;

import distributor.Distributor;
import producer.Producer;

import java.util.List;

public interface ProducerChooser {
    public List<Producer> chooseProducers(Distributor distr);
}

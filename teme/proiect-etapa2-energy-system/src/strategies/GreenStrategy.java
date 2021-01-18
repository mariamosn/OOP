package strategies;

import distributor.Distributor;
import entity.DataBase;
import producer.Producer;

import java.util.ArrayList;
import java.util.List;

/**
 * This type of strategy determines the new producers for a specific
 * distributor based on the energy type, price and quantity
 */
public final class GreenStrategy implements ProducerChooser {

    @Override
    public List<Producer> chooseProducers(Distributor distr) {
        List<Producer> producers = DataBase.getInstance().getProducers();
        ArrayList<Producer> prod = new ArrayList<>(producers);

        // sorting the list of producers
        for (int i = 0; i < prod.size() - 1; i++) {
            for (int j = i + 1; j < prod.size(); j++) {
                Producer p1 = prod.get(i), p2 = prod.get(j);
                if (!p1.getEnergyType().isRenewable() && p2.getEnergyType().isRenewable()
                    || (p1.getEnergyType().isRenewable() == p2.getEnergyType().isRenewable()
                        && (p1.getPriceKW() > p2.getPriceKW()
                        || (p1.getPriceKW() == p2.getPriceKW()
                            && p1.getEnergy().getEnergyPerDistributor()
                                < p2.getEnergy().getEnergyPerDistributor())))) {
                    Producer aux = prod.get(i);
                    prod.set(i, prod.get(j));
                    prod.set(j, aux);
                }
            }
        }

        // forming the list of new producers
        return QuantityAssurance.selectProducers(distr, prod);
    }
}

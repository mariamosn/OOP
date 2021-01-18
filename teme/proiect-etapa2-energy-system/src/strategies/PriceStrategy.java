package strategies;

import distributor.Distributor;
import entity.DataBase;
import producer.Producer;

import java.util.ArrayList;
import java.util.List;

public final class PriceStrategy implements ProducerChooser {
    @Override
    public List<Producer> chooseProducers(Distributor distr) {
        List<Producer> producers = DataBase.getInstance().getProducers();
        ArrayList<Producer> prod = new ArrayList<>(producers);
        for (int i = 0; i < prod.size() - 1; i++) {
            for (int j = i + 1; j < prod.size(); j++) {
                if (prod.get(i).getPriceKW() > prod.get(j).getPriceKW()
                    || (prod.get(i).getPriceKW() == prod.get(j).getPriceKW()
                    && prod.get(i).getEnergy().getEnergyPerDistributor()
                        < prod.get(j).getEnergy().getEnergyPerDistributor())) {
                    Producer aux = prod.get(i);
                    prod.set(i, prod.get(j));
                    prod.set(j, aux);
                }
            }
        }

        return QuantityAssurance.selectProducers(distr, prod);
    }
}

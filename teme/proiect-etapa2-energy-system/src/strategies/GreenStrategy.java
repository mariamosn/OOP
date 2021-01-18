package strategies;

import distributor.Distributor;
import entity.DataBase;
import producer.Producer;

import java.util.ArrayList;
import java.util.List;

public final class GreenStrategy implements ProducerChooser {

    @Override
    public List<Producer> chooseProducers(Distributor distr) {
        List<Producer> producers = DataBase.getInstance().getProducers();
        ArrayList<Producer> prod = new ArrayList<>(producers);
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

        int quantity = 0;
        ArrayList<Producer> chosen = new ArrayList<>();
        for (int i = 0; i < prod.size() && quantity < distr.getEnergyNeededKW(); i++) {
            int currentDistributors = prod.get(i).getEnergy().getDistributors().size();
            if (currentDistributors < prod.get(i).getMaxDistributors()) {
                chosen.add(prod.get(i));
                quantity += prod.get(i).getEnergy().getEnergyPerDistributor();
            }
        }

        return chosen;
    }
}

package strategies;

import distributor.Distributor;
import producer.Producer;

import java.util.ArrayList;
import java.util.List;

public final class QuantityAssurance {
    private QuantityAssurance() {
    }

    /**
     * The method selects from the list as many producers as needed in order to meet the energy
     * requirements of the distributor.
     * @param distr - the distributor for which we need to choose producers
     * @param prod - the sorted list (depending on strategy) of producers
     * @return - a list with the new producers of the distributor
     */
    public static List<Producer> selectProducers(Distributor distr, ArrayList<Producer> prod) {
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

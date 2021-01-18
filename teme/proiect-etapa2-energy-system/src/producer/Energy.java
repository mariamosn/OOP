package producer;

import distributor.Distributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Energy extends Observable {
    private int energyPerDistributor;
    private List<Distributor> distributors;

    public Energy(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        distributors = new ArrayList<>();
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        setChanged();
        notifyObservers(energyPerDistributor);
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }
}

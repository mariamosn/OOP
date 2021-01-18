package output;

import entities.EnergyType;
import producer.Producer;

import java.util.List;

public final class ProducerOutput {
    private int id;
    private int maxDistributors;
    private double priceKW;
    private entities.EnergyType energyType;
    private int energyPerDistributor;
    private List<MonthlyStatOutput> monthlyStats;

    public ProducerOutput(Producer prod) {
        this.id = prod.getId();
        this.maxDistributors = prod.getMaxDistributors();
        this.priceKW = prod.getPriceKW();
        this.energyType = prod.getEnergyType();
        this.energyPerDistributor = prod.getEnergy().getEnergyPerDistributor();
        this.monthlyStats = prod.getMonthlyStats();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    /**
     * Getter for the list of monthly stats regarding the
     * distributors - producers relations
     * @return - the list of monthly stats
     */
    public List<MonthlyStatOutput> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStatOutput> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }
}

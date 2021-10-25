package producer;

import entity.Entity;
import input.ProducerInput;
import output.MonthlyStatOutput;

import java.util.ArrayList;
import java.util.List;

public final class Producer extends Entity {
    private EnergyType energyType;
    private int maxDistributors;
    private double priceKW;
    private Energy energy;
    private final List<MonthlyStatOutput> monthlyStats;

    public Producer(final ProducerInput producerInput) {
        super(producerInput.getId());
        this.energyType = producerInput.getEnergyType();
        this.maxDistributors = producerInput.getMaxDistributors();
        this.priceKW = producerInput.getPriceKW();
        this.energy = new Energy(producerInput.getEnergyPerDistributor());
        this.monthlyStats = new ArrayList<>();
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
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

    public Energy getEnergy() {
        return energy;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public List<MonthlyStatOutput> getMonthlyStats() {
        return monthlyStats;
    }
}

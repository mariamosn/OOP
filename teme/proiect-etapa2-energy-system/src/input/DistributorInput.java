package input;

import strategies.EnergyChoiceStrategyType;

public final class DistributorInput {
    private int id;
    private int contractLength;
    private int initialBudget;
    private int initialInfrastructureCost;
    private int energyNeededKW;
    private strategies.EnergyChoiceStrategyType producerStrategy;

    /**
     * Getter for the distributor's id
     */
    public int getId() {
        return id;
    }
    /**
     * Setter for the distributor's id
     * @param id - the distributor's id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Getter for the distributor's contract length
     */
    public int getContractLength() {
        return contractLength;
    }
    /**
     * Setter for the distributor's contract length
     * @param contractLength - the distributor's standard contract length
     */
    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    /**
     * Getter for the distributor's initial budget
     */
    public int getInitialBudget() {
        return initialBudget;
    }
    /**
     * Setter for the distributor's initial budget
     * @param initialBudget - the distributor's initial budget
     */
    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    /**
     * Getter for the distributor's initial infrastructure cost
     */
    public int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }
    /**
     * Setter for the distributor's initial infrastructure cost
     * @param initialInfrastructureCost - the distributor's initial infrastructure cost
     */
    public void setInitialInfrastructureCost(final int initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }
}

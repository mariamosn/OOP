package distributor;

import entity.Entity;
import input.DistributorInput;
import producer.Producer;
import strategies.EnergyChoiceStrategyType;
import strategies.ProducerChooser;
import strategies.StrategyFactory;

import java.util.ArrayList;
import java.util.List;

public final class Distributor extends Entity {
    public static final double PROFIT_PERCENT = 0.2;
    private int contractLength;
    private int infrastructureCost;
    private int productionCost;
    private List<Contract> contracts;
    private List<Producer> currentProducers;
    private int energyNeededKW;
    private strategies.EnergyChoiceStrategyType producerStrategy;
    private final ProducerChooser strategy;
    private final ProducerUpdate flag;
    private int currentContractCost;

    public Distributor(final DistributorInput distributorInput) {
        super(distributorInput.getId(), distributorInput.getInitialBudget());
        this.contractLength = distributorInput.getContractLength();
        this.infrastructureCost = distributorInput.getInitialInfrastructureCost();
        this.productionCost = 0;
        this.energyNeededKW = distributorInput.getEnergyNeededKW();
        this.producerStrategy = distributorInput.getProducerStrategy();
        this.contracts = new ArrayList<>();
        this.currentProducers = new ArrayList<>();
        this.strategy = StrategyFactory.createStrategy(producerStrategy);
        this.flag = new ProducerUpdate();
    }

    /**
     * Getter for the length of the distributor's contract
     */
    public int getContractLength() {
        return contractLength;
    }
    /**
     * Setter for the length of the distributor's contract
     * @param contractLength - the distributor's standard contract length
     */
    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    /**
     * Getter for the current cost of the infrastructure
     */
    public int getInfrastructureCost() {
        return infrastructureCost;
    }
    /**
     * Setter for the current cost of the infrastructure
     * @param infrastructureCost - the new infrastructure cost that the distributor has to pay
     */
    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     * Getter for the current cost of the production (per consumer)
     */
    public int getProductionCost() {
        return productionCost;
    }
    /**
     * Setter for the current cost of the production (per consumer)
     * @param productionCost - the new production cost that the distributor
     *                       has to pay for each consumer
     */
    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    /**
     * Getter for the distributor's current list of contracts
     */
    public List<Contract> getContracts() {
        return contracts;
    }
    /**
     * Setter for the distributor's current list of contracts
     * @param contracts - the new list of contracts
     */
    public void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }

    public List<Producer> getCurrentProducers() {
        return currentProducers;
    }

    public void setCurrentProducers(List<Producer> currentProducers) {
        this.currentProducers = currentProducers;
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

    public ProducerChooser getStrategy() {
        return strategy;
    }

    public ProducerUpdate getFlag() {
        return flag;
    }

    public int getCurrentContractCost() {
        return currentContractCost;
    }

    public void setCurrentContractCost(int currentContractCost) {
        this.currentContractCost = currentContractCost;
    }

    /**
     * The method calculates the price of the current offer of the distributor
     * @return - the current price of the distributor's contract offer
     */
    public int getCurrentPrice() {
        int profit = (int) Math.round(Math.floor(PROFIT_PERCENT * productionCost));
        if (contracts.size() == 0) {
            return infrastructureCost + productionCost + profit;
        } else {
            //noinspection IntegerDivisionInFloatingPointContext
            return (int) Math.round(Math.floor(infrastructureCost / contracts.size())
                    + productionCost + profit);
        }
    }
}

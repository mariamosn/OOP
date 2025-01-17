package consumer;

import distributor.Contract;
import distributor.Distributor;
import entity.Entity;
import input.ConsumerInput;

public final class Consumer extends Entity {
    private int monthlyIncome;
    private int leftToPay;
    private Contract contract = null;
    private Distributor distributorLeftToPay = null;

    public Consumer(final ConsumerInput consumerInput) {
        super(consumerInput.getId(), consumerInput.getInitialBudget());
        this.monthlyIncome = consumerInput.getMonthlyIncome();
        this.leftToPay = 0;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    /**
     * Getter for the consumer's current sum left to pay
     */
    public int getLeftToPay() {
        return leftToPay;
    }
    /**
     * Setter for the consumer's current sum left to pay
     * @param sum - the amount that is left to pay after the consumer
     *            wasn't able to pay the bill in a month
     */
    public void setLeftToPay(final int sum) {
        leftToPay = sum;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    public Distributor getDistributorLeftToPay() {
        return distributorLeftToPay;
    }

    public void setDistributorLeftToPay(Distributor distributorLeftToPay) {
        this.distributorLeftToPay = distributorLeftToPay;
    }
}

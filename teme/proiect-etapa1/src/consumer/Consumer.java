package consumer;

import distributor.Contract;
import entity.Entity;
import input.ConsumerInput;

public class Consumer extends Entity {
    private int monthlyIncome;
    private int leftToPay;
    private Contract contract = null;

    public Consumer(final ConsumerInput consumerInput) {
        super(consumerInput.getId(), consumerInput.getInitialBudget());
        this.monthlyIncome = consumerInput.getMonthlyIncome();
        this.leftToPay = 0;
    }

    /**
     * Getter for the consumer's monthly income
     */
    public int getMonthlyIncome() {
        return monthlyIncome;
    }
    /**
     * Setter for the consumer's monthly income
     * @param monthlyIncome - the consumer's monthly income
     */
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

    /**
     * Getter for the consumer's current contract
     */
    public Contract getContract() {
        return contract;
    }
    /**
     * Setter for the consumer's current contract
     * @param contract - the consumer's new contract
     */
    public void setContract(final Contract contract) {
        this.contract = contract;
    }
}

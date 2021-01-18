package input;

public class ConsumerInput {
    private int id;
    private int initialBudget;
    private int monthlyIncome;

    /**
     * Getter for the consumer's id
     */
    public int getId() {
        return id;
    }
    /**
     * Setter for the consumer's id
     * @param id - the consumer's id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Getter for the consumer's initial budget
     */
    public int getInitialBudget() {
        return initialBudget;
    }
    /**
     * Setter for the consumer's initial budget
     * @param initialBudget - the consumer's initial budget
     */
    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
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
}

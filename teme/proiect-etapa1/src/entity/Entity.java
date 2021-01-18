package entity;

public abstract class Entity {
    private int id;
    private int budget;
    private boolean isBankrupt;

    protected Entity(final int id, final int initialBudget) {
        this.id = id;
        this.budget = initialBudget;
        this.isBankrupt = false;
    }

    /**
     * Getter for the entity's id
     */
    public int getId() {
        return id;
    }
    /**
     * Setter for the entity's id
     * @param id - the entity's new id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Getter for the entity's current budget
     */
    public int getBudget() {
        return budget;
    }
    /**
     * Setter for the entity's current budget
     * @param budget - the entity's new budget
     */
    public void setBudget(final int budget) {
        this.budget = budget;
    }

    /**
     * Getter for the entity's current financial status (bankrupt or not)
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }
    /**
     * Setter for the entity's current financial status (bankrupt or not)
     * @param bankrupt - the entity's new financial status (bankrupt or not)
     */
    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }
}

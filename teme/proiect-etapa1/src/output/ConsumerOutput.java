package output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import consumer.Consumer;

@JsonPropertyOrder({ "id", "isBankrupt", "budget" })
public class ConsumerOutput {
    @JsonProperty("id")
    private int id;
    @JsonProperty("isBankrupt")
    private final boolean isBankrupt;
    @JsonProperty("budget")
    private int budget;

    public ConsumerOutput(final Consumer original) {
        this.id = original.getId();
        this.isBankrupt = original.isBankrupt();
        this.budget = original.getBudget();
    }

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
     * Getter for the consumer's final financial status (bankrupt or not)
     */
    @JsonProperty(value = "isBankrupt")
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /**
     * Getter for the consumer's final budget
     */
    public int getBudget() {
        return budget;
    }
    /**
     * Setter for the consumer's final budget
     * @param budget - the consumer's final budget
     */
    public void setBudget(final int budget) {
        this.budget = budget;
    }
}

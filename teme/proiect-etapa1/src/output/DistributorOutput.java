package output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import distributor.Contract;
import distributor.Distributor;

import java.util.List;

@JsonPropertyOrder({ "id", "budget", "isBankrupt", "contracts" })
public class DistributorOutput {
    @JsonProperty("id")
    private int id;
    @JsonProperty("budget")
    private int budget;
    @JsonProperty("isBankrupt")
    private final boolean isBankrupt;
    @JsonProperty("contracts")
    private List<Contract> contracts;

    public DistributorOutput(final Distributor original) {
        this.id = original.getId();
        this.budget = original.getBudget();
        this.isBankrupt = original.isBankrupt();
        this.contracts = original.getContracts();
    }

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
     * Getter for the distributor's final financial status (bankrupt or not)
     */
    @JsonProperty(value = "isBankrupt")
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /**
     * Getter for the distributor's final budget
     */
    public int getBudget() {
        return budget;
    }
    /**
     * Setter for the distributor's final budget
     * @param budget - the distributor's final budget
     */
    public void setBudget(final int budget) {
        this.budget = budget;
    }

    /**
     * Getter for the distributor's current contracts
     */
    public List<Contract> getContracts() {
        return contracts;
    }
    /**
     * Setter for the distributor's current contracts
     * @param contracts - the final list of contracts
     */
    public void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }
}

package distributor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "consumerId", "price", "remainedContractMonths" })
public class Contract {
    @JsonProperty("consumerId")
    private int consumerId;
    @JsonProperty("price")
    private int price;
    @JsonProperty("remainedContractMonths")
    private int remainedContractMonths;

    public Contract(final int consumerId, final int price, final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    /**
     * Getter for the consumer's id
     */
    public int getConsumerId() {
        return consumerId;
    }
    /**
     * Setter for the consumer's id
     * @param consumerId - consumer's id
     */
    public void setConsumerId(final int consumerId) {
        this.consumerId = consumerId;
    }

    /**
     * Getter for the contract's price
     */
    public int getPrice() {
        return price;
    }
    /**
     * Setter for the contract's price
     * @param price - the contract's price
     */
    public void setPrice(final int price) {
        this.price = price;
    }

    /**
     * Getter for the number of months left until the contract expires
     */
    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
    /**
     * Setter for the number of months left until the contract expires
     * @param remainedContractMonths - the initial number of months of the contract
     */
    public void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }
}

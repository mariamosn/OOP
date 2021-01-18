package strategies;

public final class StrategyFactory {
    private StrategyFactory() {
    }

    /**
     * The method creates a new instance of a specific strategy, based on the type.
     * @param type - the required type of strategy
     * @return - an instance of the required strategy type
     */
    public static ProducerChooser createStrategy(EnergyChoiceStrategyType type) {
        ProducerChooser strategy = null;
        if (type == EnergyChoiceStrategyType.GREEN) {
            strategy = new GreenStrategy();
        } else if (type == EnergyChoiceStrategyType.PRICE) {
            strategy = new PriceStrategy();
        } else if (type == EnergyChoiceStrategyType.QUANTITY) {
            strategy = new QuantityStrategy();
        }
        return strategy;
    }
}

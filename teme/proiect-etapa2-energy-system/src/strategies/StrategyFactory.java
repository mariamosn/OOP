package strategies;

public class StrategyFactory {
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

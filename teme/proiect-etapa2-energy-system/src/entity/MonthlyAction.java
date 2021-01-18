package entity;

import consumer.Consumer;
import distributor.Contract;
import distributor.Distributor;
import input.ConsumerInput;
import input.DistributorChange;
import input.MonthlyUpdates;
import input.ProducerChange;
import output.MonthlyStatOutput;
import producer.Producer;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyAction {
    private final DataBase db = DataBase.getInstance();
    private static final double PENALTY = 1.2;

    /**
     * The method contains all the actions that need to be done in a month
     * @param month - the month's number
     * @return - false if all distributors are bankrupt or true otherwise
     */
    public boolean monthAction(final int month) {
        // make updates based on the input
        if (month != 0) {
            makeUpdates(month);
        }

        if (month == 0) {
            chooseProducers();
            getProductionPrices();
        }

        // calculate the new contract offers and the best deal
        Distributor bestDistributor = null;
        int bestPrice = -1;
        for (Distributor distributor
                : db.getDistributors()) {
            distributor.setCurrentContractCost(distributor.getCurrentPrice());
            if ((bestPrice == -1 || bestPrice > distributor.getCurrentPrice())
                    && !distributor.isBankrupt()) {
                bestPrice = distributor.getCurrentPrice();
                bestDistributor = distributor;
            }
        }

        // get rid of the expired contracts
        freeOldContracts();

        // find contracts for the consumers that don't have one (and are not bankrupt)
        getNewContracts(bestDistributor, bestPrice);

        // make infrastructure and production payments (distributors)
        distributorPayments();

        // make contract payments (consumers)
        payCurrentContracts();

        if (month != 0) {
            updateProducers(month);
        }

        if (month != 0) {
            chooseProducers();
            getProductionPrices();
            producerStatUpdate(month);
        }

        // check if there are any distributors (that are not bankrupt) left
        return checkDistributors();
    }

    /**
     * The method adds consumers or changes the distributors' costs based on the input
     * @param month - the current month
     */
    private void makeUpdates(final int month) {
        MonthlyUpdates updates = db.getMonthlyUpdates().get(month - 1);

        // add the new consumers
        List<ConsumerInput> newConsumers = updates.getNewConsumers();
        List<Consumer> consumers = db.getConsumers();
        for (ConsumerInput consumer
                : newConsumers) {
            consumers.add((Consumer) EntityFactory.createEntity("consumer", consumer));
        }
        db.setConsumers(consumers);

        // apply cost changes (for distributors)
        List<DistributorChange> distributorChanges = updates.getDistributorChanges();
        for (DistributorChange change
                : distributorChanges) {
            Distributor distributor = db.getDistributor(change.getId());
            assert distributor != null;
            distributor.setInfrastructureCost(change.getInfrastructureCost());
            // distributor.setProductionCost(change.getProductionCost());
        }
    }

    /**
     * The method removes all the expired contracts from distributors' lists
     * and from the consumers' information
     */
    private void freeOldContracts() {
        for (Distributor distributor
                : db.getDistributors()) {
            List<Contract> contracts = distributor.getContracts();
            for (int i = 0; contracts != null && i < contracts.size(); i++) {
                Contract contract = contracts.get(i);
                Consumer consumer = db.getConsumer(contract.getConsumerId());
                assert consumer != null;
                if (consumer.getContract().getRemainedContractMonths() == 0) {
                    consumer.setContract(null);
                    contracts.remove(i);
                    i--;
                }
            }
        }
    }

    /**
     * The method creates new contracts for those consumers that don't have one
     * based on the best deal available in the current month
     * @param bestDistributor - the distributor that offers the best deal
     * @param bestPrice - the price of the best deal
     */
    private void getNewContracts(final Distributor bestDistributor, final int bestPrice) {
        for (Consumer consumer
                : db.getConsumers()) {
            if (consumer.getContract() == null && !consumer.isBankrupt()) {
                Contract newContract = new Contract(consumer.getId(),
                        bestPrice, bestDistributor.getContractLength());
                List<Contract> contracts = bestDistributor.getContracts();
                contracts.add(newContract);
                bestDistributor.setContracts(contracts);
                consumer.setContract(newContract);
            }
        }
    }

    /**
     * The method is responsible for the distributors' payments
     * (infrastructure and production costs)
     */
    private void distributorPayments() {
        for (Distributor distributor
                : db.getDistributors()) {
            if (!distributor.isBankrupt()) {
                int budget = distributor.getBudget();
                // pay the infrastructure cost
                budget -= distributor.getInfrastructureCost();

                // pay the production costs
                if (!distributor.getContracts().isEmpty()) {
                    budget -= distributor.getContracts().size() * distributor.getProductionCost();
                    // System.out.println(distributor.getProductionCost());
                }
                // budget -= distributor.getProductionCost();

                // update the distributor's budget
                distributor.setBudget(budget);
            }
        }
    }

    /**
     * The method is responsible for the consumers' payments
     */
    private void payCurrentContracts() {
        for (Distributor distributor
                : db.getDistributors()) {
            if (!distributor.isBankrupt()) {
                List<Contract> contracts = distributor.getContracts();
                for (int i = 0; !contracts.isEmpty() && i < contracts.size(); i++) {
                    boolean remove = false;
                    Contract contract = contracts.get(i);
                    Consumer consumer = db.getConsumer(contract.getConsumerId());

                    // calculate the current available budget
                    assert consumer != null;
                    int budget = consumer.getBudget();
                    budget += consumer.getMonthlyIncome();

                    if (consumer.getLeftToPay() == 0) {
                        // the consumer is up to date with the payments
                            // and can affords to pay the bill
                        if (budget >= contract.getPrice()) {
                            budget -= contract.getPrice();
                            distributor.setBudget(distributor.getBudget() + contract.getPrice());

                        // the consumer is up to date with the payments,
                            // but can't afford to pay the bill
                        } else {
                            consumer.setLeftToPay(contract.getPrice());
                            consumer.setDistributorLeftToPay(distributor);
                        }
                    } else {
                        int toPay = (int) Math.round(Math.floor(PENALTY * consumer.getLeftToPay()))
                                + contract.getPrice();
                        int partialPay = (int) Math.round(Math.floor(PENALTY * consumer.getLeftToPay()));

                        // the consumer isn't up to date with the payments, but can affords to pay
                        if (toPay <= budget) {
                            budget -= toPay;
                            consumer.setLeftToPay(0);
                            Distributor restantDistr = consumer.getDistributorLeftToPay();
                            restantDistr.setBudget(restantDistr.getBudget() + partialPay);
                            consumer.setDistributorLeftToPay(null);
                            distributor.setBudget(contract.getPrice());

                        // the consumer can only afford to pay the old contract
                        } else if (partialPay <= budget && distributor != consumer.getDistributorLeftToPay()) {
                            budget -= partialPay;
                            consumer.setLeftToPay(contract.getPrice());
                            Distributor restantDistr = consumer.getDistributorLeftToPay();
                            restantDistr.setBudget(restantDistr.getBudget() + partialPay);
                            consumer.setDistributorLeftToPay(distributor);

                        // the consumer is bankrupt
                        } else {
                            consumer.setBankrupt(true);
                            remove = true;
                        }
                    }

                    consumer.setBudget(budget);
                    contract.setRemainedContractMonths(contract.getRemainedContractMonths() - 1);

                    // if the consumer is now bankrupt, the contract must be removed
                    if (remove) {
                        contracts.remove(i);
                        consumer.setContract(null);
                        i--;
                    }
                }
            }
        }
    }

    /**
     * The method checks the distributors' financial status;
     * it checks if there are any new bankrupt distributors
     * and removes all of their contracts;
     * it also checks if there are any distributors left that are not bankrupt.
     * @return - true if there are distributors left that are not bankrupt.
     */
    private boolean checkDistributors() {
        boolean ok = false;
        for (Distributor distributor
                : db.getDistributors()) {
            if (!distributor.isBankrupt() && distributor.getBudget() < 0) {
                distributor.setBankrupt(true);
                List<Contract> contracts = distributor.getContracts();
                for (int i = 0; !contracts.isEmpty() && i < contracts.size(); i++) {
                    Consumer consumer = db.getConsumer(contracts.get(i).getConsumerId());
                    assert consumer != null;
                    consumer.setLeftToPay(0);
                    consumer.setContract(null);
                    contracts.remove(i);
                }
            }
            if (!distributor.isBankrupt()) {
                ok = true;
            }
        }
        return ok;
    }

    private void chooseProducers() {
        for (Distributor distributor
             : db.getDistributors()) {
            if (distributor.getFlag().getIsUpdated()) {
                removePrevProducers(distributor);
                List<Producer> producers = distributor.getStrategy().chooseProducers(distributor);
                distributor.setCurrentProducers(producers);

                for (Producer producer
                        : producers) {
                    producer.getEnergy().getDistributors().add(distributor);
                    producer.getEnergy().addObserver(distributor.getFlag());
                }
                distributor.getFlag().reset();
            }
        }
    }

    private void removePrevProducers(Distributor distributor) {
        for (Producer prod
                : distributor.getCurrentProducers()) {
            for (int i = 0; i < prod.getEnergy().getDistributors().size(); i++) {
                if (prod.getEnergy().getDistributors().get(i) == distributor) {
                    prod.getEnergy().getDistributors().remove(i);
                    prod.getEnergy().deleteObserver(distributor.getFlag());
                }
            }
        }
    }

    private void getProductionPrices() {
        for (Distributor distributor
                : db.getDistributors()) {
            double cost = 0;
            for (Producer producer
                    : distributor.getCurrentProducers()) {
                cost += producer.getEnergy().getEnergyPerDistributor() * producer.getPriceKW();
            }
            int productionCost = (int) Math.round(Math.floor(cost / 10));
            distributor.setProductionCost(productionCost);
        }
    }

    private void updateProducers(final int month) {
        MonthlyUpdates updates = db.getMonthlyUpdates().get(month - 1);
        for (ProducerChange change
                : updates.getProducerChanges()) {
            Producer producer = db.getProducer(change.getId());
            producer.getEnergy().setEnergyPerDistributor(change.getEnergyPerDistributor());
        }
    }

    private void producerStatUpdate(int month) {
        for (Producer prod
                : db.getProducers()) {
            List<Integer> distributorsIds = new ArrayList<>();
            for (Distributor distributor
                    : prod.getEnergy().getDistributors()) {
                distributorsIds.add(distributor.getId());
            }

            prod.getMonthlyStats().add(new MonthlyStatOutput(month, distributorsIds));
        }
    }
}

package org.sale.project.enums;

public enum ActionType {
    SEARCH(0.2),
    VIEW(0.3),
    ADD_TO_CART(0.7),
    PURCHASE(1.0),
    RETURN(-2.5);


    private final double weight;

    ActionType(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}

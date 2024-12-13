package com.Kaushik.Exchange.utils.constants;

public class OrderConstants {

    public static final String CREATE_ORDER = "CREATE_ORDER";
    public static final String CANCEL_ORDER = "CANCEL_ORDER";
    public static final String ON_RAMP = "ON_RAMP";
    public static final String GET_OPEN_ORDERS = "GET_OPEN_ORDERS";
    public static final String GET_DEPTH = "GET_DEPTH";

    // Prevent instantiation
    private OrderConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}

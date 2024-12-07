package com.Kaushik.Exchange.service;

import com.Kaushik.Exchange.model.Fill;
import com.Kaushik.Exchange.model.Order;
import com.Kaushik.Exchange.model.OrderBook;
import com.Kaushik.Exchange.model.OrderBookEntry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class orderService {

    private static final String BASE_ASSET = "BTC";
    private static final String QUOTE_ASSET = "USD";
    private static final OrderBook orderBook = new OrderBook();
    private static int globalTradeId = 0;

    public Map<String, Object> fillOrder(@NotNull Order order) {
        if (!isValidAssets(order.getBaseAsset(), order.getQuoteAsset())) {
            return Map.of("error", "Invalid base or quote asset");
        }

        String orderId = generateOrderId();
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);

        List<Fill> fills = new ArrayList<>();
        double executedQuantity = fillOrderMatching(orderId, order.getPrice(), order.getQuantity(), order.getSide(), order.getType(), fills);

        result.put("executedQty", executedQuantity);
        result.put("fills", fills);

        return result;
    }

    private boolean isValidAssets(@org.jetbrains.annotations.NotNull String baseAsset, String quoteAsset) {
        return baseAsset.equals(BASE_ASSET) && quoteAsset.equals(QUOTE_ASSET);
    }

    private @NotNull String generateOrderId() {
        return randomHexString(8) + randomHexString(8);
    }

    @Contract("_ -> new")
    private @NotNull String randomHexString(int length) {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }

    private double fillOrderMatching(String orderId, double price, double quantity, @NotNull String side, String type, List<Fill> fills) {
        double executedQuantity = 0.0;
        if (side.equals("buy")) {
            executedQuantity = fillBuyOrder(orderId, price, quantity, fills);
        } else {
            executedQuantity = fillSellOrder(orderId, price, quantity, fills);
        }

        if (type != null && type.equals("ioc") && executedQuantity < quantity) {
            return executedQuantity;
        }

        if (executedQuantity < quantity) {
            addOrderToBook(orderId, price, quantity - executedQuantity, side);
        }

        return executedQuantity;
    }

    private double fillBuyOrder(String orderId, double price, double quantity, List<Fill> fills) {
        double executedQuantity = 0.0;
        for (OrderBookEntry ask : orderBook.getAsks()) {
            if (ask.getPrice() <= price && quantity > 0) {
                double filledQuantity = Math.min(quantity, ask.getQuantity());
                ask.setQuantity(ask.getQuantity() - filledQuantity);
                orderBook.updateAskQuantity(ask.getPrice(), ask.getQuantity());
                fills.add(new Fill(ask.getPrice(), filledQuantity, globalTradeId++));
                executedQuantity += filledQuantity;
                quantity -= filledQuantity;
                if (ask.getQuantity() == 0) {
                    orderBook.removeAsk(ask);
                }
            }
        }
        return executedQuantity;
    }

    private double fillSellOrder(String orderId, double price, double quantity, List<Fill> fills) {
        double executedQuantity = 0.0;
        for (OrderBookEntry bid : orderBook.getBids()) {
            if (bid.getPrice() >= price && quantity > 0) {
                double filledQuantity = Math.min(quantity, bid.getQuantity());
                bid.setQuantity(bid.getQuantity() - filledQuantity);
                orderBook.updateBidQuantity(bid.getPrice(), bid.getQuantity());
                fills.add(new Fill(bid.getPrice(), filledQuantity, globalTradeId++));
                executedQuantity += filledQuantity;
                quantity -= filledQuantity;
                if (bid.getQuantity() == 0) {
                    orderBook.removeBid(bid);
                }
            }
        }
        return executedQuantity;
    }

    private void addOrderToBook(String orderId, double price, double quantity, String side) {
        if (side.equals("buy")) {
            orderBook.addBid(new OrderBookEntry(price, quantity, orderId));
        } else {
            orderBook.addAsk(new OrderBookEntry(price, quantity, orderId));
        }
    }
}


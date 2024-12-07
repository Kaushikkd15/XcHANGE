package com.Kaushik.Exchange.model;



import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBook {

    private List<OrderBookEntry> bids = new ArrayList<>();
    private Map<Double, Integer> askQuantity = new HashMap<>();
    private Map<Double, Integer> bidQuantity = new HashMap<>();

    private List<OrderBookEntry> asks = new ArrayList<>();

    public List<OrderBookEntry> getAsks() {
        return asks;
    }

    public void setAsks(List<OrderBookEntry> asks) {
        this.asks = asks;
    }

    public void setBids(List<OrderBookEntry> bids) {
        this.bids = bids;
    }

    public Map<Double, Integer> getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(Map<Double, Integer> askQuantity) {
        this.askQuantity = askQuantity;
    }

    public Map<Double, Integer> getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Map<Double, Integer> bidQuantity) {
        this.bidQuantity = bidQuantity;
    }



    public List<OrderBookEntry> getBids() {
        return bids;
    }

    public void addAsk(OrderBookEntry ask) {
        asks.add(ask);
        askQuantity.merge(ask.getPrice(), (int) ask.getQuantity(), Integer::sum);
    }

    public void addBid(OrderBookEntry bid) {
        bids.add(bid);
        bidQuantity.merge(bid.getPrice(), (int) bid.getQuantity(), Integer::sum);
    }

    public void removeAsk(OrderBookEntry ask) {
        asks.remove(ask);
        askQuantity.put(ask.getPrice(), askQuantity.get(ask.getPrice()) - (int) ask.getQuantity());
        if (askQuantity.get(ask.getPrice()) == 0) {
            askQuantity.remove(ask.getPrice());
        }
    }

    public void removeBid(OrderBookEntry bid) {
        bids.remove(bid);
        bidQuantity.put(bid.getPrice(), bidQuantity.get(bid.getPrice()) - (int) bid.getQuantity());
        if (bidQuantity.get(bid.getPrice()) == 0) {
            bidQuantity.remove(bid.getPrice());
        }
    }

    public void updateAskQuantity(double price, double quantity) {
        askQuantity.put(price, (int) quantity);
    }

    public void updateBidQuantity(double price, double quantity) {
        bidQuantity.put(price, (int) quantity);
    }
}
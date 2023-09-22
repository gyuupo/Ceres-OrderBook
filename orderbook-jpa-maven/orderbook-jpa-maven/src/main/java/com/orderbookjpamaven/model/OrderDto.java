package com.orderbookjpamaven.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderDto {

    private int id;


    private int size;


    private boolean side; // buy - 1 sell - 0


    private LocalDateTime orderTime;


    private boolean activeOrder; // active - 1 inactive - 0


    private BigDecimal offerPrice;


    private String symbol;

    public OrderDto(int id, int size, boolean side, LocalDateTime orderTime, boolean activeOrder, BigDecimal offerPrice, String symbol) {
        this.id = id;
        this.size = size;
        this.side = side;
        this.orderTime = orderTime;
        this.activeOrder = activeOrder;
        this.offerPrice = offerPrice;
        this.symbol = symbol;
    }

    // public no-args constructor for entities
    public OrderDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSide() {
        return side;
    }

    public void setSide(boolean side) {
        this.side = side;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public boolean isActiveOrder() {
        return activeOrder;
    }

    public void setActiveOrder(boolean activeOrder) {
        this.activeOrder = activeOrder;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return id == orderDto.id && size == orderDto.size && side == orderDto.side && activeOrder == orderDto.activeOrder && Objects.equals(orderTime, orderDto.orderTime) && Objects.equals(offerPrice, orderDto.offerPrice) && Objects.equals(symbol, orderDto.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, side, orderTime, activeOrder, offerPrice, symbol);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", size=" + size +
                ", side=" + side +
                ", orderTime=" + orderTime +
                ", activeOrder=" + activeOrder +
                ", offerPrice=" + offerPrice +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}

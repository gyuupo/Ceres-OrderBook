package com.orderbookjpamaven.entities;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Entity
@Table(name = "`order_details`")
public class OrderEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderId")
    private int id;

    @Column
    private int size;

    @Column
    private boolean side; // buy - 1 sell - 0

    @Column
    private LocalDateTime orderTime;

    @Column
    private boolean activeOrder; // active - 1 inactive - 0

    @Column(name = "offerPrice")
    private BigDecimal offerPrice;

    @Column(name = "symbol")
    private String symbol;

    // public no-args constructor for entities
    public OrderEntity() {
    }
    public OrderEntity(int id, int size, boolean side, LocalDateTime orderTime, boolean activeOrder, BigDecimal offerPrice, String symbol) {
        this.id = id;
        this.size = size;
        this.side = side;
        this.orderTime = orderTime;
        this.activeOrder = activeOrder;
        this.offerPrice = offerPrice;
        this.symbol = symbol;
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

        OrderEntity orderEntity = (OrderEntity) o;

        if (id != orderEntity.id) return false;
        if (size != orderEntity.size) return false;
        if (side != orderEntity.side) return false;
        if (activeOrder != orderEntity.activeOrder) return false;
        if (!orderTime.equals(orderEntity.orderTime)) return false;
        if (!offerPrice.equals(orderEntity.offerPrice)) return false;
        return symbol.equals(orderEntity.symbol);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + size;
        result = 31 * result + (side ? 1 : 0);
        result = 31 * result + orderTime.hashCode();
        result = 31 * result + (activeOrder ? 1 : 0);
        result = 31 * result + offerPrice.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
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
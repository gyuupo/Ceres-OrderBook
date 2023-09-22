package com.orderbookjpamaven.model;

import com.orderbookjpamaven.entities.OrderEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class TransactionDto {



    private int transactionId;


    private OrderEntity buyOrderIdEntity;


     //foreign key
    private OrderEntity sellOrderIdEntity;

    private Timestamp finalTime;


    private BigDecimal finalPrice;


    private int amount;


    private String finalSymbol;

    public TransactionDto(int transactionId, OrderEntity buyOrderIdEntity, OrderEntity sellOrderIdEntity, Timestamp finalTime, BigDecimal finalPrice, int amount, String finalSymbol) {
        this.transactionId = transactionId;
        this.buyOrderIdEntity = buyOrderIdEntity;
        this.sellOrderIdEntity = sellOrderIdEntity;
        this.finalTime = finalTime;
        this.finalPrice = finalPrice;
        this.amount = amount;
        this.finalSymbol = finalSymbol;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public OrderEntity getBuyOrderId() {
        return buyOrderIdEntity;
    }

    public void setBuyOrderId(OrderEntity buyOrderIdEntity) {
        this.buyOrderIdEntity = buyOrderIdEntity;
    }

    public OrderEntity getSellOrderId() {
        return sellOrderIdEntity;
    }

    public void setSellOrderId(OrderEntity sellOrderIdEntity) {
        this.sellOrderIdEntity = sellOrderIdEntity;
    }

    public Timestamp getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(Timestamp finalTime) {
        this.finalTime = finalTime;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFinalSymbol() {
        return finalSymbol;
    }

    public void setFinalSymbol(String finalSymbol) {
        this.finalSymbol = finalSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto that = (TransactionDto) o;
        return transactionId == that.transactionId && amount == that.amount && Objects.equals(buyOrderIdEntity, that.buyOrderIdEntity) && Objects.equals(sellOrderIdEntity, that.sellOrderIdEntity) && Objects.equals(finalTime, that.finalTime) && Objects.equals(finalPrice, that.finalPrice) && Objects.equals(finalSymbol, that.finalSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, buyOrderIdEntity, sellOrderIdEntity, finalTime, finalPrice, amount, finalSymbol);
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "transactionId=" + transactionId +
                ", buyOrderId=" + buyOrderIdEntity +
                ", sellOrderId=" + sellOrderIdEntity +
                ", finalTime=" + finalTime +
                ", finalPrice=" + finalPrice +
                ", amount=" + amount +
                ", finalSymbol='" + finalSymbol + '\'' +
                '}';
    }
}

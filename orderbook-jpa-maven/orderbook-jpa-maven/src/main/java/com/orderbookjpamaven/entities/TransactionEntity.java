package com.orderbookjpamaven.entities;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table( name = "`transaction_details`" )
public class TransactionEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "transactionId")
    private int transactionId;
    @ManyToOne
    @JoinColumn(name = "buyOrderId")//foreign key
    private OrderEntity buyOrderIdEntity;

    @ManyToOne
    @JoinColumn(name = "sellOrderId") //foreign key
    private OrderEntity sellOrderIdEntity;
    @Column(name = "finalTime")
    private Timestamp finalTime;

    @Column(name = "finalPrice")
    private BigDecimal finalPrice;

    @Column(name = "amount")
    private int amount;

    @Column(name = "finalSymbol")
    private String finalSymbol;

    public TransactionEntity() {
    }
    public TransactionEntity(int transactionId, OrderEntity buyOrderIdEntity, OrderEntity sellOrderIdEntity, Timestamp finalTime, BigDecimal finalPrice, int amount, String finalSymbol) {
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

        TransactionEntity that = (TransactionEntity) o;

        if (transactionId != that.transactionId) return false;
        if (amount != that.amount) return false;
        if (!buyOrderIdEntity.equals(that.buyOrderIdEntity)) return false;
        if (!sellOrderIdEntity.equals(that.sellOrderIdEntity)) return false;
        if (!finalTime.equals(that.finalTime)) return false;
        if (!finalPrice.equals(that.finalPrice)) return false;
        return finalSymbol.equals(that.finalSymbol);
    }

    @Override
    public int hashCode() {
        int result = transactionId;
        result = 31 * result + buyOrderIdEntity.hashCode();
        result = 31 * result + sellOrderIdEntity.hashCode();
        result = 31 * result + finalTime.hashCode();
        result = 31 * result + finalPrice.hashCode();
        result = 31 * result + amount;
        result = 31 * result + finalSymbol.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
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


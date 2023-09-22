package com.orderbookjpamaven.service;

import com.orderbookjpamaven.model.OrderDto;
import com.orderbookjpamaven.model.TransactionDto;
import org.springframework.stereotype.Component;

import com.orderbookjpamaven.entities.OrderEntity;

import java.time.LocalDate;
import java.util.List;

@Component
public interface ServiceLayer {
//    public List<Order> getAllOrders();
//
//    public List<Order> getAllActiveOrders();

    public List<OrderDto> getAllBuyOrdersForSymbol(String symbol);

    public List<OrderDto> getAllSellOrdersForSymbol(String symbol);

    public List<TransactionDto> getAllTransactions();

    public List<TransactionDto> getAllTransactionsForSymbol(String symbol);

    public List<TransactionDto> getTop5ByFinalDate();

    public List<TransactionDto> getAllTransactionsForSymbolAndDate(String symbol, LocalDate date);

    public void deleteUnmatchedOrder(int orderId);

    public TransactionDto makeTransaction(OrderEntity buyOrderEntity, OrderEntity sellOrderEntity);

    public TransactionDto matchOrders(int givenOrderId);

    public List findPotentialTransactions(String symbol);

    public List getSymbols();

    public void createOrderbook(String symbol);

    public OrderDto addOrder(OrderDto newOrder);

    public OrderDto getOrderById(int orderId);

    public void incrementBuyOrders(String tick, String symbol);

    public void decrementSellOrders(String tick, String symbol);
}


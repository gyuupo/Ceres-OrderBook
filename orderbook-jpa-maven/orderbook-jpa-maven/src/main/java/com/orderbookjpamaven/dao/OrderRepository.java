package com.orderbookjpamaven.dao;

import com.orderbookjpamaven.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {


    @Query("SELECT o FROM Order o WHERE o.size > 0")
    List<OrderEntity> findAllActiveOrders();

    @Query("SELECT o FROM Order o WHERE o.side = 1 AND o.size > 0 AND o.symbol = :symbol ORDER BY o.offerPrice DESC, time ASC")
    List<OrderEntity> findAllBuyOrdersForSymbol(@Param("symbol") String symbol);

    @Query("SELECT o FROM Order o WHERE o.side = 0 AND o.size > 0 AND o.symbol = :symbol ORDER BY o.offerPrice ASC, time ASC")
    List<OrderEntity> findAllSellOrdersForSymbol(@Param("symbol") String symbol);

    @Query("SELECT DISTINCT symbol FROM Order o ORDER BY o.symbol ASC")
    List getSymbols();
}

package com.orderbookjpamaven.dao;


import com.orderbookjpamaven.entities.OrderEntity;
import com.orderbookjpamaven.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    List findByFinalSymbolOrderByFinalTimeDesc(String symbol);

    List findByFinalTime(Timestamp finalTime);

    @Query("SELECT t FROM Transaction t WHERE t.buyOrderId = :order OR t.sellOrderID = :order")
    List findAllTransactionsForOrder(@Param("order") OrderEntity orderEntity);
// when you call this method with an Order object, it will execute the JPQL query with the
// appropriate parameters and return a list of matching Transaction objects.
    List findTop5ByOrderByFinalTimeDesc();

    @Query(value = "SELECT * FROM Transaction t WHERE t.finalSymbol = :symbol AND CAST(t.finalTime AS DATE) = :date", nativeQuery = true)
    List<TransactionEntity> findAllTransactionsForSymbolAndDate(@Param("symbol") String symbol, @Param("date") LocalDate date);
}


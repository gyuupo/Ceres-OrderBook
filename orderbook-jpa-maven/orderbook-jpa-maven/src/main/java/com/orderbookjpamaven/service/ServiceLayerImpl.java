package com.orderbookjpamaven.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.orderbookjpamaven.dao.OrderRepository;
import com.orderbookjpamaven.dao.TransactionRepository;
import com.orderbookjpamaven.entities.OrderEntity;
import com.orderbookjpamaven.entities.TransactionEntity;

import com.orderbookjpamaven.model.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceLayerImpl implements ServiceLayer {

    @Autowired
    OrderRepository ordersDao;

    @Autowired
    TransactionRepository transactionsDao;

    @Override
    public List<OrderDto> getAllBuyOrdersForSymbol(String symbol) {
       List<OrderEntity> allOrderSymbolEntity = ordersDao.findAll();
       List<OrderDto> allOrderSymbolDto = new ArrayList<>(OrderDto);
       for(OrderEntity eachOrderSymbolEntity: allOrderSymbolEntity)
        return ordersDao.findAllBuyOrdersForSymbol(symbol);
    }

    /*
    public List<AuthorDto> fetchAllAuthors() {
		List<AuthorEntity> allAuthorEntity = authorDao.findAll();
		// we can't return a collection of AuthorEntity, we have to return a collection of AuthorDto
		// so we have to iterate through the collection of AuthorEntity and copy them into a collection of AuthorDto
		List<AuthorDto> allAuthorDto = new ArrayList<AuthorDto>();
		for(AuthorEntity eachAuthorEntity: allAuthorEntity) {
			// now copy eachAuthorEntity into an AuthorDto object
			AuthorDto eachAuthorDto = new AuthorDto();
			BeanUtils.copyProperties(eachAuthorEntity, eachAuthorDto);

			// copy the collection of book entity(inside eachAuthorEntity) into a collection of book dto
			List<BookDto> allBooksDto = new ArrayList<BookDto>();
			for(BookEntity eachBookEntity: eachAuthorEntity.getAllBooksEntity()) {
				BookDto bookDto = new BookDto();
				BeanUtils.copyProperties(eachBookEntity, bookDto);
				allBooksDto.add(bookDto);
			}

			// set the collection into the eachAuthorDto
			eachAuthorDto.setAllBooksDto(allBooksDto);

			allAuthorDto.add(eachAuthorDto);
		}
		return allAuthorDto;

     */



    @Override
    public List<OrderEntity> getAllSellOrdersForSymbol(String symbol) {
        return ordersDao.findAllSellOrdersForSymbol(symbol);
    }

    // get list of transactions stored in transaction database table
    @Override
    public List<TransactionEntity> getAllTransactions() {
        List<TransactionEntity> transactionEntityList = transactionsDao.findAll();
        return transactionEntityList;
    }

    // Gets all transaction from database table and filters them to only return transactions that have the given symbol
    @Override
    public List<TransactionEntity> getAllTransactionsForSymbol(String symbol) {
        // pull all transactions from database
        List<TransactionEntity> transactionEntityList = transactionsDao.findByFinalSymbolOrderByFinalTimeDesc(symbol);

        return transactionEntityList;
    }

    // Create new transaction and save it to transaction database table; pull info from buy order
    @Override
    public TransactionEntity makeTransaction(OrderEntity buyOrderEntity, OrderEntity sellOrderEntity) {
        // Create the time field for the new transaction w/ format that sql can handle
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
//        String formattedDateTime = now.format(formatter);

        // Create and fill a new transaction
        TransactionEntity newTransactionEntity = new TransactionEntity();
        newTransactionEntity.setFinalSymbol(buyOrderEntity.getSymbol());
        newTransactionEntity.setFinalPrice(buyOrderEntity.getOfferPrice());
        newTransactionEntity.setFinalTime(timestamp);

        boolean buySizeBigger = buyOrderEntity.getSize() >= sellOrderEntity.getSize();

        newTransactionEntity.setAmount(buySizeBigger ? sellOrderEntity.getSize()
                : buyOrderEntity.getSize());

        int buyOrderSize = buyOrderEntity.getSize();
        int sellOrderSize = sellOrderEntity.getSize();

        buyOrderEntity.setSize(buySizeBigger ? buyOrderSize - sellOrderSize : 0);
        sellOrderEntity.setSize(buySizeBigger ? 0 : sellOrderSize - buyOrderSize);

        buyOrderEntity = ordersDao.save(buyOrderEntity);
        sellOrderEntity = ordersDao.save(sellOrderEntity);
        newTransactionEntity.setBuyOrderId(buyOrderEntity);
        newTransactionEntity.setSellOrderId(sellOrderEntity);

        newTransactionEntity = transactionsDao.save(newTransactionEntity);

        return newTransactionEntity;
    }

    // Compare the buy and sell order offer prices; if buy order price is greater than or equal to the sell order price, returns true (vlid match)-else false (not a match)
    @Override
    public TransactionEntity matchOrders(int givenOrderId) {
        OrderEntity givenOrderEntity = ordersDao.getOne(givenOrderId);

        OrderEntity createdOrderEntity = new OrderEntity();

        createdOrderEntity.setSide(!givenOrderEntity.isSide());
        createdOrderEntity.setActiveOrder(false);
        createdOrderEntity.setSize(givenOrderEntity.getSize());
        createdOrderEntity.setSymbol(givenOrderEntity.getSymbol());
        createdOrderEntity.setOrderTime(LocalDateTime.now());
        createdOrderEntity.setOfferPrice(givenOrderEntity.getOfferPrice());

        createdOrderEntity = ordersDao.save(createdOrderEntity);

        givenOrderEntity.setActiveOrder(false);

        givenOrderEntity = ordersDao.save(givenOrderEntity);

        TransactionEntity transactionEntity = new TransactionEntity();

        if (givenOrderEntity.isSide()) {
            transactionEntity = makeTransaction(givenOrderEntity, createdOrderEntity);
        } else {
            transactionEntity = makeTransaction(createdOrderEntity, givenOrderEntity);
        }

        return transactionEntity;
    }

    @Override
    public void deleteUnmatchedOrder(int orderId) {
        //Order order = orders.getOne(orderId);
        OrderEntity orderEntity = ordersDao.findById(orderId).orElse(null);

        // Attempts to delete an order from the db if and the order is active and
        // there exists no transactions that use said order
        List transactionList = transactionsDao.findAllTransactionsForOrder(orderEntity);

        if (orderEntity.getSize() > 0) {

            if (transactionList.isEmpty()) {
                ordersDao.deleteById(orderEntity.getId());
            } else {
                orderEntity.setSize(0);
                orderEntity.setActiveOrder(false);
                ordersDao.save(orderEntity);
            }
        }
    }

    @Override
    public void findPotentialTransactions(String symbol) {
        List<OrderEntity> buyOrderEntities = ordersDao.findAllBuyOrdersForSymbol(symbol);
        List<OrderEntity> sellOrderEntities = ordersDao.findAllSellOrdersForSymbol(symbol);

        boolean doneMatching = false;
        while (!doneMatching) {
            if (buyOrderEntities.size() == 0 || sellOrderEntities.size() == 0) {
                doneMatching = true;
            } else if (sellOrderEntities.get(0).getOfferPrice().compareTo(buyOrderEntities.get(0).getOfferPrice()) <= 0) {
                makeTransaction(buyOrderEntities.get(0), sellOrderEntities.get(0));
                buyOrderEntities = ordersDao.findAllBuyOrdersForSymbol(symbol);
                sellOrderEntities = ordersDao.findAllSellOrdersForSymbol(symbol);
            } else {
                doneMatching = true;
            }
        }
    }

    @Override
    public List getSymbols() {
        return ordersDao.getSymbols();
    }

    @Override
    public void createOrderbook(String symbol) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setSymbol(symbol);
        orderEntity.setOfferPrice(BigDecimal.ZERO);
        orderEntity.setActiveOrder(false);
        orderEntity.setOrderTime(LocalDateTime.now());

        orderEntity = ordersDao.save(orderEntity);
    }

    @Override
    public OrderEntity addOrder(OrderEntity newOrderEntity) {
        return ordersDao.save(newOrderEntity);
    }

    @Override
    public List<TransactionEntity> getTop5ByFinalDate() {
        return transactionsDao.findTop5ByOrderByFinalTimeDesc();
    }

    @Override
    public List<TransactionEntity> getAllTransactionsForSymbolAndDate(String symbol, LocalDate date) {
        return transactionsDao.findAllTransactionsForSymbolAndDate(symbol, date);
    }

    @Override
    public void incrementBuyOrders(String tick, String symbol) {
        List<OrderEntity> buyOrderListEntity = ordersDao.findAllBuyOrdersForSymbol(symbol);
        buyOrderListEntity.stream().forEach(
                (o) -> {
                    o.setOfferPrice(o.getOfferPrice().add(new BigDecimal(tick)));
                    ordersDao.save(o);
                });
    }

    @Override
    public void decrementSellOrders(String tick, String symbol) {
        List<OrderEntity> sellOrderListEntity = ordersDao.findAllSellOrdersForSymbol(symbol);
        sellOrderListEntity.stream().forEach(
                (o) -> {
                    o.setOfferPrice(o.getOfferPrice().subtract(new BigDecimal(tick)));
                    ordersDao.save(o);
                });
    }

    @Override
    public OrderEntity getOrderById(int orderId) {
        return ordersDao.findById(orderId).orElse(null);
    }

}


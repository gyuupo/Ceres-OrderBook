package com.orderbookjpamaven.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.orderbookjpamaven.entities.OrderEntity;
import com.orderbookjpamaven.entities.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class Controller {
    // index.html <-- with ticker
    // bookSymbol.html <-- book with latest information for a given symbol, like in PowerPoint example
    // history.html <-- history of all trades
    // historyForSymbol <--history of trade for symbol

    // matchOrder() <-- either bring size of order to 0
    // checks to see if it is possible to
    // deleteOrder() <-- deletes order from order table
    @Autowired
    ServiceLayer service;

    @GetMapping("/")


    @GetMapping("/orderbook")
    public String viewOrderbook(HttpServletRequest request, Model model) {
        String symbol = request.getParameter("symbol").toUpperCase();

        int messageCode = request.getParameter("message") == null ? 0 : Integer.parseInt(request.getParameter("message"));
        String message = "";
        if (messageCode == 1) {
            message += "New Order Created!";
        } else if (messageCode == 2) {
            message += "Order Successfully Matched!";
        } else if (messageCode == 3) {
            message += "Order Successfully Deleted!";
        } else if (messageCode == 4) {
            message += "Order Successfully Edited!";
        }

        if (symbol == null || symbol.isEmpty()) {
            return "redirect:/";
        }

        if (service.getSymbols().contains(symbol)) {
            service.findPotentialTransactions(symbol);
        } else {
            return "redirect:/symbolnotfound?symbol=" + symbol;
        }

        List<OrderEntity> buyOrderEntities = service.getAllBuyOrdersForSymbol(symbol);
        List<OrderEntity> sellOrderEntities = service.getAllSellOrdersForSymbol(symbol);

        model.addAttribute("symbol", symbol);
        model.addAttribute("symbols", service.getSymbols());
        model.addAttribute("buyOrders", buyOrderEntities);
        model.addAttribute("sellOrders", sellOrderEntities);
        model.addAttribute("message", message);
        return "orderbook";
    }

    @GetMapping("/symbolnotfound")


    @GetMapping("/neworderbook")


    @GetMapping("/tradehistory")

        List<TransactionEntity> transactionEntities = new ArrayList<>();

        if (dateString == null || dateString.isEmpty()) {
            transactionEntities = service.getAllTransactionsForSymbol(symbol);
        } else {
            transactionEntities = service.getAllTransactionsForSymbolAndDate(symbol, LocalDate.parse(dateString));
        }

        model.addAttribute("symbol", symbol);
        model.addAttribute("symbols", service.getSymbols());
        model.addAttribute("transactions", transactionEntities);
        return "history";
    }

    @GetMapping("/deleteorder")
    public String deleteOrder(Integer id, String symbol) {
        service.deleteUnmatchedOrder(id);
        return "redirect:/orderbook?symbol=" + symbol + "&message=3";
    }

    @GetMapping("matchorder")

    @PostMapping("createorder")
    public String addOrder(HttpServletRequest request) {
        int size = Integer.parseInt(request.getParameter("size"));
        String symbol = request.getParameter("symbol");
        boolean side = Boolean.parseBoolean(request.getParameter("side"));
        BigDecimal offerPrice = new BigDecimal(request.getParameter("offerPrice"));

        OrderEntity createdOrderEntity = new OrderEntity();
        createdOrderEntity.setSize(size);
        createdOrderEntity.setOfferPrice(offerPrice);
        createdOrderEntity.setActiveOrder(true);
        createdOrderEntity.setOrderTime(LocalDateTime.now());
        createdOrderEntity.setSide(side);
        createdOrderEntity.setSymbol(symbol);

        service.addOrder(createdOrderEntity);

        return "redirect:/orderbook?symbol=" + symbol + "&message=1";
    }

    @GetMapping("editorder")
    public String editOrder(HttpServletRequest request, Model model) {
        OrderEntity orderEntity = service.getOrderById(Integer.parseInt(request.getParameter("orderId")));

        model.addAttribute("order", orderEntity);
        model.addAttribute("symbols", service.getSymbols());

        return "editorder";
    }

    @PostMapping("editorder")
    public String performEditOrder(HttpServletRequest request, Model model) {
        OrderEntity orderEntity = service.getOrderById(Integer.parseInt(request.getParameter("orderId")));

        orderEntity.setSize(Integer.parseInt(request.getParameter("size")));
        orderEntity.setOfferPrice(new BigDecimal(request.getParameter("amount")));
        orderEntity.setOrderTime(LocalDateTime.now());

        service.addOrder(orderEntity);

        return "redirect:/orderbook?symbol=" + orderEntity.getSymbol() + "&message=4";
    }
}


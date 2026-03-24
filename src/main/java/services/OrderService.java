package services;

import hib.proj.Client;
import hib.proj.Order;
import hib.proj.TransactionHelper;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;


    public OrderService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Order saveOrder(long orderYear, double totalAmount, String status, Client client){
        return transactionHelper.executeInTransaction(session -> {
           var order = new Order(orderYear,totalAmount,status,client);
           session.persist(order);
           return order;
        });
    }

    public void cancelOrder(long id){
        transactionHelper.executeInTransaction(session -> {
            Order orderToCancel = session.get(Order.class,id);
            session.remove(orderToCancel);
        });
    }

}

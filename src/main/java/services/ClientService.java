package services;

import hib.proj.Client;
import hib.proj.TransactionHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;

    public ClientService(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Client saveClient(Client client){
        return transactionHelper.executeInTransaction(session -> {
           session.persist(client);
           return client;
        });
    }

    public void deleteClient(long id){
        transactionHelper.executeInTransaction(session -> {
            Client clientToDelete = session.get(Client.class,id);
            session.remove(clientToDelete);
        });
    }

    public Client getById(long id){
        try(Session session = sessionFactory.openSession()){
            return  session.get(Client.class, id);
        }
    }

    public List<Client> findAll(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT s from Client s ",Client.class).list();
        }
    }

    public Client updateClient(Client client){
        return transactionHelper.executeInTransaction(session -> {
           return session.merge(client);
        });
    }

}

package hib.proj;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main (String [] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("hib.proj");

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);


        Client client = new Client(2001,"cl1@gmail.com","Bob");

        var session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(client);
        session.getTransaction().commit();

        session.close();




    }
}

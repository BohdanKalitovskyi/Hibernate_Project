package hib.proj;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.ClientService;
import services.CouponService;
import services.OrderService;
import services.ProfileService;

import java.util.Scanner;

public class Main {


    public static void main (String [] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("hib.proj","services");

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);

        ClientService clientService = context.getBean(ClientService.class);
        ProfileService profileService = context.getBean(ProfileService.class);
        OrderService orderService = context.getBean(OrderService.class);
        CouponService couponService = context.getBean(CouponService.class);

        context.getBean(ConsoleUI.class).start();

    }
}

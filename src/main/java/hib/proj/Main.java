package hib.proj;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.ClientService;
import services.CouponService;
import services.OrderService;
import services.ProfileService;

public class Main {
    public static void main (String [] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("hib.proj","services");

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);

        ClientService clientService = context.getBean(ClientService.class);
        ProfileService profileService = context.getBean(ProfileService.class);
        OrderService orderService = context.getBean(OrderService.class);
        CouponService couponService = context.getBean(CouponService.class);




        Client client = new Client(2001,"cl1@gmail.com","Bob");

        clientService.saveClient(client);

        Profile profile = new Profile("023412311","Shevchenka st. 111",client);

        profileService.saveProfile(profile);

        orderService.saveOrder(2003L,10.2,"processing",client);

        Coupon coupon = new Coupon(40f,"40percentOff");
        couponService.saveCoupon(coupon);

        couponService.addCouponToUser(1L,1L);

        System.out.println(clientService.findAll());

    }
}

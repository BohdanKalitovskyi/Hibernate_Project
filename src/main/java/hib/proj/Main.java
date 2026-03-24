package hib.proj;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.ClientService;
import services.ProfileService;

public class Main {
    public static void main (String [] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("hib.proj","services");

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);

        ClientService clientService = context.getBean(ClientService.class);
        ProfileService profileService = context.getBean(ProfileService.class);



        Client client = new Client(2001,"cl1@gmail.com","Bob");

        clientService.saveClient(client);

        Profile profile = new Profile("023412311","Shevchenka st. 111",client);

        profileService.saveProfile(profile);


        System.out.println(clientService.findAll());

    }
}

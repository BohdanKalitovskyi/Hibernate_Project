package hib.proj;

import org.springframework.stereotype.Component;
import services.ClientService;
import services.CouponService;
import services.OrderService;
import services.ProfileService;

import java.io.Console;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Component
public class ConsoleUI {
    private final ClientService clientService;
    private final CouponService couponService;
    private final OrderService orderService;
    private final ProfileService profileService;


    public ConsoleUI(ClientService clientService, CouponService couponService, OrderService orderService, ProfileService profileService) {
        this.clientService = clientService;
        this.couponService = couponService;
        this.orderService = orderService;
        this.profileService = profileService;
    }

    public void registrationCoupon(Long clientId) {
        Scanner scanner = new Scanner(System.in);
        List<Coupon> coupons = couponService.findAll();

        System.out.println("Available Coupons:");
        coupons.forEach(c -> System.out.println("ID: " + c.getId() + " | Code: " + c.getCode()));

        long couponId = readLong("Enter Coupon ID to assign");

        couponService.addCouponToUser(couponId, clientId);
        System.out.println("Coupon linked successfully!");
    }

    public void addUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        long regYear = readLong("Enter the registration year");
        System.out.println("Enter email:");
        String email = scanner.nextLine();

        Client savedClient = clientService.saveClient(new Client(regYear, email, name));
        System.out.println("Client saved with ID: " + savedClient.getId());

        registrationCoupon(savedClient.getId());

        long choice = readLong("Add more information: \n" +
                "1 Continue: \n" +
                "2 Later");
        if(choice==1)
            setProfile(savedClient.getId());

    }

    public void showCoupons(){
        long id = readLong("Enter the client's Id");
        List<Coupon> coupons = clientService.getClientCoupons(id);

        System.out.println("\n"+clientService.getById(id).getName() + " owns :");
        coupons.forEach(coupon -> System.out.println(coupon.getCode()));
    }

    public void showClient(){
        long clientId = readLong("Enter the client's Id");
        Client client = clientService.getById(clientId);
        System.out.println("Client Id: "+ client.getId() + " | Name: "+client.getName()+ " | Email: "+client.getEmail()+" | Registration Year: "+ client.getRegistrationYear());
    }

    private void deleteClient(){
        long clientId = readLong("Enter the client's Id");
        clientService.deleteClient(clientId);
    }

    private void setProfile(Long clientId){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the phone number: ");
        String phone = scanner.nextLine();
        System.out.println("Enter the home address: ");
        String address = scanner.nextLine();
        profileService.saveProfile(new Profile(address,phone,clientService.getById(clientId)));
    }

    public void showProfile(){
        long clientId = readLong("Enter the client's Id");
        Client client = clientService.getById(clientId);
        System.out.println(client.getName()+"'s address: "+client.getProfile().getAddress()+" | Phone number: "+client.getProfile().getPhone());
    }

    private long readLong(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(message + ": ");
            String input = scanner.nextLine();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! '" + input + "' is not a number. Try again.");
            }
        }
    }



    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        couponService.saveCoupon(new Coupon(40f,"OFF40"));
        couponService.saveCoupon(new Coupon(50f,"OFF50"));

        for (int i = 0; i<10; i++){
            System.out.println();
        }

        while (isRunning) {
            System.out.println("Choose the action:\n" +
                    "1 Add a client\n" +
                    "2 Delete Client\n" +
                    "3 Update the client\n" +
                    "4 Show the client by id\n" +
                    "5 Show the client's coupons\n" +
                    "6 Show the client's profile\n" +
                    "7 Exit\n");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addUser();
                case 2 -> deleteClient();
                case 3 -> {
                }
                case 4 -> showClient();
                case 5 -> showCoupons();
                case 6 -> showProfile();
                case 7 -> {
                    isRunning = false;
                    System.out.println("Bye!");
                }

                default -> System.out.println("Invalid input! Try again");
            }
        }
    }
}

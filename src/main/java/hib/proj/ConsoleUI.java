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
        if(couponId<=couponService.findAll().stream().count()){
            couponService.addCouponToUser(couponId, clientId);
            System.out.println("Coupon linked successfully!");
        }else {
            registrationCoupon(clientId);
            System.out.println("Wrong coupon number");
        }
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
        long Id = readLong("Enter the client's Id");
        if(!checkIfExists(Id)) return;
        List<Coupon> coupons = clientService.getClientCoupons(Id);

        System.out.println("\n"+clientService.getById(Id).getName() + " owns :");
        coupons.forEach(coupon -> System.out.println(coupon.getCode()));
    }

    public void showClient(){
        long Id = readLong("Enter the client's Id");
        if(!checkIfExists(Id)) return;
        Client client = clientService.getById(Id);
        System.out.println("Client Id: "+ client.getId() + " | Name: "+client.getName()+ " | Email: "+client.getEmail()+" | Registration Year: "+ client.getRegistrationYear());
    }

    public void showProfile(){
        long Id = readLong("Enter the client's Id");
        if(!checkIfExists(Id)) return;
        if(clientService.getById(Id).getProfile()==null){
            System.out.println("This client does not have a profile");
            return;
        }
        Client client = clientService.getById(Id);
        System.out.println(client.getName()+"'s address: "+client.getProfile().getAddress()+" | Phone number: "+client.getProfile().getPhone());
    }

    public void updateClient() {
        Scanner scanner = new Scanner(System.in);
        long Id = readLong("Enter the client's Id");
        if(!checkIfExists(Id)) return;
        Client client = clientService.getById(Id);

        System.out.print("Enter new name (leave blank to keep '" + client.getName() + "'): ");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) {
            client.setName(newName);
        }

        System.out.print("Enter new email (leave blank to keep '" + client.getEmail() + "'): ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isBlank()) {
            client.setEmail(newEmail);
        }

        System.out.print("Enter new registration year (leave blank to keep " + client.getRegistrationYear() + "): ");
        String yearInput = scanner.nextLine();
        if (!yearInput.isBlank()) {
            try {
                client.setRegistrationYear(Long.parseLong(yearInput));
            } catch (NumberFormatException e) {
                System.out.println("Invalid year format. Keeping the old value.");
            }
        }

        clientService.updateClient(client);
        System.out.println("Update successful!");
    }

    private void deleteClient(){
        long Id = readLong("Enter the client's Id");
        if(!checkIfExists(Id)) return;
        clientService.deleteClient(Id);
    }

    private void setProfile(Long clientId){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the phone number: ");
        String phone = scanner.nextLine();
        System.out.println("Enter the home address: ");
        String address = scanner.nextLine();
        profileService.saveProfile(new Profile(address,phone,clientService.getById(clientId)));
    }

    public void addProfile(){
        long Id = readLong("Enter the client's Id");
        if(!checkIfExists(Id)) return;
        Client client = clientService.getById(Id);
        if(client.getProfile()==null){
            setProfile(Id);
        }else {
            System.out.println("Client has the profile, update if needed.");
        }
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

    public boolean checkIfExists(long Id){
        if (clientService.getById(Id) == null){
            System.out.println("Error: Client not found.");
            return false;
        }
        return true;
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
                    "4 Add profile to the client\n" +
                    "5 \n" +
                    "6 \n" +
                    "7 \n" +
                    "8 Show the client by id\n" +
                    "9 Show the client's coupons\n" +
                    "10 Show the client's profile\n" +
                    "11 Exit\n");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addUser();
                case 2 -> deleteClient();
                case 3 -> updateClient();
                case 4 -> addProfile();
                case 5 -> {}
                case 6 -> {}
                case 7 -> {}
                case 8 -> showClient();
                case 9 -> showCoupons();
                case 10 -> showProfile();

                case 11 -> {
                    isRunning = false;
                    System.out.println("Bye!");
                }

                default -> System.out.println("Invalid input! Try again");
            }
        }
    }
}

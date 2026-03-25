package hib.proj;

import org.springframework.stereotype.Component;
import services.ClientService;
import services.CouponService;
import services.OrderService;
import services.ProfileService;

import java.io.Console;
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

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        for (int i = 0; i<10; i++){
            System.out.println();
        }

        while (isRunning) {
            System.out.println("Choose the action:\n" +
                    "1 Add a client\n" +
                    "2 Delete the client by id\n" +
                    "3 Update the client\n" +
                    "4 Show the client by id\n" +
                    "5 Exit\n");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
//                    long regYear = scanner.nextLong();
//                    String email = scanner.next();
//                    String name = scanner.next();
//                    clientService.saveClient(new Client(regYear,email,name));
                }
                case 2 -> {
                }
                case 3 -> {

                }
                case 4 -> {
                    long clientId = scanner.nextLong();
                    System.out.println(clientService.getById(clientId));
                }
                case 5 -> {
                    isRunning = false;
                    System.out.println("Bye!");
                }

                default -> System.out.println("Invalid input! Try again");
            }
        }
    }
}

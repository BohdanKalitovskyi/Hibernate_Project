package hib.proj;

import org.springframework.stereotype.Component;
import services.ClientService;
import services.CouponService;
import services.OrderService;
import services.ProfileService;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUI {
    private final ClientService clientService;
    private final CouponService couponService;
    private final OrderService orderService;
    private final ProfileService profileService;

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(ClientService clientService, CouponService couponService, OrderService orderService,
            ProfileService profileService) {
        this.clientService = clientService;
        this.couponService = couponService;
        this.orderService = orderService;
        this.profileService = profileService;
    }

    public void registrationCoupon(Long clientId) {
        List<Coupon> coupons = couponService.findAll();

        System.out.println("Available Coupons:");
        coupons.forEach(c -> System.out.println("ID: " + c.getId() + " | Code: " + c.getCode()));

        long couponId = readLong("Enter Coupon ID to assign");
        if (couponId <= couponService.findAll().stream().count()) {
            couponService.addCouponToUser(couponId, clientId);
            System.out.println("Coupon linked successfully!");
        } else {
            registrationCoupon(clientId);
            System.out.println("Wrong coupon number");
        }
    }

    public void addUser() {
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
        if (choice == 1)
            setProfile(savedClient.getId());

    }

    public void showCoupons() {
        long Id = readLong("Enter the client's Id");
        Client client = clientService.getById(Id);
        if (!checkIfExists(client))
            return;

        System.out.println("\n" + client.getName() + " owns :");
        client.getCoupons().forEach(coupon -> System.out.println(coupon.getCode()));
    }

    public void showClient() {
        long Id = readLong("Enter the client's Id");
        Client client = clientService.getById(Id);
        if (!checkIfExists(client))
            return;
        System.out.println("Client Id: " + client.getId() + " | Name: " + client.getName() + " | Email: "
                + client.getEmail() + " | Registration Year: " + client.getRegistrationYear());
    }

    public void showProfile() {
        long Id = readLong("Enter the client's Id");
        Client client = clientService.getById(Id);
        if (!checkIfExists(client))
            return;
        if (client.getProfile() == null) {
            System.out.println("This client does not have a profile");
            return;
        }
        System.out.println(client.getName() + "'s address: " + client.getProfile().getAddress() + " | Phone number: "
                + client.getProfile().getPhone());
    }

    public void updateClient() {
        long Id = readLong("Enter the client's Id");
        Client client = clientService.getById(Id);
        if (!checkIfExists(client))
            return;

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

    private void deleteClient() {
        long Id = readLong("Enter the client's Id");
        Client client = clientService.getById(Id);
        if (!checkIfExists(client))
            return;
        clientService.deleteClient(Id);
    }

    private void setProfile(Long clientId) {
        System.out.println("Enter the phone number: ");
        String phone = scanner.nextLine();
        System.out.println("Enter the home address: ");
        String address = scanner.nextLine();
        profileService.saveProfile(new Profile(address, phone, clientService.getById(clientId)));
    }

    public void updateProfile() {
        long Id = readLong("Enter the client's Id");
        Client client = clientService.getById(Id);
        if (!checkIfExists(client))
            return;
        Profile profile = client.getProfile();
        if (profile == null) {
            System.out.println("This client does not have a profile to update.");
            return;
        }

        System.out.print("Enter new phone (leave blank to keep '" + profile.getPhone() + "'): ");
        String newPhone = scanner.nextLine();
        if (!newPhone.isBlank()) {
            profile.setPhone(newPhone);
        }

        System.out.print("Enter new address (leave blank to keep '" + profile.getAddress() + "'): ");
        String newAddress = scanner.nextLine();
        if (!newAddress.isBlank()) {
            profile.setAddress(newAddress);
        }

        profileService.updateProfile(profile);
        System.out.println("Profile updated successfully!");
    }

    public void addProfile() {
        long Id = readLong("Enter the client's Id");
        Client client = clientService.getById(Id);
        if (!checkIfExists(client))
            return;
        if (client.getProfile() == null) {
            setProfile(Id);
        } else {
            System.out.println("Client has the profile, update if needed.");
        }
    }

    public void addOrder() {
        long Id = readLong("Enter the client's Id");
        Client client = clientService.getById(Id);

        if (client == null) {
            System.out.println("Client with ID " + Id + " not found.");
            return;
        }

        long orderYear = readLong("Enter the order year:");
        double amount = readDouble("Enter the amount");
        String status = "Processing";

        orderService.saveOrder(orderYear, amount, status, client);
        System.out.println("Order created for client: " + client.getName());
    }

    public void assignCoupon() {
        long clientId = readLong("Enter the client's Id");
        Client client = clientService.getById(clientId);
        if (!checkIfExists(client))
            return;
        registrationCoupon(clientId);
    }

    public void cancelOrder() {
        long id = readLong("Enter the order ID to cancel");
        orderService.cancelOrder(id);
        System.out.println("Order cancelled successfully!");
    }

    public void addCoupon() {
        System.out.println("Enter coupon code:");
        String code = scanner.nextLine();
        double discount = readDouble("Enter discount percentage (e.g., 20.0)");
        couponService.saveCoupon(new Coupon((float) discount, code));
        System.out.println("Coupon created successfully!");
    }

    public void showOrders() {
        long id = readLong("Enter the client's Id");
        Client client = clientService.getById(id);
        if (!checkIfExists(client))
            return;
        List<Order> orders = client.getOrders();
        System.out.println("\n" + client.getName() + " has " + orders.size() + " orders:");
        orders.forEach(order -> System.out.println("ID: " + order.getId() + " | Year: " + order.getOrderYear()
                + " | Amount: " + order.getTotalAmount() + " | Status: " + order.getStatus()));
    }

    public void showClientsByYear() {
        long year = readLong("Enter registration year to search");
        List<Client> clients = clientService.findByRegistrationYear(year);
        if (clients.isEmpty()) {
            System.out.println("No clients found for year " + year);
            return;
        }
        System.out.println("\nClients registered in " + year + ":");
        clients.forEach(
                c -> System.out.println("ID: " + c.getId() + " | Name: " + c.getName() + " | Email: " + c.getEmail()));
    }

    private double readDouble(String message) {
        while (true) {
            System.out.println(message + ": ");
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! '" + input + "' is not a number. Try again.");
            }
        }
    }

    private long readLong(String message) {
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

    public boolean checkIfExists(Client client) {
        if (client == null) {
            System.out.println("Error: Client not found.");
            return false;
        }
        return true;
    }

    public void start() {
        boolean isRunning = true;

        couponService.saveCoupon(new Coupon(40f, "OFF40"));
        couponService.saveCoupon(new Coupon(50f, "OFF50"));

        while (isRunning) {
            System.out.println("Choose the action:\n" +
                    "1 Add a client\n" +
                    "2 Delete Client\n" +
                    "3 Update the client\n" +
                    "4 Add profile to the client\n" +
                    "5 Make an order\n" +
                    "6 Create a new coupon\n" +
                    "7 Show the client's orders\n" +
                    "8 Show the client by id\n" +
                    "9 Show the client's coupons\n" +
                    "10 Show the client's profile\n" +
                    "11 Update the client's profile\n" +
                    "12 Assign a coupon to client\n" +
                    "13 Cancel an order\n" +
                    "14 Search clients by registration year\n" +
                    "15 Exit\n");

            int choice = -1;
            try {
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
            }

            switch (choice) {
                case 1 -> addUser();
                case 2 -> deleteClient();
                case 3 -> updateClient();
                case 4 -> addProfile();
                case 5 -> addOrder();
                case 6 -> addCoupon();
                case 7 -> showOrders();
                case 8 -> showClient();
                case 9 -> showCoupons();
                case 10 -> showProfile();
                case 11 -> updateProfile();
                case 12 -> assignCoupon();
                case 13 -> cancelOrder();
                case 14 -> showClientsByYear();
                case 15 -> {
                    isRunning = false;
                    System.out.println("Bye!");
                }

                default -> System.out.println("Invalid input! Try again");
            }
        }
    }
}

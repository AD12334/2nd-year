import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VirtualGumballMachineMenu {
    private Scanner scanner;

    // Constructor
    public VirtualGumballMachineMenu() {
        scanner = new Scanner(System.in);
    }

    public void run(VirtualGballs gballs) throws IOException {
        boolean more = true;
        while (more) {
            System.out.println("Enter user mode O)wner, C)onsumer, or E)xit");
            String answer = scanner.nextLine().toUpperCase();

            if (answer.equals("O")) {
                handleOwnerMode(gballs);
            } else if (answer.equals("C")) {
                handleConsumerMode(gballs);
            } else if (answer.equals("E")) {
                more = false; // Exit the loop
            } else {
                System.out.println("Invalid input. Please enter 'O', 'C', or 'E'.");
            }
        }
        scanner.close(); // Close the scanner at the end
    }

    private void handleOwnerMode(VirtualGballs gballs) {
        try {
            System.out.println("Enter the type of gumballs we wish to stock the gumball machine with: ");
            String description = scanner.nextLine();

            System.out.println("Enter the number of gumballs to add: ");
            int gumballCount = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the price per gumball: ");
            double price = Double.parseDouble(scanner.nextLine());

            // Add new gumball stock
            GumballStock newGumball = new GumballStock(description, 0, price);
            GumballCount(gumballCount); // Check if the count exceeds limit
            gballs.addProduct(newGumball, gumballCount);
            System.out.println("Successfully stocked " + gumballCount + " gumballs of type: " + description);
        } catch (Gumball e) {
            System.out.println("Error stocking gumballs: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter valid numeric values.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleConsumerMode(VirtualGballs gballs) {
        try {
            System.out.println("Insert coin!");
            double coin = Double.parseDouble(scanner.nextLine());

            // Validate and accept the coin
            AcceptMoney(coin);
            gballs.addBalance(coin); 
            System.out.println("Current balance = " + gballs.getBalance());

            // Display available gumballs
            System.out.println("Available gumballs:");
            for (GumballStock gumballStock : gballs.getProductTypes()) {
                System.out.println(gumballStock.getDescription());
            }

            System.out.println("Enter the description of the gumball you want to buy:");
            String description = scanner.nextLine();
            GumballStock selectedGumball = findGumballByDescription(gballs, description);

            // Attempt to purchase the selected gumball
            if (selectedGumball != null) {
                gballs.buyGumball(selectedGumball);
                System.out.println("Successfully purchased: " + selectedGumball.getDescription());
            } else {
                System.out.println("Gumball not found.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid coin value.");
            scanner.nextLine(); // Clear the invalid input
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter valid numeric values.");
        } catch (Money e) {
            System.out.println("Currency not accepted: " + e.getMessage());
        } catch (GumballError e) {
            System.out.println("Purchase failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private GumballStock findGumballByDescription(VirtualGballs gballs, String description) {
        for (GumballStock gumball : gballs.getProductTypes()) {
            if (gumball.getDescription().equalsIgnoreCase(description)) {
                return gumball;
            }
        }
        return null;
    }

    // Method to check the gumball count
    public static void GumballCount(int gumballs) throws Gumball {
        if (gumballs > 60) {
            throw new Gumball("Gumball limit exceeded");
        } else if (gumballs < 0) {
            throw new Gumball("Negative gumballs");
        }
    }

    // Your original AcceptMoney method
    public static void AcceptMoney(double money) throws Money {
        double[] AcceptedCurrency = {0.05, 0.10, 0.20, 0.5, 1.0, 2.0};
        boolean accepted = false;
        for (double currency : AcceptedCurrency) {
            if (money == currency) {
                accepted = true;
                break;
            }
        }

        if (accepted) {
            System.out.println("Money is acceptable");
        } else {
            throw new Money("Currency not accepted");
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        try {
            VirtualGballs machine = new VirtualGballs();
            VirtualGumballMachineMenu menu = new VirtualGumballMachineMenu();
            menu.run(machine);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}

import java.util.ArrayList;
import java.util.HashMap;

public class VirtualGballs {
    private double balance;
    private ArrayList<GumballStock> Stock;

    public VirtualGballs() {
        balance = 0;
        Stock = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    // Method to add money to the balance
    public void addBalance(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Added " + amount + " to balance. New balance = " + balance);
        } else {
            System.out.println("Invalid amount. Cannot add a non-positive value to balance.");
        }
    }

    public void addProduct(GumballStock product, int quantity) {
        Stock.add(product);
        product.addCount(quantity);
        System.out.println("Added " + product.getDescription() + " x " + quantity);
    }

    public GumballStock[] getProductTypes() {
        HashMap<String, GumballStock> productmap = new HashMap<>();
        for (GumballStock product : Stock) {
            productmap.put(product.getDescription(), product);
        }
        return productmap.values().toArray(new GumballStock[0]);
    }

    public void buyGumball(GumballStock gumball) throws GumballError {
        if (!Stock.contains(gumball)) {
            throw new GumballError("Gumball is out of stock");
        }
        if (gumball.getCount() <= 0) {
            throw new GumballError("Gumball is out of stock");
        }
        if (balance < gumball.getPrice()) {
            throw new GumballError("Insufficient funds");
        }
        gumball.buy();
        balance -= gumball.getPrice();
        System.out.println("Purchased " + gumball.getDescription() + " for " + gumball.getPrice());
    }
}

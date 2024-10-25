public class GumballStock {
    private String description;
    private int count;
    private double price;
    public GumballStock(String description, int count, double price) {
        this.description = description;
        this.count = 0;
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public  int getCount() {
        return count;
    }
    public  double getPrice() {
        return price;
    }
    public void addCount(int count) {
        this.count += count;
    }public  boolean buy(){
        if (count > 0){
            count--;
            return true;
        }
        return false;
    }
    public String toString() {
        return description + "\t" + count + "\t" + price;
    }
}

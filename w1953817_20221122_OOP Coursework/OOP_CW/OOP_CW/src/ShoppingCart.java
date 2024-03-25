import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingCart {
    private ArrayList<Product> cart;

    public ShoppingCart(){

        this.cart = new ArrayList<>();
    }

    public void add(Product product){
        cart.add(product);
        System.out.println("Product added ");
    }

    public void remove() {
        String found = "no";
        while (true) {
            System.out.println("To remove product enter product id ");
            Scanner input = new Scanner(System.in);
            String pId = input.nextLine();
            for (int i = 0; i < cart.size(); i++) {
                if (cart.get(i).getProductID() == pId) {
                    found = "yes";
                    cart.get(i).toString();
                    cart.remove(i);
                    System.out.println("total products in the list =  " + cart.size());
                } else {
                    System.out.println("Enter product id ");
                }
            }
            if (found == "yes") {
                break;
            }
        }
    }

    public double finalCost(){
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total += cart.get(i).getPrice();
        }
        return total;
    }

}

public class User {
    private String username;
    private String password;
    private String email;
    private  ShoppingCart shoppingCart;
    private int Numberofperchuss;

    public User(String username, String password ,int Numberofperchuss) {
        this.username = username;
        this.password = password;
        this.shoppingCart = new ShoppingCart();
        this.Numberofperchuss = Numberofperchuss;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumberofperchuss(int numberofperchuss) {
        Numberofperchuss = numberofperchuss;
    }

    public int getNumberofperchuss() {
        return Numberofperchuss;
    }

    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }



}

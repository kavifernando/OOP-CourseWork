import javax.swing.*;

public class Interface {
    User user;
    public Interface(User user){
        this.user = user;
        DisplayInterface mainFrame = new DisplayInterface(user);
        mainFrame.setTitle("Westminster Shopping Centre");
        mainFrame.setSize(800,600);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

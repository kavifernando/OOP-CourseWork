import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class DisplayInterface extends JFrame {
    private ArrayList<Product> productsArr;
    private ArrayList<Product> clothingArr;
    private ArrayList<Product> electronicsArray;
    Object[][] data;
    private String dropDown;
    private String comboItem;
    private JTextPane details;
    private JTable table;
    private JPanel productdetails;
    private JScrollPane paneScroll;
    private DefaultTableModel model;
    private JButton buttonCrt;
    private JButton buttonShopCart;

    private User user;
    public DisplayInterface(User user){
        productsArr = new ArrayList<Product>();
        clothingArr = new ArrayList<Product>();
        electronicsArray = new ArrayList<Product>();
        this.user = user;
        loadFile();
        setLayout(new BorderLayout());

        // create top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(new JLabel("Select Product Category: "));
        JComboBox<String> comboBox = new JComboBox<>();

        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 100)); // Adjust the right margin as needed
        String[] items = {"Electronic", "Clothing"};
        comboBox.addItem("Select an item"); // Add a default item
        for (String item : items) {
            comboBox.addItem(item);
        }
        comboBox.setBounds(700, 50, 150, 30); // Adjust the right margin as needed
        topPanel.add(comboBox);
        comboBox.addActionListener(e -> {
            comboItem = (String) comboBox.getSelectedItem();
            comboEvent();
        });


        buttonCrt = new JButton("Shopping Cart");

        topPanel.add(buttonCrt);

        buttonCrt.addActionListener(e -> {
            Interface2 newWindow = new Interface2();
            newWindow.setVisible(true);
        });

        add(topPanel, BorderLayout.NORTH);

//center panel
        Object[][] data = new Object[productsArr.size()][4];
        String columnName [] = { "Product ID" ,"Name" , "Category" , "Price($)"};

        for (int i = 0; i < productsArr.size(); i++) {
            Product product = productsArr.get(i);
            if (product instanceof Clothing){
                dropDown = "Clothing";
                clothingArr.add(product);
            }else {
                dropDown = "Electronic";
                electronicsArray.add(product);
            }
            data[i] = new Object[]{product.getProductID(), product.getProductName(), dropDown, product.getPrice()};
        }

        table = new JTable();
        model = new DefaultTableModel(data, columnName);
        table.setModel(model);

        paneScroll = new JScrollPane(table);
        table.setGridColor(Color.black);

        details = new JTextPane();
        details.setEditable(false);
        add(paneScroll, BorderLayout.CENTER);


// create bottom panel
        productdetails = new JPanel();
        productdetails.setLayout(new BoxLayout(productdetails, BoxLayout.Y_AXIS));
        productdetails.setVisible(true);
        add(productdetails, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            int select_Row = table.getSelectedRow();
            if (select_Row != -1) {
                Display_Product_Details(select_Row);
            }
        });
        productdetails.add(details);
        buttonShopCart = new JButton("Add To Cart");
        JPanel addCartPannel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addCartPannel.add(buttonShopCart);
        productdetails.add(addCartPannel);
        buttonShopCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Product selectedProduct = productsArr.get(selectedRow);
                    user.getShoppingCart().add(selectedProduct);
                    JOptionPane.showMessageDialog(DisplayInterface.this, "Product added to the Shopping Cart!");
                    table.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
                    Display_Product_Details(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(DisplayInterface.this, "Please select a product from the table.");
                }
            }
        });
    }
    public void Display_Product_Details(int select_Row){
        if (select_Row >= 0 && select_Row < productsArr.size()) {
            Product select_Product = productsArr.get(select_Row);

            // Build the details text
            StringBuilder detailsText = new StringBuilder();
            detailsText.append("<html><body style='margin: 50px;'>");

            // Header
            detailsText.append("<h1>Selected Product - Details</h1>");

            // Product details
            detailsText.append("<p><b>Product Id:</b> ").append(select_Product.getProductID()).append("</p>");
            detailsText.append("<p><b>Name:</b> ").append(select_Product.getProductName()).append("</p>");
            if (select_Product instanceof Electronics) {
                detailsText.append("<p><b>Brand:</b> ").append(((Electronics) select_Product).getBrand()).append("</p>");
                detailsText.append("<p><b>Warranty Period:</b> ").append(((Electronics) select_Product).getWarranty()).append(" months</p>");
            } else {
                detailsText.append("<p><b>Size:</b> ").append(((Clothing) select_Product).getSize()).append("</p>");
                detailsText.append("<p><b>Colour:</b> ").append(((Clothing) select_Product).getColour()).append("</p>");
            }
            detailsText.append("<p><b>Items Available:</b> ").append(select_Product.getNumberOfAvailableItems()).append("</p>");

            detailsText.append("</body></html>");

            // Set the formatted text to the JTextPane
            details.setContentType("text/html");
            details.setText(detailsText.toString());
        }
        buttonShopCart = new JButton("Shopping Cart");
    }
    public void loadFile()  {
        try{
            File f1 = new File("Productfile.txt");
            FileInputStream fot = new FileInputStream(f1);
            ObjectInputStream obj = new ObjectInputStream(fot);

            while (true){
                try {
                    Product p1 = (Product) obj.readObject();
                    productsArr.add(p1);
                } catch (EOFException e) {
                    break;
                }
            }
            obj.close();
            fot.close();

        }catch (FileNotFoundException e){
            System.out.println("file not found");
        } catch (IOException e){
            System.out.println("there is a Error");
        }catch (ClassNotFoundException e){
            System.out.println("some classes are not found ");
        }catch (Exception e){
            System.out.println("error");
        }
    }

    public void comboEvent(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        if(comboItem.equals("Clothing")) {
            Object[][] data = new Object[clothingArr.size()][4];
            String columnName2[] = {"Product ID", "Name", "Category", "Price($)"};
            for (int i = 0; i < clothingArr.size(); i++) {
                Product product = clothingArr.get(i);
                data[i] = new Object[]{product.getProductID(), product.getProductName(), "Clothing", product.getPrice()};
                model = new DefaultTableModel(data, columnName2);
                table.setModel(model);
            }
        }
        else if (comboItem.equals("Electronic")){
            Object[][] data3 = new Object[electronicsArray.size()][4];
            String columnName3 [] = { "Product ID" ,"Name" , "Category" , "Price($)"};
            for (int i = 0; i < electronicsArray.size(); i++) {
                Product product = productsArr.get(i);
                data3[i] = new Object[]{product.getProductID(), product.getProductName(), "Electronic", product.getPrice()};
            }
            model = new DefaultTableModel(data3, columnName3);
            table.setModel(model);
        }
        else{
            Object[][] data3 = new Object[productsArr.size()][4];
            String columnName [] = { "Product ID" ,"Name" , "Category" , "Price($)"};
            for (int i = 0; i < productsArr.size(); i++) {
                if (productsArr.get(i) instanceof Electronics){
                    Product product = productsArr.get(i);
                    data3[i] = new Object[]{product.getProductID(), product.getProductName(), "Electronic", product.getPrice()};
                }
                else {
                    Product product = productsArr.get(i);
                    data3[i] = new Object[]{product.getProductID(), product.getProductName(), "Clothing", product.getPrice()};
                }
            }
            model = new DefaultTableModel(data3, columnName);
            table.setModel(model);
        }
    }
}


import java.io.*;
import java.util.*;
public class WestminsterShoppingManager implements ShoppingManager{
    private ArrayList<Product> arrayList;
    private int option;
    public WestminsterShoppingManager(){
        arrayList = new ArrayList<>();
        loadFile();
        Scanner input = new Scanner(System.in);
        Interface i;
        do {
            System.out.println("_______________________________________________________________"); //Menu bar
            System.out.println("Please Enter 1 to add a new product");
            System.out.println("Please Enter 2 to delete a product");
            System.out.println("Please Enter 3 to print the list of the products");
            System.out.println("Please Enter 4 to save in a file");
            System.out.println("Please Enter 5 to open the GUI");
            System.out.println("Please Enter 6 to exist");

            while(true){
                try{
                    option = input.nextInt();
                    break;
                }catch(InputMismatchException e){
                    System.out.println("Please enter valid input !");
                    input.nextLine();
                }
            }

            switch (option){
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    printProduct();
                    break;
                case 4:
                    try {
                        saveFile();
                    } catch (IOException e) {
                        System.out.println("There is an error !");;
                    }
                    break;
                case 5:
                    User user = new User("user name" , "new password" , 10);
                    i = new Interface(user);
                    break;
                default:
                    System.out.println("Please enter a valid option !");
            }
        }while(option!=5);
    }
//Add a new product
    public void addProduct(){
        Scanner input = new Scanner(System.in);
        int type;
        int availableItems;
        double price;
        int warranty;

        if (arrayList.size()<50){
            while(true){
                try{
                    while(true){
                        System.out.println( //Select which product wants to add
                                "To add Electronic products press 1.\n" +
                                "To add Clothing products press 2 .");

                        type = input.nextInt();
                        if(type == 1 || type == 2){
                            break;
                        }
                        else{
                            System.out.println("Please enter valid number"); // validate the add option
                        }
                    }
                    break;
                }catch(InputMismatchException e){
                    System.out.println("Please enter valid number");
                    input.nextLine();
                }
            }
            if (type == 1){
                input.nextLine();    //Getting product information about electronic products
                System.out.print("Please enter product id -: ");
                String productID = input.nextLine();

                System.out.print("Please enter product name -: ");
                String productName = input.nextLine();

                while(true){
                    try{
                        System.out.print("Please enter number of available item -: ");
                        availableItems = input.nextInt();
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("Please enter valid number of available item");
                        input.nextLine();
                    }
                }

                while(true){
                    try{
                        System.out.print("Please enter price -: ");
                        price = input.nextDouble();
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("Please enter a valid price");
                        input.nextLine();
                    }
                }

                while(true){
                    try{
                        System.out.print("Please enter warranty period  -: ");
                        warranty  = input.nextInt();
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("Please enter a valid warranty period");
                        input.nextLine();
                    }
                }
                input.nextLine();
                System.out.print("Please enter brand -: ");
                String brand = input.nextLine();

                Product elecobj = new Electronics(productID,productName,availableItems,price,brand,warranty);
                arrayList.add(elecobj);
                System.out.println("product added to the arraylist");
                System.out.println("_______________________________________________________________");

            }else if (type == 2) {  //Getting product information about clothing products
                input.nextLine();
                System.out.print("Please Enter product id -: ");
                String productID = input.nextLine();

                System.out.print("Please enter product name -: ");
                String productName = input.nextLine();

                while(true){
                    try{
                        System.out.print("Please Enter number of available item -: ");
                        availableItems = input.nextInt();
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("Please enter a valid number");
                        input.nextLine();
                    }
                }

                while(true){
                    try{
                        System.out.print("Please enter price -: ");
                        price = input.nextDouble();
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("Please enter a valid price");
                        input.nextLine();
                    }
                }
                input.nextLine();
                System.out.print("Please enter colour -: ");
                String colour = input.nextLine();

                System.out.print("Please enter size -: ");
                String size  = input.nextLine();


                Product clothobj = new Clothing(productID,productName,availableItems,price,size,colour);
                arrayList.add(clothobj);
                System.out.println("adding complete");

            }
        }
        else{
            System.out.println("The limit of the products can add is 50 ");
        }
    }
// Delete product
    public void removeProduct() {
        if (arrayList.isEmpty()) {
            System.out.println("array list is empty.");
        } else {
            boolean IDfound = false;
            while (!IDfound){
                System.out.print("Please enter product id -: ");
                Scanner input = new Scanner(System.in);
                String productID = input.nextLine();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getProductID().equals(productID)) {
                        System.out.println(arrayList.get(i).toString());
                        arrayList.remove(i);
                        System.out.println("Total number of products in the list =  " + arrayList.size());
                        System.out.println("-------Product Removed Successfully------");
                        IDfound = true;
                        break;
                    }
                }
                if (!IDfound){
                    System.out.println("ID is not found, please enter correct ID number"); //validate ID No
                }
            }
        }

    }

//Print the list of the products
    public void printProduct(){
        Collections.sort(arrayList, new Comparator<Product>(){
            public int compare(Product productobj1, Product productobj2){
                return productobj1.getProductID().compareTo(productobj2.getProductID());
            }
        });
        for (Object obj : arrayList) {
            if (obj instanceof Clothing) {
                Clothing clothingobj = (Clothing) obj;
                System.out.println("Clothing: " + clothingobj.toString());
            } else if (obj instanceof Electronics) {
                Electronics electronicsobj = (Electronics) obj;
                System.out.println("Electronics: " + electronicsobj.toString());
            } else {
                System.out.println("Unknown object: " + obj.toString());
            }
        }
    }
//products saved to the file
    public void saveFile() throws IOException {
        try{
            File f1 = new File("Productfile.txt");
            if (!f1.exists()){
                f1.createNewFile();
            }
            FileOutputStream fileoutobj = new FileOutputStream(f1);
            ObjectOutputStream objectoutobj = new ObjectOutputStream(fileoutobj);
            Iterator it = arrayList.iterator();
            while (it.hasNext()){
                Product productobj1 = (Product) it.next();
                objectoutobj.writeObject(productobj1);
            }
            System.out.println("----Data Saved Successfully to the File----");
            objectoutobj.close();
            fileoutobj.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("There is an error");
        }catch (Exception e){
            System.out.println("There is an error");
        }
    }
//Load products from the file
    public void loadFile()  {
        try{
            File f1 = new File("Productfile.txt");
            FileInputStream fileoutobj = new FileInputStream(f1);
            ObjectInputStream objectoutobj = new ObjectInputStream(fileoutobj);

            while (true){
                try {
                    Product productobj1 = (Product) objectoutobj.readObject();
                    arrayList.add(productobj1);
                } catch (EOFException e) {
                    break;
                }
            }
            objectoutobj.close();
            fileoutobj.close();

        }catch (FileNotFoundException e){
            System.out.println("file not found");
        } catch (IOException e){
            System.out.println("There is an error");
        }catch (ClassNotFoundException e){
            System.out.println("class is not found ");
        }

    }
}

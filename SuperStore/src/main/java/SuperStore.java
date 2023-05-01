import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SuperStore {
    private static ArrayList<Customer> customersdata = new ArrayList<>(); // List of all customers

    private static ArrayList<Order> orderdata = new ArrayList<>(); // List of all customers

    /**
     * @param args
     */
    public static void main(String[] args)   {
        String filePath = "C:\\Users\\01957\\Downloads\\2023-wk14-final-exercise-Hasan300254\\data/SuperStoreOrders.csv";
        List<Order> orders = readOrdersFromCSV(filePath);
        //System.out.println(customersdata.get(2).getName());
        showMainMenu();
        // Use the orders list to perform further processing
    }
    
    private static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);

        // Display menu options
        System.out.println("Welcome to the application!");
        System.out.println("1. Search for a customer by name");
        System.out.println("2. View orders by customer");
        System.out.println("3. View products by category");
        System.out.println("4. Exit");

        // Get user input
        int choice = scanner.nextInt();

        // Process user choice
        switch (choice) {
            case 1:
                searchCustomerByName();
                break;
            case 2:
            	oderdetailsbycustomer();
                // TODO: Implement view orders by customer
                break;
            case 3:
                // TODO: Implement view products by category
                break;
            case 4:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMainMenu();
        }
    }

    private static void searchCustomerByName() {
        Scanner scanner = new Scanner(System.in);

        // Get search query from user
        System.out.println("Enter customer name:");
        String query = scanner.nextLine();
        
        if (customersdata.isEmpty()) {
            System.out.println("No customers found.");
        }
        else
        {
        	for(int i=0;i<customersdata.size();i++)
        	{
        		System.out.println(customersdata.get(i).getName());
        		if(query.equals(customersdata.get(i).getName()))
        		{
        			System.out.println(customersdata.get(i).getId()+"  "+
        		    customersdata.get(i).getName()+"  "+
        			customersdata.get(i).getSegment()+"  "+
        			customersdata.get(i).getCountry()+"  "+
        			customersdata.get(i).getCity()+"  "+
        			customersdata.get(i).getState()+"  "+
        			customersdata.get(i).getPostalCode()+"  "+
        			customersdata.get(i).getRegion()+"  ");
        		}
        	}
        }

        
        // Show main menu
        showMainMenu();
    }

    private static void oderdetailsbycustomer() {
    	Scanner scanner = new Scanner(System.in);

        // Get search query from user
        System.out.println("Enter customer name:");
        String query = scanner.nextLine();
        
        if (customersdata.isEmpty()) {
            System.out.println("No customers found.");
        }
        else
        {
        	for(int i=0;i<customersdata.size();i++)
        	{
        		System.out.println(customersdata.get(i).getName());
        		if(query.equals(customersdata.get(i).getName()))
        		{
        			
        			for(int j=0;j<orderdata.size();j++)
        			{
        				if(customersdata.get(i).getOrderId().equals(orderdata.get(j).getId()))
        				{
        					       System.out.println(i+"  "+
        							orderdata.get(j).getId()+"  "+
        							orderdata.get(j).getOrderDate()+"  "+
        							orderdata.get(j).getShipDate()+"  "+
        							orderdata.get(j).getSales()+"  "
        		        			);
        				}
        			}
        		}
        	}
        }

        
        // Show main menu
        showMainMenu();
        // TODO: Implement view customer details
    }

    private static List<Order> readOrdersFromCSV(String filePath) throws NumberFormatException {
        List<Order> orders = new ArrayList<>();
        Map<String, Customer> customers = new HashMap<>();
        Map<String, Product> products = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	String line;
            String[] data;
        	line=br.readLine();
            data =line.split(",");
            data = data[0].split(";");
            int lng=data.length;
        	while ((line = br.readLine()) != null) {
        		if(lng==line.length())
        		{
            	 data =line.split(",");
                 data = data[0].split(";");
                // Create objects for each row in the CSV file
                Customer customer =new Customer(data);
                Product product = new Product(data);
                customersdata.add(customer);
                Order order = new Order(
                        data[1], data[2], data[3], data[4], customer,
                        data[14], data[15], data[16], data[8], data[9], data[10],
                        data[11], data[12], product, Double.parseDouble(data[17]),
                        Integer.parseInt(data[18]), Double.parseDouble(data[19]),
                        Double.parseDouble(data[20]));
                orderdata.add(order);

                // Check if the order is part of an existing order
                int index = orders.indexOf(order);
                if (index == -1) {
                    orders.add(order);
                } else {
                    orders.get(index).addLineItem(order.getLineItems().get(0));
                }
        	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }
}

class Customer {
	private final String orderId;
    private final String id;
    private final String name;
    private final String segment;
    private final String country;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String region;

    public Customer(String[] data) {
    	this.orderId = data[1];
        this.id = data[5];
        this.name = data[6];
        this.segment = data[7];
        this.country = data[8];
        this.city = data[9];
        this.state = data[10];
        this.postalCode = data[11];
        this.region = data[12];
    }

    public String getId() {
        return id;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getName() {
        return name;
    }

    public String getSegment() {
        return segment;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getRegion() {
        return region;
    }
}


class Order {
    private final String id;
    private final String orderDate;
    private final String shipDate;
    private final String shipMode;
    private final Customer customer;
    private final String category;
    private final String subCategory;
    private final String productName;
    private final String country;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String region;
    private final Product product;
    private final double sales;
    private final int quantity;
    private final double discount;
    private final double profit;
    private final List<LineItem> lineItems;

    public Order(String id, String orderDate, String shipDate, String shipMode,
                 Customer customer, String category, String subCategory, String productName,
                 String country, String city, String state, String postalCode, String region,
                 Product product, double sales, int quantity, double discount, double profit) {
        this.id = id;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.shipMode = shipMode;
        this.customer = customer;
        this.category = category;
        this.subCategory = subCategory;
        this.productName = productName;
        this.country = country;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.region = region;
        this.product = product;
        this.sales = sales;
        this.quantity = quantity;
        this.discount = discount;
        this.profit = profit;
        this.lineItems = new ArrayList<>();
        lineItems.add(new LineItem(product, sales, quantity, discount, profit));
    }

    public String getId() {
        return id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getShipDate() {
        return shipDate;
    }

    public String getShipMode() {
        return shipMode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getProductName() {
        return productName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getRegion() {
        return region;
    }

    public Product getProduct() {
        return product;
    }

    public double getSales() {
        return sales;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public double getProfit() {
        return profit;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }
}

class Product {
private final String id;
private final String category;
private final String subCategory;
private final String productName;
public Product(String[] data) {
    this.id = data[13];
    this.category = data[14];
    this.subCategory = data[15];
    this.productName = data[16];
}

public String getId() {
    return id;
}

public String getCategory() {
    return category;
}

public String getSubCategory() {
    return subCategory;
}

public String getProductName() {
    return productName;
}

}
class LineItem {
private final Product product;
private final double sales;
private final int quantity;
private final double discount;
private final double profit;
public LineItem(Product product, double sales, int quantity, double discount, double profit) {
    this.product = product;
    this.sales = sales;
    this.quantity = quantity;
    this.discount = discount;
    this.profit = profit;
}

public Product getProduct() {
    return product;
}
}

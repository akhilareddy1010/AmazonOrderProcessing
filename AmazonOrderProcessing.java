
import java.sql.*;
import java.util.Scanner;

public class AmazonOrderProcessing
{
public static void main(String[] args) throws Exception
{
// Scanner object used to take input
Scanner sc = new Scanner(System.in);

// Creating connection between Java and MySQL
Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/amazon_db","root","root");
// Ask user to enter order id
System.out.print("Enter Order ID : ");
int orderId =sc.nextInt();

/*STEP 1   Fetch order details    using order id.*/

PreparedStatement ps1 =con.prepareStatement("select * from orders where order_id=?");
// Replacing ? with actual order id
ps1.setInt(1, orderId);
// Execute select query
ResultSet rs1 =ps1.executeQuery();
// Check whether order exists
if(rs1.next())
{
// Read customer name
String customerName =rs1.getString("customer_name");
// Read product id
int productId =rs1.getInt("product_id");
// Read quantity ordered
int quantity =rs1.getInt("quantity");
/*
STEP 2  Fetch product details   using product id.
*/
PreparedStatement ps2 =con.prepareStatement("select * from products where product_id=?");
ps2.setInt(1,productId);
ResultSet rs2 =ps2.executeQuery();
// Check whether product exists
if(rs2.next())
{
// Product name
String productName =rs2.getString("product_name");
// Available stock
int stock =rs2.getInt("stock");
/*
STEP 3 Verify stock availability
*/
if(stock >= quantity)
{
// Calculate remaining stock
int remainingStock =stock - quantity;
/*
STEP 4
Update stock quantity
*/
PreparedStatement ps3 =con.prepareStatement("update products set stock=? where product_id=?");
ps3.setInt(1,remainingStock);
ps3.setInt(2,productId);
ps3.executeUpdate();
/*
STEP 5
Update order status
*/
PreparedStatement ps4 =con.prepareStatement("update orders set status=? where order_id=?");
ps4.setString(1,"PROCESSED");
ps4.setInt(2,orderId);
ps4.executeUpdate();
/*
STEP 6
Generate shipment
*/
int shipmentId =orderId + 1000;
PreparedStatement ps5 =con.prepareStatement("insert into shipments values(?,?,?)");
ps5.setInt(1,shipmentId);
ps5.setInt(2,orderId);
ps5.setString(3,"READY FOR DISPATCH");
ps5.executeUpdate();
/*
Final Success Output
*/
System.out.println();
System.out.println("ORDER PROCESSED SUCCESSFULLY");
System.out.println("Customer Name : "+ customerName);
System.out.println("Product Name : "+ productName);
System.out.println("Ordered Quantity : "+ quantity);
System.out.println("Remaining Stock : "+ remainingStock);
System.out.println("Shipment ID : "+ shipmentId);
}
else
{
/*
Insufficient Stock
Update order status
as REJECTED
*/
PreparedStatement ps6 =con.prepareStatement("update orders set status=? where order_id=?");
ps6.setString(1,"REJECTED");
ps6.setInt(2,orderId);
ps6.executeUpdate();
System.out.println("ORDER REJECTED");
System.out.println("Reason : Insufficient Stock");
}
}
else
{
System.out.println("PRODUCT NOT FOUND");
}
}
else
{
System.out.println("INVALID ORDER ID");
}
// Close database connection
con.close();
}

}
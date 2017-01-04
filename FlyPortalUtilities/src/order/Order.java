package order;

import java.util.TreeMap;

public class Order {

	private String user;
	private TreeMap<String, Integer> cart;
	private double price;
	private int quantity;
	private String flight;
	private String timestamp;
	
	public Order(String user, TreeMap<String, Integer> cart, double price) {
		this.user = user;
		this.cart = cart;
		this.price = price;
	}
	
	public Order(String user, TreeMap<String, Integer> cart) {
		this.user = user;
		this.cart = cart;
	}
	
	public Order(String user, String flight, int quantity, double price, String timestamp) {
		this.user = user;
		this.flight = flight;
		this.quantity = quantity;
		this.price = price;
		this.timestamp = timestamp;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public TreeMap<String, Integer> getCart() {
		return cart;
	}

	public void setCart(TreeMap<String, Integer> cart) {
		this.cart = cart;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}

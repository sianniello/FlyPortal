package transaction;

public class Transaction {

	private int orderId;
	private String username;
	private String timestamp;
	private double amount;
	private String status;
	
	public Transaction(int orderId, String username, String timestamp,
			double amount, String status) {
		this.orderId = orderId;
		this.username = username;
		this.timestamp = timestamp;
		this.amount = amount;
		this.status = status;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}

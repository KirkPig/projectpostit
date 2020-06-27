package bill;

import java.util.ArrayList;

import logic.Customer;

public class Invoice {

	private String id;
	private String date;
	private Customer customer;
	private ArrayList<Item> itemList;
	private double totalAmount;
	private double tax7;
	private double plusTax7;

	public Invoice(String id, String date, Customer customer, ArrayList<Item> itemList, String attn, String cr) {
		this.setCustomer(customer);
		this.setDate(date);
		this.setId(id);
		this.setItemList(itemList);
		double total = 0.00;
		for (Item e : itemList) {
			total += e.getAmount();
			this.setTotalAmount(total);
			this.setTax7(total * 7 / 100);
			this.setPlusTax7(this.getTotalAmount() + this.getTax7());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTax7() {
		return tax7;
	}

	public void setTax7(double tax7) {
		this.tax7 = tax7;
	}

	public double getPlusTax7() {
		return plusTax7;
	}

	public void setPlusTax7(double plusTax7) {
		this.plusTax7 = plusTax7;
	}
}

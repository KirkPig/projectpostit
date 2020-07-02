package bill;

import java.util.ArrayList;

import logic.Customer;

public class Invoice {

	private String id;
	private String date;
	private Customer customer;
	private ArrayList<Item> itemList;
	private double valueBeforeTax;
	private double valueTax;
	private double valueAfterTax;

	public Invoice(String id, String date, Customer customer, ArrayList<Item> itemList, String attn, String cr) {
		this.setCustomer(customer);
		this.setDate(date);
		this.setId(id);
		this.setItemList(itemList);
		double total = 0.00;
		for (Item e : itemList) {
			total += e.getAmount();
		}
		this.setValueBeforeTax(total);
		this.setValueTax(total * 7 / 100);
		this.setValueAfterTax(getValueBeforeTax() + getValueTax()); 
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

	public double getValueBeforeTax() {
		return valueBeforeTax;
	}

	public void setValueBeforeTax(double valueBeforeTax) {
		this.valueBeforeTax = valueBeforeTax;
	}

	public double getValueTax() {
		return valueTax;
	}

	public void setValueTax(double valueTax) {
		this.valueTax = valueTax;
	}

	public double getValueAfterTax() {
		return valueAfterTax;
	}

	public void setValueAfterTax(double valueAfterTax) {
		this.valueAfterTax = valueAfterTax;
	}
}

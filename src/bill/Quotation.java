package bill;

import java.text.DecimalFormat;
import java.util.ArrayList;

import logic.Customer;

public class Quotation implements Comparable<Quotation>{

	private String id;
	private String date;
	private Customer customer;
	
	private ArrayList<Item> itemList;
	private String attn;
	private String cr;
	private double valueBeforeTax;
	private double valueTax;
	private double valueAfterTax;
	private String creator;
	private String productName;
	private String productQuantity;
	private String productUnit;
	private String valueAfterTaxForTable;
	private String customerName;

	public Quotation(String id, String date, Customer customer, ArrayList<Item> itemList, String attn, String cr,String creator) {
		this.setAttn(attn);
		this.setCr(cr);
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
		this.setProductName(itemList);
		this.setProductQuantity(itemList);
		this.setProductUnit(itemList);
		this.setValueAfterTaxForTable(getValueBeforeTax() + getValueTax());
		this.setCustomerName(customer.getName());
		this.setCreator(creator);
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

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	public String getAttn() {
		return attn;
	}

	public void setAttn(String attn) {
		this.attn = attn;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(ArrayList<Item> itemList) {
		this.productName = "";
		for (Item item : itemList) {
			this.productName += item.getProduct().getDescription() + "\n";
			
		}
		
	}

	public String getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(ArrayList<Item> itemList) {
		this.productQuantity = "";
		for (Item item:itemList) {
			this.productQuantity += Integer.toString(item.getItemQuantity())+ "\n";
		}
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(ArrayList<Item> itemList) {
		this.productUnit = "";
		for (Item item:itemList) {
			this.productUnit += item.getUnit() + "\n";
		}
	}

	public String getValueAfterTaxForTable() {
		return valueAfterTaxForTable;
	}

	public void setValueAfterTaxForTable(double value) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		this.valueAfterTaxForTable = df.format(value);
	}

	@Override
	public int compareTo(Quotation o) {
		return this.getDate().compareTo(o.getDate());
	}
	
}

package bill;

import java.text.DecimalFormat;
import java.util.ArrayList;

import logic.Customer;

public class ProductLoan implements Comparable<ProductLoan>{
	
	private String id;
	private String date;
	private Customer customer;
	private ArrayList<Item> itemList;
	private String contact;
	private double valueBeforeTax;
	private double valueTax;
	private double valueAfterTax;
	private String creator;
	private String productName;
	private String productQuantity;
	private String productUnit;
	private String valueAfterTaxForTable;
	private String customerName;

	
	public ProductLoan(String id, String date, Customer customer, ArrayList<Item> itemList, String contact,String creator) {
		// TODO Auto-generated constructor stub
		this.setCustomer(customer);
		this.setDate(date);
		this.setId(id);
		this.setItemList(itemList);
		this.setContact(contact);
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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
		DecimalFormat df = new DecimalFormat("#,###.##");
		this.valueTax = Double.parseDouble(df.format(valueTax+0.00).replace(",", ""));
	}

	public double getValueAfterTax() {
		return valueAfterTax;
	}

	public void setValueAfterTax(double valueAfterTax) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		this.valueAfterTax = Double.parseDouble(df.format(valueAfterTax+0.00).replace(",", ""));
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
		this.valueAfterTaxForTable = df.format(value+0.00);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public int compareTo(ProductLoan o) {
		// TODO Auto-generated method stub
		return this.getDate().compareTo(o.getDate());
	}
	
	public double onlyTwoDecimalPlaces(String number) {
        StringBuilder sbFloat = new StringBuilder(number);
        int start = sbFloat.indexOf(".");
        if (start < 0) {
            return Double.parseDouble(sbFloat.toString());
        }
        int end = start+3;
        if((end)>(sbFloat.length()-1)) end = sbFloat.length();

        String twoPlaces = sbFloat.substring(start, end);
        sbFloat.replace(start, sbFloat.length(), twoPlaces);
        return Double.parseDouble(sbFloat.toString());
    }
}

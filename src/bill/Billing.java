package bill;

import java.util.ArrayList;

import logic.Customer;

public class Billing {

	private String id;
	private String date;
	private Customer customer;
	private ArrayList<Invoice> invoiceList;
	private ArrayList<String> psList;
	private String billingBy;
	private String billingDate;
	private String ps;
	private double value;

	public Billing(String id, String date, Customer customer, ArrayList<Invoice> invoiceList, ArrayList<String> psList, String billingBy,
			String billingDate, String ps) {
		// TODO Auto-generated constructor stub
		this.setCustomer(customer);
		this.setDate(date);
		this.setId(id);
		this.setBillingBy(billingBy);
		this.setBillingDate(billingDate);
		this.setPs(ps);
		this.setInvoiceList(invoiceList);
		this.setPsList(psList);
		double total = 0.00;
		for(Invoice i : invoiceList) {
			total = total + i.getValueAfterTax();
		}
		this.setValue(total);
		
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

	public String getBillingBy() {
		return billingBy;
	}

	public void setBillingBy(String billingBy) {
		this.billingBy = billingBy;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public ArrayList<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(ArrayList<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public ArrayList<String> getPsList() {
		return psList;
	}

	public void setPsList(ArrayList<String> psList) {
		this.psList = psList;
	}

}
